package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/3/9.
 */
public class OnlineConcurrent
{
    private  String appId ;//应用ID
    private  String queryTime;//请求查询的时间，精确到分钟，格式"yyyyMMddHHmm"。如：201404161420

    private  String statusCode ;//请求状态码，取值000000（成功）
    private  String dateCreated;//请求时间
    private  Long concurrentNumber;//瞬时在线并发通话数。可稍有延时的统计数据

    //将对应字段转换成XML字符串
    public String toXML(String rootElement) {
        StringBuffer str=new StringBuffer();
        str.append( "<?xml version='1.0'?>");
        str.append( "<" +rootElement+ ">");
        str.append( "<statusCode>"+statusCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<concurrentNumber>"+concurrentNumber+"</concurrentNumber>");
        str.append( "</" +rootElement+ ">");
        return str.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getConcurrentNumber() {
        return concurrentNumber;
    }

    public void setConcurrentNumber(Long concurrentNumber) {
        this.concurrentNumber = concurrentNumber;
    }
}
