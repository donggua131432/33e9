package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RechargeRecord;
import com.e9cloud.mybatis.domain.User;

import java.util.List;

/**
 * Created by admin on 2016/2/19.
 */
public interface RechargeRecordService extends IBaseService{

    /**
     * 分页查询充值记录
     * @param page 分页信息
     * @return
     */
    PageWrapper pageRechargeRecord(Page page);


    /**
     * 查询导出充值记录
     * @param page 查询信息
     * @return
     */
    List<RechargeRecord> getRechargeRecordList(Page page);



}
