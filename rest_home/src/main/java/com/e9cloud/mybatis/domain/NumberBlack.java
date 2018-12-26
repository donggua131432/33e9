package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class NumberBlack implements Serializable {

    private int id;//主键id
    private Date createTime;//创建时间
    private String number;//黑名单号码
    private String remark;//备注原因

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}