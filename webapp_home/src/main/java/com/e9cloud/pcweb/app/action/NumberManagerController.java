package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.NumberManagerService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dukai on 2016/3/28.
 */
@Controller
@RequestMapping("/numMgr")
public class NumberManagerController extends BaseController{

    @Autowired
    private AppService appService;

    @Autowired
    private NumberManagerService numberManagerService;

    @RequestMapping("/index")
    public String index(){
        return "app/numberAppList";
    }

    /**
     * 获取区域分页信息
     * @param page
     * @return
     */
    @RequestMapping("/pageNumber")
    @ResponseBody
    public PageWrapper getNumberPageList(HttpServletRequest request, Page page){
        logger.info("------------------------------------------------GET TrafficDispatchController getDispatchPageList START----------------------------------------------------------------");
        String appid = request.getParameter("appid");
        page.addParams("appid", appid);
        return numberManagerService.pageNumberManager(page);
    }

    @RequestMapping("/appNumberManager")
    public String appNumberManager(HttpServletRequest request, Model model, AppNumber appNumber){
        logger.info("------------------------------------------------NumberManagerController appNumberManager START----------------------------------------------------------------");
        String appid = request.getParameter("appid");
        String appName = request.getParameter("appName");

        if(appid == null || appName == null){
            return "app/numberAppList";
        }

        List<AppNumber> appNumberList = numberManagerService.findAppNumberList(appNumber);
        List<AppNumber> pendingList = new ArrayList<>();
        List<AppNumber> auditPassList = new ArrayList<>();
        List<AppNumber> auditNoPassList = new ArrayList<>();
        for (AppNumber appNumberResult: appNumberList) {
            if("00".equals(appNumberResult.getNumberStatus())){
                pendingList.add(appNumberResult);
            }else if("01".equals(appNumberResult.getNumberStatus())){
                auditPassList.add(appNumberResult);
            }else if("02".equals(appNumberResult.getNumberStatus())){
                auditNoPassList.add(appNumberResult);
            }
        }

        AppNumberRest appNumberRest = new AppNumberRest();
        appNumberRest.setAppId(request.getParameter("appid"));

        model.addAttribute("pendingCount",pendingList.size());
        model.addAttribute("auditPassCount",numberManagerService.selectAppNumberRest(appNumberRest).size());
        model.addAttribute("auditNoPassCount",auditNoPassList.size());

        model.addAttribute("pendingList",pendingList);
        model.addAttribute("auditPassList",auditPassList);
        model.addAttribute("auditNoPassList",auditNoPassList);

        model.addAttribute("appid",appid);
        model.addAttribute("appName",appName);
        return "app/numberManager";
    }

    @RequestMapping("/appNumberManager1")
    public String appNumberManager1(HttpServletRequest request, Model model, AppNumber appNumber){
        logger.info("------------------------------------------------NumberManagerController appNumberManager START----------------------------------------------------------------");
        String appid = request.getParameter("appid");
        String appName = request.getParameter("appName");

        if(appid == null || appName == null){
            return "app/numberAppList";
        }
        model.addAttribute("appid",appid);
        model.addAttribute("appName",appName);
        return "app/numberManager1";
    }

    /**
     * app应用列表
     */
    @RequestMapping(value = "/getAppList", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper getAppList(HttpServletRequest request, AppInfo app, Page page) {
        logger.info("------------------------------------------------NumberManagerController getAppList START----------------------------------------------------------------");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        PageWrapper pageWrapper = null;
        Map param = new HashMap<String,String>();
        param.put("sid",account.getSid());
        try{
            param.put("appName",app.getAppName());
            param.put("appType",app.getAppType());
            param.put("busType", "02");
            param.put("status", "00");
            int count = appService.findAppListCountByMap(param);
            pageWrapper = new PageWrapper(page.getPage(), page.getPageSize(), count, null);
            param.put("start",pageWrapper.getFromIndex());
            param.put("pageSize",page.getPageSize());
            List<AppInfo> appList = appService.findAppListByMap(param);
            pageWrapper.setRows(appList);
        }catch (Exception e){
            logger.info("--------NumberManagerController getAppList error----",e);
        }
        return  pageWrapper;
    }

    @RequestMapping("/deleteAppNumber")
    @ResponseBody
    public Map<String, Object> deleteAppNumber(HttpServletRequest request){
        logger.info("------------------------------------------------NumberManagerController deleteAppNumber START----------------------------------------------------------------");
        Map<String,Object> map = new HashMap<>();

        String[] numIdStr = request.getParameter("numId").split(",");
        Long[] ids = new Long[numIdStr.length];
        for (int i=0; i<numIdStr.length; i++) {
            ids[i] = new Long(numIdStr[i]);
        }

        //Long[] ids = {Long.parseLong(request.getParameter("numId"))};
        if(ids.length > 0){
            numberManagerService.deleteAppNumberRest(ids);
            numberManagerService.deleteAppNumbers(ids);
            map.put("status","1");
            map.put("info","号码已删除！");
        }else{
            map.put("status","0");
            map.put("info","号码删除失败！");
        }
        return map;
    }

    @RequestMapping("/clearAppNumber")
    @ResponseBody
    public Map<String, Object> clearAppNumber(HttpServletRequest request){
        logger.info("------------------------------------------------NumberManagerController clearAppNumber START----------------------------------------------------------------");
        Map<String,Object> map = new HashMap<>();
        String appid = request.getParameter("appid");
        if(!"".equals(appid)){
            numberManagerService.clearAppNumberRestByAppId(appid);
            numberManagerService.clearAppNumberByAppId(appid);
            map.put("status","1");
            map.put("info","号码已清空！");
        }else{
            map.put("status","0");
            map.put("info","号码清空失败！");
        }
        return map;
    }

    @RequestMapping("/submitAppNumber")
    @ResponseBody
    public Map<String, Object> submitAppNumber(HttpServletRequest request){
        logger.info("------------------------------------------------NumberManagerController submitAppNumber START----------------------------------------------------------------");
        //返回view信息
        Map<String, Object> map = new HashMap<>();

        //查询审核通过的号码信息
        Map<String,Object> mapRest = new HashMap<>();
        mapRest.put("appid",request.getParameter("appid"));
        mapRest.put("numbers",request.getParameter("numberArrayStr").split(","));

        //查询是否有待审核的号码
        AppNumber appNum = new AppNumber();
        appNum.setAppid(request.getParameter("appid"));
        appNum.setNumberStatus("00");
        List<AppNumber> appNumberList = numberManagerService.findAppNumberList(appNum);

        //查询提交的号码是否已经审核通过
        List<AppNumberRest> appNumberRestList = numberManagerService.selectAppNumberRestByNumbers(mapRest);
        if(appNumberRestList.size() == 0 && appNumberList.size() == 0){
            AppNumber[] appNumberAyyay = JSonUtils.readValue(request.getParameter("appNumberJsonStr"),AppNumber[].class);
            if(appNumberAyyay.length > 0) {
                List<AppNumber> list = new ArrayList<>();
                for (AppNumber appNumber : appNumberAyyay) {
                    list.add(appNumber);
                }
                numberManagerService.addAppNumbers(list);
                map.put("status","1");
                map.put("info","号码提交成功！");
            }
        }else{
            map.put("status","0");
            map.put("info","号码已提交，请耐心等待审核！");
            map.put("numberData",appNumberRestList);
        }
        return map;
    }

    @RequestMapping("/getRepeatList")
    public String getRepeatList (HttpServletRequest request ,Model model){
        logger.info("------------------------------------------------NumberManagerController getRepeatList START----------------------------------------------------------------");
        //查询审核通过的号码信息
        Map<String,Object> mapRest = new HashMap<>();
        mapRest.put("appid",request.getParameter("appid"));
        mapRest.put("numbers",request.getParameter("numberArrayStr").split(","));

        //查询提交的号码是否已经审核通过
        List<AppNumberRest> appNumberRestList = numberManagerService.selectAppNumberRestByNumbers(mapRest);
        model.addAttribute("numRepeatList",appNumberRestList);
        return "app/numberRepeatList";
    }

    /**
     * aname 区域名称唯一性校验
     * @param appNumber 号码
     * @return
     */
    @RequestMapping("uniqueAname")
    @ResponseBody
    public JSonMessage uniqueAname(AppNumber appNumber) {

        AppNumber appNumberResult = numberManagerService.countNumByNumber(appNumber);
        if(appNumberResult!=null) {
            if ("00".equals(appNumberResult.getNumberStatus())) {
                return new JSonMessage("Pending", "");
            } else if ("01".equals(appNumberResult.getNumberStatus())) {
                return new JSonMessage("Passed", "");
            } else if ("02".equals(appNumberResult.getNumberStatus())) {
                return new JSonMessage("Passed", "");
            }
        }else{
            return new JSonMessage("ok", "");
        }
        return null;
    }

    @RequestMapping("/submitAppNumber1")
    @ResponseBody
    public Map<String, Object> submitAppNumber1(HttpServletRequest request){
        logger.info("------------------------------------------------NumberManagerController submitAppNumber START----------------------------------------------------------------");
        //返回view信息
        Map<String, Object> map = new HashMap<>();

        AppNumber appNum = new AppNumber();
        appNum.setAppid(request.getParameter("appid"));
        appNum.setNumber(request.getParameter("number"));
        AppNumber appNumber = numberManagerService.findNumByAppidAndNumber(appNum);
        if(appNumber!=null){
            if(appNumber.getNumberStatus().equals("03")){
                appNum.setNumberType("01");
                appNum.setNumberStatus("00");
                numberManagerService.addAppNumber(appNum);
                map.put("status","1");
            }else{
                map.put("status","0");
            }

        }else{
            appNum.setNumberType("01");
            appNum.setNumberStatus("00");
            numberManagerService.addAppNumber(appNum);
            map.put("status","1");
        }

        return map;
    }

    /**
     * 上传Numbers Excel文件信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "appNumExcelUpload", method = RequestMethod.POST)
    public String upload(@RequestParam(required = false, value = "numbers") MultipartFile sipNumFile, HttpServletRequest request,Model model){
        model.addAttribute("appid",request.getParameter("appid"));
        if(sipNumFile == null){
            model.addAttribute("error", "导入数据失败！");
            return "app/numberManager1";
        }else{
            try {
                model.addAttribute("uploadCallback","yes");
                List<AppNumber> insertAppNumErrorList = saveExcel(sipNumFile, request);
                if (insertAppNumErrorList.size() > 0) {
                    model.addAttribute("uploadCallback","no");
                    model.addAttribute("error", "存在错误号码,部分数据导入成功,请查看下载内容！");
                    return "app/numberManager1";
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                model.addAttribute("error", "导入数据失败,请下载模板编辑数据进行导入！");
                return "app/numberManager1";
            }
            model.addAttribute("error", "导入数据成功,重复及空列数据不会被导入！");
            return "app/numberManager1";
        }
    }

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<AppNumber> saveExcel(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<AppNumber> appNumList = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();
        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置SIP选号信息
            AppNumber appNumber = new AppNumber();
            appNumber.setAppid(request.getParameter("appid"));
            appNumber.setNumber(arrayStr[0]);
            appNumber.setNumberType("01");
            appNumber.setNumberStatus("00");
            appNumList.add(appNumber);
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<AppNumber> insertAppNumErrorList = new ArrayList<>();
        for (AppNumber appNumResult : appNumList) {

            isNum = pattern.matcher(appNumResult.getNumber());
            //判断号码是否合法
            if (isNum.matches()) {
                AppNumber appNumber = new AppNumber();
                appNumber.setAppid(request.getParameter("appid"));
                appNumber.setNumber(appNumResult.getNumber());
                AppNumber appNumberResult = numberManagerService.countNumByNumber(appNumber);
                if(appNumberResult!=null){
                    insertAppNumErrorList.add(appNumResult);
                }else{
                    numberManagerService.addAppNumber(appNumResult);
                }

            }else{
                //将错误号码添加至错误的集合中
                insertAppNumErrorList.add(appNumResult);
            }
        }

        request.getSession().setAttribute("insertAppNumErrorList",insertAppNumErrorList);
        return insertAppNumErrorList;
    }

    /**
     * 下载SIPNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorAppNum")
    public ModelAndView downLoadErrorAppNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------AppNumberPoolController downLoadErrorSipNum start--------------------------------");
        List<AppNumber> insertAppNumErrorList = (List<AppNumber>) request.getSession().getAttribute("insertAppNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertAppNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<AppNumber> insertAppNumErrorList) {
        logger.info("--------------------------------AppNumberPoolController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (AppNumber appNumber : insertAppNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("number", appNumber.getNumber());
            mapList.add(excelMap);
        }

        List titles = new ArrayList<String>();
        titles.add("号码");

        List columns = new ArrayList<String>();
        columns.add("number");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","错误号码信息");
        contentMap.put("excelName", "错误号码信息");
        return contentMap;
    }

    /**
     * app应用编辑
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage numDel(HttpServletRequest request, HttpServletResponse response, AppNumber appnumber) {
        try{
            numberManagerService.deleteAppNumber(appnumber.getId());
            return new JSonMessage("0","号码删除成功！");
        }catch (Exception e){
            logger.info("--------numberdel error----",e);
            return new JSonMessage("1","号码删除失败！");
        }
    }

    /**
     * 重新提交数据初始化
     */
    @RequestMapping(value = "getNumberInfo", method = RequestMethod.POST)
    @ResponseBody
    public AppNumber getNumberInfo(Long id) {
        return numberManagerService.getNumberInfo(id);
    }

    /**
     * app应用编辑
     */
    @RequestMapping(value = "reCommit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reCommit(HttpServletRequest request, HttpServletResponse response, AppNumber appnumber) {
        Map<String, Object> map = new HashMap<>();
        try{
            appnumber.setRemark(null);
            appnumber.setAddtime(new Date());
            numberManagerService.reCommitAppNumber(appnumber);
            map.put("status","1");
        }catch (Exception e){
            logger.info("--------appSave error----",e);
            map.put("status","0");
        }
        return map;
    }

    /**
     * 导出号码列表
     *
     * @return
     */
    @RequestMapping(value = "downloadAppNumReport", method = RequestMethod.GET)
    public ModelAndView downloadSubReport(HttpServletRequest request, Model model, Page page) {
        Map<String, Object> paramMap = new HashMap<>();
        String number = request.getParameter("number");
        String numberStatus = request.getParameter("numberStatus");
        String appid = request.getParameter("appid");
        paramMap.put("number",number);
        page.setParams(paramMap);
        paramMap.put("numberStatus",numberStatus);
        page.setParams(paramMap);
        paramMap.put("appid",appid);
        page.setParams(paramMap);

        List<Map<String, Object>> totals = numberManagerService.downloadAppNumReport(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                if("".equals(total.get("addtime")) || null == total.get("addtime")){
                    map.put("addtime", "");
                } else {
                    map.put("addtime", String.valueOf(total.get("addtime").toString().substring(0,19)));
                }
                if("".equals(total.get("reviewtime")) || null == total.get("reviewtime")){
                    map.put("reviewtime", "");
                } else {
                    map.put("reviewtime", String.valueOf(total.get("reviewtime").toString().substring(0,19)));
                }
                map.put("appid", String.valueOf(total.get("appid")));
                map.put("number", String.valueOf(total.get("number")));

                if("00".equals(total.get("number_status"))){
                    map.put("number_status", "待审核");
                } else if("01".equals(total.get("number_status"))){
                    map.put("number_status", "审核通过");
                }else if("02".equals(total.get("number_status"))){
                    map.put("number_status", "审核不通过");
                }else if("03".equals(total.get("number_status"))){
                    map.put("number_status", "已删除");
                }
                if("".equals(total.get("remark")) || null == total.get("remark")){
                    map.put("remark", "");
                } else {
                    map.put("remark", String.valueOf(total.get("remark")));
                }

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("APP ID");
        titles.add("号码");
        titles.add("号码状态");
        titles.add("备注");
        titles.add("号码提交时间");
        titles.add("审核时间");

        List<String> columns = new ArrayList<String>();

        columns.add("appid");
        columns.add("number");
        columns.add("number_status");
        columns.add("remark");
        columns.add("addtime");
        columns.add("reviewtime");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "应用号码列表");
        map.put("excelName","应用号码列表");

        Map<String, Object> params = page.getParams();
        return new ModelAndView(new POIXlsView(), map);
    }
}
