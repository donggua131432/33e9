package com.e9cloud.pcweb.supplier;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wangyu on 2016/8/4.
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseController{
    @Autowired
    private SupplierInfoService supplierInfoService;



    @RequestMapping("/supplierList")
    public String supplierIndex(Model model){
        logger.info("=====================================SupplierController supplierIndex Execute=====================================");
//        getInfoAppendToModel(model);
        return "supplier/supplierList";
    }
    /**
     * 分页查询供应商信息
     * @param page
     * @return
     */
    @RequestMapping("/pageSupplierList")
    @ResponseBody
    public PageWrapper pageSupplierList(Page page){
        logger.info("=====================================SupplierController pageSupplierList Execute=====================================");
        return supplierInfoService.pageSupplierList(page);
    }
    @RequestMapping("/addSupplier")
    public String addSupplier(Model model){
        logger.info("=====================================SupplierController addSupplier Execute=====================================");
        return "supplier/supplierAdd";
    }
    @RequestMapping("/supplierEditView")
    public String supplierEditView(HttpServletRequest request, Model model){
        logger.info("=====================================SupplierController supplierEditView Execute=====================================");
        String supId = request.getParameter("supId");
        String operationType = request.getParameter("operationType");
        Supplier supplier = supplierInfoService.findSupplierById(supId);
        model.addAttribute("operationType",operationType);
        model.addAttribute("supplier",supplier);
//        getInfoAppendToModel(model);
        return "supplier/supplierEditView";
    }
    /**
     * 修改供应商信息
     * @param request
     * @return
     */
    @RequestMapping("/updateSupplier")
    @ResponseBody
    public JSonMessageSubmit updateCity(HttpServletRequest request, Supplier supplier){
        logger.info("=====================================SupplierrController updateCity Execute=====================================");
        String supId = request.getParameter("supId");
        Supplier supplierR = supplierInfoService.findSupplierById(supId);
        if(supplierR != null){
            supplierInfoService.updateSupplierInfo(supplier);
            return new JSonMessageSubmit("0","信息保存成功！");
        }else{
            return new JSonMessageSubmit("1","不存在该供应商，信息保存失败！");
        }
    }

    /**
     * 删除供应商信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteSupplier")
    @ResponseBody
    public JSonMessageSubmit deleteSupplier(HttpServletRequest request){
        logger.info("=====================================SupplierController deleteSupplier Execute=====================================");
        String id = request.getParameter("id");
        Supplier supplier = supplierInfoService.findSupplierById(id);
        if(supplier != null){

            supplierInfoService.deleteSupplierInfo(id);
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }
    /**
     * 添加供应商信息
     * @param request
     * @return
     */
    @RequestMapping("/saveSupplier")
    @ResponseBody
    public JSonMessageSubmit saveSupplier(HttpServletRequest request, Supplier supplier){
        logger.info("=====================================SupplierController saveSupplier Execute=====================================");
        Supplier supplierReturn = supplierInfoService.findSupplierByName(supplier.getSupName());
        if(null!=supplierReturn){
            return new JSonMessageSubmit("1","供应商名称已存在，信息保存失败！");
        }
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        supplier.setSupId(uuid);
        supplierInfoService.saveSupplierInfo(supplier);
        return new JSonMessageSubmit("0","信息保存成功！");
    }


    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadSupplierInfo")
    public ModelAndView downloadSupplierInfo(Page page) {
        logger.info("=====================================SupplierController downloadSupplierInfo Execute=====================================");
        List<Map<String, Object>> totals = supplierInfoService.downloadSupplierInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();
//            tempMap.put("rowNO", map.get("rowNO").toString());
            tempMap.put("rowNO", map.get("rowNO").toString().substring(0,map.get("rowNO").toString().length()-2));
            tempMap.put("supName", map.get("supName").toString()==null?"":map.get("supName").toString());
            tempMap.put("supId", map.get("supId").toString()==null?"":map.get("supId").toString());

            tempMap.put("contacts", map.get("contacts")==null?"":map.get("contacts"));
            if(map.get("payType")!=null){
                tempMap.put("payType", map.get("payType").equals("00")?"预付":"月结");
            }
            tempMap.put("mobile", map.get("mobile")==null?"":map.get("mobile"));
            list.add(tempMap);
        });
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("供应商名称");
        titles.add("供应商ID");
        titles.add("结算方式");
        titles.add("联系人");
        titles.add("联系方式");
        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("supName");
        columns.add("supId");
        columns.add("payType");
        columns.add("contacts");
        columns.add("mobile");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "供应商信息列表");
        map.put("excelName","供应商信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }
}