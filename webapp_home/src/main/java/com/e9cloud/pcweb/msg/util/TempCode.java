package com.e9cloud.pcweb.msg.util;

import com.e9cloud.core.util.Tools;

import java.util.Map;

/**
 * 通知公告的模板
 * Created by Administrator on 2016/7/6.
 */
public enum TempCode {

    modify_token("修改token","亲爱的用户，您token修改已成功，为了您的账户安全，建议不要频繁更换token，谢谢"),
    modify_email("修改邮箱", "亲爱的用户，您的邮箱已修改成功，谢谢"),
    modify_mobile("修改手机", "亲爱的用户，您的手机号已修改成功，谢谢"),
    modify_password("修改密码", "亲爱的用户，您的密码已修改成功，登录时请输入新密码，谢谢");

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
