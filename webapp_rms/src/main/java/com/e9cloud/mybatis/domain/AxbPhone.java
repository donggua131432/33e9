package com.e9cloud.mybatis.domain;

import java.util.Date;

/**
 * Created by hzd on 2017/4/18.
 */
public class AxbPhone {

    //主键
    private String id;

    //虚拟小号
    private String number;

    //城市区号
    private String areacode;

    // 城市区号ID
    private String cityid;

    //  状态（00:待分配 01:已分配  02：已删除）
    private String status;

    //  创建时间
    private Date addtime;
    //最后的修改时间
    private Date updatetime;

    // execl 导入时错误信息
    private String importCommon;

    //运营商
    private String operator;

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {

        return operator;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }

    public String getImportCommon() {

        return importCommon;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getId() {

        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getCityid() {
        return cityid;
    }

    public String getStatus() {
        return status;
    }

    public Date getAddtime() {
        return addtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }
}
