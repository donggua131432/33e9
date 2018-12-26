package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SpRegTask;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SpRegTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 云话机注册解绑任务 相关Service
 */
@Service
public class SpRegTaskServiceImpl extends BaseServiceImpl implements SpRegTaskService {

    /**
     * 得到待办任务类别
     *
     * @param envName 环境名
     */
    @Override
    public List<SpRegTask> getToDoTaskList(String envName) {
        return this.findObjectListByPara(Mapper.SpRegTask_Mapper.selectToDoTaskList, envName);
    }

    /**
     * 更改任务状态
     *
     * @param task 任务
     */
    @Override
    public void updateTaskStatus(SpRegTask task) {
        this.update(Mapper.SpRegTask_Mapper.updateStatusByPK, task);
    }

    /**
     * 锁定任务状态
     *
     * @param taskList 即将执行的任务
     */
    @Override
    public void updateTaskStatusForLocked(List<SpRegTask> taskList) {
        this.update(Mapper.SpRegTask_Mapper.updateTaskStatusForLocked, taskList);
    }

    /**
     * 添加一条待办任务
     *
     * @param appid      appid
     * @param shownumid  外显号id
     * @param sipphoneId sip 号码
     * @param fixphoneId 固话id
     * @param taskType   任务类型
     */
    @Override
    public void addToDoTask(String appid, Long shownumid, String sipphoneId, String fixphoneId, String taskType) {
        SpRegTask task = new SpRegTask();

        task.setAppid(appid);
        task.setShownumId(shownumid);
        task.setSipphoneId(sipphoneId);
        task.setFixphoneId(fixphoneId);
        task.setTaskType(taskType);

        this.save(Mapper.SpRegTask_Mapper.insertSelective, task);
    }
}
