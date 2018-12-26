package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hzd on 2017/3/1.
 */
public class IvrRecord implements Serializable{

    private static final long serialVersionUID = 9097246183310032343L;

    private String rcdType; //通话类型
    private String appid; //应用ID
    private String feeid; //费率ID
    private String sid; //Account ID
    private String zj;//主叫号码
    private String bj;//被叫号码
    private String display; //显示号码
    private String callSid; //通话记录CALLSID(AB路一致)
    private Double fee; //通话费用
    private Double recordingFee; //录音费用
    private String outgoing;//落地线路
    private String reason;//线路返回真实错误码
    private String rcdLine;//录音标志位
    private String url; //录音文件存放地址

    public void setRcdLine(String rcdLine) {
        this.rcdLine = rcdLine;
    }

    public String getRcdLine() {

        return rcdLine;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qssj; //起始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj; //结束时间

    private Integer thsc; //通话时长
    private Integer jfsc; //计费时长
    private Integer connectSucc; //接通成功状态


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCallSid() {
        return callSid;
    }

    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(Double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public Integer getThsc() {
        return thsc;
    }

    public void setThsc(Integer thsc) {
        this.thsc = thsc;
    }

    public Integer getJfsc() {
        return jfsc;
    }

    public void setJfsc(Integer jfsc) {
        this.jfsc = jfsc;
    }

    public String getRcdType() {
        return rcdType;
    }

    public void setRcdType(String rcdType) {
        this.rcdType = rcdType;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public Integer getConnectSucc() {
        return connectSucc;
    }

    public void setConnectSucc(Integer connectSucc) {
        this.connectSucc = connectSucc;
    }
}
