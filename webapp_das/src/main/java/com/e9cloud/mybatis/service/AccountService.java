package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    public User findAccountByLoginName(String loginName){
        return userService.findAccountByLoginName(loginName);
    }

}
