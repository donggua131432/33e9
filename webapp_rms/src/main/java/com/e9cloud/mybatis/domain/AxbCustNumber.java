package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * Created by hzd on 2017/4/19.
 */
public class AxbCustNumber {
    //主键
    private String id;

    //appid
    private String appid;

    //公共号码池关联外键
    private String axbNumId;

    //状态
    private String status;

    //  创建时间
    private Date addtime;
    //最后的修改时间
    private Date updatetime;

    //冻结的时候，保存冻结时间
    private Date freezetime;

    //记录删除的时间
    private Date deltime;

    //客户名称
    private String name;

    private String sid;
    //应用名称
    private String appName;

    public String getId() {
        return id;
    }

    public String getAppid() {
        return appid;
    }

    public String getAxbNumId() {
        return axbNumId;
    }

    public String getStatus() {
        return status;
    }

    public Date getAddtime() {
        return addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public Date getFreezetime() {
        return freezetime;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    public void setAxbNumId(String axbNumId) {
        this.axbNumId = axbNumId == null ? null : axbNumId.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setFreezetime(Date freezetime) {
        this.freezetime = freezetime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {

        return name;
    }

    public String getSid() {
        return sid;
    }

    public String getAppName() {
        return appName;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }
}
