package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SipRelayInfo {
    private Integer id;

    private String appid;

    private String subid;

    private String relayNum;

    private String subName;

    private String numFlag;

    private Integer calledType;

    private Integer maxConcurrent;

    private String status;

    private Date createTime;

    private Date createTime1;

    private String feeid;

    private Date startDate;

    private Date endDate;

    private Boolean forever;

    private Integer cycle;

    private BigDecimal per6;

    private Integer per6Discount;

    private BigDecimal per60;

    private Integer per60Discount;

    private BigDecimal perx;

    private BigDecimal recording;

    private Integer recordingDiscount;


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
        this.subid = subid == null ? null : subid.trim();
    }

    public String getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(String relayNum) {
        this.relayNum = relayNum == null ? null : relayNum.trim();
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getNumFlag() {
        return numFlag;
    }

    public void setNumFlag(String numFlag) {
        this.numFlag = numFlag == null ? null : numFlag.trim();
    }

    public Integer getCalledType() {
        return calledType;
    }

    public void setCalledType(Integer calledType) {
        this.calledType = calledType;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime1() {
        return createTime1;
    }

    public void setCreateTime1(Date createTime1) {
        this.createTime1 = createTime1;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getForever() {
        return forever;
    }

    public void setForever(Boolean forever) {
        this.forever = forever;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getPer6() {
        return per6;
    }

    public void setPer6(BigDecimal per6) {
        this.per6 = per6;
    }

    public Integer getPer6Discount() {
        return per6Discount;
    }

    public void setPer6Discount(Integer per6Discount) {
        this.per6Discount = per6Discount;
    }

    public BigDecimal getPer60() {
        return per60;
    }

    public void setPer60(BigDecimal per60) {
        this.per60 = per60;
    }

    public Integer getPer60Discount() {
        return per60Discount;
    }

    public void setPer60Discount(Integer per60Discount) {
        this.per60Discount = per60Discount;
    }

    public BigDecimal getPerx() {
        return perx;
    }

    public void setPerx(BigDecimal perx) {
        this.perx = perx;
    }

    public BigDecimal getRecording() {
        return recording;
    }

    public void setRecording(BigDecimal recording) {
        this.recording = recording;
    }

    public Integer getRecordingDiscount() {
        return recordingDiscount;
    }

    public void setRecordingDiscount(Integer recordingDiscount) {
        this.recordingDiscount = recordingDiscount;
    }
}