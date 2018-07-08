package com.powerjun.rpcserver;

import com.powerjun.RpcService;
import com.powerjun.common.IWorkService;

/**
 * Created by Administrator on 2018/7/8.
 */
@RpcService(IWorkService.class)
public class WorkServiceImpl implements IWorkService {

    private String name;

    public WorkServiceImpl(String name) {
        this.name = name;
    }

    public String doWork() {
        return name + " is coding!!";
    }
}
