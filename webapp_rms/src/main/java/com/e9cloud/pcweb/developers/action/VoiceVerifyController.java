package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.domain.VoiceVerify;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.VoiceVerifyService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by admin on 2017/5/3.
 */
@Controller
@RequestMapping("/voiceVerify")
public class VoiceVerifyController extends BaseController {

    @Autowired
    private VoiceVerifyService voiceVerifyService;
    @Autowired
    private CbTaskService cbTaskService;


    /**
     * 菜单展示列表
     */

    @RequestMapping("list")
    public String list() {
        return "tempVoice/voiceVerifyTemp";
    }

    /**
     * 语音验证码列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("pageVoiceList")
    @ResponseBody
    public PageWrapper pageVoiceList(Page page) {

        return voiceVerifyService.pageVoiceList(page);
    }


    @RequestMapping("toTempCertification")
    public String toTempCertification(Model model, int id) {
        Map<String, Object> tvInfo = voiceVerifyService.getTVInfoByTempId(id);
        model.addAttribute("voiceVerify", voiceVerifyService.getTempVoiceByPK(id));
        model.addAttribute("tvInfo", tvInfo);

        return "tempVoice/voiceVerifyCertification";
    }

    /**
     * 下载语音模板 列表
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping("/downloadReport")
    public ModelAndView downloadReport(Page page) throws Exception{
        logger.info("--------------------------------TempVoiceAuditController downloadReport start--------------------------------");
        List<Map<String, Object>> tempList = voiceVerifyService.downloadReport(page);
        Map<String, Object> contentMap = downLoadExcel(tempList);

        return new ModelAndView(new POIXlsView(), contentMap);
    }


    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<Map<String, Object>> tempList) {

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : tempList) {
            Map<String, Object> excelMap = new HashMap<>();

            excelMap.put("id", map.get("id"));
            excelMap.put("name", map.get("name"));
            excelMap.put("companyName", map.get("companyName"));
            excelMap.put("sid", map.get("sid"));
            excelMap.put("appName", map.get("appName"));
            excelMap.put("appid", map.get("appid"));

            // 00 语音，01 文本。
            if ("00".equals(map.get("type"))) {
                excelMap.put("type", "语音");
            } else if ("01".equals(map.get("type"))) {
                excelMap.put("type", "文本");
            } else {
                excelMap.put("type", "");
            }

            excelMap.put("atime", Tools.formatDate(map.get("atime")));
            excelMap.put("auditTime", Tools.formatDate(map.get("auditTime")));

            // 00 待审核，01 审核通过。02 审核不通过 03 已作废
            if ("00".equals(map.get("auditStatus"))) {
                excelMap.put("auditStatus", "待审核");
            } else if ("01".equals(map.get("auditStatus"))) {
                excelMap.put("auditStatus", "审核通过");
            } else if ("02".equals(map.get("auditStatus"))){
                excelMap.put("auditStatus", "审核不通过");
            } else if ("03".equals(map.get("auditStatus"))){
                excelMap.put("auditStatus", "已作废");
            } else {
                excelMap.put("auditStatus", map.get(""));
            }

            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("模板ID");
        titles.add("模板名称");
        titles.add("客户名称");
        titles.add("account Sid");
        titles.add("应用名称");
        titles.add("APP ID");
        titles.add("模板类型");
        titles.add("创建时间");
        titles.add("审核时间");
        titles.add("审核状态");

        List<String> columns = new ArrayList<String>();
        columns.add("id");
        columns.add("name");
        columns.add("companyName");
        columns.add("sid");
        columns.add("appName");
        columns.add("appid");
        columns.add("type");
        columns.add("atime");
        columns.add("auditTime");
        columns.add("auditStatus");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","语音验证码模板审核列表");
        contentMap.put("excelName", "语音验证码模板审核列表");

        return contentMap;
    }


    /**
     * 审核语音验证码文件
     * @return
     */
    @RequestMapping("audit")
    @ResponseBody
    public JSonMessage audit(VoiceVerify voiceVerify) {

        try {
            CbTask cbTask = null;
            // 如果审核通过，转换语音文件
            if ("01".equals(voiceVerify.getAuditStatus()) && "00".equals(voiceVerify.getType())) {

                VoiceVerify tv = voiceVerifyService.getTempVoiceByPK(voiceVerify.getId());
                String vurl = tv.getAppid() + "/"+ tv.getId().toString()+ ".wav";
                String src = appConfig.getLocalVoiceVerifyTempPath() + tv.getvUrl();
                String trg = appConfig.getNasVoiceVerifyTempPath() + vurl;

                File target = new File(trg);

                FileUtil.mkParentDir(target);

                Audio2Wav.audio2wav(new File(src), target);
                voiceVerify.setvPath(vurl);

                //保存tb_cb_task表
                cbTask = new CbTask();

                int num = trg.indexOf("/web");
                String filePath =trg.substring(num+4,trg.length());

                Map<String, Object> paramJson = new LinkedHashMap<>();
                paramJson.put("type", CbTask.TaskType.files.toString());
                paramJson.put("filePath", filePath);
                paramJson.put("operation", "add");

                cbTask.setType(CbTask.TaskType.files);
                cbTask.setParamJson(JSonUtils.toJSon(paramJson));

            }

            voiceVerifyService.updateAuditStatus(voiceVerify);
            if (null!=cbTask) {
                cbTaskService.saveCbTask(cbTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSonMessage("error", "");
        }

        return new JSonMessage("ok", "");
    }


    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(String url, HttpServletResponse response, String vType) {
        try {
            // path是指欲下载的文件的路径。
            String newPath = appConfig.getLocalVoiceVerifyTempPath() + url;
            if("01".equals(vType)){
                newPath = appConfig.getNasVoiceVerifyTempPath() + url;
            }
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
