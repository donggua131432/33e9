package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestStatMinuteRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * rest业务，话务统计-分钟统计
 * Created by Administrator on 2016/10/10.
 */
@Service
public class RestStatMinuteRecordServiceImpl extends BaseServiceImpl implements RestStatMinuteRecordService {

    /**
     * 分钟统计 专线语音
     *
     * @param params
     */
    @Override
    public List<Map<String, Object>> countRestScanByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.RestStatMinuteRecord_Mapper.countRestScanByMin, params);
    }

    /**
     * 错误码统计 专线语音
     *
     * @param params
     */
    @Override
    public List<Map<String, Object>> countRestResponseByMin(Map<String, Object> params) {
        return this.findObjectList(Mapper.StatRestResponse_Mapper.countRestResponseByMin, params);
    }
}
