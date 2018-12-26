package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 审核记录entity add by hzd 2016/11/1
 */
public class SpNumAudit {
  private Long id;
  private Long showNumId;
  private String showNum;
  private Date atime;
  private String auditStatus;
  private String auditCommon;
  private Date auditTime;

  public void setId(Long id) {
    this.id = id;
  }

  public void setShowNumId(Long showNumId) {
    this.showNumId = showNumId;
  }

  public void setShowNum(String showNum) {
    this.showNum = showNum;
  }

  public void setAtime(Date atime) {
    this.atime = atime;
  }

  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus;
  }

  public void setAuditCommon(String auditCommon) {
    this.auditCommon = auditCommon;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  public Long getId() {

    return id;
  }

  public Long getShowNumId() {
    return showNumId;
  }

  public String getShowNum() {
    return showNum;
  }

  public Date getAtime() {
    return atime;
  }

  public String getAuditStatus() {
    return auditStatus;
  }

  public String getAuditCommon() {
    return auditCommon;
  }

  public Date getAuditTime() {
    return auditTime;
  }
}
