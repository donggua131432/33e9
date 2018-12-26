package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.Interest;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 *
 */
public interface AccountService extends IBaseService {

    /**
     * 得到用户包括salt和password
     *
     * @return Account
     */
    Account getAccountForAuthentication(String loginName);

    /**
     * 得到用户包括salt和password
     *
     * @return uid
     */
    Account getAccountByUid(String uid);

    /**
     * 根据Uid查询认证信息状态
     *
     * @return uid
     */
    String getAuthStatusByUid(String uid);

    /**
     * 验证邮箱是否唯一
     *
     * @param email 邮箱
     * @return boolean true 唯一，false 不唯一
     */
    boolean checkEmailUnique(String email);

    /**
     * 校验手机号是否唯一
     * @param mobile
     * @return boolean false:不唯一 true:唯一
     */
    boolean checkMobileUnique(String mobile);

    /**
     * 注册用户
     * @param account 账户信息
     */
    void saveUserForReg(Account account);

    /**
     * 根据邮箱得到用户
     * @param email 邮箱
     */
    Account getAccountByEmail(String email);

    /**
     * 查询用户基本信息
     * @param uid
     */
    User getUserByUid(String uid);

    /**
     * 查询用户基本信息及扩展信息
     * @param uid
     */
    User findUserAndExternInfoForUid(String uid);

    /**
     * 修改用户基本信息
     * @param user
     */
    void updateUserInfo(User user);

    /**
     * 修改用户外部信息
     * @param userExternInfo
     */
    void updateUserExterninfo(UserExternInfo userExternInfo);

    /**
     * 账户管理下修改用户信息
     * @param user UserExternInfo
     */
    void updateUserForAccMgr(User user,UserExternInfo userExternInfo);

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     */
    void updateAccount(Account account);

    /**
     * 保存外部用户信息
     * @param userExternInfo
     */
    void saveUserExterninfo(UserExternInfo userExternInfo);

    /**
     * 保存用户的感兴趣的产品
     * @param interest
     */
    void saveUserInterest(Interest interest);


    /**
     * 更改用户的认证状态
     * @param account 认证状态 和 UID
     */
    void updateAuthStatus(Account account);


}
