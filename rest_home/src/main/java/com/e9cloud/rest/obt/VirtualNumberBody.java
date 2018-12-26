package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/8/29.
 */
public class VirtualNumberBody
{
    private  String appId ;//应用ID
    private  String numberType ;//虚拟号类型 S—短效  L-长效
    private String caller;//主叫
    private String called;//被叫
    private String vnId;//虚拟号id
    private String virtualNumber;//虚拟号
    private Integer validTime=86400;//有效时间（秒） 不传默认24小时有效
    private Integer needRecord=0;//是否录音（0表示不录音；1表示录音；不传默认值0）

    // 扩增字段
    private String accountSid;

    // 将对象字段转换成Json字符串
    public String toJson() {
        return "\"caller\":\"" + caller + "\"," + "\"called\":\"" + called + "\"," + "\"vnId\":\""
                + vnId + "\"," + "\"virtualNumber\":\"" + virtualNumber + "\"," + "\"validTime\":" + validTime + ","
                + "\"needRecord\":" + needRecord;
    }

    // 将对象字段转换成XML字符串
    public String toXML() {
        return "<caller>" + caller + "</caller>" + "<called>" + called + "</called>" + "<vnId>"
                + vnId + "</vnId>" + "<virtualNumber>" + virtualNumber + "</virtualNumber>" + "<validTime>" + validTime
                + "</validTime>" + "<needRecord>" + needRecord + "</needRecord>";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getVnId() {
        return vnId;
    }

    public void setVnId(String vnId) {
        this.vnId = vnId;
    }

    public String getVirtualNumber() {
        return virtualNumber;
    }

    public void setVirtualNumber(String virtualNumber) {
        this.virtualNumber = virtualNumber;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Integer getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(Integer needRecord) {
        this.needRecord = needRecord;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
}
