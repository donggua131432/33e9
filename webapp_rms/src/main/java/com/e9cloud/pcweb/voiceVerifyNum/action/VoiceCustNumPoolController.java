package com.e9cloud.pcweb.voiceVerifyNum.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.BusTypeService;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.mybatis.service.VoiceCustNumPoolService;
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
 * Created by admin on 2017/5/3.
 */

@Controller
@RequestMapping("/voiceCustNum")
public class VoiceCustNumPoolController extends BaseController{

    @Autowired
    private VoiceCustNumPoolService voiceCustNumPoolService;
    @Autowired
    private CityManagerService cityManagerService;
    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private BusTypeService busTypeService;

    /**=================================================================================================================
     * 客户号码池配置
     * @return
     */
    @RequestMapping("/voiceCustNumberPoolConfig")
    public String voiceCustNumberConfig(){
        logger.info("----VoiceCustNumPoolController voiceCustNumberPoolConfig start-------");
        return "voiceVerify/voiceCustNumberPoolConfig";
    }

    /**
     * 获取客户号码池列表
     * @param page
     * @return
     */
    @RequestMapping("/getPageList")
    @ResponseBody
    public PageWrapper getVoiceCustAppList(Page page){
        logger.info("----VoiceCustNumPoolController getVoiceCustNumberList start-------");
        return voiceCustNumPoolService.getVoiceCustAppList(page);
    }

    /**
     * 创建号码池
     * @return
     */
    @RequestMapping("/addVoiceCustNumberPool")
    public String addVoiceCustNumberPool(Model model){
        logger.info("----VoiceCustNumPoolController addVoiceCustNumberPool start-------");
        return "voiceVerify/addVoiceCustNumPool";
    }
    /**
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkAndgetUserAdminWithCompany")
    @ResponseBody
    public Map<String, Object> checkAndgetUserAdminWithCompany(HttpServletRequest request, HttpServletResponse response, BusinessType businessType) throws Exception {
        logger.info("----checkAndgetUserAdminWithCompany start-------");
        Map map = new HashMap<String, Object>();

        BusinessType businessTypeReturn = busTypeService.getBusinessTypeInfo(businessType);
        if(businessTypeReturn !=null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setSid(request.getParameter("sid"));
            UserAdmin userAdminResult = userAdminService.getUserAdminWithCompany(userAdmin);
            map.put("status",0);
            map.put("info","信息查询成功！");
            map.put("userAdminResult",userAdminResult);
        }else{
            map.put("status",1);
            map.put("info","开发者ID不存在！");
        }

        return map;
    }

    /**
     * 编辑号码池
     * @param model
     * @return
     */
    @RequestMapping("/managerVoiceCustNumberPool")
    public String editVoiceCustNumberPool(Model model, String appid, String managerType){
        logger.info("----VoiceCustNumPoolController managerVoiceCustNumberPool start-------");
        VoiceVerifyApp voiceAppPool = new VoiceVerifyApp();
        voiceAppPool.setAppid(appid);
        model.addAttribute("voiceCustPool",voiceCustNumPoolService.getVoiceCustAppPoolByObj(voiceAppPool));
        model.addAttribute("managerType", managerType);
        return "voiceVerify/managerVoiceCustNumberPool";
    }



    /**
     * 删除客户号码池
     * @param request
     * @return
     */
    @RequestMapping("/deleteVoiceAppPool")
    @ResponseBody
    public JSonMessageSubmit deleteAxbAppPool(HttpServletRequest request){
        logger.info("----VoiceCustNumPoolController deleteVoiceAppPool start-------");
        //删除号码池的信息
        VoiceVerifyApp voiceAppPool = new VoiceVerifyApp();
        voiceAppPool.setId(request.getParameter("id"));
        VoiceVerifyApp voiceAppPoolResult = voiceCustNumPoolService.getVoiceCustAppPoolByObj(voiceAppPool);
        if(voiceAppPoolResult != null){
            boolean hasNum = voiceCustNumPoolService.checkHasNumber(request.getParameter("appid"));
            if(hasNum){
                voiceCustNumPoolService.deleteVoiceCustAppPoolById(request.getParameter("id"));
                return new JSonMessageSubmit("0","删除数据成功！");
            }else {
                return new JSonMessageSubmit("1","号码池中仍有号码，删除数据失败！");
            }
        }else {
            return new JSonMessageSubmit("1","删除数据失败！");
        }
    }



    /**
     * 获取用户号码池号码信息列表
     * @param page
     * @return
     */
    @RequestMapping("/getVoiceCustNumberList")
    @ResponseBody
    public PageWrapper getVoiceCustNumberList(Page page){
        logger.info("----VoiceCustNumPoolService getVoiceCustAppList start-------");
        return voiceCustNumPoolService.getVoiceCustNumberList(page);
    }



    /**
     * 导出用户号码
     *
     * @return
     */
    @RequestMapping(value = "downloadVoiceCustNumPool", method = RequestMethod.GET)
    public ModelAndView downloadVoiceNumPool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = voiceCustNumPoolService.downloadVoiceCustNumPool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("area_code", String.valueOf(total.get("area_code")).equals("null")?"":String.valueOf(total.get("area_code")));
                map.put("pname", String.valueOf(total.get("pname")).equals("null")?"":String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
                map.put("addtime", String.valueOf(total.get("addtime").toString().substring(0,19)));
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("区号");
        titles.add("创建时间");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("number");
        columns.add("pname");
        columns.add("cname");
        columns.add("area_code");
        columns.add("addtime");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "用户号码资源池列表");
        map.put("excelName","用户号码资源池列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 添加voiceCustNum
     * @return
     */
    @RequestMapping("/toAddVoiceCustNumber")
    public String addVoiceCustNumber(HttpServletRequest request, Model model){
        logger.info("----VoiceCustNumPoolController addVoiceCustNumber start-------");
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "voiceVerify/addVoiceCustNumber";
    }


    /**
     * 导入用户号码
     * @return
     */
    @RequestMapping("/importVoiceCustNumber")
    public String importAxbCustNumber(HttpServletRequest request, Model model){
        logger.info("----VoiceCustNumPoolController importVoiceCustNumber start-------");
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "voiceVerify/importVoiceCustNumber";
    }



    /**
     * 单个添加voice用户号码
     * @param voiceVerNum
     * @return
     */
    @RequestMapping("/addVoiceCustNum")
    @ResponseBody
    public JSonMessageSubmit addVoiceCustNum(VoiceVerifyNumPool voiceVerNum, HttpServletRequest request){
        logger.info("--------------------------------VoiceCustNumPoolController addVoiceCustNum start--------------------------------");
        String appid = request.getParameter("appid");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        //添加用户号码信息
        List<VoiceVerCustNum> voiceCustNumResultLsit = new ArrayList<>();
        isNum = pattern.matcher(voiceVerNum.getNumber());
        //判断号码是否合法
        if (isNum.matches()) {
//            TelnoCity city = cityManagerService.getTelnoCityByAreaCode(voiceVerNum.getAreacode());
            VoiceVerifyNumPool paramVoicePhone = new VoiceVerifyNumPool();
            paramVoicePhone.setCityid(voiceVerNum.getCityid());
            paramVoicePhone.setNumber(voiceVerNum.getNumber());
            List<VoiceVerifyNumPool> voicePhoneReturn = voiceCustNumPoolService.getVoiceNumPoolByPhone(paramVoicePhone);

            if (voicePhoneReturn != null && voicePhoneReturn.size()>0) {
                if("01".equals(voicePhoneReturn.get(0).getStatus())){
                    return new JSonMessageSubmit("1", voiceVerNum.getNumber()+"号码已分配，信息添加失败！");
                }else if("02".equals(voicePhoneReturn.get(0).getStatus())){
                    return new JSonMessageSubmit("1", voiceVerNum.getNumber()+"号码已删除，信息添加失败！");
                }

                else{
                    VoiceVerifyApp voiceAppPoolResult = voiceCustNumPoolService.getVoiceCustAppPoolByObj(new VoiceVerifyApp(appid));
                    if(voiceAppPoolResult == null){
                        VoiceVerifyApp voiceAppPool = new VoiceVerifyApp();
                        voiceAppPool.setId(ID.randomUUID());
                        voiceAppPool.setSid(request.getParameter("sid"));
                        voiceAppPool.setUid(request.getParameter("uid"));
                        voiceAppPool.setAppid(appid);
                        voiceCustNumPoolService.addVoiceCustAppPool(voiceAppPool);
                    }
                    //成功
                    VoiceVerCustNum voiceCustNumber = new VoiceVerCustNum();
                    voiceCustNumber.setAppid(appid);
                    voiceCustNumber.setNumId(voicePhoneReturn.get(0).getId());
                    voiceCustNumber.setStatus("00");
                    //更改公共号码池号码为已分配
                    VoiceVerifyNumPool voicePhoneSucc = voicePhoneReturn.get(0);
                    voicePhoneSucc.setStatus("01");
                    voiceCustNumPoolService.addVoiceCustNumberPool(voiceCustNumber,voicePhoneSucc);
                }
            } else {
                return new JSonMessageSubmit("1", "城市、号码不在公共号码资源池中，信息添加失败！");
            }
        } else {
            return new JSonMessageSubmit("1", voiceVerNum.getNumber()+"号码格式错误，信息添加失败！");
        }

        return new JSonMessageSubmit("0","信息添加成功！");
    }



    /**
     * 上传AxbNumber Excel文件信息
     * @param voiceCustNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/voiceCustNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody

    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile voiceCustNumFile, HttpServletRequest request){
        logger.info("--------------------------------VoiceCustNumResPoolController upload start--------------------------------");
        String appid = request.getParameter("appid");
        if(voiceCustNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            VoiceVerifyApp voiceAppPoolResult = voiceCustNumPoolService.getVoiceCustAppPoolByObj(new VoiceVerifyApp(appid));

            //向用户号码池中添加信息
            if(voiceAppPoolResult == null){
                VoiceVerifyApp voiceAppPool = new VoiceVerifyApp();
                voiceAppPool.setId(ID.randomUUID());
                voiceAppPool.setSid(request.getParameter("sid"));
                voiceAppPool.setUid(request.getParameter("uid"));
                voiceAppPool.setAppid(appid);
                voiceCustNumPoolService.addVoiceCustAppPool(voiceAppPool);
            }

            try {
                List<VoiceVerifyNumPool> insertVoiceCustNumErrorList = saveExcel(voiceCustNumFile, request,appid);
                if (insertVoiceCustNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    public List<VoiceVerifyNumPool> saveExcel(MultipartFile voiceCustNumFile, HttpServletRequest request,String appid) throws Exception{
        List<VoiceVerifyNumPool> voicePhoneList = new ArrayList<>();
        InputStream is = voiceCustNumFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for(Integer key : mapFile.keySet()){
            String[] arrayStr = mapFile.get(key).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                //设置axbCustNumber信息
                VoiceVerifyNumPool voiceVerifyNumPool = new VoiceVerifyNumPool();
                voiceVerifyNumPool.setAreacode(arrayStr[0]);
                voiceVerifyNumPool.setNumber(arrayStr[1]);

                voicePhoneList.add(voiceVerifyNumPool);
            }
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<VoiceVerifyNumPool> insertVoiceCustNumberErrorList = new ArrayList<>();
        for (VoiceVerifyNumPool voicePhone:voicePhoneList){
            if(Tools.isNullStr(voicePhone.getAreacode())){
                voicePhone.setImportCommon("区号为空");
                insertVoiceCustNumberErrorList.add(voicePhone);
                continue;
            }
            if(Tools.isNullStr(voicePhone.getNumber())){
                voicePhone.setImportCommon("号码为空");
                insertVoiceCustNumberErrorList.add(voicePhone);
                continue;
            }
            TelnoCity city = cityManagerService.getTelnoCityByAreaCode(voicePhone.getAreacode());
            if(city==null){
                voicePhone.setImportCommon("区号不存在");
                insertVoiceCustNumberErrorList.add(voicePhone);
                continue;
            }
            VoiceVerifyNumPool paramVoicePhone = new VoiceVerifyNumPool();
            paramVoicePhone.setCityid(city.getCcode());
            paramVoicePhone.setNumber(voicePhone.getNumber());
            List<VoiceVerifyNumPool> voicePhoneReturn = voiceCustNumPoolService.getVoiceNumPoolByPhone(paramVoicePhone);


            if (voicePhoneReturn != null && voicePhoneReturn.size()>0) {
                if("01".equals(voicePhoneReturn.get(0).getStatus())){
                    voicePhone.setImportCommon("号码已分配");
                    insertVoiceCustNumberErrorList.add(voicePhone);
                    continue;
                }
//                else if("02".equals(voicePhoneReturn.get(0).getStatus())){
//                    voicePhone.setImportCommon("号码已删除");
//                    insertVoiceCustNumberErrorList.add(voicePhone);
//                    continue;
//                }
                else {
                    //成功
                    VoiceVerCustNum voiceCustNumber = new VoiceVerCustNum();
                    voiceCustNumber.setAppid(appid);
                    voiceCustNumber.setNumId(voicePhoneReturn.get(0).getId());
                    voiceCustNumber.setStatus("00");
                    //更改公共号码池号码为已分配
                    VoiceVerifyNumPool voicePhoneSucc = voicePhoneReturn.get(0);
                    voicePhoneSucc.setStatus("01");
                    voiceCustNumPoolService.addVoiceCustNumberPool(voiceCustNumber,voicePhoneSucc);
                }
            } else {
                voicePhone.setImportCommon("区号、号码不在公共号码资源池中");
                insertVoiceCustNumberErrorList.add(voicePhone);
                continue;
            }

        }
        request.getSession().setAttribute("insertVoiceCustNumberErrorList", insertVoiceCustNumberErrorList);
        return insertVoiceCustNumberErrorList;
    }



    /**
     * 下载AxbCustNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorVoiceCustNum")
    public ModelAndView downLoadErrorAxbCustNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------VoiceCustNumResPoolController downLoadErrorVoiceCustNum start--------------------------------");
        List<VoiceVerifyNumPool> insertVoiceCustNumErrorList = (List<VoiceVerifyNumPool>) request.getSession().getAttribute("insertVoiceCustNumberErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertVoiceCustNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    public Map<String, Object> downLoadExcel(List<VoiceVerifyNumPool> insertVoiceCustNumErrorList) {
        logger.info("--------------------------------VoiceCustNumResPoolController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (VoiceVerifyNumPool voicePhone : insertVoiceCustNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", voicePhone.getAreacode());
            excelMap.put("number", voicePhone.getNumber());
            excelMap.put("importCommon", voicePhone.getImportCommon());
            mapList.add(excelMap);
        }

        List titles = new ArrayList<String>();
        titles.add("区号");
        titles.add("号码");
        titles.add("原因");

        List columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("number");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("excelName", "错误用户号码信息");
        return contentMap;
    }



    /**
     * 删除客户号码
     * @param request
     * @return
     */
    @RequestMapping("/deleteVoiceCustNumber")
    @ResponseBody
    public JSonMessageSubmit deleteVoiceCustNumber(HttpServletRequest request){
        logger.info("----VoiceCustNumResPoolController deleteVoiceCustNumber start-------");
        String strId = request.getParameter("strId");
        voiceCustNumPoolService.deletePhones(strId);
        return new JSonMessageSubmit("0","删除数据成功！");

    }


    /**
     * 导出应用信息
     *
     * @return
     */
    @RequestMapping(value = "downloadVoiceAppInfo", method = RequestMethod.GET)
    public ModelAndView downloadVoiceAppInfo(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = voiceCustNumPoolService.downloadVoiceAppInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("addtime", String.valueOf(total.get("addtime").toString().substring(0,19)));
                map.put("name", String.valueOf(total.get("name")).equals("null")?"":String.valueOf(total.get("name")));
                map.put("sid", String.valueOf(total.get("sid")).equals("null")?"":String.valueOf(total.get("sid")));
                map.put("app_name", String.valueOf(total.get("app_name")).equals("null")?"":String.valueOf(total.get("app_name")));
                map.put("appid", String.valueOf(total.get("appid")).equals("null")?"":String.valueOf(total.get("appid")));

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("添加时间");
        titles.add("客户名称");
        titles.add("account ID");
        titles.add("应用名称");
        titles.add("appid");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("addtime");
        columns.add("name");
        columns.add("sid");
        columns.add("app_name");
        columns.add("appid");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "app账号信息列表");
        map.put("excelName","app账号信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }



}
