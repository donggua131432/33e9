package com.e9cloud.mybatis.domain;

import java.util.Date;

public class VoiceVerCustNum {

    // id
    private String id;

    // 应用appid
    private String appid;

    // 用户号码id   等于 （tb_voiceverify_num_pool.id）
    private String numId;

    // 状态（00:正常 02：已删除 ）
    private String status;

    private Date addtime;

    private Date updatetime;

    private Date deltime;

    //客户名称
    private String name;

    private String sid;
    //应用名称
    private String appName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId == null ? null : numId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {

        return name;
    }

    public String getSid() {
        return sid;
    }

    public String getAppName() {
        return appName;
    }

}