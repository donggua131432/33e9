package com.e9cloud.cache;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.CodeType;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CodeTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shenliang on 2016/9/30.
 */
public class CacheSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheSchedule.class);
    /**
     * 定时任务执行方法，更新缓存数据
     */
    public void execute() {
        LOGGER.info(" CacheSchedule  execute start");
        AppInfoService appInfoService = SpringContextHolder.getBean(AppInfoService.class);
        CodeTypeService codeTypeService = SpringContextHolder.getBean(CodeTypeService.class);
        List<AppInfo> apps = appInfoService.findAll();
        if (apps != null) {
            for (AppInfo appInfo : apps) {
                CacheManager.setAppInfo(appInfo);
            }
        }
        List<CodeType> types = codeTypeService.findAll();
        if (types != null) {
            for (CodeType codeType : types) {
                CacheManager.setCodeType(codeType);
            }
        }
        LOGGER.info(" CacheSchedule  execute end");
    }
}
