package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.AuthenCompanyRecords;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AuthenCompanyRecordsService;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/2.
 */
@Service
public class AuthenCompanyRecordsServiceImpl extends BaseServiceImpl implements AuthenCompanyRecordsService {

    /**
     * 根据id查询用户（公司）认证信息
     * @param id
     * @return
     */
    @Override
    public AuthenCompanyRecords getAuthenCompanyRecordsById(Integer id) {
        return this.findObject(Mapper.AuthenCompanyRecords_Mapper.selectAuthenCompanyRecordsById, id);
    }

    /**
     * 分页查询用户（公司）认证信息
     * @param page
     * @return
     */
    @Override
    public PageWrapper getAuthenCompanyRecordsPage(Page page) {
        return this.page(Mapper.AuthenCompanyRecords_Mapper.selectAuthenCompanyRecordsPage, page);
    }

    @Override
    public List<Map<String, Object>> getPageAuthenCompanyRecords(Page page) {
        return this.findObjectList(Mapper.AuthenCompanyRecords_Mapper.getPageAuthenCompanyRecords, page);
    }

    @Override
    public void updateStatus(AuthenCompanyRecords authenCompanyRecords) {
        this.update(Mapper.AuthenCompanyRecords_Mapper.updateByPrimaryKeySelective, authenCompanyRecords);
    }
    /**
     * 保存公司认证信息
     *
     * @param ac
     */
    @Override
    public void saveRecordInfo(AuthenCompanyRecords ac) {
        this.save(Mapper.AuthenCompanyRecords_Mapper.insertSelective, ac);
    }
}
