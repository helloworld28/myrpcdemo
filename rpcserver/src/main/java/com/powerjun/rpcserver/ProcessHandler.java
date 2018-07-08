package com.powerjun.rpcserver;

import com.powerjun.common.RPCRequest;
import com.powerjun.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/5.
 */
public class ProcessHandler implements Runnable {

    private Socket socket;

    private Map<String, Object> handleMap;

    public ProcessHandler(Socket socket, Map<String, Object> handleMap) {
        this.socket = socket;
        this.handleMap = handleMap;
    }

    public void process() {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest rpcRequest = (RPCRequest) objectInputStream.readObject();

            System.out.println("收到请求-》" + rpcRequest);
            String className = rpcRequest.getClassName();

            Object service = handleMap.get(className);
            Class[] parameterTypes = null;

            if (rpcRequest.getParameters() != null) {
                parameterTypes = new Class[rpcRequest.getParameters().length];

                for (int i = 0; i < rpcRequest.getParameters().length; i++) {
                    parameterTypes[i] = rpcRequest.getParameters()[i].getClass();
                }
            }

            Method method = service.getClass().getMethod(rpcRequest.getMethod(), parameterTypes);
            Object ressult = method.invoke(service, rpcRequest.getParameters());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(new RPCResponse(ressult));

            objectInputStream.close();
            objectOutputStream.close();

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void run() {
        process();
    }
}
