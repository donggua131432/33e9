package com.e9cloud.core.support;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.e9cloud.core.util.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 主要用于处理ajax请求
 * Created by Administrator on 2016/3/18.
 */
public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter{

    private static final Logger log = LoggerFactory.getLogger(SimpleFormAuthenticationFilter.class);

    /*
     * 主要是针对登入成功的处理方法。对于请求头是AJAX的之间返回JSON字符串。
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (!WebUtils.isAjax(httpServletRequest)) {// 不是ajax请求
            issueSuccessRedirect(request, response);
        } else {
            WebUtils.sendJson(httpServletResponse,
                    JSonUtils.toJSon(new JSonMessage(R.OK, "", UserUtil.getCurrentUser())));
        }
        return false;
    }

    /**
     * 主要是处理登入失败的方法
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        if (!WebUtils.isAjax((HttpServletRequest) request)) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {

            JSonMessage jsonMessage = new JSonMessage();
            String message = e.getClass().getName();
            if (IncorrectCredentialsException.class.getName().equals(message)) {
                jsonMessage.setCode(R.ERROR);
                jsonMessage.setMsg("用户名或密码错误");
            } else if (UnknownAccountException.class.getName().equals(message)) {
                jsonMessage.setCode(R.ERROR);
                jsonMessage.setMsg("用户名或密码错误");
            } else {
                jsonMessage.setCode(R.ERROR);
                jsonMessage.setMsg("用户名或密码错误");
            }

            WebUtils.sendJson((HttpServletResponse) response, JSonUtils.toJSon(jsonMessage));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * 所有请求都会经过的方法。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                // allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the "
                        + "Authentication url [" + getLoginUrl() + "]");
            }
            if (!WebUtils.isAjax((HttpServletRequest) request)) {// 不是ajax请求
                saveRequestAndRedirectToLogin(request, response);
            } else {
                WebUtils.sendJson((HttpServletResponse) response, JSonUtils.toJSon(new JSonMessage(R.LOGIN, "", null)));
            }
            return false;
        }
    }
}
