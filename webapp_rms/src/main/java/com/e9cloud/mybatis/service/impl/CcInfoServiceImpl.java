package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CcInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CcInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by pengchunchen on 2016/8/5.
 */
@Service
public class CcInfoServiceImpl extends BaseServiceImpl implements CcInfoService {
    /**
     * 保存呼叫中心
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public void saveCcInfo(CcInfo ccInfo) {
        this.save(Mapper.CcInfo_Mapper.saveCcInfo, ccInfo);
    }

    /**
     * 删除呼叫中心
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public void delCcInfo(CcInfo ccInfo){
        this.delete(Mapper.CcInfo_Mapper.delCcInfo, ccInfo);
    }

    /**
     * 修改呼叫中心
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public void updateCcInfo(CcInfo ccInfo) {
        this.update(Mapper.CcInfo_Mapper.updateCcInfo, ccInfo);
    }

    /**
     * 修改呼叫中心状态
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public void updateCcInfoStatus(CcInfo ccInfo) {
        this.update(Mapper.CcInfo_Mapper.updateCcInfoStatus, ccInfo);
    }

    /**
     * 修改呼叫中心默认
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public void updateCcInfodefaultCc(CcInfo ccInfo) {
        this.update(Mapper.CcInfo_Mapper.updateCcInfodefaultCc, ccInfo);
    }

    /**
     * 根据id查找一条呼叫中心信息
     * @param id
     */
    @Override
    public CcInfo queryCcInfoById(String id){
        return this.findObjectByPara(Mapper.CcInfo_Mapper.queryCcInfoById, id);
    }

    /**
     * 校验呼叫中心存在性
     * @param ccInfo 呼叫中心的基本信息
     */
    @Override
    public Integer countCcInfo(CcInfo ccInfo) {
        return this.findObject(Mapper.CcInfo_Mapper.countCcInfo, ccInfo);
    }
    
    /**
     * 分页选取智能云调度呼叫中心列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageCcInfoList(Page page) {
        return this.page(Mapper.CcInfo_Mapper.pageCcInfoList, page);
    }

    /**
     * 导出呼叫中心
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCcInfo(Page page) {
        return this.download(Mapper.CcInfo_Mapper.pageCcInfoList, page);
    }

    /**
     * 根据sid查找呼叫中心列表
     * @param sid
     */
    @Override
    public List<CcInfo> queryCcInfoBySid(String sid){
        return this.findObjectListByPara(Mapper.CcInfo_Mapper.queryCcInfoBySid, sid);
    }


}
