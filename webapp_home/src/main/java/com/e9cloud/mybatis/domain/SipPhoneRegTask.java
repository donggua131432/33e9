package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 删除外显号记录
 */
public class SipPhoneRegTask{

  private static final long serialVersionUID = -5871590343444875970L;

  private Long id;

  private String appid;

  private Long showNumId;

  private String sipphoneId;

  private String fixphoneId;

  private Date taskTime;

  private String taskType;

  private String taskStatus;

  private Date addTime;

  private Date updateTime;

  public Long getId() {
    return id;
  }

  public String getAppid() {
    return appid;
  }

  public Long getShowNumId() {
    return showNumId;
  }

  public String getSipphoneId() {
    return sipphoneId;
  }

  public String getFixphoneId() {
    return fixphoneId;
  }

  public Date getTaskTime() {
    return taskTime;
  }

  public String getTaskStatus() {
    return taskStatus;
  }

  public Date getAddTime() {
    return addTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public void setShowNumId(Long showNumId) {
    this.showNumId = showNumId;
  }

  public void setSipphoneId(String sipphoneId) {
    this.sipphoneId = sipphoneId;
  }

  public void setFixphoneId(String fixphoneId) {
    this.fixphoneId = fixphoneId;
  }

  public void setTaskTime(Date taskTime) {
    this.taskTime = taskTime;
  }

  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

  public void setAddTime(Date addTime) {
    this.addTime = addTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public String getTaskType() {

    return taskType;
  }
}
