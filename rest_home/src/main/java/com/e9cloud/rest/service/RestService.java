package com.e9cloud.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.AppNumberRestService;
import com.e9cloud.mybatis.service.CardPhoneService;
import com.e9cloud.mybatis.service.ExtraServiceService;
import com.e9cloud.util.Constants;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.RandomUtil;
import com.e9cloud.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.rest.obt.CallBack;
import com.e9cloud.rest.obt.MaskCallBack;
import com.e9cloud.rest.obt.TelnoObj;
import com.e9cloud.telno.NumType;
import com.e9cloud.telno.Operators;
import com.e9cloud.telno.TelnoUtil;

@Service
public class RestService {
    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private AppNumberRestService appNumberRestService;
    @Autowired
    private CardPhoneService cardPhoneService;
    @Autowired
    private ExtraServiceService extraServiceService;
    @Autowired
    private AppInfoService appInfoService;

    /**
     * 获得显号号码，优先移动再顺序
     *
     * @param list
     * @return
     */
    public String getXh(List<String> list) {
        // 没有显号
        if (list == null || list.size() == 0) {
            return "";
        }

        // 移动优先
        List<String> results = new ArrayList<>();
        for (String phone : list) {
            String result = getOperatorAndAreacode(phone);
            if (result == null || result.length() == 0) {
                continue;
            }
            if (result.startsWith(Operators.UNICOM.getValue())) {
                return phone;
            }

            results.add(phone);
        }
        return results.size() == 0 ? "" : results.get(0);
    }

    /**
     * 获得显号运营商和电话号码的区号
     *
     * @param phone 号码
     * @return
     */
    public String getXhOperatorAndAreacode(String rnType, String phone) {
        // 号码为95则返回移动号码
        if (rnType.equals(NumType.NINE_FIVE.getValue())) {
            StringBuffer result = new StringBuffer(Operators.UNICOM.getValue());
            result.append(phone);
            return TelnoUtil.format(result);
        }
        // rn=3 显号0000000
        if (rnType.equals(NumType.PHONE_DEST.getValue())) {
            return "0000000";
        }
        // 获得号码前几位
        int bit = phone.length() < 7 ? phone.length() : 7;
        String phoneNew = phone;
        TelnoObj telnoObj = null;
        while (telnoObj == null && bit >= 3) {
            phone = phone.substring(0, bit);
            telnoObj = CacheManager.telnoInfoMap.get(phone);
            bit--;
        }

        if (telnoObj == null) {
            logger.info("没有号码对应的" + phoneNew + "号段");
            switch (TelnoUtil.getRnType(phoneNew, null)) {
                case PHONE:
                    return Operators.UNICOM.getValue() + "00000";
                case FIXED:
                    return Operators.TELECOM.getValue() + "00000";
                case FOUR_ZERO_ZERO:
                    return Operators.UNICOM.getValue() + "00000";
                case NINE_FIVE:
                    return Operators.UNICOM.getValue() + "00000";
                default:
                    break;
            }
            return "0000000";
        }
        // rn为1或2时显示省份
        if (rnType.equals("1") || rnType.equals("2")) {
            String pcode = CacheManager.areacodePcodeMap.get(telnoObj.getAreacode());
            StringBuffer result = new StringBuffer(telnoObj.getOperator());
            result.append(pcode == null ? "" : pcode);
            return TelnoUtil.format(result);
        }
        return telnoObj.toString();
    }

    /**
     * 获得目的地运营商和电话号码的区号
     *
     * @param phone 号码
     * @return
     */
    public String getDestOperatorAndAreacode(String rnType, String phone) {
        // 号码为95则返回移动号码
//		if (rnType.equals(NumType.NINE_FIVE.getValue())) {
//			StringBuffer result = new StringBuffer(Operators.UNICOM.getValue());
//			result.append(phone);
//			return TelnoUtil.format(result);
//		}

        // 获得号码前几位
        int bit = phone.length() < 7 ? phone.length() : 7;
        String phoneNew = phone;
        TelnoObj telnoObj = null;
        while (telnoObj == null && bit >= 3) {
            phone = phone.substring(0, bit);
            telnoObj = CacheManager.telnoInfoMap.get(phone);
            bit--;
        }

        if (telnoObj == null) {
            logger.info("没有号码对应的" + phoneNew + "号段");
            switch (TelnoUtil.getRnType(phoneNew, null)) {
                case PHONE:
                    return Operators.UNICOM.getValue() + "00000";
                case FIXED:
                    return Operators.TELECOM.getValue() + "00000";
                case FOUR_ZERO_ZERO:
                    return Operators.UNICOM.getValue() + "00000";
                case NINE_FIVE:
                    return Operators.UNICOM.getValue() + "00000";
                default:
                    break;
            }
            return "0000000";
        }
        // rn为1或2时显示省份
        if (rnType.equals("1") || rnType.equals("2") || rnType.equals("3")) {
            String pcode = CacheManager.areacodePcodeMap.get(telnoObj.getAreacode());
            StringBuffer result = new StringBuffer(telnoObj.getOperator());
            result.append(pcode == null ? "" : pcode);
            return TelnoUtil.format(result);
        }
        return telnoObj.toString();
    }

    /**
     * 获得运营商和电话号码的区号
     *
     * @param phone 号码
     * @return
     */
    public String getOperatorAndAreacode(String phone) {
        // 获得号码前几位
        int bit = phone.length() < 7 ? phone.length() : 7;
        String phoneNew = phone;
        TelnoObj telnoObj = null;
        while (telnoObj == null && bit >= 1) {
            phone = phone.substring(0, bit);
            telnoObj = CacheManager.telnoInfoMap.get(phone);
            bit--;
        }

        if (telnoObj == null) {
            logger.info("没有号码对应的" + phoneNew + "号段");
            switch (TelnoUtil.getRnType(phoneNew, null)) {
                case PHONE:
                    return Operators.UNICOM.getValue() + "00000";
                case FIXED:
                    return Operators.TELECOM.getValue() + "00000";
                case FOUR_ZERO_ZERO:
                    return Operators.UNICOM.getValue() + "00000";
                case NINE_FIVE:
                    return Operators.UNICOM.getValue() + "00000";
                default:
                    break;
            }
            return "0000000";
        }
        return telnoObj.toString();
    }

    /**
     * 添加rn码
     *
     * @param callBack
     */
    public void callBackRn(CallBack callBack) {
        //获取业务和app路由编码
        AppInfo appInfo = CacheManager.getAppInfo(callBack.getAppId());
        String routeCode = routeCode(appInfo, Constants.BusinessCode.REST);
        // 主叫显号运营商+区号
        String fromXh = getXh(callBack.getFromSerNum());
        String rna = getRn(fromXh, callBack.getFrom());
        callBack.setRn_a(routeCode + rna);
        // 被叫显号运营商+区号
        String toXh = getXh(callBack.getToSerNum());
        String rnb = getRn(toXh, callBack.getTo());
        callBack.setRn_b(routeCode + rnb);

    }



    /**
     * 计算业务码路由码
     *
     * @param appInfo
     * @param businessCode
     * @return
     */
    public String routeCode(AppInfo appInfo, Constants.BusinessCode businessCode) {
        String busCode = Utils.cutString(businessCode.getCode(), 2);
        if (appInfo == null || "00".equals(appInfo.getIsRoute())) {
            CodeType codeType = CacheManager.getCodeType(businessCode);
            if (codeType != null && "01".equals(codeType.getStatus())) {
                return "A" + busCode + "0000";
            } else {
                return "";
            }
        }
        return "A" + busCode + Utils.cutString(appInfo.getRouteCode(), 4);
    }

    /**
     * 根据主叫被叫获得rn
     *
     * @param from 主叫
     * @param to   被叫
     * @return rn
     */
    public String getRn(String from, String to) {
        String rn = appointLink(from, to);
        if (rn == null) {
            // 没有指定链路按规则找
            String rnType = TelnoUtil.getRnType(from, to).getValue();
            String xh = getXhOperatorAndAreacode(rnType, from);
            // 主叫运营商+区号
            String dest = getDestOperatorAndAreacode(rnType, to);
            rn = new StringBuffer(rnType).append(xh).append(dest).toString();
        }
        return rn;
    }

    /**
     * 隐私回拨添加rn码
     *
     * @param callBack
     */
    public void callBackRn(MaskCallBack callBack) {
        //获取业务和app路由编码
        AppInfo appInfo = CacheManager.getAppInfo(callBack.getAppId());
        String routeCode = routeCode(appInfo, Constants.BusinessCode.MASK);
        // 主叫显号运营商+区号
        String fromXh = callBack.getMaskNumber() == null ? "" : callBack.getMaskNumber();
        String rna = getRn(fromXh, callBack.getFrom());
        callBack.setRn_a(routeCode + rna);

        // 被叫显号运营商+区号
        String toXh = callBack.getMaskNumber() == null ? "" : callBack.getMaskNumber();
        String rnb = getRn(toXh, callBack.getTo());
        callBack.setRn_b(routeCode + rnb);
    }

    /**
     * 获得指定的rn
     *
     * @param xh
     * @param dest
     * @return
     */
    public String appointLink(String xh, String dest) {
        List<AppointLink> appointLinks = CacheManager.getAppointLinks();
        int size = appointLinks.size();
        for (int i = 0; i < size; i++) {
            AppointLink appointLink = appointLinks.get(i);
            if("*".equals(appointLink.getXhTelno())&& dest.startsWith(appointLink.getDestTelno())){
                return appointLink.getRn();
            }
            if (xh.startsWith(appointLink.getXhTelno()) && dest.startsWith(appointLink.getDestTelno())) {
                return appointLink.getRn();
            }
        }
        return null;
    }

    /**
     * 获取A B路录音标识
     *
     * @param sid
     * @return
     */
    public String getRecordFlag(String sid) {
        ////根据sid查询增值服务01-B路录音
        Map extraMap = new HashMap<>();
        extraMap.put("sid", sid);
        extraMap.put("extraType", "01");
        ExtraService extraService = extraServiceService.findExtraServiceByMap(extraMap);
        if (extraService != null) {
            //存在增值服务，返回B标识
            return "B";
        }
        return "A";
    }

    /**
     * 主被叫显号处理
     *
     * @param callBack
     * @return map
     */
    public Map callBackXh(CallBack callBack, List<String> numberBlacks) {
        Map map = new HashMap<>();
        String respDesc = "";// 响应描述
        String subrespCode = "";// 子响应码
        boolean flag = true;
        // 主被叫显号临时集合
        List<String> fromSerNumTemp = new ArrayList<String>();
        List<String> toSerNumTemp = new ArrayList<String>();
        // 进行主被叫显号操作（主叫或被叫显号有一个不为空时）
        if (Utils.notEmpty(callBack.getFromSerNum()) || Utils.notEmpty(callBack.getToSerNum())) {
            List<String> appNumberList = appNumberRestService.findAppNumberRestListByAppid(callBack.getAppId());
            if (appNumberList == null || appNumberList.size() == 0) {
                // 主被叫显号未审核
                subrespCode = ConstantsEnum.REST_FROMORTOSERNUM_NOT_AUDIT.getCode();
                respDesc = ConstantsEnum.REST_FROMORTOSERNUM_NOT_AUDIT.getDesc();

                // 被叫显号处理
                if (Utils.notEmpty(callBack.getToSerNum())) {
                    for (String str : callBack.getToSerNum()) {
                        if (callBack.getFrom().equals(str) && Utils.phoneValid(str)) {
                            // 增加一个被叫显号规则，即被叫可以显示主叫号码（必须手机号），
                            // 无需审核，直接可以显示
                            toSerNumTemp.add(str);
                            // 重新填充主被叫显号
                            callBack.setToSerNum(toSerNumTemp);
                            logger.info("被叫可以显示主叫号码（必须手机号）");
                            break;
                        } else if (!callBack.getTo().equals(str) && Utils.phoneValid(str)) {
                            // 增加一个被叫显号规则，即sid存在增值服务03-B路手机显号免审时，无需审核，直接可以显示
                            ////根据sid查询增值服务
                            Map extraMap = new HashMap<>();
                            extraMap.put("sid", callBack.getAccountSid());
                            extraMap.put("extraType", "03");
                            ExtraService extraService = extraServiceService.findExtraServiceByMap(extraMap);
                            if (extraService != null) {
                                // 无需审核，直接可以显示
                                toSerNumTemp.add(str);
                                // 重新填充主被叫显号
                                callBack.setToSerNum(toSerNumTemp);
                                logger.info("sid存在增值服务03-B路手机显号免审");
                                break;
                            }
                        }
                    }
                }
            } else {
                // 主被叫显号为号码本身标识
                boolean flagFromSerNum = false;
                boolean flagToSerNum = false;
                // 主被叫显号为黑名单号码标识
                boolean flagFromSerNum_black = false;
                boolean flagToSerNum_black = false;
                // 主叫显号处理
                if (Utils.notEmpty(callBack.getFromSerNum())) {
                    for (String str : callBack.getFromSerNum()) {
                        if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(str)) {
                            // 被叫显号黑名单限制，则不传递显号，根据线路自动显号
                            logger.info("主叫显号黑名单号码限制");
                            flagFromSerNum_black = true;
                            continue;
                        } else if (appNumberList.contains(str)) {
                            if (str.equals(callBack.getFrom())) {
                                logger.info("主叫显号不能为主叫号码本身");
                                flagFromSerNum = true;
                                continue;
                            } else {
                                fromSerNumTemp.add(str);
                                logger.info("取第一个有效的主叫显号" + str);
                                break;
                            }
                        }
                    }
                }
                // 被叫显号处理
                if (Utils.notEmpty(callBack.getToSerNum())) {
                    for (String str : callBack.getToSerNum()) {
                        if (Utils.notEmpty(numberBlacks) && numberBlacks.contains(str)) {
                            // 被叫显号黑名单限制，则不传递显号，根据线路自动显号
                            logger.info("被叫显号黑名单号码限制");
                            flagToSerNum_black = true;
                            continue;
                        } else if (callBack.getFrom().equals(str) && Utils.phoneValid(str)) {
                            // 增加一个被叫显号规则，即被叫可以显示主叫号码（必须手机号），
                            // 无需审核，直接可以显示
                            toSerNumTemp.add(str);
                            logger.info("被叫可以显示主叫号码（必须手机号）");
                            break;
                        } else if (Utils.cardValid(callBack.getFrom())) {
                            // 主叫为物联网卡时，被叫显号规则：必须绑定的号码
                            //获取物联网卡绑定的号码列表集合
                            Map<String, String> mapTmp = new HashMap<String, String>();
                            mapTmp.put("card", callBack.getFrom());
                            mapTmp.put("appId", callBack.getAppId());
                            List<String> cardPhones = cardPhoneService.findPhoneListBycard(mapTmp);
                            if (Utils.notEmpty(cardPhones)) {
                                if (cardPhones.contains(str)) {
                                    toSerNumTemp.add(str);
                                    logger.info("主叫为物联网卡时，被叫可以显示绑定的号码");
                                    break;
                                }
                            }
                        } else if (appNumberList.contains(str)) {
                            if (str.equals(callBack.getTo())) {
                                // 被叫显号不能为被叫号码本身
                                logger.info("被叫显号不能为被叫号码本身");
                                flagToSerNum = true;
                                continue;
                            } else {
                                // 取第一个有效的被叫显号
                                toSerNumTemp.add(str);
                                logger.info("取第一个有效的被叫显号" + str);
                                break;
                            }
                        } else if (!callBack.getTo().equals(str) && Utils.phoneValid(str)) {
                            // 增加一个被叫显号规则，即sid存在增值服务03-B路手机显号免审时，无需审核，直接可以显示
                            ////根据sid查询增值服务
                            Map extraMap = new HashMap<>();
                            extraMap.put("sid", callBack.getAccountSid());
                            extraMap.put("extraType", "03");
                            ExtraService extraService = extraServiceService.findExtraServiceByMap(extraMap);
                            if (extraService != null) {
                                // 无需审核，直接可以显示
                                toSerNumTemp.add(str);
                                logger.info("sid存在增值服务03-B路手机显号免审");
                                break;
                            }
                        }
                    }
                }
                //报文有传递主叫显号参数，审核校验为空时，进行字错误码提示
                if (Utils.notEmpty(callBack.getFromSerNum()) && !Utils.notEmpty(fromSerNumTemp)) {
                    // 主叫显号无效
                    if (flagFromSerNum_black) {
                        // 主叫显号黑名单号码限制
                        subrespCode = ConstantsEnum.REST_FROMSERNUM_NUMBERBLACK.getCode();
                        respDesc = ConstantsEnum.REST_FROMSERNUM_NUMBERBLACK.getDesc();
                    } else if (flagFromSerNum) {
                        // 主叫显号不能为主叫号码本身
                        subrespCode = ConstantsEnum.REST_FROMSERNUM_NOSELF.getCode();
                        respDesc = ConstantsEnum.REST_FROMSERNUM_NOSELF.getDesc();
                    } else {
                        // 主叫显号未审核
                        subrespCode = ConstantsEnum.REST_FROMSERNUM_NOT_AUDIT.getCode();
                        respDesc = ConstantsEnum.REST_FROMSERNUM_NOT_AUDIT.getDesc();
                    }
                }
                //报文有传递被叫显号参数，审核校验为空时，进行字错误码提示
                if (Utils.notEmpty(callBack.getToSerNum()) && !Utils.notEmpty(toSerNumTemp)) {
                    // 被叫显号无效
                    if (flagToSerNum_black) {
                        // 被叫显号黑名单号码限制
                        subrespCode = ConstantsEnum.REST_TOSERNUM_NUMBERBLACK.getCode();
                        respDesc = ConstantsEnum.REST_TOSERNUM_NUMBERBLACK.getDesc();
                    } else if (flagToSerNum) {
                        // 被叫显号不能为被叫号码本身
                        subrespCode = ConstantsEnum.REST_TOSERNUM_NOSELF.getCode();
                        respDesc = ConstantsEnum.REST_TOSERNUM_NOSELF.getDesc();
                    } else {
                        // 被叫显号未审核
                        subrespCode = ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getCode();
                        respDesc = ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getDesc();
                    }
                }
            }
            //主叫物联网卡处理
            if (Utils.cardValid(callBack.getFrom())) {
                // 主叫为物联网卡时，主叫显号无效，根据线路显号
                //fromSerNumTemp.clear();
                // 主叫为物联网卡时，如被叫显号未是绑定的号码，返回错误码限制呼叫
                if (!Utils.notEmpty(toSerNumTemp)) {
                    flag = false;
                } else if (toSerNumTemp.contains(callBack.getTo())) {
                    logger.info("物联网卡被叫显号不能为被叫号码本身");
                    flag = false;
                }
                //物联网卡不存在子错误码，重置为空字符串，避免返回报文中有字错误码提示
                subrespCode = "";
            }
            // 重新填充主被叫显号
            callBack.setFromSerNum(fromSerNumTemp);
            callBack.setToSerNum(toSerNumTemp);
        }

        //集成默认显号业务
        if (!Utils.notEmpty(callBack.getFromSerNum())) {
            //主叫无显号时
            //根据appInfo中配置，选择是系统默认或自定义中继(0:系统默认,1:自定义)
            AppInfo appInfo = appInfoService.getAppInfoByAppid(callBack.getAppId());
            if (Utils.notEmpty(appInfo)) {
                //获取系统默认显号池列表或自定义中继显号池列表
                List<String> relayNumpools = appInfoService.selectRelayNumpoolList(callBack.getAppId(), appInfo.getIsDefined());
                //列表不为空时，随机一个作为主叫显号
                if (Utils.notEmpty(relayNumpools)) {
                    //重新初始化临时列表
                    fromSerNumTemp = new ArrayList<String>();
                    int num = RandomUtil.getInt(0, relayNumpools.size() - 1);
                    fromSerNumTemp.add(relayNumpools.get(num));
                    callBack.setFromSerNum(fromSerNumTemp);
                }
            }
        }
        if (!Utils.notEmpty(callBack.getToSerNum())) {
            //被叫无显号时
            //根据appInfo中配置，选择是系统默认或自定义中继(0:系统默认,1:自定义)
            AppInfo appInfo = appInfoService.getAppInfoByAppid(callBack.getAppId());
            if (Utils.notEmpty(appInfo)) {
                //获取系统默认显号池列表或自定义中继显号池列表
                List<String> relayNumpools = appInfoService.selectRelayNumpoolList(callBack.getAppId(), appInfo.getIsDefined());
                //列表不为空时，随机一个作为被叫显号
                if (Utils.notEmpty(relayNumpools)) {
                    //重新初始化临时列表
                    toSerNumTemp = new ArrayList<String>();
                    int num = RandomUtil.getInt(0, relayNumpools.size() - 1);
                    toSerNumTemp.add(relayNumpools.get(num));
                    callBack.setToSerNum(toSerNumTemp);
                }
            }
        }
        map.put("flag", flag);
        map.put("subrespCode", subrespCode);
        map.put("respDesc", respDesc);
        return map;
    }

}
