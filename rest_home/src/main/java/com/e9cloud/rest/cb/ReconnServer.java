package com.e9cloud.rest.cb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
/**
 * Created by Administrator on 2016/3/18.
 */
public class ReconnServer {


	private static final Logger log = LoggerFactory.getLogger(ReconnServer.class);

	/**
	 * 服务器编号，
	 */
	private int id;

	/**
	 * 游戏服务器名称，
	 */
	private String name;

	/**
	 * IP地址，
	 */
	private String ip;

	/**
	 * 监听端口，
	 */
	private int port;

	/**
	 * 是否开启
	 */
	private boolean isOpened;


	private Bootstrap bootstrap;

	/**
	 * 会话
	 */
	private Channel channel;


	public void shutDown() {
		bootstrap.group().shutdownGracefully();
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setBootstrap(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public void checkConnect() {

		try {
			if(channel!=null && channel.isActive()) {
				isOpened=true;
				return;
			}
			ChannelFuture connect = bootstrap.connect(ip,port);
			ChannelFuture awaitUninterruptibly = connect.awaitUninterruptibly();
			this.channel = awaitUninterruptibly.channel();
		} catch (Exception e) {

		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}


}
