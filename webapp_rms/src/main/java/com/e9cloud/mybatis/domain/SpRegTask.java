package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 云话机 注册 任务表
 */
public class SpRegTask {

    // id 自增
    private Long id;

    // appid
    private String appid;

    // 外显号id
    private Long shownumId;

    // sip 号码id
    private String sipphoneId;

    // 固话id
    private String fixphoneId;

    // 任务成功执行的时间
    private Date taskTime;

    // 任务类型: 注册 ADD, 解绑 DEL
    private String taskType;

    // 任务状态：00 未执行，01 执行中， 02 已执行
    private String taskStatus;

    // 生成时间
    private Date addtime;

    // 更新时间
    private Date updatetime;

    // sip 号码
    private String sipphone;

    // 外网ip
    private String outerIp;

    // 内网ip
    private String innerIp;

    // 密码
    private String pwd;

    //////////////////////////////////////以下是 setter 和 getter 方法//////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public Long getShownumId() {
        return shownumId;
    }

    public void setShownumId(Long shownumId) {
        this.shownumId = shownumId;
    }

    public String getSipphoneId() {
        return sipphoneId;
    }

    public void setSipphoneId(String sipphoneId) {
        this.sipphoneId = sipphoneId == null ? null : sipphoneId.trim();
    }

    public String getFixphoneId() {
        return fixphoneId;
    }

    public void setFixphoneId(String fixphoneId) {
        this.fixphoneId = fixphoneId == null ? null : fixphoneId.trim();
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType == null ? null : taskType.trim();
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
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

    public String getSipphone() {
        return sipphone;
    }

    public void setSipphone(String sipphone) {
        this.sipphone = sipphone;
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}