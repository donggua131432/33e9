package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.GenerateLinkUtils;
import com.e9cloud.pcweb.account.biz.MailUtils;
import com.e9cloud.pcweb.app.biz.AppCommonService;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.redis.util.RedisDBUtils;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.msg.sms.SmsUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 账户管理（基础资料查看、修改邮箱、修改手机号、密码设置）
 *
 */
@Controller
@RequestMapping("/accMgr")
public class AccMgrController extends BaseController{

    private final static int MAIL_TIME_OUT = 3600; // 1小时过期
    private final static int MOBILE_TIME_OUT = 120; // 2分钟
    private final static String MOBILE_KEY_HEAD = "MOBILE_OTP:";
    private final static String USER_VIEW = "acc/userView";
    private final static String USER_INDEX = "acc/userIndex";
    private final static String USER_UPDATE = "acc/userUpdate";
    private final static String PWD_VIEW = "acc/pwdView";
    private final static String PWD_SUC = "acc/pwdSuc";
    private final static String EMAIL_SHOW = "common/UpemailOK";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppCommonService appCommonService;

    /**
     * 账户主页
     */
    @RequestMapping("/index")
    public String queryAccIndex(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        logger.info("queryAccInfo start");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        Account dbAccount = accountService.getAccountByUid(account.getUid());
        String authFlag = (String)request.getSession().getAttribute("authFlag");
        String sFlag = accountService.getAuthStatusByUid(account.getUid());
        model.addAttribute("account", dbAccount);
        model.addAttribute("authFlag", authFlag);
        model.addAttribute("sFlag", sFlag);

       /* //获取Rest昨天费用总和及本月费用总和
        double restYesterdayFee =  appCommonService.getCallRecordSumFee(account, "RestRecord_"+account.getFeeid() ,"yesterday");
        double restCurrentMonthFee =  appCommonService.getCallRecordSumFee(account, "RestRecord_"+account.getFeeid() ,"month");
        //获取Mask昨天费用总和及本月费用总和
        double maskYesterdayFee =  appCommonService.getCallRecordSumFee(account, "MaskRecord_"+account.getFeeid() ,"yesterday");
        double maskCurrentMonthFee =  appCommonService.getCallRecordSumFee(account, "MaskRecord_"+account.getFeeid() ,"month");
        //获取Sip昨天费用总和及本月费用总和
        double sipYesterdayFee =  appCommonService.getCallRecordSumFee(account, "SipRecord_"+account.getFeeid() ,"yesterday");
        double sipCurrentMonthFee =  appCommonService.getCallRecordSumFee(account, "SipRecord_"+account.getFeeid() ,"month");

        logger.info("==========================================费用信息===========================================");
        logger.info("rest昨日消费总和："+restYesterdayFee);
        logger.info("mask昨日消费总和："+maskYesterdayFee);
        logger.info("sip昨日消费总和："+sipYesterdayFee);
        logger.info("rest当月消费总和："+restCurrentMonthFee);
        logger.info("mask当月消费总和："+maskCurrentMonthFee);
        logger.info("sip当月消费总和："+sipCurrentMonthFee);
        logger.info("=============================================================================================");

        //计算（rest、mask、sip）昨天费用总和及本月费用总和
        String yesterdayFee = String.format("%.2f",restYesterdayFee + maskYesterdayFee + sipYesterdayFee);
        String currentMonthFee = String.format("%.2f",restCurrentMonthFee + maskCurrentMonthFee + sipCurrentMonthFee);

        model.addAttribute("yesterdayFee", yesterdayFee);
        model.addAttribute("currentMonthFee", currentMonthFee);*/

        Map<String, Object> map = new HashMap<>();
        map.put("feeid",account.getFeeid());

        model.addAttribute("yesterdayFee", appCommonService.getYesterDaySpendSumFee(map));
        model.addAttribute("currentMonthFee", appCommonService.getMonthSpendSumFee(map));
        return USER_INDEX;
    }

    /**
     * 重置Token
     */
    @RequestMapping("/reset")
    public String resetToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("updateAccInfo start");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        account.setUpdateDate(new Date());
        account.setToken(ID.randomUUID());
        accountService.updateAccount(account);
        //发送系统消息
        User u = new User();
        u.setUid(account.getUid());
        Sender.sendMessage(u, TempCode.modify_token, null);

        logger.info("-------resetToken uid---"+uid);
        return this.redirect+appConfig.getAuthUrl()+"accMgr/index";
    }
    /**
     * 基础资料查看
     */
    @RequestMapping("/query")
    public String queryAccInfo(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        logger.info("queryAccInfo start");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        User user = accountService.findUserAndExternInfoForUid(uid);
        model.addAttribute("user", user);
        logger.info("queryAccInfo uid"+uid);
        return USER_VIEW;
    }

    @RequestMapping("/update")
    public String updateAccInfo(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        logger.info("updateAccInfo start");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        User user = accountService.findUserAndExternInfoForUid(uid);
        model.addAttribute("user", user);
        logger.info("updateAccInfo uid"+uid);
        return USER_UPDATE;
    }

    /**
     * 账户信息保存
     */
    @RequestMapping("/save")
    public String saveUserExterin(HttpServletRequest request, HttpServletResponse response, Model model, UserExternInfo userExternInfo) throws Exception {
        logger.info("saveUserExterin start");
        if(userExternInfo==null){
            userExternInfo = new UserExternInfo();
        }
        try{
            Account account =  (Account) request.getSession().getAttribute("userInfo");
            userExternInfo.setUid(account.getUid());
            accountService.saveUserExterninfo(userExternInfo);
            logger.info("saveUserExterin uid"+account.getUid());
        }catch (Exception e){
            logger.info("---saveUserExterin  error----",e);
            throw new Exception("修改用户信息失败");
        }
        return this.redirect+appConfig.getAuthUrl()+"accMgr/query";
    }


    /**
     * 修改Token
     */
    @RequestMapping("/upToken")
    @ResponseBody
    public Map<String,String> updateToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----updateToken start-------");
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        User dbuser = accountService.findUserAndExternInfoForUid(uid);
        String mobile = dbuser.getMobile();
        String valCode = request.getParameter("vcode");
        if(StringUtils.isEmpty(mobile)){
            logger.info("----updateToken mobile is null-------");
            map.put("code","99");
            return map;
        }
        if(StringUtils.isEmpty(valCode)){
            logger.info("----updateToken valCode is null-------");
            map.put("code","99");
            return map;
        }
        try{
            String rVcode = RedisDBUtils.get(MOBILE_KEY_HEAD+mobile);
            if(StringUtils.isEmpty(rVcode)){
                logger.info("----updateToken vcode is timeout-------");
                map.put("code","01");
                return map;
            }
            if(!valCode.equals(rVcode)){
                logger.info("----updateToken the vcode and rvcode is diff-------");
                map.put("code","02");
                return map;
            }
            RedisDBUtils.del(MOBILE_KEY_HEAD+mobile);
            map.put("token",dbuser.getToken());
            request.getSession().setAttribute("authFlag","00");
//            User user = new User();
//            user.setUid(account.getUid());
//            user.setToken(ID.randomUUID());
//            accountService.updateUserInfo(user);
        }catch (Exception e){
            logger.info("----updateToken error-------",e);
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        return map;
    }
    /**
     * 修改手机号
     */
    @RequestMapping("/upMoble")
    @ResponseBody
    public Map<String,String> updateMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----updateMobile start-------");
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String mobile = request.getParameter("mobile");
        String valCode = request.getParameter("vcode");
        if(StringUtils.isEmpty(mobile)){
            logger.info("----updateMobile mobile is null-------");
            map.put("code","99");
            return map;
        }
        if(StringUtils.isEmpty(valCode)){
            logger.info("----updateMobile valCode is null-------");
            map.put("code","99");
            return map;
        }
        try{
            String rVcode = RedisDBUtils.get(MOBILE_KEY_HEAD+mobile);
            if(StringUtils.isEmpty(rVcode)){
                logger.info("----updateMobile vcode is timeout-------");
                map.put("code","01");
                return map;
            }
            if(!valCode.equals(rVcode)){
                logger.info("----updateMobile the vcode and rvcode is diff-------");
                map.put("code","02");
                return map;
            }
            RedisDBUtils.del(MOBILE_KEY_HEAD+mobile);
            User user = new User();
            user.setUid(account.getUid());
            user.setMobile(mobile);
            accountService.updateUserInfo(user);
        }catch (Exception e){
            logger.info("----updateMobile error-------",e);
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        //发送系统消息
        User u = new User();
        u.setUid(account.getUid());
        Sender.sendMessage(u, TempCode.modify_mobile, null);
        return map;
    }

    /**
     * 发送邮箱确认邮件
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public Map<String,String> sendEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        logger.info("----sendEmail sendEmail is null-------");
        String email = request.getParameter("email");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
       if(StringUtils.isEmpty(email)){
           logger.info("----sendEmail is fail the mail is null-------");
           map.put("code","99");
           return map;
       }
        if(!accountService.checkEmailUnique(email)){
            logger.info("----sendEmail the email is register-------");
            map.put("code","01");
            return map;
        }
        try{
            //生成邮箱确认链接
            genEmailConfUrl(email,account.getUid());
        }catch (Exception e){
            logger.info("----sendEmail error-------",e);
            map.put("code","99");
            return  map;
        }
        map.put("code","00");
        User u = new User();
        u.setUid(account.getUid());
        Sender.sendMessage(u, TempCode.modify_email, null);
        return map;
    }



    /**
     * 生成邮箱确认发送URL
     */
    public void genEmailConfUrl(String email,String uid) throws  Exception{
        Map map = new HashMap<String,String>();
        map.put("uuid", ID.randomUUID());
        map.put("email",email);
        String vcode  = Tools.Base64Encode(new Gson().toJson(map),"utf-8");
        RedisDBUtils.set(Constants.REDIS_H_UPDATE_EMAIL+uid,vcode,MAIL_TIME_OUT);
        String url = GenerateLinkUtils.generateUpdateEmailLink(uid,vcode);
        logger.info("---sendEmail---url:"+url);
        MailUtils.sendUpdateEmail(url,email);
    }

    /**
     * 发送手机号验证码
     */
    @RequestMapping("/sendMobileCode")
    @ResponseBody
    public Map<String,String> sendMobileCode(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        boolean flag = true;
        Map map = new HashMap<String,String>();
        String mobile = request.getParameter("mobile");
        if(StringUtils.isEmpty(mobile)){
            map.put("status","99");
            logger.info("-----sendMobileCode----the mobile is null");
            return  map;
        }
        if(!accountService.checkMobileUnique(mobile)){
            map.put("status","02");
            return map;
        }
        try{
            String mobileKey = MOBILE_KEY_HEAD+mobile;
            if(RedisDBUtils.exists(mobileKey)){
                long second = RedisDBUtils.ttl(mobileKey);
                map.put("status","01");
                map.put("second",String.valueOf(second));
                return map;
            }
            String vcode = Tools.getRandomNum();

            Map smsMap = new HashMap<String,String>();
            smsMap.put("vcode",vcode);
            String content = SmsUtils.genSmsContent(Constants.SEND_SMS_MOBILECODE,smsMap);
            flag =  SmsUtils.sendSms(mobile,content);
            logger.info("-----sendMobileCode-the mobile:"+mobile);
            logger.info("-----sendMobileCode-the vode:"+vcode);

            if(flag){
                RedisDBUtils.set(mobileKey,vcode,MOBILE_TIME_OUT);
                map.put("status","00");
            }else{
                map.put("status","99");
                logger.info("-----sendMobileCode---- sendSms return false");
            }
        }catch (Exception e){
            map.put("status","99");
            logger.info("-----sendMobileCode error----",e);
            logger.error("-----sendMobileCode error----",e);
        }
        return map;
    }

    /**
     * 检查手机号是否当前用户手机号并发送验证码
     */
    @RequestMapping("/checkMobile")
    @ResponseBody
    public Map<String,String> checkMobileAndSendCode(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        Map map = new HashMap<String,String>();
        try{
            Account account =  (Account) request.getSession().getAttribute("userInfo");
            String uid = account.getUid();
            User user = accountService.findUserAndExternInfoForUid(uid);
            String mobile = user.getMobile();
            String mobileKey = MOBILE_KEY_HEAD+mobile;
            if(RedisDBUtils.exists(mobileKey)){
                long second = RedisDBUtils.ttl(mobileKey);
                map.put("status","01");
                map.put("second",String.valueOf(second));
                return map;
            }
            String vcode = Tools.getRandomNum();
            Map smsMap = new HashMap<String,String>();
            smsMap.put("vcode",vcode);
            String content = SmsUtils.genSmsContent(Constants.SEND_SMS_MOBILECODE,smsMap);
            boolean flag = true;
            flag =  SmsUtils.sendSms(mobile,content);
            logger.info("-----sendMobileCode-the mobile:"+mobile);
            logger.info("-----sendMobileCode-the vode:"+vcode);
            if(flag){
                RedisDBUtils.set(mobileKey,vcode,MOBILE_TIME_OUT);
                map.put("status","00");
            }else{
                map.put("status","99");
                logger.info("-----sendMobileCode---- sendSms return false");
            }
        }catch (Exception e){
            map.put("status","99");
            logger.info("-----sendMobileCode error----",e);
            logger.error("-----sendMobileCode error----",e);
        }
        return map;
    }
    /**
     * 修改邮箱页面
     */
    @RequestMapping("/updateEmail")
    public String queryAccIndex1(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {

        return EMAIL_SHOW;
    }
    /**
     * 进入密码修改页面
     */
    @RequestMapping("/pwd")
    public String pwdView(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        logger.info("----pwdView--- start");
        return PWD_VIEW;
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/upPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> pwdUpdate(HttpServletRequest request,String oldpwd,String newpwd) throws Exception {
        Map map = new HashMap<String,String>();

        oldpwd = RSAUtils.decryptStringByJs(oldpwd);
        newpwd = RSAUtils.decryptStringByJs(newpwd);

        logger.info("----pwdUpdate--- start");
        //判断原始密码是否正确
        Account account = this.getCurrUser(request);
        String uid = account.getUid();
        try{
            Account dbAccount = (Account)accountService.getAccountByUid(uid);
            if (!DigestsUtils.checkpwd(dbAccount, oldpwd)){
                map.put("code","01");
                return map;
            }
            Account upAcc = new Account();
            upAcc.setUid(uid);
            upAcc.setPwd(newpwd);
            DigestsUtils.encryption(upAcc); // 密码加密
            accountService.updateAccount(upAcc);
            MailUtils.sendForUpdatepwd(account.getEmail());
        }catch (Exception e){
            logger.info("-----pwdUpdate----error",e);
            logger.error("-----pwdUpdate----error",e);
            map.put("code","04");
            return map;
        }
        map.put("code","00");
        //发送系统消息
        User u = new User();
        u.setUid(uid);
        Sender.sendMessage(u, TempCode.modify_password, null);

        return map;

    }



    /**
     * 修改手机号
     */
    @RequestMapping("/upSmsMoble")
    @ResponseBody
    public Map<String,String> updateSmsMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----updateSmsMobile start-------");
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String mobile = request.getParameter("mobile");
        String valCode = request.getParameter("vcode");
        if(StringUtils.isEmpty(mobile)){
            logger.info("----updateMobile mobile is null-------");
            map.put("code","99");
            return map;
        }
        if(StringUtils.isEmpty(valCode)){
            logger.info("----updateMobile valCode is null-------");
            map.put("code","99");
            return map;
        }
        try{
            String rVcode = RedisDBUtils.get(MOBILE_KEY_HEAD+mobile);
            if(StringUtils.isEmpty(rVcode)){
                logger.info("----updateMobile vcode is timeout-------");
                map.put("code","01");
                return map;
            }
            if(!valCode.equals(rVcode)){
                logger.info("----updateMobile the vcode and rvcode is diff-------");
                map.put("code","02");
                return map;
            }
            RedisDBUtils.del(MOBILE_KEY_HEAD+mobile);
            User user = new User();
            user.setUid(account.getUid());
            user.setSmsMobile(mobile);
            accountService.updateUserInfo(user);
        }catch (Exception e){
            logger.info("----updateMobile error-------",e);
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        //发送系统消息
        User u = new User();
        u.setUid(account.getUid());
        Sender.sendMessage(u, TempCode.modify_mobile, null);
        return map;
    }

    /**
     * 发送手机号验证码
     */
    @RequestMapping("/sendMobileCodeSms")
    @ResponseBody
    public Map<String,String> sendMobileCodeSms(HttpServletRequest request, HttpServletResponse response) throws  Exception{
        boolean flag = true;
        Map map = new HashMap<String,String>();
        String mobile = request.getParameter("mobile");
        if(StringUtils.isEmpty(mobile)){
            map.put("status","99");
            logger.info("-----sendMobileCode----the mobile is null");
            return  map;
        }
        try{
            String mobileKey = MOBILE_KEY_HEAD+mobile;
            if(RedisDBUtils.exists(mobileKey)){
                long second = RedisDBUtils.ttl(mobileKey);
                map.put("status","01");
                map.put("second",String.valueOf(second));
                return map;
            }
            String vcode = Tools.getRandomNum();

            Map smsMap = new HashMap<String,String>();
            smsMap.put("vcode",vcode);
            String content = SmsUtils.genSmsContent(Constants.SEND_SMS_MOBILECODE,smsMap);
            flag =  SmsUtils.sendSms(mobile,content);
            logger.info("-----sendMobileCode-the mobile:"+mobile);
            logger.info("-----sendMobileCode-the vode:"+vcode);

            if(flag){
                RedisDBUtils.set(mobileKey,vcode,MOBILE_TIME_OUT);
                map.put("status","00");
            }else{
                map.put("status","99");
                logger.info("-----sendMobileCode---- sendSms return false");
            }
        }catch (Exception e){
            map.put("status","99");
            logger.info("-----sendMobileCode error----",e);
            logger.error("-----sendMobileCode error----",e);
        }
        return map;
    }


    /**
     * 修改remindFee
     */
    @RequestMapping("/upremindFee")
    @ResponseBody
    public Map<String,String> updateremindFee(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String remindFee = request.getParameter("remindFee");
        if(StringUtils.isEmpty(remindFee)){
            map.put("code","99");
            return map;
        }
        try{
            User user = new User();
            user.setUid(account.getUid());
            BigDecimal bd=new BigDecimal(remindFee);
            user.setRemindFee(bd);
            user.setLimitFlag("01");
            user.setSmsStatus("00");
            accountService.updateUserInfo(user);
        }catch (Exception e){
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        return map;
    }


    @RequestMapping("/upFlag")
    @ResponseBody
    public Map<String,String> updateFlag(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        try{
            User user = new User();
            user.setUid(account.getUid());
            user.setLimitFlag("00");
            accountService.updateUserInfo(user);
        }catch (Exception e){
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        return map;
    }



    @RequestMapping("/openFlag")
    @ResponseBody
    public Map<String,String> updateopenFlag(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap<String,String>();
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        try{
            User user = new User();
            user.setUid(account.getUid());
            user.setLimitFlag("01");
            accountService.updateUserInfo(user);
        }catch (Exception e){
            map.put("code","99");
            return map;
        }
        map.put("code","00");
        return map;
    }
}
