package com.e9cloud.rest.obt;

import java.util.List;

/**
 * Created by Administrator on 2016/3/9.
 */
public class CancelCallBack
{
    private  String appId ;//应用ID
    private String callId;//回拨通话ID

    private  String statusCode ;//请求状态码，取值000000（成功）
    private  String dateCreated;//请求时间

    //扩增字段
    private String accountSid;

    //将对象字段转换成XML字符串
    public String toXML(String rootElement) {
        StringBuffer str=new StringBuffer();
        str.append( "<?xml version='1.0'?>");
        str.append( "<" +rootElement+ ">");
        str.append( "<statusCode>"+statusCode+"</statusCode>" +
                "<dateCreated>"+dateCreated+"</dateCreated>" +
                "<callId>"+callId+"</callId>");
        str.append( "</" +rootElement+ ">");
        return str.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
}
