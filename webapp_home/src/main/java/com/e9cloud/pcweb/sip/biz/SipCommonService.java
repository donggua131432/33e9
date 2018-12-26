package com.e9cloud.pcweb.sip.biz;

import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Dukai on 2016/7/19.
 */
@Service
public class SipCommonService extends BaseController{

    @Autowired
    private AppService appService;
    /**
     * 获取sip应用信息
     * @param request
     * @return
     */
    public AppInfo getSipAppInfo(HttpServletRequest request){
        //获取当前登录的用信息
        Account account = (Account) request.getSession().getAttribute(JSession.USER_INFO);
        Map<String, Object> map = new HashMap<>();
        map.put("sid",account.getSid());
        map.put("busType", "03");
        map.put("status", "00");
        List<AppInfo> appInfos = appService.selectAppInfoListByMap(map);
        if (appInfos != null && appInfos.size() > 0) {
            return appInfos.get(0);
        }
        return new AppInfo();
    }

}
