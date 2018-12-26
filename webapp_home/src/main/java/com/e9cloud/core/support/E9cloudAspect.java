package com.e9cloud.core.support;

import com.e9cloud.core.common.BaseLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面用于打印日志
 * Created by Administrator on 2016/4/8.
 */
@Component
@Aspect
public class E9cloudAspect extends BaseLogger{

    //配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
    @Pointcut("execution(public * com.e9cloud..*.*(..))")
    public void aspect(){}

    /*
     * 配置前置通知,使用在方法aspect()上注册的切入点
     * 同时接受JoinPoint切入点对象,可以没有该参数
     */
    @Before("aspect()")
    public void before(JoinPoint joinPoint){
        System.out.println("=================after");
        if(logger.isInfoEnabled()){
            logger.info("================before " + joinPoint);
        }
    }

    //配置后置通知,使用在方法aspect()上注册的切入点
    @After("aspect()")
    public void after(JoinPoint joinPoint){
        if(logger.isInfoEnabled()){
            logger.info("=================after " + joinPoint);
        }
    }

}
