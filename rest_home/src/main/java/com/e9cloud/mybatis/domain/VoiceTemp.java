package com.e9cloud.mybatis.domain;

import java.util.Date;

public class VoiceTemp {
    private Integer id;

    private String appid;

    private String name;

    private String type;

    private String auditStatus;

    private String auditCommon;

    private Date auditTime;

    private String vUrl;

    private String vSize;

    private String tContent;

    private Date atime;

    private String status;

    private String vPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getAuditCommon() {
        return auditCommon;
    }

    public void setAuditCommon(String auditCommon) {
        this.auditCommon = auditCommon == null ? null : auditCommon.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getvUrl() {
        return vUrl;
    }

    public void setvUrl(String vUrl) {
        this.vUrl = vUrl == null ? null : vUrl.trim();
    }

    public String getvSize() {
        return vSize;
    }

    public void setvSize(String vSize) {
        this.vSize = vSize == null ? null : vSize.trim();
    }

    public String gettContent() {
        return tContent;
    }

    public void settContent(String tContent) {
        this.tContent = tContent == null ? null : tContent.trim();
    }

    public Date getAtime() {
        return atime;
    }

    public void setAtime(Date atime) {
        this.atime = atime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getvPath() {
        return vPath;
    }

    public void setvPath(String vPath) {
        this.vPath = vPath == null ? null : vPath.trim();
    }
}