package com.e9cloud.redis.base;

import com.e9cloud.core.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/1/5.
 */
@Service
public class RedisDBDaoImpl implements RedisDBDao{

    private static String redisCode = "utf-8";

  //  @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询key的过期时间
     *
     * @param key
     * @return 以秒为单位的时间表示
     **/
    @Override
    public long ttl(String key) {
       return redisTemplate.getExpire(key);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     */
    @Override
    public void expireAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param liveTime seconds秒
     */
    @Override
    public void expire(String key, long liveTime) {
        redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);

    }

    /**
     * 通过keys删除
     *
     * @param keys
     */
    public void del(String... keys) {
        List<String> ks = new ArrayList<>();

        for (String key : keys) {
            ks.add(key);
        }
        redisTemplate.delete(ks);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.set(key, value);
            if (liveTime > 0) {
                connection.expire(key, liveTime);
            }
            return 1L;
        });
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return (String) redisTemplate.execute((RedisConnection connection) -> {
            try {
                if (connection.get(key.getBytes()) != null) {
                    return new String(connection.get(key.getBytes()), redisCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        });
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    public String flushDB() {
        return (String) redisTemplate.execute((RedisConnection connection) -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 查看redis里有多少数据
     */
    public long dbSize() {
        return (long) redisTemplate.execute((RedisConnection connection) -> connection.dbSize());
    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
    public String ping() {
        return (String) redisTemplate.execute((RedisConnection connection) -> connection.ping());
    }

}
