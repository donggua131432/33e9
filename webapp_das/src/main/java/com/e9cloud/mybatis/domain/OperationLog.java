package com.e9cloud.mybatis.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 操作日志
 */
public class OperationLog implements Serializable{

    /** 用于分页（行号） */
    private Long rowNO;

    private Long id;

    /** 日志名称（末级菜单名） */
    private String logName;

    /** 日志内容 */
    private String logContent;

    /** 操作类型 */
    private String logType;

    /** 用户id */
    private Integer userId;

    /** 用户登录名 */
    private String username;

    /** 用户姓名 */
    private String nick;

    /** 电脑ip */
    private String ip;

    /** 角色名称 */
    private String roleName;

    /** 操作时间 */
    private Date createTime;

    /** sid 客户的行为 */
    private String sid;

    //////////////////////////以下是setter 和 getter 方法/////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName == null ? null : logName.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick == null ? null : nick.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getRowNO() {
        return rowNO;
    }

    public void setRowNO(Long rowNO) {
        this.rowNO = rowNO;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}