package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CustomerManager;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CustomerManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/7/6.
 */
@Service
public class CustomerManagerServiceImpl extends BaseServiceImpl implements CustomerManagerService {

    /**
     * 根据ID查找客户信息
     * @param id
     * @return
     */
    @Override
    public CustomerManager findCustomerById(String id) {
        return this.findObject(Mapper.CustomerManager_Mapper.selectCustomerManagerById, id);
    }

    /**
     * 根据AccountId 查找客户信息
     * @param accountId
     * @return
     */
    @Override
    public CustomerManager findCustomerByAccountId(String accountId) {
        return this.findObject(Mapper.CustomerManager_Mapper.selectCustomerManagerByAccountId, accountId);
    }

    /**
     * 保存客户信息
     * @param customerManager 客户信息
     */
    @Override
    public void saveCustomerManager(CustomerManager customerManager) {
        this.save(Mapper.CustomerManager_Mapper.saveCustomerManager, customerManager);
    }

    /**
     * 修改客户信息
     * @param customerManager
     */
    @Override
    public void updateCustomerManager(CustomerManager customerManager) {
        this.update(Mapper.CustomerManager_Mapper.updateCustomerManager, customerManager);
    }

    /**
     * 删除客户信息
     * @param id
     */
    @Override
    public void deleteCustomerManager(String id) {
        this.delete(Mapper.CustomerManager_Mapper.deleteCustomerManager, id);
    }

    /**
     * 分页查询客户信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageCustomerList(Page page) {
        return this.page(Mapper.CustomerManager_Mapper.pageCustomerManagerList, page);
    }

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadCustomerInfo(Page page) {
        return this.download(Mapper.CustomerManager_Mapper.pageCustomerManagerList, page);
    }
}
