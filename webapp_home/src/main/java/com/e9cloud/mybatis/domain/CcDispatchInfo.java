package com.e9cloud.mybatis.domain;

import java.io.Serializable;

public class CcDispatchInfo implements Serializable{

    private static final long serialVersionUID = 5415145374353905334L;

    private Integer id;

    private String dispatchId;

    private String subid;

    private Byte pri;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId == null ? null : dispatchId.trim();
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid == null ? null : subid.trim();
    }

    public Byte getPri() {
        return pri;
    }

    public void setPri(Byte pri) {
        this.pri = pri;
    }
}