package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/9/12.
 */
public class WhiteNumberBody
{
    private  String appId ;//应用ID
    private  String action ;//操作 add—新增  del—删除
    private String number;//白名单号码
    private String type;//类型 L—永久有效   S—固定时长
    private Integer validTime=86400;//有效时间，秒为单位；例如3600秒-有效时间为一个小时，不传默认24小时有效  S类型可用

    // 扩增字段
    private String accountSid;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
}
