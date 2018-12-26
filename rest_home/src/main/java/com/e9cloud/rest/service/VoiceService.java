package com.e9cloud.rest.service;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.domain.VoiceTemp;
import com.e9cloud.mybatis.service.VoiceCodeService;
import com.e9cloud.mybatis.service.VoiceNotifyService;
import com.e9cloud.rest.obt.VoiceReq;
import com.e9cloud.rest.voice.DynamicJob;
import com.e9cloud.rest.voice.DynamicSchedulerFactory;
import com.e9cloud.rest.voice.JobParamManager;
import com.e9cloud.rest.voice.VoiceNotifyJob;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.Utils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 2016/9/27.
 */
@Service
public class VoiceService {

    @Autowired
    VoiceNotifyService voiceNotifyService;

    @Autowired
    private SysPropertits sysPropertits;

    @Autowired
    private VoiceCodeService voiceCodeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(VoiceService.class);
    public boolean addJob(VoiceReq voiceReq) {
        final boolean addSuccessful = addVoiceJob(voiceReq.getRequestId(),voiceReq);
        if (!addSuccessful) {
            LOGGER.info("NOTE: Add VoiceJob[jobName={}] failed", voiceReq.getRequestId());
            return false;
        }
        LOGGER.info("NOTE: Add VoiceJob[jobName={}] success", voiceReq.getRequestId());
        return true;
    }

    private boolean addVoiceJob(String requestId, VoiceReq voiceReq) {
        final String jobName = JobParamManager.generateJobName(requestId);
        Date startTime = null;
        try {
            startTime = DateUtil.strToDate(voiceReq.getOrderTime(),"yyyy/MM/dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("requestId",requestId);
        reqMap.put("voiceReq",voiceReq);
        DynamicJob job = new DynamicJob(jobName)
                .startTime(startTime)
                .target(VoiceNotifyJob.class)
                .addJobData(VoiceNotifyJob.VOICENOTIF,reqMap);

        return executeStartup(job);
    }

    private boolean executeStartup(DynamicJob job) {
        boolean result = false;
        try {
                result = DynamicSchedulerFactory.registerFixTimeJob(job);
                LOGGER.info("register  voice schedule[jobname={}] result: {} ",job.jobName(), result);
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Register [" + job + "] failed",job.jobName(), e);
        }
        return result;
    }

    public boolean removeJob(String requestId) {
        final String jobName = JobParamManager.generateJobName(requestId);
        DynamicJob job = new DynamicJob(jobName);
        try {
            if (DynamicSchedulerFactory.existJob(job)) {
                final boolean result = DynamicSchedulerFactory.removeJob(job);
                LOGGER.info("<{}> Remove DynamicJob[{}] result [{}]", job.jobName(), job, result);
                return true;
            }else{
                return  false;
            }
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Remove [" + job + "] failed", job.jobName(), e);
            return false;
        }
    }

    public void syncVoiceNotify(){
        String snCode = sysPropertits.getSnCode();
        List<VoiceReq> list = voiceNotifyService.selectVoiceList(snCode);
        if(list!=null&&list.size()>0){
            for (VoiceReq voiceReq:list){
                    this.addJob(voiceReq);
            }
        }
    }

    /**
     * 添加语音通知信息  并注册到任务中
     * @param voiceReq
     * @return
     */
    public Map<String, String> saveVoiceNotify(VoiceReq voiceReq){
        Map<String, String> map = new HashMap<>();
        voiceReq.setId(ID.randomUUID());
        //判断语音模板是否合法 设置语音模板地址
        VoiceTemp voiceTemp = voiceNotifyService.getVoiceTempById(Integer.parseInt(voiceReq.getVoiceRecId()));
        if(!Utils.notEmpty(voiceTemp) || "01".equals(voiceTemp.getStatus())){
            map.put("respCode",ConstantsEnum.REST_VOICE_RECID.getCode());
            map.put("respDesc",ConstantsEnum.REST_VOICE_RECID.getDesc());
            LOGGER.info(ConstantsEnum.REST_VOICE_RECID.getDesc());
            return map;
        }

        //获取语音编码文件
        String[] vPathArray = voiceTemp.getvPath().split("/");
        String voiceFile = vPathArray[vPathArray.length-1].split("\\.")[0];
        voiceReq.setVoicePath(voiceFile);
        //voiceReq.setVoicePath(voiceTemp.getvPath());
        //是否批量请求  0:否 1：是
        if(voiceReq.getToList().size() <= 1){
            voiceReq.setBatchFlag(0);
        }else{
            voiceReq.setBatchFlag(1);
        }
        voiceReq.setToNum(StringUtils.join(voiceReq.getToList().toArray(),","));
        voiceReq.setSnCode(sysPropertits.getSnCode());
        voiceReq.setIpPort(sysPropertits.getIpPort());
        voiceNotifyService.insertVoice(voiceReq);

        //获取语音编码
        CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("voice");
        //设置语音编码
        if(cbVoiceCode != null) voiceReq.setCodec(cbVoiceCode.getVoiceCode());
        this.addJob(voiceReq);

        map.put("respCode",ConstantsEnum.REST_SUCCCODE.getCode());
        map.put("respDesc",ConstantsEnum.REST_SUCCCODE.getDesc());
        map.put("requestId",voiceReq.getRequestId());
        return map;
    }


}
