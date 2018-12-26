package com.e9cloud.pcweb.record.action.biz;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mongodb.domain.DownLoadWrapper;
import com.e9cloud.mybatis.service.CommonService;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by wangqing on 2016/12/8.
 * 文件下载线程管理
 */

public class DownloadRecordManager {

    CommonService commonService = SpringContextHolder.getBean(CommonService.class);
    private static DownloadRecordManager instance = null;
    private DownLoadThread[] downLoadThread;
    private int threadNum = 3;
    private Queue<DownLoadWrapper> messages = new ConcurrentLinkedQueue<DownLoadWrapper>();

    public static DownloadRecordManager getInstance() {
        if (instance == null) {
            synchronized (DownloadRecordManager.class) {
                if (instance == null) {
                    instance = new DownloadRecordManager();
                }
            }
        }
        return instance;
    }
    public void init(){

        //获取所有的文件下载列表，放入到队列中
        List<DownLoadWrapper> list = commonService.findDownloadList();
        if(list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++) {
                messages.offer(list.get(i));
            }
        }

        //初始化开启多个线程
        downLoadThread = new DownLoadThread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            downLoadThread[i] = new DownLoadThread("DownLoadThread#", i);
            downLoadThread[i].start();
        }


    }

    public  Queue<DownLoadWrapper> getQueue(){
        return messages;
    }

    public void addQueue(DownLoadWrapper downLoadWrapper){
        messages.offer(downLoadWrapper);
    }
}
