package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IvrRate {
    private String feeid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate1;

    private Boolean forever;

    private BigDecimal callinSip;

    private Short callinSipDiscount;

    private BigDecimal callinNonsip;

    private Short callinNonsipDiscount;

    private BigDecimal callinDirect;

    private Short callinDirectDiscount;

    private BigDecimal callinRecording;

    private Short callinRecordingDiscount;

    private BigDecimal calloutLocal;

    private Short calloutLocalDiscount;

    private BigDecimal calloutNonlocal;

    private Short calloutNonlocalDiscount;

    private BigDecimal calloutRecording;

    private Short calloutRecordingDiscount;

    private BigDecimal ivrRent;

    private BigDecimal sipnumRent;

    private Integer sipnumRentDiscount;

    private BigDecimal sipnumMinCost;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime1;

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public String getStartDate() {
        if(startDate != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(startDate);
        }
        return null;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        if(endDate != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(endDate);
        }
        return null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(Date endDate1) {
        this.endDate1 = endDate1;
    }

    public Boolean getForever() {
        return forever;
    }

    public void setForever(Boolean forever) {
        this.forever = forever;
    }

    public BigDecimal getCallinSip() {
        return callinSip;
    }

    public void setCallinSip(BigDecimal callinSip) {
        this.callinSip = callinSip;
    }

    public Short getCallinSipDiscount() {
        return callinSipDiscount;
    }

    public void setCallinSipDiscount(Short callinSipDiscount) {
        this.callinSipDiscount = callinSipDiscount;
    }

    public BigDecimal getCallinNonsip() {
        return callinNonsip;
    }

    public void setCallinNonsip(BigDecimal callinNonsip) {
        this.callinNonsip = callinNonsip;
    }

    public Short getCallinNonsipDiscount() {
        return callinNonsipDiscount;
    }

    public void setCallinNonsipDiscount(Short callinNonsipDiscount) {
        this.callinNonsipDiscount = callinNonsipDiscount;
    }

    public BigDecimal getCallinDirect() {
        return callinDirect;
    }

    public void setCallinDirect(BigDecimal callinDirect) {
        this.callinDirect = callinDirect;
    }

    public Short getCallinDirectDiscount() {
        return callinDirectDiscount;
    }

    public void setCallinDirectDiscount(Short callinDirectDiscount) {
        this.callinDirectDiscount = callinDirectDiscount;
    }

    public BigDecimal getCallinRecording() {
        return callinRecording;
    }

    public void setCallinRecording(BigDecimal callinRecording) {
        this.callinRecording = callinRecording;
    }

    public Short getCallinRecordingDiscount() {
        return callinRecordingDiscount;
    }

    public void setCallinRecordingDiscount(Short callinRecordingDiscount) {
        this.callinRecordingDiscount = callinRecordingDiscount;
    }

    public BigDecimal getCalloutLocal() {
        return calloutLocal;
    }

    public void setCalloutLocal(BigDecimal calloutLocal) {
        this.calloutLocal = calloutLocal;
    }

    public Short getCalloutLocalDiscount() {
        return calloutLocalDiscount;
    }

    public void setCalloutLocalDiscount(Short calloutLocalDiscount) {
        this.calloutLocalDiscount = calloutLocalDiscount;
    }

    public BigDecimal getCalloutNonlocal() {
        return calloutNonlocal;
    }

    public void setCalloutNonlocal(BigDecimal calloutNonlocal) {
        this.calloutNonlocal = calloutNonlocal;
    }

    public Short getCalloutNonlocalDiscount() {
        return calloutNonlocalDiscount;
    }

    public void setCalloutNonlocalDiscount(Short calloutNonlocalDiscount) {
        this.calloutNonlocalDiscount = calloutNonlocalDiscount;
    }

    public BigDecimal getCalloutRecording() {
        return calloutRecording;
    }

    public void setCalloutRecording(BigDecimal calloutRecording) {
        this.calloutRecording = calloutRecording;
    }

    public Short getCalloutRecordingDiscount() {
        return calloutRecordingDiscount;
    }

    public void setCalloutRecordingDiscount(Short calloutRecordingDiscount) {
        this.calloutRecordingDiscount = calloutRecordingDiscount;
    }

    public BigDecimal getIvrRent() {
        return ivrRent;
    }

    public void setIvrRent(BigDecimal ivrRent) {
        this.ivrRent = ivrRent;
    }

    public BigDecimal getSipnumRent() {
        return sipnumRent;
    }

    public void setSipnumRent(BigDecimal sipnumRent) {
        this.sipnumRent = sipnumRent;
    }

    public Integer getSipnumRentDiscount() {
        return sipnumRentDiscount;
    }

    public void setSipnumRentDiscount(Integer sipnumRentDiscount) {
        this.sipnumRentDiscount = sipnumRentDiscount;
    }

    public BigDecimal getSipnumMinCost() {
        return sipnumMinCost;
    }

    public void setSipnumMinCost(BigDecimal sipnumMinCost) {
        this.sipnumMinCost = sipnumMinCost;
    }

    public String getCtime() {
        if(ctime != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(ctime);
        }
        return null;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getCtime1() {
        if(ctime1 != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(ctime1);
        }
        return null;
    }

    public void setCtime1(Date ctime1) {
        this.ctime1 = ctime1;
    }

}