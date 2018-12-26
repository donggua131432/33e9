package com.e9cloud.pcweb.sip.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.RelayInfoService;
import com.e9cloud.mybatis.service.SipNumService;
import com.e9cloud.mybatis.service.SubNumService;
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
 * 子帐号号码池管理
 */

@Controller
@RequestMapping("/subNumPool")

public class SubNumPoolController extends BaseController {
    @Autowired
    private SubNumService subNumService;

    @Autowired
    private RelayInfoService relayInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private SipNumService sipNumService;


    /**
     * 子帐号号码池管理界面
     * @return
     */
    @RequestMapping("/subNumPoolMgr")
    public String subNumPoolMgr(HttpServletRequest request, Model model){
        String subid = request.getParameter("subid");
        SipRelayInfo subApp = subNumService.getSubApp(subid);
        model.addAttribute("subApp", subApp);
        return "sip/subNumPoolMgr";
    }


    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/addSubNumber")
    public String addSubNumber(HttpServletRequest request, Model model){
        model.addAttribute("subid", request.getParameter("subid"));
        return "sip/addSubNumber";
    }


    /**
     * 添加子帐号号码
     * @param
     * @return
     */
    @RequestMapping("/addNumbers")
    @ResponseBody
    public JSonMessageSubmit addNumbers(SipRelayNumPool sipRelayNumPool,  String numbers, Integer maxConcurrent,HttpServletRequest request){

        logger.info("--------------------------------SubNumberController regionNumUpload start--------------------------------");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        //添加隐私号信息
        String[] numArr = numbers.split(",");
//        List<SubAppNumPool> SubNumResultLsit = new ArrayList<>();
        if(numArr.length > 0) {
            for (int i = 0; i < numArr.length; i++) {
                isNum = pattern.matcher(numArr[i]);
                //判断号码是否合法
                if (isNum.matches()) {
                    SipRelayInfo sipRelayInfo = relayInfoService.getRelayInfoBySubid(sipRelayNumPool.getSubid());
                    SipRelayNumPool SubNumber = new SipRelayNumPool();
                    SubNumber.setSubid(sipRelayNumPool.getSubid());
                    Map map = new HashMap<String,String>();
                    map.put("number",numArr[i]);
                    map.put("type","0");
                    List<SipNumPool> sipPNumPools = sipNumService.getSipNumPoolByNumber(map);
                    //全局号码池有该号码
                    if(sipPNumPools.size()!=0){
                        //同一个应用
                        if(sipPNumPools.get(0).getAppid().equals(sipRelayInfo.getAppid())){
                            map.put("number",numArr[i]);
                            map.put("type","1");
                            map.put("appid",sipPNumPools.get(0).getAppid());
                            List<SipNumPool> newSipNumPools = sipNumService.getSipNumPoolByNumber(map);
                            if(newSipNumPools.size()!=0){
                                Boolean flag = false;//设置标记，等全部判断后才进行入库
                                for(SipNumPool sipNumPool:newSipNumPools) {
                                    //同一个子账号的号码不可重复
                                    if (sipRelayNumPool.getSubid().equals(sipNumPool.getSubid())) {
                                        flag = false;
                                        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                                        return new JSonMessageSubmit("1", "该子账号已存在该号码！");
                                    } else {
                                        flag = true;
                                    }
                                }
                                //判断flag,true表示同一应用下的子账号号码，可入库
                                if(flag){
                                    SubNumber.setNumber(numArr[i]);
                                    SubNumber.setMaxConcurrent(maxConcurrent);
                                    SubNumber.setCreateTime(new Date());
                                }

                            }else {
                                //同一个应用下子账号不存在该号码，入库
                                SubNumber.setNumber(numArr[i]);
                                SubNumber.setMaxConcurrent(maxConcurrent);
                                SubNumber.setCreateTime(new Date());
                            }
                        }else{
                            //不同应用，不可重复
                            AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipPNumPools.get(0).getAppid());
                            return new JSonMessageSubmit("1", numArr[i] + "号码已存在，信息添加失败！\n错误类型：" +
                                    "号码被‘" + appInfo.getAppName() + "’应用下的全局号码池使用!");
                        }
                    }else{
                        //全局号码池无该号码，判断子账号
                        map.put("number",numArr[i]);
                        map.put("type","1");
                        List<SipNumPool> sipCNumPools = sipNumService.getSipNumPoolByNumber(map);
                        if(sipCNumPools.size()!=0){
                            Boolean flag = false;//设置标记，等全部判断后才进行入库
                            for(SipNumPool sipNumPool:sipCNumPools){
                                //如果是同一个应用下的子账号号码
                                if(sipNumPool.getAppid().equals(sipRelayInfo.getAppid())){
                                    //同一个子账号的号码不可重复
                                    if(sipRelayNumPool.getSubid().equals(sipNumPool.getSubid())){
                                        flag=false;
                                        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                                        return new JSonMessageSubmit("1", "该子账号已存在该号码！");
                                    }else{
                                        flag=true;
                                    }
                                }else{
                                    //不同应用下的子账号号码不可重复
                                    flag=false;
                                    AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                                    return new JSonMessageSubmit("1", numArr[i]+"号码已存在，信息添加失败！\n" +
                                            "错误类型：号码被‘"+appInfo.getAppName()+"’应用下的子账号使用!");
                                }
                            }
                            //判断flag,true表示同一应用下的子账号号码，可入库
                            if(flag){
                                SubNumber.setNumber(numArr[i]);
                                SubNumber.setMaxConcurrent(maxConcurrent);
                                SubNumber.setCreateTime(new Date());
                            }
                        }else{
                            //子账号不存在该号码，直接入库
                            SubNumber.setNumber(numArr[i]);
                            SubNumber.setMaxConcurrent(maxConcurrent);
                            SubNumber.setCreateTime(new Date());

                        }
                    }
                    subNumService.addSubNumberPool(SubNumber);
                } else {
                    return new JSonMessageSubmit("1", numArr[i]+"号码格式错误，信息添加失败！");
                }
            }
        }
        return new JSonMessageSubmit("0","信息添加成功！");
    }

    /**
     * 子帐号号码池列表
     * @param page
     * @return
     */
    @RequestMapping("/getSubNumberList")
    @ResponseBody
    public PageWrapper getSubNumberList(Page page){
        logger.info("----SubNumPoolController getSubNumberList start-------");
        return subNumService.getSubNumberList(page);
    }

    // 修改并发数页面
    @RequestMapping(value = "toUpdateMax", method = RequestMethod.GET)
    public String toEditAppInfo(String id, Model model) {

        SipRelayNumPool sipRelayNumPool = subNumService.getSubNumInfoById(Integer.parseInt(id));
        model.addAttribute("sipRelayNumPool", sipRelayNumPool);
        return "sip/sub_num_update";
    }
    // 修改子帐号号码池并发数
    @RequestMapping(value = "updateMax", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateApp(SipRelayNumPool sipRelayNumPool,String maxCon) {
        if (Tools.isNullStr(maxCon)){
            sipRelayNumPool.setMaxConcurrent(0);
        }else {
            sipRelayNumPool.setMaxConcurrent(Integer.parseInt(maxCon));
        }
        subNumService.updateSubNumber(sipRelayNumPool);

        return new JSonMessage("ok", "修改并发数成功！");
    }

    /**
     * 删除Sub号码
     * @param request
     * @return
     */
    @RequestMapping("/deleteSubNumber")
    @ResponseBody
    public JSonMessageSubmit deleteSubNumber(HttpServletRequest request){

        SipRelayNumPool sipRelayNumPool = new SipRelayNumPool();
        sipRelayNumPool.setId(Integer.parseInt(request.getParameter("id")));

        //判断是否还存在此条数据
        boolean flag = subNumService.checkSubNumUnique(sipRelayNumPool);
        if(flag == false){
            subNumService.deleteSubNumber(sipRelayNumPool);
            return new JSonMessageSubmit("0","删除数据成功！");
        }else{
            return new JSonMessageSubmit("1","该数据信息已删除！");
        }
    }


    /**
     * 导入号码
     * @return
     */
    @RequestMapping("/importSubNumber")
    public String importSubNumber(HttpServletRequest request, Model model){

        model.addAttribute("subid", request.getParameter("subid"));
        return "sip/importSubNumbers";
    }

    /**
     * 上传Sub Number Excel文件信息
     * @param SubNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/SubNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody

    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile SubNumFile, HttpServletRequest request){

        if(SubNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<SipRelayNumPool> insertSubNumErrorList = saveExcel(SubNumFile, request);
                if (insertSubNumErrorList.size() > 0) {
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
     * @param SubNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<SipRelayNumPool> saveExcel(MultipartFile SubNumFile, HttpServletRequest request) throws Exception{
        List<SipRelayNumPool> SubNumList = new ArrayList<>();
        InputStream is = SubNumFile.getInputStream();
        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置Sub选号信息
            SipRelayNumPool SubNumber = new SipRelayNumPool();
            SubNumber.setSubid(request.getParameter("subid"));
            SubNumber.setMaxConcurrent(Integer.parseInt(arrayStr[0]));
            SubNumber.setNumber(arrayStr[1]);
            SubNumber.setCreateTime(new Date());
            SubNumList.add(SubNumber);
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<SipRelayNumPool> insertSubNumErrorList = new ArrayList<>();
        for (SipRelayNumPool SubNumResult : SubNumList) {

            isNum = pattern.matcher(SubNumResult.getNumber());
            //判断号码是否合法
            if (isNum.matches()) {
                SipRelayInfo sipRelayInfo = relayInfoService.getRelayInfoBySubid(SubNumResult.getSubid());
                SipRelayNumPool SubNumber = new SipRelayNumPool();
                SubNumber.setSubid(SubNumResult.getSubid());
                Map map = new HashMap<String,String>();
                map.put("number",SubNumResult.getNumber());
                map.put("type","0");
                List<SipNumPool> sipPNumPools = sipNumService.getSipNumPoolByNumber(map);
                //全局号码池有该号码
                if(sipPNumPools.size()!=0){
                    //同一个应用
                    if(sipPNumPools.get(0).getAppid().equals(sipRelayInfo.getAppid())){
                        map.put("number",SubNumResult.getNumber());
                        map.put("type","1");
                        map.put("appid",sipPNumPools.get(0).getAppid());
                        List<SipNumPool> newSipNumPools = sipNumService.getSipNumPoolByNumber(map);
                        if(newSipNumPools.size()!=0){
                            Boolean flag = false;//设置标记，等全部判断后才进行入库
                            for(SipNumPool sipNumPool:newSipNumPools) {
                                //同一个子账号的号码不可重复
                                if (SubNumResult.getSubid().equals(sipNumPool.getSubid())) {
                                    flag = false;
                                    AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                                    SubNumResult.setSid(appInfo.getSid());
                                    insertSubNumErrorList.add(SubNumResult);
                                } else {
                                    flag = true;
                                }
                            }
                            //判断flag,true表示同一应用不同子账号，可入库
                            if(flag){
                                SubNumber.setNumber(SubNumResult.getNumber());
                                SubNumber.setMaxConcurrent(SubNumResult.getMaxConcurrent());
                                SubNumber.setCreateTime(new Date());
                                subNumService.addSubNumberPool(SubNumber);
                            }

                        }else{
                            //同一应用不同子账号，入库
                            SubNumber.setNumber(SubNumResult.getNumber());
                            SubNumber.setMaxConcurrent(SubNumResult.getMaxConcurrent());
                            SubNumber.setCreateTime(new Date());
                            subNumService.addSubNumberPool(SubNumber);
                        }

                    }else{
                        //不同应用，不可重复
                        //将错误号码添加至错误的集合中
                        AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipPNumPools.get(0).getAppid());
                        SubNumResult.setSid(appInfo.getSid());
                        insertSubNumErrorList.add(SubNumResult);
                    }
                }else{
                    //全局号码池无该号码，判断子账号
                    map.put("number",SubNumResult.getNumber());
                    map.put("type","1");
                    List<SipNumPool> sipCNumPools = sipNumService.getSipNumPoolByNumber(map);
                    if(sipCNumPools.size()!=0){
                        Boolean flag = false;//设置标记，等全部判断后才进行入库
                        for(SipNumPool sipNumPool:sipCNumPools){
                            AppInfo appInfo = appInfoService.getSipAppInfoByAppId(sipNumPool.getAppid());
                            //如果是同一个应用下的子账号号码
                            if(sipNumPool.getAppid().equals(sipRelayInfo.getAppid())){
                                //同一个子账号的号码不可重复
                                if(SubNumResult.getSubid().equals(sipNumPool.getSubid())){
                                    flag=false;
                                    //将错误号码添加至错误的集合中
                                    SubNumResult.setSid(appInfo.getSid());
                                    insertSubNumErrorList.add(SubNumResult);
                                    break;
                                }else{
                                    flag=true;
                                }
                            }else{
                                //不同应用下的子账号号码不可重复
                                flag=false;
                                //将错误号码添加至错误的集合中

                                SubNumResult.setSid(appInfo.getSid());
                                insertSubNumErrorList.add(SubNumResult);
                                break;
                            }
                        }
                        //判断flag,true表示同一应用下的子账号号码，可入库，false表示不同应用下的子账号号码，不可以入库
                        if(flag){
                            SubNumber.setNumber(SubNumResult.getNumber());
                            SubNumber.setMaxConcurrent(SubNumResult.getMaxConcurrent());
                            SubNumber.setCreateTime(new Date());
                            subNumService.addSubNumberPool(SubNumber);
                        }
                    }else{
                        //子账号不存在该号码，直接入库
                        SubNumber.setNumber(SubNumResult.getNumber());
                        SubNumber.setMaxConcurrent(SubNumResult.getMaxConcurrent());
                        SubNumber.setCreateTime(new Date());
                        subNumService.addSubNumberPool(SubNumber);
                    }
                }

            }else{
                //将错误号码添加至错误的集合中
                insertSubNumErrorList.add(SubNumResult);
            }
        }

        request.getSession().setAttribute("insertSubNumErrorList",insertSubNumErrorList);
        return insertSubNumErrorList;
    }

    /**
     * 下载SubNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorSubNum")
    public ModelAndView downLoadErrorSubNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SubNumberPoolController downLoadErrorSubNum start--------------------------------");
        List<SipRelayNumPool> insertSubNumErrorList = (List<SipRelayNumPool>) request.getSession().getAttribute("insertSubNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertSubNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<SipRelayNumPool> insertSubNumErrorList) {
        logger.info("--------------------------------SubNumberPoolController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (SipRelayNumPool SubAppNumPool : insertSubNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("MaxConcurrent",SubAppNumPool.getMaxConcurrent().toString());
            excelMap.put("number", SubAppNumPool.getNumber());
            excelMap.put("sid",SubAppNumPool.getSid());
//            excelMap.put("errorType", SubAppNumPool.getErrorType());
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
        contentMap.put("title","错误Sub号码信息");
        contentMap.put("excelName", "错误Sub号码信息");
        return contentMap;
    }


    /**
     * 导出子账号号码池
     *
     * @return
     */
    @RequestMapping(value = "downloadMaskNumber", method = RequestMethod.GET)
    public ModelAndView downloadMaskNumber(HttpServletRequest request, Model model, Page page) {

        List<Map<String, Object>> totals = subNumService.getSubNumberListDownload(page);
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
        map.put("title", "Sip子账号号码池列表");
        map.put("excelName","Sip子账号号码池列表");

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
        subNumService.deleteStatusBylink(aa);
        map.put("code","00");
        return map;
    }
}
