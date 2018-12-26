package com.e9cloud.mybatis.domain;

import com.e9cloud.core.util.Tools;

/**
 * 应用信息的扩展信息 tb_app_info 扩展信息
 */
public class AppInfoExtra {

    // 主键自增
    private Integer id;

    // 应用id
    private String appid;

    // 录音开关：bit0:回拨 bit1:、直拨  bit2:、回呼
    private Integer recordingType;

    // 计费回调地址
    private String feeUrl;

    // 语音编码：如G729等
    private String voiceCode;

    // 增值业务：bit0： SIP phone间回拨 ，bit1：SIP phone间直拨
    private Integer valueAdded;

    /////////////////////////// 以下 是 setter 和 getter 方法 /////////////////////////////////

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