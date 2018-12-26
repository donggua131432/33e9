package com.e9cloud.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 64位不重复ID生成器，前16位是1，中间32位是当前时间戳，最后16位是当前时间戳的自增ID
 * 注：时间戳同一秒内生成ID超过32767，就会导致生产重复ID
 * 
 * @author hongzhiwu
 * @createDate 2013-11-20 上午8:26:55
 */
public class IDGenerator {

	private static AtomicInteger id = new AtomicInteger();

	public static Long getUniqueID() {
		return 1 << 48 | System.currentTimeMillis() / 1000 << 16 | Math.abs((short) id.getAndIncrement());
	}

	public static String getUniqueIDStr() {
		return getUniqueID().toString();
	}
}