package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.AuthenCompanyRecords;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/2.
 */
public interface AuthenCompanyRecordsService extends IBaseService{
    /**
     * 根据参数查询用户（公司）认证信息
     * @param id
     * @return
     */
    public AuthenCompanyRecords getAuthenCompanyRecordsById(Integer id);

    /**
     * 分页查询用户（公司）认证信息
     * @param page
     * @return
     */
    public PageWrapper getAuthenCompanyRecordsPage(Page page);

    List<Map<String, Object>> getPageAuthenCompanyRecords(Page page);

    /**
     * 更改（公司）认证信息的审核状态
     * @param authenCompanyRecords
     * @return
     */
    public void updateStatus(AuthenCompanyRecords authenCompanyRecords);

    /**
     * 保存公司认证信息
     * @param ac
     */
    public void saveRecordInfo(AuthenCompanyRecords ac);

}
