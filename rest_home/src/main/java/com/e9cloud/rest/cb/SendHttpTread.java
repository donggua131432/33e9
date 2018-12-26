package com.e9cloud.rest.cb;

import com.e9cloud.http.HttpParam;
import com.e9cloud.http.HttpResult;
import com.e9cloud.http.exception.HttpProcessException;
import com.e9cloud.http.util.SimpleHttpUtil;
import com.e9cloud.rest.obt.CallNotifyStatus;
import com.e9cloud.rest.service.HttpTask;
import com.e9cloud.util.Tools;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 回调任务发送线程
 * Created by Administrator on 2016/3/18.
 */
public class SendHttpTread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(SendHttpTread.class);

	private Queue<HttpTask> tasks = new ConcurrentLinkedQueue<>();

	private boolean isRun = true;

	public SendHttpTread(){}

	public SendHttpTread(String name, int threadId) {
		super(name + threadId);
	}

	@Override
	public void run() {
		while (isRun) {
			try {
				//logger.info("开发发送");
				long startTime = System.currentTimeMillis();
				send();
				long diff = System.currentTimeMillis() - startTime;
				if (diff < 50) {
					Thread.sleep(50 - diff);
				}
				//logger.info("发送结束");
			} catch (Throwable e) {
				logger.error("刷帧线程出现异常" + e.getMessage() + ",error : {}", e);
			}
		}
		logger.warn("已停止刷帧线程!");
	}

	private void send() {
		HttpTask task = tasks.poll();
		if (task != null) {
			try {
				logger.info("开始发送：{}, {}, {}", task.getCallSid(), task.getUrl(), task.getMsgBody());
				HttpParam param = HttpParam.url(task.getUrl()).jsonData(task.getMsgBody(), "UTF-8")
						.header("Accept" ,"application/xml")
						.header("Content-Type", "application/xml;charset=utf-8")
						.timeout(2500,500).pool(100,10);

				HttpResult result = SimpleHttpUtil.post(param);
				String context = result.getContext();
				logger.info("HttpResult,callsid:{},code:{}, context:{}",task.getCallSid(),result.getCode(),context);
				if(result.getCode()==200){
					logger.info("回调成功 callsid:{}", task.getCallSid());
				}else {
					logger.info("回调失败 callsid:{}", task.getCallSid());
					// 回调失败保存到库
					CallNotifyStatus status = new CallNotifyStatus();
					status.setCallSid(task.getCallSid());
					status.setAction(task.getAction());
					status.setCreateDateTime(new Date());
					status.setStatus(false);
					status.setErrorMsg(result.getCode()+":"+context);
					StatisMsgManager.getInstance().addParseMsg(status);
				}
			} catch (HttpProcessException e) {
				logger.error("send error callId:{} exception:{}",task.getCallSid(),e.getMessage());
				if (!e.getMessage().contains("java.net.SocketTimeoutException")){ // 响应超时
					CallNotifyStatus status = new CallNotifyStatus();
					status.setCallSid(task.getCallSid());
					status.setAction(task.getAction());
					status.setCreateDateTime(new Date());
					status.setStatus(false);
					status.setErrorMsg(e.getMessage());
					StatisMsgManager.getInstance().addParseMsg(status);
				}
			}
		}
	}

	// 添加任务
	public void addTask(HttpTask task){
		logger.info("添加任务", task.getCallSid());
		tasks.offer(task);
	}

	// 添加任务
	public int getTaskSize(int i){
		int size = tasks.size();
		if (size > 1000) {
			logger.info("线程{}：中的任务数量{}", i, size);
		}
		return size;
	}

}
