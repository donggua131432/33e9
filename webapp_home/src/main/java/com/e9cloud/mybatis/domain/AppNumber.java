package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class AppNumber implements Serializable{

    private static final long serialVersionUID = 7234658224391478263L;

    private Long id;

    private String appid;

    private String number;

    private String numberType;

    private String numberStatus;

    private String remark;

    private Date addtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reviewtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType == null ? null : numberType.trim();
    }

    public String getNumberStatus() {
        return numberStatus;
    }

    public void setNumberStatus(String numberStatus) {
        this.numberStatus = numberStatus == null ? null : numberStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getReviewtime() {
        if(reviewtime != null){
            String strDate = new SimpleDateFormat("yyyy.MM.dd").format(reviewtime);
            return strDate;
        }
        return null;
    }

    public void setReviewtime(Date reviewtime) {
        this.reviewtime = reviewtime;
    }
}