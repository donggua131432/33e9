package com.e9cloud.mybatis.domain;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单相关
 */
public class Menu implements Serializable{

    /** id 主键 */
    private String id;

    /** 上级菜单id */
    private String parentId;

    /** 菜单名称 */
    private String name;

    /** 菜单路径 */
    private String url;

    /** 菜单图标 */
    private String icon;

    /** 菜单状态 */
    private String state;

    /** 菜单级别 */
    private String level;

    /** 菜单排序 */
    private Integer orderBy;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String sysType;

    /** 是否是最后一个 */
    private boolean isLast;

    /** 父类是否是最后一个 */
    private boolean isParentLast;

    /** 子菜单 */
    private List<Menu> children = Lists.newArrayList();

    /** 权限 */
    private List<Action> actions = Lists.newArrayList();

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof Menu) {
            Menu o = (Menu) obj;
            return this.id != null && this.id.equals(o.getId());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /** ------以下为setter何getter方法------ **/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isParentLast() {
        return isParentLast;
    }

    public void setParentLast(boolean parentLast) {
        isParentLast = parentLast;
    }

    public String getSysType() { return sysType; }

    public void setSysType(String sysType) { this.sysType = sysType; }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}