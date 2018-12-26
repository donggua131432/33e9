package com.e9cloud.core.support;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import  org.springframework.web.method.HandlerMethod;

/**
 * Created by Administrator on 2015/12/16.
 */
public class RestInvokeInterceptor extends HandlerInterceptorAdapter
{
    /** 进入Controler方法处理前执行，此处可以进行鉴权、日志打印，前传到登录
     * 功能：前向日志统计， 鉴权
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
      ///{SoftVersion}/Accounts/{AccountSid}/Calls/callBack
      //  HandlerMethod handerMethod = (HandlerMethod)handler;
      //  System.out.println(handerMethod);

        String url = request.getRequestURI();
        String sig =  request.getParameter("sig");

        System.out.println(url);
        System.out.println(sig);

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
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
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

        System.out.println("----------- afterCompletion -----------------");

    }
}
