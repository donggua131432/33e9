package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.domain.Province;
import com.e9cloud.mybatis.domain.TelnoCity;

import java.util.List;

/**
 * 字段表业务类
 */
public interface IDicDataService extends IBaseService {
    /**
     * 根据type类型查询字典信息
     * @param type
     */
    List<DicData> findDicListByType(String type);

    /**
     * 根据父类查询字典信息
     * @param pid
     */
    List<DicData> findDicListByPid(String pid);

    /**
     * 根据省份编号得到城市列表
     * @param pcode 省份编号
     * @return 城市列表
     */
    List<TelnoCity> getCitysByPcode(String pcode);

    /**
     * 选择身份类别
     * @param pcode 城市
     */
    List<Province> getProvinceByCode(String pcode);
}
