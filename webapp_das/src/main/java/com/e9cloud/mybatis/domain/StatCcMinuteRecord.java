package com.e9cloud.mybatis.domain;

import java.math.BigDecimal;
import java.util.Date;

// 智能云调度 分钟统计表 stat_cc_minute_record
public class StatCcMinuteRecord {

    // 主键 自增
    private Integer id;

    // 统计时间
    private Date statmin;

    // 计费id
    private String feeid;

    // appid
    private String appid;

    // 子应用id
    private String subid;

    // IO路
    private String abline;

    // 运营商
    private String operator;

    // 省份
    private String pname;

    // 城市
    private String cname;

    // 呼叫总数
    private Integer callcnt;

    // 呼叫接通数
    private Integer succcnt;

    // 呼叫应答数
    private Integer answercnt;

    // 振铃时长
    private Integer ringringsum;

    // 总通话时长(秒)
    private Integer thscsum;

    // 总计费时长(分)
    private Integer jfscsum;

    // 总话费
    private BigDecimal fee;

    //
    private Date ctime;

    ///////////////以下是setter和getter方法//////////////////

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

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid == null ? null : feeid.trim();
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

    public String getAbline() {
        return abline;
    }

    public void setAbline(String abline) {
        this.abline = abline == null ? null : abline.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
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

    public Integer getAnswercnt() {
        return answercnt;
    }

    public void setAnswercnt(Integer answercnt) {
        this.answercnt = answercnt;
    }

    public Integer getRingringsum() {
        return ringringsum;
    }

    public void setRingringsum(Integer ringringsum) {
        this.ringringsum = ringringsum;
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}