package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

public class StatMaskDayRecord {
    private Integer id;

    private Date statday;

    private String appid;

    private String subid;

    private String feeid;

    private String abline;

    private Integer callcnt;

    private Integer succcnt;

    private Integer thscsum;

    private Integer jfscsum;

    private BigDecimal fee;

    private BigDecimal recordingfee;

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

    public BigDecimal getRecordingfee() {
        return recordingfee;
    }

    public void setRecordingfee(BigDecimal recordingfee) {
        this.recordingfee = recordingfee;
    }
}