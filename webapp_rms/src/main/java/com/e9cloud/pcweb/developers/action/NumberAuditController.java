package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.developers.biz.NumberAuditBizService;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.sms.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * DeveloperController用于开发者管理
 *
 * Created by wujiang on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/number")
public class NumberAuditController extends BaseController {

    @Autowired
    private NumberAuditBizService numberAuditBizService;

    @Autowired
    private AppNumberService appNumberService;

    @Autowired
    private AppVoiceService appVoiceService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppNumberRestService appNumberRestService;

    @Autowired
    private UserAdminService userAdminService;

    @RequestMapping(value = "audit")
    public String audit() {
        return "developersMgr/numberAudit";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public PageWrapper list(Page page){
        return appNumberService.pageAppNumberList(page);
    }

    @RequestMapping(value = "toShowCertification", method = RequestMethod.GET)
    public String toShowCertification(String id, String type, Model model){
        AppNumber appNumber = appNumberService.getAppNumberById(Long.valueOf(id));
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appNumber.getAppid());
        model.addAttribute("appNumber", appNumber);
        model.addAttribute("appInfo", appInfo);
        if(type.equals("edit")){
            return "developersMgr/editNumberCertification";
        }else if(type.equals("show")){
            return "developersMgr/showNumberCertification";
        }
        return  null;
    }

    @RequestMapping(value = "editAudit")
    @ResponseBody
    public Map<String, String> editAudit(String id,String appId, String comment, String status){
        Map<String, String> map = new HashMap<String, String>();
        Map result = appVoiceService.getmobileByAppid(appId);
        String mobile = result.get("mobile").toString();
        String sid = result.get("sid").toString();
        String cname = result.get("name").toString();
        String appname = result.get("app_name").toString();

        AppNumber appNumber = new AppNumber();
        appNumber.setId(Long.valueOf(id));
        appNumber.setRemark(comment);
        appNumber.setNumberStatus(status);
        appNumber.setReviewtime(new Date());
        if(status.equals("01")){
            AppNumber appNumber1 = appNumberService.getAppNumberById(Long.valueOf(id));
            if(appNumber1.getNumberType().equals("02")){
                String number[] = appNumber1.getNumber().split("-");
                String num0 = number[0].substring(number[0].length()-4, number[0].length());
                String num1 = number[1].substring(number[1].length()-4, number[1].length());
                Long length = Long.valueOf(num1)-Long.valueOf(num0);
                if(length > 0 && length <=999){
                    appNumberService.updateAppNumber(appNumber);
                    List<AppNumberRest> appNumberRestList = new ArrayList<AppNumberRest>();
                    for(int i=0; i<= length; i++){
                        AppNumberRest appNumberRest = new AppNumberRest();
                        String preNum = number[0].substring(0, number[0].length()-4);
                        String sufNum = String.valueOf(Long.valueOf(num0) + i);
                        if(sufNum.length() < 4){
                            switch(4-sufNum.length()){
                                case 1 : sufNum = "0" + sufNum; break;
                                case 2 : sufNum = "00" + sufNum; break;
                                case 3 : sufNum = "000" + sufNum; break;
                            }
                        }
                        String newNumber = preNum + sufNum;
                        appNumberRest.setAddtime(new Date());
                        appNumberRest.setAppid(appNumber1.getAppid());
                        appNumberRest.setNumberId(appNumber1.getId());
                        appNumberRest.setNumber(newNumber);
                        appNumberRestList.add(appNumberRest);
                    }
                    appNumberRestService.saveAppNumberList(appNumberRestList);
                    map.put("status", "0");
                }else if(length > 999){
                    map.put("status", "1");
                }

            }else{
                AppNumberRest appNumberRest = new AppNumberRest();
                appNumberRest.setAddtime(new Date());
                appNumberRest.setAppid(appNumber1.getAppid());
                appNumberRest.setNumberId(appNumber1.getId());
                appNumberRest.setNumber(appNumber1.getNumber());
                appNumberService.updateAppNumber(appNumber);
                appNumberRestService.saveAppNumber(appNumberRest);
                map.put("status", "0");
            }

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_SUCCESS, null);

            //操作日志工具类（审核通过）
            long lon = Long.parseLong(id);
            AppNumber num  = appNumberService.getAppNumberById(lon);
            String number = num.getNumber();
            LogUtil.log("号码审核", "accountID："+sid+" ，企业名称："+cname+" ，应用名称："+appname+"。 号码："+number+"审核通过。", LogType.UPDATE);


        }else{
            appNumberService.updateAppNumber(appNumber);
            map.put("status", "0");

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_ERROR, null);

            //操作日志工具类(审核不通过)
            long lon = Long.parseLong(id);
            AppNumber num  = appNumberService.getAppNumberById(lon);
            String remark = num.getRemark();
            String number = num.getNumber();
            LogUtil.log("号码审核", "accountID："+sid+" ，企业名称："+cname+" ，应用名称："+appname+" ，号码: "+number+" 审核未通过。原因："+remark, LogType.UPDATE);
        }
        return map;
    }

    // 批量审核
    @RequestMapping("auditAll")
    public String auditAll(Model model, String appid, String[] id) {

        logger.info("=========appid:{}, ids:{}=========", appid, id);

        List<AppNumber> appNumbers = appNumberService.getAppNumbersByIds(id);
        AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appid);

        model.addAttribute("appNumbers", appNumbers);
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("ids", Tools.joinArray(id, "-"));

        return "developersMgr/editAllNumberCertification";
    }

    @RequestMapping(value = "editAuditAll")
    @ResponseBody
    public Map<String, String> editAuditAll(String id, String appId, String comment, String status){

        logger.info("================ id:{}, comment:{} ================", id, comment);

        Map<String, String> map = numberAuditBizService.auditAllExecute(id, appId, comment, status);

        return map;
    }
}
