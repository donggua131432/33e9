package com.e9cloud.rest.cb;

import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.rest.service.CallBackTaskPool;
import com.e9cloud.rest.service.HttpTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/3/18.
 */
public class AppIdTreadManager {

	private static AppIdTreadManager appIdTreadManager = new AppIdTreadManager();
	private SendHttpTread[] sendHttpTreads;
	private int threadNum = 10;

	private AppIdTreadManager() {
	}

	private static final Logger logger = LoggerFactory.getLogger(AppIdTreadManager.class);

	public static AppIdTreadManager getInstance() {
		return appIdTreadManager;
	}

	public void init() {
		start();

		sendHttpTreads = new SendHttpTread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			sendHttpTreads[i] = new SendHttpTread("SendHttpTread#", i);
			sendHttpTreads[i].start();
		}
	}

	/***
	 * 添加task发送任务
	 * 
	 * @param task
	 */
	public void addTask(HttpTask task) {
		if (task != null) {
			int i = Math.abs(Objects.hash(task.getCallSid())%threadNum);
			sendHttpTreads[i].addTask(task);
			logger.info("添加消息." + "线程id：{}， callId {}:", i, task.getCallSid());
		}
	}

	public int getMaxSize(){
		int max = 0;
		for (int i=0; i<threadNum; i++) {
			if (sendHttpTreads[i] != null) {
				int size = sendHttpTreads[i].getTaskSize(i);
				max = max > size ? max : size;
			}
		}
		return max;
	}


	/**
	 * 解析消息队列
	 */
	private final Queue<CallNotifyHttp> paresMsgTaskQueue = new ConcurrentLinkedQueue<CallNotifyHttp>();

	/***
	 * 开始解析消息
	 */
	public void start() {
		addToRedis(); // 存入redis
		addToHttpPool(); // 存入发送的线程池
	}

	private void addToRedis(){
		CallBackTaskPool taskPool = CallBackTaskPool.instance();
		Runnable addToRedis = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						long startTime = System.currentTimeMillis();
						CallNotifyHttp callNotifyHttp = paresMsgTaskQueue.poll();
						if (callNotifyHttp != null) {
							logger.info("开始解析cb消息");
							taskPool.addParseMsg(callNotifyHttp);
							logger.info("结束解析cb消息");
						}
						long diff = System.currentTimeMillis() - startTime;
						if (diff < 50){
							Thread.sleep(50 - diff);
						}
					} catch (Exception e) {
						logger.error("AppIdTreadManager-start: {}", e);
					}
				}
			}
		};
		Thread thread = new Thread(addToRedis, "接收消息线程");
		thread.start();
	}

	private void addToHttpPool(){
		CallBackTaskPool taskPool = CallBackTaskPool.instance();
		Runnable addToHttpPool = new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if (getMaxSize() < 1000) {
							List<String> taskstrs = taskPool.poll();
							if (taskstrs != null && taskstrs.size() > 0) {
								taskstrs.stream().forEach(str -> {
									String[] arr = str.split(CallBackTaskPool.SPILT);

									HttpTask task = new HttpTask();
									task.setCallSid(arr[0]);
									task.setAction(arr[1]);
									task.setUrl(arr[2]);
									task.setMsgBody(arr[3]);

									addTask(task);
								});
							} else {
								Thread.sleep(500);
							}
						} else {
							Thread.sleep(500);
						}

					} catch (Exception e) {
						logger.error("AppIdTreadManager-start: {}", e);
					}
				}
			}
		};
		Thread thread = new Thread(addToHttpPool, "存入发送的线程池");
		thread.start();
	}

	/**
	 * 添加解析消息
	 * 
	 * @param callNotifyHttp
	 */
	public void addParseMsg(CallNotifyHttp callNotifyHttp) {
		logger.info("将CB消息添加到task队列中 callId {}", callNotifyHttp.getCallSid());
		paresMsgTaskQueue.offer(callNotifyHttp);
		if ("Hangup".equals(callNotifyHttp.getAction())||"VoiceNotify".equals(callNotifyHttp.getAction())) {
			StatisMsgManager.getInstance().addParseMsg(callNotifyHttp);
		}
	}

	/**
	 * 添加解析消息
	 *
	 * @param callNotifyHttp
	 */
	public void addParseMsg(CallNotifyHttp callNotifyHttp, String feeUrl) {
		logger.info("将CB消息添加到task队列中 callId {}", callNotifyHttp.getCallSid());
		callNotifyHttp.setFeeUrl(feeUrl);
		paresMsgTaskQueue.offer(callNotifyHttp);
		//StatisMsgManager.getInstance().addParseMsg(callNotifyHttp);
	}
}
