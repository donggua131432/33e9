package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.CcDispatchInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CcDispatchInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengchunchen on 2016/8/10.
 */
@Service
public class CcDispatchInfoServiceImpl extends BaseServiceImpl implements CcDispatchInfoService {
    /**
     * 批量保存话务调度呼叫中心
     * @param ccDispatchInfoList 话务调度呼叫中心的基本信息
     */
    @Override
    public void addCcDispatchInfoList(List<CcDispatchInfo> ccDispatchInfoList) {
        this.save(Mapper.CcDispatchInfo_Mapper.addCcDispatchInfoList, ccDispatchInfoList);
    }

    /**
     * 删除话务调度呼叫中心
     * @param ccDispatchInfo 话务调度呼叫中心的基本信息
     */
    @Override
    public void delCcDispatchInfo(CcDispatchInfo ccDispatchInfo) {
        this.update(Mapper.CcDispatchInfo_Mapper.delCcDispatchInfo, ccDispatchInfo);
    }

    /**
     * 根据调度ID查询话务调度呼叫中心
     *
     * @return list
     */
    @Override
    public List<CcDispatchInfo> queryListByDId(String dispatchId) {
        return this.findObjectListByPara(Mapper.CcDispatchInfo_Mapper.queryListByDId, dispatchId);
    }

    /**
     * 校验话务调度呼叫中心存在性
     * @param ccDispatchInfo 呼叫中心的基本信息
     */
    @Override
    public Integer countCcDispatchInfo(CcDispatchInfo ccDispatchInfo) {
        return this.findObject(Mapper.CcDispatchInfo_Mapper.countCcDispatchInfo, ccDispatchInfo);
    }

    /**
     * 统计subId下的话务调度
     *
     * @return list
     */
    @Override
    public List<CcDispatch> getDispatchBySubId(String subId) {
        return this.findObjectListByPara(Mapper.CcDispatch_Mapper.getDispatchBySubId, subId);
    }

}
