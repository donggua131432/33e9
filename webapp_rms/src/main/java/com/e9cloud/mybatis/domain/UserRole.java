package com.e9cloud.mybatis.domain;

// 用户角色关联信息
public class UserRole {

    // id,自增
    private Integer id;

    // 后台用户
    private Integer userId;

    // 角色 id
    private String roleId;

    /////////////////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}