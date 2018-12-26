package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SipDayRecord implements Serializable {

    private static final long serialVersionUID = 4127271959570580017L;

    private Integer id;
    //统计日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statday;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statday1;
    //费率ID
    private String feeid;
    //应用ID
    private String appid;
    //子账号ID
    private String subid;
    //AB路
    private String abline;
    //呼叫次数
    private Integer callcnt;
    //呼叫成功次数
    private Integer succcnt;
    //呼叫应答次数
    private Integer answercnt;
    //总通话时长
    private Integer thscsum;
    //总计费时长
    private Integer jfscsum;
    //总话费
    private BigDecimal fee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatday() {
        return statday;
    }

    public void setStatday(Date statday) {
        this.statday = statday;
    }

    public Date getStatday1() {
        return statday1;
    }

    public void setStatday1(Date statday1) {
        this.statday1 = statday1;
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
        this.subid = subid == null ? null : subid.trim();
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public Integer getCallcnt() {
        return callcnt;
    }

    public void setCallcnt(Integer callcnt) {
        this.callcnt = callcnt;
    }

    public Integer getSucccnt() {
        return succcnt;
    }

    public void setSucccnt(Integer succcnt) {
        this.succcnt = succcnt;
    }

    public Integer getAnswercnt() {
        return answercnt;
    }

    public void setAnswercnt(Integer answercnt) {
        this.answercnt = answercnt;
    }

    public Integer getThscsum() {
        return thscsum;
    }

    public void setThscsum(Integer thscsum) {
        this.thscsum = thscsum;
    }

    public Integer getJfscsum() {
        return jfscsum;
    }

    public void setJfscsum(Integer jfscsum) {
        this.jfscsum = jfscsum;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}