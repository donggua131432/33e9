package com.e9cloud.mybatis.domain;

import java.util.Date;

public class CbTask {

    private Integer id;

    //任务类型 具体类型查看taskType
    private String type;

    //json参数
    private String paramJson;

    //任务执行时间
    private Date executeTime;

    //任务执行编号
    private String executeCode;

    //执行状态
    private String status;


    private Date addtime;

    private Date updatetime;

    // 类型
    public enum TaskType {
        fsproxy, area_code, special_code, files, zj_ivr_xml, app_rn, business_rn
    }

    /********************************************************* 以下是setter和getter方法 *********************************************************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public void setType(TaskType taskType) {
        this.type = taskType.toString();
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson == null ? null : paramJson.trim();
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getExecuteCode() {
        return executeCode;
    }

    public void setExecuteCode(String executeCode) {
        this.executeCode = executeCode == null ? null : executeCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}