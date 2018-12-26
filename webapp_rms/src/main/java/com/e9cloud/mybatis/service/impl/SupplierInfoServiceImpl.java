package com.e9cloud.mybatis.service.impl;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Supplier;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SupplierInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SupplierInfoServiceImpl extends BaseServiceImpl implements SupplierInfoService {
    /**
     * 根据ID查找供应商信息
     * @param id
     * @return
     */
    @Override
    public Supplier findSupplierById(String id) {
        return this.findObject(Mapper.Supplier_Mapper.selectSupplierById, id);
    }
    /**
     * 根据名称查找供应商信息
     * @param name
     * @return
     */
    @Override
    public Supplier findSupplierByName(String name) {
        return this.findObject(Mapper.Supplier_Mapper.selectSupplierByName, name);
    }

    /**
     * 保存供应商信息
     * @param Supplier 供应商信息
     */
    @Override
    public void saveSupplierInfo(Supplier Supplier) {
        this.save(Mapper.Supplier_Mapper.saveSupplier, Supplier);
    }

    /**
     * 修改客户信息
     * @param Supplier
     */
    @Override
    public void updateSupplierInfo(Supplier Supplier) {
        this.update(Mapper.Supplier_Mapper.updateSupplier, Supplier);
    }

    /**
     * 删除供应商信息
     * @param id
     */
    @Override
    public void deleteSupplierInfo(String id) {
        this.delete(Mapper.Supplier_Mapper.deleteSupplier, id);
    }

    /**
     * 分页查询供应商信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageSupplierList(Page page) {
        return this.page(Mapper.Supplier_Mapper.pageSupplierList, page);
    }

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSupplierInfo(Page page) {
        return this.download(Mapper.Supplier_Mapper.pageSupplierList, page);
    }
}
