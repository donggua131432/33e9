package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class MaskNumPool implements Serializable{

    private static final long serialVersionUID = -2257936141473913973L;

    private String id;

    private String uid;
    //用户名称
    private String name;

    private String sid;
    //应用名称
    private String appName;

    private String appid;

    private Date addtime;


    public MaskNumPool(){

    }

    public MaskNumPool(String  appid){
        this.appid = appid == null ? null : appid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}