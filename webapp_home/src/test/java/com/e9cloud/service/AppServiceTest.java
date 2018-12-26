package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * test for AccountService
 */
public class AppServiceTest extends BaseTest {

    @Autowired
    private AppService appService;

    @Test
    public void testApp(){
        int count = 0;
        List<AppInfo> appList;
        PageWrapper pageWrapper;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sid", "0687e3828ed84d1782d90105dfeca43f");

        try{
            count = appService.findAppListCountByMap(param);
            pageWrapper = new PageWrapper(2, 3, count, null);
            param.put("start", pageWrapper.getFromIndex());
            param.put("pageSize", 1);
            appList = appService.findAppListByMap(param);
            pageWrapper.setRows(appList);

            logger.info(JSonUtils.toJSon(appList));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
