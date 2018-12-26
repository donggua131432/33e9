package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayBusinessZero;

import java.util.List;

/**
 * 中继--0前缀业务相关Service
 * Created by Administrator on 2017/1/7.
 */
public interface RelayBussinessZeroService extends IBaseService {



    /**
     * 添加
     * @param relayBusinessZero
     * @return
     */
    public  void saveRelayBussinessZero(RelayBusinessZero relayBusinessZero);

    /**
     * 根据relayId删除
     * @param relayId
     */
    void deleteByRelayId(Integer relayId);

    /**
     * 根据relayId查找0前缀业务
     * @param relayId
     * @return
     */
    List<RelayBusinessZero> findListByRelayId(Integer relayId);

}
