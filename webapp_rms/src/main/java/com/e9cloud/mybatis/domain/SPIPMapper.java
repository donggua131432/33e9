package com.e9cloud.mybatis.domain;

public class SPIPMapper {

    // id 自增
    private Integer id;

    // 内网ip
    private String innerIp;

    // 外网ip
    private String outerIp;

    // 可以使用的环境 ：环境标记 : DEV 开发环境；GAMMA 测试环境；IDC 生产环境
    private String envName;

    ///////////////////////一下是setter和getter方法////////////////////////

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp == null ? null : innerIp.trim();
    }

    public String getOuterIp() {
        return outerIp;
    }

    public void setOuterIp(String outerIp) {
        this.outerIp = outerIp == null ? null : outerIp.trim();
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName == null ? null : envName.trim();
    }
}