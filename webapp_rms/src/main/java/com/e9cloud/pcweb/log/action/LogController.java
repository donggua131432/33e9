package com.e9cloud.pcweb.log.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.IP;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.OperationLog;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.OperationLogService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志相关Controller
 * Created by Administrator on 2016/2/22.
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AppInfoService appInfoService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public void index(){

    }

    /**
     * 查看日志详情
     * @return
     */
    @RequestMapping(value = "toShowLog", method = RequestMethod.GET)
    public String toShowLog(Model model, Long id) {
        OperationLog operationLog = operationLogService.getLogById(id);
        model.addAttribute("log", operationLog);
        return "log/show";
    }

    /**
     * 查看日志详情
     * @return
     */
    @RequestMapping(value = "pageLog", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageLog(Page page) {
        return operationLogService.pageLog(page);
    }

    /**
     * 添加日志
     */
    @RequestMapping(value = "additive")
    public void additive(String appid, HttpServletRequest request) {
        try {
            AppInfo appInfo = appInfoService.getZnyddAppInfoByAppId(appid);
            UserAdmin user = userAdminService.findUserAdminSID(appInfo.getSid());
            user.setName(appInfo.getCompanyName());
            operationLogService.saveOperationLogforClient("隐私号申请", "“" + appInfo.getCompanyName() + "（account ID：" + appInfo.getSid() + "）”的应用“"
                    + appInfo.getAppName() + "（appId：" + appid +  "）”私密号码不足", LogType.UPDATE, IP.getIp(request), user);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

}
