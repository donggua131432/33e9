package com.e9cloud.pcweb.record.action.biz;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mongodb.domain.DownLoadWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * Created by Administrator on 2016/12/8.
 */

public class DownLoadThread extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(DownLoadThread.class);

    DownLoadService downLoadService = SpringContextHolder.getBean(DownLoadService.class);

    private boolean isRun = true;
    private int threadId;
    public DownLoadThread(String threadName, int threadId) {
        this.setName(threadName + threadId);
        this.threadId = threadId;
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                logger.error("消息处理线程出现未被捕获的异常", e);
            }
        });
    }

    @Override
    public void run() {
        while (isRun) {
            try {
                Queue<DownLoadWrapper> queue = DownloadRecordManager.getInstance().getQueue();
                while (!queue.isEmpty()) {
                    DownLoadWrapper downLoadWrapper = queue.poll();
                    if(downLoadWrapper==null){
                        return;
                    }
                    long now = System.currentTimeMillis();
                    logger.info("thead {} is gen fileName {}",Thread.currentThread().getName(),downLoadWrapper.getFileName());
                    downLoadService.genRecord(downLoadWrapper);
                    long diff = System.currentTimeMillis() - now;
                    logger.info("fileName {} gen time is {}",downLoadWrapper.getFileName(),diff);
                }

            } catch (Throwable e) {
                logger.error("刷帧线程出现异常" + e.getMessage(), e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.warn("已停止刷帧线程!");
    }
}
