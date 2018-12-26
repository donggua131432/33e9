package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/9/26.
 */
public class VoiceNotify
{
    private String appId ;//应用ID
    private String requestId;//语音通知请求ID
    private String to;//被叫号码

    //扩增字段
    private String accountSid;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
