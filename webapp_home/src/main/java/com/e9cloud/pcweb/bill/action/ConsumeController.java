package com.e9cloud.pcweb.bill.action;

import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.RestStafDayRecord;
import com.e9cloud.mybatis.service.ZnyddService;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消费总览
 *
 * Created by Administrator on 2016/1/26.
 */
@Controller
@RequestMapping("/znydd/bill/consume")
public class ConsumeController extends BaseController{

    @Autowired
    private ZnyddService znyddService;

    /**
     * 获取消费总览的信息
     * @param request
     * @param model
     * @param restStafDayRecord
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getConsumeView(HttpServletRequest request, Model model, RestStafDayRecord restStafDayRecord){
        System.out.println("------------------------------------------------GET ConsumeController getConsumeView START----------------------------------------------------------------");

        String currentDate = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -1));

        //获取12个月前时间
        String beforcurrentDate = new SimpleDateFormat("yyyy年M月").format(DateUtils.addMonths(new Date(), -12));

        String currentDate2 = new SimpleDateFormat("yyyy年M月dd日").format(DateUtils.addDays(new Date(), -1));
        //获取12个月前时间
        String beforcurrentDate2 = new SimpleDateFormat("yyyy年M月dd日").format(DateUtils.addDays(new Date(), -365));

        //获取当前的日期
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("currentDate2", currentDate2);

        model.addAttribute("beforcurrentDate", beforcurrentDate);
        model.addAttribute("beforcurrentDate2", beforcurrentDate2);

        return "bill/consume";
    }

    /**
     * 获取消费总览的实时信息( 颗粒度为：月 )
     * @param request
     * @param ym
     * @return
     * @throws Exception
     */
    @RequestMapping("/getConsumeAlways")
    @ResponseBody
    public Map<String, Object> getConsumeAlways(HttpServletRequest request, String ym) throws Exception{
        System.out.println("------------------------------------------------GET ConsumeController getConsumeAlways START----------------------------------------------------------------");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ym", ym);
        map.put("feeid",account.getFeeid());
        try{
            List<Map<String, Object>> consumeTrendList = znyddService.getMonthRecordByDay(map);
            map.put("consumeTrendList", consumeTrendList);

            //获取呼叫中心消费概况信息
            List<Map<String, Object>> consumeSurveyList = znyddService.getMonthRecordByCallCenter(map);
            map.put("consumeSurveyList", consumeSurveyList);

            map.put("consumeTotal", znyddService.getMonthRecordTotal(map));

            map.put("status", "0");
        }catch (Exception e){
            e.printStackTrace();
            map.put("status", "1");
            map.put("info", "系统繁忙，稍后重试！");
        }

        return map;
    }

    /**
     * 获取消费总览的实时信息( 颗粒度为：天 )
     * @param request
     * @param ym
     * @return
     * @throws Exception
     */
    @RequestMapping("/getHourConsumeAlways")
    @ResponseBody
    public Map<String, Object> getHourConsumeAlways(HttpServletRequest request, String ym) throws Exception{
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ym", ym);
        map.put("feeid",account.getFeeid());
        try{
            List<Map<String, Object>> consumeTrendList = znyddService.getDayRecordByHour(map);
            map.put("consumeTrendList", consumeTrendList);

            //获取呼叫中心消费概况信息
            List<Map<String, Object>> consumeSurveyList = znyddService.getDayRecordByCallCenter(map);
            map.put("consumeSurveyList", consumeSurveyList);

            map.put("consumeTotal", znyddService.getDayRecordTotal(map));

            map.put("status", "0");
        }catch (Exception e){
            e.printStackTrace();
            map.put("status", "1");
            map.put("info", "系统繁忙，稍后重试！");
        }

        return map;
    }

}
