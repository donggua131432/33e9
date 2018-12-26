package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dukai on 2016/2/2.
 */
public class AuthenCompanyServiceTest extends BaseTest {
    @Autowired
    private AuthenCompanyService authenCompanyService;

    @Test
    public void testGetAuthenCompany(){
        AuthenCompany ac = new AuthenCompany();
        ac.setId(1);
        ac.setUid("13030c558d864a278a1bac88cd7a582c");
        //System.out.println("-----------------"+ac.getId());
        AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(ac);
        System.out.println("用户（公司）认证信息："+authenCompany.getUid()+"------------"+authenCompany.getName());

        AuthenCompany authenCompany1 = authenCompanyService.getAuthenCompanyById(1);
        System.out.println("用户（公司）认证信息："+authenCompany1.getUid()+"------------"+authenCompany1.getName());
    }

    @Test
    public void testCompanyToPinYin(){
        
    }
}
