package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestStafDayRecord implements Serializable{

    private static final long serialVersionUID = -8418404200586677360L;

    private Integer id;

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
    private Integer callCnt;
    //呼叫成功次数
    private Integer succCnt;
    //总通话时长
    private Integer thscSum;
    //总计费时长
    private Integer jfscSum;
    //总话费
    private BigDecimal fee;
    //总录音费
    private BigDecimal recordingFee;



    //费用总和
    private BigDecimal totalFee;
    //通话费用总和
    private BigDecimal sumFee;
    //录音费用总和
    private BigDecimal sumRecordingfee;

    private BigDecimal dayFee;
    //应用名称
    private String appName;
    //应用类
    private AppInfo appInfo;

    public String getTotalFee() {
        //以万元为单位计算
        if(totalFee != null) {
            return String.format("%.2f", (totalFee.doubleValue() / 10000));
        }
        return null;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getSumFee() {
        //以万元为单位计算
        if(sumFee != null){
            return String.format("%.2f", (sumFee.doubleValue() / 10000));
        }
        return null;
    }

    public void setSumFee(BigDecimal sumFee) {
        this.sumFee = sumFee;
    }

    public String getSumRecordingfee() {
        //以万元为单位计算
        if(sumRecordingfee != null) {
            return String.format("%.2f", (sumRecordingfee.doubleValue() / 10000));
        }
        return null;
    }

    public void setSumRecordingfee(BigDecimal sumRecordingfee) {
        this.sumRecordingfee = sumRecordingfee;
    }

    public String getDayFee() {
        if(dayFee != null) {
            return String.format("%.2f", (dayFee.doubleValue()));
        }
        return null;
    }

    public void setDayFee(BigDecimal dayFee) {
        this.dayFee = dayFee;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

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
        this.feeid = feeid == null ? null : feeid.trim();
    }


    public String getStafDay() {
        if(stafDay != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(stafDay);
        }
        return null;
    }

    public void setStafDay(Date stafDay) {
        this.stafDay = stafDay;
    }

    public String getStafDay1() {
        if(stafDay1 != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(stafDay1);
        }
        return null;
    }

    public void setStafDay1(Date stafDay1) {
        this.stafDay1 = stafDay1;
    }

    public String getAbLine() {
        return abLine;
    }

    public void setAbLine(String abLine) {
        this.abLine = abLine;
    }

    public Integer getCallCnt() {
        return callCnt;
    }

    public void setCallCnt(Integer callCnt) {
        this.callCnt = callCnt;
    }

    public Integer getSuccCnt() {
        return succCnt;
    }

    public void setSuccCnt(Integer succCnt) {
        this.succCnt = succCnt;
    }

    public Integer getThscSum() {
        return thscSum;
    }

    public void setThscSum(Integer thscSum) {
        this.thscSum = thscSum;
    }

    public Integer getJfscSum() {
        return jfscSum;
    }

    public void setJfscSum(Integer jfscSum) {
        this.jfscSum = jfscSum;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(BigDecimal recordingFee) {
        this.recordingFee = recordingFee;
    }
}