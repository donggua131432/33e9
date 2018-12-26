package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CityAreaCodeService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dukai on 2016/6/2.
 */
@Service
public class CityAreaCodeServiceImpl extends BaseServiceImpl implements CityAreaCodeService{

    /**
     * 获取城市区号信息列表
     * @return
     */
    @Override
    public List<CityAreaCode> getCityAreaCodeList(CityAreaCode cityAreaCode) {
        return this.findObjectList(Mapper.CityAreaCode_Mapper.selectCityAreaCodeList, cityAreaCode);
    }

    @Override
    public List<CityAreaCode> selectCityAreaCodeByAreaCode(String areaCodes) {
        return this.findObjectList(Mapper.CityAreaCode_Mapper.selectCityAreaCodeByAreaCode, areaCodes);
    }
}
