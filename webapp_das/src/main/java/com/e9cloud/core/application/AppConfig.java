package com.e9cloud.core.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 类作用：使用properties文件中的静态变量，在容器初始化时加载，默认为singleton
 *
 * @author wzj
 */
@Component
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 247253246087931029L;

	private String url;

	private String resourcesRoot;

	public String getUrl() {
		return url;
	}

	private String nasPath;

	private String voicePath;

	private String nasVoicePath;
	// 间隔秒数
	private String intervalSecond;

	private String nasDownLoadPath;


	private String monthFilePath;

	private String tempRecordFilePath;
	//系统类型

	private String sysType;

	//录音下载建权串
	private String rcdDownloadKey;

	@Value("${pro.url}")
	public void setUrl(String url) {
		this.url = url;
	}

	public String getResourcesRoot() {
		return resourcesRoot;
	}

	@Value("${url.rsc.root}")
	public void setResourcesRoot(String resourcesRoot) {
		this.resourcesRoot = resourcesRoot;
	}

	public String getNasPath() {
		return nasPath;
	}

	@Value("${url.nas.path}")
	public void setNasPath(String nasPath) {
		this.nasPath = nasPath;
	}

	public String getVoicePath() {
		return voicePath;
	}

	@Value("${url.voice.path}")
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}

	public String getNasVoicePath() {
		return nasVoicePath;
	}

	@Value("${url.nas.voice.path}")
	public void setNasVoicePath(String nasVoicePath) {
		this.nasVoicePath = nasVoicePath;
	}

	public String getIntervalSecond() {
		return intervalSecond;
	}

	@Value("${intervalSecond}")
	public void setIntervalSecond(String intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

	public String getMonthFilePath() {
		return monthFilePath;
	}
	@Value("${url.call.monthFilePath}")
	public void setMonthFilePath(String monthFilePath) {
		this.monthFilePath = monthFilePath;
	}

	public String getNasDownLoadPath() {
		return nasDownLoadPath;
	}
	@Value("${url.nas.downLoadPath}")
	public void setNasDownLoadPath(String nasDownLoadPath) {
		this.nasDownLoadPath = nasDownLoadPath;
	}

	public String getSysType() { return sysType; }

	@Value("${sysType}")
	public void setSysType(String sysType) { this.sysType = sysType; }


	public String getTempRecordFilePath() {
		return tempRecordFilePath;
	}

	@Value("${temp.record.downLoadPath}")
	public void setTempRecordFilePath(String tempRecordFilePath) {
		this.tempRecordFilePath = tempRecordFilePath;
	}

	@Value("${rcd.download.key}")
	public void setRcdDownloadKey(String rcdDownloadKey) {
		this.rcdDownloadKey = rcdDownloadKey;
	}

	public String getRcdDownloadKey() {

		return rcdDownloadKey;
	}

}