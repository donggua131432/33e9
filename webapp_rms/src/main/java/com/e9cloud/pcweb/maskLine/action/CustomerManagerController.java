package com.e9cloud.pcweb.maskLine.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.CustomerManager;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.service.CustomerManagerService;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.pcweb.BaseController;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/8.
 */
@Controller
@RequestMapping("/customerMgr")
public class CustomerManagerController extends BaseController{
    @Autowired
    private CustomerManagerService customerManagerService;

    @Autowired
    private IDicDataService dicDataService;

    @RequestMapping("/customerMgrIndex")
    public String customerMgrIndex(Model model){
        logger.info("=====================================CustomerManagerController customerMgrIndex Execute=====================================");
        getInfoAppendToModel(model);
        return "customerMgr/customerList";
    }

    @RequestMapping("/addCustomer")
    public String addCustomer(Model model){
        logger.info("=====================================CustomerManagerController addCustomer Execute=====================================");
        getInfoAppendToModel(model);
        return "customerMgr/customerAdd";
    }


    @RequestMapping("/customerEditView")
    public String customerEditView(HttpServletRequest request, Model model){
        logger.info("=====================================CustomerManagerController customerEditView Execute=====================================");
        String id = request.getParameter("id");
        String operationType = request.getParameter("operationType");
        CustomerManager customerManager = customerManagerService.findCustomerById(id);
        model.addAttribute("operationType",operationType);
        model.addAttribute("customerManager",customerManager);

        getInfoAppendToModel(model);

        return "customerMgr/customerEditView";
    }

    private Model getInfoAppendToModel(Model model){
        DicData dicData = new DicData();
        dicData.setCode("0000");
        dicData.setTypekey("areaCity");
        List<DicData> provinceDicDatas = dicDataService.findDicLikeCode(dicData);
        List<DicData> cityDicDatas = dicDataService.findDicListByPid("f505118a44b111e6958f000c29779dd9");
        dicData.setCode("00");
        List<DicData> provinceCityDicDatas = dicDataService.findDicLikeCode(dicData);
        List<DicData> tradeTypeDicDatas = dicDataService.findDicListByType("tradeType");
        List<DicData> coopBusinessTypeDicDatas = dicDataService.findDicListByType("coopBusinessType");
        List<DicData> signedDic = dicDataService.findDicListByType("signed");

        model.addAttribute("provinceDic", provinceDicDatas);
        model.addAttribute("cityDic", cityDicDatas);
        model.addAttribute("provinceCityDic", provinceCityDicDatas);
        model.addAttribute("tradeTypeDic", tradeTypeDicDatas);
        model.addAttribute("coopBusinessTypeDic", coopBusinessTypeDicDatas);
        model.addAttribute("signedDic", signedDic);

        return model;
    }

    /**
     * 分页查询客户信息
     * @param page
     * @return
     */
    @RequestMapping("/pageCustomerList")
    @ResponseBody
    public PageWrapper pageCustomerList(Page page){
        logger.info("=====================================CustomerManagerController pageCustomerList Execute=====================================");
        return customerManagerService.pageCustomerList(page);
    }

    /**
     * 删除客户信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteCustomer")
    @ResponseBody
    public JSonMessageSubmit deleteCustomer(HttpServletRequest request){
        logger.info("=====================================CustomerManagerController deleteCustomer Execute=====================================");
        String id = request.getParameter("id");
        CustomerManager customerManager = customerManagerService.findCustomerById(id);
        if(customerManager != null){
            customerManagerService.deleteCustomerManager(id);
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }


    /**
     * 添加客户信息
     * @param request
     * @return
     */
    @RequestMapping("/saveCustomer")
    @ResponseBody
    public JSonMessageSubmit saveCustomer(HttpServletRequest request, CustomerManager customerManager){
        logger.info("=====================================CustomerManagerController saveCustomer Execute=====================================");
        //String accountId = request.getParameter("accountId");
        String id = request.getParameter("id");
        String businessType = customerManager.getBusinessType();
        //CustomerManager customerManagerResult = customerManagerService.findCustomerByAccountId(accountId);
        CustomerManager customerManagerResult = customerManagerService.findCustomerById(id);
        if(customerManagerResult == null){
            if(businessType != null &&  !"".equals(businessType)) {
                customerManager.setBusinessType(businessType.substring(0, businessType.length() - 1));
            }
            customerManagerService.saveCustomerManager(customerManager);
            return new JSonMessageSubmit("0","信息保存成功！");
        }else{
            return new JSonMessageSubmit("1","存在该客户，信息保存失败！");
        }
    }

    /**
     * 修改客户信息
     * @param request
     * @return
     */
    @RequestMapping("/updateCustomer")
    @ResponseBody
    public JSonMessageSubmit updateCustomer(HttpServletRequest request, CustomerManager customerManager){
        logger.info("=====================================CustomerManagerController updateCustomer Execute=====================================");
        String id = request.getParameter("id");
        String businessType = customerManager.getBusinessType();
        CustomerManager customerManagerResult = customerManagerService.findCustomerById(id);
        if(customerManagerResult != null){
            if(businessType != null &&  !"".equals(businessType)){
                customerManager.setBusinessType(businessType.substring(0,businessType.length()-1));
            }
            customerManagerService.updateCustomerManager(customerManager);
            return new JSonMessageSubmit("0","信息保存成功！");
        }else{
            return new JSonMessageSubmit("1","该信息不存在，信息保存失败！");
        }
    }


    /**
     * 将字典转化成MAP格式
     * @param list
     * @return
     */
    public Map<String, String> getDicDataMap(List<DicData> list){
        Map<String, String> map = new HashMap<>();
        for (DicData dicData: list) {
            map.put(dicData.getCode(),dicData.getName());
        }
        return map;
    }

    /**
     * 翻译合作业务类型
     * @param businessType
     * @param map
     * @return
     */
    public String getBusinessTypeName(String businessType, Map<String, String> map){
        String businessTypeStr = "";
        String[] businessTypeStrArr = businessType.split(",");
        for (int i=0; i< businessTypeStrArr.length; i++){
            if(i == (businessTypeStrArr.length-1)){
                businessTypeStr += map.get(businessTypeStrArr[i]);
            }else{
                businessTypeStr += map.get(businessTypeStrArr[i])+",";
            }
        }
        return businessTypeStr;
    }
    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadCustomerInfo")
    public ModelAndView downloadNumberBlack(Page page) {
        logger.info("=====================================CustomerManagerController downloadCustomerInfo Execute=====================================");
        List<Map<String, Object>> totals = customerManagerService.downloadCustomerInfo(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        DicData dicData = new DicData();
        dicData.setCode("00");
        dicData.setTypekey("areaCity");
        Map<String, String> provinceCityMap = getDicDataMap(dicDataService.findDicLikeCode(dicData));
        Map<String, String> tradeTypeMap = getDicDataMap(dicDataService.findDicListByType("tradeType"));
        Map<String, String> businessTypeMap = getDicDataMap(dicDataService.findDicListByType("coopBusinessType"));

        totals.forEach((map) -> {
            Map<String, Object> customerMap = new HashMap<String, Object>();
            customerMap.put("id", map.get("id").toString());
            customerMap.put("createTime", map.get("create_time").toString());
            customerMap.put("accountId", map.get("account_id"));
            customerMap.put("customerName", map.get("customer_name"));
            customerMap.put("trade", tradeTypeMap.get(map.get("trade").toString()));
            customerMap.put("provinceCity", provinceCityMap.get(map.get("province").toString())+provinceCityMap.get(map.get("city").toString()));
            customerMap.put("businessType", getBusinessTypeName(map.get("business_type").toString(),businessTypeMap));
            customerMap.put("customerManager", map.get("customer_manager"));
            list.add(customerMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("id");
        titles.add("创建时间");
        titles.add("Account ID");
        titles.add("客户名称");
        titles.add("所属行业");
        titles.add("客户所属地区");
        titles.add("合作业务类型");
        titles.add("客户经理");

        List<String> columns = new ArrayList<String>();
        columns.add("id");
        columns.add("createTime");
        columns.add("accountId");
        columns.add("customerName");
        columns.add("trade");
        columns.add("provinceCity");
        columns.add("businessType");
        columns.add("customerManager");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "客户信息列表");
        map.put("excelName","客户信息列表");

        return new ModelAndView(new POIXlsView(), map);
    }
}
