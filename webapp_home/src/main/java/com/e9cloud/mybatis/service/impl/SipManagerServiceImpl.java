package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.SipDayRecord;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/6/1.
 */
@Service
public class SipManagerServiceImpl extends BaseServiceImpl implements SipManagerService {

    /**
     *获取SIP应用的标准费用信息
     * @param feeid
     * @return
     */
    @Override
    public SipRelayInfo getSipRateByFeeid(String feeid) {
        return this.findObject(Mapper.SipRelayInfo_Mapper.selectSipRateByFeeid, feeid);
    }

    /**
     * 获取sip应用信息
     * @param subid
     * @return
     */
    @Override
    public SipRelayInfo getSipRelayUnionRateBySubid(String subid) {
        return this.findObject(Mapper.SipRelayInfo_Mapper.selectSipRelayUnionRateBySubid, subid);
    }

    /**
     * 根据sid获取sip列表
     * @param sid
     * @return
     */
    @Override
    public List<SipRelayInfo> getSipRelayInfoListBySid(String sid) {
        return this.findObjectList(Mapper.SipRelayInfo_Mapper.selectSipRelayInfoListBySid, sid);
    }

    /**
     * 根据中继号获取中继基本信息
     * @param relayNum
     * @return
     */
    @Override
    public SipBasic getSipBasicByRelayNum(String relayNum) {
        return this.findObject(Mapper.SipBasic_Mapper.selectSipBasicByRelayNum, relayNum);
    }

    /**
     *获取中继信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipRelayInfoList(Page page) {
        return this.page(Mapper.SipRelayInfo_Mapper.selectSipRelayInfoByObj, page);
    }

    /**
     * 获取中继号码池列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipRelayNumberPool(Page page) {
        return this.page(Mapper.SipRelayNumPool_Mapper.selectSipRelayNumPoolPage, page);
    }

    /**
     * 下载中继号码池
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipRelayNumberPool(Page page) {
        return this.download(Mapper.SipRelayNumPool_Mapper.selectSipRelayNumPoolPage, page);
    }


    /**
     * 获取话务统计分页信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipTrafficListPage(Page page) {
        return this.page(Mapper.SipDayRecord_Mapper.selectSipDayRecordPage, page);
    }

    /**
     * 导出话务统计信息
     * @param page
     * @return
     */
    @Override
    public List<SipDayRecord> downloadSipTrafficList(Page page) {
        return this.findObjectList(Mapper.SipDayRecord_Mapper.selectSipDayRecordList, page);
    }

    /**
     * 获取全局号码池分页信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipAppNumberPool(Page page) {
        return this.page(Mapper.SipAppNumPool_Mapper.selectSipAppNumPoolPage, page);
    }

    /**
     * 导出全局号码池
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipAppNumberPool(Page page) {
        return this.download(Mapper.SipAppNumPool_Mapper.selectSipAppNumPoolPage, page);
    }

    /**
     * sip消费报表（日报）
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipDayReportList(Page page) {
        return this.page(Mapper.SipDayRecord_Mapper.selectSipDayReportPage, page);
    }

    /**
     * 导出sip消费报表（日报）
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipDayReportList(Page page) {
        return this.download(Mapper.SipDayRecord_Mapper.selectSipDayReportPage, page);
    }

    /**
     * sip消费报表（月报）
     * @param page
     * @return
     */
    @Override
    public PageWrapper getSipMonthReportList(Page page) {
        return this.page(Mapper.SipDayRecord_Mapper.selectSipMonthReportPage, page);
    }

    /**
     * 导出sip消费报表（月报）
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipMonthReportList(Page page) {
        return this.download(Mapper.SipDayRecord_Mapper.selectSipMonthReportPage, page);
    }

}
