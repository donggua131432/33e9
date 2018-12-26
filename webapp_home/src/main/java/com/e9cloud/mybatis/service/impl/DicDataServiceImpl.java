package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.domain.Province;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.IDicDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
@Service
public class DicDataServiceImpl extends BaseServiceImpl implements IDicDataService {

    /**
     * 根据type类型查询字典信息
     *
     * @param type
     */
    @Override
    public List<DicData> findDicListByType(String type) {
        return this.findObjectList(Mapper.DicData_Mapper.findDicListByType,type);
    }

    /**
     * 根据父类查询字典信息
     *
     * @param pid
     */
    @Override
    public List<DicData> findDicListByPid(String pid) {
        return this.findObjectList(Mapper.DicData_Mapper.findDicListByPid,pid);
    }

    /**
     * 根据省份编号得到城市列表
     *
     * @param pcode 省份编号
     * @return 城市列表
     */
    @Override
    public List<TelnoCity> getCitysByPcode(String pcode) {
        return this.findObjectListByPara(Mapper.CityManager_Mapper.selectCitysByPcode, pcode);
    }

    /**
     * 选择身份类别
     *
     * @param
     */
    @Override
    public List<Province> getProvinceByCode(String pcode) {
        return this.findObjectListByPara(Mapper.Province_Mapper.selectAll, pcode);
    }
}
