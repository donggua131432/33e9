package com.e9cloud.rest.obt;

import com.e9cloud.util.Utils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class VoiceReq {

    private String id;//ID
    private String appId;//应用ID
    private String requestId;//语音通知请求ID
    private String voiceRecId;//语音通知模板ID
    private String accountSid;//用户ID
    private Integer dtmfFlag;//反馈接收开关
    private Integer orderFlag;//预约下发开关
    private String orderTime;//预约下发时间
    private String moduleParams;//模板参数
    private List<String> toList;//被叫号码列表
    private String displayNum;//外显号码
    private String voicePath;
    /* 下面是扩展字段*/
    private String toNum;//单个被叫号码
    private Integer syncFlag;//同步状态
    private String snCode;//设备码
    private Integer batchFlag;//是否批量请求  0:否 1：是
    private String callSid;//等同于reqeustId
    private String ipPort;
    private String to;
    private String voiceFile;
    private String codec; //语音编码

    private String rn_a;

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getVoiceRecId() {
        return voiceRecId;
    }

    public void setVoiceRecId(String voiceRecId) {
        this.voiceRecId = voiceRecId;
    }

    public List<String> getToList() {

        return toList;
    }

    public void setToList(List<String> toList) {
        this.toList = toList;
    }

    public String getToNum() {
        return toNum;
    }

    public void setToNum(String toNum) {
        this.toNum = toNum;
    }

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

    public Integer getDtmfFlag() {
        return dtmfFlag;
    }

    public void setDtmfFlag(Integer dtmfFlag) {
        this.dtmfFlag = dtmfFlag;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getModuleParams() {
        return moduleParams;
    }

    public void setModuleParams(String moduleParams) {
        this.moduleParams = moduleParams;
    }


    public String getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(String displayNum) {
        this.displayNum = displayNum;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(Integer batchFlag) {
        this.batchFlag = batchFlag;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getVoiceFile() {
        return voiceFile;
    }

    public void setVoiceFile(String voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getRn_a() {
        return rn_a;
    }

    public void setRn_a(String rn_a) {
        this.rn_a = rn_a;
    }
}
