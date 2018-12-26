package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.mybatis.domain.TelnoOperator;
import com.e9cloud.mybatis.domain.TelnoProvince;

import java.util.List;
import java.util.Map;

/**
 * Created by pengchunchen on 2016/8/3.
 */
public interface TelnoInfoService extends IBaseService {

    /**
     * 保存号段
     * @param telnoInfo 号段的基本信息
     */
    void saveTelnoInfo(TelnoInfo telnoInfo);

    /**
     * 删除号段
     * @param telnoInfo 号段的基本信息
     */
    void delTelnoInfo(TelnoInfo telnoInfo);

    /**
     * 修改号段
     * @param telnoInfo 号段的基本信息
     */
    void updateTelnoInfo(TelnoInfo telnoInfo);

    /**
     * 根据id查找一条号段信息
     * @param id
     */
    TelnoInfo queryTelnoInfoById(String id);

    /**
     * 校验号段存在性
     * @param telnoInfo 号段的基本信息
     */
    Integer countTelnoInfo(TelnoInfo telnoInfo);

    /**
     * 分页选取智能云调度号段列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageTelnoInfoList(Page page);

    /**
     * 导出号段
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadTelnoInfo(Page page);

    //查询所有运营商
    List<TelnoOperator> queryOperatorList();

    //查询所有省份
    List<TelnoProvince> queryProvinceList();

    //根据省份编号查询所有城市
    List<TelnoCity> queryCityList(Map map);

    //根据城市编号查询城市信息
    TelnoCity queryCityByCcode(String ccode);

    /**
     * 根据城市编号查询号段配置信息
     * @param ccode
     */
    TelnoInfo queryTelnoInfoByCcode(String ccode);

    //根据省名称模糊查询省份
    List<TelnoProvince> queryProvinceListByPname(Page page);


    TelnoInfo getTelnoInfoByTelno(String pre);
}
