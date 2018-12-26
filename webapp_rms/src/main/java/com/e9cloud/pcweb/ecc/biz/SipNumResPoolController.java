package com.e9cloud.pcweb.ecc.biz;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.R;
import com.e9cloud.mybatis.domain.EccSipphone;
import com.e9cloud.mybatis.service.PubNumResPoolService;
import com.e9cloud.mybatis.service.SipNumResPoolService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.ecc.action.EccSipPhoneImportService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 号码池--sip号码
 */
@Controller
@RequestMapping("/sipNumResPool")
public class SipNumResPoolController extends BaseController {
    @Autowired
    private PubNumResPoolService pubNumResPoolService;

    @Autowired
    private SipNumResPoolService sipNumResPoolService;

    @Autowired
    private EccSipPhoneImportService importService;

    /**
     * sip号码池管理界面
     * @return
     */
    @RequestMapping("/sipNumResPool")
    public String pubNumResPoolMgr(HttpServletRequest request, Model model){
        return "ecc/sipNumResPoolMgr";
    }

    /**
     * 接听号码Sip资源表
     * @param page
     * @return
     */
    @RequestMapping("/getSipPhoneList")
    @ResponseBody
    public PageWrapper getSipPhoneList(Page page){
        logger.info("----SipNumResPoolController getSipPhoneList start-------");
        PageWrapper pageWrapper = sipNumResPoolService.getSipPhoneList(page);
        return pageWrapper;
    }

    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/toAddPhone")
    public String toAddSipPhone(HttpServletRequest request, Model model){
            return "ecc/addSipPhone";

    }

    /**
     * 校验sipPhone的唯一性
     * @return
     */
    @RequestMapping("checkSipPhone")
    @ResponseBody
    public JSonMessage checkSipPhone(String sipphone){
        List<EccSipphone> sipPhone = sipNumResPoolService.getSipNumResPoolBySipPhone(sipphone);
        //数据库有此sip号码
        if(sipPhone.size() > 0){
            return new JSonMessage(R.ERROR, "SIP号码已存在");
        }
        return new JSonMessage(R.OK, "");
    }

    /**
     * 添加公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/addSipPhone")
    @ResponseBody
    public JSonMessageSubmit addSipPhone(HttpServletRequest request, EccSipphone sipPhone){
        logger.info("=====================================SipNumResPoolController addSipPhone Execute==================================");
        List<EccSipphone> sipPhoneReturn = sipNumResPoolService.getSipNumResPoolBySipPhone(sipPhone.getSipPhone());
        if(null!=sipPhoneReturn && sipPhoneReturn.size()>0){
            return new JSonMessageSubmit("1","sip号码已存在，信息保存失败！");
        }
        sipNumResPoolService.addSipPhone(sipPhone);
        return new JSonMessageSubmit("0","信息保存成功！");
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
        map.put("code","00");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        StringBuilder phone = new StringBuilder();
        String[] aa = strId.split(",");

        for(String id : aa){
            if(sipNumResPoolService.getSipPhoneById(id)!=null&&sipNumResPoolService.getSipPhoneById(id).size()>0){
                EccSipphone eccSipphone = sipNumResPoolService.getSipPhoneById(id).get(0);
                if(eccSipphone.getStatus().equals("01")){
                    phone.append(eccSipphone.getSipPhone()).append(",");
                }else {
                    sipNumResPoolService.deleteSipPhoneByIds(id.split(","));
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

    /**
     * 导出SIP号码
     *
     * @return
     */
    @RequestMapping(value = "downloadSipPhonePool", method = RequestMethod.GET)
    public ModelAndView downloadSipPhonePool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = sipNumResPoolService.downloadSipPhonePool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("type","sip号码");
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
        titles.add("接听号码类型");
        titles.add("接听号码");
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
        columns.add("type");
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
        map.put("title", "接听号码-SIP号码列表");
        map.put("excelName","接听号码-SIP号码列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 校验number的唯一性
     * @return
     */
    @RequestMapping("checkEccSipPhone")
    @ResponseBody
    public JSonMessage checkEccSipPhone(String sipphone){
        List<EccSipphone> eccSipPhoneReturn = sipNumResPoolService.getSipNumResPoolBySipPhone(sipphone);
        if(eccSipPhoneReturn.size() > 0){
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
     * 导入号码
     * @return
     */
    @RequestMapping("/importPhone")
    public String importPhone(HttpServletRequest request, Model model){
       return "ecc/importSipPhones";
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
                List<EccSipphone> insertSipPhoneErrorList = importService.saveSipPhoneExcel(SipPhoneFile, request);
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
        logger.info("--------------------------------SipNumResPoolController downLoadErrorSipPhone start--------------------------------");
        List<EccSipphone> insertEccSipPhoneErrorList = (List<EccSipphone>) request.getSession().getAttribute("insertEccSipPhoneErrorList");
        Map<String, Object> contentMap = downLoadSipPhoneExcel(insertEccSipPhoneErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadSipPhoneExcel(List<EccSipphone> insertSipPhoneErrorList) {
        logger.info("--------------------------------SipNumResPoolController downLoadSipPhoneExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (EccSipphone sipPhone : insertSipPhoneErrorList) {
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
        contentMap.put("title","接听号码-SIP号码错误信息");
        contentMap.put("excelName", "接听号码-SIP号码错误信息");

        return contentMap;
    }

    /**
     * 获取IP_PORT
     * @return
     */
    @RequestMapping("/getIPPortList")
    @ResponseBody
    public List<EccSipphone> getIPPortList(){
        logger.info("----SipNumResPoolController getIPPortList start-------");
        List<EccSipphone> sipPhone = sipNumResPoolService.getIPPortList();
        return sipPhone;
    }
}
