package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SysMessage;

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
    PageWrapper pageMsg(Page page);


    String countHadRead (String uid);

    String countUnRead (String uid);

    /**
     * 添加一条消息
     * @param message
     */
    void addMsg(SysMessage message);

    void updateStatusBylink(String[] strId);

    void deleteStatusBylink(String[] strId);

    void readUpStatus(String strId);

    /**
     * 根据消息选择一条消息
     * @param strId
     * @return
     */
    SysMessage getSysMessageById(String strId);
}
