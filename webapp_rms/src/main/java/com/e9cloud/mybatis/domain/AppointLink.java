package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 特例表
 *
 * @author Created by hzd on 2016/8/31.
 */

public class AppointLink implements Serializable {


    private static final long serialVersionUID = 1816538011484425935L;

    private Integer id;

    private String xhTelno;//显号号段

    private String destTelno;//目标号段

    private Integer type;//类型

    private String rn;//指定rn

    private String remake;//备注

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public String getXhTelno() {
        return xhTelno;
    }

    public String getDestTelno() {
        return destTelno;
    }

    public Integer getType() {
        return type;
    }

    public String getRn() {
        return rn;
    }

    public String getRemake() {
        return remake;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setXhTelno(String xhTelno) {
        this.xhTelno = xhTelno == null ? null : xhTelno.trim();
    }

    public void setDestTelno(String destTelno) {
        this.destTelno = destTelno == null ? null : destTelno.trim();
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setRn(String rn) {
        this.rn = rn == null ? null : rn.trim();
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }
}