package com.e9cloud.pcweb.relaygroup.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.domain.RelayGroup3;
import com.e9cloud.mybatis.service.RelayGroup3Service;
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
 * 中继群主叫强显号段表
 * Created by Administrator on 2016/9/9.
 */
@Controller
@RequestMapping("/relaygroup3")
public class RelayGroup3Controller extends BaseController {

    @Autowired
    private RelayGroup3Service relayGroup3Service;

    @Autowired
    private RelayGroupService relayGroupService;

    // 中继群 页面3
    @RequestMapping("index")
    public String index () {
        return "relayGroup/group3";
    }

    // 分页查询 中继群
    @RequestMapping("pageGroup3")
    @ResponseBody
    public PageWrapper pageGroup3 (Page page) {
        logger.info("-----------RelayGroup3Controller pageGroup3--------------------");
        return relayGroup3Service.pageGroup3(page);
    }

    /**
     * 删除头域信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteRelayGroup3")
    @ResponseBody
    public JSonMessageSubmit deleteRelayGroup3(HttpServletRequest request){
        logger.info("=====================================SupplierController deleteSupplier Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup3 relayGroup2 = relayGroup3Service.findRelayGroup3ById(Integer.parseInt(id));
        if(relayGroup2 != null){

            relayGroup3Service.deleteRelayGroup3(Integer.parseInt(id));
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }


    @RequestMapping("/RelayGroup3EditView")
    public String relayGroup2EditView(HttpServletRequest request, Model model){
        logger.info("=====================================RelayGroup3rController relayGroup2EditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        RelayGroup3  relayGroup3 = relayGroup3Service.findRelayGroup3ById(Integer.parseInt(id));
        model.addAttribute("operationType",operationType);
        model.addAttribute("relayGroup3",relayGroup3);
//        getInfoAppendToModel(model);
        return "relayGroup/group3EditView";
    }
    /**
     * 修改中继群信息
     * @param request
     * @return
     */
    @RequestMapping("/updateRelayGroup3")
    @ResponseBody
    public JSonMessageSubmit updateCity(HttpServletRequest request, RelayGroup3 relayGroup2){
        logger.info("=====================================RelayGroup3rController updateCity Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup3 relayGroup2R = relayGroup3Service.findRelayGroup3ById(Integer.parseInt(id));
        if(relayGroup2R != null){
            relayGroup3Service.updateRelayGroup3(relayGroup2);
            return new JSonMessageSubmit("0","信息修改成功！");
        }else{
            return new JSonMessageSubmit("1","不存在该中继群，信息保存失败！");
        }
    }

    @RequestMapping("/toAddRelayGroup3")
    public String toAddRelayGroup3(Model model){
        logger.info("=====================================RelayGroup3Controller toAddRelayGroup3 Execute=====================================");
        return "relayGroup/group3Add";
    }
    /**
     * 添加中继群信息
     * @param request
     * @return
     */
    @RequestMapping("/saveRelayGroup3")
    @ResponseBody
    public JSonMessageSubmit saveRelayGroup3(HttpServletRequest request, RelayGroup3 relayGroup2){
        logger.info("=====================================RelayGroup3Controller saveRelayGroup3 Execute=====================================");
        RelayGroup3 supplierReturn = relayGroup3Service.findRelayGroup3ByName(relayGroup2.getTgName());
        if(null!=supplierReturn){
            return new JSonMessageSubmit("1","中继群名称已存在，信息保存失败！");
        }
        relayGroup3Service.saveRelayGroup3(relayGroup2);
        return new JSonMessageSubmit("0","信息保存成功！");
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadRelayGroup3")
    public ModelAndView downloadRelayGroup3(Page page) {
        logger.info("=====================================RelayGroup3Controller downloadRoleInfo Execute=====================================");
        List<Map<String, Object>> totals = relayGroup3Service.downloadRelayGroup3(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();
//            tempMap.put("rowNO", map.get("rowNO").toString());
            tempMap.put("rowNO", map.get("rowNO").toString().substring(0,map.get("rowNO").toString().length()-2));
            tempMap.put("tgNum", map.get("tgNum").toString()==null?"":map.get("tgNum").toString());
            tempMap.put("tgName", map.get("tgName")==null?"":map.get("tgName"));
            tempMap.put("codeStart", map.get("codeStart")==null?"":map.get("codeStart"));
            tempMap.put("codeEnd", map.get("codeEnd")==null?"":map.get("codeEnd"));
            list.add(tempMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("中继群编号");
        titles.add("中继群名");
        titles.add("号段起始号码");
        titles.add("号段结束号码");
        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("tgNum");
        columns.add("tgName");
        columns.add("codeStart");
        columns.add("codeEnd");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "主叫强显号段");
        map.put("excelName","主叫强显号段");
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
                List<RelayGroup3> group3ErrorList = relayGroupService.saveExcel3(relayGroupFile, request);
                if (group3ErrorList.size() > 0) {
                    addAttributeToSession(request, "group3ErrorList", group3ErrorList);
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
        logger.info("--------------------------------RelayGroup3Controller downLoadErrorList start--------------------------------");
        List<RelayGroup3> insertErrorList = (List<RelayGroup3>) getAttributeFromSession(request, "group3ErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertErrorList);

        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<RelayGroup3> relayGroups) {
        logger.info("--------------------------------RelayGroup3Controller downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (RelayGroup3 relayGroup : relayGroups) {
            Map<String, Object> excelMap = new HashMap<>();

            excelMap.put("tgNum", relayGroup.getTgNum());
            excelMap.put("tgName", relayGroup.getTgName());
            excelMap.put("codeStart", relayGroup.getCodeStart());
            excelMap.put("codeEnd", relayGroup.getCodeEnd());

            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("中继群编号");
        titles.add("中继群名");
        titles.add("号段起始号码");
        titles.add("号段结束号码");

        List<String> columns = new ArrayList<String>();
        columns.add("tgNum");
        columns.add("tgName");
        columns.add("codeStart");
        columns.add("codeEnd");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title", "中继群头域号段错误信息");
        contentMap.put("excelName", "中继群头域号段错误信息");

        return contentMap;
    }

    // 导入表格
    @RequestMapping("toImportGroup3")
    public String toImportGroup3(Model model, String g) {
        model.addAttribute("g", g);
        return "relayGroup/importAppointLink";
    }

}
