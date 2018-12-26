package com.e9cloud.mybatis.domain;

public class AppInfoExtra {
    private Integer id;
    private String appid;
    private Integer recordingType;
    private String feeUrl;
    private String voiceCode;
    // 增值业务：bit0： SIP phone间回拨 ，bit1：SIP phone间直拨
    private Integer valueAdded;



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