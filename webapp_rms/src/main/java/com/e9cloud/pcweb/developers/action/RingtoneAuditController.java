package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.sms.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DeveloperController用于开发者管理
 *
 * Created by wujiang on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/ringtone")
public class RingtoneAuditController extends BaseController {
    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AppVoiceService appVoiceService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private CbTaskService cbTaskService;

    @RequestMapping(value = "audit")
    public String audit() {
        return "developersMgr/ringtoneAudit";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PageWrapper list(Page page){
        return appVoiceService.pageAppVoiceList(page);
    }

    @RequestMapping(value="toShowCertification", method = RequestMethod.GET)
    public String toShowCertification(String id, String type, Model model){
        AppVoice appVoice = appVoiceService.getAppVoiceById(Integer.valueOf(id));
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appVoice.getAppid());
        model.addAttribute("appVoice", appVoice);
        model.addAttribute("appInfo", appInfo);
        if(type.equals("edit")){
            return "developersMgr/editRingtoneCertification";
        }else if(type.equals("show")){
            return "developersMgr/showRingtoneCertification";
        }
        return  null;
    }

    @RequestMapping(value="editAudit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> editAudit( String id, String comment, String status, String voiceUrl, String appId){
        Map<String, String> map = new HashMap<String, String>();
        Map result = appVoiceService.getmobileByAppid(appId);
        String mobile = result.get("mobile").toString();
        String sid = result.get("sid").toString();
        String cname = result.get("name").toString();
        String appname = result.get("app_name").toString();
        AppVoice appVoice = new AppVoice();
        appVoice.setId(Integer.valueOf(id));
        appVoice.setCommon(comment);
        appVoice.setStatus(status);
        appVoice.setUpdateDate(new Date());
        appVoiceService.updateAppVoice(appVoice);
        if(status.equals("01")){

            CopyVoiceUtil.copyFile(voiceUrl, true, appId, Constants.VOICE_COMMON);

            //保存tb_cb_task表
            String destFileName = appConfig.getVoicePath() + appId + ".wav";
            CbTask cbTask = new CbTask();

            int num = destFileName.indexOf("/web");
            String filePath =destFileName.substring(num+4,destFileName.length());

            Map<String, Object> paramJson = new LinkedHashMap<>();
            paramJson.put("type", CbTask.TaskType.files.toString());
            paramJson.put("filePath", filePath);
            paramJson.put("operation", "add");

            cbTask.setType(CbTask.TaskType.files);
            cbTask.setParamJson(JSonUtils.toJSon(paramJson));

            cbTaskService.saveCbTask(cbTask);

            //操作日志工具类（审核通过）
            LogUtil.log("铃声审核", "accountID："+sid+"，企业名称："+cname+"，应用名称："+appname+"，铃声审核通过。", LogType.UPDATE);

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_RING_SUCCESS, null);
        }
        else{
            //操作日志工具类(审核不通过)
            String remark = appVoiceService.getremarkByAppid(appId);
            LogUtil.log("铃声审核", "accountID："+sid+"，企业名称："+cname+"，应用名称："+appname+"，铃声审核未通过。原因："+remark, LogType.UPDATE);

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_RING_ERROR, null);
        }
        map.put("status", "0");
        return  map;
    }

    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void download(String url, HttpServletResponse response) {
        FileUtil.download(url, response);
    }
}
