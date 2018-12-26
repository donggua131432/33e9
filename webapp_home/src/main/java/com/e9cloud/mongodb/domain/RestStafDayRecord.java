package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dukai on 2016/3/2.
 */
public class RestStafDayRecord implements Serializable{

    private static final long serialVersionUID = -4069405271175754878L;

    //统计日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stafDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date stafDay1;
    //应用ID
    private String appid;
    //费率ID
    private String feeid;
    //子账号ID
    private String subid;
    //AB路
    private String abLine;
    //呼叫次数
    private int callCnt;
    //呼叫成功次数
    private int succCnt;
    //总通话时长
    private int thscSum;
    //总计费时长
    private int jfscSum;
    //总话费
    private double fee;
    //总录音费
    private double recordingFee;

    public Date getStafDay() {
        return stafDay;
    }

    public void setStafDay(Date stafDay) {
        this.stafDay = stafDay;
    }

    public Date getStafDay1() {
        return stafDay1;
    }

    public void setStafDay1(Date stafDay1) {
        this.stafDay1 = stafDay1;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public String getAbLine() {
        return abLine;
    }

    public void setAbLine(String abLine) {
        this.abLine = abLine;
    }

    public int getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(int callCnt) {
        this.callCnt = callCnt;
    }

    public int getSuccCnt() {
        return succCnt;
    }

    public void setSuccCnt(int succCnt) {
        this.succCnt = succCnt;
    }

    public int getThscSum() {
        return thscSum;
    }

    public void setThscSum(int thscSum) {
        this.thscSum = thscSum;
    }

    public int getJfscSum() {
        return jfscSum;
    }

    public void setJfscSum(int jfscSum) {
        this.jfscSum = jfscSum;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }
}
