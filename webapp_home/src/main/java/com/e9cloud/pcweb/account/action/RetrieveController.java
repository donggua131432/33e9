package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.RetrieveService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.MailUtils;
import com.e9cloud.pcweb.account.biz.RegAndFindPwdService;
import com.e9cloud.redis.util.RedisDBUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2016/1/13.
 */
@Controller
@RequestMapping("/retrieve")
public class RetrieveController extends BaseController {

    @Autowired
    private RetrieveService retrieveService;

    @Autowired
    private RegAndFindPwdService rafService;

    @Autowired
    private AccountService accountService;


    private static final String RETR_RETRIEVE = "/retrievePwd/home2/retrieve";

    private static final String RETR_SENDEMAILS = "/retrievePwd/home2/sendEmails";

    private static final String REGI_ACCOMPLISH = "/retrievePwd/home2/accomplish";

    private static final String RERI_UPDATEPWD = "/retrievePwd/home2/updatepwd";

    private static final String ERRER = "/common/emailError";
    private final static String EMAIL_ERROR = "acc/emailerror";

    /**
     * 找回主页
     */
    @RequestMapping(value ="index" ,method = RequestMethod.GET)
    public String indexs() {
        return  RETR_RETRIEVE;
    }

    /**
     * 找回主页
     */
    @RequestMapping(value ="completed" ,method = RequestMethod.GET)
    public String completed(Model model,HttpServletRequest request, HttpServletResponse response, String email) {
        model.addAttribute("email", email);
        return  REGI_ACCOMPLISH;
    }


    /**
     * ajax判断email是否是注册时的邮箱
     * @return
     */
    @RequestMapping(value ="validate" ,method = RequestMethod.POST)
    @ResponseBody
    public boolean validate(String email) {

        return !accountService.checkEmailUnique(email);
    }

    /**
     * 发送信息到邮箱中
     * @param model
     * @param request
     * @param response
     * @param email
     * @param verifyCode
     * @return
     * @throws MessagingException
     */
    @RequestMapping(value = "sendEmails", method = RequestMethod.POST)
    public String sendEmails(Model model,HttpServletRequest request, HttpServletResponse response, String email, String verifyCode) throws MessagingException {

        String bool = "false";

        if (Tools.isNullStr(email)) {
            model.addAttribute("email", "请输入您的注册邮箱");
            //return bool;
            return RETR_RETRIEVE;
        }

        if (Tools.isNullStr(verifyCode)) {
            model.addAttribute("yzm", "验证码错误，请刷新验证码");
            return RETR_RETRIEVE;
        }

        //获取kaptcha生成存放在session中的验证码
        String kaptchaValue = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        logger.info("==========verifyCode======="+"==========yzm============"+kaptchaValue);

        //比较输入的验证码和实际生成的验证码是否相同
        if (Tools.isNullStr(kaptchaValue) || !verifyCode.equalsIgnoreCase(kaptchaValue)) {
            model.addAttribute("yzm", "验证码错误，请刷新验证码");
            return RETR_RETRIEVE;
        }

        //发送重设密码链接的邮件
        rafService.sendMailForPwd(email);

        model.addAttribute("email", email);
        model.addAttribute("fum", Tools.toEmailUrl(email));

        logger.info("<><><><><><><><>RETR_SENDEMAILS");

        return RETR_SENDEMAILS;
    }


    /**
     * 修改密码
     * @param password
     * @param passwords
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "updatepwd", method = RequestMethod.POST)
    public String updatepwd(String password,String passwords, HttpServletRequest request,Model model) throws Exception {

        User user = (User) request.getSession().getAttribute("user");

        if (!passwords.equals(password)) {
                model.addAttribute("passerrer", "再次输入密码不正确");
                return RERI_UPDATEPWD;
        }
        //对密码进行加密
        //获取rsa 公参数据
        //pass= DigestUtils.md5Hex(password);
        logger.info("password:" + password);

        Account account = new Account();
        account.setUid(user.getUid());
        account.setPwd(password);
        DigestsUtils.encryption(account);
        retrieveService.udpatepassword(account);

        try {
            RedisDBUtils.del(Constants.REDIS_H_PWD_EMAIL + user.getUid());
            MailUtils.sendForResetPasswordSuc(user.getEmail());
        } catch (Exception me) {
            me.printStackTrace();
            logger.error("send email for resetPasswordSuc or del PWDE from redis: {}", me.getMessage());
        }

        model.addAttribute("email",user.getEmail());


        return this.redirect+appConfig.getAuthUrl()+"retrieve/completed";
    }

    /**
     * 跳转update页面
     * @return
     */
    @RequestMapping(value = "updatepwds", method = RequestMethod.GET)
    public String updatepwds(Model model, String uid, String vcode,HttpServletRequest request) {

        if (Tools.isNullStr(uid) || Tools.isNullStr(vcode)) {
            return ERRER;
        }
        // vcode 过期或被覆盖时
        String sid = RedisDBUtils.get(Constants.REDIS_H_PWD_EMAIL + uid);
        if (Tools.isNullStr(sid) || !vcode.equals(sid)) {
            return ERRER;
        }
        User user = accountService.getUserByUid(uid);
        request.getSession().setAttribute("user",user);
        model.addAttribute("user",user);

        logger.info("----- {} ----", "actAccount end");

        return RERI_UPDATEPWD;
    }
    /**
     * 邮件修改
     */
    @RequestMapping("/upEmail")
    public String updateEmail(HttpServletRequest request, HttpServletResponse response,Model model,String uid,String vcode) throws Exception {
        if (Tools.isNullStr(uid) || Tools.isNullStr(vcode)) {
            return EMAIL_ERROR;
        }
        // vcode 过期或被覆盖时
        String sid = RedisDBUtils.get(Constants.REDIS_H_UPDATE_EMAIL + uid);
        if (Tools.isNullStr(sid) || !vcode.equals(sid)) {
            return EMAIL_ERROR;
        }
        String jsonStr = Tools.unBase64(vcode);
        Map<String,String> map = new Gson().fromJson(jsonStr,Map.class);
        String email = map.get("email");
        User user = new User();
        user.setUid(uid);
        user.setEmail(email);
        try{
            accountService.updateUserInfo(user);
            RedisDBUtils.del(Constants.REDIS_H_UPDATE_EMAIL + uid);
            String encodeEmail =  new String(Encodes.encodeHex(email.getBytes()));
            boolean flag = RedisDBUtils.exists(Constants.REDIS_H_REG_EMAIL + encodeEmail);
            if(flag){
                RedisDBUtils.del(Constants.REDIS_H_REG_EMAIL + encodeEmail);
            }
        }catch (Exception e){
            logger.info("----updateEmail error-------",e);
        }
        return "common/UpemailOK";
    }

    /**
     * 核对图片验证码
     *
     * @param vcode 验证码
     * @return
     */
    @RequestMapping(value = "checkVcode", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkVcode(HttpServletRequest request, String vcode) {

        logger.info("the vcode is {}", vcode);

        if (Tools.isNotNullStr(vcode)) {
            try {
                String code_m = (String) this.getSession(request).getAttribute(Constants.KAPTCHA_SESSION_KEY);

                logger.info("the code_m is {}", code_m);

                return vcode.equals(code_m);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        logger.info("return false");

        return false;
    }

}
