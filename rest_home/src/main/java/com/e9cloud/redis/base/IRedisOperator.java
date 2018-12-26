package com.e9cloud.redis.base;

import java.util.TreeSet;

/**
 * Created by Administrator on 2016/4/16.
 */
public interface IRedisOperator {
	/**
	 * 根据pattern 获取所有的keys
	 * 
	 * @param pattern
	 * @return
	 */
	TreeSet<String> keys(String pattern);

	/**
	 * 根据pattern 判断key存不存在
	 * 
	 * @param pattern
	 * @return
	 */
	boolean existKeys(String pattern);
}
