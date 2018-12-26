package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mongodb.domain.DownLoadWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
@Service
public class CommonServiceImpl  extends BaseServiceImpl implements CommonService{
    /**
     * 获取所有需要下载的文件列表
     */
    @Override
    public List<DownLoadWrapper> findDownloadList() {
        return this.findObjectList(Mapper.DownLoadWrapper_Mapper.findDownList,null);
    }

    /**
     * 检查当前文件名是否存在
     */
    @Override
    public boolean checkFileName(String fileName) {
        DownLoadWrapper wrapper = this.findObject(Mapper.DownLoadWrapper_Mapper.checkFile,fileName);
        if(wrapper!=null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 插入下载记录
     *
     * @param wrapper 用户下载信息
     */
    @Override
    public void saveDownLoadInfo(DownLoadWrapper wrapper) {
        this.save(Mapper.DownLoadWrapper_Mapper.insertDownSelective,wrapper);
    }

    /**
     * 修改
     *
     * @param wrapper
     */
    @Override
    public void updateDownLoadInfo(DownLoadWrapper wrapper) {
        this.update(Mapper.DownLoadWrapper_Mapper.updateDownSelective,wrapper);
    }

    /**
     * 查询所有所有可下载的记录
     */
    @Override
    public PageWrapper findNotDelList(Page page) {
        return this.page(Mapper.DownLoadWrapper_Mapper.findNotDelList,page);

    }
}
