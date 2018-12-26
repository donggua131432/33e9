package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RestStatDayRecord;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RestStatRecordService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by dukai on 2016/8/23.
 */
@Service
public class RestStatRecordServiceImpl extends BaseServiceImpl implements RestStatRecordService {

    /**
     * 获取月统计数据
     * @param map
     * @return
     */
    @Override
    public RestStatDayRecord getMonthRestOverview(Map map) {
        return this.findObject(Mapper.RestStatDayRecord_Mapper.selectMonthRestOverview, map);
    }

    /**
     * 根据日期范围获取消费概况
     * @param map
     * @return
     */
    @Override
    public RestStatDayRecord getRestDayRangeRecordInfo(Map map) {
        return this.findObject(Mapper.RestStatDayRecord_Mapper.selectRestDayRangeRecordInfo, map);
    }

    /**
     * 根据日期范围获取客户消费Top10
     * @param page
     * @return
     */
    @Override
    public PageWrapper getRestDayRangeRecordTopTen(Page page) {
        return this.page(Mapper.RestStatDayRecord_Mapper.selectRestDayRangeRecordTopTen, page);
    }


    /**
     * 获取专线语音日报表信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getRestDayReportList(Page page) {
        return this.page(Mapper.RestStatDayRecord_Mapper.selectRestRecordDayReportList, page);
    }

    /**
     * 获取专线语音月报表信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getRestMonthReportList(Page page) {
        return this.page(Mapper.RestStatDayRecord_Mapper.selectRestRecordMonthReportList, page);
    }


    /**
     * 获取专线语音日报表应付款总额
     * @param page
     * @return
     */
    @Override
    public Map getRestDayTotalFee(Page page) {
        return this.findObject(Mapper.RestStatDayRecord_Mapper.selectRestRecordDayTotalFee, page);
    }

    /**
     * 获取专线语音月报表应付款总额
     * @param page
     * @return
     */
    @Override
    public Map getRestMonthTotalFee(Page page) {
        return this.findObject(Mapper.RestStatDayRecord_Mapper.selectRestRecordMonthTotalFee, page);
    }

    /**
     * 导出日报表信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadDayRestReport(Page page) {
        return this.download(Mapper.RestStatDayRecord_Mapper.selectRestRecordDayReportList, page);
    }

    /**
     * 导出月报表信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadMonthRestReport(Page page) {
        return this.download(Mapper.RestStatDayRecord_Mapper.selectRestRecordMonthReportList, page);
    }
}
