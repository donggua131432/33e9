package com.e9cloud.cache;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;


/**
 * Created by liutao on 2016/2/15.
 */


/**
 * <p>
 * LocalCacheMap 是一个 支持 LRU 算法 容量控制 超期检查的 本地缓存<br>
 *
 * 1 LRU 算法 和 容量控制 是通过内嵌的 ConcurrentLinkedHashMap 来实现的<br>
 * 2 超时删除是通过记录存储时间实现的 删除动作在查询操作的时候触发 因此会导致延时删除 与LRU算法共同协作的时候会导致部分存储的数据不严谨<br>
 * 3 LocalCacheMap 的使用与普通的 map 对象大致相同 只是需要在 put 的时候指定超时时间<br>
 * 4 LocalCacheMap 是线程安全的<br>
 * 5 LocalCacheMap 创建后会有一个引用在 静态变量 allLocalCacheMaps 以用于监控 外部不能回收<br>
 * </p>
 *
 */
public class LocalCacheMap<T> {

    /**
     * <p>
     * JVM 中所有的 LocalCacheMap 列表
     * </p>
     */
    @SuppressWarnings("rawtypes")
    private static List<LocalCacheMap> allLocalCacheMaps = new ArrayList<LocalCacheMap>();

    /**
     * <p>
     * 多线程有序HashMap
     * </p>
     */
    private ConcurrentLinkedHashMap<String, MapValue> map;

    /**
     * <p>
     * 当前 缓存 的名称
     * </p>
     */
    private String name;

    /**
     * <p>
     * 构造一个 LocalCacheMap 本地缓存
     * </p>
     *
     * @param name
     *            缓存的名称
     * @param capacity
     *            缓存的容量
     *
     */
    public LocalCacheMap(String name, int capacity) {
        this.name = name;
        map = new ConcurrentLinkedHashMap.Builder<String, MapValue>().maximumWeightedCapacity(capacity).build();
        allLocalCacheMaps.add(this);
    }

    /**
     * <p>
     * 检查某一个键值 如果存在则返回 不存在或者超时则返回 null
     * </p>
     *
     * @param key
     *            键
     * @return 值
     */
    private MapValue check(String key) {

        MapValue value = map.get(key);

        // 不存在这个键
        if (value == null) {
            return null;
        }

        // 存在但超时了
        if (value.getExpireSecond() > 0 && (System.currentTimeMillis() - value.getPutTime()) > value.getExpireSecond() * 1000) {
            map.remove(key);
            return null;
        }

        return value;
    }

    /**
     * <p>
     * 保存一个键值对到本地缓存 并设置其超时时间
     * </p>
     *
     * @param key
     *            键
     * @param v
     *            值
     * @param expireSecond
     *            超时时间 单位：秒
     * @return 如果存在同样键的值将会返回 不存在则返回null
     */
    public T put(String key, T v, long expireSecond) {

        check(key);

        MapValue ret = map.put(key, new MapValue(v, expireSecond));

        return ret != null ? ret.getValue() : null;
    }

    /**
     * <p>
     * 按照键查询一个值 如果超时或者不存在则返回 null
     * </p>
     *
     * @param key
     *            键
     * @return 值
     */
    public T get(String key) {

        MapValue value = check(key);

        if (value != null) {
            return value.getValue();
        }

        return null;

    }

    /**
     * <p>
     * 删除缓存中的某个键
     * </p>
     *
     * @param key
     *            键
     * @return 如果该键有值则返回 否则返回null
     */
    public T remove(String key) {

    //    LocalCacheMonitor.del.inc();

        check(key);

        MapValue ret = map.remove(key);

     //   CacheLogger.localCache_run.info("localCache_run#[" + name + "]#remove[" + key + "]:" + String.valueOf(ret));

        return ret != null ? ret.getValue() : null;
    }

    /**
     * <p>
     * 保存一个键值对到本地缓存 并设置其为永久有效
     * </p>
     *
     * @param key
     *            键
     * @param v
     *            值
     * @return 如果存在同样键的值将会返回 不存在则返回null
     */
    public T put(String key, T v) {
        return put(key, v, 0);
    }


    /**
     * <p>
     * 删除缓存中的某个键
     * </p>
     */
    public void clear() {
        map.clear();
    }

    /**
     * <p>
     * 判断是否存在某个键值
     * </p>
     *
     * @param key
     *            键
     * @return 是否存在这个键
     */
    public boolean containsKey(String key) {
        check(key);
        return map.containsKey(key);
    }

    /**
     * <p>
     * 查看当前缓存的 size
     * </p>
     *
     * @return 缓存的大小
     */
    public int size() {
        checkAll();
        return map.size();
    }

    /**
     * <p>
     * 获取所有的键集合
     * </p>
     *
     * @return 键的集合
     */
    public Set<String> keySet() {
        checkAll();
        return map.keySet();
    }

    /**
     * <p>
     * 遍历检查所有的键值 并删除过期的键值
     * </p>
     */
    public void checkAll() {
        for (String key : map.keySet()) {
            check(key);
        }
    }

    /**
     * <p>
     * 获取缓存的初始化容量
     * </p>
     *
     * @return 初始化的容量
     */
    public long getCapacity() {
        return map.capacity();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        checkAll();
        return "LocalCacheMap[" + name + "]:" + String.valueOf(map);
    }

    class MapValue {

        private T value;

        // 超时时间 默认为 0 即不超时 单位 秒
        private long expireSecond;

        // 设置时间 默认为当前时刻
        private long putTime = System.currentTimeMillis();

        public MapValue() {
        }

        public MapValue(T v) {
            this.value = v;
        }

        public MapValue(T v, long expire) {
            this.value = v;
            this.expireSecond = expire;
        }

        public long getPutTime() {
            return putTime;
        }
        public void setPutTime(long putTime) {
            this.putTime = putTime;
        }

        public T getValue() {
            return value;
        }
        public void setValue(T value) {
            this.value = value;
        }

        public long getExpireSecond() {
            return expireSecond;
        }

        public void setExpireSecond(long expireSecond) {
            this.expireSecond = expireSecond;
        }

        @Override
        public String toString() {
            return String.valueOf(value) + ";putTime=" + putTime + ";expireSecond=" + expireSecond;
        }
    }

    /***
     * 使用例子
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        //缓存容量
        LocalCacheMap<String> strCache = new LocalCacheMap<String>("CacheName",10 );

        //先从缓存读取
        String value1= strCache.get("key1");

        if (value1 == null)
        {
            //从数据库获取vulue1, 并缓存2秒
            strCache.put("key1","value1",2);

            //return vlaue1
        }

        //return vlaue1




    }
}

