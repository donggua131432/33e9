package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.SipDayRecord;
import com.e9cloud.mybatis.domain.SipRelayInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/15.
 */

public  interface SipManagerService extends IBaseService {

    /**
     * 获取SIP应用的标准费用信息
     */
    SipRelayInfo getSipRateByFeeid(String feeid);

    /**
     * 获取sip应用信息
     * @param subid
     * @return
     */
    SipRelayInfo getSipRelayUnionRateBySubid(String subid);

    /**
     * 根据sid获取sip列表
     * @param sid
     * @return
     */
    List<SipRelayInfo> getSipRelayInfoListBySid(String sid);

    /**
     * 根据中继号获取中继基本信息
     * @param relayNum
     * @return
     */
    SipBasic getSipBasicByRelayNum(String relayNum);

    /**
     * 获取中继信息列表
     * @param page
     * @return
     */
    PageWrapper getSipRelayInfoList(Page page);

    /**
     * 获取中继号码池列表信息
     * @param page
     * @return
     */
    PageWrapper getSipRelayNumberPool(Page page);

    /**
     * 导出中继号码池
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipRelayNumberPool(Page page);

    /**
     * 获取话务统计分页信息
     * @param page
     * @return
     */
    PageWrapper getSipTrafficListPage(Page page);

    /**
     * 导出话务统计信息
     * @param page
     * @return
     */
    List<SipDayRecord> downloadSipTrafficList(Page page);


    /**
     * 获取全局号码池列表信息
     * @param page
     * @return
     */
    PageWrapper getSipAppNumberPool(Page page);

    /**
     * 导出全局号码池
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipAppNumberPool(Page page);

    /**
     * sip消费报表（日报）
     * @param page
     * @return
     */
    PageWrapper getSipDayReportList(Page page);

    /**
     * 导出sip消费报表（日报）
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipDayReportList(Page page);

    /**
     * sip消费报表（月报）
     * @param page
     * @return
     */
    PageWrapper getSipMonthReportList(Page page);

    /**
     * 导出sip消费报表（月报）
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipMonthReportList(Page page);

}


