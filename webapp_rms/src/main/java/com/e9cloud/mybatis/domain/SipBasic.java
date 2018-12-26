package com.e9cloud.mybatis.domain;

import java.util.Date;

public class SipBasic {
    private Integer id;

    private String relayNum;

    private String relayName;

    private String relayType;

    private String ipport1;

    private String ipport2;

    private String ipport3;

    private String ipport4;

    private String areacode;

    private Long sipBusiness;

    private String sipUrl;

    private String sipOutnoPr;

    private String status;

    private Integer maxConcurrent;

    private Date createTime;

    /** 冻结时间 */
    private Date freezeTime;

    private DicData dicData;

    private String limitStatus;//中继占用状态 00:未占用 01:已占用

    private String isForce;//系统默认强显中继(0:否, 1:是)

    private String isChecked;//是否选中（0:否, 1:是）

    /** 用途类型：00 客户，01 供应商，02 平台内部 */
    private String useType;

    /** 业务类型:01:智能云调度(955xx);03：sip, 05 ：云话机 */
    private String busType;

    ////////////////////////// 以下是setter和getter方法 /////////////////////////

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public DicData getDicData() {
        return dicData;
    }

    public void setDicData(DicData dicData) {
        this.dicData = dicData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(String relayNum) {
        this.relayNum = relayNum == null ? null : relayNum.trim();
    }

    public String getRelayName() {
        return relayName;
    }

    public void setRelayName(String relayName) {
        this.relayName = relayName == null ? null : relayName.trim();
    }

    public String getRelayType() {
        return relayType;
    }

    public void setRelayType(String relayType) {
        this.relayType = relayType == null ? null : relayType.trim();
    }

    public String getIpport1() {
        return ipport1;
    }

    public void setIpport1(String ipport1) {
        this.ipport1 = ipport1 == null ? null : ipport1.trim();
    }

    public String getIpport2() {
        return ipport2;
    }

    public void setIpport2(String ipport2) {
        this.ipport2 = ipport2 == null ? null : ipport2.trim();
    }

    public String getIpport3() {
        return ipport3;
    }

    public void setIpport3(String ipport3) {
        this.ipport3 = ipport3 == null ? null : ipport3.trim();
    }

    public String getIpport4() {
        return ipport4;
    }

    public void setIpport4(String ipport4) {
        this.ipport4 = ipport4 == null ? null : ipport4.trim();
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public Long getSipBusiness() {
        return sipBusiness;
    }

    public void setSipBusiness(Long sipBusiness) {
        this.sipBusiness = sipBusiness;
    }

    public String getSipUrl() {
        return sipUrl;
    }

    public void setSipUrl(String sipUrl) {
        this.sipUrl = sipUrl == null ? null : sipUrl.trim();
    }

    public String getSipOutnoPr() {
        return sipOutnoPr;
    }

    public void setSipOutnoPr(String sipOutnoPr) {
        this.sipOutnoPr = sipOutnoPr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Date freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(String limitStatus) {
        this.limitStatus = limitStatus;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType == null ? null : useType.trim();
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }
}