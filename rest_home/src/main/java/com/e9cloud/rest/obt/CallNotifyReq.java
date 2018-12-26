package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/7/7.
 */
public class CallNotifyReq
{
    private  String callId ;//REST请求生成通话的唯一标示
    private  int type;//业务类型 1：专线语音，2：专号通

    //业务需要 扩增字段
    private String accountSid;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
}
