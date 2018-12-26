package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CcDispatch;
import com.e9cloud.mybatis.domain.CcDispatchInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CcDispatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by pengchunchen on 2016/8/8.
 */
@Service
public class CcDispatchServiceImpl extends BaseServiceImpl implements CcDispatchService {
    /**
     * 保存话务调度
     * @param ccDispatch 话务调度的基本信息
     */
    @Override
    public void saveCcDispatch(CcDispatch ccDispatch,List<CcDispatchInfo> ccDispatchInfoList) {
        this.save(Mapper.CcDispatch_Mapper.saveCcDispatch, ccDispatch);
        this.save(Mapper.CcDispatchInfo_Mapper.addCcDispatchInfoList, ccDispatchInfoList);
    }

    /**
     * 修改话务调度
     * @param ccDispatch 话务调度的基本信息
     */
    @Override
    public void updateCcDispatch(CcDispatch ccDispatch,CcDispatchInfo ccDispatchInfo,List<CcDispatchInfo> ccDispatchInfoList) {
        this.update(Mapper.CcDispatchInfo_Mapper.delCcDispatchInfo, ccDispatchInfo);
        this.update(Mapper.CcDispatch_Mapper.updateCcDispatch, ccDispatch);
        this.save(Mapper.CcDispatchInfo_Mapper.addCcDispatchInfoList, ccDispatchInfoList);
    }

    /**
     * 删除话务调度
     * @param ccDispatch 话务调度的基本信息
     */
    @Override
    public void deleteCcDispatch(CcDispatch ccDispatch) {
        this.update(Mapper.CcDispatch_Mapper.updateCcDispatch, ccDispatch);
        this.delete(Mapper.CcDispatchInfo_Mapper.delCcDispatchInfo, ccDispatch);
    }

    /**
     * 根据id查找一条话务调度信息
     * @param id
     */
    @Override
    public CcDispatch queryCcDispatchById(String id){
        return this.findObjectByPara(Mapper.CcDispatch_Mapper.queryCcDispatchById, id);
    }

    /**
     * 分页选取智能云调度话务调度列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageCcDispatchList(Page page) {
        return this.page(Mapper.CcDispatch_Mapper.pageCcDispatchList, page);
    }

    /**
     * 导出话务调度
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcDispatch(Page page) {
        return this.download(Mapper.CcDispatch_Mapper.pageCcDispatchList, page);
    }

    /**
     * 查询所有省份
     *
     * @return list
     */
    @Override
    public PageWrapper queryCcAreaList1(Page page) {
        return this.page(Mapper.CcArea_Mapper.queryCcAreaList1, page);
    }
    @Override
    public PageWrapper queryCcAreaList(Page page) {
        String sid = page.getParams().get("sid").toString();
        if(Tools.isNullStr(sid)){
            return null;
        }else{
            return this.page(Mapper.CcArea_Mapper.queryCcAreaList, page);
        }
    }

    /**
     * 得到公司名和sid下拉列表
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper getCompanyNameAndSidByPage(Page page) {
        return this.page(Mapper.CcDispatch_Mapper.findCompanyNameAndSidByPage, page);
    }

    /**
     * 统计sid下某个调度名称的的个数
     *
     * @param ccDispatch
     * @return
     */
    @Override
    public long countDispatchBySidAndDispatchName(CcDispatch ccDispatch) {
        return this.findObject(Mapper.CcDispatch_Mapper.countDispatchBySidAndDispatchName, ccDispatch);
    }

    /**
     * 统计areaid下的话务调度
     *
     * @return list
     */
    @Override
    public List<CcDispatch> getDispatchByAreaId(String areaId) {
        return this.findObjectListByPara(Mapper.CcDispatch_Mapper.getDispatchByAreaId, areaId);
    }

}
