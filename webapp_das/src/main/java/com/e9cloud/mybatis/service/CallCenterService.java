package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CallCenter;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.Province;

import java.util.List;
import java.util.Map;

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
    List<CityAreaCode> getAllCity(CityAreaCode cityAreaCode);

    List<CcInfo> getAllCallCenterInfo(CcInfo ccInfo);

    List<CcInfo> getAllCallCenterInfo1(CcInfo ccInfo);

    /**
     * 根据条件获取呼叫中心信息
     * @param params
     * @return
     */
    List<Map<String,Object>> getCcInfoByMap(Map<String, Object> params);

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
