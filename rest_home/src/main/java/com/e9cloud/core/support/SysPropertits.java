package com.e9cloud.core.support;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/3/18.
 */
@Component
public class SysPropertits {
    private String envName;
    private String snCode;
    private String ipPort;

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getEnvName() {
        return envName;
    }
    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }
}
