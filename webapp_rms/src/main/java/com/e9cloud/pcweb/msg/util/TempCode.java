package com.e9cloud.pcweb.msg.util;

import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Tools;

import java.util.Map;

/**
 * 通知公告的模板
 * Created by Administrator on 2016/7/6.
 */
public enum TempCode {

    SEND_SMS_IN_MONEY("账户充值", Constants.SEND_SMS_IN_MONEY), // 账户充值
    SEND_SMS_OUT_MONEY("账户扣款", Constants.SEND_SMS_OUT_MONEY), // 账户扣款

    SEND_SMS_ANTH_SUCCESS("认证成功", Constants.SEND_SMS_ANTH_SUCCESS),  // 认证成功
    SEND_SMS_ANTH_ERROR("认证失败", Constants.SEND_SMS_ANTH_ERROR),  // 认证失败

    SEND_SMS_RING_SUCCESS("铃声审核成功", Constants.SEND_SMS_RING_SUCCESS),  // 铃声审核成功
    SEND_SMS_RING_ERROR("铃声审核失败", Constants.SEND_SMS_RING_ERROR),  // 铃声审核失败

    SEND_SMS_NUM_SUCCESS("号码审核成功", Constants.SEND_SMS_NUM_SUCCESS),  // 号码审核成功
    SEND_SMS_NUM_ERROR("号码审核失败", Constants.SEND_SMS_NUM_ERROR);  // 号码审核失败

    TempCode(String title, String temp) {
        this.title = title;
        this.temp = temp;
    }

    // 标题
    private String title;

    // 模板内容
    private String temp;

    public String getTitle() {
        return title;
    }

    public String getContent(Map params) {
        return Tools.process(temp, params);
    }
}
