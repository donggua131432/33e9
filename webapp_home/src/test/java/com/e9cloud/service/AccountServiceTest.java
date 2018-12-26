package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.HelloService;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * test for AccountService
 */
public class AccountServiceTest extends BaseTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testGetAccountForAuthentication(){

        Account account = accountService.getAccountForAuthentication("zhongjun0218@126.com");

        logger.info("============================= " + account.getSid() + " =============================");

    }

    @Test
    @Rollback(false)
    public void testSelectUserAndExterinInfoForUid(){
//        User user = new User();//accountService.selectUserAndExternInfoForUid("123456aaaaadd");
//        logger.info("============================= " + user.getUid() + " =============================");
//        user.setUid("123456aaaaadd");
//        user.setEmail("wangqing1111@qq.com");
//        accountService.updateUserInfo(user);
        UserExternInfo userExternInfo = new UserExternInfo();
        userExternInfo.setId(123);
        userExternInfo.setQq("810191903");
        userExternInfo.setAddress("深圳市南山区科技园");
        accountService.updateUserExterninfo(userExternInfo);
    }

    @Test
    @Rollback(false)
    public void testSender() {
        User u = new User();
        u.setUid("e951343d43924150b385e0b9167965aa");
        Sender.sendMessage(u, TempCode.modify_email, null);
    }


}
