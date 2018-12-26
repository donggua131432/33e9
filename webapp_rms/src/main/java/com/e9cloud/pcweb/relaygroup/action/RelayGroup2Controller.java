package com.e9cloud.pcweb.relaygroup.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.domain.RelayGroup2;
import com.e9cloud.mybatis.service.RelayGroup2Service;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.relaygroup.biz.RelayGroupService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中继群运营商被叫前缀
 * Created by Administrator on 2016/9/9.
 */
@Controller
@RequestMapping("/relaygroup2")
public class RelayGroup2Controller extends BaseController {

    @Autowired
    private RelayGroup2Service relayGroup2Service;

    @Autowired
    private RelayGroupService relayGroupService;

    // 中继群 页面2
    @RequestMapping("index")
    public String index () {
        return "relayGroup/group2";
    }

    // 分页查询 中继群
    @RequestMapping("pageGroup2")
    @ResponseBody
    public PageWrapper pageGroup2 (Page page) {
        logger.info("-----------RelayGroup2Controller pageGroup2--------------------");
        return relayGroup2Service.pageGroup2(page);
    }

    /**
     * 删除头域信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteRelayGroup2")
    @ResponseBody
    public JSonMessageSubmit deleteRelayGroup2(HttpServletRequest request){
        logger.info("=====================================SupplierController deleteSupplier Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup2 relayGroup2 = relayGroup2Service.findRelayGroup2ById(Integer.parseInt(id));
        if(relayGroup2 != null){

            relayGroup2Service.deleteRelayGroup2(Integer.parseInt(id));
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }


    @RequestMapping("/RelayGroup2EditView")
    public String relayGroup2EditView(HttpServletRequest request, Model model){
        logger.info("=====================================RelayGroup2rController relayGroup2EditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        RelayGroup2  relayGroup2 = relayGroup2Service.findRelayGroup2ById(Integer.parseInt(id));
        model.addAttribute("operationType",operationType);
        model.addAttribute("relayGroup2",relayGroup2);
//        getInfoAppendToModel(model);
        return "relayGroup/group2EditView";
    }
    /**
     * 修改中继群信息
     * @param request
     * @return
     */
    @RequestMapping("/updateRelayGroup2")
    @ResponseBody
    public JSonMessageSubmit updateCity(HttpServletRequest request, RelayGroup2 relayGroup2){
        logger.info("=====================================RelayGroup2rController updateCity Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup2 relayGroup2R = relayGroup2Service.findRelayGroup2ById(Integer.parseInt(id));
        if(relayGroup2R != null){
            relayGroup2Service.updateRelayGroup2(relayGroup2);
            return new JSonMessageSubmit("0","信息修改成功！");
        }else{
            return new JSonMessageSubmit("1","不存在该中继群，信息保存失败！");
        }
    }

    @RequestMapping("/toAddRelayGroup2")
    public String toAddRelayGroup2(Model model){
        logger.info("=====================================RelayGroup2Controller toAddRelayGroup2 Execute=====================================");
        return "relayGroup/group2Add";
    }

    /**
     * 添加中继群信息
     * @param request
     * @return
     */
    @RequestMapping("/saveRelayGroup2")
    @ResponseBody
    public JSonMessageSubmit saveRelayGroup2(HttpServletRequest request, RelayGroup2 relayGroup2){
        logger.info("=====================================RelayGroup2Controller saveRelayGroup2 Execute=====================================");
        RelayGroup2 supplierReturn = relayGroup2Service.findRelayGroup2ByName(relayGroup2.getTgName());
        if(null!=supplierReturn){
            return new JSonMessageSubmit("1","中继群名称已存在，信息保存失败！");
        }
        relayGroup2Service.saveRelayGroup2(relayGroup2);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadRelayGroup2")
    public ModelAndView downloadRelayGroup2(Page page) {
        logger.info("=====================================RoleController downloadRoleInfo Execute=====================================");
        List<Map<String, Object>> totals = relayGroup2Service.downloadRelayGroup2(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();
//            tempMap.put("rowNO", map.get("rowNO").toString());
            tempMap.put("rowNO", map.get("rowNO").toString().substring(0,map.get("rowNO").toString().length()-2));
            tempMap.put("tgNum", map.get("tgNum").toString()==null?"":map.get("tgNum").toString());
            tempMap.put("tgName", map.get("tgName")==null?"":map.get("tgName"));
            tempMap.put("operatorCode", map.get("operatorCode")==null?"":map.get("operatorCode"));
            tempMap.put("operatorName", map.get("operatorName")==null?"":map.get("operatorName"));
            tempMap.put("calledPre", map.get("calledPre")==null?"":map.get("calledPre"));

            list.add(tempMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("中继群编号");
        titles.add("中继群名");
        titles.add("运营商编号");
        titles.add("运营商名称");
        titles.add("被叫前缀");
        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("tgNum");
        columns.add("tgName");
        columns.add("operatorCode");
        columns.add("operatorName");
        columns.add("calledPre");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "运营商被叫前缀");
        map.put("excelName","运营商被叫前缀");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 上传SIP Number Excel文件信息
     * @param relayGroupFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/appointLinkExcelUpload", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile relayGroupFile, HttpServletRequest request){

        if(relayGroupFile == null){
            return new JSonMessageSubmit("0", "导入数据失败！");
        }else{
            try {
                List<RelayGroup2> group2ErrorList = relayGroupService.saveExcel2(relayGroupFile, request);
                if (group2ErrorList.size() > 0) {
                    addAttributeToSession(request, "group2ErrorList", group2ErrorList);
                    return new JSonMessageSubmit("2", "存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0", "导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1", "导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 下载SIPNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorList")
    public ModelAndView downLoadErrorList(HttpServletRequest request, String g) throws Exception{
        logger.info("--------------------------------RelayGroup1Controller downLoadErrorList start--------------------------------");
        List<RelayGroup2> insertErrorList = (List<RelayGroup2>) getAttributeFromSession(request, "group2ErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertErrorList);

        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<RelayGroup2> relayGroups) {
        logger.info("--------------------------------RelayGroup1Controller downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (RelayGroup2 relayGroup : relayGroups) {
            Map<String, Object> excelMap = new HashMap<>();

            excelMap.put("tgNum", relayGroup.getTgNum());
            excelMap.put("tgName", relayGroup.getTgName());
            excelMap.put("operatorCode", relayGroup.getOperatorCode());
            excelMap.put("operatorName", relayGroup.getOperatorName());
            excelMap.put("calledPre", relayGroup.getCalledPre());

            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("中继群编号");
        titles.add("中继群名");
        titles.add("运营商编号");
        titles.add("运营商名称");
        titles.add("被叫前缀");

        List<String> columns = new ArrayList<String>();
        columns.add("tgNum");
        columns.add("tgName");
        columns.add("operatorCode");
        columns.add("operatorName");
        columns.add("calledPre");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title", "中继群运营商被叫前缀错误信息");
        contentMap.put("excelName", "中继群运营商被叫前缀错误信息");

        return contentMap;
    }

    // 导入表格
    @RequestMapping("toImportGroup2")
    public String toImportGroup2(Model model, String g) {
        model.addAttribute("g", g);
        return "relayGroup/importAppointLink";
    }
}
