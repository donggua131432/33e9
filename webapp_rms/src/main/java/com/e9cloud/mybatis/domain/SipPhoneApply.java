package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * 对应表格 tb_sp_apply
 * 申请记录entity 2016/10/26
 */
public class SipPhoneApply {

  // 主键 uuid
  private String id;

  // 应用id
  private String appid;

  // 城市区号ID
  private String cityid;

  // 申请的sip号码数量
  private Integer amount;

  // SIP/固话号码比 整数大于0
  private Integer rate;

  // 添加时间、申请时间
  private Date atime;

  // 00审核中   01:通过:  02:不通过
  private String auditStatus;

  // 审核时的备注信息
  private String auditCommon;

  // 审核时间
  private Date auditTime;

  // 操作人员
  private String operator;

  // 城市名称
  private String cname;

  // 省份名称
  private String pname;

  public Integer fixAmount() {
    if (amount > 0 && rate >0) {
      return amount%rate==0 ? amount/rate : (amount/rate+1);
    }
    return 0;
  }

  ///////////////////////////// 以下是 setter 和 getter 方法 /////////////////////////////

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

  public String getCityid() {
    return cityid;
  }

  public void setCityid(String cityid) {
    this.cityid = cityid == null ? null : cityid.trim();
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Integer getRate() {
    return rate;
  }

  public void setRate(Integer rate) {
    this.rate = rate;
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

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator == null ? null : operator.trim();
  }

  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getPname() {
    return pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }
}
