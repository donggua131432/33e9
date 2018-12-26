package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 城市管理
 * Created by wy on 2016/7/8.
 */
public interface CityManagerService extends IBaseService {

    /**
     * 根据ID查找城市信息
     * @param id
     * @return CityManager
     */
    TelnoCity findCityById(String id);
    /**
     * 根据名称查找城市信息
     * @param cname
     * @return CityManager
     */
    TelnoCity findCityByName(String cname);

    /**
     * 保存城市信息
     * @param city 城市信息
     */
    void saveCityManager(TelnoCity city);
    /**
     * 修改城市信息
     */
    void updateCityManager(TelnoCity city);

    /**
     * 删除城市信息
     */
    void deleteCityManager(String id);

    /**
     * 分页查询城市信息
     * @param page
     * @return
     */
    PageWrapper pageCityList(Page page);

    /**
     * 导出城市信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCityInfo(Page page);

    /**
     * 查询最大的ID
     * @param cname
     * @return
     */
    public int getMaxId(String cname);

    /**
     * 根据编号查找城市信息
     * @param ccode
     * @return
     */
    public TelnoCity findCityByCode(String ccode);

    /**
     * 根据区号查找城市信息
     * @param areaCode
     * @return
     */
    TelnoCity getTelnoCityByAreaCode(String areaCode);

    /**
     * 校验城市区号
     * @param areaCode 城市
     */
    public TelnoCity findCityByAreaCode(String areaCode);

    /**
     * 根据省份编号得到城市列表
     * @param params pcode 省份编号,diy是否选择自定义城市：true 是
     * @return 城市列表
     */
    List<TelnoCity> getCitysByPcode(Map<String,Object> params);
}
