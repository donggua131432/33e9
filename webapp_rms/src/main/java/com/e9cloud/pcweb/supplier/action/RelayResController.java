package com.e9cloud.pcweb.supplier.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.RelayRes;
import com.e9cloud.mybatis.domain.RelayResPer;
import com.e9cloud.mybatis.domain.Supplier;
import com.e9cloud.mybatis.service.RelayResService;
import com.e9cloud.mybatis.service.SupplierInfoService;
import com.e9cloud.pcweb.BaseController;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 2016/8/4.
 */
@Controller
@RequestMapping("/relayRes")
public class RelayResController extends BaseController {

    @Autowired
    private SupplierInfoService supplierInfoService;

    @Autowired
    private RelayResService resService;

    @RequestMapping("relayResList")
    public String relayResList(Model model, String supId) {
        Supplier supplier = supplierInfoService.findSupplierById(supId);

        model.addAttribute("supplier", supplier);

        return "supplier/relayResList";
    }

    //
    @RequestMapping("pageRes")
    @ResponseBody
    public PageWrapper pageRes(Page page, String supId) {
        page.addParams("supId", supId);
        return resService.pageRes(page);
    }

    @RequestMapping("toAddRes")
    public String toAddRes(Model model, String supId) {
        model.addAttribute("supId", supId);
        return "supplier/relayResAdd";
    }

    @RequestMapping("toEditRes")
    public String toEditRes(Model model, Integer id) {
        RelayRes relayRes = resService.getResWithPersAndCityById(id);
        model.addAttribute("res", relayRes);
        return "supplier/relayResEdit";
    }

    @RequestMapping("toShowRes")
    public String toShowRes(Model model, Integer id) {
        RelayRes relayRes = resService.getResWithPersAndCityById(id);
        model.addAttribute("res", relayRes);
        return "supplier/relayResShow";
    }

    @RequestMapping("addRes")
    @ResponseBody
    public JSonMessage addRes(RelayRes relayRes, String[] resPers) {
        logger.info(JSonUtils.toJSon(relayRes));
        logger.info(JSonUtils.toJSon(resPers));
        JSonMessage r = null;
        try {
            List<RelayResPer> relayResPers = new ArrayList<>();
            if (resPers != null && resPers.length > 0) {
                for (String per : resPers) {
                    String[] p = Tools.stringToArray(per, "-");
                    RelayResPer relayResPer = new RelayResPer();

                    relayResPer.setOperator(p[0]);
                    relayResPer.setCallType(p[1]);
                    relayResPer.setNumType(p[2]);
                    relayResPer.setPer(new BigDecimal(p[3]));
                    relayResPer.setCycle(Integer.valueOf(p[4]));

                    relayResPers.add(relayResPer);
                }
            }
            r = resService.addResAndPers(relayRes, relayResPers);
        } catch (Exception e) {
            logger.info("添加供应商资源时出现错误", e);
            r = new JSonMessage(R.ERROR, "提交失败");
        }

        return r;
    }

    @RequestMapping("updateRes")
    @ResponseBody
    public JSonMessage updateRes(RelayRes relayRes, String[] resPers) {
        logger.info(JSonUtils.toJSon(relayRes));
        logger.info(JSonUtils.toJSon(resPers));
        JSonMessage r = null;

        try {
            List<RelayResPer> relayResPers = new ArrayList<>();
            if (resPers != null && resPers.length > 0) {
                for (String per : resPers) {
                    String[] p = Tools.stringToArray(per, "-");
                    RelayResPer relayResPer = new RelayResPer();

                    relayResPer.setOperator(p[0]);
                    relayResPer.setCallType(p[1]);
                    relayResPer.setNumType(p[2]);
                    relayResPer.setPer(new BigDecimal(p[3]));
                    relayResPer.setCycle(Integer.valueOf(p[4]));

                    relayResPers.add(relayResPer);
                }
            }
            r = resService.updateResAndPers(relayRes, relayResPers);

        } catch (Exception e) {
            logger.info("修改供应商资源时出现错误", e);
            r = new JSonMessage(R.ERROR, "提交失败");
        }

        return r;
    }

    @RequestMapping("delRes")
    @ResponseBody
    public JSonMessage delRes(Integer id) {
        resService.delRes(id);
        return new JSonMessage(R.OK, "删除成功");
    }

    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadReport")
    public ModelAndView downloadReport(Page page, String supId) {
        logger.info("=====================================RelayResController downloadReport Execute=====================================");
        page.addParams("supId", supId);

        List<Map<String, Object>> totals = resService.downloadRes(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();

            tempMap.put("resName", Tools.toStr(map.get("resName")));
            tempMap.put("id", Tools.toStr(map.get("id")));
            tempMap.put("relayName", Tools.toStr(map.get("relayName")));
            tempMap.put("relayNum", Tools.toStr(map.get("relayNum")));
            tempMap.put("operator", Tools.decode(map.get("operator"), "00", "中国移动", "01", "中国联通", "02", "中国电信", "03", "其他"));

            list.add(tempMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("线路资源名称");
        titles.add("线路资源ID");
        titles.add("中继名称");
        titles.add("中继ID");
        titles.add("归属运营商");

        List<String> columns = new ArrayList<String>();
        columns.add("resName");
        columns.add("id");
        columns.add("relayName");
        columns.add("relayNum");
        columns.add("operator");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "供应商资源信息列表");
        map.put("excelName","供应商资源信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }
}
