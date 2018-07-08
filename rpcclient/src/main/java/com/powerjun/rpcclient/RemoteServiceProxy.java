package com.powerjun.rpcclient;

import com.powerjun.common.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/7/5.
 */
public class RemoteServiceProxy implements InvocationHandler {

    private ServiceDiscovery serviceDiscovery;
    private LoadBalance loadBalance;


    public RemoteServiceProxy(Class serviceName, ServiceDiscovery serviceDiscovery) throws Exception {
        this.serviceDiscovery = serviceDiscovery;
        serviceDiscovery.watchService(serviceName);
        loadBalance = new RandomLoadBalance();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCResponse respose = null;
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethod(method.getName());
        rpcRequest.setParameters(args);


        List<String> servies = serviceDiscovery.getServies(method.getDeclaringClass());
        String service = loadBalance.selectService(servies);
        if (service == null || "".equals(service)) {
            throw new RuntimeException("no available service->" + method.getDeclaringClass());
        }

        String[] array = service.split(":");
        System.out.println("开始调用远程服务-》" + service);
        Socket sock = new Socket(array[0], Integer.parseInt(array[1]));
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);

            objectOutputStream.flush();


            ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());

            respose = (RPCResponse) objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
        } catch (Exception e) {
            System.out.println(e.getCause());
        } finally {
            sock.close();
        }
        System.out.println("调用远程服务结束-》" + service);
        return respose.getObject();


    }
}
