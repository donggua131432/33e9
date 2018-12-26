package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.PaymentRecords;
import com.e9cloud.mybatis.domain.RechargeRecords;

import java.util.List;

/**
 * Created by wujiang on 2016/2/18.
 */
public interface PaymentRecordsService extends IBaseService{

    /**
     * 扣费记录入库
     * @param paymentRecords 充值账号
     *
     */
    public void insertPaymentRecords(PaymentRecords paymentRecords);

    /**
     * 查询充值记录
     * @param
     */
    public PageWrapper selectPaymentRecordsPage(Page page);

    /**
     * 下载充值记录
     * @param page
     */
    public List<PaymentRecords> selectPaymentRecordsDownload(Page page);
}
