package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AxbRate {
    private String feeid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate1;
    private Boolean forever;
    private Integer cycle;
    private BigDecimal per6;
    private Integer per6Discount;
    private BigDecimal per60;
    private Integer per60Discount;
    private BigDecimal perx;
    private BigDecimal recording;
    private Integer recordingDiscount;

    private BigDecimal rent;

    private Integer rentDiscount;
    public Integer getRentDiscount() {
        return rentDiscount;
    }

    public void setRentDiscount(Integer rentDiscount) {
        this.rentDiscount = rentDiscount;
    }

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

        if(per60 != null) {
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

    public BigDecimal getRent() {
        if(rent != null) {
            return rent.setScale(4);
        }
        return null;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
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

}