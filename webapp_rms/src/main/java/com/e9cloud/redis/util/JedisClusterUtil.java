package com.e9cloud.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/1/12.
 */
@Component
public class JedisClusterUtil {

    /**
     * 缓存生存时间
     */
    private final int expire = 60000;

    /**
     * 操作Key的方法
     */
    public Keys KEYS = new Keys();

    /**
     * 对存储结构为String类型的操作
     */
    public Strings STRINGS = new Strings();

    /**
     * 对存储结构为List类型的操作
     */
    public Lists LISTS = new Lists();

    /**
     * 对存储结构为Set类型的操作
     */
    public Sets SETS = new Sets();

    /**
     * 对存储结构为HashMap类型的操作
     */
    public Hash HASH = new Hash();

    /**
     * 对存储结构为Set(排序的)类型的操作
     */
    public SortSet SORTSET = new SortSet();

    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    /**
     * 从jedis连接池中获取获取jedis对象
     *
     * @return
     */
    public JedisCluster getJedisCluster() {
        return jedisClusterFactory.getObject();
    }

    /**
     * 回收jedis
     *
     * @param jedis
     */
    public void returnJedis(JedisCluster jedis) {
      // jedisClusterFactory.
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     * @author ruan 2013-4-11
     */
    public  void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        JedisCluster jedisCluster = getJedisCluster();
        jedisCluster.expire(key, seconds);
        returnJedis(jedisCluster);
    }

    /**
     * 设置默认过期时间
     *
     * @param key
     * @author ruan 2013-4-11
     */
    public void expire(String key) {
        expire(key, expire);
    }

    // *******************************************Keys*******************************************//
    public class Keys {

        /**
         * 清空所有key
         */
        public String flushAll() {
            JedisCluster jedisCluster = getJedisCluster();
            String stata = jedisCluster.flushAll();
            returnJedis(jedisCluster);
            return stata;
        }


        /**
         * 更改key
         *
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public String rename(String oldkey, String newkey) {
            return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
        }

        /**
         * 更改key,仅当新key不存在时才执行
         *
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public long renamenx(String oldkey, String newkey) {
            JedisCluster jedisCluster = getJedisCluster();
            long status = jedisCluster.renamenx(oldkey, newkey);
            returnJedis(jedisCluster);
            return status;
        }

        /**
         * 更改key
         *
         * @param oldkey
         * @param newkey
         * @return 状态码
         */
        public String rename(byte[] oldkey, byte[] newkey) {
            JedisCluster jedisCluster = getJedisCluster();
            String status = jedisCluster.rename(oldkey, newkey);
            returnJedis(jedisCluster);
            return status;
        }

        /**
         * 设置key的过期时间，以秒为单位
         *
         * @param key
         * @param seconds 时间
         *                ,已秒为单位
         * @return 影响的记录数
         */
        public long expired(String key, int seconds) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.expire(key, seconds);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 设置key的过期时间,它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00，格里高利历）的偏移量。
         *
         * @param key
         * @param timestamp 时间
         *                  ,已秒为单位
         * @return 影响的记录数
         */
        public long expireAt(String key, long timestamp) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.expireAt(key, timestamp);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 查询key的过期时间
         *
         * @param key
         * @return 以秒为单位的时间表示
         */
        public long ttl(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.ttl(key);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 取消对key过期时间的设置
         *
         * @param key
         * @return 影响的记录数
         */
        public long persist(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.persist(key);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param keys
         * @return 删除的记录数
         */
        public long del(String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.del(keys);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 删除keys对应的记录,可以是多个key
         *
         * @param keys
         * @return 删除的记录数
         */
        public long del(byte[]... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.del(keys);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 判断key是否存在
         *
         * @param key
         * @return boolean
         */
        public boolean exists(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            boolean exis = jedisCluster.exists(key);
            returnJedis(jedisCluster);
            return exis;
        }

        /**
         * 对List,Set,SortSet进行排序,如果集合数据较大应避免使用这个方法
         *
         * @param key
         * @return List<String> 集合的全部记录
         **/
        public List<String> sort(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> list = jedisCluster.sort(key);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 对List,Set,SortSet进行排序或limit
         *
         * @param key
         * @param parame SortingParams 定义排序类型或limit的起止位置.
         * @return List<String> 全部或部分记录
         **/
        public List<String> sort(String key, SortingParams parame) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> list = jedisCluster.sort(key, parame);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 返回指定key存储的类型
         *
         * @param key
         * @return String string|list|set|zset|hash
         **/
        public String type(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            String type = jedisCluster.type(key);
            returnJedis(jedisCluster);
            return type;
        }

        /**
         * 查找所有匹配给定的模式的键
         *
         * @param pattern String
         *                key的表达式,*表示多个，？表示一个
         */
        public Set<String> keys(String pattern) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.hkeys(pattern);
            returnJedis(jedisCluster);
            return set;
        }
    }

    // *******************************************Sets*******************************************//
    public class Sets {

        /**
         * 向Set添加一条记录，如果member已存在返回0,否则返回1
         *
         * @param key
         * @param member
         * @return 操作码, 0或1
         */
        public long sadd(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.sadd(key, member);
            returnJedis(jedisCluster);
            return s;
        }

        public long sadd(byte[] key, byte[] member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.sadd(key, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 获取给定key中元素个数
         *
         * @param key
         * @return 元素个数
         */
        public long scard(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.scard(key);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 返回从第一组和所有的给定集合之间的差异的成员
         *
         * @param keys
         * @return 差异的成员集合
         */
        public Set<String> sdiff(String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.sdiff(keys);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 这个命令等于sdiff,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
         *
         * @param newkey 新结果集的key
         * @param keys   比较的集合
         * @return 新集合中的记录数
         **/
        public long sdiffstore(String newkey, String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.sdiffstore(newkey, keys);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 返回给定集合交集的成员,如果其中一个集合为不存在或为空，则返回空Set
         *
         * @param keys
         * @return 交集成员的集合
         **/
        public Set<String> sinter(String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.sinter(keys);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 这个命令等于sinter,但返回的不是结果集,而是将结果集存储在新的集合中，如果目标已存在，则覆盖。
         *
         * @param newkey 新结果集的key
         * @param keys   比较的集合
         * @return 新集合中的记录数
         **/
        public long sinterstore(String newkey, String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.sinterstore(newkey, keys);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 确定一个给定的值是否存在
         *
         * @param key
         * @param member 要判断的值
         * @return 存在返回1，不存在返回0
         **/
        public boolean sismember(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            boolean s = jedisCluster.sismember(key, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 返回集合中的所有成员
         *
         * @param key
         * @return 成员集合
         */
        public Set<String> smembers(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.smembers(key);
            returnJedis(jedisCluster);
            return set;
        }

        public Set<byte[]> smembers(byte[] key) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<byte[]> set = jedisCluster.smembers(key);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 将成员从源集合移出放入目标集合 <br/>
         * 如果源集合不存在或不包哈指定成员，不进行任何操作，返回0<br/>
         * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
         *
         * @param srckey 源集合
         * @param dstkey 目标集合
         * @param member 源集合中的成员
         * @return 状态码，1成功，0失败
         */
        public long smove(String srckey, String dstkey, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.smove(srckey, dstkey, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 从集合中随机删除并返回一个成员
         *
         * @param key
         * @return 被删除的成员
         */
        public String spop(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            String s = jedisCluster.spop(key);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 从集合中删除指定成员
         *
         * @param key
         * @param member 要删除的成员
         * @return 状态码，成功返回1，成员不存在返回0
         */
        public long srem(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.srem(key, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 合并多个集合并返回合并后的结果，合并后的结果集合并不保存<br/>
         *
         * @param keys
         * @return 合并后的结果集合
         * @see
         */
        public Set<String> sunion(String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.sunion(keys);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 合并多个集合并将合并后的结果集保存在指定的新集合中，如果新集合已经存在则覆盖
         *
         * @param newkey 新集合的key
         * @param keys   要合并的集合
         **/
        public long sunionstore(String newkey, String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.sunionstore(newkey, keys);
            returnJedis(jedisCluster);
            return s;
        }
    }

    // *******************************************SortSet*******************************************//
    public class SortSet {

        /**
         * 向集合中增加一条记录,如果这个值已存在，这个值对应的权重将被置为新的权重
         *
         * @param key
         * @param score  权重
         * @param member 要加入的值，
         * @return 状态码 1成功，0已存在member的值
         */
        public long zadd(String key, double score, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.zadd(key, score, member);
            returnJedis(jedisCluster);
            return s;
        }

        public long zadd(String key, Map<String, Double> scoreMembers) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.zadd(key, scoreMembers);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 获取集合中元素的数量
         *
         * @param key
         * @return 如果返回0则集合不存在
         */
        public long zcard(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.zcard(key);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 获取指定权重区间内集合的数量
         *
         * @param key
         * @param min 最小排序位置
         * @param max 最大排序位置
         */
        public long zcount(String key, double min, double max) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.zcount(key, min, max);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 获得set的长度
         *
         * @param key
         * @return
         */
        public long zlength(String key) {
            long len = 0;
            Set<String> set = zrange(key, 0, -1);
            len = set.size();
            return len;
        }

        /**
         * 权重增加给定值，如果给定的member已存在
         *
         * @param key
         * @param score  要增的权重
         * @param member 要插入的值
         * @return 增后的权重
         */
        public double zincrby(String key, double score, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            double s = jedisCluster.zincrby(key, score, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 返回指定位置的集合元素,0为第一个元素，-1为最后一个元素
         *
         * @param key
         * @param start 开始位置(包含)
         * @param end   结束位置(包含)
         * @return Set<String>
         */
        public Set<String> zrange(String key, int start, int end) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.zrange(key, start, end);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 返回指定权重区间的元素集合
         *
         * @param key
         * @param min 上限权重
         * @param max 下限权重
         * @return Set<String>
         */
        public Set<String> zrangeByScore(String key, double min, double max) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.zrangeByScore(key, min, max);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 获取指定值在集合中的位置，集合排序从低到高
         *
         * @param key
         * @param member
         * @return long 位置
         * @see
         */
        public long zrank(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long index = jedisCluster.zrank(key, member);
            returnJedis(jedisCluster);
            return index;
        }

        /**
         * 获取指定值在集合中的位置，集合排序从高到低
         *
         * @param key
         * @param member
         * @return long 位置
         */
        public long zrevrank(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long index = jedisCluster.zrevrank(key, member);
            returnJedis(jedisCluster);
            return index;
        }

        /**
         * 从集合中删除成员
         *
         * @param key
         * @param member
         * @return 返回1成功
         */
        public long zrem(String key, String member) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.zrem(key, member);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 删除
         *
         * @param key
         * @return
         */
        public long zrem(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.del(key);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 删除给定位置区间的元素
         *
         * @param key
         * @param start 开始区间，从0开始(包含)
         * @param end   结束区间,-1为最后一个元素(包含)
         * @return 删除的数量
         */
        public long zremrangeByRank(String key, int start, int end) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.zremrangeByRank(key, start, end);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 删除给定权重区间的元素
         *
         * @param key
         * @param min 下限权重(包含)
         * @param max 上限权重(包含)
         * @return 删除的数量
         */
        public long zremrangeByScore(String key, double min, double max) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.zremrangeByScore(key, min, max);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 获取给定区间的元素，原始按照权重由高到低排序
         *
         * @param key
         * @param start
         * @param end
         * @return Set<String>
         */
        public Set<String> zrevrange(String key, int start, int end) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.zrevrange(key, start, end);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 获取给定值在集合中的权重
         *
         * @param key
         * @param memebr
         * @return double 权重
         */
        public double zscore(String key, String memebr) {
            JedisCluster jedisCluster = getJedisCluster();
            Double score = jedisCluster.zscore(key, memebr);
            returnJedis(jedisCluster);
            if (score != null)
                return score;
            return 0;
        }
    }

    // *******************************************Hash*******************************************//
    public class Hash {

        /**
         * 从hash中删除指定的存储
         *
         * @param key
         * @param fieid 存储的名字
         * @return 状态码，1成功，0失败
         */
        public long hdel(String key, String fieid) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.hdel(key, fieid);
            returnJedis(jedisCluster);
            return s;
        }

        public long hdel(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.del(key);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 测试hash中指定的存储是否存在
         *
         * @param key
         * @param fieid 存储的名字
         * @return 1存在，0不存在
         */
        public boolean hexists(String key, String fieid) {
            JedisCluster jedisCluster = getJedisCluster();
            boolean s = jedisCluster.hexists(key, fieid);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 返回hash中指定存储位置的值
         *
         * @param key
         * @param fieid 存储的名字
         * @return 存储对应的值
         */
        public String hget(String key, String fieid) {
            JedisCluster jedisCluster = getJedisCluster();
            String s = jedisCluster.hget(key, fieid);
            returnJedis(jedisCluster);
            return s;
        }

        public byte[] hget(byte[] key, byte[] fieid) {
            JedisCluster jedisCluster = getJedisCluster();
            byte[] s = jedisCluster.hget(key, fieid);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 以Map的形式返回hash中的存储和值
         *
         * @param key
         * @return Map<Strinig,String>
         */
        public Map<String, String> hgetAll(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            Map<String, String> map = jedisCluster.hgetAll(key);
            returnJedis(jedisCluster);
            return map;
        }

        /**
         * 添加一个对应关系
         *
         * @param key
         * @param fieid
         * @param value
         * @return 状态码 1成功，0失败，fieid已存在将更新，也返回0
         **/
        public long hset(String key, String fieid, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.hset(key, fieid, value);
            returnJedis(jedisCluster);
            return s;
        }

        public long hset(String key, String fieid, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.hset(key.getBytes(), fieid.getBytes(), value);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 添加对应关系，只有在fieid不存在时才执行
         *
         * @param key
         * @param fieid
         * @param value
         * @return 状态码 1成功，0失败fieid已存
         **/
        public long hsetnx(String key, String fieid, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.hsetnx(key, fieid, value);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 获取hash中value的集合
         *
         * @param key
         * @return List<String>
         */
        public List<String> hvals(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> list = jedisCluster.hvals(key);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 在指定的存储位置加上指定的数字，存储位置的值必须可转为数字类型
         *
         * @param key
         * @param fieid 存储位置
         * @param value 要增加的值,可以是负数
         * @return 增加指定数字后，存储位置的值
         */
        public long hincrby(String key, String fieid, long value) {
            JedisCluster jedisCluster = getJedisCluster();
            long s = jedisCluster.hincrBy(key, fieid, value);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 返回指定hash中的所有存储名字,类似Map中的keySet方法
         *
         * @param key
         * @return Set<String> 存储名称的集合
         */
        public Set<String> hkeys(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            Set<String> set = jedisCluster.hkeys(key);
            returnJedis(jedisCluster);
            return set;
        }

        /**
         * 获取hash中存储的个数，类似Map中size方法
         *
         * @param key
         * @return long 存储的个数
         */
        public long hlen(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.hlen(key);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 根据多个key，获取对应的value，返回List,如果指定的key不存在,List对应位置为null
         *
         * @param key
         * @param fieids 存储位置
         * @return List<String>
         */
        public List<String> hmget(String key, String... fieids) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> list = jedisCluster.hmget(key, fieids);
            returnJedis(jedisCluster);
            return list;
        }

        public List<byte[]> hmget(byte[] key, byte[]... fieids) {
            JedisCluster jedisCluster = getJedisCluster();
            List<byte[]> list = jedisCluster.hmget(key, fieids);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param key
         * @param map <String,String> 对应关系
         * @return 状态，成功返回OK
         */
        public String hmset(String key, Map<String, String> map) {
            JedisCluster jedisCluster = getJedisCluster();
            String s = jedisCluster.hmset(key, map);
            returnJedis(jedisCluster);
            return s;
        }

        /**
         * 添加对应关系，如果对应关系已存在，则覆盖
         *
         * @param key Strin
         * @param map <String,String> 对应关系
         * @return 状态，成功返回OK
         */
        public String hmset(byte[] key, Map<byte[], byte[]> map) {
            JedisCluster jedisCluster = getJedisCluster();
            String s = jedisCluster.hmset(key, map);
            returnJedis(jedisCluster);
            return s;
        }

    }

    // *******************************************Strings*******************************************//
    public class Strings {
        /**
         * 根据key获取记录
         *
         * @param key String
         * @return 值
         */
        public String get(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            String value = jedisCluster.get(key);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 根据key获取记录
         *
         * @param key byte[]
         * @return 值
         */
        public byte[] get(byte[] key) {
            JedisCluster jedisCluster = getJedisCluster();
            byte[] value = jedisCluster.get(key);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key     String
         * @param seconds int 过期时间，以秒为单位
         * @param value   String
         * @return String 操作状态
         */
        public String setEx(String key, int seconds, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            String str = jedisCluster.setex(key, seconds, value);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 添加有过期时间的记录
         *
         * @param key     String
         * @param seconds int  过期时间，以秒为单位
         * @param value   String
         * @return String 操作状态
         */
        public String setEx(byte[] key, int seconds, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            String str = jedisCluster.setex(key, seconds, value);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 添加一条记录，仅当给定的key不存在时才插入
         *
         * @param key   String
         * @param value String
         * @return long 状态码，1插入成功且key不存在，0未插入，key存在
         */
        public long setnx(String key, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long str = jedisCluster.setnx(key, value);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, String value) {
            return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(String key, byte[] value) {
            return set(SafeEncoder.encode(key), value);
        }

        /**
         * 添加记录,如果记录已存在将覆盖原有的value
         *
         * @param key
         * @param value
         * @return 状态码
         */
        public String set(byte[] key, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            String status = jedisCluster.set(key, value);
            returnJedis(jedisCluster);
            return status;
        }

        /**
         * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据<br/>
         * 例:String str1="123456789";<br/>
         * 对str1操作后setRange(key,4,0000)，str1="123400009";
         *
         * @param key
         * @param offset
         * @param value
         * @return long value的长度
         */
        public long setRange(String key, long offset, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.setrange(key, offset, value);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 在指定的key中追加value
         *
         * @param key
         * @param value
         * @return long 追加后value的长度
         **/
        public long append(String key, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.append(key, value);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用
         *
         * @param key
         * @param number 要减去的值
         * @return long 减指定值后的值
         */
        public long decrBy(String key, long number) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.decrBy(key, number);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * <b>可以作为获取唯一id的方法</b><br/>
         * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用
         *
         * @param key
         * @param number 要减去的值
         * @return long 相加后的值
         */
        public long incrBy(String key, long number) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.incrBy(key, number);
            returnJedis(jedisCluster);
            return len;
        }

        /**
         * 对指定key对应的value进行截取
         *
         * @param key
         * @param startOffset 开始位置(包含)
         * @param endOffset   结束位置(包含)
         * @return String 截取的值
         */
        public String getrange(String key, long startOffset, long endOffset) {
            JedisCluster jedisCluster = getJedisCluster();
            String value = jedisCluster.getrange(key, startOffset, endOffset);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 获取并设置指定key对应的value<br/>
         * 如果key存在返回之前的value,否则返回null
         *
         * @param key
         * @param value
         * @return String 原始value或null
         */
        public String getSet(String key, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            String str = jedisCluster.getSet(key, value);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
         *
         * @param keys
         * @return List<String> 值得集合
         */
        public List<String> mget(String... keys) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> str = jedisCluster.mget(keys);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 批量存储记录
         *
         * @param keysvalues 例:keysvalues="key1","value1","key2","value2";
         * @return String 状态码
         */
        public String mset(String... keysvalues) {
            JedisCluster jedisCluster = getJedisCluster();
            String str = jedisCluster.mset(keysvalues);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 获取key对应的值的长度
         *
         * @param key
         * @return value值得长度
         */
        public long strlen(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            long len = jedisCluster.strlen(key);
            returnJedis(jedisCluster);
            return len;
        }
    }

    // *******************************************Lists*******************************************//
    public class Lists {
        /**
         * List长度
         *
         * @param key
         * @return 长度
         */
        public long llen(String key) {
            return llen(SafeEncoder.encode(key));
        }

        /**
         * List长度
         *
         * @param key
         * @return 长度
         */
        public long llen(byte[] key) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.llen(key);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @param value 值
         * @return 状态码
         */
        public String lset(byte[] key, int index, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            String status = jedisCluster.lset(key, index, value);
            returnJedis(jedisCluster);
            return status;
        }

        /**
         * 覆盖操作,将覆盖List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @param value 值
         * @return 状态码
         */
        public String lset(String key, int index, String value) {
            return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
        }

        /**
         * 在value的相对位置插入记录
         *
         * @param key
         * @param where 前面插入或后面插入
         * @param pivot 相对位置的内容
         * @param value 插入的内容
         * @return 记录总数
         */
        public long linsert(String key, LIST_POSITION where, String pivot, String value) {
            return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
        }

        /**
         * 在指定位置插入记录
         *
         * @param key
         * @param where 前面插入或后面插入
         * @param pivot 相对位置的内容
         * @param value 插入的内容
         * @return 记录总数
         */
        public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.linsert(key, where, pivot, value);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 获取List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @return 值
         **/
        public String lindex(String key, int index) {
            return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
        }

        /**
         * 获取List中指定位置的值
         *
         * @param key
         * @param index 位置
         * @return 值
         **/
        public byte[] lindex(byte[] key, int index) {
            JedisCluster jedisCluster = getJedisCluster();
            byte[] value = jedisCluster.lindex(key, index);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 将List中的第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public String lpop(String key) {
            return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
        }

        /**
         * 将List中的第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public byte[] lpop(byte[] key) {
            JedisCluster jedisCluster = getJedisCluster();
            byte[] value = jedisCluster.lpop(key);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 将List中最后第一条记录移出List
         *
         * @param key
         * @return 移出的记录
         */
        public String rpop(String key) {
            JedisCluster jedisCluster = getJedisCluster();
            String value = jedisCluster.rpop(key);
            returnJedis(jedisCluster);
            return value;
        }

        /**
         * 向List尾部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long lpush(String key, String value) {
            return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
        }

        /**
         * 向List头部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long rpush(String key, String value) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.rpush(key, value);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 向List头部追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long rpush(byte[] key, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.rpush(key, value);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 向List中追加记录
         *
         * @param key
         * @param value
         * @return 记录总数
         */
        public long lpush(byte[] key, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.lpush(key, value);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         *
         * @param key
         * @param start
         * @param end
         * @return List
         */
        public List<String> lrange(String key, long start, long end) {
            JedisCluster jedisCluster = getJedisCluster();
            List<String> list = jedisCluster.lrange(key, start, end);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 获取指定范围的记录，可以做为分页使用
         *
         * @param key
         * @param start
         * @param end   如果为负数，则尾部开始计算
         * @return List
         */
        public List<byte[]> lrange(byte[] key, int start, int end) {
            JedisCluster jedisCluster = getJedisCluster();
            List<byte[]> list = jedisCluster.lrange(key, start, end);
            returnJedis(jedisCluster);
            return list;
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         *
         * @param key
         * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public long lrem(byte[] key, int c, byte[] value) {
            JedisCluster jedisCluster = getJedisCluster();
            long count = jedisCluster.lrem(key, c, value);
            returnJedis(jedisCluster);
            return count;
        }

        /**
         * 删除List中c条记录，被删除的记录值为value
         *
         * @param key
         * @param c     要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
         * @param value 要匹配的值
         * @return 删除后的List中的记录数
         */
        public long lrem(String key, int c, String value) {
            return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         *
         * @param key
         * @param start 记录的开始位置(0表示第一条记录)
         * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         */
        public String ltrim(byte[] key, int start, int end) {
            JedisCluster jedisCluster = getJedisCluster();
            String str = jedisCluster.ltrim(key, start, end);
            returnJedis(jedisCluster);
            return str;
        }

        /**
         * 算是删除吧，只保留start与end之间的记录
         *
         * @param key
         * @param start 记录的开始位置(0表示第一条记录)
         * @param end   记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
         * @return 执行状态码
         */
        public String ltrim(String key, int start, int end) {
            return ltrim(SafeEncoder.encode(key), start, end);
        }
    }
}
