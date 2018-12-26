package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.EccAppInfoService;
import com.e9cloud.mybatis.service.EccExtentionService;
import com.e9cloud.pcweb.BaseController;
import com.sun.javafx.sg.prism.NGShape;
import it.sauronsoftware.jave.EncoderException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/2/13.
 */
@Controller
@RequestMapping("/eccAppInfo")
public class ECCAppInfoController extends BaseController {

    @Autowired
    private EccExtentionService extentionService;

    @Autowired
    private EccAppInfoService eccAppInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppConfig appConfig;


    @Autowired
    private CbTaskService cbTaskService;

    // 编辑ecc的应用
    @RequestMapping("toEdit")
    public String toEdit(Model model, String appid, String managerType) {
        AppInfo appInfo = appInfoService.getSipPhoneAppInfoByAppId(appid);
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByAppid(appid);

        model.addAttribute("appInfo", appInfo);
        model.addAttribute("eccInfo", eccInfo);
        return "view".equals(managerType) ? "ecc/app_show": "ecc/app_edit";
    }

    // 分页查询分机号
    @RequestMapping("pageExtention")
    @ResponseBody
    public PageWrapper pageExtention(Page page) {
        logger.info("pageExtention by appid:{}", page);
        return extentionService.pageExtention(page);
    }

    // 开启禁用长途、开启禁用号码
    @RequestMapping("updateSubNumStatus")
    @ResponseBody
    public JSonMessage updateSubNumStatus(EccExtention eccExtention) {
        extentionService.updateSubNumStatus(eccExtention);
        return new JSonMessage(R.OK, "操作成功");
    }

    // 添加分机号码
    @RequestMapping("toAddSubNum")
    public String toAddSubNum(Model model, String appid, String sid) {
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByAppid(appid);

        model.addAttribute("eccInfo", eccInfo);
        return "ecc/add_subNum";
    }

    @RequestMapping("saveOrUpdateIVR")
    @ResponseBody
    public JSonMessage saveOrUpdateIVR(@RequestParam(required = false, value = "video") MultipartFile video, EccAppInfo eccInfo, String oldCityid) {

        EccAppInfo eccAppInfo = eccAppInfoService.getEccAppInfoByAppid(eccInfo.getAppid());
        if (eccAppInfo != null && Tools.isNullStr(eccInfo.getId())) {
            return new JSonMessage(R.ERROR, "该应用下已经存在总机信息");
        }

        if (Tools.isNotNullStr(eccInfo.getId()) && !Tools.eqStr(eccInfo.getCityid(), oldCityid)) {
            long sum = extentionService.countExtentionByEccId(eccInfo.getId());
            if (sum != 0) {
                return new JSonMessage(R.ERROR, "存在分机号不能修改城市");
            }
        }

        if (eccInfo.getTransfer() == null) {
            eccInfo.setTransfer(false);
            eccInfo.setTransferNum("");
        }

        if (eccInfo.getIvrVoiceNeed()) {
            if (video != null) {
                try {
                    if (!FileUtil.isMP3File(video) && !FileUtil.isWavFile(video)) {
                        return new JSonMessage(R.ERROR, "文件格式不正确");
                    }
                    if (FileUtil.getFileSize(video) > Constants.ECC_VIDEO_SIZE) {
                        return new JSonMessage(R.ERROR, "文件过大，最大支持2M");
                    }
                    CommonsMultipartFile cf= (CommonsMultipartFile)video;
                    DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                    File f = fi.getStoreLocation();

                    String ivrUrl = Constants.ECC_VIDEO_PRE + eccInfo.getAppid() + ".wav";
                    eccInfo.setIvrVoiceUrl(ivrUrl);

                    File target = new File(appConfig.getIvrVoicePath() + ivrUrl);
                    Audio2Wav.audio2wav(f, target);
                } catch (EncoderException e) {
                    e.printStackTrace();
                    return new JSonMessage(R.ERROR, "提示音转码失败");
                }
            }
        } else {
            eccInfo.setIvrVoiceUrl("");
            eccInfo.setIvrVoiceName("");
        }

        String eccid = eccAppInfoService.saveOrUpdateIVRAndReturnId(eccInfo);

        return new JSonMessage(R.OK, eccid, eccInfo.getIvrVoiceUrl());
    }

    // 添加分机号码
    @RequestMapping("addSubNum")
    @ResponseBody
    public JSonMessage addSubNum(EccExtention eccExtention) {
        extentionService.saveSubNum(eccExtention);

        Map<String, Object> ivrJson = new LinkedHashMap<>();
        ivrJson.put("type", CbTask.TaskType.zj_ivr_xml.toString());
        ivrJson.put("appid", eccExtention.getAppid());
        List<Map<String, Object>> totals = extentionService.getExtentionNumList(eccExtention.getAppid());
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("digits", String.valueOf(total.get("subNum")));
                if("01".equals(String.valueOf(total.get("numType")))){
                    map.put("param", String.valueOf(total.get("sipphone")));
                }else {
                    map.put("param", String.valueOf(total.get("fixphone")));
                }
                data.add(map);
            }
        }
        ivrJson.put("data", data);
        CbTask task = new CbTask();
        task.setType(CbTask.TaskType.zj_ivr_xml);
        task.setParamJson(JSonUtils.toJSon(ivrJson));
        cbTaskService.saveCbTask(task);

        return new JSonMessage(R.OK, "添加成功");
    }


    @RequestMapping("toEditSubNum")
    public String toEditSubNum(Model model, EccExtention eccExtention) {
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByAppid(eccExtention.getAppid());
        EccExtention extention = extentionService.getExtentionByPK(eccExtention.getId());

        model.addAttribute("eccInfo", eccInfo);
        model.addAttribute("extention", extention);
        return "ecc/edit_subNum";
    }

    // 修改分机号码
    @RequestMapping("editSubNum")
    @ResponseBody
    public JSonMessage editSubNum(EccExtention eccExtention) {
        extentionService.editSubNum(eccExtention);

        Map<String, Object> ivrJson = new LinkedHashMap<>();
        ivrJson.put("type", CbTask.TaskType.zj_ivr_xml.toString());
        ivrJson.put("appid", eccExtention.getAppid());
        List<Map<String, Object>> totals = extentionService.getExtentionNumList(eccExtention.getAppid());
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("digits", String.valueOf(total.get("subNum")));
                if("01".equals(String.valueOf(total.get("numType")))){
                    map.put("param", String.valueOf(total.get("sipphone")));
                }else {
                    map.put("param", String.valueOf(total.get("fixphone")));
                }
                data.add(map);
            }
        }
        ivrJson.put("data", data);
        CbTask task = new CbTask();
        task.setType(CbTask.TaskType.zj_ivr_xml);
        task.setParamJson(JSonUtils.toJSon(ivrJson));
        cbTaskService.saveCbTask(task);
        return new JSonMessage(R.OK, "修改成功");
    }

    @RequestMapping("toImportSubNum")
    public String toImportSubNum(Model model, String appid, String eccid){
        model.addAttribute("appid", appid);
        model.addAttribute("eccid", eccid);
        return "ecc/importEccSubNum";
    }

    // 删除分机号码
    @RequestMapping("deleteSubNums")
    @ResponseBody
    public JSonMessage deleteSubNums(String[] id,String appid) {
        extentionService.deleteSubNums(id);

        Map<String, Object> ivrJson = new LinkedHashMap<>();
        ivrJson.put("type", CbTask.TaskType.zj_ivr_xml.toString());
        ivrJson.put("appid", appid);
        List<Map<String, Object>> totals = extentionService.getExtentionNumList(appid);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("digits", String.valueOf(total.get("subNum")));
                if("01".equals(String.valueOf(total.get("numType")))){
                    map.put("param", String.valueOf(total.get("sipphone")));
                }else {
                    map.put("param", String.valueOf(total.get("fixphone")));
                }
                data.add(map);
            }
        }
        ivrJson.put("data", data);
        CbTask task = new CbTask();
        task.setType(CbTask.TaskType.zj_ivr_xml);
        task.setParamJson(JSonUtils.toJSon(ivrJson));
        cbTaskService.saveCbTask(task);

        return new JSonMessage(R.OK, "删除成功");
    }

    // 下载列表
    @RequestMapping("downloadReport")
    public ModelAndView downloadReport(Page page) {

        logger.info("=====================================ECCAppInfoController downloadReport Execute=====================================");

        List<Map<String, Object>> totals = extentionService.downloadExtention(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        totals.forEach((map) -> {
            Map<String, Object> appMap = new HashMap<String, Object>();
            appMap.put("rowNO",(int)Float.parseFloat(Tools.toStr(map.get("rowNO"))));
            appMap.put("numType", Tools.decode(map.get("numType"), "01", "SIP 号码", "02", "手机", "03", "固话"));

            if (Tools.eqStr(Tools.toStr(map.get("numType")), "01")) {
                appMap.put("fixphone", Tools.toStr(map.get("sipphone")));
                appMap.put("showNum", Tools.toStr(map.get("showNum")));
                appMap.put("pwd", map.get("pwd"));
                appMap.put("sipRealm", map.get("sipRealm"));
                appMap.put("ipPort", map.get("ipPort"));
                appMap.put("longDistanceFlag", Tools.decode(map.get("longDistanceFlag"), "00", "开启", "01", "关闭"));
            } else {
                appMap.put("fixphone", Tools.toStr(map.get("fixphone")));
                appMap.put("showNum", "");
                appMap.put("pwd", "");
                appMap.put("sipRealm", "");
                appMap.put("ipPort", "");
                appMap.put("longDistanceFlag", "");
            }

            appMap.put("callSwitchFlag", Tools.decode(map.get("callSwitchFlag"), "00", "开启", "01", "禁用"));
            appMap.put("addtime", Tools.formatDate(map.get("addtime")));
            appMap.put("subNum", Tools.toStr(map.get("subNum")));
            appMap.put("pname", Tools.toStr(map.get("pname")));
            appMap.put("cname", Tools.toStr(map.get("cname")));

            list.add(appMap);
        });

        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("接听号码类型");
        titles.add("接听号码");
        titles.add("外显号码");
        titles.add("分机号码");
        titles.add("省份");
        titles.add("城市");
        titles.add("认证密码");
        titles.add("SIP REALM");
        titles.add("IP:PORT");
        titles.add("创建时间");
        titles.add("长途状态");
        titles.add("号码状态");

        List<String> columns = new ArrayList<String>();
        columns.add("rowNO");
        columns.add("numType");
        columns.add("fixphone");
        columns.add("showNum");
        columns.add("subNum");
        columns.add("pname");
        columns.add("cname");
        columns.add("pwd");
        columns.add("sipRealm");
        columns.add("ipPort");
        columns.add("addtime");
        columns.add("longDistanceFlag");
        columns.add("callSwitchFlag");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "分机设置");
        map.put("excelName","分机设置");

        return new ModelAndView(new POIXlsView(), map);
    }

    // 核对总机号码
    @RequestMapping("checkSwitchboard")
    @ResponseBody
    public JSonMessage checkSwitchboard(String appid, String eccid, String cityid, String switchboard) {
        return eccAppInfoService.checkSwitchboard(appid, eccid, cityid, switchboard);
    }

    // 核对外显号码
    @RequestMapping("checkShowNum")
    @ResponseBody
    public JSonMessage checkShowNum(String appid, String eccid, String showNum, String extid) {
        return extentionService.checkShowNum(appid, eccid, showNum, extid);
    }

    // 核对分机号码
    @RequestMapping("checkSubNum")
    @ResponseBody
    public JSonMessage checkSubNum(String appid, String eccid, String subNum, String extid) {
        return extentionService.checkSubNum(eccid, subNum, extid);
    }

    // 核对分机号码
    @RequestMapping("checkPhone")
    @ResponseBody
    public JSonMessage checkPhone(String eccid, String numType, String phone, String extid) {
        return extentionService.checkPhone(eccid, numType, phone, extid);
    }

    /**
     * 上传SipPhone Excel文件信息
     * @param EccShowNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/eccSubNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit eccSubNumExcelUpload(@RequestParam(required = false, value = "file") MultipartFile EccShowNumFile, HttpServletRequest request){

        if(EccShowNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<EccExtention> insertEccShownumErrorList = extentionService.saveEccSubNumExcel(EccShowNumFile, request);
                if (insertEccShownumErrorList.size() > 0) {
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
     * 下载SubNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorEccSubNum")
    public ModelAndView downLoadErrorEccSubNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------ECCAppInfoController insertEccSubNumErrorList start--------------------------------");
        List<EccExtention> insertEccShownumErrorList = (List<EccExtention>) request.getSession().getAttribute("insertEccSubNumErrorList");
        Map<String, Object> contentMap = downLoadEccSubNumExcel(insertEccShownumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadEccSubNumExcel(List<EccExtention> insertEccShownumErrorList) {
        logger.info("--------------------------------ECCAppInfoController downLoadEccShowNumExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (EccExtention eccExtention : insertEccShownumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("numType", eccExtention.getNumTypeStr());
            excelMap.put("phone", eccExtention.getPhone());
            excelMap.put("showNum", eccExtention.getShownum());
            excelMap.put("subNum", eccExtention.getSubNum());

            String callSwitchFlagStr = eccExtention.getCallSwitchFlag();
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

            String longDistanceFlagStr = eccExtention.getLongDistanceFlag();
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

            excelMap.put("importCommon", eccExtention.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("接听号码类型");
        titles.add("接听号码");
        titles.add("外显号码");
        titles.add("分机号");
        titles.add("号码状态（0禁用/1启用）");
        titles.add("长途权限（0关闭/1开启）");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("numType");
        columns.add("phone");
        columns.add("showNum");
        columns.add("subNum");
        columns.add("callSwitchFlag");
        columns.add("longDistanceFlag");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","云总机分机号错误信息");
        contentMap.put("excelName", "云总机分机号错误信息");

        return contentMap;
    }
    @RequestMapping(value = "downloadIvr", method = RequestMethod.GET)
    public void downloadIvr(String url, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = appConfig.getIvrVoicePath() + url;
            File file = new File(newPath);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(newPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
