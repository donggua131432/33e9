package com.e9cloud.mybatis.service;


import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;

import java.util.List;

/**
 * Created by admin on 2016/12/23.
 */
public interface TaskInfoService extends IBaseService {

    /**
     * 保存任务
     * @param taskHttp 任务的基本信息
     */
    void saveTaskHttp(TaskHttp taskHttp);

    /**
     * 保存任务所对应的url
     * @param urlHttp 任务的基本信息
     */
    void saveUrlHttp(UrlHttp urlHttp);

    void updateUrlHttp(UrlHttp urlHttp);

    /**
     * 分页查询
     * @return
     */
    PageWrapper pageTaskList(Page page);

    /**
     * 删除任务
     * @param taskHttp
     */
    void delTaskHttpInfo(TaskHttp taskHttp);

    /**
     * 修改任务状态值
     * @param taskHttp
     */
    void updateTaskHttp(TaskHttp taskHttp);

    void updateTaskHttpList(TaskHttp taskHttp);

    /**
     *  指定任务下的URL展示
     * @param page
     */
    PageWrapper pageURLList(Page page);


    /**
     * 任务执行相关数据
     * @param executeHttp
     */
    void saveExecuteHttp(ExecuteHttp executeHttp);


    /**
     * 根据id查找一条任务信息
     * @param taskID
     */
    TaskHttp queryTaskHttpById(String taskID);


    //根据任务ID查询URL
    List<UrlHttp> queryListByDId(String taskID);


    ExecuteHttp queryExecuteHttpById(String id);


    void delUrlHttpInfo(String id);
}
