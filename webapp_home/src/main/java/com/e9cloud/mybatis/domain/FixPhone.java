package com.e9cloud.mybatis.domain;

public class FixPhone {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sp_fixphone.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sp_fixphone.appid
     *
     * @mbggenerated
     */
    private String appid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sp_fixphone.cityid
     *
     * @mbggenerated
     */
    private String cityid;

    // 城市区号
    private String areaCode;

    // execl 导入时错误信息
    private String importCommon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sp_fixphone.number
     *
     * @mbggenerated
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_sp_fixphone.status
     *
     * @mbggenerated
     */
    private String status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sp_fixphone.id
     *
     * @return the value of tb_sp_fixphone.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sp_fixphone.id
     *
     * @param id the value for tb_sp_fixphone.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sp_fixphone.appid
     *
     * @return the value of tb_sp_fixphone.appid
     *
     * @mbggenerated
     */
    public String getAppid() {
        return appid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sp_fixphone.appid
     *
     * @param appid the value for tb_sp_fixphone.appid
     *
     * @mbggenerated
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sp_fixphone.cityid
     *
     * @return the value of tb_sp_fixphone.cityid
     *
     * @mbggenerated
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sp_fixphone.cityid
     *
     * @param cityid the value for tb_sp_fixphone.cityid
     *
     * @mbggenerated
     */
    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sp_fixphone.number
     *
     * @return the value of tb_sp_fixphone.number
     *
     * @mbggenerated
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sp_fixphone.number
     *
     * @param number the value for tb_sp_fixphone.number
     *
     * @mbggenerated
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_sp_fixphone.status
     *
     * @return the value of tb_sp_fixphone.status
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_sp_fixphone.status
     *
     * @param status the value for tb_sp_fixphone.status
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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