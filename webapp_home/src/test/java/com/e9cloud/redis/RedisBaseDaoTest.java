package com.e9cloud.redis;

import com.e9cloud.base.BaseTest;
import com.e9cloud.redis.base.IRedisOperator;
import com.e9cloud.redis.base.RedisDBDao;
import com.e9cloud.redis.base.RedisOperator;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.redis.util.RedisDBUtils;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2016/1/5.
 */
public class RedisBaseDaoTest extends BaseTest{

    @Autowired
    private RedisDBDao redisDBDao;

    @Autowired
    IRedisOperator iRedisOperator;
    @Test
    public void testSet(){
        try{
            JedisClusterUtil jedisClusterUtil =  ctx.getBean(JedisClusterUtil.class);
            jedisClusterUtil.STRINGS.setEx("JSession:1234567",6000,"123456");
            jedisClusterUtil.STRINGS.setEx("JSession:12345678",6000,"123456");
            jedisClusterUtil.STRINGS.setEx("JSession:12345679",6000,"123456");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testGet(){
        try{
            JedisClusterUtil jedisClusterUtil =  ctx.getBean(JedisClusterUtil.class);
            System.out.print("---"+jedisClusterUtil.STRINGS.get("wangqing"));
            System.out.print("---"+jedisClusterUtil.KEYS.ttl("wangqing"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void getTokens(){
        JedisClusterUtil jedisClusterUtil =  ctx.getBean(JedisClusterUtil.class);
        String prefix = "JSession:*";
        Set set =  jedisClusterUtil.KEYS.keys(prefix);
        TreeSet<String> keys = iRedisOperator.keys(prefix);
        System.out.println(keys);

    }
}
