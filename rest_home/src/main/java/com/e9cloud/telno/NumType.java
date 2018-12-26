package com.e9cloud.telno;

public enum NumType {
	/**
	 * 显号手机
	 */
	PHONE("1"),
	/**
	 * 显号固话
	 */
	FIXED("2"),
	/**
	 * 显号及目的地均是手机
	 */
	PHONE_DEST("3"),
	/**
	 * 显号95
	 */
	NINE_FIVE("4"),
	/**
	 * 显号400
	 */
	FOUR_ZERO_ZERO("5"),
	/**
	 * 无显号号码
	 */
	NO_NUM("6"),
	/**
	 * 指定链路
	 */
	APPOINT_LINK("9");

	String value;

	private NumType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
