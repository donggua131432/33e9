package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 中继费率配置
 */
public class SipRate implements Serializable {

    // 子账号id
    private String subid;

    // 应用id
    private String appid;

    // 计费主账号
    private String feeid;

    // 开始时间
    private Date startDate;

    // 结束时间
    private Date endDate;

    // 是否永久生效
    private Boolean forever;

    // 计费周期(计费单位)
    private Integer cycle;

    // 6秒计费的系统价
    private BigDecimal per6;

    // 6秒折扣率
    private Integer per6Discount;

    // 60秒（分钟）计费的系统价
    private BigDecimal per60;

    // 60秒（分钟）折扣率
    private Integer per60Discount;

    // 自定义秒计费的折后价
    private BigDecimal perx;

    // 录音价
    private BigDecimal recording;

    // 录音折扣
    private Integer recordingDiscount;

    // 记录添加时间
    private Date addtime;

    //////////////////////////  以下是setter和getter方法 //////////////////////////////

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid == null ? null : subid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getForever() {
        return forever;
    }

    public void setForever(Boolean forever) {
        this.forever = forever;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getPer6() {
        if(per6 != null){
            return per6.setScale(3);
        }
        return null;
    }

    public void setPer6(BigDecimal per6) {
        this.per6 = per6;
    }

    public Integer getPer6Discount() {
        return per6Discount;
    }

    public void setPer6Discount(Integer per6Discount) {
        this.per6Discount = per6Discount;
    }

    public BigDecimal getPer60() {
        if(per60 != null){
            return per60.setScale(3);
        }
        return null;
    }

    public void setPer60(BigDecimal per60) {
        this.per60 = per60;
    }

    public Integer getPer60Discount() {
        return per60Discount;
    }

    public void setPer60Discount(Integer per60Discount) {
        this.per60Discount = per60Discount;
    }

    public BigDecimal getPerx() {
        return perx;
    }

    public void setPerx(BigDecimal perx) {
        this.perx = perx;
    }

    public BigDecimal getRecording() {
        return recording;
    }

    public void setRecording(BigDecimal recording) {
        this.recording = recording;
    }

    public Integer getRecordingDiscount() {
        return recordingDiscount;
    }

    public void setRecordingDiscount(Integer recordingDiscount) {
        this.recordingDiscount = recordingDiscount;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}