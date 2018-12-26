package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.service.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/12/14.
 */
public class HelloServiceTest extends BaseTest{

    @Autowired
    private HelloService helloService;

    @Test
    public void testGetValueByValue(){
        String value = helloService.getValueByKey("url");
        logger.info(value);
    }

    @Test
    public void testGetDataByPage(){
        PageWrapper pageData = helloService.getDataByPage(new Page());
        logger.info(JSonUtils.toJSon(pageData));
    }
}
