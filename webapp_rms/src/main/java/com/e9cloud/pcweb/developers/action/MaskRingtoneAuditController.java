package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.SecretVoice;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 *
 * Created by wzj on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/maskRingtone")
public class MaskRingtoneAuditController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private SecretVoiceService secretVoiceService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private CbTaskService cbTaskService;

    @RequestMapping(value = "audit")
    public String audit() {
        return "developersMgr/maskRingtoneAudit";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PageWrapper list(Page page){
        return secretVoiceService.pageSecretVoiceList(page);
    }

    @RequestMapping(value="toShowCertification", method = RequestMethod.GET)
    public String toShowCertification(String id, String type, Model model){

        SecretVoice secretVoice = secretVoiceService.getSecretVoiceById(Integer.valueOf(id));
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(secretVoice.getAppid());
        model.addAttribute("appVoice", secretVoice);
        model.addAttribute("appInfo", appInfo);

        if("edit".equals(type)) {
            return "developersMgr/editMaskRingtoneCertification";
        } else if("show".equals(type)) {
            return "developersMgr/showMaskRingtoneCertification";
        }

        return  null;
    }

    @RequestMapping(value="editAudit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> editAudit(String id, String comment, String status, String voiceUrl, String appId){
        Map<String, String> map = new HashMap<String, String>();
        Map result = secretVoiceService.getmobileByAppid(appId);
        String mobile = result.get("mobile").toString();
        String sid = result.get("sid").toString();
        String cname = result.get("name").toString();
        String appname = result.get("app_name").toString();

        SecretVoice secretVoice = new SecretVoice();
        secretVoice.setId(Integer.valueOf(id));
        secretVoice.setCommon(comment);
        secretVoice.setStatus(status);
        secretVoice.setUpdateDate(new Date());
        secretVoiceService.updateSecretVoice(secretVoice);

        if("01".equals(status)) {
            CopyVoiceUtil.copyFile(voiceUrl, true, appId, Constants.VOICE_MASK);

            //保存tb_cb_task表
            String destFileName = appConfig.getVoicePath() + appId + "_callin_failed.wav";
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
            LogUtil.log("私密专线铃声审核", "accountID：" + sid + "，企业名称：" + cname + "，应用名称：" + appname + "，铃声审核通过。", LogType.UPDATE);

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_RING_SUCCESS, null);
        } else {
            //操作日志工具类(审核不通过)
            String remark = secretVoiceService.getremarkByAppid(appId);
            LogUtil.log("私密专线铃声审核", "accountID：" + sid + "，企业名称：" + cname + "，应用名称：" + appname + "，铃声审核未通过。原因：" + remark, LogType.UPDATE);

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
