package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.StafRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ZnyddService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 智能云调度相关处理
 * Created by wzj on 2016/3/11.
 */
@Service
public class ZnyddServiceImpl extends BaseServiceImpl implements ZnyddService {
    /**
     * 分页查询通话记录
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageRecentCall(Page page) {
        return this.page(Mapper.StafRecord_Mapper.pageRecentCall ,page);
    }

    /**
     * 插入通话记录 用于测试
     *
     * @param record
     */
    @Override
    public void insertStafRecordSelective(StafRecord record) {
        this.save(Mapper.StafRecord_Mapper.insertSelective, record);
    }

    /**
     * 得到月结账单
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> procMonth(Map<String,Object> map) {
        return this.findObjectListByMap(Mapper.StatCcDayRecord_Mapper.procMonth, map);
    }

    /**
     * 导出月结账单
     * @param map
     * @return
     */

    @Override
    public List<Map<String, Object>> downloadProcMonth(Map<String,Object> map) {
        return this.findObjectListByMap(Mapper.StatCcDayRecord_Mapper.downloadProcMonth, map);
    }

    /**
     * 按照呼叫中心统计消费情况 （按月）
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getMonthRecordByCallCenter(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StatCcDayRecord_Mapper.getMonthRecordByCallCenter, map);
    }

    /**
     * 按照呼叫中心统计消费情况 （按天）
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getDayRecordByCallCenter(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StatCcHourRecord_Mapper.getDayRecordByCallCenter, map);
    }


    /**
     * 统计每天的消费情况
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getMonthRecordByDay(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StatCcDayRecord_Mapper.getMonthRecordByDay, map);
    }

    /**
     * 统计小时的消费情况
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getDayRecordByHour(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StatCcHourRecord_Mapper.getDayRecordByHour, map);
    }

    /**
     * 得到一个月的消费总额
     *
     * @param map
     * @return
     */
    @Override
    public String getMonthRecordTotal(Map<String, Object> map) {
        return this.findObjectByMap(Mapper.StatCcDayRecord_Mapper.getMonthRecordTotal, map);
    }

    /**
     * 得到一天的消费总额
     *
     * @param map
     * @return
     */
    @Override
    public String getDayRecordTotal(Map<String, Object> map) {
        return this.findObjectByMap(Mapper.StatCcHourRecord_Mapper.getDayRecordTotal, map);
    }

    /**
     * 分页统计呼入信息
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCallInt(Page page) {
        return this.page(Mapper.StafRecord_Mapper.pageCallIn, page);
    }

    /**
     * 分页统计呼出信息
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCallOut(Page page) {
        return this.page(Mapper.StafRecord_Mapper.pageCallOut, page);
    }

    /**
     * 接通数
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getCallConnByDay(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StafRecord_Mapper.countAllConnByDay , map);
    }

    /**
     * 呼入呼出总量
     *
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> getCallInOutByDay(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StafRecord_Mapper.countAllInOutByDay , map);
    }

    /**
     * 统计呼叫中心的呼叫量
     * @param map
     * @return
     */
    @Override
    public List<Map<String, Object>> countAllByCC(Map<String, Object> map) {
        return this.findObjectListByMap(Mapper.StafRecord_Mapper.countAllByCC, map);
    }
}
