package com.e9cloud.rest.cb;

/**
 * Created by Administrator on 2016/4/19.
 */
public abstract class AppMessage implements Runnable{
	//执行次数
	private int times;

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public abstract void run();
}