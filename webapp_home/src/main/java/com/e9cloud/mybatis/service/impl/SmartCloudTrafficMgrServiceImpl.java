package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SmartCloudTrafficMgrService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 智能云调度相关处理
 * Created by wzj on 2016/3/11.
 */
@Service
public class SmartCloudTrafficMgrServiceImpl extends BaseServiceImpl implements SmartCloudTrafficMgrService {

    /**
     *根据sid获取调度区域信息
     * @param sid
     * @return
     */
    @Override
    public List<CcArea> findCcAreaList(String sid) {
        return this.findObjectList(Mapper.SmartCloudTrafficMgr_Mapper.selectCcAreaBySid, sid);
    }

    /**
     * 添加话务调度信息
     * @param ccDispatch
     */
    @Override
    public void addDispatchInfo(CcDispatch ccDispatch,List<CcDispatchInfo> ccDispatchInfoList) {
        this.save(Mapper.SmartCloudTrafficMgr_Mapper.insertDispatch, ccDispatch);
        this.save(Mapper.SmartCloudTrafficMgr_Mapper.insertCcDispatchInfoList, ccDispatchInfoList);
    }

    /**
     * 修改话务调度信息
     * @param ccDispatch
     */
    @Override
    public void updateDispatchInfo(CcDispatch ccDispatch) {
        this.update(Mapper.SmartCloudTrafficMgr_Mapper.updateDispatch, ccDispatch);
    }

    /**
     * 根据话务调度ID查找信息
     * @param dispatchId
     * @return
     */
    @Override
    public CcDispatch findByDispatchId(String dispatchId) {
        return this.findObject(Mapper.SmartCloudTrafficMgr_Mapper.selectByDispatchId, dispatchId);
    }


    /**
     * 分页查询话务调度信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageDispatchList(Page page) {
        return this.page(Mapper.SmartCloudTrafficMgr_Mapper.selectDispatchPage, page);
    }

    /**
     * 导出话务调度信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadDispatchList(Page page) {
        return this.download(Mapper.SmartCloudTrafficMgr_Mapper.selectDispatchPage, page);
    }


    /**
     * 根据dispatchId删除调度信息
     * @param dispatchId
     */
    @Override
    public void deleteCcDispatchByDispatchId(String dispatchId) {
        this.delete(Mapper.SmartCloudTrafficMgr_Mapper.deleteCcDispatchByDispatchId, dispatchId);
    }

    /**
     * 根据dispatchId删除配置的呼叫中心
     * @param dispatchId
     */
    @Override
    public void deleteCcDispatchInfoByDispatchId(String dispatchId) {
        this.delete(Mapper.SmartCloudTrafficMgr_Mapper.deleteCcDispatchInfoByDispatchId, dispatchId);
    }

    /**
     * 批量添加话务调度呼叫中心
     * @param ccDispatchInfoList
     */
    @Override
    public void insertCcDispatchInfoList(List<CcDispatchInfo> ccDispatchInfoList) {
        this.save(Mapper.SmartCloudTrafficMgr_Mapper.insertCcDispatchInfoList, ccDispatchInfoList);
    }

    /**
     * 根据sid获取呼叫中心信息列表
     * @param sid
     * @return
     */
    @Override
    public List<CcInfo> findCcInfoListBySid(String sid) {
        return this.findObjectList(Mapper.SmartCloudTrafficMgr_Mapper.selectCcInfoListBySid, sid);
    }

    /**
     *根据dispatchId获取配置的呼叫中心
     * @param dispatchId
     * @return
     */
    @Override
    public List<CcDispatchInfo> findCcDispatchInfoList(String dispatchId) {
        return this.findObjectList(Mapper.SmartCloudTrafficMgr_Mapper.selectCcDispatchInfoList, dispatchId);
    }

    /**
     * 统计sid下某个调度名称的的个数
     *
     * @param ccDispatch
     * @return
     */
    @Override
    public long countDispatchBySidAndDispatchName(CcDispatch ccDispatch) {
        return this.findObject(Mapper.SmartCloudTrafficMgr_Mapper.countDispatchBySidAndDispatchName, ccDispatch);
    }

    /**
     * 根据subid统计呼叫中心个数
     *
     * @param subId
     * @return
     */
    @Override
    public long countCallCenterBySubId(String subId) {
        return this.findObjectByPara(Mapper.SmartCloudTrafficMgr_Mapper.countCallCenterBySubId, subId);
    }


    /**
     * ==============================================话务统计===========================================================
     * 月统计 分页信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCcMonthRecordList(Page page) {
        return this.page(Mapper.StatCcDayRecord_Mapper.selectCcMonthRecordPage, page);
    }

    /**
     * 下载 月统计报表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcMonthRecordList(Page page) {
        return this.download(Mapper.StatCcDayRecord_Mapper.selectCcMonthRecordPage, page);
    }

    /**
     * 日统计 分页信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCcDayRecordList(Page page) {
        return this.page(Mapper.StatCcDayRecord_Mapper.selectCcDayRecordPage, page);
    }

    /**
     * 下载 日统计报表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcDayRecordList(Page page) {
        return this.download(Mapper.StatCcDayRecord_Mapper.selectCcDayRecordPage, page);
    }

    /**
     * 时统计 分页信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCcHourRecordList(Page page) {
        return this.page(Mapper.StatCcHourRecord_Mapper.selectCcHourRecordPage, page);
    }

    /**
     * 下载 时统计报表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcHourRecordList(Page page) {
        return this.download(Mapper.StatCcHourRecord_Mapper.selectCcHourRecordPage, page);
    }

    /**
     *话务总览
     * @param statCcDayRecord
     * @return
     */
    @Override
    public List<StatCcDayRecord> findCcCallInRecordList(StatCcDayRecord statCcDayRecord) {
        return this.findObjectList(Mapper.StatCcDayRecord_Mapper.selectCcCallInRecordList, statCcDayRecord);
    }


    /**
     *  分钟统计 智能云调度
     */
    @Override
    public List<Map<String, Object>> selectCcMinuteList(Map<String, Object> params) {
        return this.findObjectList(Mapper.SmartCloudTrafficMgr_Mapper.selectCcMinuteList, params);
    }
}
