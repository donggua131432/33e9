package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 对应表格tb_sp_apply_num
 */
public class SpApplyNum {

  // 主键 自增
  private Long id;

  // 应用id
  private String appid;

  // 申请记录的主键
  private String applyId;

  // sip号码的主键
  private String sipphoneId;

  // 固话的主键
  private String fixphoneId;

  // 外显号，默认和固话号码一致
  private String showNum;

  // 城市ccode
  private String cityid;

  // 城市区号
  private String areaCode;

  // 状态：00正常,01已删除
  private String status;

  // 创建时间
  private Date atime;

  // 00审核中   01:通过:  02:不通过
  private String auditStatus;

  // audit_common 审核备注
  private String auditCommon;

  // 长途权限：00 开启，01 关闭 (默认01)
  private String longDistanceFlag;

  // 号码禁用开关：00 可用，01禁用 （默认00）
  private String  callSwitchFlag;

  //
  private Date auditTime;

  private String sipphone;

  private String fixphone;

  /////////////////////////// 以下是 setter 和 getter 方法 ////////////////////////////////

  public String getSipphone() {
    return sipphone;
  }

  public String getFixphone() {
    return fixphone;
  }

  public void setSipphone(String sipphone) {
    this.sipphone = sipphone;
  }

  public void setFixphone(String fixphone) {
    this.fixphone = fixphone;
  }

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

  public String getApplyId() {
    return applyId;
  }

  public void setApplyId(String applyId) {
    this.applyId = applyId == null ? null : applyId.trim();
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

  public String getShowNum() {
    return showNum;
  }

  public void setShowNum(String showNum) {
    this.showNum = showNum == null ? null : showNum.trim();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getAtime() {
    return atime;
  }

  public void setAtime(Date atime) {
    this.atime = atime;
  }

  public String getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus == null ? null : auditStatus.trim();
  }

  public String getCityid() {
    return cityid;
  }

  public void setCityid(String cityid) {
    this.cityid = cityid;
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

  public String getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(String areaCode) {
    this.areaCode = areaCode;
  }

  public String getLongDistanceFlag() {
    return longDistanceFlag;
  }

  public void setLongDistanceFlag(String longDistanceFlag) {
    this.longDistanceFlag = longDistanceFlag;
  }

  public String getCallSwitchFlag() {
    return callSwitchFlag;
  }

  public void setCallSwitchFlag(String callSwitchFlag) {
    this.callSwitchFlag = callSwitchFlag;
  }
}