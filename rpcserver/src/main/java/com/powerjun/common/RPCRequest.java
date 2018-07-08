package com.powerjun.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2018/7/5.
 */
public class RPCRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;
    private String method;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RPCRequest{" +
                "className='" + className + '\'' +
                ", method='" + method + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
