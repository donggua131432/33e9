package com.e9cloud.mybatis.service;

import com.e9cloud.core.common.LogType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户监听session销毁
 * Created by Administrator on 2016/2/26.
 */
public class LogWebSessionManager extends DefaultWebSessionManager {

    @Autowired
    private OperationLogService operationLogService;

    @Override
    public void destroy() {
        ServletRequest request = ((WebSubject) SecurityUtils.getSubject()).getServletRequest();
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        operationLogService.saveOperationLog(LogType.LOGOUT.getName(), LogType.LOGOUT.getName(), LogType.LOGOUT, httpServletRequest);
        super.destroy();
    }
}
