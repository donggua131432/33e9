package com.e9cloud.telno;

/**
 * Created by Administrator on 2016/5/13.
 */
public enum Operators {
	/**
	 * 移动
	 */
	UNICOM("00"),
	/**
	 * 联通
	 */
	MOBILE("01"),
	/**
	 * 电信
	 */
	TELECOM("02");

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private Operators(String value) {
		this.value = value;
	}
}
