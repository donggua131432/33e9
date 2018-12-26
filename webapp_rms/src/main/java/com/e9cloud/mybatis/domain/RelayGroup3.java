package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 中继群主叫强显号段表
 */
public class RelayGroup3 extends RelayGroup implements Serializable {

    // 主键 自增
    private Integer id;

    // 中继群编号
    private String tgNum;

    // 中继群名称
    private String tgName;

    // 号段起始号码
    private String codeStart;

    // 号段结束号码
    private String codeEnd;

    public RelayGroup3 () {
        this.setG("3");
    }

    //////////////////////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTgNum() {
        return tgNum;
    }

    public void setTgNum(String tgNum) {
        this.tgNum = tgNum == null ? null : tgNum.trim();
    }

    public String getTgName() {
        return tgName;
    }

    public void setTgName(String tgName) {
        this.tgName = tgName == null ? null : tgName.trim();
    }

    public String getCodeStart() {
        return codeStart;
    }

    public void setCodeStart(String codeStart) {
        this.codeStart = codeStart == null ? null : codeStart.trim();
    }

    public String getCodeEnd() {
        return codeEnd;
    }

    public void setCodeEnd(String codeEnd) {
        this.codeEnd = codeEnd == null ? null : codeEnd.trim();
    }
}