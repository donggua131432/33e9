package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 话务总览 相关 service
 * Created by Administrator on 2016/8/23.
 */
public interface TrafficScanService extends IBaseService{

    /**
     * 按天统计 专线语音 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countRestScanByDay(Map<String, Object> params);

    /**
     * 专号通 按天统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countMaskScanByDay(Map<String, Object> params);

    /**
     * 专号通 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countMaskScanByHour(Map<String, Object> params);

    /**
     * 专号通 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countMaskScanByMin(Map<String, Object> params);

    /**
     * 智能云调度 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countCcScanByDay(Map<String, Object> params);

    /**
     * 智能云调度 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countCcScanByHour(Map<String, Object> params);

    /**
     * 智能云调度 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countCcScanByMin(Map<String, Object> params);

    /**
     * 语音通知 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceScanByDay(Map<String, Object> params);

    /**
     * 语音通知 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceScanByHour(Map<String, Object> params);

    /**
     * 语音通知 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceScanByMin(Map<String, Object> params);

    /**
     * SIP 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSipScanByDay(Map<String, Object> params);

    /**
     * SIP 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSipScanByHour(Map<String, Object> params);

    /**
     * SIP 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSipScanByMin(Map<String, Object> params);

    /**
     * 云话机 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSpScanByDay(Map<String, Object> params);

    /**
     * 云话机 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSpScanByHour(Map<String, Object> params);

    /**
     * 云话机 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countSpScanByMin(Map<String, Object> params);


    Map<String,Object> countCcScanByOperator(Map<String, Object> params);

    /**
     * 云总机 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String,Object>> countEccScanByHour(Map<String, Object> params);

    /**
     * 云总机 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String,Object>> countEccScanByDay(Map<String, Object> params);

    /**
     * 云总机 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String,Object>> countEccScanByMin(Map<String, Object> params);

    /**
     * 语音验证码 日统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceverifyScanByDay(Map<String, Object> params);

    /**
     * 语音验证码 小时统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceverifyScanByHour(Map<String, Object> params);

    /**
     * 语音验证码 分钟统计 的话务情况
     * @param params 请求
     * @return
     */
    List<Map<String, Object>> countVoiceverifyScanByMin(Map<String, Object> params);
}
