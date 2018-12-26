package com.e9cloud.rest.obt;

import java.util.Date;

/**
 * @描述: 虚拟小号请求信息实体
 * @作者: DuKai
 * @创建时间: 2017/4/19 9:27
 * @版本: 1.0
 */
public class AxbNumber{

    private String accountSid; //用户ID
    private String appId; //应用ID
    private String telA; //A路号码
    private String telB; //B路号码
    private String telX; // 隐私号码
    private String areacode; //区号
    private Integer expiration; //X号码绑定时长
    private String callrecording; //录音控制

    private String userData; //用户自定义数据

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTelA() {
        return telA;
    }

    public void setTelA(String telA) {
        this.telA = telA;
    }

    public String getTelB() {
        return telB;
    }

    public void setTelB(String telB) {
        this.telB = telB;
    }

    public String getTelX() {
        return telX;
    }

    public void setTelX(String telX) {
        this.telX = telX;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public Integer getExpiration() {
        return expiration;
    }

    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getCallrecording() {
        return callrecording;
    }

    public void setCallrecording(String callrecording) {
        this.callrecording = callrecording;
    }
}
