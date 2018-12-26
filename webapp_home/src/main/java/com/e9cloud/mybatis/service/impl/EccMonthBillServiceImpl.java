package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccMonthBillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/2/14.
 */
@Service
public class EccMonthBillServiceImpl extends BaseServiceImpl implements EccMonthBillService {
    /**
     * 云总机月账单
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageMonthEccBill(Page page) {
        return this.page(Mapper.EccMonthBill_Mapper.pageMonthEccBill, page);
    }

    @Override
    public List<Map<String, Object>> downloadEccMonthBill(Map<String,Object> map) {
        return this.findObjectListByMap(Mapper.EccMonthBill_Mapper.downloadEccMonthBill, map);
    }

    /**
     * 导出云总机消费统计-月单明细
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadEccMonthBillRecordList(Page page) {
        return this.download(Mapper.EccMonthBill_Mapper.downloadEccMonthBillRecordList, page);
    }
}
