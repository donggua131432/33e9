package com.e9cloud.mybatis.service.impl;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CityManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/6.
 */
@Service
public class CityManagerServiceImpl extends BaseServiceImpl implements CityManagerService {
    /**
     * 根据ID查找城市信息
     * @param id
     * @return
     */
    @Override
    public TelnoCity findCityById(String id) {
        return this.findObject(Mapper.CityManager_Mapper.selectCityManagerById, id);
    }
    /**
     * 根据名称查找城市信息
     * @param cname
     * @return
     */
    @Override
    public TelnoCity findCityByName(String cname) {
        return this.findObject(Mapper.CityManager_Mapper.selectCityManagerByName, cname);
    }
    /**
     * 根据编号查找城市信息
     * @param ccode
     * @return
     */
    @Override
    public TelnoCity findCityByCode(String ccode) {
        return this.findObject(Mapper.CityManager_Mapper.selectCityManagerByAreaCode, ccode);
    }

    /**
     * 根据区号查找城市信息
     * @param areaCode
     * @return
     */
    @Override
    public TelnoCity findCityByAreaCode(String areaCode) {
        return this.findObject(Mapper.CityManager_Mapper.selectCityManagerByAreaCode, areaCode);
    }

    /**
     * 根据区号查找城市信息
     * @param areaCode
     * @return
     */
    @Override
    public TelnoCity getTelnoCityByAreaCode(String areaCode) {
        return this.findObject(Mapper.CityManager_Mapper.selectTelnoCityByAreaCode, areaCode);
    }

    /**
     * 保存城市信息
     * @param city 城市信息
     */
    @Override
    public void saveCityManager(TelnoCity city) {
        this.save(Mapper.CityManager_Mapper.saveCityManager, city);
    }

    /**
     * 修改客户信息
     * @param city
     */
    @Override
    public void updateCityManager(TelnoCity city) {
        this.update(Mapper.CityManager_Mapper.updateCityManager, city);
    }

    /**
     * 删除城市信息
     * @param id
     */
    @Override
    public void deleteCityManager(String id) {
        this.delete(Mapper.CityManager_Mapper.deleteCityManager, id);
    }

    /**
     * 分页查询城市信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCityList(Page page) {
        return this.page(Mapper.CityManager_Mapper.pageCityManagerList, page);
    }

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCityInfo(Page page) {
        return this.download(Mapper.CityManager_Mapper.pageCityManagerList, page);
    }

    /**
     * 查询最大的ID
     * @param cname
     * @return
     */
    @Override
    public int getMaxId(String cname) {
        return this.findObject(Mapper.CityManager_Mapper.getMaxId, cname);
    }

    /**
     * 根据省份编号得到城市列表
     *
     * @param params pcode 省份编号,diy是否选择自定义城市：true 是
     * @return 城市列表
     */
    @Override
    public List<TelnoCity> getCitysByPcode(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.CityManager_Mapper.selectCitysByPcode, params);
    }
}
