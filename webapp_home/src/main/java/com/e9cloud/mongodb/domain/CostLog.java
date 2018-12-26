package com.e9cloud.mongodb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DK on 2016/6/22.
 */
public class CostLog implements Serializable {

    private static final long serialVersionUID = 8247028282257423783L;

    private String feeid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date itime;

    private Double fee;

    private String remark;

    public String getFeeid() {
        return feeid;
    }

    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }

    public Date getItime() {
        return itime;
    }

    public void setItime(Date itime) {
        this.itime = itime;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
