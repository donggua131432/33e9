package com.e9cloud.thirdparty.sms;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送工具类
 *
 * Created by wzj on 2016/1/18.
 */
public class SmsUtils implements BaseLogger {

    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param content 短信内容
     * @return
     */
    public static boolean sendSms(String mobile, String content){

        logger.info("---------{}----------", "sendSms start");

        String r = "";

        try {
            String status = SmsSenderFactory.getSender().send(mobile, content);
            r = parseSmsStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        logger.info("---------{}----------", "sendSms end");

        return Constants.RESULT_STATUS_OK.equals(r);
    }

    /**
     * 发送短信(定时发送)
     *
     * @param mobile 手机号
     * @param content 短信内容
     * @param presendTime 发送时间：
     * @return
     */
    public static boolean sendSms(String mobile, String content, String presendTime){

        logger.info("---------{}----------", "sendSms start");

        String status = SmsSenderFactory.getSender().send(mobile, content, presendTime);
        String r = parseSmsStatus(status);

        logger.info("---------{}----------", "sendSms end");

        return Constants.RESULT_STATUS_OK.equals(r);
    }

    /**
     * OK:325689 表示短信发送成功 发送成功的最后一条短信ID为325689；
     * ERROR:eUser 表示短信发送不成功，出错原因是因为用户名称有误；
     * ERROR:eDate 表示日期错误，出错原因是预发送时间格式不对；
     * ERROR:eIllegalPhone 表示发送号码错误；
     * ERROR:ePassword 表示密码错误；
     * ERROR:eStop 表示用户已经停用；
     * ERROR:eDenyDate 表示帐户过期；
     * ERROR:eBalance 表示余额不足；
     * ERROR:不明错误！表示不明错误，出错原因一般是SQL异常（内容过长或者机构ID填写英文字母等类似错误）；
     * ERROR:eFrequent 表示请求频繁；
     * ERROR:eContentLen 表示短信内容超长；
     * ERROR:nContent 表示短信内容为空；
     * ERROR:eContentWrong 表示短信模板拦截
     * ERROR:未知错误 表示未知错误。
     *
     * @param status
     * @return
     */
    private static String parseSmsStatus(String status){

        logger.info("the send status is {}", status);

        if (Tools.isNotNullStr(status)) {
            if (status.startsWith("OK")) {
                return Constants.RESULT_STATUS_OK;
            }
            if (status.startsWith("ERROR")) {
                return Constants.RESULT_STATUS_ERROR;
            }
        }
        return "";
    }

    /**
     * 发送短信(定时发送)
     *
     * @param templateContext 模版内容
     * @param param 短信动态参数
     * @return
     */
    public static String genSmsContent(String templateContext, Map<String,String> param){
        if (param != null) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                templateContext = templateContext.replace("{"+key+"}",value);
            }
        }
        return templateContext;
    }

    public static void main(String[] args) {
        Map smsMap = new HashMap<String,String>();
        smsMap.put("vcode","123456");
        smsMap.put("phone","40081234567");
        String content = genSmsContent(Constants.SEND_SMS_MOBILECODE,smsMap);
        System.out.println(content);
    }

}
