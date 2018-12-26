package com.e9cloud.util;

import com.e9cloud.cache.LocalCacheMap;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.User;

import java.util.List;

public class InitValue {
	// 用户对象缓存容器 100容量
	public static LocalCacheMap<User> userCache = new LocalCacheMap<User>("userCache", 100);
	// 应用对象缓存容器 100容量
	public static LocalCacheMap<AppInfo> appInfoCache = new LocalCacheMap<AppInfo>("appInfoCache", 100);
	public static LocalCacheMap<AppInfoExtra> appInfoExtraCache = new LocalCacheMap<AppInfoExtra>("appInfoExtraCache", 100);
	// 应用显号集合缓存容器 100容量
	public static LocalCacheMap<List<String>> appNumberCache = new LocalCacheMap<List<String>>("appNumberCache", 100);
	// 黑名单集合缓存容器 100容量
	public static LocalCacheMap<List<String>> numberBlackCache = new LocalCacheMap<List<String>>("numberBlackCache", 100);
	// 默认显号集合缓存容器 100容量
	public static LocalCacheMap<List<String>> relayNumpoolCache = new LocalCacheMap<List<String>>("relayNumpoolCache", 100);

	// 虚拟小号X号码缓存容器 100容量
	public static LocalCacheMap<List<String>> abxNumberCache = null;
	public static void setAbxNumberCacheKey(String cacheKey){
		abxNumberCache = new LocalCacheMap<List<String>>(cacheKey, 100);
	}

	//设置sipPhone号码缓存
	public static LocalCacheMap<List<SpApplyNum>> sipPhponeCache = null;
	public static void setSipPhponeCache(String cacheKey){
		sipPhponeCache = new LocalCacheMap<List<SpApplyNum>>(cacheKey, 100);
	}
	//语音验证码显号缓存
	public static LocalCacheMap<List<String>> voiceVerifyNumberCache = null;
	public static void setVoiceVerifyNumberCache(String cacheKey){
		voiceVerifyNumberCache = new LocalCacheMap<List<String>>(cacheKey, 100);
	}



}
