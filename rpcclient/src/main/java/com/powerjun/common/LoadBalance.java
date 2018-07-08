package com.powerjun.common;

import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */
public interface LoadBalance {

    String selectService(List<String> services);
}
