package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.CcDispatchInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by pengchunchen on 2016/8/8.
 */
public interface CcDispatchService extends IBaseService {

    /**
     * 保存话务调度
     * @param ccDispatch 
     */
    void saveCcDispatch(CcDispatch ccDispatch,List<CcDispatchInfo> ccDispatchInfoList);

    /**
     * 修改话务调度
     * @param ccDispatch 话务调度的基本信息
     */
    void updateCcDispatch(CcDispatch ccDispatch,CcDispatchInfo ccDispatchInfo,List<CcDispatchInfo> ccDispatchInfoList);

    /**
     * 删除话务调度
     * @param ccDispatch 话务调度的基本信息
     */
    void deleteCcDispatch(CcDispatch ccDispatch);

    /**
     * 根据id查找一条话务调度信息
     * @param id
     */
    CcDispatch queryCcDispatchById(String id);

    /**
     * 分页选取话务调度列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageCcDispatchList(Page page);

    /**
     * 导出话务调度
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCcDispatch(Page page);

    //查询所有调度区域
    PageWrapper queryCcAreaList1(Page page);
    PageWrapper queryCcAreaList(Page page);

    /**
     * 得到公司名和sid下拉列表
     * @param page
     * @return
     */
    PageWrapper getCompanyNameAndSidByPage(Page page);

    /**
     * 统计sid下某个名称的的个数
     * @param ccDispatch
     * @return
     */
    long countDispatchBySidAndDispatchName(CcDispatch ccDispatch);

    /**
     * 统计areaid下的话务调度
     * @param areaId
     * @return
     */
    List<CcDispatch> getDispatchByAreaId(String areaId);

}
