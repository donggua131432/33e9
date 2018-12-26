package com.e9cloud.core.application;

import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.rest.cb.CBMsg;
import com.e9cloud.rest.cb.ConnServer;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.cb.WeightedRoundRobinScheduling;
import com.e9cloud.util.Utils;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.util.internal.StringUtil;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class InitApp {

	private static final Logger logger = LoggerFactory.getLogger(InitApp.class);
	private volatile static InitApp instance = null;
	private WeightedRoundRobinScheduling schedu = new WeightedRoundRobinScheduling();
	public Set<String> notRecieveServer = new HashSet<String>();

	private Properties properties = null;
	// CB IP地址
	private String tcpHost;
	// CB 端口号
	private int tcpPort;
	// 权重
	private int weight;
	// 多长时间未请求后，发送心跳
	private int write_wait_seconds;
	// 隔N秒后重连
	private int re_conn_wait_seconds;
	// 隐私号日志记录地址
	public static String maskLogUrl;
	// 隐私号锁的秒数
	public static int lockSecond;
	// 隐私号锁等待的秒数
	public static int lockWait;

	public static  Map connMap = new ConcurrentHashMap<>();

	public void connServer() {
		if(!connMap.containsKey(tcpHost+":"+tcpPort)){
			Thread tt = new Thread(new ConnServer(tcpHost, tcpPort, re_conn_wait_seconds, write_wait_seconds, weight));
			tt.start();
			connMap.put(tcpHost+":"+tcpPort,tt);
		}else{
			Thread tt = (Thread) connMap.get(tcpHost+":"+tcpPort);
			if(tt.isAlive()){
				tt.start();
			}
		}
	}

	public static InitApp getInstance() {
		if (instance == null) {
			synchronized (InitApp.class) {
				if (instance == null) {
					instance = new InitApp();
				}
			}
		}
		return instance;
	}

	public void initToApp() {
		loadProperties();
		connAllCbServer();
		addHttpServer();
	}

	private void loadProperties() {
		logger.debug("loadProperties...");
		SysPropertits sysPropertits = SpringContextHolder.getBean(SysPropertits.class);
		String nameDir = "/settings/appConfig" + "_" + sysPropertits.getEnvName() + ".properties";
		InputStream inputStream = this.getClass().getResourceAsStream(nameDir);
		properties = new Properties();
		try {
			properties.load(inputStream);
			write_wait_seconds = Utils.convertToInt(getValue("cb.write.wait_seconds", ""), 5);
			re_conn_wait_seconds = Utils.convertToInt(getValue("cb.re.conn.wait.seconds", ""), 5);
			lockSecond = Utils.convertToInt(getValue("lockSecond", "5"), 5);
			lockWait = Utils.convertToInt(getValue("lockWait", "5"), 5);
			maskLogUrl = getValue("mask.log.url", "");
		} catch (IOException e) {
			logger.error("IOException error [{}]", e);
		}
	}

	public String getValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * 发送消息(cb可选,可指定cb)
	 * 
	 * @param msg
	 *            消息
	 * @param server
	 * @return
	 * @throws Exception
	 */
	public boolean sendMsg(CBMsg msg, Server server) throws Exception {
		if (server != null) {
			Channel channel = server.getChannel();
			if (channel != null) {
				channel.writeAndFlush(msg);
				logger.info("rest success: rest to cb:" + channel.remoteAddress() + ":" + new Gson().toJson(msg));
				logger.info("发送AS请求报文到：" + channel.remoteAddress());
				return true;
			} else {
				logger.info("消息发送失败,连接尚未建立!...");
				return false;
			}
		} else {
			logger.info("rest error:the cb serverlist is null");
			logger.info("消息发送失败,没有建立的CB服务!...");
			return false;
		}

	}

	/**
	 * 获得cb server
	 * 
	 * @return
	 */
	public Server getBestServer() {
		return schedu.GetBestServer();
	}


	public String getHttpUrl() {
		return schedu.getHttpUrl();
	}

	public void addHttpServer(){
		String httpAddress = getValue("abx.ip.httpAddress", "");
		System.out.println("-----------:"+httpAddress);
		if (!StringUtil.isNullOrEmpty(httpAddress)) {
			String[] address = httpAddress.split(";");
			for (String str : address) {
				String[] remoteAddress = str.split(":");
				InitApp.getInstance().getSchedu().addHttpServer(remoteAddress[0]+":"+remoteAddress[1],weight);
			}
		}
	}

	/**
	 * 获得指定cb server
	 * 
	 * @param cbip
	 * @return
	 */
	public Server getApointCBServer(String cbip) {
		return schedu.GetApointCBServer(cbip);
	}

	public void connAllCbServer() {
		String scoketAddress = getValue("cb.tcp.socketAddress", "");
		if (!StringUtil.isNullOrEmpty(scoketAddress)) {
			String[] address = scoketAddress.split(";");
			for (String str : address) {
				String[] remoteAddress = str.split(":");
				tcpHost = remoteAddress[0];
				tcpPort = Utils.convertToInt(remoteAddress[1], 80);
				weight = Utils.convertToInt(remoteAddress[2], 1);
				connServer();
			}
		}
	}

	public void connServerBySocketAddress(String remoteAddress) {
		String scoketAddress = getValue("cb.tcp.socketAddress", "");
		if (!StringUtil.isNullOrEmpty(scoketAddress)) {
			String[] address = scoketAddress.split(";");
			for (String str : address) {
				if (!StringUtil.isNullOrEmpty(remoteAddress) && str.contains(remoteAddress)) {
					String[] remoteAdd = str.split(":");
					tcpHost = remoteAdd[0];
					tcpPort = Utils.convertToInt(remoteAdd[1], 80);
					weight = Utils.convertToInt(remoteAdd[2], 1);
					connServer();
				}

			}
		}
	}

	public WeightedRoundRobinScheduling getSchedu() {
		return schedu;
	}

	public void setSchedu(WeightedRoundRobinScheduling schedu) {
		this.schedu = schedu;
	}

	public void stopCBServer(String ip){
		this.notRecieveServer.add(ip);
	}

	public void startCBServer(String ip){
		this.notRecieveServer.remove(ip);
	}

	public void startAllCBServer(){
		this.notRecieveServer.clear();
	}
}
