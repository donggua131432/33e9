package com.e9cloud.pcweb.sipPhone;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sipPhone.biz.SPNumImportService;
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
import java.util.*;

/**
 * 子帐号号码池管理
 */

@Controller
@RequestMapping("/pubNumResPool")
public class PubNumResPoolController extends BaseController {
    @Autowired
    private PubNumResPoolService pubNumResPoolService;

    @Autowired
    private SubNumService subNumService;

    @Autowired
    private SPNumImportService importService;

    /**
     * 子帐号号码池管理界面
     * @return
     */
    @RequestMapping("/pubNumResPool")
    public String pubNumResPoolMgr(HttpServletRequest request, Model model){
        /*String subid = request.getParameter("subid");
        SipRelayInfo subApp = subNumService.getSubApp(subid);
        model.addAttribute("subApp", subApp)*/;
        return "SIPPhone/pubNumResPoolMgr";
    }


    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/toAddPhone")
    public String toAddSipPhone(HttpServletRequest request, Model model){
//        model.addAttribute("subid", request.getParameter("subid"));
        String type = request.getParameter("type");
        if(type.equals("sip")){
            return "SIPPhone/addSipPhone";
        }else {
            return "SIPPhone/addFixPhone";
        }
    }

    /**
     * 添加公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/addSipPhone")
    @ResponseBody
    public JSonMessageSubmit addSipPhone(HttpServletRequest request, SipPhone sipPhone){
        logger.info("=====================================PubNumResPoolController addSipPhone Execute=====================================");
        List<SipPhone> sipPhoneReturn = pubNumResPoolService.getPubNumResPoolBySipPhone(sipPhone.getSipPhone());
        if(null!=sipPhoneReturn && sipPhoneReturn.size()>0){
            return new JSonMessageSubmit("1","sip号码已存在，信息保存失败！");
        }
        pubNumResPoolService.addSipPhone(sipPhone);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 添加公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/addFixPhone")
    @ResponseBody
    public JSonMessageSubmit addFixPhone(HttpServletRequest request, FixPhone fixPhone){
        logger.info("=====================================PubNumResPoolController addFixPhone Execute=====================================");
        List<FixPhone> fixPhoneReturn = pubNumResPoolService.getPubNumResPoolByNum(fixPhone.getNumber());
        if(null!=fixPhoneReturn && fixPhoneReturn.size()>0){
            return new JSonMessageSubmit("1","公共号码已存在，信息保存失败！");
        }
        pubNumResPoolService.addFixPhone(fixPhone);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 公共号码SipPhone资源表
     * @param page
     * @return
     */
    @RequestMapping("/getSipPhoneList")
    @ResponseBody
    public PageWrapper getSipPhoneList(Page page){
        logger.info("----PubNumResPoolController getSipPhoneList start-------");
        PageWrapper pageWrapper = pubNumResPoolService.getSipPhoneList(page);
        return pageWrapper;
    }

    /**
     * 固话号码FixPhone资源表
     * @param page
     * @return
     */
    @RequestMapping("/getFixPhoneList")
    @ResponseBody
    public PageWrapper getFixPhoneList(Page page){
        logger.info("----PubNumResPoolController getFixPhoneList start-------");
        PageWrapper pageWrapper = pubNumResPoolService.getFixPhoneList(page);
        return pageWrapper;
    }

    /**
     * 校验fixPhone的唯一性
     * @return
     */
    @RequestMapping("checkFixPhone")
    @ResponseBody
    public JSonMessage checkFixPhone(String fixphone){
        List<FixPhone> fixPhone = pubNumResPoolService.getPubNumResPoolByNum(fixphone);
        if(fixPhone.size() > 0){
            return new JSonMessage(R.ERROR, "固话号码已经存在");
        }
        return new JSonMessage(R.OK, "");
    }

    /**
     * 校验sipPhone的唯一性
     * @return
     */
    @RequestMapping("checkSipPhone")
    @ResponseBody
    public JSonMessage checkSipPhone(String sipphone){
        List<SipPhone> sipPhone = pubNumResPoolService.getPubNumResPoolBySipPhone(sipphone);
        //数据库有此sip号码
        if(sipPhone.size() > 0){
            return new JSonMessage(R.ERROR, "SIP号码已存在");
        }
        //检查sip号码是否需要注册（true为需要）
        boolean flag = pubNumResPoolService.checkRegisterSipphone(sipphone);
        if(flag == true){
            return new JSonMessage(R.OK, "");
        }else{
            return new JSonMessage("NotRegister","");
        }

    }

    /**
     * 获取IP_PORT
     * @return
     */
    @RequestMapping("/getIPPortList")
    @ResponseBody
    public List<SipPhone> getIPPortList(){
        logger.info("----PubNumResPoolController getIPPortList start-------");
        List<SipPhone> sipPhone = pubNumResPoolService.getIPPortList();
        return sipPhone;
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
    @RequestMapping("/importPhone")
    public String importPhone(HttpServletRequest request, Model model){
        String type = request.getParameter("type");
        if(type.equals("sip")){
            return "SIPPhone/importSipPhones";
        }else {
            return "SIPPhone/importFixPhones";
        }
    }

    /**
     * 上传SipPhone Excel文件信息
     * @param SipPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/SipPhoneExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit uploadSipPhoneExcel(@RequestParam(required = false, value = "file") MultipartFile SipPhoneFile, HttpServletRequest request){

        if(SipPhoneFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<SipPhone> insertSipPhoneErrorList = importService.saveSipPhoneExcel(SipPhoneFile, request);
                if (insertSipPhoneErrorList.size() > 0) {
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
     * 下载SubNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorSipPhone")
    public ModelAndView downLoadErrorSipPhone(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SubNumberPoolController downLoadErrorSipPhone start--------------------------------");
        List<SipPhone> insertSipPhoneErrorList = (List<SipPhone>) request.getSession().getAttribute("insertSipPhoneErrorList");
        Map<String, Object> contentMap = downLoadSipPhoneExcel(insertSipPhoneErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadSipPhoneExcel(List<SipPhone> insertSipPhoneErrorList) {
        logger.info("--------------------------------SubNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (SipPhone sipPhone : insertSipPhoneErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", sipPhone.getAreaCode());
            excelMap.put("sipPhone", sipPhone.getSipPhone());
            excelMap.put("pwd", sipPhone.getPwd());
            excelMap.put("sipRealm", sipPhone.getSipRealm());
            excelMap.put("ipPort", sipPhone.getIpPort());
            excelMap.put("importCommon", sipPhone.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("城市区号");
        titles.add("SIP 号码");
        titles.add("认证密码（PWD）");
        titles.add("SIP Realm");
        titles.add("SIP 服务器（IP：PORT）");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("sipPhone");
        columns.add("pwd");
        columns.add("sipRealm");
        columns.add("ipPort");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","云话机SIP号码错误信息");
        contentMap.put("excelName", "云话机SIP号码错误信息");

        return contentMap;
    }

    /*******************************************************FixPhone Excel文件相关 of start***************************************************/
    /**
     * 上传FixPhone Excel文件信息
     * @param FixPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/FixPhoneExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit uploadFixPhoneExcel(@RequestParam(required = false, value = "file") MultipartFile FixPhoneFile, HttpServletRequest request){

        if(FixPhoneFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<FixPhone>  insertFixPhoneErrorList = importService.saveFixPhoneExcel(FixPhoneFile, request);
                if (insertFixPhoneErrorList.size() > 0) {
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
     * 下载SubNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorFixPhone")
    public ModelAndView downLoadErrorFixPhone(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SubNumberPoolController downLoadErrorSipPhone start--------------------------------");
        List<FixPhone> insertFixPhoneErrorList = (List<FixPhone>) request.getSession().getAttribute("insertFixPhoneErrorList");
        Map<String, Object> contentMap = downLoadFixPhoneExcel(insertFixPhoneErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadFixPhoneExcel(List<FixPhone> insertFixPhoneErrorList) {
        logger.info("--------------------------------SubNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (FixPhone fixPhone : insertFixPhoneErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", fixPhone.getAreaCode());
            excelMap.put("number", fixPhone.getNumber());
            excelMap.put("importCommon", fixPhone.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("城市区号");
        titles.add("固话号码");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("number");
        columns.add("importCommon");
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","云话机固话号码错误信息");
        contentMap.put("excelName", "云话机固话号码错误信息");
        return contentMap;
    }

/*******************************************************FixPhone Excel文件相关 of end  ***************************************************/




    /**
     * 导出SIP号码
     *
     * @return
     */
    @RequestMapping(value = "downloadSipPhonePool", method = RequestMethod.GET)
    public ModelAndView downloadSipPhonePool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = pubNumResPoolService.downloadSipPhonePool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("sipPhone", String.valueOf(total.get("sipPhone")).equals("null")?"":String.valueOf(total.get("sipPhone")));
                map.put("pname", String.valueOf(total.get("pname")).equals("null")?"":String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
//                map.put("status", String.valueOf(total.get("status")));
                map.put("companyName", String.valueOf(total.get("companyName")).equals("null")?"":String.valueOf(total.get("companyName")));
                map.put("sid", String.valueOf(total.get("sid")).equals("null")?"":String.valueOf(total.get("sid")));
                map.put("appName", String.valueOf(total.get("appName")).equals("null")?"":String.valueOf(total.get("appName")));
                map.put("appId", String.valueOf(total.get("appId")).equals("null")?"":String.valueOf(total.get("appId")));
                map.put("pwd", String.valueOf(total.get("pwd")).equals("null")?"":String.valueOf(total.get("pwd")));
                map.put("sipRealm", String.valueOf(total.get("sipRealm")).equals("null")?"":String.valueOf(total.get("sipRealm")));
                map.put("ipPort", String.valueOf(total.get("ipPort")).equals("null")?"":String.valueOf(total.get("ipPort")));

                if(String.valueOf(total.get("status")).equals("01")){
                    map.put("status", "已分配");
                }else if(String.valueOf(total.get("status")).equals("02")){
                    map.put("status", "已锁定");
                }else {
                    map.put("status", "待分配");
                }
                if("".equals(total.get("updatetime")) || null == total.get("updatetime")){
                    map.put("updatetime", "");
                } else {
                    map.put("updatetime", String.valueOf(total.get("updatetime").toString().substring(0,19)));
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("SIP号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("状态");
        titles.add("修改时间");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("Appid");
        titles.add("认证密码");
        titles.add("SIP REALM");
        titles.add("IP：PORT");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("sipPhone");
        columns.add("pname");
        columns.add("cname");
        columns.add("status");
        columns.add("updatetime");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appId");
        columns.add("pwd");
        columns.add("sipRealm");
        columns.add("ipPort");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "SIP号码资源池列表");
        map.put("excelName","SIP号码资源池列表");
        return new ModelAndView(new POIXlsView(), map);
    }



    /**
     * 导出固话号码
     *
     * @return
     */
    @RequestMapping(value = "downloadFixPhonePool", method = RequestMethod.GET)
    public ModelAndView downloadFixPhonePool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = pubNumResPoolService.downloadFixPhonePool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("pname", String.valueOf(total.get("pname")).equals("null")?"":String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
                map.put("companyName", String.valueOf(total.get("companyName")).equals("null")?"":String.valueOf(total.get("companyName")));
                map.put("sid", String.valueOf(total.get("sid")).equals("null")?"":String.valueOf(total.get("sid")));
                map.put("appName", String.valueOf(total.get("appName")).equals("null")?"":String.valueOf(total.get("appName")));
                map.put("appId", String.valueOf(total.get("appId")).equals("null")?"":String.valueOf(total.get("appId")));
                if(String.valueOf(total.get("status")).equals("01")){
                    map.put("status", "已分配");
                }else if(String.valueOf(total.get("status")).equals("02")){
                    map.put("status", "已锁定");
                }else {
                    map.put("status", "待分配");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("固话号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("状态");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("Appid");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("number");
        columns.add("pname");
        columns.add("cname");
        columns.add("status");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appId");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "固话号码资源池列表");
        map.put("excelName","固话号码资源池列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 批量删除公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("deletePhones")
    @ResponseBody
    public Map<String,String> deleteSipPhones (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        String type = request.getParameter("type");
        map.put("code","00");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        StringBuilder phone = new StringBuilder();
        String[] aa = strId.split(",");
        if(type.equals("sip")){
            for(String id : aa){
                if(pubNumResPoolService.getSipPhoneById(id)!=null&&pubNumResPoolService.getSipPhoneById(id).size()>0){
                    SipPhone sipPhone = pubNumResPoolService.getSipPhoneById(id).get(0);
                    if(sipPhone.getStatus().equals("01")){
                        phone.append(sipPhone.getSipPhone()).append(",");
                    }else {
                        pubNumResPoolService.deleteSipPhoneByIds(id.split(","));
                    }
                }
            }
        }else {
            for(String id : aa){
                if(pubNumResPoolService.getFixPhoneById(id)!=null){
                    FixPhone fixPhone = pubNumResPoolService.getFixPhoneById(id);
                    if(fixPhone.getStatus().equals("01")){
                        phone.append(fixPhone.getNumber()).append(",");
                    }else {
                        pubNumResPoolService.deleteFixPhoneByIds(id.split(","));
                    }
                }
            }
        }
        if(phone.length()>1){
            map.put("code","02");
            map.put("msg",phone.substring(0,phone.length()-1)+"号码已分配不能删除");
        }else {
            map.put("code","00");
        }
        return map;
    }
}
