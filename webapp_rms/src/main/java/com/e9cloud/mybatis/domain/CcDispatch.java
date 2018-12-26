package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 智能云调度：话务调度表
 *
 * @author Created by pengchunchen on 2016/8/8.
 */
public class CcDispatch implements Serializable {

    private Integer id;
    private String dispatchName;//调度名称
    private String dispatchId;//调度id
    private String sid;//客户sid
    private String areaId;//调度区域id
    private Integer timeStart;//开始时间
    private Integer timeEnd;//结束时间
    private Integer weekday;//作用日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validDate;//生效时间
    private Integer remark;//备注
    private Date ctime;
    private String status;//删除状态

    //扩展字段
    private String name;//客户名称
    private String appName;//应用名称
    private String aname;//区域名称


    private String timeStartShow;//开始时间格式查看
    private String timeEndShow;//结束时间格式查看

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Integer timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Integer timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getTimeStartShow() {
        return timeStartShow;
    }

    public void setTimeStartShow(String timeStartShow) {
        this.timeStartShow = timeStartShow;
    }

    public String getTimeEndShow() {
        return timeEndShow;
    }

    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    public String getDispatchName() {

        return dispatchName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    public void setTimeEndShow(String timeEndShow) {
        this.timeEndShow = timeEndShow;
    }
}