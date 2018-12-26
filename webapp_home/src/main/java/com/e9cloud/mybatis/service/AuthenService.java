package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/3/8.
 */

public  interface AuthenService extends IBaseService {

    /**
     * 添加认证信息
     * @param company
     * @return
     */
    public void saveAuthToRecords(UserCompany company);


    /**
     * 验证税务登记号是否唯一
     *
     * @param taxregNo 邮箱
     * @return boolean true 唯一，false 不唯一
     */
    boolean checkTaxregNoUnique(String taxregNo);

    boolean checkNameUnique(String name);

    /**
     * 根据信息校验公司的唯一性
     * @param company
     * @return
     */
    boolean checkCompanyUnique(UserCompany company);

    /**
     * 根据信息校验公司的唯一性
     * @param company
     * @return
     */
    boolean checkCompanyUniqueForAll(UserCompany company);
    /**
     * 根据Uid查询认证信息状态
     *
     * @return uid
     */
    String getAuthStatusByUid(String uid);


    /**
     * 认证信息查看
     * @param uid
     * @return UserCompany
     */
    public UserCompany findAuthInfoByUid(String uid);

    /**
     * 展示正在认证中的信息
     * @param uid
     * @return UserCompany
     */
    public AuthenCompanyRecords findAuthingInfoByUid(String uid);
}
