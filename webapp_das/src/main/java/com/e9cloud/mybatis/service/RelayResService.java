package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RelayRes;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.domain.Supplier;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/31.
 */
public interface RelayResService extends IBaseService {

    /**
     * 统计资源
     * @param page 分页信息
     * @return
     */
    PageWrapper pageRecord(Page page);

    List<Map<String,Object>> downloadRecord(Page page);

    /**
     * 中继资源列表
     * @return
     */
    List<RelayRes> getAllRelayRes();

    /**
     * 得到所有的供应商
     * @return
     */
    List<Supplier> getAllSupplier();

    /**
     *
     * @return
     */
    List<SipBasic> getAllRelayByUseType(String useType);
}
