package com.e9cloud.pcweb.sipPhone;

import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SipAppNumPool;
import com.e9cloud.mybatis.domain.SipNumPool;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.sipPhone.biz.SPNumImportService;
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
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 云话机--云话机号码分配
 * Created by Administrator on 2016/10/28.
 */
@Controller
@RequestMapping("/spImport")
public class SPNumImportController extends BaseController {

    @Autowired
    private SPNumImportService spNumImportService;

    // 云话机外显号号码批量导入
    @RequestMapping("toImportShowNum")
    public String toImportShowNum(Model model, String appid){
        model.addAttribute("appid", appid);
        return "SIPPhone/importShowNumbers";
    }

    /**
     * 上传SIP Number Excel文件信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/showNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile sipNumFile, HttpServletRequest request){

        if(sipNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }

        try {
            List<SpApplyNum> insertSipNumErrorList = spNumImportService.saveExcel(sipNumFile, request);
            if (insertSipNumErrorList.size() > 0) {
                return new JSonMessageSubmit("2", "存在错误号码,部分数据导入成功,请查看下载内容！");
            }
        } catch (Exception e) {
            logger.info("导入文件失败：" + e);
            return new JSonMessageSubmit("0", "导入数据失败,请下载模板编辑数据进行导入！");
        }

        return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");

    }

    /**
     * 下载SIPNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorShowNum")
    public ModelAndView downLoadErrorSipNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SipNumberPoolController downLoadErrorSipNum start--------------------------------");
        List<SpApplyNum> insertSipNumErrorList = (List<SpApplyNum>) request.getSession().getAttribute("insertSpShowNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertSipNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<SpApplyNum> insertSipNumErrorList) {
        logger.info("--------------------------------SipNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (SpApplyNum spApplyNum : insertSipNumErrorList) {

            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("ccode", spApplyNum.getAreaCode());
            excelMap.put("sipphone", spApplyNum.getSipphone());
            excelMap.put("fixphone", spApplyNum.getFixphone());
            excelMap.put("showNum", spApplyNum.getShowNum());

            String callSwitchFlagStr = spApplyNum.getCallSwitchFlag();
            String callSwitchFlag;
            if("00".equals(callSwitchFlagStr)){
                callSwitchFlag = "1";
            }else if("01".equals(callSwitchFlagStr)){
                callSwitchFlag = "0";
            }else if(null==callSwitchFlagStr){
                callSwitchFlag = "";
            }else{
                callSwitchFlag = callSwitchFlagStr;
            }

            String longDistanceFlagStr = spApplyNum.getLongDistanceFlag();
            String longDistanceFlag;
            if("00".equals(longDistanceFlagStr)){
                longDistanceFlag = "1";
            }else if("01".equals(longDistanceFlagStr)){
                longDistanceFlag = "0";
            }else if(null==longDistanceFlagStr){
                longDistanceFlag = "";
            }else{
                longDistanceFlag = longDistanceFlagStr;
            }

            excelMap.put("callSwitchFlag", callSwitchFlag);
            excelMap.put("longDistanceFlag",longDistanceFlag );
            excelMap.put("case", spApplyNum.getAuditCommon());

            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("城市区号");
        titles.add("sip号码");
        titles.add("固话号码");
        titles.add("外显号码");
        titles.add("号码状态（0禁用/1启用）");
        titles.add("长途权限（0关闭/1开启）");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("ccode");
        columns.add("sipphone");
        columns.add("fixphone");
        columns.add("showNum");
        columns.add("callSwitchFlag");
        columns.add("longDistanceFlag");
        columns.add("case");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","错误外显号码信息");
        contentMap.put("excelName", "错误外显号码信息");

        return contentMap;
    }

}
