package com.e9cloud.rest.mask;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.google.gson.Gson;

/**
 * 隐私号关系
 * 
 * @author Administrator
 *
 */
public class MakeRelationRedis {

	private static JedisClusterUtil jedisClusterUtil = SpringContextHolder.getBean(JedisClusterUtil.class);

	private static final MakeRelationRedis MAKE_RELATION_REDIS = new MakeRelationRedis();

	private MakeRelationRedis() {
	}

	public static MakeRelationRedis getInstance() {
		return MAKE_RELATION_REDIS;
	}

	/**
	 * 获得规则key
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public String getKey(String key1, String key2, String... appId) {
		if (appId != null && appId.length > 0) {
			StringBuffer key = new StringBuffer("mask");
			key.append(":").append(appId[0]);
			key.append(":").append(key1);
			key.append(":").append(key2);
			return key.toString();
		}
		return "mask:" + key1 + "_" + key2;
	}

	/**
	 * 获得号码key存储value
	 * 
	 * @param phone1
	 * @param phone2
	 * @return
	 */
	public String getValue(String phone1, String phone2, String... appId) {
		return jedisClusterUtil.STRINGS.get(getKey(phone1, phone2, appId));
	}

	/**
	 * 判断是否存在对应key
	 * 
	 * @param phone1
	 * @param phone2
	 * @return
	 */
	public boolean exesit(String phone1, String phone2, String... appId) {
		return jedisClusterUtil.KEYS.exists(getKey(phone1, phone2, appId));
	}

	/**
	 * 剩余时间
	 * 
	 * @param phone1
	 * @param phone2
	 * @return
	 */
	public long ttl(String phone1, String phone2, String... appId) {
		return jedisClusterUtil.KEYS.ttl(getKey(phone1, phone2, appId));
	}

	/**
	 * 释放映射关系
	 * 
	 * @param phoneA
	 * @param phoneB
	 * @return
	 */
	public long delete(String phoneA, String phoneB, String... appId) {
		String key = getKey(phoneA, phoneB, appId);
		long result = jedisClusterUtil.KEYS.del(key);
		return result;
	}

	/**
	 * 添加号码映射
	 * 
	 * @param phoneA
	 * @param phoneB
	 * @param maskNum
	 * @param appId
	 * @param timeOut
	 * @param isRecord
	 * @param sid 账号sid
	 * @param maskId 隐私映射唯一id
	 * @param codec 语音编码，默认G729
	 */
	public void addABMask(String phoneA, String phoneB, String maskNum, String appId, String timeOut, String isRecord,
			String arean, String sid, String maskId,String codec) {
		String abk = getKey(phoneA, phoneB, appId);
		String amk = getKey(phoneA, maskNum);
		String bmk = getKey(phoneB, maskNum);
		BestMask bestMask = new BestMask();
		bestMask.setAppId(appId);
		bestMask.setArean(arean);
		bestMask.setMaskNum(maskNum);
		bestMask.setMaskId(maskId);
		bestMask.setNeedRecord(isRecord);
		bestMask.setCodec(codec);
		String abv = new Gson().toJson(bestMask);
		String amv = phoneB + "_" + isRecord + "_" + appId + "_" + sid + "_" + codec;
		String bmv = phoneA + "_" + isRecord + "_" + appId + "_" + sid + "_" + codec;
		int seconds = Integer.valueOf(timeOut);
		jedisClusterUtil.STRINGS.setEx(abk, seconds, abv);
		jedisClusterUtil.STRINGS.setEx(amk, seconds, amv);
		jedisClusterUtil.STRINGS.setEx(bmk, seconds, bmv);
	}
}
