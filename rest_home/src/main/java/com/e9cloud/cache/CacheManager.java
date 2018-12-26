package com.e9cloud.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.util.Constants;
import com.sun.tools.javac.jvm.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.rest.obt.TelnoObj;

/**
 * Created by Administrator on 2016/5/13.
 */
public class CacheManager {

    public static Map<String, TelnoObj> telnoInfoMap = new HashMap<>();

    public static Map<String, String> areacodePcodeMap = new HashMap<>();

    private static Map<String, AppInfo> appInfoMap = new HashMap();

    private static Map<String, CodeType> codeTypeMap = new HashMap<>();

    public static LocalCacheMap<List<AppointLink>> appointLinkMap = new LocalCacheMap<List<AppointLink>>("appointLinks", 100000);

    private static TelnoInfoService telnoInfoService = SpringContextHolder.getBean(TelnoInfoService.class);

    private static AreacodePcodeService areacodePcodeService = SpringContextHolder.getBean(AreacodePcodeService.class);

    private static AppointLinkService appointLinkService = SpringContextHolder.getBean(AppointLinkService.class);

    private static AppInfoService appInfoService = SpringContextHolder.getBean(AppInfoService.class);

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    public static void init() {
        logger.info("init   start");
        long start = System.currentTimeMillis();
        List<TelnoInfo> telnoInfos = telnoInfoService.getAll();
        List<AreacodePcode> areacodePcodes = areacodePcodeService.getAll();

        TelnoObj telnoObj = null;
        for (TelnoInfo telnoInfo : telnoInfos) {
            telnoObj = new TelnoObj(telnoInfo.getAreacode(), telnoInfo.getOperator());
            telnoInfoMap.put(telnoInfo.getTelno(), telnoObj);
        }
        for (AreacodePcode areacodePcode : areacodePcodes) {
            areacodePcodeMap.put(areacodePcode.getAreacode(), areacodePcode.getPcode());
        }
        loadAppiont();
        long end = System.currentTimeMillis();
        logger.info("init   end:" + (end - start));
    }

    public static List<AppointLink> loadAppiont() {
        logger.info("init   appiontstart");
        long start = System.currentTimeMillis();
        List<AppointLink> appointLinks = appointLinkService.getAll();
        Collections.sort(appointLinks, new Comparator<AppointLink>() {

            @Override
            public int compare(AppointLink o1, AppointLink o2) {
                int len1 = o1.getXhTelno().length() * 100 + o1.getDestTelno().length();
                int len2 = o2.getXhTelno().length() * 100 + o2.getDestTelno().length();
                if ("*".equals(o1.getXhTelno())) {
                    len1 = 5000 + o1.getDestTelno().length();
                }
                if ("*".equals(o2.getXhTelno())) {
                    len2 = 5000 + o2.getDestTelno().length();
                }
                return len2 - len1;
            }
        });
        appointLinkMap.put("appointLinks", appointLinks, 5 * 60);
        long end = System.currentTimeMillis();
        logger.info("init   appiontend:" + (end - start));
        return appointLinks;
    }

    public static List<AppointLink> getAppointLinks() {
        List<AppointLink> appointLinks = appointLinkMap.get("appointLinks");
        if (appointLinks == null) {
            return loadAppiont();
        }
        return appointLinks;
    }

    public static void reload() {
        logger.info("reload----------");
        telnoInfoMap.clear();
        areacodePcodeMap.clear();
        init();
    }

    public static void setAppInfo(AppInfo appInfo) {
        appInfoMap.put(appInfo.getAppid(), appInfo);
    }

    public static AppInfo getAppInfo(String appid) {
        AppInfo appInfo = appInfoMap.get(appid);
        if (appInfo == null) {
            appInfo = appInfoService.getAppInfoByAppid(appid);
            if (appInfo == null) {
                return null;
            } else {
                appInfoMap.put(appInfo.getAppid(), appInfo);
                return appInfo;
            }
        } else {
            return appInfo;
        }
    }

    public static CodeType getCodeType(Constants.BusinessCode businessCode) {
        return codeTypeMap.get(businessCode.getCode());
    }

    public static void setCodeType(CodeType codeType){
        codeTypeMap.put(codeType.getBusType(), codeType);
    }

}
