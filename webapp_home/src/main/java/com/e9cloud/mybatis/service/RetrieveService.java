package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Account;

/**
 * Created by admin on 2016/1/15.
 */
public interface RetrieveService extends IBaseService {
    /**
     * 验证是否存在
     * @param email
     * @return
     */
    String  validate(String email);

    /**
     * 修改密码
     */
    void udpatepassword(Account account);



}
