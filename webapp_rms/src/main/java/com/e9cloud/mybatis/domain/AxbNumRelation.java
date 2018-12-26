package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by dell on 2017/4/22.
 */
public class AxbNumRelation {

    private String id;

    //消息请求标识
    private String requestId;

    private String appid;

    //A路号码
    private String numA;

    //X号码
    private String numX;

    //B路号码
    private String numB;

    //区号
    private String areaCode;

    //绑定ID
    private String subid;

    //添加时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addtime;

    //到期时长，秒为单位
    private Integer secondLength;

    //过期时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outtime;

    //锁定时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date locktime;

    //锁定释放时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ridtime;

    public void setId(String id) {
        this.id = id;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setNumA(String numA) {
        this.numA = numA;
    }

    public void setNumX(String numX) {
        this.numX = numX;
    }

    public void setNumB(String numB) {
        this.numB = numB;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public void setSecondLength(Integer secondLength) {
        this.secondLength = secondLength;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }

    public void setRidtime(Date ridtime) {
        this.ridtime = ridtime;
    }

    public String getId() {

        return id;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getAppid() {
        return appid;
    }

    public String getNumA() {
        return numA;
    }

    public String getNumX() {
        return numX;
    }

    public String getNumB() {
        return numB;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getSubid() {
        return subid;
    }

    public Date getAddtime() {
        return addtime;
    }

    public Integer getSecondLength() {
        return secondLength;
    }

    public Date getOuttime() {
        return outtime;
    }

    public Date getLocktime() {
        return locktime;
    }

    public Date getRidtime() {
        return ridtime;
    }
}
