package com.e9cloud.pcweb.relaygroup.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.service.RelayGroup1Service;
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
 * 中继群To头域号段
 * Created by Administrator on 2016/9/9.
 */
@Controller
@RequestMapping("/relaygroup1")
public class RelayGroup1Controller extends BaseController {

    @Autowired
    private RelayGroup1Service relayGroup1Service;

    @Autowired
    private RelayGroupService relayGroupService;

    // 中继群 页面1
    @RequestMapping("index")
    public String index () {
        return "relayGroup/group1";
    }

    // 分页查询 中继群
    @RequestMapping("pageGroup1")
    @ResponseBody
    public PageWrapper pageGroup1 (Page page) {
        logger.info("-----------RelayGroup1Controller pageGroup1--------------------");
        return relayGroup1Service.pageGroup1(page);
    }

    /**
     * 删除头域信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteRelayGroup1")
    @ResponseBody
    public JSonMessageSubmit deleteRelayGroup1(HttpServletRequest request){
        logger.info("=====================================RelayGroup1rController deleteSupplier Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup1 relayGroup1 = relayGroup1Service.findRelayGroup1ById(Integer.parseInt(id));
        if(relayGroup1 != null){

            relayGroup1Service.deleteRelayGroup1(Integer.parseInt(id));
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }


    @RequestMapping("/RelayGroup1EditView")
    public String relayGroup1EditView(HttpServletRequest request, Model model){
        logger.info("=====================================RelayGroup1rController relayGroup1EditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        RelayGroup1  relayGroup1 = relayGroup1Service.findRelayGroup1ById(Integer.parseInt(id));
        model.addAttribute("operationType",operationType);
        model.addAttribute("relayGroup1",relayGroup1);
//        getInfoAppendToModel(model);
        return "relayGroup/group1EditView";
    }

    /**
     * 修改中继群信息
     * @param request
     * @return
     */
    @RequestMapping("/updateRelayGroup1")
    @ResponseBody
    public JSonMessageSubmit updateCity(HttpServletRequest request, RelayGroup1 relayGroup1){
        logger.info("=====================================RelayGroup1rController updateCity Execute=====================================");
        String id = request.getParameter("id");
        RelayGroup1 relayGroup1R = relayGroup1Service.findRelayGroup1ById(Integer.parseInt(id));
        if(relayGroup1R != null){
            relayGroup1Service.updateRelayGroup1(relayGroup1);
            return new JSonMessageSubmit("0","信息修改成功！");
        }else{
            return new JSonMessageSubmit("1","不存在该中继群，信息保存失败！");
        }
    }

    @RequestMapping("/toAddRelayGroup1")
    public String toAddRelayGroup1(Model model){
        logger.info("=====================================RelayGroup1Controller toAddRelayGroup1 Execute=====================================");
        return "relayGroup/group1Add";
    }

   /**
    * 添加中继群信息
    * @param request
    * @return
    */
    @RequestMapping("/saveRelayGroup1")
    @ResponseBody
    public JSonMessageSubmit saveRelayGroup1(HttpServletRequest request, RelayGroup1 relayGroup1){
        logger.info("=====================================RelayGroup1Controller saveRelayGroup1 Execute=====================================");
        RelayGroup1 supplierReturn = relayGroup1Service.findRelayGroup1ByName(relayGroup1.getTgName());
        if(null!=supplierReturn){
            return new JSonMessageSubmit("1","中继群名称已存在，信息保存失败！");
        }
        relayGroup1Service.saveRelayGroup1(relayGroup1);
        return new JSonMessageSubmit("0","信息保存成功！");
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
                List<RelayGroup1> group1ErrorList = relayGroupService.saveExcel1(relayGroupFile, request);
                if (group1ErrorList.size() > 0) {
                    addAttributeToSession(request, "group1ErrorList", group1ErrorList);
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
    public ModelAndView downLoadErrorAppointLink(HttpServletRequest request, String g) throws Exception{
        logger.info("--------------------------------RelayGroup1Controller downLoadErrorList start--------------------------------");
        List<RelayGroup1> insertAppointLinkErrorList = (List<RelayGroup1>) getAttributeFromSession(request, "group1ErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertAppointLinkErrorList);

        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<RelayGroup1> relayGroups) {
        logger.info("--------------------------------RelayGroup1Controller downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (RelayGroup1 relayGroup : relayGroups) {
            Map<String, Object> excelMap = new HashMap<>();

            excelMap.put("tgNum", relayGroup.getTgNum());
            excelMap.put("tgName", relayGroup.getTgName());
            excelMap.put("cityName", relayGroup.getCityName());
            excelMap.put("areaCode", relayGroup.getAreaCode());
            excelMap.put("codeStart", relayGroup.getCodeStart());
            excelMap.put("codeEnd", relayGroup.getCodeEnd());

            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("中继群编号");
        titles.add("中继群名");
        titles.add("城市");
        titles.add("电话区号");
        titles.add("号段起始号码");
        titles.add("号段结束号码");

        List<String> columns = new ArrayList<String>();
        columns.add("tgNum");
        columns.add("tgName");
        columns.add("cityName");
        columns.add("areaCode");
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
    @RequestMapping("toImportGroup1")
    public String toImportGroup1(Model model, String g) {
        model.addAttribute("g", g);
        return "relayGroup/importAppointLink";
    }
}
