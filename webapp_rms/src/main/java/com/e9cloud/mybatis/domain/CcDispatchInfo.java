package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 智能云调度：话务调度呼叫中心表
 *
 * @author Created by pengchunchen on 2016/8/8.
 */
public class CcDispatchInfo implements Serializable {

    private Integer id;
    private String dispatchId;//调度id
    private String subid;//呼叫中心subid
    private Integer pri;//优先级

    //扩展字段
    private String ccname;//呼叫中心名称

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
        this.dispatchId = dispatchId;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public Integer getPri() {
        return pri;
    }

    public void setPri(Integer pri) {
        this.pri = pri;
    }

    public String getCcname() {
        return ccname;
    }

    public void setCcname(String ccname) {
        this.ccname = ccname;
    }
}