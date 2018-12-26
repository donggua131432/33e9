package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.SecretVoice;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.SecretVoiceService;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2016/5/31.
 */
@Controller
@RequestMapping("/secretMgr")
public class SecretVoiceMgrController extends BaseController {

    @Autowired
    private SecretVoiceService secretService;
    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private CbTaskService cbTaskService;

    /**
     * 页面展示
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Page page) {

        return "app/secretList";

    }

    /**
     * app铃声列表
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageWrapper query(HttpServletRequest request, Page page) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        page.getParams().put("sid", account.getSid());
        return secretService.pageVoiceList(page);
    }

    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "savePage", method = RequestMethod.GET)
    public String voiceAdd(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"secretMgr/index";
        }
        model.addAttribute("appname", secretService.findAppNameByAPPid(appid));
        model.addAttribute("appid", appid);
        return "app/secretSave";
    }

    /**
     * 铃声创建
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String appSave(Model model, HttpServletRequest request, SecretVoice voice, String appid, String appname,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {

        System.out.println("appid:"+voice.getAppid()+"========voiceName:"+voice.getVoiceName());

        logger.info("===========================save===========================start");
        try{
            voice.setAppid(appid);
            voice.setCreateDate(new Date());
            voice.setStatus(Constants.VOICE_STATUS_ING);
            String count =secretService.countVoiceByAppid(appid);
            if ("0".equals(count) ){
                String resultType = upgradeService.saveSecretVoiceUrl(voice, voiceUrl);
                if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                    //上传成功
                    return this.redirect+appConfig.getAuthUrl()+"secretMgr/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/secretSave";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/secretSave";
                }
            }else {
                model.addAttribute("error", "repeat");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "app/secretSave";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "app/secretSave";
    }


    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "updatePage", method = RequestMethod.GET)
    public String voiceUpdate(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"secretMgr/index";
        }
        model.addAttribute("appname", secretService.findAppNameByAPPid(appid));
        //model.addAttribute("appid", appid);
        //model.addAttribute("voiceURL", voiceService.getAppvoiceURLByAPPid(appid));
        model.addAttribute("voice", secretService.findVoiceListByAppid(appid));
        return "app/secretUpdate";
    }
    /**
     * 铃声修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String voiceUpdate(Model model,HttpServletRequest request, SecretVoice voice, String appid,String appname,
                              @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {
        try{
            voice.setCreateDate(new Date());
            voice.setStatus(Constants.VOICE_STATUS_ING);
            String count =secretService.updateVoiceByAppid(appid);
            if ("0".equals(count)){
                logger.info("--------无记录--------"+ count );
                String resultType = upgradeService.updateSecretVoiceUrl(voice, voiceUrl);
                if (Constants.RESULT_STATUS_OK.equals(resultType)) { // 上传成功
                    return this.redirect+appConfig.getAuthUrl()+"secretMgr/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/secretUpdate";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {

                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/secretUpdate";
                }
            }else {
                logger.info("--------信息重复--------"+ count );
                model.addAttribute("error", "chong");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "app/secretUpdate";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "app/secretUpdate";
    }
    /**
     * 详情查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(HttpServletRequest request,String appid, Model model ) {
        model.addAttribute("appname", secretService.findAppNameByAPPid(appid));
        SecretVoice voice = secretService.findVoiceListByAppid(appid);
        model.addAttribute("voice", voice);
        model.addAttribute("appid", appid);
        return "app/secretView";
    }

    /**
     * 铃声删除
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delVoice(String appid) {
        Map<String, String> map = new HashMap<String, String>();
        SecretVoice voice = secretService.findVoiceListByAppid(appid);
        secretService.delVoice(appid);
        FileUtil.deleteVoice(appid);
        //保存tb_cb_task表
        if("01".equals(voice.getStatus())){
            String path = appConfig.getVoicePath() +  appid + "_callin_failed.wav";
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

        map.put("status", "00");
        return map;
    }
    /**
     * 详情查看
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String url, HttpServletResponse response) {
        FileUtil.download(url, response);
    }
}
