package com.e9cloud.rest.voice;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.service.VoiceNotifyService;
import com.e9cloud.rest.cb.CBTcpClient;
import com.e9cloud.rest.cb.Server;
import com.e9cloud.rest.obt.VoiceReq;
import com.e9cloud.rest.service.RestService;
import com.e9cloud.util.Constants;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.Tools;
import com.google.gson.Gson;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class VoiceNotifyJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceNotifyJob.class);
    public static final String VOICENOTIF = "voicenotify";
    VoiceNotifyService voiceNotifyService = SpringContextHolder.getBean(VoiceNotifyService.class);
    private static String version = InitApp.getInstance().getValue("rest.version", "");


    RestService restService = SpringContextHolder.getBean(RestService.class);

    public VoiceNotifyJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try{
            final JobKey key = context.getJobDetail().getKey();
            LOGGER.info("*****  Start execute Job [{}]", key);
            Map<String,Object> reqMap  = (Map<String, Object>) context.getMergedJobDataMap().get(VOICENOTIF);
            String requestId = (String)reqMap.get("requestId");
            VoiceReq voiceReq =(VoiceReq) reqMap.get("voiceReq");
            List<String> toList = voiceReq.getToList();
            voiceReq.setToList(null);
            String toNum = voiceReq.getToNum();
            boolean status = false;
            if(Tools.isNotNullStr(toNum)){
                for (String to:toNum.split(",")){
                    // CB报文头4域值
                    String v1 = version;// 版本号
                    String v2 = String.valueOf(System.currentTimeMillis());// 消息流水号
                    String v3 = requestId;// 32位标识号
                    String v4 = ConstantsEnum.REST_VOICE_REQUEST_CODE.getStrValue();// 消息类型
                    String v5 = voiceReq.getSnCode();// 设备码
                   // voiceReq.setToNum(to);
                    voiceReq.setTo(to);
                    voiceReq.setVoiceFile(voiceReq.getVoicePath());
                    voiceReq.setCallSid(voiceReq.getRequestId());
                    //设置RN码
                    voiceReq.setRn_a(getVoiceRna(voiceReq.getAppId(),voiceReq.getDisplayNum(),to));
                    if(StringUtils.isEmpty(voiceReq.getOrderTime())){
                        voiceReq.setOrderTime(DateUtil.dateToStr(new Date(),"yyyy/MM/dd HH:mm:ss"));
                    }
                    voiceReq.setOrderTime(voiceReq.getOrderTime().replace(" ", "|"));
                    String body = new Gson().toJson(voiceReq);
                    // 调用CB发送方法
                    Server server = InitApp.getInstance().getBestServer();
                    status = InitApp.getInstance().sendMsg(CBTcpClient.hbMsg(v1, v2, v3, v4, v5, body),
                            server);
                    if(status) {
                        //将同步状态改为已同步
                        VoiceReq uVoice = new VoiceReq();
                        uVoice.setId(voiceReq.getId());
                        uVoice.setSyncFlag(1);
                        voiceNotifyService.updateVoice(uVoice);
                    }
                    LOGGER.info("&&&&&  End execute Job [{}]", key);
                }

             }
        }
        catch (Exception e){
            LOGGER.error("&&&&&   execute Job error ", e);
        }
    }


    /**
     * 设置语音通知的RN码
     * @param appid
     * @param displayNum
     * @param to
     * @return
     */
    private String getVoiceRna(String appid, String displayNum, String to){
        //获取业务和app路由编码
        AppInfo appInfo = CacheManager.getAppInfo(appid);
        String routeCode = restService.routeCode(appInfo, Constants.BusinessCode.VOICE);
        // 主叫显号运营商+区号
        String rna = restService.getRn(displayNum, to);
        return routeCode + rna;
    }
}