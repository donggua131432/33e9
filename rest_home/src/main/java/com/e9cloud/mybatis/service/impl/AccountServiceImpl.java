package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.util.InitValue;
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
     * 查询用户基本信息
     *
     * @param sid
     */
    @Override
    public User getUserBySid(String sid) {
        //先从缓存容器中加载，不存在或超时再查数据库
        User user= InitValue.userCache.get(sid);
        if(user == null){
            user = this.findObjectByPara(Mapper.User_Mapper.selectBySid, sid);
            //放入缓存容器，一分钟有效
            InitValue.userCache.put(sid,user,60);
        }
        return user;
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
     * 修改用户基本信息
     *
     * @param user
     */
    @Override
    public void updateUserInfo(User user) {
        this.update(Mapper.User_Mapper.updateUserInfo, user);
    }



}
