package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
@Service
public class AppInfoServiceImpl extends BaseServiceImpl implements AppInfoService {

    /**
     * 查询应用基本信息
     *
     * @param appid
     */
    @Override
    public AppInfo getAppInfoByAppid(String appid) {
        //先从缓存容器中加载，不存在或超时再查数据库
        AppInfo appInfo = InitValue.appInfoCache.get(appid);

        logger.info("缓存中返回：{} for {}", appInfo, appid);

        if (appInfo == null) {
            logger.info("缓存中没有，从数据库中取值 app: {}", appid);
            appInfo = this.findObjectByPara(Mapper.AppInfo_Mapper.selectByAppid, appid);
            //放入缓存容器，一分钟有效
            InitValue.appInfoCache.put(appid, appInfo, 60);
        }
        return appInfo;
    }

    /**
     * 查询显号池
     *
     * @param appid
     * @param isDefined
     */
    @Override
    public List<String> selectRelayNumpoolList(String appid, String isDefined) {
        //先从缓存容器中加载，不存在或超时再查数据库
        List<String> relayNumpools = InitValue.relayNumpoolCache.get("relayNumpools_" + appid);
        if (relayNumpools == null) {
            List<RelayNumpool> list = null;
            if ("0".equals(isDefined)) {
                //系统默认显号池列表
                list = this.findObjectList(Mapper.RelayNumpool_Mapper.selectRelayNumpoolsByZero, RelayNumpool.class);
            } else {
                //自定义中继显号池列表
                list = this.findObjectListByPara(Mapper.RelayNumpool_Mapper.selectRelayNumpoolsByOne, appid);
            }

            if (list != null && list.size() > 0) {
                relayNumpools = new ArrayList<String>();
                //进行黑名单号码获取存放list集合
                for (RelayNumpool relayNumpool : list) {
                    relayNumpools.add(relayNumpool.getNumber());
                }
                //放入缓存容器，一分钟有效
                InitValue.relayNumpoolCache.put("relayNumpools_" + appid, relayNumpools, 60);
            }
        }
        return relayNumpools;
    }

    /**
     * 查询所有app数据
     *
     * @return
     */
    @Override
    public List<AppInfo> findAll() {
        return super.findList(Mapper.AppInfo_Mapper.findAll);
    }


    /**
     * 查询app应用扩展信息
     * @param appid
     * @return
     */
    @Override
    public AppInfoExtra findAppInfoExtraByAppid(String appid) {
        //先从缓存容器中加载，不存在或超时再查数据库
        AppInfoExtra appInfoExtra = InitValue.appInfoExtraCache.get(appid);
        if (appInfoExtra == null) {
            appInfoExtra = this.findObjectByPara(Mapper.AppInfoExtra_Mapper.selectAppInfoExtraByAppId, appid);
            //放入缓存容器，一分钟有效
            InitValue.appInfoExtraCache.put(appid, appInfoExtra, 60);
        }
        return appInfoExtra;
    }
}
