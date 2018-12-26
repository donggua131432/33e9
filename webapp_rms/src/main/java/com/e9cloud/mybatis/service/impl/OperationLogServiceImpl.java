package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.util.IP;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.OperationLog;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.OperationLogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志操作相关Service
 * Created by Administrator on 2016/2/23.
 */
@Service
public class OperationLogServiceImpl extends BaseServiceImpl implements OperationLogService {

    /**
     * 分页查询日志信息
     *
     * @param page 分页信息
     * @return 一页信息
     */
    @Override
    public PageWrapper pageLog(Page page) {
        return this.page(Mapper.OperationLog_Mapper.countLog, Mapper.OperationLog_Mapper.pageLog, page);
    }

    /**
     * 根据Id查询一条日志信息
     *
     * @param id 日志Id
     * @return 日志信息
     */
    @Override
    public OperationLog getLogById(Long id) {
        return this.findObject(Mapper.OperationLog_Mapper.selectByPrimaryKey, id);
    }

    /**
     * 保存操作日志
     *
     * @param menu  菜单
     * @param content 日志内容
     * @param logType 操作类型
     * @param ip      访问的IP
     */
    @Override
    public void saveOperationLog(String menu, String content, LogType logType, String ip) {
        User user = UserUtil.getCurrentUser();
        OperationLog operationLog = new OperationLog();

        operationLog.setCreateTime(new Date());
        operationLog.setUsername(user.getUsername());
        operationLog.setUserId(user.getId());
        operationLog.setNick(user.getNick());
        operationLog.setLogName(menu);
        operationLog.setLogContent(content);
        operationLog.setLogType(logType.toString());
        operationLog.setRoleName("");
        operationLog.setIp(ip);

        this.save(Mapper.OperationLog_Mapper.insertSelective, operationLog);
    }

    /**
     * 保存操作日志
     *
     * @param menu  菜单id
     * @param content 日志内容
     * @param logType 操作类型
     * @param request HttpServletRequest
     */
    @Override
    public void saveOperationLog(String menu, String content, LogType logType, HttpServletRequest request) {
        this.saveOperationLog(menu, content, logType, IP.getIp(request));
    }

    /**
     * 保存操作日志
     *
     * @param menu  菜单id
     * @param content 日志内容
     * @param logType 操作类型
     */
    @Override
    public void saveOperationLog(String menu, String content, LogType logType){
        ServletRequest request = ((WebSubject) SecurityUtils.getSubject()).getServletRequest();
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        this.saveOperationLog(menu, content, logType, httpServletRequest);
    }

    /**
     * 保存操作日志for客户
     *
     * @param menu  菜单id
     * @param content 日志内容
     * @param logType 操作类型
     * @param userAdmin    用户信息
     */
    @Override
    public void saveOperationLogforClient(String menu, String content, LogType logType, String ip, UserAdmin userAdmin) {
        OperationLog operationLog = new OperationLog();

        operationLog.setCreateTime(new Date());
        operationLog.setUsername(userAdmin.getEmail());
        operationLog.setUserId(0);
        operationLog.setSid(userAdmin.getSid());
        operationLog.setNick(userAdmin.getName());
        operationLog.setLogName(menu);
        operationLog.setLogContent(content);
        operationLog.setLogType(logType.toString());
        operationLog.setRoleName("");
        operationLog.setIp(ip);

        this.save(Mapper.OperationLog_Mapper.insertSelective, operationLog);
    }
}
