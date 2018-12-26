package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RestStafDayRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/3/2.
 */
public interface RestStafDayRecordService extends IBaseService {

    /**
     * 根据条件获取统计信息
     * @param restStafDayRecord
     * @return
     */
    List<RestStafDayRecord> getRestStafDayRecordByObj(RestStafDayRecord restStafDayRecord);


    /**
     * 近期消费走势（月消费走势）
     * @param restStafDayRecord
     * @return
     */
    List<RestStafDayRecord> getConsumeTrendView(RestStafDayRecord restStafDayRecord);

    /**
     * 应用消费概况
     * @param restStafDayRecord
     * @return
     */
    List<RestStafDayRecord> getConsumeSurveyView(RestStafDayRecord restStafDayRecord);

    /**
     * 获取每个月的消费总额
     * @param restStafDayRecord
     * @return
     */
    RestStafDayRecord getMonthConsumeTotal(RestStafDayRecord restStafDayRecord);

    /**
     * 获取每天的消费总额
     * @param map
     * @return
     */
    BigDecimal getRestDayConsumeTotal(Map map);

    /**
     * 获取数据日报
     * @param map
     * @return
     */
    List<RestStafDayRecord> getDataAnalysisList(Map map);

    int getDataAnalysisCount(Map map);
}
