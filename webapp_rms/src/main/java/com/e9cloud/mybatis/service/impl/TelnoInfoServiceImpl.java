package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.mybatis.domain.TelnoOperator;
import com.e9cloud.mybatis.domain.TelnoProvince;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TelnoInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/1.
 */
@Service
public class TelnoInfoServiceImpl extends BaseServiceImpl implements TelnoInfoService {
    /**
     * 保存号段
     * @param telnoInfo 号段的基本信息
     */
    @Override
    public void saveTelnoInfo(TelnoInfo telnoInfo) {
        this.save(Mapper.TelnoInfo_Mapper.saveTelnoInfo, telnoInfo);
    }

    /**
     * 删除号段
     * @param telnoInfo 号段的基本信息
     */
    @Override
    public void delTelnoInfo(TelnoInfo telnoInfo){
        this.delete(Mapper.TelnoInfo_Mapper.delTelnoInfo, telnoInfo);
    }

    /**
     * 修改号段
     * @param telnoInfo 号段的基本信息
     */
    @Override
    public void updateTelnoInfo(TelnoInfo telnoInfo) {
        this.update(Mapper.TelnoInfo_Mapper.updateTelnoInfo, telnoInfo);
    }

    /**
     * 根据id查找一条号段信息
     * @param id
     */
    @Override
    public TelnoInfo queryTelnoInfoById(String id){
        return this.findObjectByPara(Mapper.TelnoInfo_Mapper.queryTelnoInfoById, id);
    }

    /**
     * 校验号段存在性
     * @param telnoInfo 号段的基本信息
     */
    @Override
    public Integer countTelnoInfo(TelnoInfo telnoInfo) {
        return this.findObject(Mapper.TelnoInfo_Mapper.countTelnoInfo, telnoInfo);
    }
    
    /**
     * 分页选取智能云调度号段列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageTelnoInfoList(Page page) {
        return this.page(Mapper.TelnoInfo_Mapper.pageTelnoInfoList, page);
    }

    /**
     * 导出号段
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadTelnoInfo(Page page) {
        return this.download(Mapper.TelnoInfo_Mapper.pageTelnoInfoList, page);
    }

    /**
     * 查询所有运营商
     *
     * @return list
     */
    @Override
    public List<TelnoOperator> queryOperatorList() {
        return this.findObjectList(Mapper.TelnoOperator_Mapper.queryOperatorList, TelnoOperator.class);
    }

    /**
     * 查询所有省份
     *
     * @return list
     */
    @Override
    public List<TelnoProvince> queryProvinceList() {
        return this.findObjectList(Mapper.ProvinceManager_Mapper.queryProvinceList, TelnoProvince.class);
    }

    /**
     * 根据省份编号查询所有城市
     *
     * @return list
     */
    @Override
    public List<TelnoCity> queryCityList(Map map) {
        return this.findObjectListByMap(Mapper.CityManager_Mapper.queryCityList, map);
    }

    //根据城市编号查询城市信息
    public TelnoCity queryCityByCcode(String ccode){
        return this.findObjectByPara(Mapper.CityManager_Mapper.selectCityManagerByCode, ccode);
    }

    /**
     * 根据城市编号查询号段信息
     *
     * @return list
     */
    @Override
    public TelnoInfo queryTelnoInfoByCcode(String ccode) {
        return this.findObjectByPara(Mapper.TelnoInfo_Mapper.queryTelnoInfoByCcode, ccode);
    }

    /**
     * 根据省份名称模糊查询省份
     *
     * @return list
     */
    @Override
    public List<TelnoProvince> queryProvinceListByPname(Page page) {
        return this.findObjectList(Mapper.ProvinceManager_Mapper.queryProvinceListByPname, page);
    }

    @Override
    public TelnoInfo getTelnoInfoByTelno(String pre) {
        return this.findObjectByPara(Mapper.TelnoInfo_Mapper.selectTelnoInfoByTelno, pre);
    }
}
