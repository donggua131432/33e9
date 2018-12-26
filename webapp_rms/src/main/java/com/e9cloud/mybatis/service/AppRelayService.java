package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppRelay;

import java.util.List;

/**
 * Created by hzd on 2016/9/9.
 */
public interface AppRelayService extends IBaseService {

    /**
     * 保存应用
     * @param appRelay 应用的基本信息
     */
    void saveAppRelay(AppRelay appRelay);

    void deleteAppRelay(AppRelay appRelay);

    AppRelay getAppRelayByObj(AppRelay appRelay);
    /**
     * 根据条件查找应用中继
     * @param appid
     * @return
     */
    List<AppRelay> findAppRelayListByAppid(String appid);
}
