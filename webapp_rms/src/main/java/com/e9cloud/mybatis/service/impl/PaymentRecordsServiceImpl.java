package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.PaymentRecords;
import com.e9cloud.mybatis.service.PaymentRecordsService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.e9cloud.mybatis.mapper.Mapper.Payment_Records;

/**
 * Created by Administrator on 2016/2/20.
 */
@Service
public class PaymentRecordsServiceImpl extends BaseServiceImpl implements PaymentRecordsService {

    @Override
    public void insertPaymentRecords(PaymentRecords paymentRecords) {
        this.save(Payment_Records.insert, paymentRecords);
    }

    @Override
    public PageWrapper selectPaymentRecordsPage(Page page) {
        return this.page(Payment_Records.selectPaymentRecordsPage, page);
    }

    @Override
    public List<PaymentRecords> selectPaymentRecordsDownload(Page page) {
        return this.findObjectList(Payment_Records.selectPaymentRecordsDownload, page);
    }
}
