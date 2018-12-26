package com.e9cloud.mybatis.domain;

import java.util.Date;

public class ExecuteHttp {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.id
     *
     * @mbggenerated
     */
    private Integer id;




    private Integer url_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.task_id
     *
     * @mbggenerated
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.url
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.result
     *
     * @mbggenerated
     */
    private String result;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.addtime
     *
     * @mbggenerated
     */
    private Date addtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_http_execute.updatetime
     *
     * @mbggenerated
     */
    private Date updatetime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.id
     *
     * @return the value of tb_http_execute.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.id
     *
     * @param id the value for tb_http_execute.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUrl_id() {
        return url_id;
    }

    public void setUrl_id(Integer url_id) {
        this.url_id = url_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.task_id
     *
     * @return the value of tb_http_execute.task_id
     *
     * @mbggenerated
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.task_id
     *
     * @param taskId the value for tb_http_execute.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.url
     *
     * @return the value of tb_http_execute.url
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.url
     *
     * @param url the value for tb_http_execute.url
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.result
     *
     * @return the value of tb_http_execute.result
     *
     * @mbggenerated
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.result
     *
     * @param result the value for tb_http_execute.result
     *
     * @mbggenerated
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.addtime
     *
     * @return the value of tb_http_execute.addtime
     *
     * @mbggenerated
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.addtime
     *
     * @param addtime the value for tb_http_execute.addtime
     *
     * @mbggenerated
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_http_execute.updatetime
     *
     * @return the value of tb_http_execute.updatetime
     *
     * @mbggenerated
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_http_execute.updatetime
     *
     * @param updatetime the value for tb_http_execute.updatetime
     *
     * @mbggenerated
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}