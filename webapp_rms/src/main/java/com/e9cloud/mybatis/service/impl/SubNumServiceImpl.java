package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SubNumService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
@Service
public class SubNumServiceImpl extends BaseServiceImpl implements SubNumService
{

    /**
     * 获取子帐号信息
     *
     * @param subid
     * @return
     */
    @Override
    public SipRelayInfo getSubApp(String subid) {
        return (SipRelayInfo) this.findObjectByPara(Mapper.SubApp_Mapper.selectSubAppBySubid,subid);
    }

    /**
     * 获取号码池信息列表
     *
     * @param number
     * @return
     */
    @Override
    public SipRelayNumPool getSubNumberByNumber(String subid,String number) {
        Map map = new HashMap<String,String>();
        map.put("subid",subid);
        map.put("number",number);
        return this.findObject(Mapper.SubPool_Mapper.selectSubNumberByNumber,map);
    }

    /**
     * 获取号码池信息列表
     *
     * @param number
     * @return
     */
    @Override
    public List<SipRelayNumPool> getSubNumberByNumber(String number) {
        Map map = new HashMap<String,String>();
        map.put("number",number);
        return this.findObjectList(Mapper.SubPool_Mapper.selectSubNumberByNumber1,map);
    }

    /**
     * 添加子帐号号码池
     *
     * @param sipRelayNumPool
     */
    @Override
    public void addSubNumberPool(SipRelayNumPool sipRelayNumPool) {
        this.save(Mapper.SubPool_Mapper.insertSubNumber,sipRelayNumPool);
    }

    @Override
    public PageWrapper getSubNumberList(Page page) {
        return  this.page(Mapper.SubPool_Mapper.getSubNumberList, page);
    }

    /**
     * 获取Sip子账号号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> getSubNumberListDownload(Page page) {
        return this.download(Mapper.SubPool_Mapper.getSubNumberList, page);
    }

    @Override
    public SipRelayNumPool getSubNumInfoById(Integer id) {
        return this.findObject(Mapper.SubPool_Mapper.selectNumByID,id);
    }

    @Override
    public void updateSubNumber(SipRelayNumPool sipRelayNumPool) {
        this.update(Mapper.SubPool_Mapper.updateSubNumber,sipRelayNumPool);
    }

    @Override
    public void deleteSubNumber(SipRelayNumPool sipRelayNumPool) {
        this.delete(Mapper.SubPool_Mapper.deleteSubNumber,sipRelayNumPool);
    }

    @Override
    public void deleteSubNumberBySubid(SipRelayNumPool sipRelayNumPool) {
        this.delete(Mapper.SubPool_Mapper.deleteSubNumberBySubid,sipRelayNumPool);
    }

    /**
     * @param sipRelayNumPool
     * @return
     */
    @Override
    public boolean checkSubNumUnique(SipRelayNumPool sipRelayNumPool) {
        long l =  this.findObject(Mapper.SubPool_Mapper.checkSubNumUnique,sipRelayNumPool);
        return l == 0;
    }

    @Override
    public void deleteStatusBylink(String[] strId) {
        this.update(Mapper.SubPool_Mapper.deleteStatusBylink, strId);
    }

}
