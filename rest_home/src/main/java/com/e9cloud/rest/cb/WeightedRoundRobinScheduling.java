package com.e9cloud.rest.cb;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.e9cloud.core.application.InitApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/3/1.
 */

import io.netty.channel.Channel;

/**
 * 权重轮询调度算法(WeightedRound-RobinScheduling)-Java实现
 * 
 * @author huligong
 */

public class WeightedRoundRobinScheduling {
	private static final Logger logger = LoggerFactory.getLogger(WeightedRoundRobinScheduling.class);
	private static Map<String, Server> serverMap = new ConcurrentHashMap<>(); // 服务器集合
	private static Map<String, Server> httpServerMap = new ConcurrentHashMap<>(); //http服务器集合
	public static final Random builder = new Random();
	public static Server random(Collection<? extends Server> servers) {
		int total = 0;
		for (Server server : servers) {
			total += server.getWeight();
		}
		if (total <= 0) {
			return null;
		}
		int rank = builder.nextInt(total) + 1;
		int sum = 0;
		for (Server server : servers) {
			sum += server.getWeight();
			if (rank <= sum) {
				return server;
			}
		}
		return null;
	}
	/**
	 * 获得最佳cb
	 * @return
	 */
	public Server GetBestServer() {
		List<Server> list = new ArrayList<>(serverMap.values());
		List<Server> serverList = new ArrayList<>();
		Set set = InitApp.getInstance().notRecieveServer;
		for (Server server : list) {
			if(!set.contains(server.getIp())){
				serverList.add(server);
			}
		}
		return random(serverList);
	}

	/**
	 *
	 */
	public String getHttpUrl(){
		List<Server> list = new ArrayList<>(httpServerMap.values());
		Server server = random(list);
		return server.getIp();
	}

	/**
	 * 得到指定cb
	 * 
	 * @param ip
	 *            唯一cb
	 * 
	 * @return
	 */
	public Server GetApointCBServer(String ip) {
		return serverMap.get(ip);
	}

	public void addServer(String socketAddress, Channel channel, int weight) {
		Server server = new Server(socketAddress, channel, weight);
		serverMap.put(socketAddress, server);
		logger.info("----addServer{socketAddress},{size},------" + socketAddress + "," + serverMap.size());
	}

	public void addHttpServer(String httpAddress, int weight) {
		Server server = new Server();
		server.setIp(httpAddress);
		server.setWeight(weight);
		httpServerMap.put(httpAddress, server);
		logger.info("----addHttpServer{httpAddress},{size},------" + httpAddress + "," + httpServerMap.size());
	}

	public void stopServer(String socketAddress) {
		Server server = serverMap.remove(socketAddress);
		if (server == null) {
			logger.error("----stopServer---失败---" + socketAddress + "," + serverMap.size());
		} else {
			logger.info("----stopServer---成功---" + socketAddress + "," + serverMap.size());
		}
	}

}
