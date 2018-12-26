package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VoiceRate {
    private String feeid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

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

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setFeeMode(String feeMode) {
        this.cycle = cycle;
    }

    public BigDecimal getPer6() {
        if(per6 != null){
            return per6.setScale(4);
        }
        return null;
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
        if(per60 != null){
            return per60.setScale(4);
        }
        return null;
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
        if(perx != null){
            return perx.setScale(4);
        }
        return null;
    }

    public void setPerx(BigDecimal perx) {
        this.perx = perx;
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
}