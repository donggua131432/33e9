package com.e9cloud.pcweb.msg.biz;

import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.pcweb.msg.util.TempCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/7.
 */
@Service
public class MsgService {

    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 添加一条消息
     */
    public void sendMsg(User receive, TempCode code, Map<String, Object> params) {
        SysMessage message = new SysMessage();

        message.setId(ID.randomUUID());
        message.setCreateTime(new Date());
        message.setSendTime(new Date());
        message.setTitle(code.getTitle());
        message.setContent(code.getContent(params));
        message.setUid(receive.getUid());

        sysMessageService.addMsg(message);
    }


}
