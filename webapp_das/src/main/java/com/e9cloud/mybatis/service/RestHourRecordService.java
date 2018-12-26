package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * 专线语音 -- 小时统计
 * Created by Administrator on 2016/8/24.
 */
public interface RestHourRecordService extends IBaseService {

    /**
     *  按小时统计 专线语音
     */
    List<Map<String,Object>> countRestScanByDay(Map<String, Object> params);
}
