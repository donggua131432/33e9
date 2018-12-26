package com.e9cloud.core.util;

/**
 * 用于返回结果(json格式)的封装
 * Created by dukai on 2016/06/06.
 */
public class JSonMessageSubmit {

    /**
     * 代码
     */
    private String status;

    /**
     * 信息
     */
    private String info;

    public JSonMessageSubmit(){}

    public JSonMessageSubmit(String status, String info){
        this.status = status;
        this.info = info;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
