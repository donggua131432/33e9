package com.e9cloud.redis.util;

import com.e9cloud.redis.base.IRedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/4/16.
 */
@Component
public class WebAppHomeUtil {

    @Autowired
    IRedisOperator iRedisOperator;

    private static String prefix = "JSession:*";
    /**
     * 获取所有的登录AccesToken
     */
    public TreeSet getAccessTokens() {
        return iRedisOperator.keys(prefix);
    }

    /**
     * 根据uid清除用户登录生成accesToken
     */
    public void removeAccesTokenByUid(String uid){
        try{
            TreeSet hashSet = this.getAccessTokens();
            if(hashSet!=null&&hashSet.size()>0){
                Iterator it = hashSet.iterator();
                while(it.hasNext()){
                    String token = (String)it.next();
                    String value = RedisDBUtils.get(token);
                    if(value.equals(uid)){
                        RedisDBUtils.del(token);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
