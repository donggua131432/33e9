package com.e9cloud.common;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/17.
 */
public class ThreadAndTimerTest {
    static int i = 0;
    public static void main(String[] args) {
        Timer timer = new Timer("register sipphone Start", false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                System.out.println("开始 执行云话机注册-解绑任务");
                try {
                    doTask(); // 定时 执行云话机注册-解绑任务
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error:{}");
                }

                System.out.println("结束 执行云话机注册-解绑任务");
            }
        };

        // 下一次执行时间相对于 上一次 实际执行完成的时间点
        timer.schedule(task, 5 * 1000, 5 * 1000);
    }

    private static void doTask() {
        i ++;
        for (int j=0;j<10;j++) {
            try {
                Thread.sleep(1 * 1000);
                System.out.println(Thread.currentThread().getName() + i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
