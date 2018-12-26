package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.UserService;

import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
public class UserServiceTest extends BaseTest{

    @Autowired
    private UserService userService;

    @Test
    public void testFindUserByLoginName() {

        List<User> user = userService.findlist();

        user.forEach((u)->{
            logger.info(u.getUsername());
        });

        logger.info(JSonUtils.toJSon(user));
    }

    @Test
    public void testHasAction(){
        boolean is = hasAction(2, "/ringtone/audit");
        System.out.println(is);
    }

    public boolean hasAction(Integer userId, String url) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("url", url);
        try {
            List<Map<String, Object>> actions = userService.findObjectListByMap(Mapper.User_Mapper.selectUserAction, params);
            System.out.println(JSonUtils.toJSon(actions));
            return actions.size() != 0
                    && !actions.stream().anyMatch(map -> (map.get("url").equals(url) && (Long)(map.get("hasAction")) == 0));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
