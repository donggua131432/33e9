package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.util.LogUtil;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by Administrator on 2016/5/23.
 */
public class OperationLogServiceTest extends BaseTest {

    @Test
    @Rollback(false)
    public void testLogUtil(){
        logger.info("========================================================");
        LogUtil.log("测试", "这是一个测试数据", LogType.DELETE);
        logger.info("========================================================");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
