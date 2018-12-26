package com.e9cloud.pcweb.sipPhone.biz;

import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.RegexUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.FixPhone;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.PubNumResPoolService;
import com.e9cloud.mybatis.service.SPApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/5.
 */
@Service
public class SPNumImportService {

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private SPApplyService spApplyService;

    @Autowired
    private SPAllotService spAllotService;

    @Autowired
    private PubNumResPoolService pubNumResPoolService;

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<SpApplyNum> saveExcel(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{

        List<SpApplyNum> sipNumList = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();

        // 对读取Excel表格内容检查
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])
                    || Tools.isNotNullStr(arrayStr[2]) || Tools.isNotNullStr(arrayStr[3])
                    || Tools.isNotNullStr(arrayStr[4])|| Tools.isNotNullStr(arrayStr[5])) { // 跳过空行

                //设置SIP选号信息
                SpApplyNum applyNum = new SpApplyNum();
                applyNum.setAppid(request.getParameter("appid"));

                applyNum.setAreaCode(arrayStr[0]);
                applyNum.setSipphone(arrayStr[1]);
                applyNum.setFixphone(arrayStr[2]);
                applyNum.setShowNum(arrayStr[3]);
                if(arrayStr.length>5){
                    applyNum.setCallSwitchFlag(arrayStr[4]);
                    applyNum.setLongDistanceFlag(arrayStr[5]);
                }
                if(arrayStr.length>4){
                    applyNum.setCallSwitchFlag(arrayStr[4]);
                }

                sipNumList.add(applyNum);
            }
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<SpApplyNum> insertSpShowNumErrorList = new ArrayList<>();

        for (SpApplyNum sipNumResult : sipNumList) {

            // 校验区号
            if (Tools.isNullStr(sipNumResult.getAreaCode())) {
                sipNumResult.setAuditCommon("城市区号不能为空");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(sipNumResult.getAreaCode());
            if (telnoCity == null) {
                sipNumResult.setAuditCommon("城市区号不存在");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 设置城市主键
            sipNumResult.setCityid(telnoCity.getCcode());

            // 校验sip号码
            if (Tools.isNullStr(sipNumResult.getSipphone())) {
                sipNumResult.setAuditCommon("sip号码不能为空");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验sip号码
            JSonMessage jSonMessage = spAllotService.checkSipphone(sipNumResult.getSipphone(), telnoCity.getCcode());
            if (!R.OK.equals(jSonMessage.getCode())) {
                sipNumResult.setAuditCommon(jSonMessage.getMsg());
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 设置 sipphoneid
            sipNumResult.setSipphoneId(String.valueOf(jSonMessage.getData()));


            // 固话和外显号码不能同时为空
            if (Tools.isNullStr(sipNumResult.getFixphone()) && Tools.isNullStr(sipNumResult.getShowNum())) {
                sipNumResult.setAuditCommon("固话号码和外显号码不能同时为空");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验固话号码
            if (Tools.isNotNullStr(sipNumResult.getFixphone())) {
                JSonMessage js = spAllotService.checkFixphone(sipNumResult.getFixphone(), telnoCity.getCcode(), sipNumResult.getAppid());
                if (!R.OK.equals(js.getCode())) {
                    sipNumResult.setAuditCommon(js.getMsg());
                    insertSpShowNumErrorList.add(sipNumResult);
                    continue;
                }
                // 设置 fixphoneid
                sipNumResult.setFixphoneId(String.valueOf(js.getData()));
            }

            // 校验外显号
            isNum = pattern.matcher(sipNumResult.getShowNum());
            if (Tools.isNotNullStr(sipNumResult.getShowNum()) && !isNum.matches()) {
                sipNumResult.setAuditCommon("外显号格式不正确");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 外显号码为空时，将其设置为 固话号码
            if (Tools.isNullStr(sipNumResult.getShowNum())) {
                sipNumResult.setShowNum(sipNumResult.getFixphone());
            }

            // 校验号码状态
            if (Tools.isNullStr(sipNumResult.getCallSwitchFlag())) {
                sipNumResult.setAuditCommon("号码状态不能为空");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验长途权限
            if (Tools.isNullStr(sipNumResult.getLongDistanceFlag())) {
                sipNumResult.setAuditCommon("长途权限不能为空");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验号码状态
            if("0".equals(sipNumResult.getCallSwitchFlag())){
                sipNumResult.setCallSwitchFlag("01");
            }else if("1".equals(sipNumResult.getCallSwitchFlag())){
                sipNumResult.setCallSwitchFlag("00");
            }else{
                sipNumResult.setAuditCommon("号码状态不正确");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            // 校验长途权限
            if("0".equals(sipNumResult.getLongDistanceFlag())){
                sipNumResult.setLongDistanceFlag("01");
            }else if("1".equals(sipNumResult.getLongDistanceFlag())){
                sipNumResult.setLongDistanceFlag("00");
            }else{
                sipNumResult.setAuditCommon("长途权限不正确");
                insertSpShowNumErrorList.add(sipNumResult);
                continue;
            }

            spApplyService.addShowNum(sipNumResult);
        }

        request.getSession().setAttribute("insertSpShowNumErrorList", insertSpShowNumErrorList);

        return insertSpShowNumErrorList;
    }

    /**
     * 处理excel中的数据信息 导入固话号码
     * @param FixPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<FixPhone> saveFixPhoneExcel(MultipartFile FixPhoneFile, HttpServletRequest request) throws Exception{
        List<FixPhone> fixPhoneList = new ArrayList<>();
        InputStream is = FixPhoneFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                //FixPhone
                FixPhone fixPhone = new FixPhone();
                fixPhone.setAreaCode(arrayStr[0]);
                fixPhone.setNumber(arrayStr[1]);

                fixPhoneList.add(fixPhone);
            }
        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<FixPhone> insertFixPhoneErrorList = new ArrayList<>();
        for (FixPhone fp : fixPhoneList) {

            // 校验区号
            if (Tools.isNullStr(fp.getAreaCode())) {
                fp.setImportCommon("城市区号不能为空");
                insertFixPhoneErrorList.add(fp);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(fp.getAreaCode());
            if (telnoCity == null) {
                fp.setImportCommon("城市区号不存在");
                insertFixPhoneErrorList.add(fp);
                continue;
            }

            // 设置城市ccode
            fp.setCityid(telnoCity.getCcode());


            isNum = pattern.matcher(fp.getNumber());
            //判断号码是否合法
            if (!isNum.matches()) {
                fp.setImportCommon("固话号码格式不正确");
                insertFixPhoneErrorList.add(fp);
                continue;
            }

            if (fp.getNumber().indexOf(fp.getAreaCode()) != 0) {
                fp.setImportCommon("固话号码区号不正确");
                insertFixPhoneErrorList.add(fp);
                continue;
            }

            // 判断号码是否存在
            List<FixPhone> fixPhone = pubNumResPoolService.getPubNumResPoolByNum(fp.getNumber());
            if(fixPhone.size() > 0){
                fp.setImportCommon("固话号码已经存在");
                insertFixPhoneErrorList.add(fp);
                continue;
            }

            pubNumResPoolService.addFixPhone(fp);

        }
        request.getSession().setAttribute("insertFixPhoneErrorList",insertFixPhoneErrorList);

        return insertFixPhoneErrorList;
    }

    /**
     * 处理excel中的数据信息
     * @param SipPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<SipPhone> saveSipPhoneExcel(MultipartFile SipPhoneFile, HttpServletRequest request) throws Exception{
        List<SipPhone> sipPhoneList = new ArrayList<>();
        InputStream is = SipPhoneFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1]) || Tools.isNotNullStr(arrayStr[2])
                    || Tools.isNotNullStr(arrayStr[3]) || Tools.isNotNullStr(arrayStr[4])) {
                //设置sipphone信息
                SipPhone sipPhone = new SipPhone();
                sipPhone.setAreaCode(arrayStr[0]);
                sipPhone.setSipPhone(arrayStr[1]);
                sipPhone.setPwd(arrayStr[2]);
                sipPhone.setSipRealm(arrayStr[3]);
                sipPhone.setIpPort(arrayStr[4]);

                sipPhoneList.add(sipPhone);
            }

        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<SipPhone> insertSipPhoneErrorList = new ArrayList<>();
        for (SipPhone sp : sipPhoneList) {

            // 校验区号
            if (Tools.isNullStr(sp.getAreaCode())) {
                sp.setImportCommon("城市区号不能为空");
                insertSipPhoneErrorList.add(sp);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(sp.getAreaCode());
            if (telnoCity == null) {
                sp.setImportCommon("城市区号不存在");
                insertSipPhoneErrorList.add(sp);
                continue;
            }

            // 设置城市主键
            sp.setCityid(telnoCity.getCcode());

            // 校验SIP号码
            isNum = pattern.matcher(sp.getSipPhone());
            //判断号码是否合法
            if (!isNum.matches()) {
                sp.setImportCommon("SIP号码格式不正确");
                insertSipPhoneErrorList.add(sp);
                continue;
            }

            if (sp.getSipPhone().indexOf(sp.getAreaCode()) != 0) {
                sp.setImportCommon("SIP号码区号不正确");
                insertSipPhoneErrorList.add(sp);
                continue;
            }

            List<SipPhone> sipPhone = pubNumResPoolService.getPubNumResPoolBySipPhone(sp.getSipPhone());
            //数据库有此sip号码
            if(sipPhone.size() > 0){
                sp.setImportCommon("SIP号码已存在");
                insertSipPhoneErrorList.add(sp);
                continue;
            }

            //检查sip号码是否需要注册（true为需要）
            boolean flag = pubNumResPoolService.checkRegisterSipphone(sp.getSipPhone());
            if (flag == true){
                // 校验认证密码
                if (!RegexUtils.chechAnyChar(sp.getPwd(), 1, 20)) {
                    sp.setImportCommon("认证密码（pwd）格式不正确");
                    insertSipPhoneErrorList.add(sp);
                    continue;
                }

                // 校验SIP Realm
                if (!RegexUtils.chechAnyChar(sp.getSipRealm(), 1, 20)) {
                    sp.setImportCommon("SIP Realm格式不正确");
                    insertSipPhoneErrorList.add(sp);
                    continue;
                }

                // 校验ipport
                if (!RegexUtils.checkIpAndPort(sp.getIpPort())) {
                    sp.setImportCommon("SIP 服务器（IP：PORT）格式不正确");
                    insertSipPhoneErrorList.add(sp);
                    continue;
                }
            }else {
                sp.setPwd("");
                sp.setSipRealm("");
                sp.setIpPort("");
            }

            //数据库无此sip号码,直接入库
            pubNumResPoolService.addSipPhone(sp);
        }

        request.getSession().setAttribute("insertSipPhoneErrorList", insertSipPhoneErrorList);

        return insertSipPhoneErrorList;
    }

}
