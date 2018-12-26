package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 消息模板
 */
public class MsgTemplate {

    private String id;

    // 模板代码
    private String tempCode;

    // 标题
    private String title;

    // 模板内容
    private String content;

    // 备注
    private String remark;

    ///////////////////以下是setter和getter方法//////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTempCode() {
        return tempCode;
    }

    public void setTempCode(String tempCode) {
        this.tempCode = tempCode == null ? null : tempCode.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}