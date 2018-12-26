package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.session.JCookie;
import com.e9cloud.redis.session.JSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

/**
 * Created by Administrator on 2016/1/4.
 */

@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, String redirect_url, Model model, String email) {
        logger.info("-------------- redirect_url:" + redirect_url);

        model.addAttribute("redirect_url", redirect_url);

        if (Tools.isNotNullStr(email)) {
            model.addAttribute("username", email);
        }

        // 未登录 或者 登录超时 清除islogin
        JCookie jCookie = new JCookie(request, response);
        if (Tools.isNullStr(jCookie.getCookieValue(JCookie.AccessToken)) || !JSession.exists(jCookie.getCookieValue(JCookie.AccessToken))) {
            jCookie.removeCookie(JCookie.isLogin);
        }

        logger.info("-------------- to login page --------------");

        return "login2";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(Model model,HttpServletResponse response,HttpServletRequest request, String username, String password, String redirect_url) {
        /**
        //获取传过来的验证码
        String verifyCode = request.getParameter("verifyCode");
        if(Tools.isNullStr(verifyCode)){
            model.addAttribute("msg","验证码不能为空！");
            return "login";
        }

        try {
            //获取kaptcha生成存放在session中的验证码
            String kaptchaValue = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

            //比较输入的验证码和实际生成的验证码是否相同
            if(kaptchaValue == null || kaptchaValue == "" || !verifyCode.equalsIgnoreCase(kaptchaValue)) {
                model.addAttribute("msg","验证码错误！");
                return "login";
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        */

        model.addAttribute("username", username);

        if(Tools.isNotNullStr(redirect_url)){
            model.addAttribute("redirect_url", redirect_url);
        }

        if(Tools.isNullStr(username) && Tools.isNullStr(password)){
            model.addAttribute("msg","账号或密码错误，请重新输入！");
            return "login2";
        }

        if(Tools.isNullStr(username)){
            model.addAttribute("msg","账号或密码错误，请重新输入！");
            return "login2";
        }

        if(Tools.isNullStr(password)){
            model.addAttribute("msg","账号或密码错误，请重新输入！");
            return "login2";
        }

        logger.info("username:{}, password:{}", username, password);

        Account account = accountService.getAccountForAuthentication(username);

        if(account == null ){
            model.addAttribute("msg","账号或密码错误，请重新输入！");
            return "login2";
        }

        //校验用户名和密码，如果成功，设置AccessToken, 和 缓存用户用户
        // 1.校验用户名和密码
        String pwd = RSAUtils.decryptStringByJs(password);

        if (DigestsUtils.checkpwd(account, pwd)) {
            //新增权限冻结、禁用控制
            String stt = account.getStatus();
            if (Constants.USER_STATUS_FROZEN.equals(stt)) {//冻结状态
                model.addAttribute("msg","用户已被冻结！请联系客户人员");
                return "login2";
            } else if (Constants.USER_STATUS_DISABLED.equals(stt)) {//禁用状态
                model.addAttribute("msg","用户已被禁用！请联系客户人员");
                return "login2";
            } else {
                // 2.如果成功，设置AccessToken
                String AccessToken = UUID.randomUUID().toString();

                JCookie jCookie = new JCookie(request, response);
                jCookie.createCookie(JCookie.AccessToken, AccessToken, "/", null);
                jCookie.createCookie(JCookie.isLogin,"true", "/", JCookie.domain);
                request.getSession().setAttribute(JSession.USER_INFO, account);

                // 3. 缓存用户用户
                JSession jSession = new JSession(AccessToken, account.getUid());
                jSession.create();

                logger.info("========================= {} ==============================", "createSession");

                //try {
                    /*if (Tools.isNotNullStr(redirect_url)) {
                        logger.info(redirect_url);
                        response.sendRedirect(redirect_url);

                        return null;
                    }*/
                    return redirect + "/accMgr/index";

                //} catch (IOException e) {
                  //  e.printStackTrace();
                //}
            }
        }

        model.addAttribute("msg","账号或密码错误，请重新输入！");

        return "login2";
    }

    // 登录成功页
    @RequestMapping(value = "suc", method = RequestMethod.GET)
    public String loginsuc() {
        return "login_success";
    }

    // 登出
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        JCookie jCookie = new JCookie(request, response);

        Cookie cookie = jCookie.getCookie(JCookie.AccessToken);

        if (cookie != null) {
            // 清除cookie
            jCookie.removeCookie(JCookie.AccessToken);
            jCookie.removeCookie(JCookie.isLogin,JCookie.domain);
            String value = cookie.getValue();

            // 清除redis
            JSession jSession = new JSession(value);
            jSession.delete();

            this.removeAttributeFromSession(request, has_read);
            // 销毁session
            request.getSession().invalidate();
        }

        return redirect + "/auth";
    }

}
