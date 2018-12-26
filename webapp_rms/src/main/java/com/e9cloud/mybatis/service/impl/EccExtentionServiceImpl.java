package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ecc 分机号
 * Created by Administrator on 2017/2/13.
 */
@Service
public class EccExtentionServiceImpl extends BaseServiceImpl implements EccExtentionService {

    @Autowired
    private EccShowNumPoolService eccShowNumPoolService;

    @Autowired
    private EccAppInfoService eccAppInfoService;

    @Autowired
    private SipNumResPoolService sipNumResPoolService;

    @Autowired
    private EccFixPhonePoolService eccFixPhonePoolService;

    @Autowired
    private TelnoInfoService telnoInfoService;

    @Autowired
    private CbTaskService cbTaskService;



    /**
     * 根据appid分页查询分机号
     * @param page 分页信息
     * @return 分页信息
     */
    @Override
    public PageWrapper pageExtention(Page page) {
        return this.page(Mapper.EccExtention_Mapper.pageExtention, page);
    }

    /**
     * 开启禁用长途、开启禁用号码
     *
     * @param eccExtention
     */
    @Override
    public void updateSubNumStatus(EccExtention eccExtention) {
        this.update(Mapper.EccExtention_Mapper.updateSubNumStatus, eccExtention);
    }

    @Override
    public EccExtention getExtentionByPK(String id) {
        return this.findObjectByPara(Mapper.EccExtention_Mapper.selectExtentionByPK, id);
    }

    /**
     * 批量删除分机号
     *
     * @param ids
     */
    @Override
    public void deleteSubNums(String[] ids) {
        if (ids != null && ids.length > 0) {
            EccExtention extention;
            for (String id : ids) {
                extention = this.getExtentionByPK(id);
                if (extention != null) {
                    if (Tools.isNotNullStr(extention.getSipphoneId())) {
                        EccSipphone sipphone = new EccSipphone();
                        sipphone.setStatus("03");
                        sipphone.setId(extention.getSipphoneId());
                        sipNumResPoolService.updateEccSipphone(sipphone);
                    }

                    if (Tools.isNotNullStr(extention.getFixphoneId())) {
                        eccFixPhonePoolService.deleteByPK(extention.getFixphoneId());
                    }

                    if (Tools.isNotNullStr(extention.getShownumId())) {
                        EccShownum shownum = new EccShownum();
                        shownum.setStatus("03");
                        shownum.setAppid("");
                        shownum.setEccid("");
                        shownum.setId(extention.getShownumId());
                        eccShowNumPoolService.updateEccShowNum(shownum);
                    }
                }
                this.delete(Mapper.EccExtention_Mapper.deleteByPrimaryKey, id);
            }
        }
    }

    /**
     * 获取下载列表
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadExtention(Page page) {
        return this.download(Mapper.EccExtention_Mapper.pageExtention, page);
    }

    /**
     * 添加分机号
     *
     * @param eccExtention
     */
    @Override
    public void saveSubNum(EccExtention eccExtention) {
        String eccid = ID.randomUUID();
        eccExtention.setId(eccid);
        if (!Tools.eqStr(eccExtention.getNumType(), "01")){ // 插入固话或手机号码并标记为已分配
            EccFixphone eccFixphone = new EccFixphone();

            String fixid = ID.randomUUID();
            eccFixphone.setId(fixid);
            eccFixphone.setEccid(eccid);
            eccFixphone.setAppid(eccExtention.getAppid());
            eccFixphone.setNumType(eccExtention.getNumType());
            eccFixphone.setCityid(eccExtention.getCityid());
            eccFixphone.setNumber(eccExtention.getPhone());
            eccFixphone.setStatus("01");

            eccFixPhonePoolService.saveFixPhone(eccFixphone);

            eccExtention.setFixphoneId(fixid);
        } else { // 插入sip号码标为已分配
            EccSipphone sipphone = new EccSipphone();
            sipphone.setStatus("01");
            sipphone.setId(eccExtention.getSipphoneId());
            sipNumResPoolService.updateEccSipphone(sipphone);
        }

        if (Tools.isNotNullStr(eccExtention.getShownumId())) { // 修改外显号码状态
            EccShownum shownum = new EccShownum();
            shownum.setStatus("01");
            shownum.setAppid(eccExtention.getAppid());
            shownum.setEccid(eccExtention.getEccid());
            shownum.setId(eccExtention.getShownumId());
            eccShowNumPoolService.updateEccShowNum(shownum);
        }


        this.save(Mapper.EccExtention_Mapper.insertSelective, eccExtention);



    }

    /**
     * 编辑分机号
     * @param newExt
     */
    @Override
    public void editSubNum(EccExtention newExt) {
        EccExtention oldExt = this.getExtentionByPK(newExt.getId());

        // 号码类型 相同时
        if (Tools.eqStr(newExt.getNumType(), oldExt.getNumType())) {
            // sip号码
            if (Tools.eqStr("01", newExt.getNumType())) {
                // 解绑旧的sipphone，绑定新的sipphone
                if (!Tools.eqStr(newExt.getSipphoneId(), oldExt.getSipphoneId())) {
                    EccSipphone sipphone = new EccSipphone();
                    sipphone.setStatus("03");
                    sipphone.setId(oldExt.getSipphoneId());
                    sipNumResPoolService.updateEccSipphone(sipphone);

                    //EccSipphone sipphone = new EccSipphone();
                    sipphone.setStatus("01");
                    sipphone.setId(newExt.getSipphoneId());
                    sipNumResPoolService.updateEccSipphone(sipphone);
                }
            // 非sip号码
            } else {
                if (!Tools.eqStr(newExt.getPhone(), oldExt.getPhone())) {
                    if (Tools.isNotNullStr(oldExt.getFixphoneId())) {
                        eccFixPhonePoolService.deleteByPK(oldExt.getFixphoneId());
                    }

                    if (Tools.isNotNullStr(newExt.getPhone())) {
                        EccFixphone eccFixphone = new EccFixphone();

                        String fixid = ID.randomUUID();
                        eccFixphone.setId(fixid);
                        eccFixphone.setEccid(newExt.getEccid());
                        eccFixphone.setAppid(newExt.getAppid());
                        eccFixphone.setNumType(newExt.getNumType());
                        eccFixphone.setCityid(newExt.getCityid());
                        eccFixphone.setNumber(newExt.getPhone());
                        eccFixphone.setStatus("01");

                        eccFixPhonePoolService.saveFixPhone(eccFixphone);

                        newExt.setFixphoneId(fixid);
                    }
                }
            }
        // 号码类型 不相同时
        } else {
            // 手机和固话转换时
            if (!Tools.eqStr(newExt.getPhone(), oldExt.getPhone()) && !Tools.eqStr("01", oldExt.getNumType()) && !Tools.eqStr("01", newExt.getNumType())) {
                if (Tools.isNotNullStr(oldExt.getFixphoneId())) {
                    eccFixPhonePoolService.deleteByPK(oldExt.getFixphoneId());
                }

                if (Tools.isNotNullStr(newExt.getPhone())) {
                    EccFixphone eccFixphone = new EccFixphone();

                    String fixid = ID.randomUUID();
                    eccFixphone.setId(fixid);
                    eccFixphone.setEccid(newExt.getEccid());
                    eccFixphone.setAppid(newExt.getAppid());
                    eccFixphone.setNumType(newExt.getNumType());
                    eccFixphone.setCityid(newExt.getCityid());
                    eccFixphone.setNumber(newExt.getPhone());
                    eccFixphone.setStatus("01");

                    eccFixPhonePoolService.saveFixPhone(eccFixphone);

                    newExt.setFixphoneId(fixid);
                }
            // sip 专为 非 sip号码
            } else if (Tools.eqStr("01", oldExt.getNumType()) && !Tools.eqStr("01", newExt.getNumType())) {
                EccSipphone sipphone = new EccSipphone();
                sipphone.setStatus("03");
                sipphone.setId(oldExt.getSipphoneId());
                sipNumResPoolService.updateEccSipphone(sipphone);

                if (Tools.isNotNullStr(newExt.getPhone())) {
                    EccFixphone eccFixphone = new EccFixphone();

                    String fixid = ID.randomUUID();
                    eccFixphone.setId(fixid);
                    eccFixphone.setEccid(newExt.getEccid());
                    eccFixphone.setAppid(newExt.getAppid());
                    eccFixphone.setNumType(newExt.getNumType());
                    eccFixphone.setCityid(newExt.getCityid());
                    eccFixphone.setNumber(newExt.getPhone());
                    eccFixphone.setStatus("01");

                    eccFixPhonePoolService.saveFixPhone(eccFixphone);

                    newExt.setFixphoneId(fixid);
                }

            // 非sip号码 转为 sip号码
            } else if (!Tools.eqStr("01", oldExt.getNumType()) && Tools.eqStr("01", newExt.getNumType())) {
                if (Tools.isNotNullStr(oldExt.getFixphoneId())) {
                    eccFixPhonePoolService.deleteByPK(oldExt.getFixphoneId());
                }

                EccSipphone sipphone = new EccSipphone();
                sipphone.setStatus("01");
                sipphone.setId(newExt.getSipphoneId());
                sipNumResPoolService.updateEccSipphone(sipphone);
            }
        }

        // 解绑旧的外显号，绑定新的外显号
        if (!Tools.eqStr(newExt.getShownumId(), oldExt.getShownumId())) {
            EccShownum shownum = new EccShownum();
            shownum.setStatus("03");
            shownum.setAppid("");
            shownum.setEccid("");
            shownum.setId(oldExt.getShownumId());
            eccShowNumPoolService.updateEccShowNum(shownum);

            //EccSipphone sipphone = new EccSipphone();
            shownum.setStatus("01");
            shownum.setAppid(newExt.getAppid());
            shownum.setEccid(newExt.getEccid());
            shownum.setId(newExt.getShownumId());
            eccShowNumPoolService.updateEccShowNum(shownum);
        }

        if (Tools.eqStr("01", newExt.getNumType())) {
            newExt.setFixphoneId("");
        } else {
            newExt.setSipphoneId("");
        }

        this.update(Mapper.EccExtention_Mapper.updateByPrimaryKeySelective, newExt);

    }

    /**
     * 核对外显号码
     *
     * @param appid   appid
     * @param eccid   eccid
     * @param showNum 外显号
     * @return
     */
    @Override
    public JSonMessage checkShowNum(String appid, String eccid, String showNum, String extid) {
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByPK(eccid);
        EccExtention extention = this.getExtentionByPK(extid);
        return checkShowNum(appid, eccInfo, showNum, extention == null ? "" : extention.getShownumId());
    }

    private JSonMessage checkShowNum(String appid, EccAppInfo eccInfo, String showNum, String shownumid) {
        JSonMessage jSonMessage = new JSonMessage();

        if (!RegexUtils.chechAnyNum(showNum, 5, 30)){
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("外显号码格式不正确");
        } else {
            List<EccShownum> shownums = eccShowNumPoolService.getEccShowNumByNumber(showNum);
            if (shownums == null || shownums.size() == 0) {
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("外显号码不存在");
            } else if (shownums.size() > 1){
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("存在多个外显号码");
            } else {
                EccShownum shownum = shownums.get(0);
                if (!Tools.eqStr("03", shownum.getStatus()) && !Tools.eqStr(shownum.getId(), shownumid)) {
                    jSonMessage.setCode(R.ERROR);
                    jSonMessage.setMsg("外显号码已被占用");
                } else if (!Tools.eqStr(shownum.getCityid(), eccInfo.getCityid())) {
                    jSonMessage.setCode(R.ERROR);
                    jSonMessage.setMsg("外显号码所在城市不匹配");
                } else {
                    jSonMessage.setCode(R.OK);
                    jSonMessage.setMsg(shownum.getId());
                }
            }
        }

        return jSonMessage;
    }

    /**
     * 核对分机号码
     *
     * @param eccid  eccid
     * @param subNum 分机号码
     * @return
     */
    @Override
    public JSonMessage checkSubNum(String eccid, String subNum, String extid) {
        Map<String, Object> params = new HashMap<>();
        params.put("eccid", eccid);
        params.put("subNum", subNum);
        params.put("extid", extid);

        long l = this.findObjectByMap(Mapper.EccExtention_Mapper.countSubNumByMap, params);

        return l > 0 ? new JSonMessage(R.ERROR, "分机号已经被占用") : new JSonMessage(R.OK, "");
    }

    /**
     * 核对接听号码
     *
     * @param eccid   eccid
     * @param numType 号码类型
     * @param phone   号码
     * @return
     */
    @Override
    public JSonMessage checkPhone(String eccid, String numType, String phone, String extid) {
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByPK(eccid);
        EccExtention extention = getExtentionByPK(extid);

        return Tools.eqStr(numType, "01") ? checkSipPhone(eccInfo, numType, phone, extention == null ? "" : (extention.getSipphoneId())) : checkFixPhone(eccInfo, numType, phone, extention == null ? "" : (extention.getFixphoneId()));
    }

    // 校验sipphone
    private JSonMessage checkSipPhone(EccAppInfo eccInfo, String numType, String phone, String sipphoneid) {
        JSonMessage jSonMessage = new JSonMessage();
        List<EccSipphone> sipphones = sipNumResPoolService.getSipNumResPoolBySipPhone(phone);
        if (sipphones == null || sipphones.size() == 0) {
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("SIP号码不存在");
        } else if (sipphones.size() > 1){
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("存在多个SIP号码");
        } else {
            EccSipphone sipphone = sipphones.get(0);
            if (!Tools.eqStr("03", sipphone.getStatus()) && !Tools.eqStr(sipphone.getId(), sipphoneid)) {
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("SIP号码已被占用");
            } else if (!Tools.eqStr(sipphone.getCityid(), eccInfo.getCityid())) {
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("SIP号码所在城市不匹配");
            } else {
                jSonMessage.setCode(R.OK);
                jSonMessage.setMsg(sipphone.getId());
            }
        }

        return jSonMessage;
    }

    // 校验Fixphone
    private JSonMessage checkFixPhone(EccAppInfo eccInfo, String numType, String phone, String extid) {
        String head = Tools.eqStr("03", numType) ? "固话" : "手机号";

        JSonMessage jSonMessage = new JSonMessage();
        List<EccFixphone> fixphones = eccFixPhonePoolService.getEccFixphoneByNumber(phone);

        if (fixphones.size() > 1) {
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("存在多个" +head);
        } else if (fixphones.size() == 1){
            EccFixphone fphone = fixphones.get(0);
            if (Tools.eqStr(fphone.getId(), extid)) {
                jSonMessage.setCode(R.OK);
                jSonMessage.setMsg(fphone.getId());
            } else {
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg(head + "已被占用");
            }
        } else if (fixphones.size() == 0){
            if (Tools.eqStr("03", numType) && !phone.startsWith(eccInfo.getAreaCode())) { // 固话
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg(head + "所在城市不匹配");
            } else if (Tools.eqStr("02", numType)) {
                if (!RegexUtils.checkMobile(phone)) {
                    jSonMessage.setCode(R.ERROR);
                    jSonMessage.setMsg(head + "格式不正确");
                } else {
                    String pre = phone.substring(0,7);
                    TelnoInfo telnoInfo = telnoInfoService.getTelnoInfoByTelno(pre);
                    if (telnoInfo == null || !Tools.eqStr(telnoInfo.getAreacode(), eccInfo.getAreaCode())) {
                        jSonMessage.setCode(R.ERROR);
                        jSonMessage.setMsg(head + "所在城市不匹配");
                    } else {
                        jSonMessage.setCode(R.OK);
                        jSonMessage.setMsg("");
                    }
                }
            } else {
                jSonMessage.setCode(R.OK);
                jSonMessage.setMsg("");
            }
        }

        return jSonMessage;
    }

    private String getNumType(String numType){
        if (Tools.isNotNullStr(numType)) {
            if (numType.contains("P") || numType.contains("p")){
                return "01";
            }
            if (numType.contains("手")){
                return "02";
            }
            if (numType.contains("固")){
                return "03";
            }
        }
        return "";
    }

    @Override
    public List<EccExtention> saveEccSubNumExcel(MultipartFile eccSubNumFile, HttpServletRequest request) throws IOException {
        String appid = request.getParameter("appid");
        String eccid = request.getParameter("eccid");
        EccAppInfo eccInfo = eccAppInfoService.getEccAppInfoByPK(eccid);

        List<EccExtention> ecclist = new ArrayList<>();
        InputStream is = eccSubNumFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();

        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1]) || Tools.isNotNullStr(arrayStr[2]) || Tools.isNotNullStr(arrayStr[3])
                    || Tools.isNotNullStr(arrayStr[4])|| Tools.isNotNullStr(arrayStr[5])) {
                //设置sipphone信息
                EccExtention extention = new EccExtention();
                extention.setNumType(getNumType(arrayStr[0]));
                extention.setNumTypeStr(arrayStr[0]);
                extention.setPhone(arrayStr[1]);
                extention.setShownum(arrayStr[2]);
                extention.setSubNum(arrayStr[3]);
                if(arrayStr.length>5){
                    extention.setCallSwitchFlag(arrayStr[4]);
                    extention.setLongDistanceFlag(arrayStr[5]);
                }
                if(arrayStr.length>4){
                    extention.setCallSwitchFlag(arrayStr[4]);
                }

                extention.setAppid(appid);
                extention.setEccid(eccid);
                extention.setCityid(eccInfo.getCityid());

                ecclist.add(extention);
            }
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<EccExtention> errorList = new ArrayList<>();
        for (EccExtention sp : ecclist) {

            // 接听号码类型
            if (Tools.isNullStr(sp.getNumType())) {
                sp.setImportCommon("接听号码类型无法识别");
                errorList.add(sp);
                continue;
            }

            // 校验接听号码
            if (Tools.isNullStr(sp.getPhone())) {
                sp.setImportCommon("接听号码不能为空");
                errorList.add(sp);
                continue;
            }

            // 校验接听号码
            if (!RegexUtils.chechAnyNum(sp.getPhone(), 5, 30)) {
                sp.setImportCommon("接听号码格式不正确");
                errorList.add(sp);
                continue;
            }

            if (Tools.eqStr("01", sp.getNumType())) { // 校验sip号码
                JSonMessage sipjs = checkSipPhone(eccInfo, sp.getNumType(), sp.getPhone(), null);
                if (Tools.eqStr(sipjs.getCode(), R.ERROR)) {
                    sp.setImportCommon(sipjs.getMsg());
                    errorList.add(sp);
                    continue;
                } else {
                    sp.setSipphoneId(sipjs.getMsg());
                }
            } else { // 固话，手机
                JSonMessage fixjs = checkFixPhone(eccInfo, sp.getNumType(), sp.getPhone(), null);
                if (Tools.eqStr(fixjs.getCode(), R.ERROR)) {
                    sp.setImportCommon(fixjs.getMsg());
                    errorList.add(sp);
                    continue;
                }
            }

            // 外显号码
            if (Tools.isNotNullStr(sp.getShownum())) {
                JSonMessage snjs = checkShowNum(appid, eccInfo, sp.getShownum(), null);
                if (Tools.eqStr(snjs.getCode(), R.ERROR)) {
                    sp.setImportCommon(snjs.getMsg());
                    errorList.add(sp);
                    continue;
                } else {
                    sp.setShownumId(snjs.getMsg());
                }
            }

            // 分机号码
            if (Tools.isNotNullStr(sp.getSubNum())) {
                JSonMessage sbjs = checkSubNum(eccid, sp.getSubNum(), null);
                if (Tools.eqStr(sbjs.getCode(), R.ERROR)) {
                    sp.setImportCommon(sbjs.getMsg());
                    errorList.add(sp);
                    continue;
                }
            }

            // 校验号码状态
            if (Tools.isNullStr(sp.getCallSwitchFlag())) {
                sp.setImportCommon("号码状态不能为空");
                errorList.add(sp);
                continue;
            }

            // 校验长途权限
            if (Tools.isNullStr(sp.getLongDistanceFlag())) {
                sp.setImportCommon("长途权限不能为空");
                errorList.add(sp);
                continue;
            }

            // 校验号码状态
            if("0".equals(sp.getCallSwitchFlag())){
                sp.setCallSwitchFlag("01");
            }else if("1".equals(sp.getCallSwitchFlag())){
                sp.setCallSwitchFlag("00");
            }else{
                sp.setImportCommon("号码状态不正确");
                errorList.add(sp);
                continue;
            }

            // 校验长途权限
            if("0".equals(sp.getLongDistanceFlag())){
                sp.setLongDistanceFlag("01");
            }else if("1".equals(sp.getLongDistanceFlag())){
                sp.setLongDistanceFlag("00");
            }else{
                sp.setImportCommon("长途权限不正确");
                errorList.add(sp);
                continue;
            }

            //数据库无此sip号码,直接入库
            saveSubNum(sp);
        }

        Map<String, Object> ivrJson = new LinkedHashMap<>();
        ivrJson.put("type", CbTask.TaskType.zj_ivr_xml.toString());
        ivrJson.put("appid", appid);
        List<Map<String, Object>> totals = this.getExtentionNumList(appid);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("digits", String.valueOf(total.get("subNum")));
                if("01".equals(String.valueOf(total.get("numType")))){
                    map.put("param", String.valueOf(total.get("sipphone")));
                }else {
                    map.put("param", String.valueOf(total.get("fixphone")));
                }
                data.add(map);
            }
        }
        ivrJson.put("data", data);
        CbTask task = new CbTask();
        task.setType(CbTask.TaskType.zj_ivr_xml);
        task.setParamJson(JSonUtils.toJSon(ivrJson));
        cbTaskService.saveCbTask(task);


        request.getSession().setAttribute("insertEccSubNumErrorList", errorList);

        return errorList;
    }

    @Override
    public long countExtentionByEccId(String eccid) {
        return this.findObjectByPara(Mapper.EccExtention_Mapper.countExtentionByEccId, eccid);
    }


    @Override
    public List<Map<String, Object>> getExtentionNumList(String appid) {
        return this.findObjectListByPara(Mapper.EccExtention_Mapper.getExtentionNumList,appid);
    }
}
