package com.e9cloud.core.support;

import com.e9cloud.core.util.UserUtil;
import com.e9cloud.core.util.WebUtils;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2015/12/16.
 */
public class WebInvokeInterceptor extends HandlerInterceptorAdapter
{
    public Logger logger = LoggerFactory.getLogger(getClass());

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

        // 资源路径
        if(!(handler instanceof HandlerMethod)){
            return super.preHandle(request,response,handler);
        }

        // 没有登录 不做处理
        if (!UserUtil.isLogin()) {
            return true;
        }

        // 校验路径权限
        String servletPath = request.getServletPath();
        User user = UserUtil.getCurrentUser();

        // 根路径和ajax直接放过 管理员直接放过
        if ("/".equals(servletPath) || "/main".equals(servletPath) || WebUtils.isAjax(request) || (user !=null  && "admin".equals(user.getUsername()))){
            return true;
        }

        // 校验路径权限
        if (!accountService.hasAction(UserUtil.getCurrentUserId(), servletPath)){
            response.sendError(HttpStatus.SC_FORBIDDEN);
            return false;
        }

        logger.info("============ servletPath:{} ============", servletPath);

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

    private boolean vildate(HttpServletRequest request) {
        request.getSession().getAttribute("menus");
        return true;
    }
}
