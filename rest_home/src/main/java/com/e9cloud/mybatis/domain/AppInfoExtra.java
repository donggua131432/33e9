package com.e9cloud.mybatis.domain;

public class AppInfoExtra {

    private Integer id; //主键
    private String appid; //appid 和tb_app_info中的appid 一一对应
    private Integer recordingType; //录音开关：bit0:回拨、  bit1:直拨、  bit2:回呼
    private String feeUrl; //计费回调地址
    private String voiceCode; //语音编码：如G729等
    private Integer valueAdded; //增值业务：bit0: SIP phone间回拨、 bit1:SIP phone间直拨

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public Integer getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(Integer recordingType) {
        this.recordingType = recordingType;
    }

    public String getFeeUrl() {
        return feeUrl;
    }

    public void setFeeUrl(String feeUrl) {
        this.feeUrl = feeUrl == null ? null : feeUrl.trim();
    }

    public String getVoiceCode() {
        return voiceCode;
    }

    public void setVoiceCode(String voiceCode) {
        this.voiceCode = voiceCode == null ? null : voiceCode.trim();
    }

    public Integer getValueAdded() {
        return valueAdded;
    }

    public void setValueAdded(Integer valueAdded) {
        this.valueAdded = valueAdded;
    }
}