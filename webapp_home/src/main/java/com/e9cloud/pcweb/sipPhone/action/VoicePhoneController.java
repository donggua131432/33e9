package com.e9cloud.pcweb.sipPhone.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppVoice;
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
import java.util.Map;

/**
 * Created by admin on 2016/10/27.
 */
@Controller
@RequestMapping("/voicePhone")
public class VoicePhoneController extends BaseController {

    @Autowired
    private VoiceService voiceService;
    @Autowired
    private UpgradeService upgradeService;

    /**
     * 页面展示
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response, Page page) {

        return "sipPhone/voicePhoneList";

    }

    /**
     * app铃声列表
     */
    @RequestMapping(value = "/query")
    @ResponseBody
    public PageWrapper query(HttpServletRequest request, Page page) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        page.getParams().put("sid", account.getSid());
        return voiceService.voicePhoneListBySid(page);
    }

    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "savePage", method = RequestMethod.GET)
    public String voiceAdd(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"voicePhone/index";
        }
        model.addAttribute("appname", voiceService.findAppNameByAPPid(appid));
        model.addAttribute("appid", appid);
        return "sipPhone/voicePhoneSave";
    }
    /**
     * 铃声创建
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String appSave(Model model, HttpServletRequest request, AppVoice voice, String appid, String appname,
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
                    return this.redirect+appConfig.getAuthUrl()+"voicePhone/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "sipPhone/voicePhoneSave";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "sipPhone/voicePhoneSave";
                }
            }else {
                model.addAttribute("error", "repeat");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "sipPhone/voicePhoneSave";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "sipPhone/voicePhoneSave";
    }

    /**
     * 铃声创建设置页面
     */
    @RequestMapping(value = "updatePage", method = RequestMethod.GET)
    public String voiceUpdate(String appid, Model model) {
        if(Tools.isNullStr(appid)){
            return this.redirect+appConfig.getAuthUrl()+"voicePhone/index";
        }
        model.addAttribute("appname", voiceService.findAppNameByAPPid(appid));
        //model.addAttribute("appid", appid);
        //model.addAttribute("voiceURL", voiceService.getAppvoiceURLByAPPid(appid));
        model.addAttribute("voice", voiceService.findVoiceListByAppid(appid));
        return "sipPhone/voicePhoneUpdate";
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
                    return this.redirect+appConfig.getAuthUrl()+"voicePhone/index";
                }else if (Constants.VOICE_ERROR.equals(resultType)) {
                    model.addAttribute("error", "error");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "sipPhone/voicePhoneUpdate";
                }else if (Constants.VOICE_SIZE_ERROR.equals(resultType)) {

                    model.addAttribute("error", "size");
                    model.addAttribute("appid", appid);
                    model.addAttribute("appname", appname);
                    return "sipPhone/voicePhoneUpdate";
                }
            }else {
                logger.info("--------信息重复--------"+ count );
                model.addAttribute("error", "chong");
                model.addAttribute("appid", appid);
                model.addAttribute("appname", appname);
                return "sipPhone/voicePhoneUpdate";
            }
        }catch (Exception e){
            logger.info("--------voiceSave error----",e);
        }
        model.addAttribute("error", "null");
        model.addAttribute("appid", appid);
        model.addAttribute("appname", appname);
        return "sipPhone/voicePhoneUpdate";
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
        return "sipPhone/voicePhoneView";
    }

    /**
     * 铃声删除
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> delVoice(String appid) {
        Map<String, String> map = new HashMap<String, String>();
        voiceService.delVoice(appid);
        FileUtil.deleteFile(appid);
        map.put("status", "00");
        return map;
    }
    /**
     * 详情下载
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(String url, HttpServletResponse response) {
        FileUtil.download(url, response);
    }
}