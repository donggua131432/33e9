package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 专线语音--小时统计
 */
public class StatRestHourRecord {

    private Integer id;

    // 统计日期
    private Date stathour;

    // 计费ID
    private String feeid;

    // AB路
    private String abline;

    // 呼叫总数
    private Integer callcnt;

    // 呼叫成功数
    private Integer succcnt;

    // 总通话时长(秒)
    private Integer thscsum;

    //总计费时长(分钟)
    private Integer jfscsum;

    // 录音数
    private Integer rcdCnt;

    // 录音时长
    private Integer rcdTime;

    // 录音话单数
    private Integer rcdBillCnt;

    // 总话费
    private BigDecimal fee;

    // 总录音费
    private BigDecimal recordingfee;

    // 呼叫应答数
    private Integer answercnt;

    ///////////////////////////////////////////// 以下是setter和getter方法 //////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStathour() {
        return stathour;
    }

    public void setStathour(Date stathour) {
        this.stathour = stathour;
    }

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
    }

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public Integer getCallcnt() {
        return callcnt;
    }

    public void setCallcnt(Integer callcnt) {
        this.callcnt = callcnt;
    }

    public Integer getSucccnt() {
        return succcnt;
    }

    public void setSucccnt(Integer succcnt) {
        this.succcnt = succcnt;
    }

    public Integer getThscsum() {
        return thscsum;
    }

    public void setThscsum(Integer thscsum) {
        this.thscsum = thscsum;
    }

    public Integer getJfscsum() {
        return jfscsum;
    }

    public void setJfscsum(Integer jfscsum) {
        this.jfscsum = jfscsum;
    }

    public Integer getRcdCnt() {
        return rcdCnt;
    }

    public void setRcdCnt(Integer rcdCnt) {
        this.rcdCnt = rcdCnt;
    }

    public Integer getRcdTime() {
        return rcdTime;
    }

    public void setRcdTime(Integer rcdTime) {
        this.rcdTime = rcdTime;
    }

    public Integer getRcdBillCnt() {
        return rcdBillCnt;
    }

    public void setRcdBillCnt(Integer rcdBillCnt) {
        this.rcdBillCnt = rcdBillCnt;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getRecordingfee() {
        return recordingfee;
    }

    public void setRecordingfee(BigDecimal recordingfee) {
        this.recordingfee = recordingfee;
    }

    public Integer getAnswercnt() {
        return answercnt;
    }

    public void setAnswercnt(Integer answercnt) {
        this.answercnt = answercnt;
    }
}