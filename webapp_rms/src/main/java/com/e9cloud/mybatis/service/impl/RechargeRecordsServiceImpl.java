package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RechargeRecords;
import com.e9cloud.mybatis.service.RechargeRecordsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.e9cloud.mybatis.mapper.Mapper.Recharge_Records;

/**
 * Created by Administrator on 2016/2/20.
 */
@Service
public class RechargeRecordsServiceImpl extends BaseServiceImpl implements RechargeRecordsService{

    @Override
    public void insertRechargeRecords(RechargeRecords rechargeRecords) {
        this.save(Recharge_Records.insert, rechargeRecords);
    }

    @Override
    public PageWrapper selectRechargeRecordsPage(Page page) {
        return this.page(Recharge_Records.selectRechargeRecordsPage, page);
    }

    @Override
    public List<RechargeRecords> selectRechargeRecordsDownload(Page page) {
        return this.findObjectList(Recharge_Records.selectRechargeRecordsDownload, page);
    }
}
