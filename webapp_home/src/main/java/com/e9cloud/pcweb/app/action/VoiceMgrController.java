package com.e9cloud.pcweb.app.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;

import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.account.biz.UpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.e9cloud.mybatis.service.VoiceService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2016/3/24.
 */
@Controller
@RequestMapping("/voiceMgr")
public class VoiceMgrController extends BaseController {
    @Autowired
    private VoiceService voiceService;
    @Autowired
    private UpgradeService upgradeService;
    @Autowired
    private CbTaskService cbTaskService;

    /**
     * 页面展示
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Page page) {

        return "app/voiceList";

    }

    /**
     * app铃声列表
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageWrapper query(HttpServletRequest request, Page page) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        page.getParams().put("sid", account.getSid());
        return voiceService.pageVoiceList(page);
    }


    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "savePage", method = RequestMethod.GET)
    public String voiceAdd(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"voiceMgr/index";
        }
        model.addAttribute("appname", voiceService.findAppNameByAPPid(appid));
        model.addAttribute("appid", appid);
        return "app/voiceSave";
    }
    /**
     * 铃声创建
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String appSave(Model model,HttpServletRequest request, AppVoice voice, String appid, String appname,
                          @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {

        System.out.println("appid:"+voice.getAppid()+"========voiceName:"+voice.getVoiceName());

        logger.info("===========================save===========================start");
        try{
            voice.setAppid(appid);
            voice.setCreateDate(new Date());
            voice.setStatus(Constants.VOICE_STATUS_ING);
            String count =voiceService.countVoiceByAppid(appid);
            if ("0".equals(count) ){
                String resultType = upgradeService.saveVoiceUrl(voice, voiceUrl);
                if (Constants.RESULT_STATUS_OK.equals(resultType)) {
                    //上传成功
                    return this.redirect+appConfig.getAuthUrl()+"voiceMgr/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/voiceSave";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/voiceSave";
                }
            }else {
                model.addAttribute("error", "repeat");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "app/voiceSave";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "app/voiceSave";
    }

    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "updatePage", method = RequestMethod.GET)
    public String voiceUpdate(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"voiceMgr/index";
        }
        model.addAttribute("appname", voiceService.findAppNameByAPPid(appid));
        //model.addAttribute("appid", appid);
        //model.addAttribute("voiceURL", voiceService.getAppvoiceURLByAPPid(appid));
        model.addAttribute("voice", voiceService.findVoiceListByAppid(appid));
        return "app/voiceUpdate";
    }
    /**
     * 铃声修改
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String voiceUpdate(Model model,HttpServletRequest request, AppVoice voice, String appid,String appname,
                              @RequestParam(required = false, value = "voiceFile") MultipartFile voiceUrl) {
        try{
            voice.setCreateDate(new Date());
            voice.setStatus(Constants.VOICE_STATUS_ING);
            String count =voiceService.updateVoiceByAppid(appid);
            if ("0".equals(count)){
                logger.info("--------无记录--------"+ count );
                String resultType = upgradeService.updateVoiceUrl(voice, voiceUrl);
                if (Constants.RESULT_STATUS_OK.equals(resultType)) { // 上传成功
                    return this.redirect+appConfig.getAuthUrl()+"voiceMgr/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/voiceUpdate";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {

                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "app/voiceUpdate";
                }
            }else {
                logger.info("--------信息重复--------"+ count );
                model.addAttribute("error", "chong");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "app/voiceUpdate";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "app/voiceUpdate";
    }
    /**
     * 详情查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(HttpServletRequest request,String appid, Model model ) {
        model.addAttribute("appname", voiceService.findAppNameByAPPid(appid));
        AppVoice voice = voiceService.findVoiceListByAppid(appid);
        model.addAttribute("voice", voice);
        model.addAttribute("appid", appid);
        return "app/voiceView";
    }

    /**
     * 铃声删除
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delVoice(String appid) {
        Map<String, String> map = new HashMap<String, String>();
        AppVoice voice = voiceService.findVoiceListByAppid(appid);
        voiceService.delVoice(appid);
        FileUtil.deleteFile(appid);

        //保存tb_cb_task表

        if("01".equals(voice.getStatus())){
            String path = appConfig.getVoicePath() +  appid + ".wav";
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
