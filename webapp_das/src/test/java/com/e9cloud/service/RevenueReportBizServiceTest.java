package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.pcweb.revenue.biz.RevenueReportBizService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/9.
 */
public class RevenueReportBizServiceTest extends BaseTest {

    @Autowired
    private RevenueReportBizService bizService;

    @Test
    public void testMonthDetails() {
        Map<String, Object> details = bizService.monthDetails("2016-11", "72c04090dda24bc49d124e442cebd110", "");
        logger.info(JSonUtils.toJSon(details.get("rest")));
        logger.info(JSonUtils.toJSon(details.get("mask")));
        logger.info(JSonUtils.toJSon(details.get("cc")));
        logger.info(JSonUtils.toJSon(details.get("sip")));
        logger.info(JSonUtils.toJSon(details.get("sp")));
    }
}
