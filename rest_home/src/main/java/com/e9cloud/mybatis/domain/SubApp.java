package com.e9cloud.mybatis.domain;

import java.util.Date;

public class SubApp {
    private String subid;//子账户ID
    private String appid;//appid
    private String subName;//子帐号名称
    private String subPwd;//子帐号密码
    private String status;//状态 默认00:正常 02：冻结 03：禁用
    private Date createDate;//创建时间

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubPwd() {
        return subPwd;
    }

    public void setSubPwd(String subPwd) {
        this.subPwd = subPwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}