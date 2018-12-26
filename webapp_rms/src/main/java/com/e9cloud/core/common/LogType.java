package com.e9cloud.core.common;

/**
 * 日志操作类型
 * 定义了5种操作类型
 * Created by wzj on 2016/2/24.
 */
public enum LogType {

    LOGIN("01", "登录"), LOGOUT("02", "退出"), INSERT("03", "新增"), DELETE("04", "删除"), UPDATE("05", "修改");

    private String nCode;

    private String nName;

    LogType(String code, String name){
        this.nCode = code;
        this.nName = name;
    }

    @Override
    public String toString() {
        return this.nCode;
    }

    public String getName() {
        return this.nName;
    }

    public String getCode() {
        return this.nCode;
    }
}
