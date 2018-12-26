package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RechargeRecord;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RechargeRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/2/19.
 */
@Service
public class RechargeRecordServiceImpl extends BaseServiceImpl implements RechargeRecordService{
    /**
     * 分页查询充值记录
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageRechargeRecord(Page page) {
        return this.page(Mapper.RechargeRecord_Mapper.pageRechargeRecord, page);
    }

    /**
     * 查询所有充值记录
     * @param page 分页信息
     * @return
     */
    @Override
    public List<RechargeRecord> getRechargeRecordList(Page page) {
        return this.findObjectList(Mapper.RechargeRecord_Mapper.selectRechargeRecordList, page);
    }

}
