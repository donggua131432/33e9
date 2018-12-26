package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestHourRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 专线语音 -- 小时统计
 * Created by Administrator on 2016/8/24.
 */
@Service
public class RestHourRecordServiceImpl extends BaseServiceImpl implements RestHourRecordService {

    /**
     *  按小时统计 专线语音
     */
    @Override
    public List<Map<String, Object>> countRestScanByDay(Map<String, Object> params) {
        return this.findObjectList(Mapper.RestHourRecord_Mapper.countRestScanByDay, params);
    }
}
