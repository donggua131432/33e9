package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppRelay;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppRelayService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppRelayServiceImpl extends BaseServiceImpl implements AppRelayService {
    /**
     * 保存应用中继
     *
     * @param appRelay 应用中继的基本信息
     */
    @Override
    public void saveAppRelay(AppRelay appRelay) {
        this.save(Mapper.AppRelay_Mapper.insertSelective, appRelay);
    }

    /**
     * 删除应用中继
     *
     * @param appRelay 应用中继的基本信息
     */
    @Override
    public void deleteAppRelay(AppRelay appRelay) {
        this.save(Mapper.AppRelay_Mapper.deleteAppRelay, appRelay);
    }

    /**
     * 根据条件查找一条应用中继
     * @param appRelay
     * @return
     */
    @Override
    public AppRelay getAppRelayByObj(AppRelay appRelay) {
        return this.findObject(Mapper.AppRelay_Mapper.findAppRelayByObj, appRelay);
    }

    @Override
    /**
     * 根据条件查找应用中继
     * @param appid
     * @return
     */
    public List<AppRelay> findAppRelayListByAppid(String appid) {
        return this.findObjectListByPara(Mapper.AppRelay_Mapper.findAppRelayListByAppid, appid);
    }

}
