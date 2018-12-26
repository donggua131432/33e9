package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.Interest;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.MailUtils;
import com.e9cloud.pcweb.account.biz.RegAndFindPwdService;
import com.e9cloud.redis.util.RedisDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户注册及激活
 *
 * Created by Administrator on 2016/1/13.
 */
@Controller
@RequestMapping("/reg")
public class RegisterController extends BaseController {

    private static int timeout = 24*3600;

    private static int time_out_for_mobile = 120;

    private static final String REG_SEND = "register/home2/reg_send";

    private static final String REG_SEND_SUCC = "register/home2/reg_sendsucc";

    private static final String REG_FILL = "register/home2/reg_fill";

    private static final String REG_TOLOGIN = "register/home2/reg_tologin";

    private static final String INDEX = "index";

    private static final  String EMAIL_ERROR = "common/emailError";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegAndFindPwdService regAndFindPwdService;

    /**
     * 跳转到注册页面
     *
     * @return 注册页面
     */
    @RequestMapping(value = "toSign", method = RequestMethod.GET)
    public String index(){
        return REG_SEND;
    }

    /**
     * 向用户发送激活码
     * @return 注册页面
     */
    @RequestMapping(value = "toSign", method = RequestMethod.POST)
    public String sendcode(Model model, String email, String agreement) throws MessagingException {

        logger.info("----- {} ------", "sendcode start");

        // 同意协议
        if (!"Y".equals(agreement)) {
            model.addAttribute("error", "请同意并勾选玖云平台服务条款");
            return REG_SEND;
        }

        // 邮箱为空
        if (Tools.isNullStr(email)) {
            model.addAttribute("error", "请输入您的邮箱");
            return REG_SEND;
        }

        // 邮箱不正确
        /*if (!RegexUtils.checkEmail(email)) {
            model.addAttribute("error", "邮箱格式不正确！");
            return REG_SEND;
        }*/

        // 邮箱已被注册
        if (!accountService.checkEmailUnique(email)) {
            model.addAttribute("error", "账号已存在，请直接登录");
            return REG_SEND;
        }

        String resultStatus = regAndFindPwdService.sendMailForReg(email);

        String emailurl = Tools.toEmailUrl(email);

        model.addAttribute("status", resultStatus);
        model.addAttribute("email", email);
        model.addAttribute("emailurl", emailurl);

        logger.info("----- {} ----", "sendcode end");

        return REG_SEND_SUCC;
    }

    /**
     * 校验用户vcode和uid
     * @return 补充注册信息页面
     */
    @RequestMapping(value = "actAccount", method = RequestMethod.GET)
    public String actAccount(Model model, String uid, String vcode) throws MessagingException {

        logger.info("----- {} ------", "actAccount start");

        // uid 或 vcode 为空时
        if (Tools.isNullStr(uid) || Tools.isNullStr(vcode)) {
            return EMAIL_ERROR;
        }

        // vcode 过期或被覆盖时
        String sid = "";
        try {
            sid = RedisDBUtils.get(Constants.REDIS_H_REG_EMAIL + uid);
        } catch (Exception e){ // redis 异常
            e.printStackTrace();
            return EMAIL_ERROR;
        }

        if(Tools.isNullStr(sid) || !sid.equals(vcode)){
            return EMAIL_ERROR;
        }

        // User user = accountService.getUserByUid(uid);
        String email = new String(Encodes.decodeHex(uid));
        if (checkemail(email)) {
            // model.addAttribute("uid", user.getUid());
            model.addAttribute("email", email);
            model.addAttribute("vcode", vcode);
        }

        logger.info("----- {} ----", "actAccount end");

        return REG_FILL;
    }

    /**
     * 校验用户vcode和uid
     * @return 补充注册信息页面
     */
    @RequestMapping(value = "addAccount")
    public String actAccount(Model model, Account account, String vcode, String code, String[] dicId) throws MessagingException {

        logger.info("----- {} ------", "addAccount start");

        model.addAttribute("email", account.getEmail());
        model.addAttribute("vcode", vcode);

        String code_m = RedisDBUtils.get(Constants.REDIS_H_REG_MOBILE + account.getMobile());

        String email = account.getEmail();
        if (Tools.isNullStr(account.getEmail())) {
            logger.info("----- status {} ------", 1);
            model.addAttribute("vcodeMsg", "验证码错误");
            return REG_FILL;
        }

        // vcode 过期或被覆盖时
        String sid = RedisDBUtils.get(Constants.REDIS_H_REG_EMAIL + Encodes.encodeHex(email.getBytes()));
        if(Tools.isNullStr(sid) || !sid.equals(vcode)){
            logger.info("----- status {} ------", 2);
            model.addAttribute("vcodeMsg", "验证码错误");
            return REG_FILL;
        }

        logger.info("--------code_m:{}----code:{}-------", code_m, code);

        // 验证码失效
        if (Tools.isNullStr(code_m) || !code_m.equals(code)) {
            logger.info("----- {} ------", "验证码错误");
            model.addAttribute("mobile", account.getMobile());
            if (RedisDBUtils.exists(Constants.REDIS_H_REG_MOBILE + account.getMobile())) {
                model.addAttribute("ttl", RedisDBUtils.ttl(Constants.REDIS_H_REG_MOBILE + account.getMobile()));
            }
            model.addAttribute("mobile", account.getMobile());
            model.addAttribute("vcodeMsg", "验证码错误");
            return REG_FILL;
        }

        // 密码为空
        if (Tools.isNullStr(account.getPwd())) {
            logger.info("----- {} ------", "密码不能为空");
            model.addAttribute("msg", "密码不能为空");
            return REG_FILL;
        }

        String r = null;
        try {
            r = regAndFindPwdService.completeRegExecute(account, dicId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("----- status:{} ------", r);

        if (Constants.RESULT_STATUS_OK.equals(r)) {
            logger.info("----- {} ------", "addAccount end r");
            try {
                RedisDBUtils.del(Constants.REDIS_H_REG_EMAIL + Encodes.encodeHex(email.getBytes()));
            } catch (Exception e) { e.printStackTrace(); }

            return REG_TOLOGIN;
        } else {

            logger.info("----- {} ------", "addAccount end end");

            return REG_FILL;
        }

    }

    /**
     * 完成注册
     * @return 补充注册信息页面
     */
    @RequestMapping(value = "regComplete")
    public String regComplete(){
        return REG_TOLOGIN;
    }

    /**
     * 发送短息
     * @return 信息
     */
    @RequestMapping(value = "sendSms", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage sendSms(String mobile) {

        logger.info("------------RegisterController sendSms for mobile:{}------------", mobile);

        if (Tools.isNotNullStr(mobile) && RegexUtils.checkMobile(mobile)) {

            logger.info("-----------sendSms start for {}---------", mobile);

            long remain_time = time_out_for_mobile;

            if (RedisDBUtils.exists(Constants.REDIS_H_REG_MOBILE + mobile)) {
                remain_time = RedisDBUtils.ttl(Constants.REDIS_H_REG_MOBILE + mobile);
                return  new JSonMessage("ttl", String.valueOf(remain_time), null);
            }

            if (regAndFindPwdService.sendSmsForReg(mobile)){
                return new JSonMessage(Constants.RESULT_STATUS_OK, "", null);
            }
        }

        logger.info("------------RegisterController sendSms the mobile:{} is invalid------------", mobile);

        return new JSonMessage(Constants.RESULT_STATUS_ERROR, "", null);
    }

    /**
     * 校验邮箱是否唯一
     * @param email
     * @return boolean false:不唯一 true:唯一
     */
    @RequestMapping(value = "checkemail", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkemail(String email){
        return accountService.checkEmailUnique(email);
    }

    /**
     * 校验手机号是否唯一
     * @param mobile
     * @return boolean false:不唯一 true:唯一
     */
    @RequestMapping(value = "checkmoblie", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkmoblie(String mobile){
        return accountService.checkMobileUnique(mobile);
    }

    /**
     * 核对手机校验码
     * @param  mobile 手机号
     * @param vcode 验证码
     * @return
     */
    @RequestMapping(value = "checkvcode", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkvcode(String mobile, String vcode) {

        logger.info("the mobile is {} and the vcode is {}", mobile, vcode);

        if (Tools.isNotNullStr(mobile) && Tools.isNotNullStr(vcode)) {
            try {
                String code_m = RedisDBUtils.get(Constants.REDIS_H_REG_MOBILE + mobile);

                logger.info("the code_m is {}", code_m);

                return Tools.isNotNullStr(code_m) && code_m.equals(vcode);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }

        logger.info("return false");

        return false;
    }
}
