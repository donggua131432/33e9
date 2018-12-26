package com.e9cloud.rest.mask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.google.gson.Gson;

public class MakePoolRedis {

	private static JedisClusterUtil jedisClusterUtil = SpringContextHolder.getBean(JedisClusterUtil.class);

	public String getKey(String appId) {
		return "makePool_" + appId;
	}

	private static final MakePoolRedis MAKE_POOL_REDIS = new MakePoolRedis();

	private MakePoolRedis() {
	}

	public static MakePoolRedis getInstance() {
		return MAKE_POOL_REDIS;
	}

	/**
	 * 获得appid池中所有隐私号码
	 * 
	 * @param appId
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public Map<String, List<String>> getAll(String appId) {
		String key = getKey(appId);
		Map<String, String> arens = jedisClusterUtil.HASH.hgetAll(key);
		Map<String, List<String>> map = new HashMap<>();
		for (Entry<String, String> e : arens.entrySet()) {
			List<String> list = new Gson().fromJson(e.getValue(), List.class);
			map.put(e.getKey(), list);
		}
		return map;
	}

	/**
	 * 获得appid池中对应区域的所有隐私号码
	 * 
	 * @param appId
	 * @param arean
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public List<String> getAll(String appId, String arean) {
		String key = getKey(appId);
		String areans = jedisClusterUtil.HASH.hget(key, arean);
		List<String> list = new Gson().fromJson(areans, List.class);
		return list;
	}

	/**
	 * 添加隐私号
	 * 
	 * @param appId
	 * @param arean
	 * @param maskNum
	 * @return
	 */
	public boolean addMakeNum(String appId, String arean, String maskNum) {
		List<String> list = getAll(appId, arean);
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(maskNum);
		String maskNums = new Gson().toJson(list);
		return jedisClusterUtil.HASH.hset(getKey(appId), arean, maskNums) == 1;

	}

	/**
	 * 删除隐私号
	 * 
	 * @param appId
	 * @param arean
	 * @param maskNum
	 * @return
	 */
	public boolean delMakeNum(String appId, String arean, String maskNum) {
		List<String> list = getAll(appId, arean);
		if (list == null) {
			list = new ArrayList<>();
		}
		boolean suc = list.remove(maskNum);
		String maskNums = new Gson().toJson(list);
		jedisClusterUtil.HASH.hset(getKey(appId), arean, maskNums);
		return suc;

	}

	/**
	 * 同步sql数据
	 * 
	 * @param appId
	 * @param arean
	 * @param maskNum
	 */
	public void sysMaskNum(String appId, String arean, Set<String> maskNum) {

		String maskNums = new Gson().toJson(maskNum);
		jedisClusterUtil.HASH.hset(getKey(appId), arean, maskNums);
	}

	/**
	 * 清空之前的数据
	 * 
	 * @param appId
	 */
	public void clearMaskNum(String appId) {
		jedisClusterUtil.KEYS.del(getKey(appId));
	}
}
