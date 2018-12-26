package com.e9cloud.core.auth.interceptor;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.redis.session.JCookie;
import com.e9cloud.redis.session.JSession;
import com.e9cloud.redis.util.RedisDBUtils;
import com.e9cloud.core.auth.annotation.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.PathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
//import  org.springframework.web.method.HandlerMethod;

/**
 * Created by Administrator on 2015/12/16.
 */
public class WebInvokeInterceptor extends HandlerInterceptorAdapter {

    public Logger logger = LoggerFactory.getLogger(getClass());

    private String[] oathUrl;

    public void setOathUrl(String[] oathUrl) {
        this.oathUrl = oathUrl;
    }

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private AccountService accountService;

    /** 进入Controler方法处理前执行，此处可以进行鉴权、日志打印，前传到登录
     * 功能：前向日志统计， 鉴权
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("----------- WebInvokeInterceptor.preHandle start -----------------");
      ///{SoftVersion}/Accounts/{AccountSid}/Calls/callBack
      //  HandlerMethod handerMethod = (HandlerMethod)handler;
      //  System.out.println(handerMethod);
        String sessonid = request.getSession().getId();
        if(!(handler instanceof HandlerMethod)){

            return super.preHandle(request,response,handler);
        }

        boolean needLogin = false;
        boolean hasLogin = false;

        HandlerMethod handerMethod = (HandlerMethod)handler;
        Method method = handerMethod.getMethod();

        Annotation[] annotations = method.getAnnotations();

        for (Annotation anote : annotations) {
            // 通过注解判断是否需要登录
            // anote.getClass().getSimpleName().equals("Login");
            if(anote.annotationType().getName().equals(Login.class.getName())) {
                needLogin = true;
                break;
            }

        }

        logger.info("oathUrl : {}", Arrays.toString(oathUrl));

        if (Tools.isNotNullArray(this.oathUrl)) {
            PathMatcher matcher = new org.springframework.util.AntPathMatcher();
            String servletPath = request.getServletPath();

            for (String url : oathUrl) {
                logger.info("oathUrl : {}, servletPath : {}", url, servletPath);
                if (needLogin = matcher.match(url, servletPath)){
                    break;
                }
            }
        }

        // 判断是否已经登录
        //1） 判断是否含有key = "AccessToken" 的cookie，如果有,
        Cookie cookie =  WebUtils.getCookie(request, JCookie.AccessToken);
        if(cookie != null && cookie.getValue() != null){
            String accessToken = cookie.getValue();
            JSession jSession = null;
            if((jSession = JSession.read(accessToken)) != null){
                jSession.reExpire();
                //cookie.setMaxAge(3600);

                if (request.getSession().getAttribute(JSession.USER_INFO) == null) {
                    Account account = accountService.getAccountByUid(jSession.getValue());
                    request.getSession().setAttribute(JSession.USER_INFO, account);
                }

                hasLogin = true;
            } else {
                // 清除session
                request.getSession().removeAttribute(JSession.USER_INFO);
            }
        } else {
            // 清除session
            request.getSession().removeAttribute(JSession.USER_INFO);
        }

        logger.info("-------------- needLogin:{}, hasLogin : {}  ---------------------", needLogin, hasLogin);

        //如果没有AccessToken字段，且需要登录， 则跳转登录页面
        if(needLogin && !hasLogin) {

            // 处理ajax请求
            if (com.e9cloud.core.util.WebUtils.isAjax(request)){
                JSonMessage jSonMessage = new JSonMessage("login", appConfig.getLoginUrl());
                com.e9cloud.core.util.WebUtils.sendJson(response, JSonUtils.toJSon(jSonMessage));
                return false;
            } else {
                String redirect_url = request.getRequestURL().toString();
                String url = appConfig.getLoginUrl() + "?redirect_url=" + Encodes.urlEncode(redirect_url);

                // TODO: 2016/1/21 参数处理
                // Map params = request.getParameterMap();

                response.sendRedirect(url);

                logger.info("-------------- redirect_url:" + redirect_url + "  ---------------------");

                return false;
            }

        }
        /*if (request.getSession().getAttribute(JSession.USER_INFO)!= null) {
            Account account = (Account)request.getSession().getAttribute(JSession.USER_INFO);
            String busType = account.getBusType();
            if(Tools.isNotNullStr(busType)&& Constants.USER_BUSTYPE_KFJK.equals(busType)){
                //获取所有智能云调度用户的URL
                PathMatcher matcher = new org.springframework.util.AntPathMatcher();
                String znyddRole = Constants.USER_ROLE_ZNYDD;
                String servletPath = request.getServletPath();
                boolean errorflag = true;
                for (String url : oathUrl) {
                    boolean aflag = url.contains(znyddRole);
                    boolean bflag = matcher.match(url,servletPath);
                    if(aflag&&bflag){
                        String page_403 = appConfig.getAuthUrl()+"error/403";
                        response.sendRedirect(page_403);
                        errorflag = false;
                        break;
                    }
                }
               return errorflag;
            }
        }*/
        return  true;
    }

    /**
     * Controler方法处理完成，进行View渲染前执行， 此时可以进行向View增加model数据,无法前转（sendRedirect）
     * 功能：增加Model数据
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // super.postHandle(request, response, handler, modelAndView);
        //强制添加不缓存
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);

    }


    /**
     * Rest响应数据生成成功（View渲染完成，返回给也没），此处只能作清理 和 异常日志打印，无法前转（sendRedirect）
     * 功能：后向日志统计, 接口性能分析
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

        logger.info("----------- afterCompletion -----------------");

    }
}
