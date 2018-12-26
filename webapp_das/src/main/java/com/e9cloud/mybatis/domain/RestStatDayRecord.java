package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestStatDayRecord implements Serializable{

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

    //客户名称
    private String name;

    private String sid;

    //账户余额
    private BigDecimal accountFee;

    public String getTotalFee() {
        //以万元为单位计算
        if(totalFee != null) {
            return String.format("%.2f", (totalFee.doubleValue()));
        }
        return null;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public BigDecimal getAccountFee() {
        return accountFee;
    }

    public void setAccountFee(BigDecimal accountFee) {
        this.accountFee = accountFee;
    }
}