package com.e9cloud.mybatis.domain;

import java.util.Date;

public class ExtraService {
    private Integer id;
    private String sid;
    private String extraType;//增值服务类型:01:B路录音;02:6秒计费 03:B路手机显号免审
    private String status;//状态 默认00:正常 01：冻结 02：禁用
    private Date createDate;//创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getExtraType() {
        return extraType;
    }

    public void setExtraType(String extraType) {
        this.extraType = extraType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}