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

    private Long sipBusiness;

    private String sipUrl;

    private String sipOutnoPr;

    private String status;

    private Integer maxConcurrent;

    private Date createTime;

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
}