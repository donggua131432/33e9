package com.e9cloud.pcweb.account.biz;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.Constants;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzj on 2016/3/9.
 */
@Service
public class UpgradeService {

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VoiceService voiceService;
    @Autowired
    private SecretVoiceService secretService;
    @Autowired
    private MouldVoiceService mouldVoiceService;

    @Autowired
    private VoiceverifyTempService voiceverifyTempService;


    /**
     * 上传并保存企业信息
     * @param company 企业信息
     * @param taxRegPic 税务登记证
     * @param businessLicensePic 营业执照
     * @return
     */
    public String saveAnduploadCompanyInfo(UserCompany company, MultipartFile taxRegPic, MultipartFile businessLicensePic) {

        if (!authenService.checkCompanyUniqueForAll(company)) { // 个别信息已经存在
            return Constants.RESULT_STATUS_HAS_REG;
        }

        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd

        boolean isUploadT = true;
        if (taxRegPic != null) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                return Constants.RESULT_STATUS_ERROR;
            }

            String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
            isUploadT = FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
            company.setTaxRegPic(dataPath + "/" + tname);
        }

        boolean isUploadB = true;
        if (businessLicensePic != null) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return Constants.RESULT_STATUS_ERROR;
            }

            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            isUploadB = FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            company.setBusinessLicensePic(dataPath + "/" + bname);
        }


        if (isUploadT && isUploadB) { // 都上传成功

            authenService.saveAuthToRecords(company);

            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }

        return Constants.RESULT_STATUS_ERROR;
    }



    /**
     * 修改并保存认证信息
     * @param company 企业信息
     * @param taxRegPic 税务登记证
     * @param businessLicensePic 营业执照
     * @return
     */
    public String updateAnduploadCompanyInfo(UserCompany company, MultipartFile taxRegPic, MultipartFile businessLicensePic) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd

        if(FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL && FileUtil.getFileSizebak(taxRegPic) > Constants.AUTH_IMG_NULL){
            String uid = company.getUid();
            AuthenCompanyRecords user = authenService.findAuthingInfoByUid(uid);
            String a = user.getBusinessLicensePic();
            company.setBusinessLicensePic(a);

            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSizebak(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                return Constants.RESULT_STATUS_ERROR;
            }
            String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
            company.setTaxRegPic(dataPath + "/" + tname);


            authenService.saveAuthToRecords(company);
            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }
        else  if(FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL && taxRegPic == null){
            String uid = company.getUid();
            AuthenCompanyRecords user = authenService.findAuthingInfoByUid(uid);
            String a = user.getBusinessLicensePic();
            company.setBusinessLicensePic(a);

            authenService.saveAuthToRecords(company);
            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }
        else if(FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_NULL && taxRegPic == null){

            if (FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return Constants.RESULT_STATUS_ERROR;
            }
            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            company.setBusinessLicensePic(dataPath + "/" + bname);

            authenService.saveAuthToRecords(company);
            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }
        else if(FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_NULL &&FileUtil.getFileSizebak(taxRegPic) == Constants.AUTH_IMG_NULL){
            String uid = company.getUid();
            AuthenCompanyRecords user = authenService.findAuthingInfoByUid(uid);
            String a = user.getTaxRegPic();
            company.setTaxRegPic(a);

            if (FileUtil.getFileSize(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                return Constants.RESULT_STATUS_ERROR;
            }
            String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
            FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
            company.setBusinessLicensePic(dataPath + "/" + bname);

            authenService.saveAuthToRecords(company);
            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }
        else if(FileUtil.getFileSizebak(businessLicensePic) == Constants.AUTH_IMG_NULL ||FileUtil.getFileSizebak(taxRegPic) == Constants.AUTH_IMG_NULL){
            String uid = company.getUid();
            AuthenCompanyRecords user = authenService.findAuthingInfoByUid(uid);
            String a = user.getBusinessLicensePic();
            String b = user.getTaxRegPic();
            company.setBusinessLicensePic(a);
            company.setTaxRegPic(b);
            authenService.saveAuthToRecords(company);
            Account account = new Account();
            account.setUid(company.getUid());
            account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
            accountService.updateAuthStatus(account); // 更改状态审核中

            return Constants.RESULT_STATUS_OK;
        }
        else {
            if (!authenService.checkCompanyUniqueForAll(company)) { // 个别信息已经存在
                return Constants.RESULT_STATUS_HAS_REG;
            }

            boolean isUploadT = true;
            if (taxRegPic != null) {
                // 文件太大，或文件后缀不正确
                if (FileUtil.getFileSizebak(taxRegPic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(taxRegPic, Constants.AUTH_IMG_REG)) {
                    return Constants.RESULT_STATUS_ERROR;
                }

                String tname = "taxreg" + ID.randomUUID() + "." + Tools.getSuffix(taxRegPic.getOriginalFilename());
                isUploadT = FileUtil.uploadToDefaultPath(taxRegPic, tname, dataPath);
                company.setTaxRegPic(dataPath + "/" + tname);
            }

            boolean isUploadB = true;
            if (businessLicensePic != null) {
                // 文件太大，或文件后缀不正确
                if (FileUtil.getFileSizebak(businessLicensePic) > Constants.AUTH_IMG_SIZE || !FileUtil.checkExtensionName(businessLicensePic, Constants.AUTH_IMG_REG)) {
                    return Constants.RESULT_STATUS_ERROR;
                }

                String bname = "business" + ID.randomUUID() + "." + Tools.getSuffix(businessLicensePic.getOriginalFilename());
                isUploadB = FileUtil.uploadToDefaultPath(businessLicensePic, bname, dataPath);
                company.setBusinessLicensePic(dataPath + "/" + bname);
            }


            if (isUploadT && isUploadB) { // 都上传成功

                authenService.saveAuthToRecords(company);
                Account account = new Account();
                account.setUid(company.getUid());
                account.setAuthStatus(Constants.COMPANY_AUTH_STATUS_AUDIT);
                accountService.updateAuthStatus(account); // 更改状态审核中

                return Constants.RESULT_STATUS_OK;
            }

            return Constants.RESULT_STATUS_ERROR;
        }

    }


    /**
     * 上传铃声文件信息
     * @param  voice 铃声信息
     * @param voiceUrl 铃声路径
     * @return
     */
    public String saveVoiceUrl(AppVoice voice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_UP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( !FileUtil.isWavFile(voiceUrl)) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadToVoicePath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voice.setVoiceUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                voice.setVoiceSize(size + "");
                voiceService.saveVoice(voice);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICE_NULL_ERROR;
    }


    /**
     * 修改铃声
     * @param  voice 铃声信息
     * @param voiceUrl 铃声路径
     * @return
     */
    public String updateVoiceUrl(AppVoice voice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;

        if (voiceUrl != null  && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_UP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( !FileUtil.isWavFile(voiceUrl)) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadToVoicePath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voice.setVoiceUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if(size.equals("0.00"))
                {size = "0.01";}
                voice.setVoiceSize(size + "");
                voice.setCommon("");
                voice.setUpdateDate(null);
                voiceService.updateVoice(voice);
                return Constants.RESULT_STATUS_OK;
            }
        }else if(voice.getVoiceName() != null && voiceUrl != null && voiceUrl.getSize() == 0){
            Map<String, String> map =  new HashMap<>();
            map.put("appid", voice.getAppid());
            map.put("voiceName", voice.getVoiceName());
            map.put("status", voice.getStatus());
            voiceService.updateVoiceName(map);
            return Constants.RESULT_STATUS_OK;
        }
        return Constants.VOICE_NULL_ERROR;
    }



    /**
     *   隐私号。上传铃声文件信息
     * @param  voice 铃声信息
     * @param voiceUrl 铃声路径
     * @return
     */
    public String saveSecretVoiceUrl(SecretVoice voice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_UP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( !FileUtil.isWavFile(voiceUrl)) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadToVoicePath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voice.setVoiceUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                voice.setVoiceSize(size + "");
                secretService.saveVoice(voice);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICE_NULL_ERROR;
    }


    /**
     *  隐私号。修改铃声
     * @param  voice 铃声信息
     * @param voiceUrl 铃声路径
     * @return
     */
    public String updateSecretVoiceUrl(SecretVoice voice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;

        if (voiceUrl != null  && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_UP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( !FileUtil.isWavFile(voiceUrl)) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadToVoicePath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voice.setVoiceUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size))
                {size = "0.01";}
                voice.setVoiceSize(size + "");
                voice.setCommon("");
                voice.setUpdateDate(null);
                secretService.updateVoice(voice);
                return Constants.RESULT_STATUS_OK;
            }
        }
        else if(voice.getVoiceName() != null && voiceUrl != null && voiceUrl.getSize() == 0){
            Map<String, String> map =  new HashMap<>();
            map.put("appid", voice.getAppid());
            map.put("voiceName", voice.getVoiceName());
            map.put("status", voice.getStatus());
            secretService.updateVoiceName(map);
            return Constants.RESULT_STATUS_OK;
        }
        return Constants.VOICE_NULL_ERROR;
    }


    /**
     * 上传语音模板文件
     * @param tempVoice  语音模板
     * @param voiceUrl 语音模板路径
     * @return
     */
    public String saveMouldUrl(TempVoice tempVoice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_TEMP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( (!FileUtil.isWavFile(voiceUrl))&&(!FileUtil.isMP3File(voiceUrl))) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadTempVoiceToPath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                tempVoice.setvUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                tempVoice.setvSize(size + "");
                mouldVoiceService.saveTempVoice(tempVoice);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICE_NULL_ERROR;
    }



    /**
     * 上传语音模板文件(重新提交)
     * @param tempVoice  语音模板
     * @param voiceUrl 语音模板路径
     * @return
     */
    public String updateMouldUrl(TempVoice tempVoice, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_TEMP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( (!FileUtil.isWavFile(voiceUrl))&&(!FileUtil.isMP3File(voiceUrl))) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadTempVoiceToPath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                tempVoice.setvUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                tempVoice.setvSize(size + "");
                mouldVoiceService.updateTempVoice(tempVoice);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICE_NULL_ERROR;
    }

    /**
     * 上传语音验证码模板文件(重新提交)
     * @param voiceverifyTemp  语音模板
     * @param voiceUrl 语音模板路径
     * @return
     */
    public String updateVoiceverifyUrl(VoiceverifyTemp voiceverifyTemp, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICE_TEMP_SIZE ) {
                return Constants.VOICE_SIZE_ERROR;
            }
            else if ( (!FileUtil.isWavFile(voiceUrl))&&(!FileUtil.isMP3File(voiceUrl))) {
                return Constants.VOICE_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadVoiceverifyTempToPath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voiceverifyTemp.setvUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                voiceverifyTemp.setvSize(size + "");
                voiceverifyTempService.updateByPrimaryKeySelective(voiceverifyTemp);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICE_NULL_ERROR;
    }


    /**
     * 上传语音验证码语音模板文件
     * @param voiceverifyTemp  语音模板
     * @param voiceUrl 语音模板路径
     * @return
     */
    public String saveVoiceverifyTempUrl(VoiceverifyTemp voiceverifyTemp, MultipartFile voiceUrl) {
        String dataPath = Tools.composeDataPath(); // 日期地址 yyyyMMdd
        boolean isUploadT = true;
        if (voiceUrl != null && voiceUrl.getSize() != 0) {
            // 文件太大，或文件后缀不正确
            if (FileUtil.getFileSize(voiceUrl) > Constants.VOICEVERIFY_TEMP_SIZE ) {
                return Constants.VOICEVERIFY_SIZE_ERROR;
            }
            else if ( (!FileUtil.isWavFile(voiceUrl))&&(!FileUtil.isMP3File(voiceUrl))) {
                return Constants.VOICEVERIFY_ERROR;
            }
            String tname = "voice" + ID.randomUUID() + "." + Tools.getSuffix(voiceUrl.getOriginalFilename());
            isUploadT = FileUtil.uploadVoiceverifyTempToPath(voiceUrl, tname, dataPath);
            if (isUploadT ) {
                voiceverifyTemp.setvUrl(dataPath + "/" + tname);
                Long fileUtil = FileUtil.getFileSize(voiceUrl);
                double temp= Double.parseDouble(fileUtil.toString())/1024.00/1024.00;
                String size = String.format("%.2f", temp);
                if("0.00".equals(size)){
                    size = "0.01";
                }
                voiceverifyTemp.setvSize(size + "");
                voiceverifyTempService.saveVoiceverifyTemp(voiceverifyTemp);
                return Constants.RESULT_STATUS_OK;
            }
        }
        return Constants.VOICEVERIFY_NULL_ERROR;
    }


}
