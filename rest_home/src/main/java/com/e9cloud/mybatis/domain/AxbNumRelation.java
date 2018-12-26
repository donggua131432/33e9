package com.e9cloud.mybatis.domain;

import java.util.Date;

public class AxbNumRelation {
    private String id;

    private String requestId;

    private String appid;

    private String numA;

    private String numX;

    private String numB;

    private String areaCode;

    private String subid;

    private Date addTime;

    private Integer secondLength;

    private Date outTime;

    private Date lockTime;

    private Date ridTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getNumA() {
        return numA;
    }

    public void setNumA(String numA) {
        this.numA = numA == null ? null : numA.trim();
    }

    public String getNumX() {
        return numX;
    }

    public void setNumX(String numX) {
        this.numX = numX == null ? null : numX.trim();
    }

    public String getNumB() {
        return numB;
    }

    public void setNumB(String numB) {
        this.numB = numB == null ? null : numB.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getSecondLength() {
        return secondLength;
    }

    public void setSecondLength(Integer secondLength) {
        this.secondLength = secondLength;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getRidTime() {
        return ridTime;
    }

    public void setRidTime(Date ridTime) {
        this.ridTime = ridTime;
    }
}