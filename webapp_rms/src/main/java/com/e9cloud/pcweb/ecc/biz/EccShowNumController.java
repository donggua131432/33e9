package com.e9cloud.pcweb.ecc.biz;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.R;
import com.e9cloud.mybatis.domain.EccShownum;
import com.e9cloud.mybatis.service.EccShowNumPoolService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.ecc.action.EccShowNumImportService;
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
@RequestMapping("/eccShowNumPool")
public class EccShowNumController extends BaseController {

    @Autowired
    private EccShowNumPoolService eccShowNumPoolService;

    @Autowired
    private EccShowNumImportService importService;

    /**
     * 外显号码管理界面
     * @return
     */
    @RequestMapping("/eccShowNumPool")
    public String pubNumResPoolMgr(HttpServletRequest request, Model model){
        return "ecc/eccShowNumPoolMgr";
    }

    /**
     * 外显号码资源表
     * @param page
     * @return
     */
    @RequestMapping("/pageEccShowNumList")
    @ResponseBody
    public PageWrapper pageAppList(Page page){
        logger.info("----EccShowNumController pageEccShowNumList start-------");
        PageWrapper pageWrapper = eccShowNumPoolService.getEccShowNumList(page);
        return pageWrapper;
    }

    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAddSipPhone(HttpServletRequest request, Model model){
        return "ecc/addEccShowNum";

    }

    /**
     * 添加公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/addEccShowNum")
    @ResponseBody
    public JSonMessageSubmit addEccShowNum(HttpServletRequest request, EccShownum eccShowNum){
        logger.info("=====================================EccShowNumController addEccShowNum Execute==================================");
        List<EccShownum> eccShownumsReturn = eccShowNumPoolService.getEccShowNumByNumber(eccShowNum.getNumber());
        if(null!=eccShownumsReturn && eccShownumsReturn.size()>0){
            return new JSonMessageSubmit("1","外显号码已存在，信息保存失败！");
        }
        eccShowNumPoolService.addEccShowNum(eccShowNum);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 批量删除公共号码资源池
     * @param request
     * @return
     */
    @RequestMapping("/deleteEccShowNums")
    @ResponseBody
    public Map<String,String> deleteEccShowNums (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        map.put("code","00");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        StringBuilder number = new StringBuilder();
        String[] aa = strId.split(",");

        for(String id : aa){
            if(eccShowNumPoolService.getEccShowNumById(id)!=null&&eccShowNumPoolService.getEccShowNumById(id).size()>0){
                EccShownum eccShownum = eccShowNumPoolService.getEccShowNumById(id).get(0);
                if(eccShownum.getStatus().equals("01")){
                    number.append(eccShownum.getNumber()).append(",");
                }else {
                    eccShowNumPoolService.deleteEccShowNumByIds(id.split(","));
                }
            }
        }

        if(number.length()>1){
            map.put("code","02");
            map.put("msg",number.substring(0,number.length()-1)+"号码已分配不能删除");
        }else {
            map.put("code","00");
        }
        return map;
    }

    /**
     * 号码修改页面
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(HttpServletRequest request, Model model){
        String id = request.getParameter("id")!=null?request.getParameter("id").trim():"";

        List<EccShownum> list = eccShowNumPoolService.getEccShowNum(id);
        EccShownum eccShownum = new EccShownum();
        if(list!=null&&list.size()>0){
            eccShownum = list.get(0);
        }
        model.addAttribute("eccShownum", eccShownum);
        return "ecc/editEccShowNum";

    }

    /**
     * 校验number的唯一性
     * @return
     */
    @RequestMapping("checkEccShowNum")
    @ResponseBody
    public JSonMessage checkEccShowNum(String number){
        List<EccShownum> eccShownumsReturn = eccShowNumPoolService.getEccShowNumByNumber(number);
        if(eccShownumsReturn.size() > 0){
            return new JSonMessage(R.ERROR, "外显号码已存在");
        }
        return new JSonMessage(R.OK, "");
    }

    /**
     * 校验number的唯一性
     * @return
     */
    @RequestMapping("checkEditEccShowNum")
    @ResponseBody
    public JSonMessage checkEditEccShowNum(String number,String id){
        List<EccShownum> eccShownumsReturn = eccShowNumPoolService.getEccShowNumByNumber(number);
        if(null!=eccShownumsReturn && eccShownumsReturn.size()>0){
            EccShownum returnEccShownum = eccShownumsReturn.get(0);
            if(!returnEccShownum.getId().equals(id)){
                return new JSonMessage(R.ERROR,"外显号码已存在！");
            }

        }

        List<EccShownum> list = eccShowNumPoolService.getEccShowNum(id);
        EccShownum eccShownum = new EccShownum();
        if(list!=null&&list.size()>0){
            eccShownum = list.get(0);
        }
        if(eccShownum.getStatus().equals("01")){
            return new JSonMessage(R.ERROR,"外显信息已分配！");
        }
        return new JSonMessage(R.OK, "");
    }

    // 修改ECC配置
    @RequestMapping("/updateEccShowNum")
    @ResponseBody
    public JSonMessage updateEccShowNum(HttpServletRequest request, EccShownum eccShowNum) {

        String id = request.getParameter("id")!=null?request.getParameter("id").trim():"";

        List<EccShownum> eccShownumsReturn = eccShowNumPoolService.getEccShowNumByNumber(eccShowNum.getNumber());
        if(null!=eccShownumsReturn && eccShownumsReturn.size()>0){
            EccShownum returnEccShownum = eccShownumsReturn.get(0);
            if(!returnEccShownum.getId().equals(eccShowNum.getId())){
                return new JSonMessage(R.ERROR,"外显号码已存在，信息保存失败！");
            }


        }

        List<EccShownum> list = eccShowNumPoolService.getEccShowNum(id);
        EccShownum eccShownum = new EccShownum();
        if(list!=null&&list.size()>0){
            eccShownum = list.get(0);
        }
        if(eccShownum.getStatus().equals("01")){
            return new JSonMessage(R.ERROR,"外显信息已分配，信息保存失败！");
        }
        eccShowNumPoolService.updateEccShowNum(eccShowNum);

        return new JSonMessage(R.OK, "外显号码修改成功！");

    }

    /**
     * 导出号码
     *
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public ModelAndView download(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals = eccShowNumPoolService.downloadEccShowNumList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total: totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("rowNO", Double.valueOf(total.get("rowNO").toString()).intValue());
                map.put("number", String.valueOf(total.get("number")).equals("null")?"":String.valueOf(total.get("number")));
                map.put("pname", String.valueOf(total.get("pname")).equals("null")?"":String.valueOf(total.get("pname")));
                map.put("cname", String.valueOf(total.get("cname")).equals("null")?"":String.valueOf(total.get("cname")));
//                map.put("status", String.valueOf(total.get("status")));
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
        titles.add("外显号码");
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
        map.put("title", "外显号码列表");
        map.put("excelName","外显号码列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 导入号码
     * @return
     */
    @RequestMapping("/import")
    public String importPhone(HttpServletRequest request, Model model){
        return "ecc/importEccShowNum";
    }

    /**
     * 上传SipPhone Excel文件信息
     * @param EccShowNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/EccShowNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit uploadSipPhoneExcel(@RequestParam(required = false, value = "file") MultipartFile EccShowNumFile, HttpServletRequest request){

        if(EccShowNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<EccShownum> insertEccShownumErrorList = importService.saveEccShowNumExcel(EccShowNumFile, request);
                if (insertEccShownumErrorList.size() > 0) {
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
    @RequestMapping("/downLoadErrorEccShowNum")
    public ModelAndView downLoadErrorEccShowNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------EccShowNumController downLoadErrorEccShowNum start--------------------------------");
        List<EccShownum> insertEccShownumErrorList = (List<EccShownum>) request.getSession().getAttribute("insertEccShownumErrorList");
        Map<String, Object> contentMap = downLoadEccShowNumExcel(insertEccShownumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadEccShowNumExcel(List<EccShownum> insertEccShownumErrorList) {
        logger.info("--------------------------------EccShowNumController downLoadEccShowNumExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (EccShownum eccShownum : insertEccShownumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", eccShownum.getAreaCode());
            excelMap.put("number", eccShownum.getNumber());
            excelMap.put("importCommon", eccShownum.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("区号");
        titles.add("号码");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("number");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","外显号码错误信息");
        contentMap.put("excelName", "外显号码错误信息");

        return contentMap;
    }

}
