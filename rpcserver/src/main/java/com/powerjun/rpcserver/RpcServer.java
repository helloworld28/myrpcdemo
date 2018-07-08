package com.powerjun.rpcserver;

import com.powerjun.RpcService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/7/5.
 */
public class RpcServer {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<String, Object> handleMap = new HashMap<String, Object>();
    private RPCServiceRegister rpcServiceRegister;

    private String ip;

    private int port;

    public RpcServer(String ip, int port) {
        this.port = port;
        this.ip = ip;
        rpcServiceRegister = new RPCServiceRegister();
    }

    public void bind(Object... objects) {

        for (Object object : objects) {
            Class cls = object.getClass().getAnnotation(RpcService.class).value();
            handleMap.put(cls.getName(), object);
            rpcServiceRegister.publishService(object, ip + ":" + port);
        }
    }

    public void listen() {
        try {
            final ServerSocket serverSocket1 = new ServerSocket(port);

            new Thread(new Runnable() {
                public void run() {
                    Socket socket = null;
                    while (true) {
                        try {
                            socket = serverSocket1.accept();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                            try {
                                serverSocket1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        executorService.submit(new ProcessHandler(socket, handleMap));
                    }

                }
            }).start();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


}
