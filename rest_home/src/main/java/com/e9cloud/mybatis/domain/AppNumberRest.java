package com.e9cloud.mybatis.domain;

import java.util.Date;

public class AppNumberRest {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_app_number_rest.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_app_number_rest.number_id
     *
     * @mbggenerated
     */
    private Long numberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_app_number_rest.appid
     *
     * @mbggenerated
     */
    private String appid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_app_number_rest.number
     *
     * @mbggenerated
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_app_number_rest.addtime
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_app_number_rest.id
     *
     * @return the value of tb_app_number_rest.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_app_number_rest.id
     *
     * @param id the value for tb_app_number_rest.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_app_number_rest.number_id
     *
     * @return the value of tb_app_number_rest.number_id
     *
     * @mbggenerated
     */
    public Long getNumberId() {
        return numberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_app_number_rest.number_id
     *
     * @param numberId the value for tb_app_number_rest.number_id
     *
     * @mbggenerated
     */
    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_app_number_rest.appid
     *
     * @return the value of tb_app_number_rest.appid
     *
     * @mbggenerated
     */
    public String getAppid() {
        return appid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_app_number_rest.appid
     *
     * @param appid the value for tb_app_number_rest.appid
     *
     * @mbggenerated
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_app_number_rest.number
     *
     * @return the value of tb_app_number_rest.number
     *
     * @mbggenerated
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_app_number_rest.number
     *
     * @param number the value for tb_app_number_rest.number
     *
     * @mbggenerated
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_app_number_rest.addtime
     *
     * @return the value of tb_app_number_rest.addtime
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_app_number_rest.addtime
     *
     * @param addtime the value for tb_app_number_rest.addtime
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}