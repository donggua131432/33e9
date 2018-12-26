package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TrafficScanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 话务总览 相关 service
 * Created by Administrator on 2016/8/23.
 */
@Service
public class TrafficScanServiceImpl extends BaseServiceImpl implements TrafficScanService {

    /**
     * 按天统计 专线语音 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countRestScanByDay(Map<String, Object> params) {
        return this.findObjectList(Mapper.RestDayRecord_Mapper.countRestScanByDay, params);
    }

    /**
     * 专号通 按天统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countMaskScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatMaskDayRecord_Mapper.countMaskScanByDay, params);
    }

    /**
     * 专号通 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countMaskScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatMaskHourRecord_Mapper.countMaskScanByHour, params);
    }

    /**
     * 专号通 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countMaskScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatMaskMinuteRecord_Mapper.countMaskScanByMin, params);
    }

    /**
     * 智能云调度 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countCcScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatCcDayRecord_Mapper.countCcScanByDay, params);
    }

    /**
     * 智能云调度 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countCcScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatCcHourRecord_Mapper.countCcScanByHour, params);
    }

    /**
     * 智能云调度 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countCcScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatCcMinuteRecord_Mapper.countCcScanByMin, params);
    }

    /**
     * 语音通知 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceDayRecord_Mapper.countVoiceScanByDay, params);
    }

    /**
     * 语音通知 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceHourRecord_Mapper.countVoiceScanByHour, params);
    }

    /**
     * 语音通知 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceMinuteRecord_Mapper.countVoiceScanByMin, params);
    }

    /**
     * SIP 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSipScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSipDayRecord_Mapper.countSipScanByDay, params);
    }

    /**
     * SIP 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSipScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSipHourRecord_Mapper.countSipScanByHour, params);
    }

    /**
     * SIP 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSipScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSipMinuteRecord_Mapper.countSipScanByMin, params);
    }

    /**
     * 云话机 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSpScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSpDayRecord_Mapper.countSpScanByDay, params);
    }

    /**
     * 云话机 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSpScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSpHourRecord_Mapper.countSpScanByHour, params);
    }

    /**
     * 云话机 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countSpScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatSpMinuteRecord_Mapper.countSpScanByMin, params);
    }

    @Override
    public Map<String, Object> countCcScanByOperator(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.StatCcDayRecord_Mapper.countCcScanByOperator, params);
    }

    /**
     * 云总机 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countEccScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatIvrHourRecord_Mapper.countEccScanByHour, params);
    }

    /**
     * 云总机 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countEccScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatIvrDayRecord_Mapper.countEccScanByDay, params);
    }

    /**
     * 云总机 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countEccScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatIvrMinuteRecord_Mapper.countEccScanByMin, params);
    }

    /**
     * 语音验证码 日统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceverifyScanByDay(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceverifyDayRecord_Mapper.countVoiceverifyScanByDay, params);
    }

    /**
     * 语音验证码 小时统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceverifyScanByHour(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceverifyHourRecord_Mapper.countVoiceverifyScanByHour, params);
    }

    /**
     * 语音验证码 分钟统计 的话务情况
     *
     * @param params 请求
     * @return
     */
    @Override
    public List<Map<String, Object>> countVoiceverifyScanByMin(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.StatVoiceverifyMinuteRecord_Mapper.countVoiceverifyScanByMin, params);
    }
}
