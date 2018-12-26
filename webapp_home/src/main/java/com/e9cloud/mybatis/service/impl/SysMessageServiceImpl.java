package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.pcweb.msg.util.TempCode;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public PageWrapper pageMsg(Page page) {
        return this.page(Mapper.SysMessage_Mapper.pageMsg, page);
    }

    /**
     * 添加一条消息
     */
    @Override
    public void addMsg(SysMessage message) {
        this.save(Mapper.SysMessage_Mapper.insertSelective, message);
    }

    @Override
    public String countHadRead(String uid) {
        return this.findObject(Mapper.SysMessage_Mapper.countHadRead, uid);
    }

    @Override
    public String countUnRead(String uid) {
        return this.findObject(Mapper.SysMessage_Mapper.countUnRead, uid);
    }

    @Override
    public void updateStatusBylink(String[] strId) {
        this.update(Mapper.SysMessage_Mapper.updateByPrimaryKeySelective, strId);
    }
    @Override
    public void deleteStatusBylink(String[] strId) {
        this.update(Mapper.SysMessage_Mapper.deleteStatusBylink, strId);
    }

    @Override
    public void readUpStatus(String strId) {
        this.update(Mapper.SysMessage_Mapper.readUpStatus, strId);
    }

    /**
     * 根据消息选择一条消息
     *
     * @param strId
     * @return
     */
    @Override
    public SysMessage getSysMessageById(String strId) {
        return this.findObjectByPara(Mapper.SysMessage_Mapper.selectByPrimaryKey, strId);
    }
}
