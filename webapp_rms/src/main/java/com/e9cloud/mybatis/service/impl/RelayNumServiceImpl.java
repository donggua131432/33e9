package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayNumPool;
import com.e9cloud.mybatis.domain.SipRelayInfo;
import com.e9cloud.mybatis.domain.SipRelayNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayNumService;
import com.e9cloud.mybatis.service.SubNumService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/21.
 */
@Service
public class RelayNumServiceImpl extends BaseServiceImpl implements RelayNumService
{

    /**
     * 获取强显号信息
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
    public List<RelayNumPool> getRelayNumberByNumber(String relayNum, String number) {
        Map map = new HashMap<String,String>();
        map.put("relayNum",relayNum);
        map.put("number",number);
        return this.findObjectListByMap(Mapper.RelayNumPool_Mapper.selectRelayNumberByNumber,map);
    }

    /**
     * 添加强显号号码池
     *
     * @param relayNumPool
     */
    @Override
    public void addRelayNumberPool(RelayNumPool relayNumPool) {
        this.save(Mapper.RelayNumPool_Mapper.insertRelayNumber,relayNumPool);
    }

    @Override
    public PageWrapper getRelayNumList(Page page) {
        return  this.page(Mapper.RelayNumPool_Mapper.getRelayNumList, page);
    }

    @Override
    public RelayNumPool getRelayNumInfoById(Integer id) {
        return this.findObject(Mapper.RelayNumPool_Mapper.selectNumByID,id);
    }

    @Override
    public void updateRelayNumber(RelayNumPool relayNumPool) {
        this.update(Mapper.RelayNumPool_Mapper.updateRelayNumber,relayNumPool);
    }

    @Override
    public void deleteRelayNumber(RelayNumPool relayNumPool) {
        this.delete(Mapper.RelayNumPool_Mapper.deleteRelayNumber,relayNumPool);
    }

    /**
     * @param relayNumPool
     * @return
     */
    @Override
    public boolean checkRelayNumUnique(RelayNumPool relayNumPool) {
        long l =  this.findObject(Mapper.RelayNumPool_Mapper.checkRelayNumUnique,relayNumPool);
        return l == 0;
    }
}
