package com.e9cloud.core.support;

import com.e9cloud.core.common.LogType;
//import com.e9cloud.mybatis.service.OperationLogService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/2/26.
 */
public class LogSessionListener extends SessionListenerAdapter {

    //@Autowired
    //private OperationLogService operationLogService;

    @Override
    public void onStart(Session session) { //会话创建时触发

    }

    @Override
    public void onStop(Session session) { //退出/会话过期时触发
        try {
            //operationLogService.saveOperationLog(LogType.LOGOUT.getName(), "", LogType.LOGOUT);
        } catch (Exception e) { e.printStackTrace(); }

    }

    @Override
    public void onExpiration(Session session) { //会话过期时触发
        try {
            //operationLogService.saveOperationLog(LogType.LOGOUT.getName(), "", LogType.LOGOUT);
        } catch (Exception e) { e.printStackTrace(); }

    }

}
