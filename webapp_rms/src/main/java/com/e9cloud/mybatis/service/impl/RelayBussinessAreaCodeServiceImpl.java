package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayBusinessAreaCode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayBussinessAreaCodeService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Administrator on 2017/1/10.
 */
@Service
public class RelayBussinessAreaCodeServiceImpl extends BaseServiceImpl implements RelayBussinessAreaCodeService {



    /**
     * 插入数据
     *
     * @param relayBusinessAreaCode
     */
    @Override
    public void saveRelayBussinessAreaCode(RelayBusinessAreaCode relayBusinessAreaCode) {
        this.save(Mapper.RelayBusinessAreaCode_Mapper.insertSelective, relayBusinessAreaCode);
    }

    /**
     * 删除数据
     * @param relayId
     */
    @Override
    public void deleteByRelayId(Integer relayId){
        this.delete(Mapper.RelayBusinessAreaCode_Mapper.deleteByRelayId, relayId);
    }

    @Override
    public List<RelayBusinessAreaCode> findListByRelayId(Integer relayId){
        return this.findObjectList(Mapper.RelayBusinessAreaCode_Mapper.findListByRelayId,relayId);
    }

}
