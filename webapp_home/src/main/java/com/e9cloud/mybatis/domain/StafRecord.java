package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class StafRecord implements Serializable{

    private Long id;

    private String area;

    private String callCenter;

    private String dispatchArea;

    private String callType;

    private String operator;

    private Date startTime;

    private Date endTime;

    private String conn;

    private String fee;

    private int thsc;//通话时长（秒）

    private int jfsc;//计费时长(分)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCallCenter() {
        return callCenter;
    }

    public void setCallCenter(String callCenter) {
        this.callCenter = callCenter == null ? null : callCenter.trim();
    }

    public String getDispatchArea() {
        return dispatchArea;
    }

    public void setDispatchArea(String dispatchArea) {
        this.dispatchArea = dispatchArea == null ? null : dispatchArea.trim();
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType == null ? null : callType.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getConn() {
        return conn;
    }

    public void setConn(String conn) {
        this.conn = conn == null ? null : conn.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public int getThsc() {
        return thsc;
    }

    public void setThsc(int thsc) {
        this.thsc = thsc;
    }

    public int getJfsc() {
        return jfsc;
    }

    public void setJfsc(int jfsc) {
        this.jfsc = jfsc;
    }
}