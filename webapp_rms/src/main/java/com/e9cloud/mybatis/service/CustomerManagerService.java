package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CustomerManager;
import com.e9cloud.mybatis.domain.NumberBlack;

import java.util.List;
import java.util.Map;

/**
 * 客户管理
 * Created by dukai on 2016/7/8.
 */
public interface CustomerManagerService extends IBaseService {

    /**
     * 根据ID查找客户信息
     * @param id
     * @return CustomerManager
     */
    CustomerManager findCustomerById(String id);

    CustomerManager findCustomerByAccountId(String accountId);

    /**
     * 保存客户信息
     * @param customerManager 客户信息
     */
    void saveCustomerManager(CustomerManager customerManager);

    /**
     * 修改客户信息
     */
    void updateCustomerManager(CustomerManager customerManager);

    /**
     * 删除客户信息
     */
    void deleteCustomerManager(String id);

    /**
     * 分页查询客户信息
     * @param page
     * @return
     */
    PageWrapper pageCustomerList(Page page);

    /**
     * 导出客户信息
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadCustomerInfo(Page page);
}
