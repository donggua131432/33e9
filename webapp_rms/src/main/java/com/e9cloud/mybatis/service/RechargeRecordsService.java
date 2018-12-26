package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.RechargeRecords;
import com.e9cloud.mybatis.domain.UserAdmin;

import java.util.List;

/**
 * Created by wujiang on 2016/2/18.
 */
public interface RechargeRecordsService extends IBaseService{

    /**
     * 充值记录入库
     * @param rechargeRecords 充值账号
     *
     */
    public void insertRechargeRecords(RechargeRecords rechargeRecords);

    /**
     * 查询充值记录
     * @param
     */
    public PageWrapper selectRechargeRecordsPage(Page page);

    /**
     *下载充值记录
     * @param page
     */
    public List<RechargeRecords> selectRechargeRecordsDownload(Page page);
}
