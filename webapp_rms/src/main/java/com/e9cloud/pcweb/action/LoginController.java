package com.e9cloud.pcweb.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.support.DisableException;
import com.e9cloud.core.support.FreezeException;
import com.e9cloud.core.support.SystemPermissionException;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.pcweb.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 *
 * 真正登录的POST请求由Filter完成,
 *
 * @author wzj
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Subject user = SecurityUtils.getSubject();
        if (user.isAuthenticated()) {
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String fail(HttpServletRequest req,
                       @RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
        String error = null;
        String exceptionClassName = (String) req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        logger.info(exceptionClassName);

        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "账号或密码错误，请重新输入";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "账号或密码错误，请重新输入";
        } else if (SystemPermissionException.class.getName().equals(exceptionClassName)) {
            error = "无该系统权限";
        } else if (FreezeException.class.getName().equals(exceptionClassName)) {
            error = "账号被冻结，请联系管理员";
        } else if (DisableException.class.getName().equals(exceptionClassName)) {
            error = "账号被禁用，请联系管理员";
        } else if (exceptionClassName != null) {
            error = "账号或密码错误，请重新输入";
        }

        model.addAttribute("error", error);
        model.addAttribute("username", userName);

        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        logger.info("================================logout=============================");

        LogUtil.log("退出", "退出", LogType.LOGOUT);
        SecurityUtils.getSubject().logout();

        return redirect + "/";
    }
}