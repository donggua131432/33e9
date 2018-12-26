package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CallCenter;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.domain.Province;

import java.util.List;

/**
 * 呼叫中心
 * Created by wzj on 2016/3/8.
 */
public interface CallCenterService extends IBaseService {

    /**
     * 得到所有的呼叫中心
     * @param callCenter
     * @return
     */
    List<CallCenter> getAllCallCenter(CallCenter callCenter);

    List<Province> getAllProvice(Province province);

    List<CcInfo> getAllCallCenterInfo(String sid);

    List<CcInfo> getAllCallCenterInfo1(String sid);

    /**
     * 选择所有的呼叫中心包括已禁用的
     * @param sid
     * @return
     */
    List<CcInfo> getAllCallCenterInfoWithDelete(String sid);

    /**
     * 得到所有的呼叫中心
     * @param page
     * @return
     */
    PageWrapper pageCallList(Page page);

    /**
     * 是否缺省调度
     * @param sid
     * @return
     */
    Integer checkDefaultCc(String sid);

    CcInfo getCcInfoByDefault(String sid);

    void updateDefault(CcInfo ccInfo);
    void updateDefault2(CcInfo ccInfo);

    void setDefault(CcInfo ccInfo);



}
