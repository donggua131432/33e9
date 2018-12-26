package com.e9cloud.mybatis.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CcDispatch implements Serializable{

    private static final long serialVersionUID = 3328297479151409794L;

    private Integer id;

    private String dispatchName;

    private String dispatchId;

    private String sid;

    private String areaId;

    private Short timeStart;
    private Short timeEnd;

    private Byte weekday;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validDate1;

    private String remark;

    private Date ctime;

    private String status;//删除标记

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Short getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Short timeStart) {
        this.timeStart = timeStart;
    }

    public Short getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Short timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Byte getWeekday() {
        return weekday;
    }

    public void setWeekday(Byte weekday) {
        this.weekday = weekday;
    }

    public String getValidDate() {
        if(validDate != null){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(validDate);
        }
        return null;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getValidDate1() {
        return validDate1;
    }

    public void setValidDate1(Date validDate1) {
        this.validDate1 = validDate1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    public String getDispatchName() {

        return dispatchName;
    }
}