package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayBusinessAreaCode;

import java.util.List;

/**
 * 中继--区号业务相关Service
 * Created by Administrator on 2017/1/7.
 */
public interface RelayBussinessAreaCodeService extends IBaseService {



    /**
     * 添加
     * @param relayBusinessAreaCode
     * @return
     */
    public  void saveRelayBussinessAreaCode(RelayBusinessAreaCode relayBusinessAreaCode);

    /**
     * 根据relayId删除
     * @param relayId
     */
    void deleteByRelayId(Integer relayId);

    /**
     * 根据relayId查找区号业务
     * @param relayId
     * @return
     */
    List<RelayBusinessAreaCode> findListByRelayId(Integer relayId);

}
