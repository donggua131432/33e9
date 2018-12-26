package com.e9cloud.core.common;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/8/1.
 */
public class Tree implements Serializable {

    private static final long serialVersionUID = 4570001015932622259L;

    /** 子节点id*/
    private String id;

    /** 父节点id*/
    private String pId;

    /** 节点名*/
    private String name;

    /** 自定义属性 **/
    private String custom;

    /** 从数据库中取值设置open的值 **/
    private String openStr;

    /** checkbox是否勾选 */
    private boolean checked = false;

    private String iconSkin = "folder";

    private boolean childOuter = false;

    private boolean open = false;

    private boolean isParent = false;

    /**
     * tree关联一个list列表
     */
    private List<?> linkList;

    /** 为映射checked字段 */
    private String hasRole;

    /**链接地址**/
    private String link;

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setOpenStr(String openStr){
        this.openStr = openStr;
        this.open = "true".equals(openStr);
    }

    public List<?> getLinkList() {
        return linkList;
    }

    public void setLinkList(List<?> linkList) {
        this.linkList = linkList;
    }

    public String getOpneStr(){
        return this.openStr;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Tree(){
    }

    public Tree(String id, String pId){
        this.id = id;
        this.pId = pId;
        this.iconSkin = "folder";
    }

    public Tree(String id, String pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.iconSkin = "folder";
    }

    public Tree(String id, String pId, String name, boolean isParent) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.isParent = isParent;
        this.iconSkin = "folder";
    }

    public Tree(String id, String pId, String name, String iconSkin) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.iconSkin = iconSkin;
    }
    /**
     * 此构造器在项目导出时应用到
     * @param id
     * @param pId
     * @param name
     * @param iconSkin
     * @param linkList
     */
    public Tree(String id, String pId, String name, String iconSkin, List<?> linkList) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.iconSkin = iconSkin;
        this.linkList = linkList;
    }

    public Tree(String id, String pId, String name, String iconSkin, boolean isParent) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.iconSkin = iconSkin;
        this.isParent = isParent;
    }

    public Tree(String id, String name, boolean isParent) {
        this.id = id;
        this.name = name;
        this.isParent = isParent;
    }

    public Tree(String id, String pId, String name, String link, String iconSkin) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.link=link;
        this.iconSkin = iconSkin;
    }

    public Tree(String id, String pId, String name, String link, String iconSkin, boolean isParent) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.link=link;
        this.iconSkin = iconSkin;
        this.isParent = isParent;
    }

    /* 以下是getter和setter方法 */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public boolean isChildOuter() {
        return childOuter;
    }

    public void setChildOuter(boolean childOuter) {
        this.childOuter = childOuter;
    }

    public String getHasRole() {
        return hasRole;
    }

    public void setHasRole(String hasRole) {
        this.checked = hasRole.equalsIgnoreCase("Y");
        this.hasRole = hasRole;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString(){
        if (iconSkin == null || iconSkin.length() <= 0){
            return "id: " + id + ", pId: " + pId + ", name: " + name;
        }else{
            return "id: " + id + ", pId: " + pId + ", name: " + name + ", iconSkin:" + iconSkin;

        }

    }

}
