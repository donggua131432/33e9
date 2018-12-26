package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * sip 中继信息-子账号
 */
public class SipRelayInfo {

    private Integer id;

    private String appid;

    private String subid;

    // 中继编号
    private String relayNum;

    // 子账号名称
    private String subName;

    // 选号开关 00:关闭 01:开启
    private String numFlag;

    // #bit0:电信  bit1:移动 bit2:联通 bit3:其他
    private Integer calledType;

    // #被叫号码限制 bit0:手机;bit1:固话;bit2:400号码;bit3:955号码; bit4:其他
    private Integer calledLimit;


    // #中继最大并发数
    private Integer maxConcurrent;

    private String status;

    private Date createTime;

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

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid == null ? null : subid.trim();
    }

    public String getRelayNum() {
        return relayNum;
    }

    public void setRelayNum(String relayNum) {
        this.relayNum = relayNum == null ? null : relayNum.trim();
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName == null ? null : subName.trim();
    }

    public String getNumFlag() {
        return numFlag;
    }

    public void setNumFlag(String numFlag) {
        this.numFlag = numFlag == null ? null : numFlag.trim();
    }

    public Integer getCalledType() {
        return calledType;
    }

    public void setCalledType(Integer calledType) {
        this.calledType = calledType;
    }

    public Integer getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(Integer maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCalledLimit() {
        return calledLimit;
    }

    public void setCalledLimit(Integer calledLimit) {
        this.calledLimit = calledLimit;
    }
}