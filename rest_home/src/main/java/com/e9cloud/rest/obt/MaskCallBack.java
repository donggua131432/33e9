package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MaskCallBack
{
    private  String appId ;//应用ID
    private  String subId;//子账号ID
    private  String from;//主叫电话号码
    private  String to ;//被叫电话号码
    private String maskNumber;//隐私号码
    private  int maxCallTime ;//通话的最大时长,单位为秒，最大通话时长8小时
    private  int needRecord;//是否录音：0表示不录音；1表示录音；默认值0
    private  int countDownTime;//设置倒计时时间，单位秒，只能选择5秒、30秒或60秒，默认值为空
    private  String userData ;//用户自定义数据
    //CB需要 扩增字段
    private String callSid;
    private String accountSid;
    private String recordFlag;//录音标识 A:A路接听开始录音   B:B路接听开始录音
    private String codec; //语音编码

    //rest调度字段
    private String rn_a;
    //rest调度字段
    private String rn_b;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMaskNumber() {
        return maskNumber;
    }

    public void setMaskNumber(String maskNumber) {
        this.maskNumber = maskNumber;
    }

    public int getMaxCallTime() {
        return maxCallTime;
    }

    public void setMaxCallTime(int maxCallTime) {
        this.maxCallTime = maxCallTime;
    }

    public int getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(int needRecord) {
        this.needRecord = needRecord;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getRn_a() {
        return rn_a;
    }

    public void setRn_a(String rn_a) {
        this.rn_a = rn_a;
    }

    public String getRn_b() {
        return rn_b;
    }

    public void setRn_b(String rn_b) {
        this.rn_b = rn_b;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }
}
