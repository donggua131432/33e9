package com.e9cloud.rest.biz;

import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.RandomUtil;
import com.e9cloud.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dukai on 2016/9/28.
 */
@Service
public class CommonService {
    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppNumberRestService appNumberRestService;

    @Autowired
    private NumberBlackService numberBlackService;

    @Autowired
    private ExtraServiceService extraServiceService;

    @Autowired
    private CardPhoneService cardPhoneService;

    /**
     * appid和accountSid的校验
     * @param appId 应用ID
     * @param accountSid 账户ID
     * @param busType 业务类型(01:智能云调度(955xx), 02:回拨REST, 03:sip, 05:云话机)
     * @return
     */
    public Map<String, String> checkAppAndAccount(String appId, String accountSid, String busType){
        Map<String, String> map = new HashMap<>();

        //判断appId是否为空
        if (!Utils.notEmpty(appId)) {
            map.put("respCode",ConstantsEnum.REST_APPID_NULL.getCode());
            map.put("respDesc",ConstantsEnum.REST_APPID_NULL.getDesc());
            logger.info(ConstantsEnum.REST_APPID_NULL.getDesc());
            return map;
        }
        //获取应用信息
        AppInfo appInfo = appInfoService.getAppInfoByAppid(appId);
        //判断appId是否存在
        if (appInfo == null || !busType.equals(appInfo.getBusType())) {
            map.put("respCode",ConstantsEnum.REST_APPID_NO.getCode());
            map.put("respDesc",ConstantsEnum.REST_APPID_NO.getDesc());
            logger.info(ConstantsEnum.REST_APPID_NO.getDesc());
            return map;
        }


        //判断appId是否可用
        if (!"00".equals(appInfo.getStatus())) {
            map.put("respCode",ConstantsEnum.REST_APPID_ABNORMAL.getCode());
            map.put("respDesc",ConstantsEnum.REST_APPID_ABNORMAL.getDesc());
            logger.info(ConstantsEnum.REST_APPID_NO.getDesc());
            return map;
        }
        //此应用是否属于该账户
        if (!accountSid.equals(appInfo.getSid())) {
            map.put("respCode",ConstantsEnum.REST_APPID_NOT_SID.getCode());
            map.put("respDesc",ConstantsEnum.REST_APPID_NOT_SID.getDesc());
            logger.info(ConstantsEnum.REST_APPID_NOT_SID.getDesc());
            return map;
        }

        //应用开启限额，并超额
        if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
            map.put("respCode",ConstantsEnum.REST_APPID_LIMITDUE.getCode());
            map.put("respDesc",ConstantsEnum.REST_APPID_LIMITDUE.getDesc());
            logger.info(ConstantsEnum.REST_APPID_LIMITDUE.getDesc());
            return map;
        }

        // 获取用户token
        User user = accountService.getUserBySid(accountSid);
        //判断该用户是否存在
        if (user == null) {
            map.put("respCode",ConstantsEnum.REST_SIDUSER_NO.getCode());
            map.put("respDesc",ConstantsEnum.REST_SIDUSER_NO.getDesc());
            logger.info(ConstantsEnum.REST_SIDUSER_NO.getDesc());
            return map;
        }
        //判断主账号是否可用 既sid是否有效
        if (!"1".equals(user.getStatus())) {
            map.put("respCode",ConstantsEnum.REST_SID_ABNORMAL.getCode());
            map.put("respDesc",ConstantsEnum.REST_SID_ABNORMAL.getDesc());
            logger.info(ConstantsEnum.REST_SID_ABNORMAL.getDesc());
            return map;
        }
        //判断用户是否欠费
        if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
            map.put("respCode",ConstantsEnum.REST_SID_OVERDUE.getCode());
            map.put("respDesc",ConstantsEnum.REST_SID_OVERDUE.getDesc());
            logger.info(ConstantsEnum.REST_SID_OVERDUE.getDesc());
            return map;
        }

        return null;
    }


    /**
     * 被叫显号校验 校验外显号码是否为审核通过的号码 或者不在黑名单中的号码
     * @param appId 应用ID
     * @param accountId 账户ID
     * @param beCalledDisplayNumList 需要校验的外显号码列表
     * @param beCalledNumList 被叫号码列表
     * @param callNum 主叫号码
     * @return
     */
    public Map<String, String> checkShowNumber(String appId, String accountId, List<String> beCalledDisplayNumList, List<String> beCalledNumList, String callNum){
        Map<String, String> map = new HashMap<>();
        map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
        map.put("respDesc",ConstantsEnum.REST_SUCCCODE.getDesc());

        //获取黑名单号码列表
        List<String> numberBlacks=numberBlackService.findList();
        //获取审核通过的显号列表
        List<String> appNumberList = appNumberRestService.findAppNumberRestListByAppid(appId);

        for (String beCalledDisplayNum : beCalledDisplayNumList) {
            if(Utils.notEmpty(numberBlacks) && numberBlacks.contains(beCalledDisplayNum)) {
                // 被叫显号黑名单限制，则不传递显号，根据线路自动显号
                map.put("respCode",ConstantsEnum.REST_TOSERNUM_NUMBERBLACK.getCode());
                map.put("respDesc",ConstantsEnum.REST_TOSERNUM_NUMBERBLACK.getDesc());
                logger.info("被叫显号黑名单号码限制");
                return map;
            }

            if(callNum.equals(beCalledDisplayNum) && Utils.phoneValid(beCalledDisplayNum)) {
                // 增加一个被叫显号规则，即被叫可以显示主叫号码（必须手机号），
                // 无需审核，直接可以显示
                logger.info("被叫可以显示主叫号码（必须手机号）");
                map.put("beCalledDisplayNum", beCalledDisplayNum);
                return map;
            }

            if (Utils.cardValid(callNum)) {
                // 主叫为物联网卡时，被叫显号规则：必须绑定的号码
                //获取物联网卡绑定的号码列表集合
                Map<String,String> mapTmp=new HashMap<>();
                mapTmp.put("card",callNum);
                mapTmp.put("appId",appId);
                List<String> cardPhones=cardPhoneService.findPhoneListBycard(mapTmp);
                if(Utils.notEmpty(cardPhones)){
                    if(cardPhones.contains(beCalledDisplayNum)){
                        logger.info("主叫为物联网卡时，被叫可以显示绑定的号码");
                        map.put("beCalledDisplayNum", beCalledDisplayNum);
                        return map;
                    }
                }
            }

            if(appNumberList.contains(beCalledDisplayNum)) {
                if (beCalledNumList.contains(beCalledDisplayNum)) {
                    // 被叫显号不能为被叫号码本身
                    logger.info("被叫显号不能为被叫号码本身");
                    map.put("respCode",ConstantsEnum.REST_TOSERNUM_NOSELF.getCode());
                    map.put("respDesc",ConstantsEnum.REST_TOSERNUM_NOSELF.getDesc());
                    return map;
                } else {
                    // 取第一个有效的被叫显号
                    logger.info("取第一个有效的被叫显号" + beCalledDisplayNum);
                    map.put("beCalledDisplayNum", beCalledDisplayNum);
                    return map;
                }
            }

            //增加一个被叫显号规则，即sid存在增值服务03-B路手机显号免审时，无需审核，直接可以显示
            if(!beCalledNumList.contains(beCalledDisplayNum) && Utils.phoneValid(beCalledDisplayNum)){
                ////根据sid查询增值服务
                Map extraMap=new HashMap<>();
                extraMap.put("sid",accountId);
                extraMap.put("extraType","03");
                ExtraService extraService=extraServiceService.findExtraServiceByMap(extraMap);
                if(extraService!=null){
                    // 无需审核，直接可以显示
                    logger.info("sid存在增值服务03-B路手机显号免审");
                    map.put("beCalledDisplayNum", beCalledDisplayNum);
                    return map;
                }
            }
        }

        //被叫无显号时或者显号审核未通过时 取中自定义中继号码池或者系统默认中继号码池强显
        //根据appInfo中配置，选择是系统默认或自定义中继(0:系统默认,1:自定义)
        AppInfo appInfo = appInfoService.getAppInfoByAppid(appId);
        if (Utils.notEmpty(appInfo)) {
            //获取系统默认显号池列表或自定义中继显号池列表
            List<String> relayNumPools=appInfoService.selectRelayNumpoolList(appId,appInfo.getIsDefined());
            //列表不为空时，随机一个作为被叫显号
            if(Utils.notEmpty(relayNumPools)){
                if(Utils.notEmpty(beCalledDisplayNumList)){
                    //被叫显号未审核
                    map.put("respCode",ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getCode());
                    map.put("respDesc",ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getDesc());
                }
                //重新初始化临时列表
                int num= RandomUtil.getInt(0,relayNumPools.size()-1);
                map.put("beCalledDisplayNum", relayNumPools.get(num));
                return map;
            }
        }

        if(Utils.notEmpty(beCalledDisplayNumList)){
            map.put("respCode",ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getCode());
            map.put("respDesc",ConstantsEnum.REST_TOSERNUM_NOT_AUDIT.getDesc());
        }

        map.put("beCalledDisplayNum","");
        return map;
    }

    /**
     * 将字符串转换为数组并返回不重复的集合
     * @param str
     * @return
     */
    public List<String> getStrToList(String str){
        HashSet<String> strList = new HashSet<>();
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] strArray = str.split(",");
        for (String temp : strArray) {
            strList.add(temp);
        }
        return new ArrayList<>(strList);
    }
}
