package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/6.
 */
public interface RevenueReportService extends IBaseService {

    /**
     * 营收日统计
     * @param page
     * @return
     */
    PageWrapper pageDays(Page page);

    /**
     * 营收月统计
     * @param page
     * @return
     */
    PageWrapper pageMonth(Page page);

    /**
     * rest月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String, Object>> restMonthDetails(Map<String, Object> params);

    /**
     * mask月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String, Object>> maskMonthDetails(Map<String, Object> params);

    /**
     * sip月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String, Object>> sipMonthDetails(Map<String, Object> params);

    /**
     * cc月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String, Object>> ccMonthDetails(Map<String, Object> params);

    /**
     * sp月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String, Object>> spMonthDetails(Map<String, Object> params);

    /**
     * ecc月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String,Object>> eccMonthDetails(Map<String, Object> params);

    /**
     * voiceVerify月详细
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String,Object>> voiceVerifyMonthDetails(Map<String, Object> params);

    /**
     * 得到各业务的月租
     * @param params ym:月份“yyyy-MM” feeid:计费id
     * @return
     */
    List<Map<String,Object>> getRents(Map<String, Object> params);

    Map<String,Object> monthByFeeid(Map<String, Object> params);

    List<Map<String,Object>> downloadDays(Page page);

    List<Map<String,Object>> downloadMonth(Page page);

}
