package com.e9cloud.rest.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SubApp;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.SubAppService;
import com.e9cloud.rest.obt.SubAppBody;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.ReadXmlUtil;
import com.e9cloud.util.Utils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/03/04.
 */
@Controller
@RequestMapping(method = RequestMethod.GET, value = "/{SoftVersion}/Accounts/{AccountSid}/")
public class SubAppController
{
    private static final Logger logger = LoggerFactory.getLogger(SubAppController.class);

    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private SubAppService subAppService;

    @ModelAttribute("params")
    public Map<String,String>  gainRestUrlParas( @PathVariable(value="SoftVersion") String softVersion , @PathVariable(value="AccountSid") String accountSid, @RequestParam(value = "sig") String sig)
    {
        Map<String,String>  params = new HashMap<String, String>();

        params.put("SoftVersion", softVersion);
        params.put("AccountSid", accountSid);
        params.put("Sig", sig);

        return params;
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/create", consumes="application/json")
    public void createJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        request.setCharacterEncoding("UTF-8");
        //获取AS-createJson报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        logger.info(out.toString());

//        // 容纳请求消息实体的字节数组
//        byte[] dataOrigin = new byte[request.getContentLength()];
//        // 得到请求消息的数据输入流
//        DataInputStream in = new DataInputStream(request.getInputStream());
//        in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
//        in.close(); // 关闭数据流
//        String reqcontent = new String(dataOrigin); // 从字节数组中得到表示实体的字符串

        String asBody=out.toString();
        logger.info("获取AS-createJson报文："+asBody);
        //去掉报文最外层的元素，以便实体直接转换
        asBody=asBody.substring(asBody.indexOf("{",1));
        asBody=asBody.substring(0,asBody.lastIndexOf("}"));
        logger.info("AS-createJson报文去掉最外层后："+asBody);
        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody=new Gson().fromJson(asBody,SubAppBody.class);
//        String name=new String(subAppBody.getSubName().getBytes("ISO8859-1"),"UTF-8");

        // 进行创建子账号业务操作
        Map<String, String> map = create(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        createReturn(true, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/create", consumes="application/xml")
    public void createXml(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        //获取AS-createXml报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        String asBody=out.toString();
        logger.info("获取AS-createXml报文："+asBody);
        //解析字符串xml
        Map<String,String> mapTemp= ReadXmlUtil.readXML(asBody);
        //获取相应报文字段
        String appId = mapTemp.get("appId");
        String subNameTemp = mapTemp.get("subName");

        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody.setAppId(appId);
        subAppBody.setSubName(subNameTemp);

        // 进行创建子账号业务操作
        Map<String, String> map = create(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        createReturn(false, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    private void createReturn(boolean isJson, String respCode, String dateCreated, SubAppBody subAppBody, String respDesc,
                                    HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"subId\":\""+subAppBody.getSubId()+"\",\"subName\":\""+subAppBody.getSubName()+"\"," +
                        "\"createTime\":\""+subAppBody.getCreateTime()+"\"}}";
            }else{
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"represent\":\"" + respDesc + "\"}}";
            }
        } else {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<subId>"+subAppBody.getSubId()+"</subId><subName>"+subAppBody.getSubName()+"</subName>" +
                        "<createTime>"+subAppBody.getCreateTime()+"</createTime></resp>";
            }else{
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated><represent>"+respDesc+"</represent></resp>";
            }
        }

        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_create报文：" + respBody);
    }

    private Map<String, String> create(SubAppBody subAppBody) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        Map map = new HashMap<>();

        //进行非空字段验证
        if(!Utils.notEmpty(subAppBody.getAppId())){
            respCode=ConstantsEnum.REST_APPID_NULL.getCode();
            respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
        }else if(!Utils.notEmpty(subAppBody.getSubName())){
            respCode= ConstantsEnum.REST_SUBNAME_NULL.getCode();
            respDesc=ConstantsEnum.REST_SUBNAME_NULL.getDesc();
        }else if(subAppBody.getSubName().length()>6){
            respCode=ConstantsEnum.REST_SUBNAME_LENGTH.getCode();
            respDesc=ConstantsEnum.REST_SUBNAME_LENGTH.getDesc();
        }else{
            //进行应用存在性验证
            AppInfo appInfo=appInfoService.getAppInfoByAppid(subAppBody.getAppId());
            //应用信息不存在，返回对应的错误码报文
            if(appInfo==null){
                respCode=ConstantsEnum.REST_APPID_NO.getCode();
                respDesc=ConstantsEnum.REST_APPID_NO.getDesc();
            }else{
                //进行用户名存在性验证
                Map mapTmp=new HashMap();
                mapTmp.put("appid",subAppBody.getAppId());
                mapTmp.put("subName",subAppBody.getSubName());
                SubApp subAppCheck=subAppService.findSubAppByMap(mapTmp);
                //该应用下用户名不存在，则新建，存在则返回已存在
                if(subAppCheck==null){
                    SubApp subApp= new SubApp();
                    String subid = ID.randomUUID();
                    String subName = subAppBody.getSubName();
                    String appid = subAppBody.getAppId();
                    Date createDate = new Date();
                    subApp.setSubid(subid);
                    subApp.setAppid(appid);
                    subApp.setSubName(subName);
                    subApp.setCreateDate(createDate);
                    //创建子账号
                    subAppService.saveSubApp(subApp);

                    //填充对应值，用于成功返回
                    subAppBody.setSubId(subid);
                    subAppBody.setCreateTime(createDate.toString());

                    respCode=ConstantsEnum.REST_SUCCCODE.getCode();
                    respDesc=subid+"子账号创建成功";
                }else{
                    respCode=ConstantsEnum.REST_SUBNAME_EXIST.getCode();
                    respDesc=ConstantsEnum.REST_SUBNAME_EXIST.getDesc();
                }
            }
        }

        map.put("respCode", respCode);
        map.put("respDesc", respDesc);
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/modify", consumes="application/json")
    public void modifyJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        //获取AS-modifyJson报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }

        String asBody=out.toString();
        logger.info("获取AS-modifyJson报文："+asBody);
        //去掉报文最外层的元素，以便实体直接转换
        asBody=asBody.substring(asBody.indexOf("{",1));
        asBody=asBody.substring(0,asBody.lastIndexOf("}"));
        logger.info("AS-modifyJson报文去掉最外层后："+asBody);
        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody=new Gson().fromJson(asBody,SubAppBody.class);

        // 进行修改子账号业务操作
        Map<String, String> map = modify(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        modifyReturn(true, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/modify", consumes="application/xml")
    public void modifyXml(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        //获取AS-modifyXml报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        String asBody=out.toString();
        logger.info("获取AS-modifyXml报文："+asBody);
        //解析字符串xml
        Map<String,String> mapTemp= ReadXmlUtil.readXML(asBody);
        //获取相应报文字段
        String appId = mapTemp.get("appId");
        String subNameTemp = mapTemp.get("newSubName");
        String subId = mapTemp.get("subId");

        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody.setAppId(appId);
        subAppBody.setNewSubName(subNameTemp);
        subAppBody.setSubId(subId);

        // 进行修改子账号业务操作
        Map<String, String> map = modify(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        modifyReturn(false, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    private void modifyReturn(boolean isJson, String respCode, String dateCreated, SubAppBody subAppBody, String respDesc,
                              HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"subId\":\""+subAppBody.getSubId()+"\",\"subName\":\""+subAppBody.getNewSubName()+"\"," +
                        "\"modifyTime\":\""+dateCreated+"\"}}";
            }else{
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"represent\":\""+respDesc+"\"}}";
            }
        } else {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<subId>"+subAppBody.getSubId()+"</subId><subName>"+subAppBody.getNewSubName()+"</subName>" +
                        "<modifyTime>"+dateCreated+"</modifyTime></resp>";
            }else{
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated><represent>"+respDesc+"</represent></resp>";
            }
        }

        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_modify报文：" + respBody);
    }

    private Map<String, String> modify(SubAppBody subAppBody) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        Map map = new HashMap<>();

        //进行非空字段验证
        if(!Utils.notEmpty(subAppBody.getAppId())){
            respCode=ConstantsEnum.REST_APPID_NULL.getCode();
            respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
        }else if(!Utils.notEmpty(subAppBody.getSubId())){
            respCode=ConstantsEnum.REST_SUBID_NULL.getCode();
            respDesc=ConstantsEnum.REST_SUBID_NULL.getDesc();
        }else if(!Utils.notEmpty(subAppBody.getNewSubName())){
            respCode=ConstantsEnum.REST_NEWSUBNAME_NULL.getCode();
            respDesc=ConstantsEnum.REST_NEWSUBNAME_NULL.getDesc();
        }else if(subAppBody.getNewSubName().length()>6){
            respCode=ConstantsEnum.REST_NEWSUBNAME_LENGTH.getCode();
            respDesc=ConstantsEnum.REST_NEWSUBNAME_LENGTH.getDesc();
        }else{
            //进行应用存在性验证
            AppInfo appInfo=appInfoService.getAppInfoByAppid(subAppBody.getAppId());
            //应用信息不存在，返回对应的错误码报文
            if(appInfo==null){
                respCode=ConstantsEnum.REST_APPID_NO.getCode();
                respDesc=ConstantsEnum.REST_APPID_NO.getDesc();
            }else{
                //进行子账号存在性验证
                SubApp subAppCheck=subAppService.findSubAppBySubid(subAppBody.getSubId());
                if(subAppCheck==null){
                    respCode=ConstantsEnum.REST_SUBID_NO.getCode();
                    respDesc=ConstantsEnum.REST_SUBID_NO.getDesc();
                }else{
                    subAppCheck=null;//重置校验对象
                    //进行应用、子账号并存性验证
                    Map mapTmp=new HashMap();
                    mapTmp.put("appid",subAppBody.getAppId());
                    mapTmp.put("subid",subAppBody.getSubId());
                    subAppCheck=subAppService.findSubAppByMap(mapTmp);
                    if(subAppCheck==null){
                        respCode=ConstantsEnum.REST_SUBID_NOT_APPID.getCode();
                        respDesc=ConstantsEnum.REST_SUBID_NOT_APPID.getDesc();
                    }else{
                        //进行用户名存在性验证
                        mapTmp=new HashMap();
                        mapTmp.put("appid",subAppBody.getAppId());
                        mapTmp.put("subName",subAppBody.getNewSubName());
                        subAppCheck=subAppService.findSubAppByMap(mapTmp);
                        //该应用下用户名不存在，则修改，存在则返回已存在
                        if(subAppCheck==null){
                            SubApp subApp= new SubApp();
                            String subid = subAppBody.getSubId();
                            String subName = subAppBody.getNewSubName();
                            subApp.setSubid(subid);
                            subApp.setSubName(subName);
                            //修改子账号
                            subAppService.updateSubApp(subApp);

                            respCode=ConstantsEnum.REST_SUCCCODE.getCode();
                            respDesc=subid+"子账号修改成功";

                        }else{
                            respCode=ConstantsEnum.REST_NEWSUBNAME_EXIST.getCode();
                            respDesc=ConstantsEnum.REST_NEWSUBNAME_EXIST.getDesc();
                        }
                    }
                }
            }
        }

        map.put("respCode", respCode);
        map.put("respDesc", respDesc);
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/delete", consumes="application/json")
    public void deteleJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        //获取AS-deleteJson报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }

        String asBody=out.toString();
        logger.info("获取AS-deleteJson报文："+asBody);
        //去掉报文最外层的元素，以便实体直接转换
        asBody=asBody.substring(asBody.indexOf("{",1));
        asBody=asBody.substring(0,asBody.lastIndexOf("}"));
        logger.info("AS-deleteJson报文去掉最外层后："+asBody);
        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody=new Gson().fromJson(asBody,SubAppBody.class);

        // 进行删除子账号业务操作
        Map<String, String> map = delete(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        deleteReturn(true, respCode, dateCreated, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/delete", consumes="application/xml")
    public void deleteXml(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述

        //获取AS-deleteXml报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        String asBody=out.toString();
        logger.info("获取AS-deleteXml报文："+asBody);
        //解析字符串xml
        Map<String,String> mapTemp= ReadXmlUtil.readXML(asBody);
        //获取相应报文字段
        String appId = mapTemp.get("appId");
        String subId = mapTemp.get("subId");

        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody.setAppId(appId);
        subAppBody.setSubId(subId);

        // 进行删除子账号业务操作
        Map<String, String> map = delete(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        deleteReturn(false, respCode, dateCreated, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    private void deleteReturn(boolean isJson, String respCode, String dateCreated, String respDesc,
                              HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"}}";
            }else{
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"represent\":\""+respDesc+"\"}}";
            }
        } else {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated></resp>";
            }else{
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated><represent>"+respDesc+"</represent></resp>";
            }
        }

        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_delete报文：" + respBody);
    }

    private Map<String, String> delete(SubAppBody subAppBody) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        Map map = new HashMap<>();

        //进行非空字段验证
        if(!Utils.notEmpty(subAppBody.getAppId())){
            respCode=ConstantsEnum.REST_APPID_NULL.getCode();
            respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
        }else if(!Utils.notEmpty(subAppBody.getSubId())){
            respCode=ConstantsEnum.REST_SUBID_NULL.getCode();
            respDesc=ConstantsEnum.REST_SUBID_NULL.getDesc();
        }else if(subAppBody.getSubId().length()!=32){
            respCode=ConstantsEnum.REST_SUBID_LENGTH.getCode();
            respDesc=ConstantsEnum.REST_SUBID_LENGTH.getDesc();
        }else if(!Utils.isLetterDigit(subAppBody.getSubId())){
            respCode=ConstantsEnum.REST_SUBID_FORMAT.getCode();
            respDesc=ConstantsEnum.REST_SUBID_FORMAT.getDesc();
        }else{
            //进行应用存在性验证
            AppInfo appInfo=appInfoService.getAppInfoByAppid(subAppBody.getAppId());
            //应用信息不存在，返回对应的错误码报文
            if(appInfo==null){
                respCode=ConstantsEnum.REST_APPID_NO.getCode();
                respDesc=ConstantsEnum.REST_APPID_NO.getDesc();
            }else{
                //进行子账号存在性验证
                SubApp subAppCheck=subAppService.findSubAppBySubid(subAppBody.getSubId());
                if(subAppCheck==null){
                    respCode=ConstantsEnum.REST_SUBID_NO.getCode();
                    respDesc=ConstantsEnum.REST_SUBID_NO.getDesc();
                }else{
                    subAppCheck=null;//重置校验对象
                    //进行应用、子账号并存性验证
                    Map mapTmp=new HashMap();
                    mapTmp.put("appid",subAppBody.getAppId());
                    mapTmp.put("subid",subAppBody.getSubId());
                    subAppCheck=subAppService.findSubAppByMap(mapTmp);
                    if(subAppCheck==null){
                        respCode=ConstantsEnum.REST_SUBID_NOT_APPID.getCode();
                        respDesc=ConstantsEnum.REST_SUBID_NOT_APPID.getDesc();
                    }else{

                        SubApp subApp= new SubApp();
                        String appid = subAppBody.getAppId();
                        String subid = subAppBody.getSubId();
                        subApp.setAppid(appid);
                        subApp.setSubid(subid);
                        //删除子账号
                        subAppService.deleteSubApp(subApp);

                        respCode=ConstantsEnum.REST_SUCCCODE.getCode();
                        respDesc=subid+"子账号删除成功";

                    }
                }
            }
        }

        map.put("respCode", respCode);
        map.put("respDesc", respDesc);
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/query", consumes="application/json")
    public void queryJson(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述
        String respBody=null;//响应报文

        //获取AS-queryJson报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }

        String asBody=out.toString();
        logger.info("获取AS-queryJson报文："+asBody);
        //去掉报文最外层的元素，以便实体直接转换
        asBody=asBody.substring(asBody.indexOf("{",1));
        asBody=asBody.substring(0,asBody.lastIndexOf("}"));
        logger.info("AS-queryJson报文去掉最外层后："+asBody);
        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody=new Gson().fromJson(asBody,SubAppBody.class);

        // 进行查询子账号业务操作
        Map<String, String> map = query(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        queryReturn(true, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    @RequestMapping(method = RequestMethod.POST, value = "Subid/query", consumes="application/xml")
    public void queryXml(HttpServletRequest request, HttpServletResponse response,ModelMap model) throws Exception
    {
        Map<String,String> params =(Map<String,String> ) model.get("params");

        //生成请求时间
        String dateCreated= DateUtil.dateToStr(new Date(),DateUtil.DATE_TIME);
        String respCode=null;//响应码
        String respDesc=null;//相应描述
        String respBody=null;//响应报文

        //获取AS-queryXml报文
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   request.getInputStream().read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        String asBody=out.toString();
        logger.info("获取AS-queryXml报文："+asBody);
        //解析字符串xml
        Map<String,String> mapTemp= ReadXmlUtil.readXML(asBody);
        //获取相应报文字段
        String appId = mapTemp.get("appId");

        //将对应字段填充到报文对象
        SubAppBody subAppBody=new SubAppBody();
        subAppBody.setAppId(appId);

        // 进行查询子账号业务操作
        Map<String, String> map = query(subAppBody);
        respCode = map.get("respCode");
        respDesc = map.get("respDesc");
        logger.info(respDesc);

        // 同步返回AS报文
        queryReturn(false, respCode, dateCreated, subAppBody, respDesc, response);

        model.addAttribute("statusCode", 200);
    }

    private void queryReturn(boolean isJson, String respCode, String dateCreated, SubAppBody subAppBody, String respDesc,
                              HttpServletResponse response) throws Exception {
        String respBody = null;// 响应报文
        // 同步返回AS报文,返回对应json/xml报文
        if (isJson) {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"count\":\""+subAppBody.getCount()+"\",,\"subId\":\""+subAppBody.getSubId()+"\"," +
                        "\"subName\":\""+subAppBody.getSubName()+"\",\"createTime\":\""+subAppBody.getCreateTime()+"\"}}";
            }else{
                respBody="{\"resp\":{\"statusCode\":\""+respCode+"\",\"dateCreated\":\""+dateCreated+"\"," +
                        "\"represent\":\""+respDesc+"\"}}";
            }
        } else {
            if(ConstantsEnum.REST_SUCCCODE.getCode().equals(respCode)){
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated>" +
                        "<count>"+subAppBody.getCount()+"</count><subId>"+subAppBody.getSubId()+"</subId>" +
                        "<subName>"+subAppBody.getSubName()+"</subName>" +
                        "<createTime>"+subAppBody.getCreateTime()+"</createTime></resp>";
            }else{
                respBody="<?xml version=\"1.0\"?><resp><statusCode>"+respCode+"</statusCode>" +
                        "<dateCreated>"+dateCreated+"</dateCreated><represent>"+respDesc+"</represent></resp>";
            }
        }

        response.setCharacterEncoding("utf-8");// 避免中文乱码
        response.getWriter().print(respBody);
        logger.info("返回AS_query报文：" + respBody);
    }

    private Map<String, String> query(SubAppBody subAppBody) throws Exception {
        String respCode = null;// 响应码
        String respDesc = null;// 相应描述
        Map map = new HashMap<>();

        //进行非空字段验证
        if(!Utils.notEmpty(subAppBody.getAppId())){
            respCode=ConstantsEnum.REST_APPID_NULL.getCode();
            respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
        }else{
            //进行应用存在性验证
            AppInfo appInfo=appInfoService.getAppInfoByAppid(subAppBody.getAppId());
            //应用信息不存在，返回对应的错误码报文
            if(appInfo==null){
                respCode=ConstantsEnum.REST_APPID_NO.getCode();
                respDesc=ConstantsEnum.REST_APPID_NO.getDesc();
            }else{
                String appid = subAppBody.getAppId();
                //获取应用的子账号列表集合
                List<SubApp> subAppList=subAppService.findSubAppListByAppid(appid);

                Integer count=0;//应用下子账号数量
                String subId="";//子账号ID（列表形式返回）
                String subName="";//子账号名称（列表形式返回）
                String createTime="";//子账号创建时间（列表形式返回）
                if(subAppList==null || subAppList.size()==0){
                    count=0;
                    subId="";
                    subName="";
                    createTime="";
                }else{
                    count=subAppList.size();
                    for (SubApp subApp:subAppList){
                        //拼接subId和subName列表，逗号隔开
                        subId=subId+subApp.getSubid()+",";
                        subName=subName+subApp.getSubName()+",";
                        createTime=createTime+DateUtil.dateToStr(subApp.getCreateDate(),DateUtil.DATE_TIME)+",";
                    }
                    //去掉最后一个逗号
                    subId=subId.substring(0,subId.length()-1);
                    subName=subName.substring(0,subName.length()-1);
                    createTime=createTime.substring(0,createTime.length()-1);
                }
                //填充相应值，用于成功返回
                subAppBody.setSubId(subId);
                subAppBody.setSubName(subName);
                subAppBody.setCreateTime(createTime);
                subAppBody.setCount(count.toString());

                respCode=ConstantsEnum.REST_SUCCCODE.getCode();
                respDesc=appid+"应用下所有子账号查询成功";

            }
        }

        map.put("respCode", respCode);
        map.put("respDesc", respDesc);
        return map;
    }

}
