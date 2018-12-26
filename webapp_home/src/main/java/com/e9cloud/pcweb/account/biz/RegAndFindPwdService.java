package com.e9cloud.pcweb.account.biz;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.Interest;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.redis.util.RedisDBUtils;
import com.e9cloud.thirdparty.msg.sms.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * 注册和忘记密码
 *
 * Created by Administrator on 2016/1/14.
 */
@Service
public class RegAndFindPwdService extends BaseLogger{

    private final static int timeout = 24*3600; // 24小时过期（邮箱）

    private final static String KEY_HEAD = Constants.REDIS_H_REG_EMAIL; // 邮箱注册

    private final static int timeout_code = 120; // 2分钟过期

    private final static int timeout_update_pwd = 3600; // 1小时过期（邮箱）

    private final static String CODE_HEAD = Constants.REDIS_H_REG_MOBILE; // 手机验证码

    @Autowired
    private AccountService accountService;

    // 校验邮箱并发送邮件
    public String sendMailForReg(String email){

        Account account = accountService.getAccountByEmail(email);

        if(account == null) { // 邮箱不存在
            // 临时uid
            String uid = Encodes.encodeHex(email.getBytes());

            // 校验码
            String vcode = ID.randomUUID();

            account = new Account();

            account.setUid(uid);
            account.setEmail(email);
            // account.setStatus(Constants.USER_STATUS_NO_ACT);
            // account.setCreateDate(new Date());

            // accountService.saveUserForReg(account);

            RedisDBUtils.set(KEY_HEAD + uid, vcode, timeout);

            try {
                MailUtils.sendAccountActivateEmail(account, vcode);
            } catch (MessagingException me) {
                me.printStackTrace();
                return Constants.RESULT_STATUS_ERROR;
            }
            return Constants.RESULT_STATUS_OK;
        } else { // TODO: 2016/2/4 mysql中 不存在状态为0 数据
            if (Constants.USER_STATUS_NO_ACT.equals(account.getStatus())){ // 用户未激活时
                // 校验码
                String vcode = ID.randomUUID();
                // RedisDBUtils.del(KEY_HEAD + account.getUid());
                RedisDBUtils.set(KEY_HEAD + account.getUid(), vcode, timeout);
                try {
                    MailUtils.sendAccountActivateEmail(account, vcode);
                } catch (MessagingException me) {
                    me.printStackTrace();
                    return Constants.RESULT_STATUS_ERROR;
                }
                return Constants.RESULT_STATUS_OK;
            }

            return Constants.RESULT_STATUS_HAS_REG;
        }

        // return Constants.RESULT_STATUS_ERROR;

    }

    /**
     * 补充完成信息
     * @param account 账户信息
     */
    public String completeRegExecute(Account account, String[] dicIds) throws Exception{
        // User c = accountService.getUserByUid(account.getUid());

        if(accountService.checkEmailUnique(account.getEmail())){

            // rsa 对密码解密
            account.setPwd(RSAUtils.decryptStringByJs(account.getPwd()));

            account.setUid(ID.randomUUID()); // 用户主账号
            account.setSid(ID.randomUUID()); // 账户主账号
            account.setFeeid(ID.randomUUID()); // 计费主账号
            account.setToken(ID.randomUUID()); // 为用户生成token
            account.setStatus(Constants.USER_STATUS_OK);
            account.setCreateDate(new Date());

            //初始化sys_type表
            account.setBusTypes(Constants.BUS_TYPE_REST);
            account.setBusTypeStatus(Constants.BUS_TYPE_STATUS_OK);

            DigestsUtils.encryption(account); // 密码加密

            accountService.saveUserForReg(account);

            Interest interest = new Interest();
            interest.setUid(account.getUid());
            if(dicIds!=null&&dicIds.length>0) {
                for (String dicid : dicIds) {
                    interest.setDicId(dicid);
                    accountService.saveUserInterest(interest);
                }
            }

            return Constants.RESULT_STATUS_OK;

        }

        return Constants.RESULT_STATUS_ERROR;
    }

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     * @return
     */
    public boolean sendSmsForReg(String mobile) {

        String vcode = Tools.getRandomNum(); // 得到六位随机码

        RedisDBUtils.set(CODE_HEAD + mobile, vcode, timeout_code);
        boolean hasSend = SmsUtils.sendSms(mobile, "您的注册验证码是：" + vcode + "，请在2分钟内完成输入~");

        return hasSend;
    }

    // 校验邮箱并发送邮件
    public String sendMailForPwd(String email){

        Account account = accountService.getAccountByEmail(email);
        // 校验码
        String vcode = ID.randomUUID();
        account.setVcode(vcode);
        if (account != null) { // 邮箱存在
            logger.info("account.uid:" + account.getUid() + " jym:" + account.getVcode());
            RedisDBUtils.set(Constants.REDIS_H_PWD_EMAIL + account.getUid(), account.getVcode(), timeout_update_pwd);
            try {
                MailUtils.sendResetPasswordEmail(account, account.getVcode());
            } catch (MessagingException me) {
                me.printStackTrace();
                return Constants.RESULT_STATUS_ERROR;
            }
            return Constants.RESULT_STATUS_OK;

        } else {
            return Constants.RESULT_STATUS_ERROR;
        }
    }

}
