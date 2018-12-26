package com.e9cloud.pcweb.sipPhone.biz;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.HttpUtil;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.TempVoice;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.TempVoiceAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by dell on 2016/12/6.
 */
@Service
public class TempVoiceService {
    private static final Logger logger = LoggerFactory.getLogger(TempVoiceService.class);

    @Autowired
    private TempVoiceAuditService tempVoiceAuditService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private CbTaskService cbTaskService;


    public void doTask() {
        String urlPath =appConfig.getUrlTempvoicePath();
        String nasTempVoicePath =appConfig.getNasTempVoicePath();
        String fileName="";

        // 得到待执行任务列表
        List<TempVoice> taskList = tempVoiceAuditService.getNotTransTempVoice("");
        if (taskList.size() < 1) return;
        for(TempVoice tempVoice:taskList){
            fileName = "voice" + ID.randomUUID() + ".wav";
            String appid = tempVoice.getAppid();
            String url = urlPath + "filename=" + fileName + "&appid=" + appid+"&type=vn";

            String content=tempVoice.gettContent();

            String r = HttpUtil.postUrl(url,content);

            logger.info("语音模板转换appid:" + appid +" id:" + tempVoice.getId()+ "返回值:" + r);

            if ("00".equals(r)){

                //修改数据库的v_path, v_url
                tempVoice.setvUrl(appid + "/" + fileName);
                tempVoice.setvPath(appid + "/" + fileName);
                tempVoiceAuditService.updateAuditStatus(tempVoice);

                //保存tb_cb_task表
                CbTask cbTask = new CbTask();

                int num = nasTempVoicePath.indexOf("/web");
                String filePath = nasTempVoicePath.substring(num+4,nasTempVoicePath.length())+appid + "/" + fileName;

                Map<String, Object> paramJson = new LinkedHashMap<>();
                paramJson.put("type", CbTask.TaskType.files.toString());
                paramJson.put("filePath", filePath);
                paramJson.put("operation", "add");

                cbTask.setType(CbTask.TaskType.files);
                cbTask.setParamJson(JSonUtils.toJSon(paramJson));
                cbTaskService.saveCbTask(cbTask);
            }
        }
    }

    @PostConstruct
    public void taskTempVoice() {
        Timer timer = new Timer("task temp voice", false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                logger.info("开始 语音模版转换定时任务");
                doTask();
                logger.info("结束 语音模版转换定时任务");
            }
        };

        // 下一次执行时间相对于 上一次 实际执行完成的时间点
        timer.schedule(task, 60 * 1000, 60 * 1000);
    }
}
