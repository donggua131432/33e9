package com.e9cloud.mybatis.domain;

import java.util.Date;

public class EccShownum {


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.appid
     *
     * @mbggenerated
     */
    private String appid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.eccid
     *
     * @mbggenerated
     */
    private String eccid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.cityid
     *
     * @mbggenerated
     */
    private String cityid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.number
     *
     * @mbggenerated
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.addtime
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_ecc_shownum.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    private String pcode;

    private String ccode;

    // 城市区号
    private String areaCode;

    // execl 导入时错误信息
    private String importCommon;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.id
     *
     * @return the value of tb_ecc_shownum.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.id
     *
     * @param id the value for tb_ecc_shownum.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.appid
     *
     * @return the value of tb_ecc_shownum.appid
     *
     * @mbggenerated
     */
    public String getAppid() {
        return appid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.appid
     *
     * @param appid the value for tb_ecc_shownum.appid
     *
     * @mbggenerated
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.eccid
     *
     * @return the value of tb_ecc_shownum.eccid
     *
     * @mbggenerated
     */
    public String getEccid() {
        return eccid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.eccid
     *
     * @param eccid the value for tb_ecc_shownum.eccid
     *
     * @mbggenerated
     */
    public void setEccid(String eccid) {
        this.eccid = eccid == null ? null : eccid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.cityid
     *
     * @return the value of tb_ecc_shownum.cityid
     *
     * @mbggenerated
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.cityid
     *
     * @param cityid the value for tb_ecc_shownum.cityid
     *
     * @mbggenerated
     */
    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.number
     *
     * @return the value of tb_ecc_shownum.number
     *
     * @mbggenerated
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.number
     *
     * @param number the value for tb_ecc_shownum.number
     *
     * @mbggenerated
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.status
     *
     * @return the value of tb_ecc_shownum.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.status
     *
     * @param status the value for tb_ecc_shownum.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.addtime
     *
     * @return the value of tb_ecc_shownum.addtime
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.addtime
     *
     * @param addtime the value for tb_ecc_shownum.addtime
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_ecc_shownum.updatetime
     *
     * @return the value of tb_ecc_shownum.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_ecc_shownum.updatetime
     *
     * @param updatetime the value for tb_ecc_shownum.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getImportCommon() {
        return importCommon;
    }

    public void setImportCommon(String importCommon) {
        this.importCommon = importCommon;
    }
}