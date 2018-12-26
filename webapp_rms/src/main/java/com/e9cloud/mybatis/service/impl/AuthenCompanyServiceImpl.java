package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/2/2.
 */
@Service
public class AuthenCompanyServiceImpl extends BaseServiceImpl implements AuthenCompanyService {


    /**
     * 根据id查询用户（公司）认证信息
     * @param autId
     * @return
     */
    @Override
    public AuthenCompany getAuthenCompanyById(Integer autId) {
        return this.findObject(Mapper.AuthenCompany_Mapper.seelctAuthenCompanyById, autId);
    }

    @Override
    public AuthenCompany selectAuthenCompanyById(Integer autId) {
        return this.findObject(Mapper.AuthenCompany_Mapper.getAuthenCompanyById, autId);
    }
    /**
     * 根据参数查询用户（公司）认证信息
     * @param authenCompany
     * @return
     */
    @Override
    public AuthenCompany getAuthenCompany(AuthenCompany authenCompany) {
        return this.findObject(Mapper.AuthenCompany_Mapper.seelctAuthenCompanyByObject, authenCompany);
    }

    /**
     * 保存公司认证信息
     *
     * @param authenCompany
     */
    @Override
    public void saveCompanyInfo(AuthenCompany authenCompany) {
        this.save(Mapper.AuthenCompany_Mapper.insertSelective, authenCompany);
    }
    /**
     * 修改公司认证信息
     *
     * @param authenCompany
     */
    @Override
    public void updateCompanyInfo(AuthenCompany authenCompany) {
        this.update(Mapper.AuthenCompany_Mapper.updateByPrimaryKeySelective, authenCompany);
    }

    /**
     * 验证company信息的合法性
     *
     * @param company 公司名称、公司证件等
     * @return
     */
    @Override
    public long countCompanyByInfo(AuthenCompany company) {
        return this.findObject(Mapper.AuthenCompany_Mapper.countCompanyByInfo, company);
    }
    @Override
    public long countCompany(AuthenCompany company) {
        return this.findObject(Mapper.AuthenCompany_Mapper.countCompany, company);
    }
}
