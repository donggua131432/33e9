package com.e9cloud.mongodb.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/3.
 */
public class StafRecord implements Serializable {
    private Date day;//日期
    private String feeId;//计费ID
    private int callType;//呼入1呼出2
    private int operator;//电信1联通2移动3
    private String callCenter;//呼叫中心
    private String dispatchArea;//调度区域
    private String area;//区域
    private int jb;//际标
    private int count;//呼叫数
    private int thsc;//通话时长（秒）
    private int jfsc;//计费时长(分)
    private int connSucc;//接通1未接通0
    private int connFalse;//接通0未接通1
    private Double fee;//花费

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getThsc() {
        return thsc;
    }

    public void setThsc(int thsc) {
        this.thsc = thsc;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
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

    public int getJb() {
        return jb;
    }

    public void setJb(int jb) {
        this.jb = jb;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getJfsc() {
        return jfsc;
    }

    public void setJfsc(int jfsc) {
        this.jfsc = jfsc;
    }

    public int getConnSucc() {
        return connSucc;
    }

    public void setConnSucc(int connSucc) {
        this.connSucc = connSucc;
    }

    public int getConnFalse() {
        return connFalse;
    }

    public void setConnFalse(int connFalse) {
        this.connFalse = connFalse;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
