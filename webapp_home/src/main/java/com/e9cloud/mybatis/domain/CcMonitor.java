package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CcMonitor implements Serializable{

    private static final long serialVersionUID = 4643279937198537002L;

    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date stime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date stime1;

    private String sid;

    private String appid;

    private String subid;

    private Integer callinCnt;

    private Integer callinSucc;

    private Float callInRate;

    private Integer calloutCnt;

    private Integer calloutSucc;

    private Float callOutRate;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStime() {
        if(stime != null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(stime);
        }
        return null;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public String getStime1() {
        if(stime1 != null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(stime1);
        }
        return null;
    }

    public void setStime1(Date stime1) {
        this.stime1 = stime1;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
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

    public Integer getCallinCnt() {
        return callinCnt;
    }

    public void setCallinCnt(Integer callinCnt) {
        this.callinCnt = callinCnt;
    }

    public Integer getCallinSucc() {
        return callinSucc;
    }

    public void setCallinSucc(Integer callinSucc) {
        this.callinSucc = callinSucc;
    }

    public Integer getCalloutCnt() {
        return calloutCnt;
    }

    public void setCalloutCnt(Integer calloutCnt) {
        this.calloutCnt = calloutCnt;
    }

    public Integer getCalloutSucc() {
        return calloutSucc;
    }

    public void setCalloutSucc(Integer calloutSucc) {
        this.calloutSucc = calloutSucc;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }


    public Float getCallOutRate() {
        return callOutRate;
    }

    public void setCallOutRate(Float callOutRate) {
        this.callOutRate = callOutRate;
    }

    public Float getCallInRate() {
        return callInRate;
    }

    public void setCallInRate(Float callInRate) {
        this.callInRate = callInRate;
    }
}