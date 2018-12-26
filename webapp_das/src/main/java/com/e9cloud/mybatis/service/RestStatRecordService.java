package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RestStatDayRecord;

import java.util.List;
import java.util.Map;


/**
 * Created by dukai on 2016/8/23.
 */
public interface RestStatRecordService extends IBaseService{
    /** 获取月统计数据 **/
    RestStatDayRecord getMonthRestOverview(Map map);

    /** 根据日期范围获取消费概况 **/
    RestStatDayRecord getRestDayRangeRecordInfo(Map map);

    /** 根据日期范围获取客户消费Top10 **/
    PageWrapper getRestDayRangeRecordTopTen(Page page);

    /** 获取专线语音日报表信息 **/
    PageWrapper getRestDayReportList(Page page);
    /** 获取专线语音月报表信息 **/
    PageWrapper getRestMonthReportList(Page page);

    /** 获取专线语音日报表应付款总额 **/
    Map getRestDayTotalFee(Page page);
    /** 获取专线语音月报表应付款总额 **/
    Map getRestMonthTotalFee(Page page);

    /** 导出日报表信息 **/
    List<Map<String, Object>> downloadDayRestReport(Page page);
    /** 导出月报表信息 **/
    List<Map<String, Object>> downloadMonthRestReport(Page page);
}