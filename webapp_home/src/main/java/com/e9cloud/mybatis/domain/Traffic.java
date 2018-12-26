package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 话务信息
 */
public class Traffic implements Serializable{

    /*计费ID*/
    private String feeId;

    /*呼叫类型*/
    private String callType;

    /*主叫*/
    private String zj;

    /*被叫*/
    private String bj;

    /*起始时间*/
    private String qssj;

    /*通话时长*/
    private int thsc;

    /*际标*/
    private int jb;

    /*应用类型*/
    private char appType;

    /*呼叫中心*/
    private String callCenter;

    /*调度区域*/
    private String dispatchArea;

    /*区域*/
    private String area;

    /*运营商*/
    private String operator;

    /* 接通状态*/
    private String connectStatus;

    /*原因*/
    private String reason;

    /*话费*/
    private BigDecimal fee;

    /*计费状态1已计费0未计费*/
    private int status;

    /** ----------------- 以下是setter和getter方法 ------------------ **/

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
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

    public String getQssj() {
        return qssj;
    }

    public void setQssj(String qssj) {
        this.qssj = qssj;
    }

    public int getThsc() {
        return thsc;
    }

    public void setThsc(int thsc) {
        this.thsc = thsc;
    }

    public int getJb() {
        return jb;
    }

    public void setJb(int jb) {
        this.jb = jb;
    }

    public char getAppType() {
        return appType;
    }

    public void setAppType(char appType) {
        this.appType = appType;
    }

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter;
    }

    public String getDispatchArea() {
        return dispatchArea;
    }

    public void setDispatchArea(String dispatchArea) {
        this.dispatchArea = dispatchArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(String connectStatus) {
        this.connectStatus = connectStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
