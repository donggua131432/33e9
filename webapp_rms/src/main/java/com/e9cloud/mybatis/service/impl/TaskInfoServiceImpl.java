package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.TaskInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/12/23.
 */
@Service
public class TaskInfoServiceImpl  extends BaseServiceImpl implements TaskInfoService {

    /**
     * 保存应用
     *
     * @param taskHttp 应用的基本信息
     */
    @Override
    public void saveTaskHttp(TaskHttp taskHttp){
        this.save(Mapper.TaskHttp_Mapper.insertSelective, taskHttp);
    }

    /**
     * 保存任务所对应的url
     *
     * @param urlHttp
     */
    @Override
    public void saveUrlHttp(UrlHttp urlHttp){
        this.save(Mapper.UrlHttp_Mapper.insertSelective, urlHttp);
    }


    @Override
    public void updateUrlHttp(UrlHttp urlHttp) {
        this.update(Mapper.UrlHttp_Mapper.updateUrlHttp, urlHttp);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageTaskList(Page page) {
        return this.page(Mapper.TaskHttp_Mapper.pageTaskList, page);
    }

    /**
     * 删除任务
     * @param taskHttp
     */
    @Override
    public void delTaskHttpInfo(TaskHttp taskHttp){
        this.delete(Mapper.TaskHttp_Mapper.delTaskHttpInfo, taskHttp);
    }

    /**
     * 修改任务
     * @param taskHttp
     */
    @Override
    public void updateTaskHttp(TaskHttp taskHttp) {
        this.update(Mapper.TaskHttp_Mapper.updateTaskHttp, taskHttp);
    }

    @Override
    public void updateTaskHttpList(TaskHttp taskHttp) {
        this.update(Mapper.TaskHttp_Mapper.updateTaskHttpList, taskHttp);
    }

    /**
     *  指定任务下的URL展示
     * @param page
     */
    @Override
    public PageWrapper pageURLList(Page page) {
        return this.page(Mapper.UrlHttp_Mapper.pageURLList, page);
    }


    /**
     * 任务执行相关数据
     * @param executeHttp
     */
    @Override
    public void saveExecuteHttp(ExecuteHttp executeHttp){
        this.save(Mapper.ExecuteHttp_Mapper.insertSelective, executeHttp);
    }

    /**
     * 根据id查找一条任务信息
     * @param taskID
     */
    @Override
    public TaskHttp queryTaskHttpById(String taskID){
        return this.findObjectByPara(Mapper.TaskHttp_Mapper.queryTaskHttpById, taskID);
    }

    /**
     * 根据任务ID查询URL
     *
     * @return list
     */
    @Override
    public List<UrlHttp> queryListByDId(String taskID) {
        return this.findObjectListByPara(Mapper.UrlHttp_Mapper.queryListByDId, taskID);
    }


    /**
     * 根据id查找一条任务信息
     * @param id
     */
    @Override
    public ExecuteHttp queryExecuteHttpById(String id){
        return this.findObjectByPara(Mapper.ExecuteHttp_Mapper.queryExecuteHttpById, id);
    }


    @Override
    public void delUrlHttpInfo(String id){
         this.delete(Mapper.UrlHttp_Mapper.delUrlHttpInfo, id);
    }

}
