package com.e9cloud.rest.obt;

import java.util.Date;

public class VnObj {
    private Integer id;

    private String vnid;

    private String appid;

    private String sid;

    private String caller;

    private String called;

    private String vn;

    private String vnType;

    private Integer validtime;

    private Integer needrecord;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVnid() {
        return vnid;
    }

    public void setVnid(String vnid) {
        this.vnid = vnid == null ? null : vnid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller == null ? null : caller.trim();
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called == null ? null : called.trim();
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn == null ? null : vn.trim();
    }

    public String getVnType() {
        return vnType;
    }

    public void setVnType(String vnType) {
        this.vnType = vnType;
    }

    public Integer getValidtime() {
        return validtime;
    }

    public void setValidtime(Integer validtime) {
        this.validtime = validtime;
    }

    public Integer getNeedrecord() {
        return needrecord;
    }

    public void setNeedrecord(Integer needrecord) {
        this.needrecord = needrecord;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String toString(){
        return this.sid+"|"+this.appid+"|"+this.getCaller()+"|"+this.getCalled()+"|"+this.needrecord+"|"+this.vnid+"|"+this.vnType;
    }
}