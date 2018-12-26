package com.e9cloud.base;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 设置环境变量
 * Created by wzj on 2016/11/15.
 */
public class EnvSpringJUnit4ClassRunner extends SpringJUnit4ClassRunner {

    static {
        System.setProperty("envName", "DEV"); // 设置环境变量
    }

    public EnvSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
