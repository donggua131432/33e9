package com.e9cloud.pcweb.voiceverify.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.VoiceVerifyNumPool;
import com.e9cloud.mybatis.service.VoiceVerifyNumPoolService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 语音验证码（号码池）
 * Created by wzj on 2017/5/4.
 */
@Controller
@RequestMapping("/voiceverify")
public class VoiceVerifyNumPoolController extends BaseController {

    @Autowired
    private VoiceVerifyNumPoolService voiceVerifyNumPoolService;


    // 跳转到公共资源号码池页面
    @RequestMapping("list")
    public String list() {
        return "voiceVerify/voiceVerifyNumPool";
    }

    // 列表查询
    @RequestMapping("pageVoiceVerifyNumPool")
    @ResponseBody
    public PageWrapper pageVoiceVerifyNumPool(Page page) {
        logger.info("语音验证码公共号码池查询：VoiceVerifyNumPoolController -- pageVoiceVerifyNumPool");
        return voiceVerifyNumPoolService.pageVoiceVerifyNumPool(page);
    }

    // （批量）删除号码池号码
    @RequestMapping("deletePhones")
    @ResponseBody
    public JSonMessage deletePhones(String strId) {
        String[] ids = Tools.stringToArray(strId);
        List<String> numbers = voiceVerifyNumPoolService.deletePhones(ids);
        if (Tools.isEmptyList(numbers)) {
            return new JSonMessage(R.OK, "删除成功");
        }
        return new JSonMessage(R.ERROR, Tools.joinList(numbers, ","));
    }

    // 跳转到添加phone页面
    @RequestMapping("toAddPhone")
    public String toAddPhone() {
        return "voiceVerify/voiceVerifyPhone";
    }

    // 增加一个号码
    @RequestMapping("addPhone")
    @ResponseBody
    public JSonMessage addPhone(VoiceVerifyNumPool numPool) {
        numPool.setId(ID.randomUUID());
        voiceVerifyNumPoolService.saveNum(numPool);
        return new JSonMessage(R.OK, "");
    }

    /**
     * 校验号码
     * @param numPool
     * @return
     */
    @RequestMapping("checkNumber")
    @ResponseBody
    public JSonMessage checkNumber(VoiceVerifyNumPool numPool) {
        return voiceVerifyNumPoolService.checkNumber(numPool);
    }

    @RequestMapping("downloadNumPool")
    public ModelAndView downloadNumPool(Page page) {
        List<Map<String, Object>> totals = voiceVerifyNumPoolService.downloadVoiceVerifyNumPool(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        totals.forEach((map) -> {
            Map<String, Object> tempMap = new HashMap<String, Object>();
            String rowNO = Tools.toStr(map.get("rowNO"));
            if (rowNO.contains(".")) {
                rowNO = rowNO.substring(0, rowNO.lastIndexOf("."));
            }
            tempMap.put("rowNO", rowNO);
            tempMap.put("number", Tools.toStr(map.get("number")));
            tempMap.put("pname", Tools.toStr(map.get("pname")));
            tempMap.put("cname", Tools.toStr(map.get("cname")));

            String areaCode = Tools.toStr(map.get("areaCode"));
            if (RegexUtils.checkMobile(Tools.toStr(map.get("number"))) || RegexUtils.check95Num(Tools.toStr(map.get("number")))) {
                areaCode = "";
            }

            tempMap.put("areaCode", areaCode);
            tempMap.put("status", Tools.decode(map.get("status"), "00", "待分配", "01", "已分配"));
            tempMap.put("companyName", Tools.toStr(map.get("companyName")));
            tempMap.put("sid", Tools.toStr(map.get("sid")));
            tempMap.put("appid", Tools.toStr(map.get("appid")));
            tempMap.put("appName", Tools.toStr(map.get("appName")));

            list.add(tempMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("区号");
        titles.add("状态");
        titles.add("客户名称");
        titles.add("Account ID");
        titles.add("应用名称");
        titles.add("Appid");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("number");
        columns.add("pname");
        columns.add("cname");
        columns.add("areaCode");
        columns.add("status");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appid");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "语音验证码号码池信息列表");
        map.put("excelName","语音验证码号码池信息列表");
        return new ModelAndView(new POIXlsView(), map);
    }

    // 跳转到添加phone页面
    @RequestMapping("importPhone")
    public String importPhone() {
        return "voiceVerify/importVoiceVerifyNumPool";
    }

    /**
     * 上传SipPhone Excel文件信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/phoneExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit uploadSipPhoneExcel(@RequestParam(required = false, value = "file") MultipartFile file, HttpServletRequest request){

        if(file == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<VoiceVerifyNumPool> insertAxbPhoneErrorList = voiceVerifyNumPoolService.savePhoneExcel(file, request);
                if (insertAxbPhoneErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 下载AXBNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorPhone")
    public ModelAndView downLoadErrorAxbPhone(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------VoiceVerifyNumPool downLoadErrorAxbPhone start--------------------------------");
        List<VoiceVerifyNumPool> insertPhoneErrorList = (List<VoiceVerifyNumPool>) request.getSession().getAttribute("insertPhoneErrorList");
        Map<String, Object> contentMap = downLoadPhoneExcel(insertPhoneErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadPhoneExcel(List<VoiceVerifyNumPool> insertPhoneErrorList) {
        logger.info("--------------------------------VoiceVerifyNumPool downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (VoiceVerifyNumPool numPool : insertPhoneErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("areaCode", numPool.getAreacode());
            excelMap.put("number", numPool.getNumber());
            excelMap.put("importCommon", numPool.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("区号");
        titles.add("号码");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("areaCode");
        columns.add("number");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","语音验证码号码池错误信息");
        contentMap.put("excelName", "语音验证码号码池错误信息");

        return contentMap;
    }
}
