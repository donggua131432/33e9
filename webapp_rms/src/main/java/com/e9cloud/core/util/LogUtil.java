package com.e9cloud.core.util;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.LogType;
import com.e9cloud.mybatis.service.OperationLogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/5/23.
 */
public class LogUtil {

    /**
     * 保存操作日志
     *
     * @param menu  菜单id
     * @param content 日志内容
     * @param logType 操作类型
     */
    public static void log(String menu, String content, LogType logType){
        try {
            ServletRequest request = ((WebSubject) SecurityUtils.getSubject()).getServletRequest();
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//            new Thread(){
//                @Override
//                public void run(){
                    OperationLogService operationLogService = SpringContextHolder.getBean(OperationLogService.class);
                    operationLogService.saveOperationLog(menu, content, logType, httpServletRequest);
//                }
//            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
