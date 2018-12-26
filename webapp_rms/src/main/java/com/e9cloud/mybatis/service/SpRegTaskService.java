package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SpRegTask;

import java.util.List;

/**
 * 云话机注册解绑任务 相关Service
 */
public interface SpRegTaskService extends IBaseService {

    /**
     * 得到待办任务类别
     * @param envName 环境名
     */
    List<SpRegTask> getToDoTaskList(String envName);

    /**
     * 更改任务状态
     * @param task 任务
     */
    void updateTaskStatus(SpRegTask task);

    /**
     * 锁定任务状态
     * @param taskList 即将执行的任务
     */
    void updateTaskStatusForLocked(List<SpRegTask> taskList);

    /**
     * 添加一条待办任务
     * @param appid appid
     * @param shownumid 外显号id
     * @param sipphoneId sip 号码
     * @param fixphoneId 固话id
     * @param taskType 任务类型
     */
    void addToDoTask(String appid, Long shownumid, String sipphoneId, String fixphoneId, String taskType);
}
