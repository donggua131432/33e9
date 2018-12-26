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

    private String loginUrl;

    private String authUrl;

    private String rsaModulus;

    private String rsaExponent;

    private String nasPath;

    private String nasDownLoadPath;

    private String monthFilePath;
    private String voicePath;

    private String localVoicePath;



    private String tempVoicePath;



    private String tempVoicePathNas;

    private String h5Home;

    private String dayFilePath;



    private String IvrEccPathNas;

    private String  voiceverifyTempPath;

    private String nasVoiceverifyTempPath;


    public String getIvrEccPathNas() {
        return IvrEccPathNas;
    }
    @Value("${url.ecc.ivrEccPathNas}")
    public void setIvrEccPathNas(String ivrEccPathNas) {
        IvrEccPathNas = ivrEccPathNas;
    }


    public String getDayFilePath() {
        return dayFilePath;
    }

    @Value("${url.call.dayFilePath}")
    public void setDayFilePath(String dayFilePath) {
        this.dayFilePath = dayFilePath;
    }

    public String getTempVoicePath() {
        return tempVoicePath;
    }
    @Value("${url.local.tempvoice.path}")
    public void setTempVoicePath(String tempVoicePath) {
        this.tempVoicePath = tempVoicePath;
    }


    public String getTempVoicePathNas() {
        return tempVoicePathNas;
    }
    @Value("${url.nas.tempvoice.path}")
    public void setTempVoicePathNas(String tempVoicePathNas) {
        this.tempVoicePathNas = tempVoicePathNas;
    }


    public String getLocalVoicePath() {
        return localVoicePath;
    }

    @Value("${url.local.voice.path}")
    public void setLocalVoicePath(String localVoicePath) {
        this.localVoicePath = localVoicePath;
    }

    public String getVoicePath() {
        return voicePath;
    }
    @Value("${url.nas.voice.path}")
    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
    public String getAuthUrl() {
        return authUrl;
    }

    @Value("${url.auth.root}")
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getUrl() {
        return url;
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

    public String getLoginUrl() {
        return loginUrl;
    }

    @Value("${url.login.root}")
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getRsaModulus() {
        return rsaModulus;
    }

    @Value("${rsa.pkey.modulus}")
    public void setRsaModulus(String rsaModulus) {
        this.rsaModulus = rsaModulus;
    }

    public String getRsaExponent() {
        return rsaExponent;
    }

    @Value("${rsa.pkey.exponent}")
    public void setRsaExponent(String rsaExponent) {
        this.rsaExponent = rsaExponent;
    }

    public String getNasPath() {
        return nasPath;
    }


    @Value("${url.nas.path}")
    public void setNasPath(String nasPath) {
        this.nasPath = nasPath;
    }

    public String getNasDownLoadPath() {
        return nasDownLoadPath;
    }
    @Value("${url.nas.downLoadPath}")
    public void setNasDownLoadPath(String nasDownLoadPath) {
        this.nasDownLoadPath = nasDownLoadPath;
    }

    public String getMonthFilePath() {
        return monthFilePath;
    }
    @Value("${url.call.monthFilePath}")
    public void setMonthFilePath(String monthFilePath) {
        this.monthFilePath = monthFilePath;
    }

    public String getH5Home() {
        return h5Home;
    }

    @Value("${url.h5.home}")
    public void setH5Home(String h5Home) {
        this.h5Home = h5Home;
    }

    public String getVoiceverifyTempPath() {return voiceverifyTempPath;}

    @Value("${url.local.voiceverifyTemp.path}")
    public void setVoiceverifyTempPath(String voiceverifyTempPath) {
        this.voiceverifyTempPath = voiceverifyTempPath;
    }

    public String getNasVoiceverifyTempPath() {return nasVoiceverifyTempPath;}

    @Value("${url.nas.voiceverifyTemp.path}")
    public void setNasVoiceverifyTempPath(String nasVoiceverifyTempPath) {this.nasVoiceverifyTempPath = nasVoiceverifyTempPath;}
}