package com.e9cloud.pcweb.msg.biz;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.Tools;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.pcweb.msg.util.TempCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Administrator on 2016/7/7.
 */
@Service
public class MsgService {

    private static final Logger logger = LoggerFactory.getLogger(MsgService.class);

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private UserAdminService userAdminService;

    /**
     * 发送系统站内信
     */
    public void sendMsg(UserAdmin receive, TempCode code, Map<String, Object> params) {
        SysMessage message = new SysMessage();

        message.setId(ID.randomUUID());
        message.setCreateTime(new Date());
        message.setSendTime(new Date());
        message.setSendId(UserUtil.getCurrentUserId() + "");
        message.setTitle(code.getTitle());
        message.setContent(code.getContent(params));
        message.setUid(receive.getUid());

        sysMessageService.addMsg(message);
    }

    /**
     * 发送定时站内信
     */
    public void addTosendMsgTemp() {
        List<SysMessage> messageTemps = sysMessageService.getAllTobeSentMsg();

        if (messageTemps.size() > 0) {
            //
            List<UserAdmin> userAdmins = userAdminService.findAllUserAdminAndBusTypes();

            for (SysMessage msg : messageTemps) {
                sysMessageService.updateMsgTempStatus(msg);

                logger.info("send message temp {} start", msg.getId());
                String[] busTypes = Tools.stringToArray(msg.getBusType());
                for (UserAdmin ua : userAdmins) {
                    if (intersect(busTypes, ua.getBusTypes())) {
                        msg.setBusType("");
                        msg.setStatus("0");
                        msg.setUid(ua.getUid());
                        msg.setTempId(msg.getId());
                        msg.setId(ID.randomUUID());
                        sysMessageService.addMsg(msg);
                    }
                }
                logger.info("send message temp {} end", msg.getId());
            }
        }
    }

    private boolean intersect(String[] strs, String busTypes) {
        if (Tools.isNotNullStr(busTypes)) {
            for (String str : strs) {
                if (busTypes.contains(str)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 定时扫描待发消息
     */
    @PostConstruct
    public void fixUpdateNumState() {
        Timer timer = new Timer("Send MsgTemp Start", false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                logger.info("开始定时扫描待发消息");
                try {
                    addTosendMsgTemp();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("error:{}", e);
                }

                logger.info("消息已发送");
            }
        };
        timer.scheduleAtFixedRate(task, 60 * 1000, 500 * 1000);
    }

}
