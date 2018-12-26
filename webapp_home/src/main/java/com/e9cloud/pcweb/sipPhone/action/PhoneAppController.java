package com.e9cloud.pcweb.sipPhone.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;


/**
 * 云话机  应用管理
 * Created by lixin on 2016/10/25.
 */
@Controller
@RequestMapping("/phoneApp")
public class PhoneAppController extends BaseController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppService appService;

    /**
     * app应用列表
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "sipPhone/appPhoneList";
    }

    /**
     * app应用列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper findAppList(HttpServletRequest request, HttpServletResponse response, AppInfo app, Page page) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        int count = 0;
        List<AppInfo> appList = new ArrayList<AppInfo>();
        PageWrapper pageWrapper = null;
        Map param = new HashMap<String,String>();
        param.put("sid",account.getSid());
        try{
            logger.info("");
            param.put("appName",app.getAppName());
            param.put("appType",app.getAppType());
            param.put("busType", "05");
            count = appService.findAppListCountByMap(param);
            pageWrapper = new PageWrapper(page.getPage(), page.getPageSize(), count, null);
            param.put("start",pageWrapper.getFromIndex());
            param.put("pageSize",page.getPageSize());
            appList = appService.findAppListByMap(param);
            pageWrapper.setRows(appList);
        }catch (Exception e){
            logger.info("--------findAppList error----",e);
        }
        return  pageWrapper;
    }
    /**
     * app应用查看
     */
    @RequestMapping(value = "view", method = RequestMethod.POST)
    @ResponseBody
    public AppInfo appView(HttpServletRequest request, HttpServletResponse response, AppInfo app) {
        try{
            String appid = app.getAppid();
            app = appService.findAppInfoByAppId(appid);
        }catch (Exception e){
            logger.info("--------appView error----",e);
        }
        return app;
    }

    @RequestMapping("appInfoView")
    public String appInfoView(AppInfo appInfo, Model model){
        model.addAttribute("appInfo",appService.findAppInfoByAppId(appInfo.getAppid()));
        model.addAttribute("appExtra",appService.findAppExtraByAppId(appInfo.getAppid()));
        return "sipPhone/appPhoneView";
    }

    /**
     * app应用创建页面
     */
    @RequestMapping(value = "savePage")
    public String appSavePage(HttpServletRequest request, HttpServletResponse response, AppInfo app) {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        Account dbAccount = (Account)accountService.getAccountByUid(account.getUid());
        String auth = dbAccount.getAuthStatus();
        if ("2".equals(auth)){
            return "sipPhone/appPhoneSave";
        }
        else
            return "app/appAuthen";
    }
    /**
     * app应用编辑
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String appSave(HttpServletRequest request, HttpServletResponse response, AppInfo app, AppInfoExtra appInfoExtra, int[] recordingTypes) throws Exception {
        try{
            Account account =  (Account) request.getSession().getAttribute("userInfo");
            String sid = account.getSid();
            app.setSid(sid);
            app.setAppid(ID.randomUUID());
            app.setCreateDate(new Date());
            app.setBusType(Constants.APP_BUS_SIP_PHONE);
            appService.saveApp(app);
            appInfoExtra.setAppid(app.getAppid());
            logger.info("-------------recordingTypes:{}-------------", recordingTypes);
            appInfoExtra.setAppid(app.getAppid());
            appInfoExtra.setVoiceCode("G729");
            int recordingType = Tools.bitToDecimal(recordingTypes);
            appInfoExtra.setRecordingType(recordingType);
            appService.saveAppExtra(appInfoExtra);
            return this.redirect+appConfig.getAuthUrl()+"phoneApp/index";
        }catch (Exception e){
            logger.info("--------appSave error----",e);
            throw new Exception(e);
        }
    }

    /**
     * app应用编辑
     */
    @RequestMapping("appInfoEdit")
    public String appInfoEdit(AppInfo appInfo, Model model){
        logger.info("------------------------------------------------GET AppMgrController appInfoEdit START----------------------------------------------------------------");
        model.addAttribute("appInfo",appService.findAppInfoByAppId(appInfo.getAppid()));
        model.addAttribute("appExtra",appService.findAppExtraByAppId(appInfo.getAppid()));
        return "sipPhone/appPhoneEdit";
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String appUpdate(HttpServletRequest request, AppInfo app, AppInfoExtra appInfoExtra,int[] valueAddeds, int[] recordingTypes) throws Exception{
        logger.info("------------------------------------------------GET AppMgrController appUpdate START----------------------------------------------------------------");
        String limitStatus = request.getParameter("limitStatus");
        String a = request.getParameter("quota1");
        String b = request.getParameter("quota");
        String c = request.getParameter("quota_");

        BigDecimal fee = null;
        if (!"".equals(a) && !"".equals(b)){
            logger.info("");
            BigDecimal quota1 = new BigDecimal(a);
            BigDecimal quota =new BigDecimal(b);
            if("00".equals(limitStatus)){
                fee = quota.add(quota1);
            }
            if("01".equals(limitStatus)){
                fee = quota.subtract(quota1);
            }
            app.setQuota(fee);
        }
        if ((a == null||"".equals(a)) && "".equals(b)&& !"".equals(c)){
            BigDecimal quota =new BigDecimal(c);
            app.setQuota(quota);
        }
        app.setUpdateDate(new Date());
        appService.updateApp(app);


        logger.info("-------------valueAddeds:{},recordingTypes:{}-------------", valueAddeds, recordingTypes);
        appInfoExtra.setAppid(app.getAppid());
        int valueAdded = Tools.bitToDecimal(valueAddeds);
        int recordingType = Tools.bitToDecimal(recordingTypes);

//        appInfoExtra.setValueAdded(valueAdded);
        appInfoExtra.setRecordingType(recordingType);
        appService.updateAppExtra(appInfoExtra);

        return "sipPhone/appPhoneList";
    }
    /**
     * app应用编辑
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object>  appDel(HttpServletRequest request, HttpServletResponse response,AppInfo app,String appid) {
        Map<String,Object> map = new HashMap<>();
        try{

            Account account =  (Account) request.getSession().getAttribute("userInfo");
            AppInfo dbApp =  appService.findAppInfoById(app.getId());

            logger.info(appid);
            //此处验证app的主键ID为本账户的ID
            if(!dbApp.getSid().equals(account.getSid())){
                map.put("status","99");
            }
            Integer count = appService.findAppNumCountByAppID(appid);
            if (count>0){
                map.put("status","01");
            }else {
                app.setStatus(Constants.APP_STATUS_DEL);
                app.setUpdateDate(new Date());
                appService.updateApp(app);
                map.put("status","00");
            }

        }catch (Exception e){
            logger.info("--------appSave error----",e);
            map.put("status","99");
        }
        return map;
    }

    @RequestMapping(value = "/getAppInfoUnionSubApp", method = RequestMethod.POST)
    @ResponseBody
    public List<AppInfo> getAppInfoUnionSubApp(HttpServletRequest request){
        logger.info("------------------------------------------------GET AppMgrController getAppInfoUnionSubApp START----------------------------------------------------------------");
        //获取当前用户信息
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        List<AppInfo> appInfoList = appService.findAppInfoUnionSubApp(account.getSid());
        return appInfoList;
    }

    @RequestMapping(value = "/getALLAppInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<AppInfo> getALLAppInfo(HttpServletRequest request){
        logger.info("------------------------------------------------GET AppMgrController getALLAppInfo START----------------------------------------------------------------");
        //获取当前用户信息
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        List<AppInfo> appInfoList = appService.findALLAppInfo(account.getSid());
        return appInfoList;
    }
}