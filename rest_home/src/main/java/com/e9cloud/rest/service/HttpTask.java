package com.e9cloud.rest.service;

/**
 * 回调的任务类
 * Created by wzj on 2017/5/17.
 */
public class HttpTask {

    // callId
    private String callSid;

    // 回调地址
    private String url;

    private String action;

    // 回调的消息
    private String msgBody;

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
