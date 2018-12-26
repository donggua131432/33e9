package com.e9cloud.pcweb.sipPhone.biz;

import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.SpNumAudit;
import com.e9cloud.mybatis.service.AppVoiceService;
import com.e9cloud.mybatis.service.ShowNumApplyService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理外显号号码审核 业务逻辑
 * Created by hzd on 2016/11/1.
 */
@Service
public class ShowNumAuditBizService {
    @Autowired
    private ShowNumApplyService showNumApplyService;
    @Autowired
    private AppVoiceService appVoiceService;
    @Autowired
    private UserAdminService userAdminService;

    // 批量审核
    public Map<String, String> auditAllExecute(String appid,String audioIds, String auditCommon, String auditStatus) {
        Map<String, String> map = new HashMap<String, String>();
        Map result = appVoiceService.getmobileByAppid(appid);
        String sid = result.get("sid").toString();

        String[] audioStr = audioIds.split("-");
        Long[] audioStr1 = new Long[audioStr.length];
        for (int i = 0; i < audioStr.length; i++) {
            audioStr1[i] = Long.valueOf(audioStr[i]);
        }
        List<SpNumAudit> spNumAuditList = showNumApplyService.getSpNumAuditByIds(audioStr1);
        if(spNumAuditList.size()!=0){
            int flag = 0;//设置标记，等全部判断后才进行入库
            for(SpNumAudit sa:spNumAuditList){
                SpNumAudit spNumAuditNew = new SpNumAudit();
                spNumAuditNew = showNumApplyService.getSpNumAuditByShowNumId(sa.getShowNumId());
                //如果批量审核的与最新审核记录不相同，则
                if (!sa.getId().equals(spNumAuditNew.getId())){
                    flag++;
                }
            }
            //如果flag大于0，则批量审批里有最新外显号修改，则直接提示
            if(flag>0) {
                map.put("status", "2");
            }else {
                //正常进行审批流程
                List<SpApplyNum> spApplyNumList = new ArrayList<SpApplyNum>();
                List<SpNumAudit> spNumAuditUpdateList = new ArrayList<SpNumAudit>();
                try {
                    if("01".equals(auditStatus)){
                        //审核通过
                        for (SpNumAudit spNumAudit:spNumAuditList){
                            SpApplyNum spApplyNum = new SpApplyNum();
                            spApplyNum.setId(spNumAudit.getShowNumId());
                            spApplyNum.setShowNum(spNumAudit.getShowNum());
                            spApplyNumList.add(spApplyNum);
                        }
                        for (SpNumAudit spNumAudit:spNumAuditList){
                            SpNumAudit spNumAudit1 = new SpNumAudit();
                            spNumAudit1.setId(spNumAudit.getId());
                            spNumAudit1.setAuditStatus("01");
                            spNumAudit1.setAuditCommon(auditCommon);
                            spNumAuditUpdateList.add(spNumAudit1);
                        }

                        showNumApplyService.updateAppNumberList(spApplyNumList,spNumAuditUpdateList);
                        // 发送站内信
                        Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_SUCCESS, null);
                        map.put("status", "0");
                    }else{
                        //审核不通过
                        spApplyNumList = null;
                        for (SpNumAudit spNumAudit:spNumAuditList){
                            SpNumAudit spNumAudit1 = new SpNumAudit();
                            spNumAudit1.setId(spNumAudit.getId());
                            spNumAudit1.setAuditStatus("02");
                            spNumAudit1.setAuditCommon(auditCommon);
                            spNumAuditUpdateList.add(spNumAudit1);
                        }
                        showNumApplyService.updateAppNumberList(spApplyNumList,spNumAuditUpdateList);
                        // 发送站内信
                        Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_ERROR, null);
                        map.put("status", "0");
                    }
                }catch (Exception e){
                    map.put("status", "1");
                }
            }
        }else{
            map.put("status", "1");
        }

        return map;

    }
}
