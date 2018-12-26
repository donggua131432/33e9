package com.e9cloud.core.util;

/**
 * 用于返回结果(json格式)的封装
 * Created by wzj on 2015/12/16.
 */
public class JSonMessage {

    /**
     * 代码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;

    public JSonMessage(){}

    public JSonMessage(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public JSonMessage(String code,String msg, Object data){
        this(code, msg);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
