package com.e9cloud.redis.util;

import com.e9cloud.core.application.SpringContextHolder;

import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/7.
 */

public class RedisDBUtils {

    /**
     * 重新设置超时时间
     * @param key string
     * @param date long秒
     */
    public static void expireAt(String key, Date date){
        redis().KEYS.expireAt(key,date.getTime());
        //redis().expireAt(key, date);
    }

    /**
     * 重新设置超时时间
     * @param key string
     * @param longtimne long秒
     */
    public static void expire(String key, long longtimne){
        int second= new Long(longtimne).intValue();
        redis().expire(key,second);
        //redis().expire(key, longtimne);
    }

    /**
     * 通过keys删除
     *
     * @param keys
     */
    public static void del(String... keys){
        redis().KEYS.del(keys);
        //redis().del(keys);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public static void set(byte[] key, byte[] value, long liveTime){
        int second= new Long(liveTime).intValue();
        redis().STRINGS.setEx(key,second,value);
        //redis().set(key, value, liveTime);
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public static void set(String key, String value, long liveTime){
        int second= new Long(liveTime).intValue();
        redis().STRINGS.setEx(key,second,value);
        //redis().set(key, value, liveTime);
    }

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value){
        redis().STRINGS.set(key,value);
        //redis().set(key, value);
    }

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    public static void set(byte[] key, byte[] value){
        redis().STRINGS.set(key,value);
        //redis().set(key, value);
    }

    /**
     * 获取redis() value (String)
     *
     * @param key
     * @return
     */
    public static String get(String key){
       return redis().STRINGS.get(key);
        //return redis().get(key);
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    public static Set keys(String pattern){
        return redis().KEYS.keys(pattern);
        //return redis().keys(pattern);
    }

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key){
        return redis().KEYS.exists(key);
        //return redis().exists(key);
    }

    /**
     * 查看redis()里有多少数据
     */
//    public static long dbSize(){
//        return redis().dbSize();
//    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
//    public static String ping(){
//        return redis().ping();
//    }

    /**
     * 查询key的过期时间
     *
     * @param key
     * @return 以秒为单位的时间表示
     **/
    public static long ttl(String key) {
        return redis().KEYS.ttl(key);
        //return redis().ttl(key);
    }


    private static JedisClusterUtil redis(){
        return SpringContextHolder.getBean(JedisClusterUtil.class);
    }

}
