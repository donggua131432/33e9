package com.e9cloud.rest.obt;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class SipPhone {

    private String appId;//应用ID
    private String subId;//子账号ID
    private String from;//主叫电话号码
    private String to;//被叫电话号码
    private List<String> fromSerNum;//主叫侧显示号码列表
    private List<String> toSerNum;//被叫侧显示号码列表
    private int maxCallTime;//通话的最大时长
    private int needRecord;//是否录音
    private int countDownTime;//设置倒计时时间
    private String userData;//用户自定义数据
    private String callSid;//REST端的通话标识
    private String codec;//指定语音编码，G729，PCMA,PCMU
    private int sipphoneFlag;//按位计算，1为A路sipphone用户，2为B路sipphone用户，3为A和B都是sipphone用户
    private String accountSid;//用户ID
    private String rn_a; //主叫号码的选路
    private String rn_b; //被叫号码的选路
    private String recordFlag;//录音标识 A:A路接听开始录音   B:B路接听开始录音

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

    public List<String> getFromSerNum() {
        return fromSerNum;
    }

    public void setFromSerNum(List<String> fromSerNum) {
        this.fromSerNum = fromSerNum;
    }

    public List<String> getToSerNum() {
        return toSerNum;
    }

    public void setToSerNum(List<String> toSerNum) {
        this.toSerNum = toSerNum;
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

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getSipphoneFlag() {
        return sipphoneFlag;
    }

    public void setSipphoneFlag(int sipphoneFlag) {
        this.sipphoneFlag = sipphoneFlag;
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
}
