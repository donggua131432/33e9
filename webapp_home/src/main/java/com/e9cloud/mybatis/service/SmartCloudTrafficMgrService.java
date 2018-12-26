package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;

import java.util.List;
import java.util.Map;

/**
 * 智能云调度相关处理
 * Created by dukai on 2016/8/6.
 */
public interface SmartCloudTrafficMgrService extends IBaseService {

    /**
     * 根据sid获取调度区域信息
     * @param sid
     * @return
     */
    List<CcArea> findCcAreaList(String sid);


    /**
     * 添加话务调度信息
     * @param ccDispatch
     */
    void addDispatchInfo(CcDispatch ccDispatch,List<CcDispatchInfo> ccDispatchInfoList);

    /**
     * 修改话务调度信息
     * @param ccDispatch
     */
    void updateDispatchInfo(CcDispatch ccDispatch);

    /**
     * 根据话务调度ID查找信息
     * @param dispatchId
     * @return
     */
    CcDispatch findByDispatchId(String dispatchId);


    /**
     * 分页查询话务调度信息
     * @param page
     * @return
     */
    PageWrapper pageDispatchList(Page page);

    /**
     * 下载话务调度信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadDispatchList(Page page);

    /**
     * 根据sid获取呼叫中心信息列表
     * @param sid
     * @return
     */
    List<CcInfo> findCcInfoListBySid(String sid);


    /**
     * 根据dispatchId删除调度信息
     * @param dispatchId
     */
    void deleteCcDispatchByDispatchId(String dispatchId);

    /**
     * 根据dispatchId删除配置的呼叫中心
     * @param dispatchId
     */
    void deleteCcDispatchInfoByDispatchId(String dispatchId);

    /**
     * 批量添加话务调度呼叫中心
     * @param ccDispatchInfoList
     */
    void insertCcDispatchInfoList(List<CcDispatchInfo> ccDispatchInfoList);

    /**
     * 根据dispatchId获取配置的呼叫中心
     * @param dispatchId
     * @return
     */
    List<CcDispatchInfo> findCcDispatchInfoList(String dispatchId);

    /**
     * 统计sid下某个名称的的个数
     * @param ccDispatch
     * @return
     */
    long countDispatchBySidAndDispatchName(CcDispatch ccDispatch);

    /**
     * 根据subid统计呼叫中心个数
     * @param subId
     * @return
     */
    long countCallCenterBySubId(String subId);



    /**
     * ===================================================话务统计=========================================================
     * 月统计 分页信息列表
     * @param page
     * @return
     */
    PageWrapper pageCcMonthRecordList(Page page);

    /**
     * 下载 月统计报表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcMonthRecordList(Page page);

    /**
     * 日统计 分页信息列表
     * @param page
     * @return
     */
    PageWrapper pageCcDayRecordList(Page page);

    /**
     * 下载 日统计报表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcDayRecordList(Page page);

    /**
     * 时统计 分页信息列表
     * @param page
     * @return
     */
    PageWrapper pageCcHourRecordList(Page page);

    /**
     * 下载 时统计报表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcHourRecordList(Page page);

    /**
     * 话务总览
     * @param statCcDayRecord
     * @return
     */
    List<StatCcDayRecord> findCcCallInRecordList(StatCcDayRecord statCcDayRecord);


    /**
     *  分钟统计 智能云调度
     */
    List<Map<String,Object>> selectCcMinuteList(Map<String, Object> params);
}
