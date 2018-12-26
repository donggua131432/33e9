package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// rest业务，话务统计--分钟统计
public class RestStatMinuteRecord {

    // 自增主键
    private Integer id;

    // 统计时间
    private Date statmin;

    // 应用id
    private String appid;

    // 子账号id
    private String subid;

    // 计费主账号
    private String feeid;

    // AB路
    private String abline;

    // 呼叫次数
    private Integer callcnt;

    // 呼叫成功次数
    private Integer succcnt;

    // 总通话时长
    private Integer thscsum;

    // 总计费时长
    private Integer jfscsum;

    // 总话费
    private BigDecimal fee;

    // 总录音费
    private BigDecimal recordingfee;

    // 呼叫应答数
    private Integer answercnt;

    // 录音数
    private Integer rcdCnt;

    // 录音时长
    private Integer rcdTime;

    // 录音话单数
    private Integer rcdBillCnt;

    //
    private Date ctime;

    ///////////////////////////////////////// 以下是setter和getter方法 //////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStatmin() {
        return statmin;
    }

    public void setStatmin(Date statmin) {
        this.statmin = statmin;
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}