package com.e9cloud.rest.service;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.BusinessType;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.BusinessTypeService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.cb.CBTcpClientHandler;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.util.JSonUtils;
import com.e9cloud.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 回调的入池、出池操作
 * Created by wzj on 2017/5/17.
 */
public class CallBackTaskPool {

    private static final Logger logger = LoggerFactory.getLogger(CallBackTaskPool.class);

    // 防止多处调用时出现并发问题
    private CallBackTaskPool(){}

    private static  CallBackTaskPool callBackTaskPool = new CallBackTaskPool();

    public static CallBackTaskPool instance(){
        return callBackTaskPool;
    }

    String poolName = "CallBackTaskPool";

    // 用五个#作为分隔符
    public static String SPILT = "#####";

    public long LEN = 2000; // 一次性取出条数

    SysPropertits sysPropertits = SpringContextHolder.getBean(SysPropertits.class);
    JedisClusterUtil jedisClusterUtil = SpringContextHolder.getBean(JedisClusterUtil.class);
    AppInfoService appInfoService = SpringContextHolder.getBean(AppInfoService.class);
    BusinessTypeService businessTypeService = SpringContextHolder.getBean(BusinessTypeService.class);

    /**
     * 将符合条件的回调放到redis中
     * @return
     */
    public void addParseMsg(CallNotifyHttp callNotifyHttp) {

        logger.info("CallBackTaskPool run for app: {}", callNotifyHttp.getAppId());

        AppInfo appInfo = appInfoService.getAppInfoByAppid(callNotifyHttp.getAppId());

        // 回调地址不正常，返回对应的错误码报文
        if (appInfo == null) {
            logger.info("无回拨REST应用信息");
        }else if(!"02".equals(appInfo.getBusType()) && !"05".equals(appInfo.getBusType())){
            logger.info("无回拨REST应用信息");
        } else if (!"00".equals(appInfo.getStatus())) {
            logger.info("该回拨REST应用不正常");
        } else if (!"00".equals(appInfo.getUrlStatus())) {
            logger.info("该回拨REST应用回调url不启用");
        } else {
            if (10 == callNotifyHttp.getType()) { // 虚拟小号业务
                //判断小号业务类型是否开通
                Map<String, Object> mapParam = new HashMap<>();
                mapParam.put("sid",appInfo.getSid());
                mapParam.put("busType","07");
                mapParam.put("status","00");
                BusinessType businessType = businessTypeService.getBusinessType(mapParam);
                if(businessType == null){
                    logger.info("该回拨REST应用虚拟小号业务未开通");
                }else{
                    // 回调地址正常，进行对应报文回调通知
                    String requestUrl = appInfo.getCallbackUrl();
                    String xml = callNotifyHttp.toXML("req");
                    logger.info("回调信息存入redis中 url:{},xml:{}", requestUrl, xml);
                    jedisClusterUtil.LISTS.rpush(redisPoolName(), callNotifyHttp.getCallSid() + SPILT + callNotifyHttp.getAction() + SPILT + requestUrl + SPILT + xml);
                }
            } else {
                String requestUrl = appInfo.getCallbackUrl();
                String xml = callNotifyHttp.toXML("req");
                //云话机挂机回调
                if (Utils.notEmpty(callNotifyHttp.getFeeUrl())) {
                    requestUrl = callNotifyHttp.getFeeUrl();
                }
                logger.info("回调信息存入redis中 url:{},xml:{}", requestUrl, xml);
                jedisClusterUtil.LISTS.rpush(redisPoolName(), callNotifyHttp.getCallSid() + SPILT + callNotifyHttp.getAction() + SPILT + requestUrl + SPILT + xml);
            }
        }

    }

    /**
     * 取出一定数量的回调任务
     * @return
     */
    public List<String> poll() {
        long length = jedisClusterUtil.LISTS.llen(poolName + sysPropertits.getSnCode());
        if (length > 0) {
            length = length < LEN ? length : LEN;
            List<String> cnhs = jedisClusterUtil.LISTS.lrange(redisPoolName(), 0, length);
            jedisClusterUtil.LISTS.ltrim(redisPoolName(), Long.valueOf(length).intValue(), -1);
            logger.info("从redis中取出 {} 条任务", cnhs.size());
            return cnhs;
        }

        return null;
    }

    public String redisPoolName() {
        return poolName + sysPropertits.getSnCode();
    }

}
