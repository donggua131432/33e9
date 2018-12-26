package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayFault;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayFaultService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
public class RelayFaultServiceImpl extends BaseServiceImpl implements RelayFaultService {

    /**
     * 根据登录名查找用户
     *
     * @param page 登录名
     * @return list
     */
    @Override
    public PageWrapper getRelayFaultList(Page page) {
        return this.page(Mapper.RelayFault_Mapper.getRelayFaultList, page);
    }

    @Override
    public List<Map<String, Object>> relayFaultList(Page page) {
        return this.findObjectList(Mapper.RelayFault_Mapper.relayFaultList, page);
    }

    @Override
    public List<RelayFault> getRelayFault(Map map) {
        return this.findObjectListByMap(Mapper.RelayFault_Mapper.getRelayFault, map);
    }
}
