package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RestStafDayRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestStafDayRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/2.
 */
@Service
public class RestStafDayRecordServiceImpl extends BaseServiceImpl implements RestStafDayRecordService {

    /**
     * 根据条件获取统计信息
     * @param restStafDayRecord
     * @return
     */
    @Override
    public List<RestStafDayRecord> getRestStafDayRecordByObj(RestStafDayRecord restStafDayRecord) {
        return this.findObjectList(Mapper.RestStafDayRecord_Mapper.selectRestStafDayRecordByObj, restStafDayRecord);
    }

    /**
     * 近期消费走势（月消费走势）
     * @param restStafDayRecord
     * @return
     */
    @Override
    public List<RestStafDayRecord> getConsumeTrendView(RestStafDayRecord restStafDayRecord) {
        return this.findObjectList(Mapper.RestStafDayRecord_Mapper.selectConsumeTrendView, restStafDayRecord);
    }

    /**
     * 应用消费概况
     * @param restStafDayRecord
     * @return
     */
    @Override
    public List<RestStafDayRecord> getConsumeSurveyView(RestStafDayRecord restStafDayRecord) {
        return this.findObjectList(Mapper.RestStafDayRecord_Mapper.selectConsumeSurveyView, restStafDayRecord);
    }

    /**
     * 获取每个月的消费总额
     * @param restStafDayRecord
     * @return
     */
    @Override
    public RestStafDayRecord getMonthConsumeTotal(RestStafDayRecord restStafDayRecord) {
        return this.findObject(Mapper.RestStafDayRecord_Mapper.selectMonthConsumeTotal, restStafDayRecord);
    }

    /**
     * 获取每天的消费总额
     * @param map
     * @return
     */
    @Override
    public BigDecimal getRestDayConsumeTotal(Map map) {
        return (BigDecimal) this.findObjectByMap(Mapper.RestStafDayRecord_Mapper.selectRestDayConsumeTotal, map);
    }

    /**
     * 获取数据日报信息
     * @param map
     * @return
     */
    @Override
    public List<RestStafDayRecord> getDataAnalysisList(Map map) {
        return this.findObjectListByMap(Mapper.RestStafDayRecord_Mapper.getDataAnalysisList, map);
    }

    @Override
    public int getDataAnalysisCount(Map map) {
        return (Integer)this.findObjectByMap(Mapper.RestStafDayRecord_Mapper.getDataAnalysisCount, map);
    }
}
