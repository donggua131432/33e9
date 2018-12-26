package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CallCenter;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.Province;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CallCenterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 呼叫中心
 * Created by wzj on 2016/3/8.
 */
@Service
public class CallCenterServiceImpl extends BaseServiceImpl implements CallCenterService {

    /**
     * 得到所有的呼叫中心
     *
     * @param callCenter
     * @return
     */
    @Override
    public List<CallCenter> getAllCallCenter(CallCenter callCenter) {
        return this.findObjectList(Mapper.CallCenter_Mapper.selectAll, callCenter);
    }

    @Override
    public List<Province> getAllProvice(Province province) {
        return this.findObjectList(Mapper.Province_Mapper.selectAll, province);
    }
    @Override
    public List<CityAreaCode> getAllCity(CityAreaCode cityAreaCode) {
        return this.findObjectList(Mapper.Province_Mapper.selectAllCity, cityAreaCode);
    }


    @Override
    public List<CcInfo> getAllCallCenterInfo(CcInfo ccInfo) {
        return this.findObjectList(Mapper.CcInfo_Mapper.selectAll, ccInfo);
    }

    @Override
    public List<CcInfo> getAllCallCenterInfo1(CcInfo ccInfo) {
        return this.findObjectList(Mapper.CcInfo_Mapper.selectAll1, ccInfo);
    }

    /**
     * 根据条件获取呼叫中心i信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getCcInfoByMap(Map<String, Object> params) {
        return this.findObjectList(Mapper.CcInfo_Mapper.selectCcInfoBySid, params);
    }

    /**
     * 选择所有的呼叫中心包括已禁用的
     * @param sid
     * @return
     */
    @Override
    public List<CcInfo> getAllCallCenterInfoWithDelete(String sid) {
        return this.findObjectList(Mapper.CcInfo_Mapper.selectAllWithDelete, sid);
    }

    /**
     * 分页查询呼叫中心信息
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageCallList(Page page) {
        return this.page(Mapper.CcInfo_Mapper.pageCallList, page);
    }

    /**
     * 是否缺省调度
     * @param sid
     * @return
     */
    @Override
    public Integer checkDefaultCc(String sid) {
        return this.findObject(Mapper.CcInfo_Mapper.checkDefaultCc, sid);
    }

    @Override
    public CcInfo getCcInfoByDefault(String sid) {
        return this.findObjectByPara(Mapper.CcInfo_Mapper.getCcInfoByDefault, sid);
    }

    @Override
    public void updateDefault(CcInfo ccInfo) {
         this.update(Mapper.CcInfo_Mapper.updateDefault, ccInfo);
    }

    @Override
    public void updateDefault2(CcInfo ccInfo) {
        this.update(Mapper.CcInfo_Mapper.updateDefault2, ccInfo);
    }

    @Override
    public void setDefault(CcInfo ccInfo) {
        this.update(Mapper.CcInfo_Mapper.setDefault, ccInfo);
    }
}
