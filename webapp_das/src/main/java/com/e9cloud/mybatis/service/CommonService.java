package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.domain.DownLoadWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.domain.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface CommonService extends IBaseService{

    /**
     * 插入下载记录
     * @param wrapper 用户下载信息
     */
    void saveDownLoadInfo(DownLoadWrapper wrapper);
    /**
     * 修改
     * @param wrapper
     */
    void updateDownLoadInfo(DownLoadWrapper wrapper);

    /**
     * 查询所有所有可下载的记录
     */
    PageWrapper findNotDelList(Page page);

    /**
     * 检查当前文件名是否存在
     */
    boolean  checkFileName(String fileName);

    /**
     * 获取所有需要下载的文件列表
     */
    List<DownLoadWrapper> findDownloadList();

}
