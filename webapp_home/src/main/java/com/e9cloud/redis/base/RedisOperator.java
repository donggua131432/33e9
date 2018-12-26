package com.e9cloud.redis.base;


import com.e9cloud.redis.util.JedisClusterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/4/16.
 */

    @Service
    public class RedisOperator implements IRedisOperator {

        @Autowired
        private JedisClusterFactory jedisClusterFactory;

        @Override
        public TreeSet<String> keys(String pattern){
            TreeSet<String> keys = new TreeSet<>();
            Map<String, JedisPool> clusterNodes = getJedisCluster().getClusterNodes();
            for(String k : clusterNodes.keySet()){
                JedisPool jp = clusterNodes.get(k);
                Jedis connection =null;
                try {
                    connection = jp.getResource();
                    Set set = connection.keys(pattern);
                    keys.addAll(set);
                } catch(Exception e){
                    e.printStackTrace();
                } finally{
                    if(connection!=null){
                        connection.close();//用完一定要close这个链接！！！
                    }
                }
            }
            return keys;
        }
    public JedisCluster getJedisCluster() {
        return jedisClusterFactory.getObject();
    }

}
