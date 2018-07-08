package com.powerjun.common;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/7/7.
 */
public class RandomLoadBalance extends AbastractLoadBalance {


    public String doSelect(List<String> cls) {

        return cls.get(new Random().nextInt(cls.size()));

    }
}
