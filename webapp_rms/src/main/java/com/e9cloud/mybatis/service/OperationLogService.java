package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.common.LogType;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.OperationLog;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserAdmin;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志操作相关Service
 * Created by Administrator on 2016/2/23.
 */
public interface OperationLogService extends IBaseService {

    /**
     * 分页查询日志信息
     * @param page 分页信息
     * @return 一页信息
     */
    PageWrapper pageLog(Page page);

    /**
     * 根据Id查询一条日志信息
     * @param id 日志Id
     * @return 日志信息
     */
    OperationLog getLogById(Long id);

    /**
     * 保存操作日志
     * @param menuId 菜单id
     * @param content 日志内容
     * @param logType 操作类型
     *
     */
    void saveOperationLog(String menuId, String content, LogType logType);

    /**
     * 保存操作日志
     * @param user 用户信息
     * @param menuId 菜单id
     * @param content 日志内容
     * @param logType 操作类型
     *
     */
    void saveOperationLogforClient(String menuId, String content, LogType logType, String ip, UserAdmin user);

    /**
     * 保存操作日志
     * @param menuId 菜单id
     * @param content 日志内容
     * @param logType 操作类型
     * @param ip 访问的IP
     *
     */
    void saveOperationLog(String menuId, String content, LogType logType, String ip);

    /**
     * 保存操作日志
     * @param menuId 菜单id
     * @param content 日志内容
     * @param logType 操作类型
     * @param request HttpServletRequest
     *
     */
    void saveOperationLog(String menuId, String content, LogType logType, HttpServletRequest request);
}
