package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class AppNumberRest implements Serializable{

    private static final long serialVersionUID = 7999841971498176127L;

    private Long id;

    private Long numberId;

    private String appid;

    private String number;

    private Date addtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberId() {
        return numberId;
    }

    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    public String getAppId() {
        return appid;
    }

    public void setAppId(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}