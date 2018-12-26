package com.e9cloud.base;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.BaseLogger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类作用：测试基类,在编写模块测试时，请继承该类
 *
 * @author wangzhongjun
 *
 *         使用说明：各测试类扩展本类
 */
@RunWith(EnvSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/test/applicationContext.xml" })
@Transactional
public abstract class BaseTest extends BaseLogger{

    /**
     * 设置全局logger，用于日志输出
     *
     * for example:
     * logger.info("----------- your log ------------");
     *
     */

    /**
     * 注入ApplicationContext，方便在测试环境中，得到bean
     *
     * for example：
     * HelloService helloService = ctx.getBean(HelloService.class);
     *
     */
    @Autowired
    protected ApplicationContext ctx;

    /**
     * 方法执行前，先注入ctx
     */
    @Before
    public void baseBefore() {
        logger.info("------------------- super before method -------------------");
        SpringContextHolder springContextHolder = new SpringContextHolder();
        springContextHolder.setApplicationContext(ctx);
    };

}