package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.Interest;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/1/7.
 */
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService {

    /**
     * 查询用户基本信息
     *
     * @param uid
     */
    @Override
    public User getUserByUid(String uid) {
        return this.findObjectByPara(Mapper.User_Mapper.selectByPrimaryKey, uid);
    }

    /**
     * 保存用户外部信息
     *
     * @param userExternInfo
     */
    @Override
    public void saveUserExterninfo(UserExternInfo userExternInfo) {
        String id = userExternInfo.getUid();
        int count = this.findObject(Mapper.User_Mapper.isExistUserExtern,id);
        if(count>0){
            User user =findUserAndExternInfoForUid(id);
            userExternInfo.setId(user.getUserExternInfo().getId());
            this.updateUserExterninfo(userExternInfo);
        }else{
            this.save(Mapper.User_Mapper.saveUserExternInfo,userExternInfo);
        }
    }

    /**
     * 查询用户基本信息及扩展信息
     *
     * @param uid
     */
    @Override
    public User findUserAndExternInfoForUid(String uid) {
        return this.findObjectByPara(Mapper.User_Mapper.selectUserAndExternInfoForUid, uid);
    }

    /**
     * 账户管理下修改用户信息
     *
     * @param user
     * @param userExternInfo
     */
    @Override
    public void updateUserForAccMgr(User user, UserExternInfo userExternInfo) {
        this.updateUserInfo(user);
        this.updateUserExterninfo(userExternInfo);
    }

    /**
     * 修改用户基本信息
     *
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {
        this.update(Mapper.User_Mapper.updateUserInfo, user);
    }

    /**
     * 修改用户外部信息
     *
     * @param userExternInfo
     */
    @Override
    public void updateUserExterninfo(UserExternInfo userExternInfo) {
        this.update(Mapper.User_Mapper.updateUserExternInfo,userExternInfo);
    }

    /**
     * 得到用户包括salt和password
     *
     * @return Account
     */
    public Account getAccountForAuthentication(String loginName){
        return this.findObjectByPara(Mapper.Account_Mapper.selectAccountForAuthentication, loginName);
    }

    /**
     * 得到用户包括salt和password
     *
     * @param uid
     * @return uid
     */
    @Override
    public Account getAccountByUid(String uid) {
        return this.findObjectByPara(Mapper.Account_Mapper.selectAccountByUid, uid);
    }



    /**
     * 根据Uid查询认证信息状态
     *
     * @param uid
     * @return uid
     */
    @Override
    public String getAuthStatusByUid(String uid) {
        return this.findObjectByPara(Mapper.Account_Mapper.getAuthStatusByUid, uid);
    }

    /**
     * 验证邮箱是否唯一
     *
     * @param email 邮箱
     * @return boolean true 唯一，false 不唯一
     */
    public boolean checkEmailUnique(String email){
        int count = this.findObjectByPara(Mapper.User_Mapper.countUserByEmail, email);
        return count == 0;
    }

    /**
     * 校验手机号是否唯一
     *
     * @param mobile
     * @return boolean false:不唯一 true:唯一
     */
    @Override
    public boolean checkMobileUnique(String mobile) {
        int count = this.findObjectByPara(Mapper.User_Mapper.countUserByMobile, mobile);
        return count == 0;
    }

    /**
     * 注册用户
     * @param account 账户信息
     */
    public void saveUserForReg(Account account) {
        this.save(Mapper.Account_Mapper.insertUserForReg, account);
        //初始化sys_type表
        this.save(Mapper.Account_Mapper.insertUserForBusType, account);
    }

    /**
     * 注册用户(初始化sys_type表)
     * @param account 账户信息
    public void saveUserForBusType(Account account) {

    }*/

    /**
     * 根据邮箱得到用户
     * @param email 邮箱
     */
    public Account getAccountByEmail(String email) {
        return this.findObjectByPara(Mapper.Account_Mapper.selectUserByEmail ,email);
    }

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     */
    public void updateAccount(Account account){
        this.update(Mapper.Account_Mapper.updateAccountByPrimaryKeySelective, account);
    }

    /**
     * 保存用户的感兴趣的产品
     * @param interest
     */
    public void saveUserInterest(Interest interest) {
        this.save(Mapper.Interest_Mapper.insert, interest);
    }

    /**
     * 更改用户的认证状态
     * @param account 认证状态
     */
    public void updateAuthStatus(Account account) {
        this.update(Mapper.Account_Mapper.updateAuthStatus, account);
    }
}
