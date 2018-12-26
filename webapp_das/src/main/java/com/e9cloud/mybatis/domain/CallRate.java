package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 95呼入呼出费率信息
 *
 * @author Created by Dukai on 2016/2/19.
 */

public class CallRate implements Serializable{

    private static final long serialVersionUID = 2924441175447541285L;

    private String feeid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate1;

    private Boolean forever;
    private String feeMode;
    private BigDecimal callin;
    private Integer callinDiscount;
    private BigDecimal callout;
    private Integer calloutDiscount;
    private BigDecimal relayRent;
    private Integer relayCnt;
    private BigDecimal minConsume;
    private Byte discount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime1;

    private UserAdmin userAdmin;
    private AuthenCompany authenCompany;

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

    public BigDecimal getCallin() {
        if(callin != null){
            return callin.setScale(3);
        }
        return null;
    }

    public void setCallin(BigDecimal callin) {
        this.callin = callin;
    }

    public Integer getCallinDiscount() {
        return callinDiscount;
    }

    public void setCallinDiscount(Integer callinDiscount) {
        this.callinDiscount = callinDiscount;
    }

    public BigDecimal getCallout() {
        if(callout != null){
            return callout.setScale(3);
        }
        return null;
    }

    public void setCallout(BigDecimal callout) {
        this.callout = callout;
    }

    public Integer getCalloutDiscount() {
        return calloutDiscount;
    }

    public void setCalloutDiscount(Integer calloutDiscount) {
        this.calloutDiscount = calloutDiscount;
    }

    public BigDecimal getRelayRent() {
        return relayRent;
    }

    public void setRelayRent(BigDecimal relayRent) {
        this.relayRent = relayRent;
    }

    public Integer getRelayCnt() {
        return relayCnt;
    }

    public void setRelayCnt(Integer relayCnt) {
        this.relayCnt = relayCnt;
    }

    public BigDecimal getMinConsume() {
        return minConsume;
    }

    public void setMinConsume(BigDecimal minConsume) {
        this.minConsume = minConsume;
    }

    public Byte getDiscount() {
        return discount;
    }

    public void setDiscount(Byte discount) {
        this.discount = discount;
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
}