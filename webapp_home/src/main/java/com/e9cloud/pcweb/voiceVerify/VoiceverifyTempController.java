package com.e9cloud.pcweb.voiceVerify;

import com.e9cloud.core.office.excel.xls.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.VoiceverifyNum;
import com.e9cloud.mybatis.domain.VoiceverifyTemp;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.VoiceverifyNumService;
import com.e9cloud.mybatis.service.VoiceverifyTempService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/voiceverifyTemp")
public class VoiceverifyTempController extends BaseController{

    @Autowired
    private VoiceverifyNumService voiceverifyNumService;

    @Autowired
    private VoiceverifyTempService voiceverifyTempService;

    @Autowired
    private UpgradeService upgradeService;

    @Autowired
    private CbTaskService cbTaskService;


    @RequestMapping("/list")
    public String goAppindex(){
        return "voiceVerify/voiceVerifyTempList";
    }

    /**
     * 跳转外显号码列表
     * @param model
     * @return
     */
    @RequestMapping("voiceverifyNumList")
    public String appInfoEdit( Model model,HttpServletRequest request){
        String appid = request.getParameter("appid");
        model.addAttribute("appid", appid);
        return "voiceVerify/voiceverifyNumList";
    }


    /**
     * 外显号码列表
     * @param request
     * @param page
     * @return
     */
    @RequestMapping(value = "/pageVoiceverifyNum")
    @ResponseBody
    public PageWrapper pageVoiceverifyNum(HttpServletRequest request, Page page) {
        int count = 0;
        List<VoiceverifyNum> list = new ArrayList<VoiceverifyNum>();
        PageWrapper pageWrapper = null;
        Map param = new HashMap<String,String>();
        try{
            param.put("appid",request.getParameter("appid"));
            param.put("number",page.getParams().get("number"));
            param.put("areaCode",page.getParams().get("areaCode"));
            count = voiceverifyNumService.findListCountByMap(param);
            pageWrapper = new PageWrapper(page.getPage(), page.getPageSize(), count, null);
            param.put("start",pageWrapper.getFromIndex());
            param.put("pageSize",page.getPageSize());
            list = voiceverifyNumService.findListByMap(param);
            pageWrapper.setRows(list);
        }catch (Exception e){
            logger.info("--------pageVoiceverifyNum error----",e);
        }
        return  pageWrapper;
    }

    /**
     * 下载外显号码报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, String type, Model model, Page page) {
        page.getParams().put("appid", request.getParameter("appid"));

        List<Map<String, Object>> totals = voiceverifyNumService.getpageVoiceverifyNumList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cname", String.valueOf(total.get("cname")));
                map.put("areaCode", String.valueOf(total.get("areaCode")));
                String number = String.valueOf(total.get("number"));
                if(RegexUtils.checkMobile(number)){
                    map.put("areaCode", "");
                }else if(RegexUtils.check95Num(number)){
                    map.put("cname", "");
                    map.put("areaCode", "");
                }
                map.put("number", number);
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("城市");
        titles.add("区号");
        titles.add("号码");

        List<String> columns = new ArrayList<String>();

        columns.add("cname");
        columns.add("areaCode");
        columns.add("number");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "外显号码列表");
        map.put("excelName","外显号码信息报表");
        return new ModelAndView(new POIXlsView(), map);
    }
    /**
     * 模板列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {

        String appid = request.getParameter("appid");
        model.addAttribute("appid", appid);
        return "voiceVerify/mouldList";
    }

    /**
     * app语音模板列表
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageWrapper query(HttpServletRequest request, Page page) {
        page.getParams().put("appid", request.getParameter("appid"));
        return voiceverifyTempService.pageVoiceverifyList(page);
    }

    /**
     * 验证语音模板名称的唯一性
     * @param voiceverifyTemp
     * @return
     */
    @RequestMapping(value = "nameUnique", method = RequestMethod.POST)
    @ResponseBody
    public boolean nameUnique(VoiceverifyTemp voiceverifyTemp, String appid) {
        logger.info("------------------ VoiceverifyTempController nameUnique start ------------------------"+voiceverifyTemp.getName()+" appid:"+appid);
        boolean b = voiceverifyTempService.checkNameUnique(voiceverifyTemp);

        logger.info("====="+b);
        logger.info("------------------ VoiceverifyTempController nameUnique end ------------------------");

        return b;
    }
    /**
     * 模板创建
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Model model, HttpServletRequest request, VoiceverifyTemp voiceverifyTemp, String appid,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {
        logger.info("=========================== VoiceverifyTempController save===========================start");
        try{
            voiceverifyTemp.setAppid(appid);
            voiceverifyTemp.setAtime(new Date());
            voiceverifyTemp.setAuditStatus(Constants.VOICEVERIFY_AUDIT_STATUS_ING);

            if ("01".equals(voiceverifyTemp.getType())){
                voiceverifyTempService.saveVoiceverifyTemp(voiceverifyTemp);
                logger.info("文本类型语音验证码模板添加成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"voiceverifyTemp/index?appid="+appid;
            }
            String resultType = upgradeService.saveVoiceverifyTempUrl(voiceverifyTemp, voiceUrl);
            if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                logger.info("上传成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"voiceverifyTemp/index?appid="+appid;
            }else if (Constants.VOICEVERIFY_ERROR.equals(resultType)) {

                model.addAttribute("error", "error");
                model.addAttribute("appid", appid);
                return "voiceVerify/mouldList";
            }else if (Constants.VOICEVERIFY_SIZE_ERROR.equals(resultType)) {
                model.addAttribute("error", "size");
                model.addAttribute("appid", appid);
                return "voiceVerify/mouldList";
            }
        }catch (Exception e){
            logger.info("--------voiceverifytemp Save error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        return "voiceVerify/mouldList";
    }


    /**
     * 语音模板查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public VoiceverifyTemp view(HttpServletRequest request,String id, Model model ) {

        VoiceverifyTemp voiceverifyTemp = voiceverifyTempService.getVoiceverifyTempByID(id);
        model.addAttribute("voiceverifyTemp", voiceverifyTemp);
        return voiceverifyTemp;
    }

    /**
     *语音模板delete
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage tempDel(HttpServletRequest request, HttpServletResponse response, Model model, VoiceverifyTemp voiceverifyTemp, String id, String appid, String vPath) {
        try{

            VoiceverifyTemp voice =voiceverifyTempService.getVoiceverifyTempByID(id);
            if(Constants.VOICEVERIFY_STATUS_DEL.equals(voice.getStatus())){
                return new JSonMessage("error", "模板不存在，删除失败");
            }

            voiceverifyTemp.setStatus(Constants.VOICEVERIFY_STATUS_DEL);
            voiceverifyTemp.setId(Integer.parseInt(id));
            voiceverifyTempService.updateByPrimaryKeySelective(voiceverifyTemp);
            FileUtil.deleteVoiceverifyTemp(appid,vPath);  //删除语音模板铃声文件

            //审核通过的模版删除需要保存到tb_cb_task表
            if("01".equals(voice.getAuditStatus())){
                String path = appConfig.getNasVoiceverifyTempPath() + vPath;
                CbTask cbTask = new CbTask();

                int num = path.indexOf("/web");
                String filePath =path.substring(num+4,path.length());

                Map<String, Object> paramJson = new LinkedHashMap<>();
                paramJson.put("type", CbTask.TaskType.files.toString());
                paramJson.put("filePath", filePath);
                paramJson.put("operation", "del");

                cbTask.setType(CbTask.TaskType.files);
                cbTask.setParamJson(JSonUtils.toJSon(paramJson));

                cbTaskService.saveCbTask(cbTask);

            }

            return new JSonMessage("ok", "删除成功");
        }catch (Exception e){
            logger.info("--------Del error----",e);
            return new JSonMessage("error", "出现异常，删除失败！");
        }
    }
    @RequestMapping(value = "viewUpdate", method = RequestMethod.POST)
    public String viewUpdate(Model model,HttpServletRequest request, HttpServletResponse response,VoiceverifyTemp voiceverifyTemp ,String id,String appid) {
        logger.info("=========================== VoiceverifyTempController update===========================start");

        voiceverifyTemp.setAuditStatus(Constants.VOICEVERIFY_AUDIT_STATUS_ING);
//            voiceverifyTemp.setId(Integer.parseInt(id));
        voiceverifyTempService.updateByPrimaryKeySelective(voiceverifyTemp);

        appid = request.getParameter("appid");
        model.addAttribute("appid", appid);
        return "voiceVerify/mouldList";
    }

        /**
     * 语音下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String url, String vType,HttpServletResponse response) {
        FileUtil.downloadVoiceverifyTemp(url, response, vType);
    }

    /**
     * 模板重新提交
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Model model, HttpServletRequest request, VoiceverifyTemp voiceverifyTemp, String id,String appid,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl2) {
        logger.info("===========================update===========================start");
        try{
            VoiceverifyTemp temp = voiceverifyTempService.getVoiceverifyTempByID(id);
            voiceverifyTemp.setId(Integer.parseInt(id));
            voiceverifyTemp.setAtime(new Date());
            voiceverifyTemp.setAuditStatus(Constants.VOICEVERIFY_AUDIT_STATUS_ING);
            if ("01".equals(voiceverifyTemp.getType())){
                voiceverifyTempService.updateByPrimaryKeySelective(voiceverifyTemp);
                logger.info("文本类型模板修改成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"voiceverifyTemp/index?appid="+temp.getAppid();
            }

            String resultType = upgradeService.updateVoiceverifyUrl(voiceverifyTemp, voiceUrl2);
            if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                logger.info("重新提交上传成功"+temp.getAppid());
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"voiceverifyTemp/index?appid="+temp.getAppid();
            }else if (Constants.VOICE_ERROR.equals(resultType)) {
                model.addAttribute("error", "errorSub");
                model.addAttribute("appid", voiceverifyTemp.getAppid());
                model.addAttribute("voiceverifyTemp", temp);
                logger.info("==============="+voiceverifyTemp.getName()+"====="+voiceverifyTemp.getAuditCommon());
                return "voiceVerify/mouldList";
            }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                model.addAttribute("error", "size");
                model.addAttribute("appid", temp.getAppid());
                model.addAttribute("id", id);
                return "voiceVerify/mouldList";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        return "voiceVerify/mouldList";
    }

}
