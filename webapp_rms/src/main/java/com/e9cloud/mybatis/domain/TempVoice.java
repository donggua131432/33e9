package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 语音通知模板
 */
public class TempVoice {

    // id 自增 6位及六位以上数字
    private Integer id;

    // 应用id
    private String appid;

    //
    private String status;

    // 模板的名称
    private String name;

    // 模板的类型
    private String type;

    // 审核状态
    private String auditStatus;

    // 审核备注
    private String auditCommon;

    // 审核时间
    private Date auditTime;

    // 语音模板的位置
    private String vUrl;

    // 语音模板的size
    private String vSize;

    // 文本模板的内容
    private String tContent;

    // 添加时间
    private Date atime;

    // 审核通过后的路径
    private String vPath;

    /////////////////// 以下是 setter 和 getter 方法 ///////////////////////

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getvPath() {
        return vPath;
    }

    public void setvPath(String vPath) {
        this.vPath = vPath;
    }
}