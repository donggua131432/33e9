package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.pcweb.msg.util.TempCode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息管理相关service
 *
 * Created by Administrator on 2016/7/6.
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl implements SysMessageService {

    /**
     * 分页选取消息
     *
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageMsgTemp(Page page) {
        return this.page(Mapper.SysMessage_Mapper.pageMsgTemp, page);
    }

    /**
     * 添加一条消息
     */
    @Override
    public void addMsg(SysMessage message) {
        this.save(Mapper.SysMessage_Mapper.insertSelective, message);
    }

    /**
     * 查询一条消息
     *
     * @param id
     * @return
     */
    @Override
    public SysMessage getSysMessageTempById(String id) {
        return this.findObjectByPara(Mapper.SysMessage_Mapper.selectTempByPrimaryKey, id);
    }

    /**
     * 添加一条消息
     *
     * @param message
     */
    @Override
    public void addMsgTemp(SysMessage message) {
        message.setId(ID.randomUUID());
        message.setSendId(UserUtil.getCurrentUserId() + "");
        message.setCreateTime(new Date());
        message.setStatus("3");
        this.save(Mapper.SysMessage_Mapper.insertTempSelective, message);
    }

    /**
     * 得到所有的待发送邮件
     *
     * @return
     */
    @Override
    public List<SysMessage> getAllTobeSentMsg() {
        SysMessage message = new SysMessage();
        message.setSendTime(new Date());
        return this.findObjectList(Mapper.SysMessage_Mapper.selectAllTobeSentMsg, message);
    }

    /**
     * 更改消息状态
     *
     * @param message
     */
    @Override
    public void updateMsgTempStatus(SysMessage message) {
        this.update(Mapper.SysMessage_Mapper.updateMsgTempStatus, message);
    }
}
