package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 智能云调度：区域定义表
 *
 * @author Created by pengchunchen on 2016/8/9.
 */
public class CcArea implements Serializable {

    private Integer id;
    private String areaId;//区域id
    private String aname;//区域名称
    private String sid;//客户sid
    private Date ctime;
    private String pinyin; //区域名称拼音
    private String status;//删除状态

    //扩展字段
    private String name;//客户名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getPinyin() { return pinyin;}

    public void setPinyin(String pinyin) { this.pinyin = pinyin;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}