package com.e9cloud.pcweb.znydd.biz;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 *
 * Created by Administrator on 2016/3/4.
 */
@Service
public class UAZnyddService {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    @Autowired
    private AuthenCompanyRecordsService authenCompanyRecordsService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private BusTypeService busTypeService;
    /**
     * 保存智能云调度用户
     *
     * @param userAdmin,authenCompany,userPerson用户信息
     * @param taxRegPic,businessLicensePic 税务登记证 公司信息
     */
    public String saveZnyddUser(UserAdmin userAdmin,AuthenCompany authenCompany, UserExternInfo userExternInfo,MultipartFile taxRegPic,MultipartFile businessLicensePic,String busType,AppInfo appInfo) throws  Exception {
        try{
            String result =  saveAnduploadCompanyInfo(taxRegPic,businessLicensePic,authenCompany);
            if("00".equals(result)) {
                userAdmin.setPwd(Constants.USER_DEFULT_PWD);
                String uid = ID.randomUUID();
                userAdmin.setUid(uid); // 用户主账号
                userAdmin.setSid(ID.randomUUID()); // 账户主账号
                userAdmin.setFeeid(ID.randomUUID()); // 计费主账号
                userAdmin.setToken(ID.randomUUID()); // 为用户生成token
                userAdmin.setStatus(Constants.USER_STATUS_OK);
                userAdmin.setAuthStatus(Constants.USER_AUTH_STATUS_YES);
                userAdmin.setCreateDate(new Date());
                DigestsUtils.encryption(userAdmin); // 密码加密
                authenCompany.setCreateDate(new Date());
                userExternInfo.setCreateDate(new Date());
//                userAdmin.setBusType(Constants.USER_TYPE_ZZYDD);
                userAdmin.setUtype(Constants.USER_TYPE_ENTERPRISE);
                authenCompany.setStatus(Constants.COMPANY_AUTH_YES);
                userAdminService.saveUserAdmin(userAdmin);
                authenCompany.setUid(uid);
                authenCompany.setPinyin(PinyinUtils.toPinYin(authenCompany.getName()));
                authenCompanyService.saveCompanyInfo(authenCompany);
                userExternInfo.setUid(uid);
                userAdminService.saveUserExtern(userExternInfo);

                AuthenCompanyRecords ac = new AuthenCompanyRecords();
                ac.setUid(authenCompany.getUid());
                ac.setAddress(authenCompany.getAddress());
                ac.setBusinessLicenseNo(authenCompany.getBusinessLicenseNo());
                ac.setBusinessLicensePic(authenCompany.getBusinessLicensePic());
                ac.setCreditNo(authenCompany.getCreditNo());
                ac.setCreateDate(authenCompany.getCreateDate());
                ac.setLegalRepresentative(authenCompany.getLegalRepresentative());
                ac.setName(authenCompany.getName());
                ac.setCardType(authenCompany.getCardType());
                ac.setOrganizationNo(authenCompany.getOrganizationNo());
                ac.setOrganizationPic(authenCompany.getOrganizationPic());
                ac.setTaxRegNo(authenCompany.getTaxRegNo());
                ac.setTaxRegPic(authenCompany.getTaxRegPic());
                ac.setTelno(authenCompany.getTelno());
                ac.setTradeId(authenCompany.getTradeId());
                ac.setWebsite(authenCompany.getWebsite());
                ac.setCreditNo(authenCompany.getCreditNo());
                ac.setRegisterNo(authenCompany.getRegisterNo());
                ac.setStatus("1");

                authenCompanyRecordsService.saveRecordInfo(ac);

//                if ("3".equals(busType)){
//                    appInfo.setStatus("00");
//                    appInfo.setCreateDate(new Date());
//                    appInfo.setAppid(ID.randomUUID());
//                    appInfo.setBusType("03");
//                    appInfo.setAppName("应用001");
//                    appInfo.setSid(userAdmin.getSid());
//                    appInfoService.saveAppInfo(appInfo);
//                }
//
//                if ("2".equals(busType)){
//                    appInfo.setStatus("00");
//                    appInfo.setCreateDate(new Date());
//                    appInfo.setAppid(ID.randomUUID());
//                    appInfo.setBusType("01");
//                    appInfo.setAppName("应用001");
//                    appInfo.setSid(userAdmin.getSid());
//                    appInfo.setCallNo("955xx");
//                    appInfoService.saveAppInfo(appInfo);
//                }
                BusinessType businessType = new BusinessType();
                businessType.setSid(userAdmin.getSid());
                businessType.setBusType("02");
                businessType.setStatus("00");
                businessType.setCreateDate(new Date());
                busTypeService.openBusinessType(businessType);

            }else{
                return result;
            }
        }catch (Exception e){
            throw new Exception(e.toString());
        }
        return "00";
    }

    /**
     * @param taxRegPic 税务登记证
     * @param businessLicensePic 营业执照
     * @param authenCompany 公司信息
     * @return
     */
    public String saveAnduploadCompanyInfo (MultipartFile taxRegPic, MultipartFile businessLicensePic,AuthenCompany authenCompany) {

        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd

        boolean isUploadT = true;
        if (taxRegPic != null) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                return "01";//文件太大，或文件后缀不正确
            }

            String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
            isUploadT = FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
            authenCompany.setTaxRegPic(dataPath + "/" + tname);
        }

        boolean isUploadB = true;
        if (businessLicensePic != null) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return "02";//文件太大，或文件后缀不正确
            }

            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            isUploadB = FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            authenCompany.setBusinessLicensePic(dataPath + "/" + bname);
        }

        if (isUploadT && isUploadB) { // 都上传成功
            return "00";
        }

        return "00";
    }


    /**
     * 修改已认证通过的信息
     *
     * @param authenCompany,userPerson用户信息
     * @param taxRegPic,businessLicensePic 税务登记证 公司信息
     */
    public String updateCertification(AuthenCompany authenCompany,MultipartFile taxRegPic,MultipartFile businessLicensePic) throws  Exception {
        try{
            String result =  updateAnduploadCompanyInfo(taxRegPic,businessLicensePic,authenCompany);
            if("00".equals(result)) {
                authenCompany.setUpdateDate(new Date());
                authenCompany.setStatus(Constants.COMPANY_AUTH_YES);
                authenCompanyService.updateCompanyInfo(authenCompany);
            }else{
                return result;
            }
        }catch (Exception e){
            throw new Exception(e.toString());
        }
        return "00";
    }

    /**
     * @param taxRegPic 税务登记证
     * @param businessLicensePic 营业执照
     * @param authenCompany 公司信息
     * @return
     */
    public String updateAnduploadCompanyInfo (MultipartFile taxRegPic, MultipartFile businessLicensePic,AuthenCompany authenCompany) {

        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd

        if (FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL && FileUtil.getFileSizebak(taxRegPic) > Constants.AUTH_IMG_NULL) {

            if (FileUtil.getFileSize(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                return "01";//文件太大，或文件后缀不正确
            }

            String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
            authenCompany.setTaxRegPic(dataPath + "/" + tname);
            return Constants.RESULT__OK;

        }

        else if (FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL && taxRegPic == null) {
            return Constants.RESULT__OK;
        }

        else if (FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_NULL && taxRegPic == null) {

            if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return "02";
            }
            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            authenCompany.setBusinessLicensePic(dataPath + "/" + bname);
            return Constants.RESULT__OK;

        }

        else if (FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_NULL && FileUtil.getFileSizebak(taxRegPic) == Constants.AUTH_IMG_NULL) {
            if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return "02";//文件太大，或文件后缀不正确
            }

            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            authenCompany.setBusinessLicensePic(dataPath + "/" + bname);
            return Constants.RESULT__OK;
        } else if (FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL || FileUtil.getFileSizebak(taxRegPic) == Constants.AUTH_IMG_NULL) {
            return Constants.RESULT__OK;
        }

        else {
            boolean isUploadT = true;
            if (taxRegPic != null) {
                if (FileUtil.getFileSize(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                    return "01";//文件太大，或文件后缀不正确
                }

                String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
                isUploadT = FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
                authenCompany.setTaxRegPic(dataPath + "/" + tname);
            }

            boolean isUploadB = true;
            if (businessLicensePic != null) {
                if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                    return "02";//文件太大，或文件后缀不正确
                }

                String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
                isUploadB = FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
                authenCompany.setBusinessLicensePic(dataPath + "/" + bname);
            }

            if (isUploadT && isUploadB) { // 都上传成功
                return Constants.RESULT__OK;
            }

            return Constants.RESULT__OK;
        }
    }
}
