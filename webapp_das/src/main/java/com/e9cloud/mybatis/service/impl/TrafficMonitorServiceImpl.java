package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestStatMinuteRecordService;
import com.e9cloud.mybatis.service.TrafficMonitorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * rest业务，话务统计-分钟统计
 * Created by dukai on 2016/12/06.
 */
@Service
public class TrafficMonitorServiceImpl extends BaseServiceImpl implements TrafficMonitorService {

    /**
     *  分钟统计 专号通
     */
    @Override
    public List<Map<String, Object>> countMaskScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatMaskMinuteRecord_Mapper.countMaskScanByMin, params);
    }

    /**
     *  分钟统计 语音通知
     */
    @Override
    public List<Map<String, Object>> countVoiceScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatVoiceMinuteRecord_Mapper.countVoiceScanByMin, params);
    }


    /**
     *  分钟统计 SIP
     */
    @Override
    public List<Map<String, Object>> countSipScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatSipMinuteRecord_Mapper.countSipScanByMin, params);
    }

    /**
     *  分钟统计 SipPhone 云话机
     */
    @Override
    public List<Map<String, Object>> countSipPhoneScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatSpMinuteRecord_Mapper.countSpScanByMin, params);
    }

    /**
     *  分钟统计 智能云调度
     */
    @Override
    public List<Map<String, Object>> countCloudScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatCcMinuteRecord_Mapper.countCcScanByMin, params);
    }

    /**
     *  省份统计 智能云调度
     */
    @Override
    public List<Map<String, Object>> countCloudScanByProv(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatCcMinuteRecord_Mapper.countCcScanByProv, params);
    }

    /**
     *  分钟统计 rest接口
     */
    @Override
    public List<Map<String, Object>> countRestApiScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatRestApiMinuteResp_Mapper.countRestApiScanByMin, params);
    }

    /**
     *  错误码统计 REST接口
     */
    @Override
    public List<Map<String, Object>> countRestApiScanByCode(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatRestApiMinuteResp_Mapper.countRestApiCodeStat, params);
    }

    @Override
    public List<Map<String, Object>> countEccScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatIvrMinuteRecord_Mapper.countEccScanByMin, params);
    }
}
