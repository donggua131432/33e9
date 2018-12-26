package com.e9cloud.rest.obt;

/**
 * @描述: 语音验证码
 * @作者: DuKai
 * @创建时间: 2017/5/3 12:05
 * @版本: 1.0
 */
public class VoiceValidate {

    private String appId;
    private String callSid;
    private String voiceRecId;
    private String voicefix;
    private String verifyCode;
    private String to;
    private String displayNum;
    private String rn_a;
    private String codec;
    private int maxTimes;
    private int language;
    private int playTimes;
    private int playInterval;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getVoiceRecId() {
        return voiceRecId;
    }

    public void setVoiceRecId(String voiceRecId) {
        this.voiceRecId = voiceRecId;
    }

    public String getVoicefix() {
        return voicefix;
    }

    public void setVoicefix(String voicefix) {
        this.voicefix = voicefix;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(String displayNum) {
        this.displayNum = displayNum;
    }

    public String getRn_a() {
        return rn_a;
    }

    public void setRn_a(String rn_a) {
        this.rn_a = rn_a;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getMaxTimes() {
        return maxTimes;
    }

    public void setMaxTimes(int maxTimes) {
        this.maxTimes = maxTimes;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(int playTimes) {
        this.playTimes = playTimes;
    }

    public int getPlayInterval() {
        return playInterval;
    }

    public void setPlayInterval(int playInterval) {
        this.playInterval = playInterval;
    }
}
