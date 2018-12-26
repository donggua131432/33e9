package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AuthenCompanyRecords;
import com.e9cloud.mybatis.domain.UserCompany;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AuthenService;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/3/8.
 */
@Service
public class AuthenServiceImpl extends BaseServiceImpl implements AuthenService {
    /**
     * 添加认证信息基本内容
     * @param company
     * @return
     */
    @Override
    public void saveAuthToRecords(UserCompany company) {
        this.save(Mapper.UserCompany_Mapper.saveAuthToRecords, company);
    }

    /**
     * 验证税务登记号是否唯一
     *
     * @param taxregNo 邮箱
     * @return boolean true 唯一，false 不唯一
     */
    public boolean checkTaxregNoUnique(String taxregNo){
        int count = this.findObjectByPara(Mapper.UserCompany_Mapper.countUserByTaxregNo, taxregNo);
        return count == 0;
    }

    public boolean checkNameUnique(String name){
        int count = this.findObjectByPara(Mapper.UserCompany_Mapper.countUserByName, name );
        return count == 0;
    }

    /**
     * 根据信息校验公司的唯一性
     *
     * @param company
     * @return
     */
    @Override
    public boolean checkCompanyUnique(UserCompany company) {
        long l = this.findObject(Mapper.UserCompany_Mapper.countUserByCompany, company);
        return l == 0;
    }

    /**
     * 根据信息校验公司的唯一性
     *
     * @param company
     * @return
     */
    @Override
    public boolean checkCompanyUniqueForAll(UserCompany company) {
        long l = this.findObject(Mapper.UserCompany_Mapper.countUserByCompanyForAll, company);
        return l == 0;
    }

    /**
     * 根据Uid查询认证信息状态
     *
     * @param uid
     * @return uid
     */
    @Override
    public String getAuthStatusByUid(String uid) {
        return this.findObjectByPara(Mapper.UserCompany_Mapper.getAuthStatusByUid, uid);
    }



    /**
     * 认证信息查看
     * @param uid
     * @return UserCompany
     */

    @Override
    public UserCompany findAuthInfoByUid(String uid) {
        return this.findObjectByPara(Mapper.UserCompany_Mapper.findAuthInfoByUid,uid);
    }
    /**
     * 展示正在认证中的信息
     * @param uid
     * @return AuthenCompanyRecords
     */
    @Override
    public AuthenCompanyRecords findAuthingInfoByUid(String uid) {
        return this.findObjectByPara(Mapper.AuthenCompanyRecords_Mapper.findAuthingInfoByUid,uid);
    }

}
