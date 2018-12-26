package com.e9cloud.core.application;

import com.e9cloud.core.common.BaseLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 *
 * @author wzj
 *
 */
public class SpringContextHolder extends BaseLogger implements ApplicationContextAware {

    // 系统为web容器，默认设置为WebApplication
    private static ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static Object getBean(String name) {
        return ctx.getBean(name);
    }

    public static <T> T getBean( Class<T> type) {
        return ctx.getBean(type);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return ctx.getBean(name, type);
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);

        if (applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextHolder.ctx);
        }

        ctx = applicationContext; // NOSONAR
    }

}