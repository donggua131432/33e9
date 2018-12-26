package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AuthenCompanyRecordsService;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.pcweb.znydd.biz.UAZnyddService;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.sms.SmsUtils;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeveloperController用于开发者管理
 *
 * Created by wujiang on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/developers")
public class DevelopersController extends BaseController {

    @Autowired
    private AuthenCompanyService authenCompanyService;
    @Autowired
    private AuthenCompanyRecordsService authenCompanyRecordsService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private UAZnyddService uaZnyddService;

    @Autowired
    private IDicDataService dicDataService;

    @RequestMapping(value = "audit")
    public String audit(Model model) {
        List<DicData> dicDatas = dicDataService.findDicListByType("tradeType");

        model.addAttribute("dicDatas", dicDatas);
        model.addAttribute("dds", JSonUtils.toJSon(dicDatas));
        return "developersMgr/certificationAudit";
    }

    @RequestMapping(value="auditDetail")
    @ResponseBody
    public PageWrapper auditDetail(Page page){
        return authenCompanyRecordsService.getAuthenCompanyRecordsPage(page);
    }

    @RequestMapping(value = "toShowCertification", method = RequestMethod.GET)
    public String toShowCertification(Model model, Integer id, String type){
        AuthenCompanyRecords authenCompanyRecords = authenCompanyRecordsService.getAuthenCompanyRecordsById(id);
        model.addAttribute("authenCompanyRecords", authenCompanyRecords);

        UserAdmin u = new UserAdmin();
        u.setUid(authenCompanyRecords.getUid());
        UserAdmin userAdmin = userAdminService.getUserAdmin(u);
        model.addAttribute("userAdmin", userAdmin);

        AuthenCompany authenCompany =  authenCompanyService.selectAuthenCompanyById(id);
        model.addAttribute("authenCompany", authenCompany);

        if(authenCompanyRecords.getTradeId() != null){
            DicData d = new DicData();
            d.setCode(authenCompanyRecords.getTradeId());
            d.setTypekey("tradeType");
            DicData dicData = dicDataService.findDicByCode(d);
            model.addAttribute("dicData", dicData);
        }

        if(type.equals("edit")){
            return "developersMgr/editCertification";
        }else if(type.equals("show")){
            return "developersMgr/showCertification";
        }
        else if(type.equals("authshow")){
            return "developersMgr/authshowCertification";
        }
        else if(type.equals("update")){
            return "developersMgr/updateCertification";
        }
        return "";

    }

    /**
     * 公司认证审核
     */
    @RequestMapping(value="editAudit", method= RequestMethod.POST)
    public String editAudi(String status, String comment, String id, String uid)throws BadHanyuPinyinOutputFormatCombination {
        AuthenCompanyRecords authenCompanyRecords = new AuthenCompanyRecords();
        authenCompanyRecords.setStatus(status);
        authenCompanyRecords.setId(Integer.valueOf(id));
        authenCompanyRecords.setComment(comment);
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setUid(uid);
        UserAdmin user = userAdminService.findUserAdminByUid(uid);
        String mobile = user.getMobile();

        if(status.equals("1")){
            userAdmin.setAuthStatus("2");
            AuthenCompanyRecords ac = authenCompanyRecordsService.getAuthenCompanyRecordsById(Integer.valueOf(id));
            AuthenCompany authenCompany = new AuthenCompany();
            authenCompany.setUid(ac.getUid());
            authenCompany.setAddress(ac.getAddress());
            authenCompany.setBusinessLicenseNo(ac.getBusinessLicenseNo());
            authenCompany.setBusinessLicensePic(ac.getBusinessLicensePic());
            authenCompany.setCreditNo(ac.getCreditNo());
            authenCompany.setCreateDate(ac.getCreateDate());
            authenCompany.setLegalRepresentative(ac.getLegalRepresentative());
            authenCompany.setName(ac.getName());
            authenCompany.setPinyin(PinyinUtils.toPinYin(ac.getName()));
            authenCompany.setCardType(ac.getCardType());
            authenCompany.setOrganizationNo(ac.getOrganizationNo());
            authenCompany.setOrganizationPic(ac.getOrganizationPic());
            authenCompany.setTaxRegNo(ac.getTaxRegNo());
            authenCompany.setTaxRegPic(ac.getTaxRegPic());
            authenCompany.setTelno(ac.getTelno());
            authenCompany.setTradeId(ac.getTradeId());
            authenCompany.setWebsite(ac.getWebsite());
            authenCompany.setCreditNo(ac.getCreditNo());
            authenCompany.setRegisterNo(ac.getRegisterNo());
            authenCompanyService.saveCompanyInfo(authenCompany);
            authenCompanyRecordsService.updateStatus(authenCompanyRecords);
            userAdminService.updateAuthStatus(userAdmin);

            // 发送站内信
            Sender.sendMessage(user, TempCode.SEND_SMS_ANTH_SUCCESS, null);

            //操作日志工具类（审核通过）
            String sid = user.getSid();
            String cname = ac.getName();
            LogUtil.log("企业资质认证审核", "accountID："+sid+"，客户名称："+cname+", 企业资质认证通过。", LogType.INSERT);

        }else if(status.equals("2")){
            userAdmin.setAuthStatus("3");
            authenCompanyRecordsService.updateStatus(authenCompanyRecords);
            userAdminService.updateAuthStatus(userAdmin);

            // 发送站内信
            Sender.sendMessage(user, TempCode.SEND_SMS_ANTH_ERROR, null);

            //操作日志工具类（审核未通过）
            AuthenCompanyRecords ac = authenCompanyRecordsService.getAuthenCompanyRecordsById(Integer.valueOf(id));
            String sid = user.getSid();
            String cname = ac.getName();
            String remark = ac.getComment();
            LogUtil.log("企业资质认证审核", "accountID："+sid+"，客户名称："+cname+",  企业资质认证未通过。  原因："+remark, LogType.UPDATE);

        }


        return "developersMgr/certificationAudit";
    }


    // 管理员 修改已认证通过的信息
    @RequestMapping(value="updateCertification", method = RequestMethod.POST)
    public String updateCertification(HttpServletRequest request,  AuthenCompany authenCompany,
                               @RequestParam(required = false, value = "taxRegFile") MultipartFile taxRegPic,
                               @RequestParam(required = false, value = "businessLicenseFile") MultipartFile businessLicensePic, Model model){
        logger.info("-------------updateCertification updateCertification start-------------");

        String result = null;
        String authAddress = request.getParameter("authAddress");
        String companyName = request.getParameter("companyName");
        authenCompany.setName(companyName);
        authenCompany.setAddress(authAddress);
        try {
            result =  uaZnyddService.updateCertification(authenCompany,taxRegPic,businessLicensePic);
        } catch (Exception e) {
            logger.info("-------------updateCertification error-------------",e);
            model.addAttribute("result", "error");
        }
        //操作日志工具类
        LogUtil.log("企业资质认证修改", "客户名称："+companyName+",  企业资质认证修改。", LogType.UPDATE);
        logger.info("-------------updateCertification updateCertification end-------------");
        model.addAttribute("result", result);
        return "developersMgr/updateCertification";
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals =  authenCompanyRecordsService.getPageAuthenCompanyRecords(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {

            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sid",String.valueOf(total.get("sid")));
                map.put("email", String.valueOf(total.get("email")));
                map.put("c_name", Tools.toStr(total.get("c_name")));
                map.put("tradeName",Tools.toStr(total.get("tradeName")));
                map.put("authStatus", String.valueOf(total.get("auth_status")));

                if("0".equals(total.get("auth_status"))){
                    map.put("authStatus","未认证");
                }
                if("1".equals(total.get("auth_status"))){
                    map.put("authStatus","认证中");
                }
                if("2".equals(total.get("auth_status"))){
                    map.put("authStatus","已认证");
                }
                if("3".equals(total.get("auth_status"))){
                    map.put("authStatus","认证不通过");
                }
                map.put("fee", String.valueOf(total.get("fee")));

                if("".equals(total.get("createDate")) || null == total.get("createDate")){
                    map.put("createDate", "");
                }
                else {
                    map.put("createDate", String.valueOf(total.get("createDate").toString().substring(0,19)));
                }

                map.put("status", String.valueOf(total.get("status")));

                if("0".equals(total.get("status"))){
                    map.put("status","待审核");

                }
                if("1".equals(total.get("status"))){
                    map.put("status","审核通过");
                }
                if("2".equals(total.get("status"))){
                    map.put("status","审核不通过");
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
        titles.add("审核状态");

        List<String> columns = new ArrayList<String>();

        columns.add("sid");
        columns.add("email");
        columns.add("c_name");
        columns.add("tradeName");
        columns.add("authStatus");
        columns.add("fee");
        columns.add("createDate");
        columns.add("status");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "信息资质审核列表");
        map.put("excelName","信息资质审核列表");
        return new ModelAndView(new POIXlsView(), map);
    }

}
