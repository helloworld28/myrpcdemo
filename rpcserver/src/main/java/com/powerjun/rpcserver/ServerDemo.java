package com.powerjun.rpcserver;

import com.powerjun.common.ISayHelloService;
import com.powerjun.common.IWorkService;

/**
 * Created by Administrator on 2018/7/5.
 */
public class ServerDemo {
    public static void main(String[] args) {
        RpcServer rpcServer1 = new RpcServer("127.0.0.1", 8088);
        RpcServer rpcServer2 = new RpcServer("127.0.0.1", 8089);
        RpcServer rpcServer3 = new RpcServer("127.0.0.1", 8090);

        ISayHelloService iSayHelloService = new ISayHelloServiceImpl();
        IWorkService iWorkService = new WorkServiceImpl("Jim");
        rpcServer1.bind( iSayHelloService, iWorkService);
        rpcServer1.listen();


        ISayHelloService iSayHelloService2 = new ISayHelloServiceImpl();
        IWorkService iWorkService2 = new WorkServiceImpl("Jac");
        rpcServer2.bind( iWorkService2, iSayHelloService2);
        rpcServer2.listen();

        ISayHelloService iSayHelloService3 = new ISayHelloServiceImpl();
        IWorkService iWorkService3 = new WorkServiceImpl("James");
        rpcServer3.bind( iWorkService3, iSayHelloService3);
        rpcServer3.listen();
    }
}
