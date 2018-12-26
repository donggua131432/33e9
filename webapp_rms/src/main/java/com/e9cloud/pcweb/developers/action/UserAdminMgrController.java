package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.util.WebAppHomeUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 开放者列表
 * Created by Administrator on 2016/3/13.
 */
@Controller
@RequestMapping("/userAdminMgr")
public class UserAdminMgrController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    @Autowired
    private IDicDataService iDicDataService;

    @Autowired
    private BusTypeService busTypeService;

    @Autowired
    public ExtraTypeService extraTypeService;


    @Autowired
    WebAppHomeUtil webAppHomeUtil;
    @RequestMapping(value = "toUserAdmin",method = RequestMethod.GET)
    public String toUserAdminList(Model model){
        List<DicData> dicDatas = iDicDataService.findDicListByType("tradeType");

        model.addAttribute("dicDatas", dicDatas);
        model.addAttribute("dds", JSonUtils.toJSon(dicDatas));

        return "developersMgr/user_admin_list";
    }

    /**
     * 分页查询开发者列表
     * @param page 分页信息
     * @return
     */
    @RequestMapping(value = "pageUserAdmin",method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageUserAdminList(Page page){
        return userAdminService.pageUserAdminList(page);
    }

    /** 查看用户的详细信息 */
    @RequestMapping(value = "showUserAdmin",method = RequestMethod.GET)
    public String showUserAdmin(Model model, String uid){

        UserAdmin userAdmin = userAdminService.findUserAdminByUid(uid);

        AuthenCompany authenCompany = new AuthenCompany();
        authenCompany.setUid(uid);
        authenCompany = authenCompanyService.getAuthenCompany(authenCompany);

        UserExternInfo userExternInfo = userAdminService.getUserExternInfoByUid(uid);

        String count1 = busTypeService.count1(uid);
        String count3 = busTypeService.count3(uid);
        String countB = extraTypeService.count1(uid);

        model.addAttribute("userAdmin", userAdmin);
        model.addAttribute("authenCompany", authenCompany);
        model.addAttribute("userExternInfo", userExternInfo);

        model.addAttribute("count1", count1);
        model.addAttribute("count3", count3);
        model.addAttribute("countB", countB);

        return "developersMgr/user_admin_info";
    }

    /**
     * 更改开发者的状态
     * @param userAdmin 开发者信息
     * @return
     */
    @RequestMapping(value = "updateUserAdminStatus",method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateUserAdminStatus(UserAdmin userAdmin,String name,String uid){
        userAdminService.updateUserAdminStatusByUid(userAdmin);
        if(!StringUtils.isEmpty(userAdmin.getStatus())){
            String status = userAdmin.getStatus();
            UserAdmin user = userAdminService.findUserAdminByUid(uid);
            String sid = user.getSid();
            if(Constants.USER_STATUS_FROZEN.equals(userAdmin.getStatus())||Constants.USER_STATUS_DISABLED.equals(userAdmin.getStatus())){
                webAppHomeUtil.removeAccesTokenByUid(userAdmin.getUid());
            }
            if("1".equals(status)){
                LogUtil.log("启用账户", "accountID：" +sid+" , 客户名称: "+name + " , 启用账户，恢复正常。" , LogType.UPDATE);

            }
            if("2".equals(status)){
                LogUtil.log("冻结账户", "accountID：" +sid+" , 客户名称: "+name + "  , 账户冻结。" , LogType.UPDATE);
            }
            if("3".equals(status)){
                LogUtil.log("禁用账户", "accountID：" +sid+" , 客户名称: "+name + " , 账户禁用" , LogType.UPDATE);
            }
        }
        return new JSonMessage("ok", "", null);
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals =  userAdminService.getPageUserAdminList(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sid",String.valueOf(total.get("sid")));
                map.put("email", String.valueOf(total.get("email")));
                map.put("name", String.valueOf(total.get("name")));
                map.put("tradeId",String.valueOf(total.get("tradeId")));
                map.put("authStatus", String.valueOf(total.get("authStatus")));

                if("0".equals(total.get("authStatus"))){
                    map.put("authStatus","未认证");
                }
                if("1".equals(total.get("authStatus"))){
                    map.put("authStatus","认证中");
                }
                if("2".equals(total.get("authStatus"))){
                    map.put("authStatus","已认证");
                }
                if("3".equals(total.get("authStatus"))){
                    map.put("authStatus","认证不通过");
                }
                map.put("fee", String.valueOf(total.get("fee")));

//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if("".equals(total.get("createDate")) || null == total.get("createDate")){
                     map.put("createDate", "");
                   }
                 else {
                     map.put("createDate", String.valueOf(total.get("createDate").toString().substring(0,19)));
                 }

                map.put("status", String.valueOf(total.get("status")));
                if("1".equals(total.get("status"))){
                    map.put("status","正常");
                }
                if("2".equals(total.get("status"))){
                    map.put("status","冻结");
                }
                if("3".equals(total.get("status"))){
                    map.put("status","禁用");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();
        titles.add("accountID");
        titles.add("客户账号");
        titles.add("客户名称");
        titles.add("所属行业");
        titles.add("认证状态");
        titles.add("账户余额（元）");
        titles.add("注册时间");
        titles.add("账户状态");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("email");
        columns.add("name");
        columns.add("tradeId");
        columns.add("authStatus");
        columns.add("fee");
        columns.add("createDate");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "开发者列表");
        map.put("excelName","开发者列表");
        return new ModelAndView(new POIXlsView(), map);
    }


}
