package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RetrieveService;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/1/15.
 */
@Service
public class RetrieveServiceimpl extends BaseServiceImpl implements RetrieveService {

    @Override
    public String validate(String email) {
        return this.findObjectByPara(Mapper.User_Mapper.selectuser,email);
    }

    @Override
    public void udpatepassword(Account account) {
        this.update(Mapper.User_Mapper.updatepassword,account);
    }


}
