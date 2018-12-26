package com.e9cloud.mybatis.service;

import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccAppInfo;

/**
 * Created by Administrator on 2017/2/14.
 */
public interface EccAppInfoService extends IBaseService {

    EccAppInfo getEccAppInfoByAppid(String appid);

    EccAppInfo getEccAppInfoByPK(String eccid);

    JSonMessage checkSwitchboard(String appid, String eccid, String cityid, String switchboard);

    String saveOrUpdateIVRAndReturnId(EccAppInfo eccInfo);
}
