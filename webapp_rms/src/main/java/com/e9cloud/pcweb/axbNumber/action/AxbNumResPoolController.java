package com.e9cloud.pcweb.axbNumber.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.R;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.service.AxbNumImportService;
import com.e9cloud.mybatis.service.AxbNumResPoolService;
import com.e9cloud.mybatis.service.CityManagerService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/4/18.
 */
@Controller
@RequestMapping("/axbNumResPool")
public class AxbNumResPoolController extends BaseController {
    @Autowired
    private AxbNumResPoolService axbNumResPoolService;

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private AxbNumImportService axbNumImportService;

    /**
     * 公共号码资源池管理界面
     */
    @RequestMapping("/axbNumResPool")
    public String axbPubNumResPoolMgr(HttpServletRequest request,Model model){
        Map<String, Object> params = new HashMap<>();
        List<TelnoCity> cityList = axbNumResPoolService.getCitys(params);
        model.addAttribute("cityList", cityList);
        return "axbNumber/axbNumResPoolMgr";
    }

    /**
     * 公共号码SipPhone资源表
     * @param page
     * @return
     */
    @RequestMapping("/getSipPhoneList")
    @ResponseBody
    public PageWrapper getSipPhoneList(Page page){
        logger.info("----AxbNumResPoolController getAxbNumList start-------");
        PageWrapper pageWrapper = axbNumResPoolService.getAxbNumList(page);
        return pageWrapper;
    }

    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/toAddPhone")
    public String toAddAxbPhone(HttpServletRequest request, Model model){
        return "axbNumber/addAxbPhone";
    }

    /**
     * 校验公共号码池-区号号码匹配
     * @return
     */
    @RequestMapping("checkAxbNumber")
    @ResponseBody
    public JSonMessage checkSipPhone(AxbPhone axbPhone){
        boolean checkAxbNumber = axbNumResPoolService.checkAxbNumberMatchAreacode(axbPhone);
        if(checkAxbNumber){
            return new JSonMessage(R.ERROR, "区号号码不匹配");
        }
        List<AxbPhone> axbPhoneReturn = axbNumResPoolService.getAxbNumResPoolByAxbPhone(axbPhone);
        if(null!=axbPhoneReturn && axbPhoneReturn.size()>0){
            return new JSonMessage(R.ERROR,"虚拟号码已存在，信息保存失败！");
        }
        return new JSonMessage(R.OK, "");

    }


    /**
     * 单个添加公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/addAxbPhone")
    @ResponseBody
    public JSonMessageSubmit addAxbPhone(HttpServletRequest request, AxbPhone axbPhone){
        logger.info("=====================================AxbNumResPoolController addAxbPhone Execute=====================================");
        TelnoCity city = cityManagerService.getTelnoCityByAreaCode(axbPhone.getAreacode());
        axbPhone.setCityid(city.getCcode());
        axbPhone.setStatus("00");
        axbNumResPoolService.addAxbPhone(axbPhone);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 导入号码
     * @return
     */
    @RequestMapping("/importPhone")
    public String importPhone(HttpServletRequest request, Model model){
        return "axbNumber/importAxbPhones";
    }

    /**
     * 上传SipPhone Excel文件信息
     * @param AxbPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/AxbPhoneExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit uploadSipPhoneExcel(@RequestParam(required = false, value = "file") MultipartFile AxbPhoneFile, HttpServletRequest request){

        if(AxbPhoneFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<AxbPhone> insertAxbPhoneErrorList = axbNumImportService.saveAxbPhoneExcel(AxbPhoneFile, request);
                if (insertAxbPhoneErrorList.size() > 0) {
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
     * 下载AXBNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorAxbPhone")
    public ModelAndView downLoadErrorAxbPhone(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------AxbNumResPoolController downLoadErrorAxbPhone start--------------------------------");
        List<AxbPhone> insertAxbPhoneErrorList = (List<AxbPhone>) request.getSession().getAttribute("insertAxbPhoneErrorList");
        Map<String, Object> contentMap = downLoadAxbPhoneExcel(insertAxbPhoneErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadAxbPhoneExcel(List<AxbPhone> insertSipPhoneErrorList) {
        logger.info("--------------------------------AxbNumResPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (AxbPhone axbPhone : insertSipPhoneErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", axbPhone.getAreacode());
            excelMap.put("number", axbPhone.getNumber());
            excelMap.put("importCommon", axbPhone.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("区号");
        titles.add("虚拟号码");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("number");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","虚拟号码错误信息");
        contentMap.put("excelName", "虚拟号码错误信息");

        return contentMap;
    }

    /**
     * 导出SIP号码
     *
     * @return
     */
    @RequestMapping(value = "downloadAxbNumPool", method = RequestMethod.GET)
    public ModelAndView downloadAxbNumPool(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = axbNumResPoolService.downloadAxbNumPool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
                map.put("area_code", String.valueOf(total.get("area_code")).equals("null")?"":String.valueOf(total.get("area_code")));
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
        titles.add("虚拟号码");
        titles.add("城市");
        titles.add("区号");
        titles.add("状态");
        titles.add("修改时间");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("Appid");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("number");
        columns.add("cname");
        columns.add("area_code");
        columns.add("status");
        columns.add("updatetime");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appId");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "虚拟号码公共号码资源池");
        map.put("excelName","虚拟号码公共号码资源池");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 批量删除公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("deletePhones")
    @ResponseBody
    public Map<String,String> deleteAxbPhones (HttpServletRequest request) {
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
            AxbPhone axbPhone = axbNumResPoolService.getAxbPhoneById(id);
            if(axbPhone!=null){
                if("01".equals(axbPhone.getStatus())){
                    phone.append(axbPhone.getNumber()).append(",");
                }else if("02".equals(axbPhone.getStatus())){
                    phone.append(axbPhone.getNumber()).append(",");
                }else {
                    axbNumResPoolService.deleteAxbPhoneByIds(id.split(","));
                }
            }else{
                map.put("code","02");
                map.put("msg","号码不存在");
            }
        }
        if(phone.length()>1){
            map.put("code","02");
            map.put("msg",phone.substring(0,phone.length()-1)+"号码已分配或已锁定，不能删除！");
        }else {
            map.put("code","00");
        }
        return map;
    }

}
