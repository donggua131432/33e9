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

	//系统类型
	private String sysType;

	// 语音通知本地路径
	private String localTempVoicePath;

	// 语音通知NAS路径
	private String nasTempVoicePath;

	//语音模版转换url
	private String urlTempvoicePath;

	//云总机IVR提示音
	private String ivrVoicePath;

	// 语音验证码本地路径
	private String localVoiceVerifyTempPath;

	// 语音验证码NAS路径
	private String nasVoiceVerifyTempPath;


	public String getIvrVoicePath() {
		return ivrVoicePath;
	}

	@Value("${url.ivr.path}")
	public void setIvrVoicePath(String ivrVoicePath) {
		this.ivrVoicePath = ivrVoicePath;
	}

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

	public String getSysType() { return sysType; }

	@Value("${sysType}")
	public void setSysType(String sysType) { this.sysType = sysType; }

	public String getLocalTempVoicePath() {
		return localTempVoicePath;
	}

	@Value("${url.local.tempvoice.path}")
	public void setLocalTempVoicePath(String localTempVoicePath) {
		this.localTempVoicePath = localTempVoicePath;
	}

	public String getNasTempVoicePath() {
		return nasTempVoicePath;
	}

	@Value("${url.nas.tempvoice.path}")
	public void setNasTempVoicePath(String nasTempVoicePath) {
		this.nasTempVoicePath = nasTempVoicePath;
	}

	public String getUrlTempvoicePath() {return urlTempvoicePath;}

	@Value("${url.tempvoice.path}")
	public void setUrlTempvoicePath(String urlTempvoicePath) {this.urlTempvoicePath = urlTempvoicePath;}



	public String getNasVoiceVerifyTempPath() {
		return nasVoiceVerifyTempPath;
	}
	@Value("${url.nas.voiceverifyTemp.path}")
	public void setNasVoiceVerifyTempPath(String nasVoiceVerifyTempPath) {
		this.nasVoiceVerifyTempPath = nasVoiceVerifyTempPath;
	}


	public String getLocalVoiceVerifyTempPath() {
		return localVoiceVerifyTempPath;
	}

	@Value("${url.local.voiceverifyTemp.path}")
	public void setLocalVoiceVerifyTempPath(String localVoiceVerifyTempPath) {
		this.localVoiceVerifyTempPath = localVoiceVerifyTempPath;
	}

}