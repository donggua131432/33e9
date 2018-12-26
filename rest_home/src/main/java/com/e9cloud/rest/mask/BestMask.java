package com.e9cloud.rest.mask;

public class BestMask {
	private String appId;
	// 区号
	private String arean;
	// 隐私号
	private String maskNum;
	// 隐私映射id
	private String maskId;

	// 号码有效时间
	private String validTime;
	// 录音标志
	private String needRecord;

	private String codec; //语音编码

	public String getCodec() {
		return codec;
	}

	public void setCodec(String codec) {
		this.codec = codec;
	}

	public String getArean() {
		return arean;
	}

	public void setArean(String arean) {
		this.arean = arean;
	}

	public String getMaskNum() {
		return maskNum;
	}

	public void setMaskNum(String maskNum) {
		this.maskNum = maskNum;
	}

	public String getMaskId() {
		return maskId;
	}

	public void setMaskId(String maskId) {
		this.maskId = maskId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getNeedRecord() {
		return needRecord;
	}

	public void setNeedRecord(String needRecord) {
		this.needRecord = needRecord;
	}

}
