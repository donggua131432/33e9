package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// ecc 云总机 分钟统计
public class StatIvrMinuteRecord {

    //
    private Integer id;

    //
    private Date statmin;

    //
    private String rcdtype;

    //
    private String abline;

    //
    private String sid;

    //
    private String feeid;

    //
    private String appid;

    //
    private Integer callcnt;

    //
    private Integer succcnt;

    //
    private Integer answercnt;

    //
    private Integer thscsum;

    //
    private Integer jfscsum;

    //
    private Integer lyscsum;

    //
    private Integer jflyscsum;

    //
    private BigDecimal fee;

    //
    private BigDecimal recordingfee;

    //
    private Date ctime;

    ///////////////////////////////////// 以下是setter和getter方法 ///////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatmin() {
        return statmin;
    }

    public void setStatmin(Date statmin) {
        this.statmin = statmin;
    }

    public String getRcdtype() {
        return rcdtype;
    }

    public void setRcdtype(String rcdtype) {
        this.rcdtype = rcdtype == null ? null : rcdtype.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
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

    public Integer getLyscsum() {
        return lyscsum;
    }

    public void setLyscsum(Integer lyscsum) {
        this.lyscsum = lyscsum;
    }

    public Integer getJflyscsum() {
        return jflyscsum;
    }

    public void setJflyscsum(Integer jflyscsum) {
        this.jflyscsum = jflyscsum;
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}