package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/2/14.
 */
public interface EccMonthBillService extends IBaseService {

    /**
     * 云总机月账单
     * @param page
     * @return
     */
    public PageWrapper pageMonthEccBill(Page page);

    /**
     * 导出月结账单
     * @param map
     * @return
     */
    List<Map<String,Object>> downloadEccMonthBill(Map<String, Object> map);

    /**
     * 导出云总机消费统计-月单明细
     * @param page
     * @return
     */
    public List<Map<String, Object>>  downloadEccMonthBillRecordList(Page page);

}
