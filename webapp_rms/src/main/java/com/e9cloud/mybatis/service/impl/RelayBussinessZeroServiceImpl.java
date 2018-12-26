package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayBusinessAreaCode;
import com.e9cloud.mybatis.domain.RelayBusinessZero;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayBussinessZeroService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Administrator on 2017/1/10.
 */
@Service
public class RelayBussinessZeroServiceImpl extends BaseServiceImpl implements RelayBussinessZeroService {



    /**
     * 插入数据
     *
     * @param relayBusinessZero
     */
    @Override
    public void saveRelayBussinessZero(RelayBusinessZero relayBusinessZero) {
        this.save(Mapper.RelayBusinessZero_Mapper.insertSelective, relayBusinessZero);
    }

    /**
     * 删除数据
     * @param relayId
     */
    @Override
    public void deleteByRelayId(Integer relayId){
        this.delete(Mapper.RelayBusinessZero_Mapper.deleteByRelayId, relayId);
    }

    @Override
    public List<RelayBusinessZero> findListByRelayId(Integer relayId){
        return this.findObjectList(Mapper.RelayBusinessZero_Mapper.findListByRelayId,relayId);
    }
}
