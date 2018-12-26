package com.e9cloud.pcweb.account.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.AuthenCompanyRecords;
import com.e9cloud.mybatis.domain.UserCompany;
import com.e9cloud.mybatis.service.AuthenService;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by admin on 2016/3/5.
 */
@Controller
@RequestMapping("/authen")
public class AuthenController  extends BaseController {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private UpgradeService upgradeService;

    private final static String authen_add = "acc/authenView";
    private final static String authing = "acc/authing";
    private final static String auth_Index = "acc/authenIndex";
    private final static String auth_Failed = "acc/authFailed";
    private final static String auth_Again = "acc/authAgain";
    /**
     * 判断认证状态
     */

    @RequestMapping("/authstatus")
    public String authstatus(HttpServletRequest request) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String sFlag = authenService.getAuthStatusByUid(account.getUid());
        if("0".equals(sFlag)){

            return this.redirect+appConfig.getAuthUrl()+"authen/addper";
        }
        else if("1".equals(sFlag)){
            return this.redirect+appConfig.getAuthUrl()+"authen/authing";
        }
        else if("2".equals(sFlag)){
            return this.redirect+appConfig.getAuthUrl()+"authen/view";
        }
        else {
            return this.redirect+appConfig.getAuthUrl()+"authen/authFailed";
        }

    }
    /**
     * 填写认证信息
     */
    @RequestMapping("/addper")
    public String addpersonal(HttpServletRequest request) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String sFlag = authenService.getAuthStatusByUid(account.getUid());
        if("0".equals(sFlag)){

            return authen_add;
        }
        if("1".equals(sFlag)){

            return authing;
        }
        if("2".equals(sFlag)){

            return this.redirect+appConfig.getAuthUrl()+"authen/view";
        }
        else {
            return auth_Failed;
        }
    }

    /**
     * 正在认证.....
     */
    @RequestMapping(value = "/authing" ,method =RequestMethod.GET)
    public String authing(HttpServletRequest request, Model model) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        String sFlag = authenService.getAuthStatusByUid(account.getUid());
        AuthenCompanyRecords company = authenService.findAuthingInfoByUid(uid);
        model.addAttribute("company", company);
        if("0".equals(sFlag)){

            return authen_add;
        }
        if("1".equals(sFlag)){

            return authing;
        }
        if("2".equals(sFlag)){

            return this.redirect+appConfig.getAuthUrl()+"authen/view";
        }
        else {
            return auth_Failed;
        }
    }
    /**
     * 认证失败..... failed
     */
    @RequestMapping(value = "/authFailed" ,method =RequestMethod.GET)
    public String authFailed(HttpServletRequest request, Model model) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        String sFlag = authenService.getAuthStatusByUid(account.getUid());
        AuthenCompanyRecords company = authenService.findAuthingInfoByUid(uid);
        model.addAttribute("company", company);
        if("0".equals(sFlag)){

            return authen_add;
        }
        if("1".equals(sFlag)){

            return authing;
        }
        if("2".equals(sFlag)){

            return this.redirect+appConfig.getAuthUrl()+"authen/view";
        }
        else {
            return auth_Failed;
        }
    }
    /**
     * 再次认证
     */
    @RequestMapping("/authAgain")
    public String authAgain(HttpServletRequest request, Model model) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String uid = account.getUid();
        AuthenCompanyRecords company = authenService.findAuthingInfoByUid(uid);

        model.addAttribute("company", company);
        return auth_Again;
    }

    /**
     * 已经认证
     */
    @RequestMapping("/authIndex")
    public String authIndex(HttpServletRequest request) throws Exception {
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String sFlag = authenService.getAuthStatusByUid(account.getUid());
        if("0".equals(sFlag)){

            return authen_add;
        }
        if("1".equals(sFlag)){

            return authing;
        }
        else {
            return auth_Index;
        }


    }

    /**
     * 添加认证信息
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String authSave(Model model, HttpServletRequest request, UserCompany company,
                           @RequestParam(required = false, value = "taxRegFile") MultipartFile taxRegPic,
                           @RequestParam(required = false, value = "businessLicenseFile") MultipartFile businessLicensePic) {

        try{

            Account account =  this.getCurrUser(request);

            String uid = account.getUid();
            company.setUid(uid);
            company.setCreateDate(new Date());
            company.setStatus("0");
            String sFlag = authenService.getAuthStatusByUid(account.getUid());
            if ("1".equals(sFlag)||"2".equals(sFlag)){
                model.addAttribute("error", "abc");
                return authen_add;
            }
            else {
            String resultType = upgradeService.saveAnduploadCompanyInfo(company, taxRegPic, businessLicensePic);

            if (Constants.RESULT_STATUS_OK.equals(resultType)) { // 认证成功

                account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
                updateCurrUser(request, account); // 更新内存中用户的状态
                return this.redirect + appConfig.getAuthUrl() + "authen/authing";

            } else if (Constants.RESULT_STATUS_HAS_REG.equals(resultType)) { // 个别信息在表中已经存在

                model.addAttribute("error", "existed");
                return authen_add;
            }
            }

        }catch (Exception e){
            logger.info("--------authSave error {}----", e);
        }
        model.addAttribute("error", "error");

        return authen_add;
    }


    /**
     * 修改认证信息
     */
    @RequestMapping(value = "updateAuth", method = RequestMethod.POST)
    public String updateAuth(Model model, HttpServletRequest request, UserCompany company,
                           @RequestParam(required = false, value = "taxRegFile") MultipartFile taxRegPic,
                           @RequestParam(required = false, value = "businessLicenseFile") MultipartFile businessLicensePic) {

        try{
            Account account =  this.getCurrUser(request);
            String uid = account.getUid();
            company.setUid(uid);
            company.setCreateDate(new Date());
            company.setStatus("0");
            String sFlag = authenService.getAuthStatusByUid(account.getUid());
            if ("1".equals(sFlag)||"2".equals(sFlag)){
                model.addAttribute("error", "abc");
                return auth_Again;
            }
            else {
                String resultType = upgradeService.updateAnduploadCompanyInfo(company, taxRegPic, businessLicensePic);

                if (Constants.RESULT_STATUS_OK.equals(resultType)) { // 认证成功

                    account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
                    updateCurrUser(request, account); // 更新内存中用户的状态
                    return this.redirect + appConfig.getAuthUrl() + "authen/authing";

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.info("--------authSave error {}----", e);
        }
        model.addAttribute("error", "error");
        return auth_Again;
    }


    /**
     * 验证公司信息的唯一性
     * @param company
     * @return
     */
    @RequestMapping(value = "companyUnique", method = RequestMethod.POST)
    @ResponseBody
    public boolean companyUnique(UserCompany company) {
        logger.info("------------------ AuthenController companyUnique start ------------------------");

        boolean b = authenService.checkCompanyUnique(company);

        logger.info("------------------ AuthenController companyUnique end ------------------------");

        return b;
    }
    /**
     * 认证信息查看
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String appView(HttpServletRequest request, Model model) {
        try{
            Account account =  (Account) request.getSession().getAttribute("userInfo");
            String uid = account.getUid();
            UserCompany company = authenService.findAuthInfoByUid(uid);
            model.addAttribute("company", company);

        }catch (Exception e){
            logger.info("--------AuthInfoView error----",e);
        }
        return auth_Index;
    }
}
