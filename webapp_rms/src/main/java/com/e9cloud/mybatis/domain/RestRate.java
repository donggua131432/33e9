package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 回拨Rest费率信息
 *
 * @author Created by Dukai on 2016/2/19.
 */

public class RestRate implements Serializable{


    private static final long serialVersionUID = 1816538011484425935L;

    private String feeid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate1;

    private Boolean forever;
    private String feeMode;
    private BigDecimal resta;
    private Integer restaDiscount;
    private BigDecimal rest;
    private Integer restDiscount;
    private BigDecimal recording;
    private Integer recordingDiscount;

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

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode == null ? null : feeMode.trim();
    }


    public BigDecimal getResta() {
        if(resta != null) {
            return resta.setScale(4);
        }
        return null;
    }

    public void setResta(BigDecimal resta) {
        this.resta = resta;
    }

    public Integer getRestaDiscount() {
        return restaDiscount;
    }

    public void setRestaDiscount(Integer restaDiscount) {
        this.restaDiscount = restaDiscount;
    }

    public BigDecimal getRest() {
        if(rest != null) {
            return rest.setScale(4);
        }
        return null;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    public Integer getRestDiscount() {
        return restDiscount;
    }

    public void setRestDiscount(Integer restDiscount) {
        this.restDiscount = restDiscount;
    }

    public BigDecimal getRecording() {
        if(recording != null) {
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(addtime);
        }
        return null;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAddtime1() {
        if(addtime1 != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(addtime);
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

    public static void main(String[] args) {
        BigDecimal b = new BigDecimal(0.0100);
        BigDecimal c = b.setScale(4);
        System.out.println(c);
    }
}