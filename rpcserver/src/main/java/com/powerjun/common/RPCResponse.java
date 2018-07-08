package com.powerjun.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/5.
 */
public class RPCResponse implements Serializable {
    private Object object;

    public RPCResponse() {
    }

    public RPCResponse(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
