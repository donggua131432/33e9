package com.e9cloud.redis;

import com.e9cloud.redis.base.RedisOperator;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TreeSet;

/**
 * Created by Administrator on 2016/1/5.
 */
public class RedisBaseDaoTest extends BaseTest {

    @Autowired
    private RedisOperator redisOperator;

    @Test
    public void testSet() {
        try {
            JedisClusterUtil jedisClusterUtil = ctx.getBean(JedisClusterUtil.class);
            jedisClusterUtil.STRINGS.setEx("wangqing", 60, "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGet() {
        try {
            JedisClusterUtil jedisClusterUtil = ctx.getBean(JedisClusterUtil.class);
            System.out.print("---" + jedisClusterUtil.STRINGS.get("c1_b6f55ae1e5344782bccf03a8fc526460_201604181737"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void testGetMaskNum() {
        try {
            String key = new StringBuffer("mask*").append("020").append("*").toString();
            TreeSet<String> set = redisOperator.keys(key);
            for (String str : set) {
                System.out.println("------"+str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
