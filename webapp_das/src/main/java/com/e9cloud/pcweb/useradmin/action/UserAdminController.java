package com.e9cloud.pcweb.useradmin.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AppService;
import com.e9cloud.mybatis.service.SipManagerService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *UserAdminController用户西信息模块相关的业务控制，
 *
 * Created by DuKai on 2016/2/19.
 *
 */
@Controller
@RequestMapping(value = "/userAdmin")
public class UserAdminController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AppService appService;

    @Autowired
    private SipManagerService sipManagerService;

    /**
     * 得到用户信息及app 列表
     * @param userAdmin 用户信息
     * @param busType app的业务业务类型
     * @return
     * @throws Exception
     */
    @RequestMapping("/loadAppAndCity")
    @ResponseBody
    public JSonMessage loadAppAndCity(UserAdmin userAdmin, String busType) throws Exception {
        logger.info("---- loadAppAndCity start -------");
        JSonMessage jSonMessage = new JSonMessage();
        Map<String, Object> data = new HashMap<>();

        if(Tools.isNotNullStr(userAdmin.getSid())){
            UserAdmin userAdminResult = userAdminService.getUserAdminWithCompany(userAdmin);
            if(userAdminResult != null){
                data.put("userAdmin", userAdminResult);

                if ("03".equals(busType)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("busType",busType);
                    map.put("sid",userAdmin.getSid());
                    List<AppInfo> appInfos = appService.getAllSpInfo(map);
                    data.put("appInfos", appInfos);
                    List<SipRelayInfo> subApps = sipManagerService.getSipRelayInfoListBySid(userAdminResult.getSid());
                    data.put("subApps", subApps);
                }else if("01".equals(busType)){
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("busType",busType);
                    map1.put("sid",userAdmin.getSid());
                    List<AppInfo> appInfos = appService.getAllSpInfo(map1);
                    data.put("appInfos", appInfos);
                }else {
                    List<AppInfo> appInfos = appService.findALLAppInfo(userAdminResult.getSid());
                    data.put("appInfos", appInfos);
                }
                jSonMessage.setCode(R.OK);
                jSonMessage.setData(data);
            }else{
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("用户不存在");
            }
        }else{
            Map<String, Object> map2 = new HashMap<>();
            map2.put("busType",busType);
            List<AppInfo> appInfos = appService.getAllSpInfo(map2);
            data.put("appInfos", appInfos);
            List<SipRelayInfo> subApps = sipManagerService.getSipRelayInfoListBySid(null);
            data.put("subApps", subApps);
            List<UserAdmin> userAdminResults = userAdminService.getUserAdminWithCompanyList(userAdmin);
            data.put("userAdmins", userAdminResults);
            jSonMessage.setCode(R.OK);
            jSonMessage.setData(data);
        }
        return jSonMessage;
    }

    @RequestMapping("getCompanyNameAndSidByPage")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidByPage(Page page){

        logger.info("-------------UserController getCompanyNameAndSidByPage start-------------");

        return userAdminService.getCompanyNameAndSidByPage(page);
    }

    @RequestMapping("getCompanyNameAndSidForSelect2")
    @ResponseBody
    public PageWrapper getCompanyNameAndSidForSelect2(Page page){

        logger.info("-------------UserController getCompanyNameAndSidForSelect2 start-------------");

        String stype = Tools.toStr(page.getParams().get("stype"));
        if (Tools.isNotNullStr(stype)) {
            page.setTimemax(Tools.getDate(0));
            if ("7".equals(stype)) { // 近7日
                page.setTimemin(Tools.getDate(-7));
            } else if ("30".equals(stype)) { // 近30日
                page.setTimemin(Tools.getDate(-30));
            } else { // 昨天
                page.setTimemin(Tools.getDate(-1));
            }
        }

        return userAdminService.getCompanyNameAndSidForSelect2(page);
    }

}
