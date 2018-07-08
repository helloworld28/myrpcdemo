package com.powerjun.common;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */
public abstract class AbastractLoadBalance implements LoadBalance {


    public String selectService(List<String> services) {

        if (services == null || services.size() == 0) {
            return null;
        } else if (services.size() == 1) {
            return services.get(0);
        } else {
            return doSelect(services);
        }
    }

    public abstract String doSelect(List<String> services);
}
