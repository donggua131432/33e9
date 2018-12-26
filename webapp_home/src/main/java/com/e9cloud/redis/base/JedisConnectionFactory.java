/*
 * IBM Corporation.
 * Copyright (c) 2014 All Rights Reserved.
 */

package com.e9cloud.redis.base;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * 类作用：
 * 
 * @author Johnny@cn.ibm.com 使用说明：
 */
public class JedisConnectionFactory extends org.springframework.data.redis.connection.jedis.JedisConnectionFactory {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<JedisShardInfo> shardInfos;
	private String serverIPPrefix = "10.";

	/**
	 * @return the jedisInfos
	 */
	public List<JedisShardInfo> getShardInfos() {
		return shardInfos;
	}

	/**
	 * @param jedisInfos
	 *            the jedisInfos to set
	 */
	public void setShardInfos(List<JedisShardInfo> jedisInfos) {
		this.shardInfos = jedisInfos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.redis.connection.jedis.JedisConnectionFactory#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface nif = netInterfaces.nextElement();
				Enumeration<InetAddress> iparray = nif.getInetAddresses();
				while (iparray.hasMoreElements()) {
					String ip = iparray.nextElement().getHostAddress();

					if (ip.startsWith(serverIPPrefix)) {
						int ipEnd = Integer.parseInt(ip.substring(ip.lastIndexOf(".") + 1));
						int idx = ipEnd % shardInfos.size();
						JedisShardInfo shardInfo = shardInfos.get(idx);
						setShardInfo(shardInfo);
						logger.info("This host IP:" + ip + " use redis: " + shardInfo);
						break;
					}
					if (getShardInfo() != null) {
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("不能初始化Redis:", e);
			throw new RuntimeException("不能初始化Redis:", e);
		}
		super.afterPropertiesSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.redis.connection.jedis.JedisConnectionFactory#fetchJedisConnector()
	 */
	@Override
	protected Jedis fetchJedisConnector() {
		// TODO failover
		Jedis jedis = super.fetchJedisConnector();

		return jedis;
	}

	/**
	 * @return the serverIPPrefix
	 */
	public String getServerIPPrefix() {
		return serverIPPrefix;
	}

	/**
	 * @param serverIPPrefix
	 *            the serverIPPrefix to set
	 */
	public void setServerIPPrefix(String serverIPPrefix) {
		this.serverIPPrefix = serverIPPrefix;
	}

}
