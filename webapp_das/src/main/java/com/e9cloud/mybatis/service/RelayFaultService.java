package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayFault;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/8/18.
 */
public interface RelayFaultService extends IBaseService{

    /**
     * 根据年份进行中继线路故障统计
     * @param page 登录名
     * @return list
     */
    PageWrapper getRelayFaultList(Page page);

    List<Map<String, Object>> relayFaultList(Page page);

    List<RelayFault> getRelayFault(Map map);
}
