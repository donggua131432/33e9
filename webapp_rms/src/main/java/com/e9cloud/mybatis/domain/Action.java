package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 系统权限
 */
public class Action implements Serializable {

    // id : uuid
    private String id;

    // 名称
    private String name;

    // 菜单的id
    private String menuId;

    // 菜单的名称
    private String menuName;

    // 类型：增加、删除、查询、修改、导出 等
    private String type;

    // 类型名称
    private String typeName;

    // url action 对应的访问地址
    private String url;

    // 状态
    private String state;

    // 系统类型：R 运营管理平台， D 数据管理平台
    private String sysType;

    ///////////////////////// 以下是setter 和 getter 方法 //////////////////////////

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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}