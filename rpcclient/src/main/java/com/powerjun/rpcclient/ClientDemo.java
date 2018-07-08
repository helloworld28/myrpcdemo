package com.powerjun.rpcclient;

import com.powerjun.common.ISayHelloService;
import com.powerjun.common.IWorkService;
import com.powerjun.common.ServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2018/7/5.
 */
public class ClientDemo {

    public static void main(String[] args) throws Exception {

        ServiceDiscovery serviceDiscovery = new ServiceDiscovery();

        RemoteServiceProxy sayHelloServiceProxy = new RemoteServiceProxy(ISayHelloService.class, serviceDiscovery);
        RemoteServiceProxy workServiceProxy = new RemoteServiceProxy(IWorkService.class, serviceDiscovery);
        ISayHelloService sayHelloService = (ISayHelloService) Proxy.newProxyInstance(
                ISayHelloService.class.getClassLoader(),
                new Class[]{ISayHelloService.class}, sayHelloServiceProxy);
        IWorkService workService = (IWorkService) Proxy.newProxyInstance(IWorkService.class.getClassLoader(), new Class[]{IWorkService.class}, workServiceProxy);

        System.out.println(sayHelloService.sayHello("Jim"));
        System.out.println(workService.doWork());
        Thread.sleep(1000);
        System.out.println(workService.doWork());
        Thread.sleep(1000);
        System.out.println(workService.doWork());
        Thread.sleep(1000);
        System.out.println(workService.doWork());
    }
}
