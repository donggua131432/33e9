package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.UserAdminService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dukai on 2016/2/4.
 */
public class UserAdminServiceTest extends BaseTest {

    @Autowired
    private UserAdminService userAdminService;

    @Test
    public void getUserAdmin(){
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setUid("13030c558d864a278a1bac88cd7a582c");

        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("用户管理主账号信息:"+resultUserAdmin.getUid()+"----------------------"+resultUserAdmin.getSid()+"--------------------------"+resultUserAdmin.getFeeid());
    }
}
