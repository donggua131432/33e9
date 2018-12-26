package com.e9cloud.rest.obt;

/**
 * Created by Administrator on 2016/3/4.
 */
public class SubAppBody
{
    private String action;//用以区分具体请求 create modify delete query
    private  String appId ;//应用ID
    private  String subName;//子账号名称，用户自定义，最多6个字符（包含中文字），同一应用下唯一
    private  String newSubName ;//修改子账号名称

    private  String statusCode ;//请求状态码，取值000000（成功）
    private  String dateCreated;//请求时间
    private  String subId;//子账号唯一标识符
    private  String createTime;//子账号创建时间
    private  String modifyTime ;//子账号信息修改时间
    private  String count;//应用下子账号数量

    /**创建子账号
     * 享有属性
     *   请求属性：appId  subName
     *   响应属性：statusCode  dateCreated  subId  subName  createTime
     **/

    /**修改子账号
     * 享有属性
     *   请求属性：appId  newSubName  subId
     *   响应属性：statusCode  dateCreated  subId  subName  modifyTime
     **/

    /**删除子账号
     * 享有属性
     *   请求属性：appId  subId
     *   响应属性：statusCode  dateCreated
     **/

    /**查询子账号
     * 享有属性
     *   请求属性：appId
     *   响应属性：statusCode  dateCreated  count
     *     subId（列表形式返回）  subName（列表形式返回）  createTime（列表形式返回）
     **/

    //将对应字段转换成XML字符串
    public String toXML(String rootElement) {
        StringBuffer str=new StringBuffer();
        str.append( "<?xml version='1.0'?>");
        str.append( "<" +rootElement+ ">");

        //如果是呼叫挂机通知，则组装挂机专有属性
        if("create".equals(action)){
            str.append( "<statusCode>"+statusCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<subId>"+subId+"</subId>" +
                    "<subName>"+subName+"</subName>" +
                    "<createTime>"+createTime+"</createTime>");
        }else if("modify".equals(action)){
            str.append( "<statusCode>"+statusCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<subId>"+subId+"</subId>" +
                    "<subName>"+subName+"</subName>" +
                    "<modifyTime>"+modifyTime+"</modifyTime>");
        }else if("delete".equals(action)){
            str.append( "<statusCode>"+statusCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>");
        }else{
            str.append( "<statusCode>"+statusCode+"</statusCode>" +
                    "<dateCreated>"+dateCreated+"</dateCreated>" +
                    "<count>"+count+"</count>" +
                    "<subId>"+subId+"</subId>" +
                    "<subName>"+subName+"</subName>" +
                    "<createTime>"+createTime+"</createTime>");
        }

        str.append( "</" +rootElement+ ">");
        return str.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getNewSubName() {
        return newSubName;
    }

    public void setNewSubName(String newSubName) {
        this.newSubName = newSubName;
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

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
