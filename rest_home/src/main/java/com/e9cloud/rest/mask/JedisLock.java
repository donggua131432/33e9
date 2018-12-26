package com.e9cloud.rest.mask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.redis.util.JedisClusterUtil;

public class JedisLock {

	private static JedisClusterUtil jedisClusterUtil = SpringContextHolder.getBean(JedisClusterUtil.class);
	private static String lockKey = "maskLock";
	private static int intervalMill = 500;
	private static final Logger logger = LoggerFactory.getLogger(JedisLock.class);

	public static boolean lock() {
		long end = System.currentTimeMillis() + InitApp.lockWait;
		while (true) {
			long value = jedisClusterUtil.STRINGS.setnx(lockKey, "");
			if (value == 1) {
				jedisClusterUtil.KEYS.expired(lockKey, InitApp.lockSecond);
				return true;
			} else if (value == 0) {
				long timeout = jedisClusterUtil.KEYS.ttl(lockKey);
				if (timeout < 0) {
					jedisClusterUtil.STRINGS.set(lockKey, "");
					jedisClusterUtil.KEYS.expired(lockKey, InitApp.lockSecond);
					return true;
				}
			}
			if (end < System.currentTimeMillis()) {
				break;
			}
			try {
				Thread.sleep(intervalMill);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("获取锁失败");
		return false;
	}

	public static void unlock() {
		jedisClusterUtil.KEYS.del(lockKey);
	}
}
