package com.e9cloud.mybatis.domain;

/**
 * 角色-权限关联
 */
public class RoleAction {

    // 主键，自增
    private Integer id;

    // 角色id
    private String roleId;

    // 权限id
    private String actionId;

    /////////////////// setter 和 getter //////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId == null ? null : actionId.trim();
    }
}