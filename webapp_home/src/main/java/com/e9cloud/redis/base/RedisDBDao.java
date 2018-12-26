package com.e9cloud.redis.base;

import java.util.Date;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/5.
 */
public interface RedisDBDao {

    /**
     * 通过keys删除
     *
     * @param keys
     */
    void del(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 设置过期时间
     *
     * @param key
     * @param liveTime
     *            单位秒
     */
    void expire(String key, long liveTime);

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     *            时间
     */
    void expireAt(String key, Date date);

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    Set keys(String pattern);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    String flushDB();

    /**
     * 查看redis里有多少数据
     */
    long dbSize();

    /**
     * 查询key的过期时间
     *  @param  key
     *  @return 以秒为单位的时间表示
     **/
    long ttl(String key);


    /**
     * 检查是否连接成功
     *
     * @return
     */
    String ping();
}
