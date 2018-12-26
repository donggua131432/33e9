package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CityAreaCode;

import java.util.List;

/**
 * Created by dukai on 2016/6/2.
 */
public interface CityAreaCodeService extends IBaseService{

    /**
     * 获取城市区号列表
     * @return
     */
    List<CityAreaCode> getCityAreaCodeList(CityAreaCode cityAreaCode);

    List<CityAreaCode> selectCityAreaCodeByAreaCode(String areaCodes);

}
