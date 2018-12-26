package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息、通知
 */
public class SysMessage implements Serializable {

    private String id;

    // 标题
    private String title;

    // 内容
    private String content;

    // 消息类型
    private String msgCode;

    // 发送人的id，或系统消息（0）
    private String sendId;

    // 发送时间
    private Date sendTime;

    // 用户第一次读取消息的时间
    private Date readTime;

    // 用户的id
    private String uid;

    // 状态（0：未读，1，已读）
    private String status;

    // 创建时间
    private Date createTime;

    // 用户的业务类型
    private String busType;

    // 模板的id
    private String tempId;

    ////////////////////////////////以下是setter和getter方法///////////////////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId == null ? null : sendId.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType == null ? null : busType.trim();
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }
}