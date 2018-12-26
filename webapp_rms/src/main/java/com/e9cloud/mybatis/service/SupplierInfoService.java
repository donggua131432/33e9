package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Supplier;

import java.util.List;
import java.util.Map;

/**
 * 供应商管理
 * Created by wy on 2016/7/8.
 */
public interface SupplierInfoService extends IBaseService {

    /**
     * 根据ID查找供应商信息
     * @param id
     * @return SupplierManager
     */
    Supplier findSupplierById(String id);
    /**
     * 根据名称查找供应商信息
     * @param cname
     * @return SupplierManager
     */
    Supplier findSupplierByName(String cname);

    /**
     * 保存供应商信息
     * @param Supplier 供应商信息
     */
    void saveSupplierInfo(Supplier Supplier);
    /**
     * 修改供应商信息
     */
    void updateSupplierInfo(Supplier Supplier);

    /**
     * 删除供应商信息
     */
    void deleteSupplierInfo(String id);

    /**
     * 分页查询供应商信息
     * @param page
     * @return
     */
    PageWrapper pageSupplierList(Page page);

    /**
     * 导出供应商信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSupplierInfo(Page page);

}
