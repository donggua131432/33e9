package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AuthenCompany;

/**
 * Created by Administrator on 2016/2/2.
 */
public interface AuthenCompanyService extends IBaseService{


    /**
     * 根据参数查询用户（公司）认证信息
     * @param id
     * @return
     */
    public AuthenCompany getAuthenCompanyById(Integer id);


    public AuthenCompany selectAuthenCompanyById(Integer id);

    /**
     * 根据参数查询用户（公司）认证信息
     * @param authenCompany
     * @return
     */
    public AuthenCompany getAuthenCompany(AuthenCompany authenCompany);

    /**
     * 保存公司认证信息
     * @param authenCompany
     */
    public void saveCompanyInfo(AuthenCompany authenCompany);

    /**
     * 修改公司认证信息
     * @param authenCompany
     */
    public void updateCompanyInfo(AuthenCompany authenCompany);


    /**
     * 验证company信息的合法性
     * @param company 公司名称、公司证件等
     * @return
     */
    long countCompanyByInfo(AuthenCompany company);

    long countCompany(AuthenCompany company);
}
