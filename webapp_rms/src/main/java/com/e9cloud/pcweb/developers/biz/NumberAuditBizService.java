package com.e9cloud.pcweb.developers.biz;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.service.*;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import org.activiti.engine.impl.bpmn.data.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 处理号码审核 业务逻辑
 * Created by Administrator on 2016/9/23.
 */
@Service
public class NumberAuditBizService {

    @Autowired
    private AppNumberService appNumberService;

    @Autowired
    private AppVoiceService appVoiceService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppNumberRestService appNumberRestService;

    @Autowired
    private UserAdminService userAdminService;

    // 批量审核
    public Map<String, String> auditAllExecute(String ids, String appId, String comment, String status) {

        Map<String, String> map = new HashMap<String, String>();

        Map result = appVoiceService.getmobileByAppid(appId);
        String mobile = result.get("mobile").toString();
        String sid = result.get("sid").toString();
        String cname = result.get("name").toString();
        String appname = result.get("app_name").toString();

        String[] idarr = Tools.stringToArray(ids, "-");

        Date now = new Date();

        if("01".equals(status)) {
            for (String id : idarr) {
                AppNumber appNumber = new AppNumber();
                appNumber.setId(Long.valueOf(id));
                appNumber.setRemark(comment);
                appNumber.setNumberStatus(status);
                appNumber.setReviewtime(now);

                AppNumber appNumber1 = appNumberService.getAppNumberById(Long.valueOf(id));
                if("02".equals(appNumber1.getNumberType())) {
                    String number[] = appNumber1.getNumber().split("-");
                    String num0 = number[0].substring(number[0].length()-4, number[0].length());
                    String num1 = number[1].substring(number[1].length()-4, number[1].length());
                    Long length = Long.valueOf(num1) - Long.valueOf(num0);

                    if(length > 0 && length <= 999) {
                        appNumberService.updateAppNumber(appNumber);
                        List<AppNumberRest> appNumberRestList = new ArrayList<AppNumberRest>();

                        for (int i = 0; i <= length; i++) {
                            AppNumberRest appNumberRest = new AppNumberRest();
                            String preNum = number[0].substring(0, number[0].length() - 4);
                            String sufNum = String.valueOf(Long.valueOf(num0) + i);
                            if (sufNum.length() < 4) {
                                switch (4 - sufNum.length()) {
                                    case 1:
                                        sufNum = "0" + sufNum;
                                        break;
                                    case 2:
                                        sufNum = "00" + sufNum;
                                        break;
                                    case 3:
                                        sufNum = "000" + sufNum;
                                        break;
                                }
                            }
                            String newNumber = preNum + sufNum;
                            appNumberRest.setAddtime(new Date());
                            appNumberRest.setAppid(appNumber1.getAppid());
                            appNumberRest.setNumberId(appNumber1.getId());
                            appNumberRest.setNumber(newNumber);
                            appNumberRestList.add(appNumberRest);
                        }
                        appNumberRestService.saveAppNumberList(appNumberRestList);
                    }

                } else {
                    AppNumberRest appNumberRest = new AppNumberRest();
                    appNumberRest.setAddtime(new Date());
                    appNumberRest.setAppid(appNumber1.getAppid());
                    appNumberRest.setNumberId(appNumber1.getId());
                    appNumberRest.setNumber(appNumber1.getNumber());
                    appNumberService.updateAppNumber(appNumber);
                    appNumberRestService.saveAppNumber(appNumberRest);
                }
            }

            map.put("status", "0");

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_SUCCESS, null);

            LogUtil.log("号码批量审核", "accountID：" + sid + " ，企业名称：" + cname + " ，应用名称：" + appname + "审核通过。", LogType.UPDATE);

        } else {

            for (String id : idarr) {
                AppNumber appNumber = new AppNumber();
                appNumber.setId(Long.valueOf(id));
                appNumber.setRemark(comment);
                appNumber.setNumberStatus(status);
                appNumber.setReviewtime(now);
                appNumberService.updateAppNumber(appNumber);
            }

            map.put("status", "0");

            // 发送站内信
            Sender.sendMessage(userAdminService.findUserAdminSID(sid), TempCode.SEND_SMS_NUM_ERROR, null);

            //操作日志工具类(审核不通过)
            LogUtil.log("号码批量审核", "accountID：" + sid + " ，企业名称：" + cname + " ，应用名称：" + appname + " ， 审核未通过。原因：" + comment, LogType.UPDATE);
        }

        return map;
    }

}
