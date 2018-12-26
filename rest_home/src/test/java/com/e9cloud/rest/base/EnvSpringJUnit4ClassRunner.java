package com.e9cloud.rest.base;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 设置环境变量
 * Created by wzj on 2016/11/15.
 */
public class EnvSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

    static {
        System.setProperty("envName", "DEV"); // 设置环境变量
        System.setProperty("logHome", "D:/log"); // 设置环境变量
        System.setProperty("snCode", "0218"); // 设置环境变量
        System.setProperty("zyCode", "001"); // 设置环境变量
        System.setProperty("ipPort", "127.0.0.1:88"); // 设置环境变量
    }

    public EnvSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
