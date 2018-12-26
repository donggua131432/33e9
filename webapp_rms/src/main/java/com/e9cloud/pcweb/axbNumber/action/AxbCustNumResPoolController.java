package com.e9cloud.pcweb.axbNumber.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
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
 * Created by hzd on 2017/4/18.
 */
@Controller
@RequestMapping("/axbCustNumResPool")
public class AxbCustNumResPoolController extends BaseController {
    @Autowired
    private AxbCustNumResPoolService axbCustNumResPoolService;

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private AxbNumResPoolService axbNumResPoolService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private BusTypeService busTypeService;

    /**=================================================================================================================
     * 客户号码池配置
     * @return
     */
    @RequestMapping("/axbCustNumberPoolConfig")
    public String axbCustNumberConfig(){
        logger.info("----AxbCustNumResPoolController axbCustNumberConfig start-------");
        return "axbNumber/axbCustNumberPoolConfig";
    }

    /**
     * 获取客户号码池列表
     * @param page
     * @return
     */
    @RequestMapping("/getAxbCustNumberPoolList")
    @ResponseBody
    public PageWrapper getAxbCustNumberPoolList(Page page){
        logger.info("----AxbCustNumResPoolController getAxbCustNumberPoolList start-------");
        return axbCustNumResPoolService.getAxbCustAppPoolList(page);
    }

    /**
     * 创建号码池
     * @return
     */
    @RequestMapping("/addAxbCustNumberPool")
    public String addAxbCustNumberPool(Model model){
        logger.info("----AxbCustNumResPoolController addAxbCustNumberPool start-------");
        return "axbNumber/addAxbCustNumberPool";
    }

    /**
     * 添加axbcustnum
     * @return
     */
    @RequestMapping("/toAddAxbCustNumber")
    public String addAxbCustNumber(HttpServletRequest request, Model model){
        logger.info("----AxbCustNumResPoolController addAxbCustNumber start-------");
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "axbNumber/addAxbCustNumber";
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
    public Map<String, Object> checkAndgetUserAdminWithCompany(HttpServletRequest request, HttpServletResponse response,BusinessType businessType) throws Exception {
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
     * 单个添加axb用户号码
     * @param axbPhone
     * @return
     */
    @RequestMapping("/addAxbCustNumber")
    @ResponseBody
    public JSonMessageSubmit addAxbCustNumber(AxbPhone axbPhone, HttpServletRequest request){
        logger.info("--------------------------------AxbCustNumResPoolController addAxbCustNumber start--------------------------------");
        String appid = request.getParameter("appid");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        //添加用户号码信息
        List<AxbCustNumber> axbCustNumResultLsit = new ArrayList<>();
        isNum = pattern.matcher(axbPhone.getNumber());
        //判断号码是否合法
        if (isNum.matches()) {
            TelnoCity city = cityManagerService.getTelnoCityByAreaCode(axbPhone.getAreacode());
            AxbPhone paramAxbPhone = new AxbPhone();
            paramAxbPhone.setCityid(city.getCcode());
            paramAxbPhone.setNumber(axbPhone.getNumber());
            List<AxbPhone> axbPhoneReturn = axbNumResPoolService.getAxbNumResPoolByAxbPhone(paramAxbPhone);


            if (axbPhoneReturn != null && axbPhoneReturn.size()>0) {
                if("01".equals(axbPhoneReturn.get(0).getStatus())){
                    return new JSonMessageSubmit("1", axbPhone.getNumber()+"号码已分配，信息添加失败！");
                }else if("02".equals(axbPhoneReturn.get(0).getStatus())){
                    return new JSonMessageSubmit("1", axbPhone.getNumber()+"号码已锁定，信息添加失败！");
                } else{
                    AxbAppPool axbAppPoolResult = axbCustNumResPoolService.getAxbCustAppPoolByObj(new AxbAppPool(appid));
                    if(axbAppPoolResult == null){
                        AxbAppPool axbAppPool = new AxbAppPool();
                        axbAppPool.setId(ID.randomUUID());
                        axbAppPool.setSid(request.getParameter("sid"));
                        axbAppPool.setUid(request.getParameter("uid"));
                        axbAppPool.setAppid(appid);
                        axbCustNumResPoolService.addAxbCustAppPool(axbAppPool);
                    }
                    //成功
                    AxbCustNumber axbCustNumber = new AxbCustNumber();
                    axbCustNumber.setAppid(appid);
                    axbCustNumber.setAxbNumId(axbPhoneReturn.get(0).getId());
                    axbCustNumber.setStatus("00");
                    //更改公共号码池号码为已分配
                    AxbPhone axbPhoneSucc = axbPhoneReturn.get(0);
                    axbPhoneSucc.setStatus("01");
                    axbCustNumResPoolService.addAxbCustNumberPool(axbCustNumber,axbPhoneSucc);
                }
            } else {
                return new JSonMessageSubmit("1", "区号、号码不在公共号码资源池中，信息添加失败！");
            }
        } else {
            return new JSonMessageSubmit("1", axbPhone.getNumber()+"号码格式错误，信息添加失败！");
        }

        return new JSonMessageSubmit("0","信息添加成功！");
    }

    /**
     * 编辑号码池
     * @param model
     * @return
     */
    @RequestMapping("/managerAxbCustNumberPool")
    public String editAxbCustNumberPool(Model model, String appid, String managerType){
        logger.info("----AxbCustNumResPoolController editMaskNumberPool start-------");
        AxbAppPool axbAppPool = new AxbAppPool();
        axbAppPool.setAppid(appid);
        model.addAttribute("axbCustPool",axbCustNumResPoolService.getAxbCustAppPoolByObj(axbAppPool));

        model.addAttribute("managerType", managerType);
        return "axbNumber/managerAxbCustNumberPool";
    }

    /**
     * 获取用户号码池号码信息列表
     * @param page
     * @return
     */
    @RequestMapping("/getAxbCustNumberList")
    @ResponseBody
    public PageWrapper getAxbCustNumberList(Page page){
        logger.info("----AxbCustNumResPoolController getAxbCustNumberList start-------");
        return axbCustNumResPoolService.getAxbCustNumberList(page);
    }

    /**
     * 导出用户号码
     *
     * @return
     */
    @RequestMapping(value = "downloadAxbCustNumPool", method = RequestMethod.GET)
    public ModelAndView downloadAxbNumPool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = axbCustNumResPoolService.downloadAxbCustNumPool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("area_code", String.valueOf(total.get("area_code")).equals("null")?"":String.valueOf(total.get("area_code")));

                if(String.valueOf(total.get("status")).equals("01")){
                    map.put("status", "已锁定");
                }else if(String.valueOf(total.get("status")).equals("02")){
                    map.put("status", "已删除");
                }else {
                    map.put("status", "已分配");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("区号");
        titles.add("虚拟号码");
        titles.add("状态");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("area_code");
        columns.add("number");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "用户号码资源池列表");
        map.put("excelName","用户号码资源池列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 导入用户号码
     * @return
     */
    @RequestMapping("/importAxbCustNumber")
    public String importAxbCustNumber(HttpServletRequest request, Model model){
        logger.info("----AxbCustNumResPoolController importAxbCustNumber start-------");
        model.addAttribute("appid", request.getParameter("appid"));
        model.addAttribute("sid", request.getParameter("sid"));
        model.addAttribute("uid", request.getParameter("uid"));
        return "axbNumber/importAxbCustNumber";
    }

    /**
     * 上传AxbNumber Excel文件信息
     * @param axbCustNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/axbCustNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody

    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile axbCustNumFile, HttpServletRequest request){
        logger.info("--------------------------------AxbCustNumResPoolController upload start--------------------------------");
        String appid = request.getParameter("appid");
        if(axbCustNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            AxbAppPool axbAppPoolResult = axbCustNumResPoolService.getAxbCustAppPoolByObj(new AxbAppPool(appid));
            //向用户号码池中添加信息
            if(axbAppPoolResult == null){
                AxbAppPool axbAppPool = new AxbAppPool();
                axbAppPool.setId(ID.randomUUID());
                axbAppPool.setSid(request.getParameter("sid"));
                axbAppPool.setUid(request.getParameter("uid"));
                axbAppPool.setAppid(appid);
                axbCustNumResPoolService.addAxbCustAppPool(axbAppPool);
            }
            try {
                List<AxbPhone> insertAxbCustNumErrorList = saveExcel(axbCustNumFile, request,appid);
                if (insertAxbCustNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    public List<AxbPhone> saveExcel(MultipartFile axbCustNumFile, HttpServletRequest request,String appid) throws Exception{
        List<AxbPhone> axbPhoneList = new ArrayList<>();
        InputStream is = axbCustNumFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for(Integer key : mapFile.keySet()){
            String[] arrayStr = mapFile.get(key).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                //设置axbCustNumber信息
                AxbPhone axbPhone = new AxbPhone();
                axbPhone.setAreacode(arrayStr[0]);
                axbPhone.setNumber(arrayStr[1]);

                axbPhoneList.add(axbPhone);
            }
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<AxbPhone> insertAxbCustNumberErrorList = new ArrayList<>();
        for (AxbPhone axbPhone:axbPhoneList){
            if(Tools.isNullStr(axbPhone.getAreacode())){
                axbPhone.setImportCommon("区号为空");
                insertAxbCustNumberErrorList.add(axbPhone);
                continue;
            }
            if(Tools.isNullStr(axbPhone.getNumber())){
                axbPhone.setImportCommon("号码为空");
                insertAxbCustNumberErrorList.add(axbPhone);
                continue;
            }
            TelnoCity city = cityManagerService.getTelnoCityByAreaCode(axbPhone.getAreacode());
            if(city==null){
                axbPhone.setImportCommon("区号不存在");
                insertAxbCustNumberErrorList.add(axbPhone);
                continue;
            }
            AxbPhone paramAxbPhone = new AxbPhone();
            paramAxbPhone.setCityid(city.getCcode());
            paramAxbPhone.setNumber(axbPhone.getNumber());
            List<AxbPhone> axbPhoneReturn = axbNumResPoolService.getAxbNumResPoolByAxbPhone(paramAxbPhone);


            if (axbPhoneReturn != null && axbPhoneReturn.size()>0) {
                if("01".equals(axbPhoneReturn.get(0).getStatus())){
                    axbPhone.setImportCommon("号码已分配");
                    insertAxbCustNumberErrorList.add(axbPhone);
                    continue;
                }else if("02".equals(axbPhoneReturn.get(0).getStatus())){
                    axbPhone.setImportCommon("号码已锁定");
                    insertAxbCustNumberErrorList.add(axbPhone);
                    continue;
                }else {
                    //成功
                    AxbCustNumber axbCustNumber = new AxbCustNumber();
                    axbCustNumber.setAppid(appid);
                    axbCustNumber.setAxbNumId(axbPhoneReturn.get(0).getId());
                    axbCustNumber.setStatus("00");
                    //更改公共号码池号码为已分配
                    AxbPhone axbPhoneSucc = axbPhoneReturn.get(0);
                    axbPhoneSucc.setStatus("01");
                    axbCustNumResPoolService.addAxbCustNumberPool(axbCustNumber,axbPhoneSucc);
                }
            } else {
                axbPhone.setImportCommon("区号、号码不在公共号码资源池中");
                insertAxbCustNumberErrorList.add(axbPhone);
                continue;
            }

        }
        request.getSession().setAttribute("insertAxbCustNumberErrorList", insertAxbCustNumberErrorList);
        return insertAxbCustNumberErrorList;
    }

    /**
     * 下载AxbCustNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorAxbCustNum")
    public ModelAndView downLoadErrorAxbCustNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------AxbCustNumResPoolController downLoadErrorAxbCustNum start--------------------------------");
        List<AxbPhone> insertAxbCustNumErrorList = (List<AxbPhone>) request.getSession().getAttribute("insertAxbCustNumberErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertAxbCustNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    public Map<String, Object> downLoadExcel(List<AxbPhone> insertAxbCustNumErrorList) {
        logger.info("--------------------------------AxbCustNumResPoolController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (AxbPhone axbPhone : insertAxbCustNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", axbPhone.getAreacode());
            excelMap.put("number", axbPhone.getNumber());
            excelMap.put("importCommon", axbPhone.getImportCommon());
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
     * 删除客户号码池
     * @param request
     * @return
     */
    @RequestMapping("/deleteAxbAppPool")
    @ResponseBody
    public JSonMessageSubmit deleteAxbAppPool(HttpServletRequest request){
        logger.info("----AxbCustNumResPoolController deleteAxbAppPool start-------");
        //删除号码池的信息
        AxbAppPool axbAppPool = new AxbAppPool();
        axbAppPool.setId(request.getParameter("id"));
        AxbAppPool axbAppPoolResult = axbCustNumResPoolService.getAxbCustAppPoolByObj(axbAppPool);
        if(axbAppPoolResult != null){
            boolean hasNum = axbCustNumResPoolService.checkHasNumber(request.getParameter("appid"));
            if(hasNum){
                axbCustNumResPoolService.deleteAxbCustAppPoolById(request.getParameter("id"));
                return new JSonMessageSubmit("0","删除数据成功！");
            }else {
                return new JSonMessageSubmit("1","号码池中仍有号码，删除数据失败！");
            }
        }else {
            return new JSonMessageSubmit("1","删除数据失败！");
        }
    }

    /**
     * 删除客户号码
     * @param request
     * @return
     */
    @RequestMapping("/deleteAxbCustNumber")
    @ResponseBody
    public JSonMessageSubmit deleteAxbCustNumber(HttpServletRequest request){
        logger.info("----AxbCustNumResPoolController deleteAxbCustNumber start-------");
        String strId = request.getParameter("strId");
        String[] aa = strId.split(",");
        StringBuilder phone = new StringBuilder();
        boolean hasRelation = false;
        for(String id : aa) {
            AxbCustNumber axbCustNumber = new AxbCustNumber();
            axbCustNumber.setId(id);
            List<AxbCustNumber> axbCustNumberReturn = axbCustNumResPoolService.getAxbCustNumberPoolByObj(axbCustNumber);
            if(axbCustNumberReturn.get(0)!=null){
                AxbPhone axbPhone = axbNumResPoolService.getAxbPhoneById(axbCustNumberReturn.get(0).getAxbNumId());
                if(axbPhone!=null){
                    AxbNumRelation axbNumRelation = axbCustNumResPoolService.getAxbNumRelationByNum(axbPhone.getNumber());
                    //未绑定为小号
                    if(axbNumRelation == null){
                        //公共号码池号码状态变为待分配
                        axbPhone.setStatus("00");
                        axbNumResPoolService.updateAxbPhoneByStatus(axbPhone);
                        //没绑定小号，直接物理删除
                        axbCustNumResPoolService.deleteAxbCustNum(axbCustNumber);
                    }else {
                        hasRelation = true;
                        phone.append(axbPhone.getNumber()).append(",");
                        Date nowDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(nowDate);
                        c.add(Calendar.HOUR, 24);
                        Date ridDate = c.getTime();
                        if(ridDate.before(axbNumRelation.getOuttime())||ridDate.equals(axbNumRelation.getOuttime())){
                            axbNumRelation.setLocktime(nowDate);
                            axbNumRelation.setRidtime(ridDate);
                            axbCustNumResPoolService.updateAxbNumRelationByObj(axbNumRelation);
                            //公共号码池号码状态变为已锁定
                            axbPhone.setStatus("02");
                            axbNumResPoolService.updateAxbPhoneByStatus(axbPhone);
                            //用户号码池号码状态变为已锁定
                            axbCustNumber.setStatus("01");
                            axbCustNumResPoolService.updateAxbCustNumByStatus(axbCustNumber);
                        }else {
                            axbNumRelation.setLocktime(nowDate);
                            axbCustNumResPoolService.updateAxbNumRelationByObj(axbNumRelation);
                            //公共号码池号码状态变为已锁定
                            axbPhone.setStatus("02");
                            axbNumResPoolService.updateAxbPhoneByStatus(axbPhone);
                            //用户号码池号码状态变为已锁定
                            axbCustNumber.setStatus("01");
                            axbCustNumResPoolService.updateAxbCustNumByStatus(axbCustNumber);
                        }
                    }
                }else {
                    return new JSonMessageSubmit("0","删除数据失败！");
                }
            }else {
                return new JSonMessageSubmit("0","删除数据失败！");
            }
        }
        if(hasRelation){
            return new JSonMessageSubmit("0",phone.substring(0,phone.length()-1)+"号码已绑定为小号，不能删除，已进行锁定！");
        }else {
            return new JSonMessageSubmit("0","删除数据成功！");
        }

    }
}
