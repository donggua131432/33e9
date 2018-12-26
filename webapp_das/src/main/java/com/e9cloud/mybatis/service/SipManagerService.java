package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.SipRelayInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/15.
 */

public interface SipManagerService extends IBaseService {

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
     *获取中继信息列表
     * @param page
     * @return
     */
    PageWrapper getSipRelayInfoList(Page page);

    /**
     * 根据sid获取子账户信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getSipRelayInfoByMap(Map<String, Object> params);

}


