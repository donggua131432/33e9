package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * rest业务，话务统计-分钟统计
 * Created by Administrator on 2016/10/10.
 */
public interface RestStatMinuteRecordService extends IBaseService {

    /**
     *  分钟统计 专线语音
     */
    List<Map<String,Object>> countRestScanByMin(Map<String, Object> params);

    /**
     *  错误码统计 专线语音
     */
    List<Map<String,Object>> countRestResponseByMin(Map<String, Object> params);

}
