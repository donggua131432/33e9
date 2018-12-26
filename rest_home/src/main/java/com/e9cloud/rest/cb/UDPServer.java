package com.e9cloud.rest.cb;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.rest.service.RestService;

public class UDPServer {
	private static final int PORT = 5000;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private byte receiveByte[];
	private String receiveStr;
	RestService restService = SpringContextHolder.getBean(RestService.class);
	private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);

	public UDPServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				init();
			}
		}, "udp").start();

	}

	public void init() {

		try {
			dataSocket = new DatagramSocket(PORT);
		} catch (SocketException e1) {
			logger.error(e1.getMessage(), e1);
		}
		receiveByte = new byte[1024];
		dataPacket = new DatagramPacket(receiveByte, receiveByte.length);

		receiveStr = "";
		int i = 0;
		while (i == 0)// 无数据，则循环

		{
			try {
				dataSocket.receive(dataPacket);

				i = dataPacket.getLength();
				// 接收数据

				if (i > 0) {
					// 指定接收到数据的长度,可使接收数据正常显示,开始时很容易忽略这一点
					int port = dataPacket.getPort();
					InetAddress addr = dataPacket.getAddress();
					receiveStr = new String(receiveByte, 0, dataPacket.getLength());
					logger.info("receiveStr："+receiveStr);
					String rn = parseMessage(receiveStr);
					byte[] send = rn.getBytes();
					dataSocket.send(new DatagramPacket(send, send.length, addr, port));
					i = 0;// 循环接收

				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				i = 0;
			}
		}

	}

	public String parseMessage(String receiveStr) throws Exception {
		String[] packet = receiveStr.split("\\ ");
		String from = packet[0];
		String to = packet[1];
		String type = packet[2];
//		System.out.println(from);
		 String rn = restService.getRn(from, to);

		logger.info("rn："+rn);
		return rn;
	}

	public static void main(String args[]) {
		// new UDPServer();
		try {
			new UDPServer().parseMessage("11231 123 123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}