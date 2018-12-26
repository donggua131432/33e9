package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * rest业务，话务统计-分钟统计
 * Created by dukai on 2016/12/06.
 */
public interface TrafficMonitorService extends IBaseService {

    /**
     *  分钟统计 专号通
     */
    List<Map<String,Object>> countMaskScanByMin(Map<String, Object> params);

    /**
     *  分钟统计 语音通知
     */
    List<Map<String,Object>> countVoiceScanByMin(Map<String, Object> params);

    /**
     *  分钟统计 SIP
     */
    List<Map<String,Object>> countSipScanByMin(Map<String, Object> params);


    /**
     *  分钟统计 SipPhone 云话机
     */
    List<Map<String,Object>> countSipPhoneScanByMin(Map<String, Object> params);

    /**
     *  分钟统计 智能云调度
     */
    List<Map<String,Object>> countCloudScanByMin(Map<String, Object> params);

    /**
     *  省份统计 智能云调度
     */
    List<Map<String,Object>> countCloudScanByProv(Map<String, Object> params);

    /**
     *  分钟统计 REST接口
     */
    List<Map<String,Object>> countRestApiScanByMin(Map<String, Object> params);


    /**
     *  错误码统计 REST接口
     */
    List<Map<String,Object>> countRestApiScanByCode(Map<String, Object> params);

    /**
     *  分钟统计 云总机
     */
    List<Map<String,Object>> countEccScanByMin(Map<String, Object> params);
}
