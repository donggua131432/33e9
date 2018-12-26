package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SysMessage;

import java.util.List;

/**
 * 消息管理相关service
 * Created by Administrator on 2016/7/6.
 */
public interface SysMessageService extends IBaseService {

    /**
     * 分页选取消息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageMsgTemp(Page page);

    /**
     * 发送一条消息
     * @param message
     */
    void addMsg(SysMessage message);

    /**
     * 查询一条消息
     * @param id
     * @return
     */
    SysMessage getSysMessageTempById(String id);

    /**
     * 添加一条消息
     * @param message
     */
    void addMsgTemp(SysMessage message);

    /**
     * 得到所有的待发送邮件
     * @return
     */
    List<SysMessage> getAllTobeSentMsg();

    /**
     *  更改消息状态
     * @param message
     */
    void updateMsgTempStatus(SysMessage message);
}
