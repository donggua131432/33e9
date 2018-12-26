package com.e9cloud.core.page;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页参数
 * Created by wzj on 2016/3/22.
 */
public class Param<K, V> extends HashMap<K, V> implements Map<K, V>{

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    @Override
    public V put(K key, V value) {
        if (value instanceof String) {
            return super.put(key, (V)((String)value).trim());
        }
        return super.put(key, value);
    }
}
