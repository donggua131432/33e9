package com.e9cloud.thirdparty;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.pcweb.msg.biz.MsgService;
import com.e9cloud.pcweb.msg.util.TempCode;

import java.util.Map;

/**
 * 消息发送器
 * Created by Administrator on 2016/7/7.
 */
public class Sender {

    public static final int SMS = 0; // 短信

    public static final int EMAIL = 1; // 邮箱

    public static final int MESSAGE = 2; // 消息、通知

    // 发送短信
    public static void sendSms(){

    }

    // 发送邮件
    public static void sendEMail(){

    }

    // 发送消息
    public static void sendMessage(User receive, TempCode code, Map<String, Object> params){
        MsgService msgService = SpringContextHolder.getBean(MsgService.class);
        msgService.sendMsg(receive, code, params);
    }

}
