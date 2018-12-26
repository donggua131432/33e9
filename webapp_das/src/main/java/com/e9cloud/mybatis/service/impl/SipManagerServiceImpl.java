package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
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
        return this.findObjectListByPara(Mapper.SipRelayInfo_Mapper.selectSipRelayInfoListBySid, sid);
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
     * 根据条件获取中继信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getSipRelayInfoByMap(Map<String, Object> params) {
        return this.findObjectList(Mapper.SipRelayInfo_Mapper.selectSipRelayInfoBySid, params);
    }
}
