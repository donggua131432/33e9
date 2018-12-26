package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/9/27.
 */
public class VoiceRecord implements Serializable {

    private static final long serialVersionUID = -4405881544198790765L;

    private String requestId; //请求ID
    private String appid; //应用ID
    private String feeid; //费率ID
    private String name; //模板名称
    private String templateId; //模板ID
    private String sid; //Account ID
    private String bj; //被叫号码
    private String display; //外显号码
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date callTime; //呼叫时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date callTime1; //呼叫时间
    private String dtmf; //dtmf
    private Double fee; //通话费用
    private Integer thsc; //通话时长
    private Boolean connectStatus; //接通状态
    private String connectStatus1;
    private Integer trys; //回执次数。0,1,2
    private Integer connectSucc;
    private String outgoing;//落地线路
    private String reason;//线路返回真实错误码

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date qssj; //起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date jssj; //结束时间

    public String getConnectStatus1() {
        return connectStatus1;
    }

    public void setConnectStatus1(String connectStatus1) {
        if ("false".equalsIgnoreCase(connectStatus1)) {
            setConnectStatus(false);
        }
        if ("true".equalsIgnoreCase(connectStatus1)) {
            setConnectStatus(true);
        }
        this.connectStatus1 = connectStatus1;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


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


    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj;
    }


    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }



    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Date getCallTime1() {
        return callTime1;
    }

    public void setCallTime1(Date callTime1) {
        this.callTime1 = callTime1;
    }

    public String getDtmf() {
        return dtmf;
    }

    public void setDtmf(String dtmf) {
        this.dtmf = dtmf;
    }


    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }


    public Integer getThsc() {
        return thsc;
    }

    public void setThsc(Integer thsc) {
        this.thsc = thsc;
    }


    public Boolean getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(Boolean connectStatus) {
        this.connectStatus = connectStatus;
    }

    public Integer getTrys() {
        return trys;
    }

    public void setTrys(Integer trys) {
        this.trys = trys;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getConnectSucc() {
        return connectSucc;
    }

    public void setConnectSucc(Integer connectSucc) {
        this.connectSucc = connectSucc;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    public String getOutgoing() {

        return outgoing;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {

        return reason;
    }
}
