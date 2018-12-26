package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;

/**
 *
 */
public interface AccountService extends IBaseService {


    /**
     * 查询用户基本信息
     * @param uid
     */
    User getUserByUid(String uid);

    /**
     * 查询用户基本信息
     * @param sid
     */
    User getUserBySid(String sid);

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


}
