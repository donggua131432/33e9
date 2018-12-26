package com.e9cloud.pcweb.relay.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.domain.RelayFault;
import com.e9cloud.mybatis.service.RelayFaultService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 话单
 * Created by Administrator on 2016/8/4.
 */
@Controller
@RequestMapping("/relaylist")
public class RelayController extends BaseController {
    @Autowired
    private RelayFaultService relayFaultService;

    //////////////////////////////////////////// rest start //////////////////////////////////////////////

    // 跳转到 中继线路故障统计页面
    @RequestMapping("/relayfault")
    public String toRest() {
        return "relay/relayfaultlist";
    }

    /**
     * 列表数据查询
     * @param page
     * @return
     */
    @RequestMapping("/pageRelayFaultList")
    @ResponseBody
    public PageWrapper pageCityList(Page page){
        logger.info("=====================================RelayController pageRelayFaultList Execute=====================================");
        List<RelayFault> returnRFList = getRealyFaultList(page);
        return new PageWrapper(page.getDraw(), page.getPage(), returnRFList.size(), returnRFList.size(), returnRFList);

    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model,Page page) {
        List<RelayFault> totals = getRealyFaultList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(RelayFault total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                if(total.getRecordRate1().equals("0.00")){
                    total.setRecordRate1("");
                }
                if(total.getRecordRate2().equals("0.00")){
                    total.setRecordRate2("");
                }
                if(total.getRecordRate3().equals("0.00")){
                    total.setRecordRate3("");
                }
                if(total.getRecordRate4().equals("0.00")){
                    total.setRecordRate4("");
                }
                if(total.getRecordRate5().equals("0.00")){
                    total.setRecordRate5("");
                }
                if(total.getRecordRate6().equals("0.00")){
                    total.setRecordRate6("");
                }
                if(total.getRecordRate7().equals("0.00")){
                    total.setRecordRate7("");
                }
                if(total.getRecordRate8().equals("0.00")){
                    total.setRecordRate8("");
                }
                if(total.getRecordRate9().equals("0.00")){
                    total.setRecordRate9("");
                }
                if(total.getRecordRate10().equals("0.00")){
                    total.setRecordRate10("");
                }
                if(total.getRecordRate11().equals("0.00")){
                    total.setRecordRate11("");
                }
                if(total.getRecordRate12().equals("0.00")){
                    total.setRecordRate12("");
                }


                map.put("faultName",total.getFaultName()+"(%)");
                map.put("recordRate1", total.getRecordRate1());
                map.put("recordRate2", total.getRecordRate2());
                map.put("recordRate3", total.getRecordRate3());
                map.put("recordRate4", total.getRecordRate4());
                map.put("recordRate5", total.getRecordRate5());
                map.put("recordRate6", total.getRecordRate6());
                map.put("recordRate7", total.getRecordRate7());
                map.put("recordRate8", total.getRecordRate8());
                map.put("recordRate9", total.getRecordRate9());
                map.put("recordRate10", total.getRecordRate10());
                map.put("recordRate11", total.getRecordRate11());
                map.put("recordRate12", total.getRecordRate12());

                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("故障类型");
        titles.add("1月");
        titles.add("2月");
        titles.add("3月");
        titles.add("4月");
        titles.add("5月");
        titles.add("6月");
        titles.add("7月");
        titles.add("8月");
        titles.add("9月");
        titles.add("10月");
        titles.add("11月");
        titles.add("12月");

        List<String> columns = new ArrayList<String>();

        columns.add("faultName");
        columns.add("recordRate1");
        columns.add("recordRate2");
        columns.add("recordRate3");
        columns.add("recordRate4");
        columns.add("recordRate5");
        columns.add("recordRate6");
        columns.add("recordRate7");
        columns.add("recordRate8");
        columns.add("recordRate9");
        columns.add("recordRate10");
        columns.add("recordRate11");
        columns.add("recordRate12");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "中继线路故障统计("+page.getParams().get("myYear")+"年)");
        map.put("excelName","中继线路故障统计");
        return new ModelAndView(new POIXlsView(), map);
    }

    /**
     * 获取组装列表数据
     * @param page
     * @return relayFaultList
     */
    public List<RelayFault> getRealyFaultList(Page page){
        String year=null;
        Map<String, String> map = new HashMap<>();
        RelayFault relayFault=null;
        List<RelayFault> relayFaultList = new ArrayList<RelayFault>();
        List<RelayFault> returnRFList = new ArrayList<RelayFault>();
        StringBuffer sb1 = null;

        if(page.getParams().isEmpty() || page.getParams()==null){
            Calendar cal = Calendar.getInstance();
            year = Integer.toString(cal.get(Calendar.YEAR));
        }else {
            year = page.getParams().get("myYear").toString();
        }

        for (int i = 1;i < 13;i++){
            map.put("myYear",year);
            map.put("month",Integer.toString(i-1));
            if(i==1){
                returnRFList = relayFaultService.getRelayFault(map);
                for(int m=0;m<returnRFList.size();m++){
                    sb1 = new StringBuffer("");
                    returnRFList.get(m).setRecordRate(sb1.append(returnRFList.get(m).getRecordRate1().toString()+",").toString());
                }
            }else{
                relayFaultList = relayFaultService.getRelayFault(map);
            }

            if(i > 1){
                for(int j=0;j<=returnRFList.size()-1;j++){
                    for(int k=0;k<=relayFaultList.size()-1;k++){
                        if (returnRFList.get(j).getFaultName().equals(relayFaultList.get(k).getFaultName())){
                            if(i < 12) {
                                returnRFList.get(j).setRecordRate(new StringBuffer(returnRFList.get(j).getRecordRate()).append(relayFaultList.get(k).getRecordRate1()+",").toString());
                            }else{
                                returnRFList.get(j).setRecordRate(new StringBuffer(returnRFList.get(j).getRecordRate()).append(relayFaultList.get(k).getRecordRate1()).toString());
                            }
                            break;
                        }
                    }
                }
            }
        }
        String[] s =null;
        for(RelayFault returnRF:returnRFList){
            s=returnRF.getRecordRate().split(",");
            returnRF.setRecordRate1(new BigDecimal(s[0]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate2(new BigDecimal(s[1]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate3(new BigDecimal(s[2]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate4(new BigDecimal(s[3]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate5(new BigDecimal(s[4]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate6(new BigDecimal(s[5]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate7(new BigDecimal(s[6]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate8(new BigDecimal(s[7]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate9(new BigDecimal(s[8]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate10(new BigDecimal(s[9]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate11(new BigDecimal(s[10]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            returnRF.setRecordRate12(new BigDecimal(s[11]).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        }

        return returnRFList;

    }

    //////////////////////////////////////////// cc end //////////////////////////////////////////////

}
