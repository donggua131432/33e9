package com.e9cloud.pcweb.maskLine.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.NumberBlack;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by dukai on 2016/7/6.
 */
@Controller
@RequestMapping("/numberBlack")
public class NumberBlackController extends BaseController{
    @Autowired
    private NumberBlackService numberBlackService;

    @RequestMapping("/numberBlackMgr")
    public String numberBlackMgr(){
        logger.info("=====================================NumberBlackController numberBlackMgr Execute=====================================");
        return "numberBlack/numberBlackList";
    }

    @RequestMapping("/addNumberBlack")
    public String addNumberBlack(){
        logger.info("=====================================NumberBlackController addNumberBlack Execute=====================================");
        return "numberBlack/addNumberBlack";
    }

    /**
     * 分页查询号码黑名单信息
     * @param page
     * @return
     */
    @RequestMapping("/numberBlackList")
    @ResponseBody
    public PageWrapper pageNumberBlack(Page page){
        logger.info("=====================================NumberBlackController pageNumberBlack Execute=====================================");
        return numberBlackService.pageNumberBlack(page);
    }

    /**
     * 删除号码黑名单信息
     * @param request
     * @return
     */
    @RequestMapping("/deleteNubmerBlack")
    @ResponseBody
    public JSonMessageSubmit deleteNubmerBlack(HttpServletRequest request){
        logger.info("=====================================NumberBlackController deleteNubmerBlack Execute=====================================");
        Integer id = Integer.valueOf(request.getParameter("id"));
        NumberBlack numberBlack = numberBlackService.findNumberBlackById(id);
        if(numberBlack != null){
            numberBlackService.deleteNumberBlack(id);
            return new JSonMessageSubmit("0","信息删除成功！");
        }else{
            return new JSonMessageSubmit("1","该信息已删除！");
        }
    }


    /**
     * 添加号码黑名单
     * @param request
     * @return
     */
    @RequestMapping("/saveNubmerBlack")
    @ResponseBody
    public JSonMessageSubmit saveNubmerBlack(HttpServletRequest request){
        logger.info("=====================================NumberBlackController saveNubmerBlack Execute=====================================");
        String strRemark = request.getParameter("remark");
        String[] strNumbers = request.getParameter("numbers").split(",");
        //String[] numbers = new String[strNumbers.length];
        String errorNumber = "";
        Pattern pattern = Pattern.compile("[0-9]{3,30}");
        //获取要添家的黑名单号码对象集合
        List<NumberBlack> numberBlackParams = new ArrayList<>();
        for (int i=0; i<strNumbers.length; i++) {
            //匹配号码的格式是否正确
            if (pattern.matcher(strNumbers[i]).matches()){
                //numbers[i] = strNumbers[i];
                NumberBlack numBlackParam = new NumberBlack();
                numBlackParam.setNumber(strNumbers[i]);
                numBlackParam.setRemark(strRemark);
                numberBlackParams.add(numBlackParam);
            }else{
                errorNumber = strNumbers[i];
                break;
            }
        }

        if("".equals(errorNumber)){
            List<NumberBlack> numberBlackResult = numberBlackService.findNumberBlackListByNumbers(strNumbers);
            if(numberBlackResult.size() <= 0){
                numberBlackService.saveNumberBlack(numberBlackParams);
                return new JSonMessageSubmit("0","号码黑名单添加成功！");
            }else{
                for (NumberBlack numberBlack : numberBlackResult) {
                    errorNumber += numberBlack.getNumber()+",";
                }
                return new JSonMessageSubmit("2",errorNumber+"号码重复！");
            }
        }else{
            return new JSonMessageSubmit("1",errorNumber+"-错误的号码格式！");
        }
    }


    /**
     * 导出报表
     * @param page
     * @return
     */
    @RequestMapping("/downloadNumberBlack")
    public ModelAndView downloadNumberBlack(Page page) {
        logger.info("=====================================NumberBlackController downloadNumberBlack Execute=====================================");
        List<Map<String, Object>> totals = numberBlackService.downloadBlackNumber(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> numBlackMap = new HashMap<String, Object>();
            numBlackMap.put("id", map.get("id").toString());
            numBlackMap.put("createTime", map.get("create_time").toString());
            numBlackMap.put("number", map.get("number"));
            numBlackMap.put("remark", map.get("remark"));
            list.add(numBlackMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("id");
        titles.add("创建时间");
        titles.add("号码");
        titles.add("备注原因");

        List<String> columns = new ArrayList<String>();
        columns.add("id");
        columns.add("createTime");
        columns.add("number");
        columns.add("remark");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "号码黑名单");
        map.put("excelName","号码黑名单");

       /* Map<String, Object> params = page.getParams();
        LogUtil.log("导出号码黑名单列表", "号码黑名单导出一条记录。导出内容的查询条件为："
                + " id： " + params.get("id") + "，创建时间：" + params.get("createTime")
                + "，号码：" + params.get("number") + "，备注原因："+ params.get("remark"), LogType.UPDATE);*/

        return new ModelAndView(new POIXlsView(), map);
    }
}
