package com.e9cloud.mybatis.service;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.util.*;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/2/25.
 */
public class LogAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(LogAuthenticationFilter.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private OperationLogService operationLogService;

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        try {
            operationLogService.saveOperationLog(LogType.LOGIN.getName(), LogType.LOGIN.getName(), LogType.LOGIN, httpServletRequest);
        } catch (Exception e) { e.printStackTrace(); }

        return super.onLoginSuccess(token, subject, request, response);

    }


    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        log.info("on access denied start");

        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                log.info("------------------isLoginSubmission true-----------------");

                if (WebUtils.isAjax((HttpServletRequest) request)) {
                    log.info("------------------ajax-----------------");
                }

                return executeLogin(request, response);

            } else {

                log.info("------------------isLoginSubmission false-----------------");

                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                // allow them to see the login page ;)
                if (WebUtils.isAjax((HttpServletRequest) request)) {// ajax请求

                    log.info("on access denied ajax loginUrl:{}", appConfig.getUrl());
                    WebUtils.sendJson((HttpServletResponse) response, JSonUtils.toJSon(new JSonMessage(R.LOGIN, appConfig.getUrl(), null)));

                    return false;
                }

                return true;
            }
        } else {

            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }

            if (!WebUtils.isAjax((HttpServletRequest) request)) {// 不是ajax请求
                log.info("on access denied http");
                saveRequestAndRedirectToLogin(request, response);
            } else {
                log.info("on access denied ajax loginUrl:{}", appConfig.getUrl());
                WebUtils.sendJson((HttpServletResponse) response, JSonUtils.toJSon(new JSonMessage(R.LOGIN, appConfig.getUrl(), null)));
            }

            return false;
        }
    }
}
