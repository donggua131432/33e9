package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayRes;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.Supplier;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayResService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2017/3/31.
 */
@Service
public class RelayResServiceImpl extends BaseServiceImpl implements RelayResService {

    /**
     * 按天统计资源
     *
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageRecord(Page page) {
        return this.page(Mapper.StatRelayResDayRecord_Mapper.countRecord, Mapper.StatRelayResDayRecord_Mapper.pageRecord, page);
    }

    @Override
    public List<Map<String, Object>> downloadRecord(Page page) {
        return this.download(Mapper.StatRelayResDayRecord_Mapper.pageRecord, page);
    }

    /**
     * 中继资源列表
     *
     * @return
     */
    @Override
    public List<RelayRes> getAllRelayRes() {
        return this.findObjectList(Mapper.RelayRes_Mapper.selectAllRelayRes, null);
    }

    /**
     * 得到所有的供应商
     *
     * @return
     */
    @Override
    public List<Supplier> getAllSupplier() {
        return this.findObjectList(Mapper.Supplier_Mapper.selectAllSupplier, null);
    }

    /**
     * @return
     */
    @Override
    public List<SipBasic> getAllRelayByUseType(String useType) {
        return this.findObjectListByPara(Mapper.SipBasic_Mapper.selectAllRelay, useType);
    }
}
