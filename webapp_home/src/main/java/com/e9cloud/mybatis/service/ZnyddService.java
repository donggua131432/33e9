package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.StafRecord;
import java.util.List;
import java.util.Map;

/**
 * 智能云调度相关处理
 * Created by wzj on 2016/3/11.
 */
public interface ZnyddService extends IBaseService {

    /**
     * 分页查询通话记录
     * @param page
     * @return
     */
    PageWrapper pageRecentCall(Page page);

    /**
     * 插入通话记录 用于测试
     * @param record
     */
    void insertStafRecordSelective(StafRecord record);

    /**
     * 得到月结账单
     * @param map
     * @return
     */
    List<Map<String,Object>> procMonth(Map<String, Object> map);

    /**
     * 导出月结账单
     * @param map
     * @return
     */
    List<Map<String,Object>> downloadProcMonth(Map<String, Object> map);

    /**
     * 按照呼叫中心统计消费情况  （按月）
     * @return
     */
    List<Map<String,Object>> getMonthRecordByCallCenter(Map<String, Object> map);

    /**
     * 按照呼叫中心统计消费情况  (按天)
     * @return
     */
    List<Map<String,Object>> getDayRecordByCallCenter(Map<String, Object> map);

    /**
     * 统计每天的消费情况
     * @return
     */
    List<Map<String,Object>> getMonthRecordByDay(Map<String, Object> map);

    /**
     * 统计小时的消费情况
     * @return
     */
    List<Map<String,Object>> getDayRecordByHour(Map<String, Object> map);

    /**
     * 得到一个月的消费总额
     * @param map
     * @return
     */
    String getMonthRecordTotal(Map<String, Object> map);

    /**
     * 得到一天的消费总额
     * @param map
     * @return
     */
    String getDayRecordTotal(Map<String, Object> map);

    /**
     * 分页统计呼入信息
     * @param page
     * @return
     */
    PageWrapper pageCallInt(Page page);

    /**
     * 分页统计呼出信息
     * @param page
     * @return
     */
    PageWrapper pageCallOut(Page page);

    /**
     *  接通数
     * @param map
     * @return
     */
    List<Map<String, Object>> getCallConnByDay(Map<String, Object> map);

    /**
     * 呼入呼出总量
     *
     * @return
     */
    List<Map<String, Object>> getCallInOutByDay(Map<String, Object> map);

    /**
     * 统计呼叫中心的呼叫量
     * @param map
     * @return
     */
    List<Map<String, Object>> countAllByCC(Map<String, Object> map);
}
