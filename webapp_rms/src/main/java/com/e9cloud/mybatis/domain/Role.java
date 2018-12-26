package com.e9cloud.mybatis.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 角色
 */
public class Role {

    // id uuid
    private String id;

    // 角色名称
    private String name;

    // 角色类型
    private String type;

    // 角色描述
    private String description;

    // 角色排序
    private Byte ordering;

    //平台标识（R：运营后台；D：数据中心）
    private String sysType;

    // 角色权限管理
    private List<Action> actions = Lists.newArrayList();

    ///////////////// 以下是setter 和 getter 方法 //////////////////

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getOrdering() {
        return ordering;
    }

    public void setOrdering(Byte ordering) {
        this.ordering = ordering;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getSysType() {

        return sysType;
    }
}