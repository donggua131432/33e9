package com.e9cloud.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * ip 获取用户的ip
 * Created by Administrator on 2016/2/25.
 */
public class IP {

    private static final Logger logger = LoggerFactory.getLogger(IP.class);

    public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        logger.info("ip-1:{}", ip);

        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }

        ip = request.getHeader("X-Real-IP");
        logger.info("ip-2:{}", ip);

        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }

        ip = request.getRemoteAddr();
        logger.info("ip-3:{}", ip);

        return ip;
    }

}
