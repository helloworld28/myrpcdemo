package com.powerjun.rpcserver;

import com.powerjun.RpcService;
import com.powerjun.common.ISayHelloService;

/**
 * Created by Administrator on 2018/7/5.
 */
@RpcService(ISayHelloService.class)
public class ISayHelloServiceImpl implements ISayHelloService {
    public String sayHello(String name) {
        return "Hello," + name;
    }
}
