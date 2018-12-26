package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RevenueReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/6.
 */
@Service
public class RevenueReportServiceImpl extends BaseServiceImpl implements RevenueReportService {

    /**
     * 营收日统计
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageDays(Page page) {
        return this.page(Mapper.RevenueReport_Mapper.pageDaysCount, Mapper.RevenueReport_Mapper.pageDays, page);
    }

    /**
     * 营收月统计
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageMonth(Page page) {
        return this.page(Mapper.RevenueReport_Mapper.pageMonthCount, Mapper.RevenueReport_Mapper.pageMonth, page);
    }

    /**
     * rest月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> restMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.restMonthDetails, params);
    }

    /**
     * mask月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> maskMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.maskMonthDetails, params);
    }

    /**
     * sip月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> sipMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.sipMonthDetails, params);
    }

    /**
     * cc月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> ccMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.ccMonthDetails, params);
    }

    /**
     * sp月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> spMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.spMonthDetails, params);
    }

    /**
     * ecc月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> eccMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.eccMonthDetails, params);
    }


    /**
     *  voiceVerify月详细
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>>  voiceVerifyMonthDetails(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.voiceVerifyMonthDetails, params);
    }


    /**
     * 得到各业务的月租
     *
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    @Override
    public List<Map<String, Object>> getRents(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.RevenueReport_Mapper.selectRents, params);
    }

    @Override
    public Map<String, Object> monthByFeeid(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.RevenueReport_Mapper.monthByFeeid, params);
    }

    @Override
    public List<Map<String, Object>> downloadDays(Page page) {
        return this.findObjectList(Mapper.RevenueReport_Mapper.downloadDays, page);
    }

    @Override
    public List<Map<String, Object>> downloadMonth(Page page) {
        return this.findObjectList(Mapper.RevenueReport_Mapper.downloadMonth, page);
    }
}
