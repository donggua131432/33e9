package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaskRate {
    private String feeid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate1;

    private Boolean forever;

    private String feeMode;

    private BigDecimal maska;

    private Integer maskaDiscount;

    private BigDecimal maskb;

    private Integer maskbDiscount;

    private BigDecimal recording;

    private Integer recordingDiscount;

    private BigDecimal callback;

    private Integer callbackDiscount;

    private BigDecimal recordingCallback;

    private Integer recordingCallbackDiscount;

    private BigDecimal rent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime1;

    private UserAdmin userAdmin;
    private AuthenCompany authenCompany;

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

    public Boolean getForever() {
        return forever;
    }

    public void setForever(Boolean forever) {
        this.forever = forever;
    }

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode == null ? null : feeMode.trim();
    }

    public BigDecimal getMaska() {
        if(maska != null){
            return maska.setScale(4);
        }
        return null;
    }

    public void setMaska(BigDecimal maska) {
        this.maska = maska;
    }

    public Integer getMaskaDiscount() {
        return maskaDiscount;
    }

    public void setMaskaDiscount(Integer maskaDiscount) {
        this.maskaDiscount = maskaDiscount;
    }

    public BigDecimal getMaskb() {
        if(maskb != null){
            return maskb.setScale(4);
        }
        return null;
    }

    public void setMaskb(BigDecimal maskb) {
        this.maskb = maskb;
    }

    public Integer getMaskbDiscount() {
        return maskbDiscount;
    }

    public void setMaskbDiscount(Integer maskbDiscount) {
        this.maskbDiscount = maskbDiscount;
    }

    public BigDecimal getRecording() {
        if(recording != null){
            return recording.setScale(4);
        }
        return null;
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

    public BigDecimal getCallback() {
        if(callback != null){
            return callback.setScale(4);
        }
        return null;
    }

    public void setCallback(BigDecimal callback) {
        this.callback = callback;
    }

    public Integer getCallbackDiscount() {
        return callbackDiscount;
    }

    public void setCallbackDiscount(Integer callbackDiscount) {
        this.callbackDiscount = callbackDiscount;
    }

    public BigDecimal getRecordingCallback() {
        if(recordingCallback != null){
            return recordingCallback.setScale(4);
        }
        return null;
    }

    public void setRecordingCallback(BigDecimal recordingCallback) {
        this.recordingCallback = recordingCallback;
    }

    public Integer getRecordingCallbackDiscount() {
        return recordingCallbackDiscount;
    }

    public void setRecordingCallbackDiscount(Integer recordingCallbackDiscount) {
        this.recordingCallbackDiscount = recordingCallbackDiscount;
    }

    public BigDecimal getRent() {
        if(rent != null){
            return rent.setScale(4);
        }
        return null;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public String getAddtime() {
        if(addtime != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(addtime);
        }
        return null;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(Date endDate1) {
        this.endDate1 = endDate1;
    }

    public String getAddtime1() {
        if(addtime1 != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(addtime1);
        }
        return null;
    }

    public void setAddtime1(Date addtime1) {
        this.addtime1 = addtime1;
    }

    public UserAdmin getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(UserAdmin userAdmin) {
        this.userAdmin = userAdmin;
    }

    public AuthenCompany getAuthenCompany() {
        return authenCompany;
    }

    public void setAuthenCompany(AuthenCompany authenCompany) {
        this.authenCompany = authenCompany;
    }
}