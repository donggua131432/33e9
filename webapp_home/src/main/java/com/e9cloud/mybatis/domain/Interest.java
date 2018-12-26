package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 用户感兴趣的产品
 */
public class Interest implements Serializable{

    /** 主键，自增 */
    private Integer id;

    /** 用户主账号 */
    private String uid;

    /** 用户喜欢产品的Id */
    private String dicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId == null ? null : dicId.trim();
    }
}