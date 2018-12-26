package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.CcDispatchInfo;

import java.util.List;

/**
 * Created by pengchunchen on 2016/8/10.
 */
public interface CcDispatchInfoService extends IBaseService {

    /**
     * 批量保存话务调度呼叫中心
     * @param ccDispatchInfoList
     */
    void addCcDispatchInfoList(List<CcDispatchInfo> ccDispatchInfoList);

    /**
     * 删除话务调度呼叫中心
     * @param ccDispatchInfo 话务调度呼叫中心的基本信息
     */
    void delCcDispatchInfo(CcDispatchInfo ccDispatchInfo);

    //根据调度ID查询话务调度呼叫中心
    List<CcDispatchInfo> queryListByDId(String dispatchId);

    /**
     * 校验话务调度呼叫中心存在性
     * @param ccDispatchInfo 呼叫中心的基本信息
     */
    Integer countCcDispatchInfo(CcDispatchInfo ccDispatchInfo);

    /**
     * 统计subId下的话务调度
     * @param subId
     * @return
     */
    List<CcDispatch> getDispatchBySubId(String subId);

}
