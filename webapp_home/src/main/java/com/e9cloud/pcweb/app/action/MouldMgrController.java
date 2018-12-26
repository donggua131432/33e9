package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.MouldVoiceService;
import com.e9cloud.mybatis.service.VoiceService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2016/9/21.
 */
@Controller
@RequestMapping("/mouldMgr")
public class MouldMgrController extends BaseController{
    @Autowired
    private VoiceService voiceService;
    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private MouldVoiceService mouldVoiceService;
    @Autowired
    private CbTaskService cbTaskService;

    @RequestMapping("/goApp")
    public String goAppindex(){

        return "app/mouldAppList";
    }

    /**
     * 模板列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {

        String appid = request.getParameter("appid");
        model.addAttribute("appid", appid);
        return "app/mouldList";
    }

    /**
     * 模板创建
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String appSave(Model model, HttpServletRequest request, TempVoice tempVoice, String appid,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {
        logger.info("===========================save===========================start");
        try{
            tempVoice.setAppid(appid);
            tempVoice.setAtime(new Date());
            tempVoice.setAuditStatus(Constants.VOICE_STATUS_ING);

            if ("01".equals(tempVoice.getType())){
                mouldVoiceService.saveTempVoice(tempVoice);
                logger.info("文本类型模板添加成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"mouldMgr/index?appid="+appid;
            }
            String resultType = upgradeService.saveMouldUrl(tempVoice, voiceUrl);
            if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                logger.info("上传成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"mouldMgr/index?appid="+appid;
            }else if (Constants.VOICE_ERROR.equals(resultType)) {

                model.addAttribute("error", "error");
                model.addAttribute("appid", appid);
                return "app/mouldList";
            }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                model.addAttribute("error", "size");
                model.addAttribute("appid", appid);
                return "app/mouldList";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        return "app/mouldList";
    }


    /**
     * app语音模板列表
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageWrapper query(HttpServletRequest request, Page page) {
        page.getParams().put("appid", request.getParameter("appid"));
        return mouldVoiceService.pageVoiceList(page);
    }

    /**
     * 语音模板查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public TempVoice view(HttpServletRequest request,String id, Model model ) {

        TempVoice tempVoice = mouldVoiceService.getTempVoiceByID(id);
        model.addAttribute("tempVoice", tempVoice);
        return tempVoice;
    }

    /**
     *语音模板delete
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public boolean tempDel(HttpServletRequest request, HttpServletResponse response,TempVoice tempVoice,String id,String appid,String vPath) {
        try{
            TempVoice voice =mouldVoiceService.getTempVoiceByID(id);
            tempVoice.setStatus(Constants.APP_STATUS_FREEZE);
            tempVoice.setId(Integer.parseInt(id));
            mouldVoiceService.delTemp(tempVoice);
            FileUtil.deleteTempVoice(appid,vPath);  //删除语音模板铃声文件

            //审核通过的模版删除需要保存到tb_cb_task表
            if("01".equals(voice.getAuditStatus())){
                String path = appConfig.getTempVoicePathNas() + vPath;
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
            return true;
        }catch (Exception e){
            logger.info("--------tempDel error----",e);
            return false;
        }
    }


    /**
     * 验证语音模板名称的唯一性
     * @param tempVoice
     * @return
     */
    @RequestMapping(value = "nameUnique", method = RequestMethod.POST)
    @ResponseBody
    public boolean nameUnique(TempVoice tempVoice,String appid) {
        logger.info("------------------ MouldMgrController nameUnique start ------------------------"+tempVoice.getName()+"3333333333===="+appid);
        logger.info("====="+appid);
        boolean b = mouldVoiceService.checkNameUnique(tempVoice);

        logger.info("====="+b);
        logger.info("------------------ MouldMgrController nameUnique end ------------------------");

        return b;
    }



    /**
     * 语音下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String url, String vType,HttpServletResponse response) {
        FileUtil.downloadTempVoice(url, response, vType);
    }


    /**
     * 模板重新提交
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(Model model, HttpServletRequest request, TempVoice tempVoice, String id,String appid,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl2) {
        logger.info("===========================update===========================start");
        try{
            TempVoice temp = mouldVoiceService.getTempVoiceByID(id);
            tempVoice.setId(Integer.parseInt(id));
            tempVoice.setAtime(new Date());
            tempVoice.setAuditStatus(Constants.VOICE_STATUS_ING);
            if ("01".equals(tempVoice.getType())){
                mouldVoiceService.updateTempVoice(tempVoice);
                logger.info("文本类型模板修改成功"+appid);
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"mouldMgr/index?appid="+temp.getAppid();
            }

            String resultType = upgradeService.updateMouldUrl(tempVoice, voiceUrl2);
            if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                logger.info("重新提交上传成功"+temp.getAppid());
                //上传成功
                return this.redirect+appConfig.getAuthUrl()+"mouldMgr/index?appid="+temp.getAppid();
            }else if (Constants.VOICE_ERROR.equals(resultType)) {
                model.addAttribute("error", "errorSub");
                model.addAttribute("appid", temp.getAppid());
                model.addAttribute("tempVoice", temp);
                logger.info("==============="+tempVoice.getName()+"====="+tempVoice.getAuditCommon());
                return "app/mouldList";
            }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                model.addAttribute("error", "size");
                model.addAttribute("appid", temp.getAppid());
                model.addAttribute("id", id);
                return "app/mouldList";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        return "app/mouldList";
    }

}
