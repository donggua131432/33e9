package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lixin on 2016/7/14.
 */

@Controller
@RequestMapping("/sipNumPool")

public class SipNumPoolController extends BaseController {
    @Autowired
    private MaskLineService maskLineService;

    @Autowired
    private SipNumService sipNumService;

    // 应用相关 Service
    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private SubNumService subNumService;

    @Autowired
    private RelayInfoService relayInfoService;

    /**
     * 添加sip号码
     * @return
     */
    @RequestMapping("/addSipNumber")
    public String addSipNumber(HttpServletRequest request, Model model){
        model.addAttribute("appid", request.getParameter("appid"));
        return "sip/addSipNumber";
    }


    /**
     * 批量添加隐私号码
     * @param sipAppNumPool
     * @return
     */
    @RequestMapping("/addNumbers")
    @ResponseBody
    public JSonMessageSubmit addNumbers(SipAppNumPool sipAppNumPool,  String numbers, Integer maxConcurrent,HttpServletRequest request){

        logger.info("--------------------------------SipNumberController regionNumUpload start--------------------------------");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        //添加隐私号信息
        String[] numArr = numbers.split(",");
//        List<SipAppNumPool> sipNumResultLsit = new ArrayList<>();
        if(numArr.length > 0) {
            for (int i = 0; i < numArr.length; i++) {
                isNum = pattern.matcher(numArr[i]);
                //判断号码是否合法
                if (isNum.matches()) {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("number",numArr[i]);
                    map.put("type","0");
                    List<SipNumPool> sipPNumPools = sipNumService.getSipNumPoolByNumber(map);
                    //如果全局号码存在，则不能添加
                    if(sipPNumPools.size()!=0){
                        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipPNumPools.get(0).getAppid());
                        return new JSonMessageSubmit("1", numArr[i]+"号码已存在，信息添加失败！\n" +
                                "错误类型：号码被‘"+appInfo.getAppName()+"’应用使用!");
                    }else{
                        SipAppNumPool sipNumber = new SipAppNumPool();
                        sipNumber.setAppid(sipAppNumPool.getAppid());
                        //全局号码不存在情况
                        map.put("number",numArr[i]);
                        map.put("type","1");
                        List<SipNumPool> sipCNumPools = sipNumService.getSipNumPoolByNumber(map);
                        //子账号有该号码
                        if(sipCNumPools.size()!=0){
                            Boolean flag = false;//设置标记，等全部判断后才进行入库
                            for(SipNumPool sipNumPool:sipCNumPools){
                                //如果是同一个应用下的子账号号码，可以添加
                                if(sipNumPool.getAppid().equals(sipAppNumPool.getAppid())){
                                    flag=true;
                                }else{
                                    flag=false;
                                    AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                                    return new JSonMessageSubmit("1", numArr[i]+"号码已存在，信息添加失败！\n" +
                                            "错误类型：号码被‘"+appInfo.getAppName()+"’应用下的子账号使用!");
                                }
                            }
                            //判断flag,true表示同一应用下的子账号号码，可入库，false表示不同应用下的子账号号码，不可以入库
                            if(flag){
                                sipNumber.setNumber(numArr[i]);
                                sipNumber.setMaxConcurrent(maxConcurrent);
                                sipNumber.setCreateTime(new Date());
                            }
                        }else{
                            //子账号无该号码，直接入库
                            sipNumber.setNumber(numArr[i]);
                            sipNumber.setMaxConcurrent(maxConcurrent);
                            sipNumber.setCreateTime(new Date());
                        }
                        sipNumService.addSIPNumberPool(sipNumber);
                    }

                } else {
                    return new JSonMessageSubmit("1", numArr[i]+"号码格式错误，信息添加失败！");
                }
            }
        }
        return new JSonMessageSubmit("0","信息添加成功！");
    }

    /**
     * 添加sip号码
     * @return
     */
    @RequestMapping("/goNumlist")
    public String goNumlist(HttpServletRequest request, Model model){
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("managerType", request.getParameter("managerType"));
        return "sip/selectSipNum";
    }

    /**
     * 获取隐私号信息列表
     * @param page
     * @return
     */
    @RequestMapping("/getSipNumberList")
    @ResponseBody
    public PageWrapper getSipNumberList(Page page){
        logger.info("----SipNumPoolController getSipNumberList start-------");
        return sipNumService.getSipNumberList(page);
    }

    // 修改并发数页面
    @RequestMapping(value = "toUpdateMax", method = RequestMethod.GET)
    public String toEditAppInfo(String id, Model model) {

        SipAppNumPool sipAppNumPool = sipNumService.getSipNumInfoById(Integer.parseInt(id));
        model.addAttribute("sipAppNumPool", sipAppNumPool);
        return "sip/sip_num_update";
    }
    // 修改sip并发数
    @RequestMapping(value = "updateMax", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateApp(HttpServletRequest request,SipAppNumPool sipAppNumPool,String maxCon) {

        if(Tools.isNullStr(maxCon)){
            sipAppNumPool.setMaxConcurrent(0);
        }else {
            sipAppNumPool.setMaxConcurrent(Integer.parseInt(maxCon));
        }

        sipNumService.updateSipNumber(sipAppNumPool);

        return new JSonMessage("ok", "修改并发数成功！");
    }

    /**
     * 删除SIP号码
     * @param request
     * @return
     */
    @RequestMapping("/deleteSipNumber")
    @ResponseBody
    public JSonMessageSubmit deleteSipNumber(HttpServletRequest request){

        SipAppNumPool sipAppNumPool = new SipAppNumPool();
        sipAppNumPool.setId(Integer.parseInt(request.getParameter("id")));

        //判断是否还存在此条数据
        boolean flag = sipNumService.checkSipNumUnique(sipAppNumPool);
        if(flag == false){
            sipNumService.deleteSipNumber(sipAppNumPool);
            return new JSonMessageSubmit("0","删除数据成功！");
        }else{
            return new JSonMessageSubmit("1","该数据信息已删除！");
        }
    }


    /**
     * 导入号码
     * @return
     */
    @RequestMapping("/importSipNumber")
    public String importSipNumber(HttpServletRequest request, Model model){

        model.addAttribute("appid", request.getParameter("appid"));
        return "sip/importSipNumbers";
    }

    /**
     * 上传SIP Number Excel文件信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/sipNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody

    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile sipNumFile, HttpServletRequest request){

        if(sipNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<SipAppNumPool> insertSipNumErrorList = saveExcel(sipNumFile, request);
                if (insertSipNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<SipAppNumPool> saveExcel(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<SipAppNumPool> sipNumList = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();
        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置SIP选号信息
            SipAppNumPool sipNumber = new SipAppNumPool();
            sipNumber.setAppid(request.getParameter("appid"));
            sipNumber.setMaxConcurrent(Integer.parseInt(arrayStr[0]));
            sipNumber.setNumber(arrayStr[1]);
            sipNumber.setCreateTime(new Date());
            sipNumList.add(sipNumber);
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<SipAppNumPool> insertSipNumErrorList = new ArrayList<>();
        for (SipAppNumPool sipNumResult : sipNumList) {

            isNum = pattern.matcher(sipNumResult.getNumber());
            //判断号码是否合法
            if (isNum.matches()) {
                Map map = new HashMap<String,String>();
                map.put("number",sipNumResult.getNumber());
                map.put("type","0");
                List<SipNumPool> sipPNumPools = sipNumService.getSipNumPoolByNumber(map);
                //如果全局号码存在，则不能添加
                if(sipPNumPools.size()!=0){
                    AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipPNumPools.get(0).getAppid());
                    sipNumResult.setSid(appInfo.getSid());
                    insertSipNumErrorList.add(sipNumResult);
                }else{
                    SipAppNumPool sipNumber = new SipAppNumPool();
                    sipNumber.setAppid(sipNumResult.getAppid());
                    //全局号码不存在情况
                    map.put("number",sipNumResult.getNumber());
                    map.put("type","1");
                    List<SipNumPool> sipCNumPools = sipNumService.getSipNumPoolByNumber(map);
                    if(sipCNumPools.size()!=0){
                        Boolean flag = false;//设置标记，等全部判断后才进行入库
                        for(int i = 0;i < sipCNumPools.size();i++){
                            //如果是同一个应用下的子账号号码，可以添加
                            if(sipCNumPools.get(i).getAppid().equals(sipNumResult.getAppid())){
                                flag=true;
                            }else {
                                flag = false;
                                //将错误号码添加至错误的集合中
                                AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumResult.getAppid());
                                sipNumResult.setSid(appInfo.getSid());
                                insertSipNumErrorList.add(sipNumResult);
                                break;
                            }
                        }
                        //判断flag,true表示同一应用下的子账号号码，可入库
                        if(flag){
                            sipNumber.setNumber(sipNumResult.getNumber());
                            sipNumber.setMaxConcurrent(sipNumResult.getMaxConcurrent());
                            sipNumber.setCreateTime(new Date());
                            sipNumService.addSIPNumberPool(sipNumber);

                        }
                    }else {
                        sipNumber.setNumber(sipNumResult.getNumber());
                        sipNumber.setMaxConcurrent(sipNumResult.getMaxConcurrent());
                        sipNumber.setCreateTime(new Date());
                        sipNumService.addSIPNumberPool(sipNumber);
                    }

                }
            }else{
                //将错误号码添加至错误的集合中
                sipNumResult.setSid("");
                insertSipNumErrorList.add(sipNumResult);
            }
        }

        request.getSession().setAttribute("insertSipNumErrorList",insertSipNumErrorList);
        return insertSipNumErrorList;
    }

    /**
     * 下载SIPNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorSipNum")
    public ModelAndView downLoadErrorSipNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SipNumberPoolController downLoadErrorSipNum start--------------------------------");
        List<SipAppNumPool> insertSipNumErrorList = (List<SipAppNumPool>) request.getSession().getAttribute("insertSipNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertSipNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<SipAppNumPool> insertSipNumErrorList) {
        logger.info("--------------------------------SipNumberPoolController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (SipAppNumPool sipAppNumPool : insertSipNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("MaxConcurrent",sipAppNumPool.getMaxConcurrent().toString());
            excelMap.put("number", sipAppNumPool.getNumber());
            excelMap.put("sid", sipAppNumPool.getSid());
//            excelMap.put("errorType", sipAppNumPool.getErrorType());
//          excelMap.put("flag", "错误号码");
            mapList.add(excelMap);
        }

        List titles = new ArrayList<String>();
        titles.add("最大并发数");
        titles.add("号码");
        titles.add("占用该号码的账号的accountSid");
//        titles.add("错误类型");
//      titles.add("信息");

        List columns = new ArrayList<String>();
        columns.add("MaxConcurrent");
        columns.add("number");
        columns.add("sid");
//        columns.add("errorType");
//      columns.add("flag");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","错误SIP号码信息");
        contentMap.put("excelName", "错误SIP号码信息");
        return contentMap;
    }

    /**
     * 导出全局号码池
     *
     * @return
     */
    @RequestMapping(value = "downloadMaskNumber", method = RequestMethod.GET)
    public ModelAndView downloadMaskNumber(HttpServletRequest request, Model model, Page page) {

        List<Map<String, Object>> totals = sipNumService.getSipNumberListDownload(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {

            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO",String.valueOf(total.get("rowNO")));
                map.put("number", String.valueOf(total.get("number")));
                if("".equals(total.get("create_time")) || null == total.get("create_time")){
                    map.put("create_time", "");
                } else {
                    map.put("create_time", String.valueOf(total.get("create_time").toString().substring(0,19)));
                }

                map.put("max_concurrent", String.valueOf(total.get("max_concurrent")));

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("序号");
        titles.add("号码");
        titles.add("创建时间");
        titles.add("最大并发数");

        List<String> columns = new ArrayList<String>();

        columns.add("rowNO");
        columns.add("number");
        columns.add("create_time");
        columns.add("max_concurrent");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "Sip全局号码池列表");
        map.put("excelName","Sip全局号码池列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("Sip全局号码池列表", "Sip全局号码池列表导出。导出内容的查询条件为：" + " 应用ID： "
                        + params.get("appid") + "，开始时间："
                        + Tools.formatDate(page.getTimemax()) + "，结束时间：" + Tools.formatDate(page.getTimemin())
                , LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

    //批量删除全局号码
    @RequestMapping("deleteNum")
    @ResponseBody
    public Map<String,String> deleteNum (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        String[] aa = strId.split(",");
        sipNumService.deleteStatusBylink(aa);
        map.put("code","00");
        return map;
    }

    //跳转号码池列表
    @RequestMapping(value = "toSipNumList", method = RequestMethod.GET)
    public String toAppList() {
        return "sip/sipNumList";
    }

    //号码池列表
    @RequestMapping(value = "pageSipNumList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageSipNumList(Page page) {
        return sipNumService.getSipNumList(page);
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadSipNumList", method = RequestMethod.GET)
    public ModelAndView downloadSipNumList(HttpServletRequest request, Model model,Page page) {
        List<Map<String, Object>> totals =  sipNumService.downloadSipNumList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO",String.valueOf(total.get("rowNO")));
                map.put("number",String.valueOf(total.get("number")));
                map.put("companyName", String.valueOf(total.get("companyName")));
                map.put("accountId", String.valueOf(total.get("accountId")));
                map.put("subName", String.valueOf(total.get("subName")));
                map.put("createTime", String.valueOf(total.get("createTime")));

                if("".equals(total.get("createDate")) || null == total.get("createDate")){
                    map.put("createDate", "");
                }
                else {
                    map.put("createDate", String.valueOf(total.get("createDate").toString().substring(0,19)));
                }

                if("0".equals(total.get("type"))){
                    map.put("type","全局");

                }
                if("1".equals(total.get("type"))){
                    map.put("type","子账号");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("序号");
        titles.add("号码");
        titles.add("所属客户");
        titles.add("account ID");
        titles.add("号码类型");
        titles.add("子账号名称");
        titles.add("创建时间");

        List<String> columns = new ArrayList<String>();

        columns.add("rowNO");
        columns.add("number");
        columns.add("companyName");
        columns.add("accountId");
        columns.add("type");
        columns.add("subName");
        columns.add("createTime");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "Sip号码池列表");
        map.put("excelName","Sip号码池列表");

        Map<String, Object> params = page.getParams();
        LogUtil.log("Sip号码池列表", "Sip号码池列表导出一条记录。导出内容的查询条件为：" + " account ID： "
                + params.get("accountId") + "，客户名称：" + params.get("companyName") + "，number：" + params.get("number") + "，号码类型："
                + params.get("type") + "，子账号名称：" + params.get("subName"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }
}
