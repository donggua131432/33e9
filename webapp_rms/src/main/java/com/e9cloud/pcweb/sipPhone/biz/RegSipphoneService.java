package com.e9cloud.pcweb.sipPhone.biz;

import com.e9cloud.core.util.HttpUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.SPIPMapper;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.SpRegTask;
import com.e9cloud.mybatis.service.SPIPMapperService;
import com.e9cloud.mybatis.service.SpRegTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 定时任务--1分钟执行一次
 *
 * 注册 sipphone
 * Created by Administrator on 2016/11/7.
 *
 * Appid：玖云平台的用户中心的appid；
 * Ip：服务器的注册地址；
 * port：sip注册的端口；（如果当前没有，则启动新的profile，开发优先级P1）
 * user：sip注册认证的号码；
 * Pwd：sip注册的认证密码。
 *
 * 返回值
 * 00——操作成功；
 * 01——参数错误；
 * 02——找不到注册端口；
 * 03——sipphone号码已经被其他appid用户占用；
 * 04——该号码不在appid下
 */
@Service
public class RegSipphoneService {

    private static final Logger logger = LoggerFactory.getLogger(RegSipphoneService.class);

    @Autowired
    private SPIPMapperService spipMapperService;

    @Autowired
    private SpRegTaskService spRegTaskService;

    /**
     * 执行云话机注册-解绑任务
     * @param envName 环境名称
     */
    public void doTask(String envName) {

        // 得到待执行任务列表
        List<SpRegTask> taskList = spRegTaskService.getToDoTaskList(envName);

        logger.info("待执行的云话机务数量:{}", taskList.size());

        if (taskList.size() < 1) return;

        // spRegTaskService.updateTaskStatusForLocked(taskList); // 锁定任务状态

        // 按照sip号码进行分组
        Map<String, List<SpRegTask>> tasks = taskList.stream().collect(Collectors.groupingBy(SpRegTask::getSipphoneId));

        tasks.forEach((sipphoneid, task)->{

            logger.info("开始执行任务sipphoneid：{}", sipphoneid);

            taskExecute(task);

            logger.info("结束执行任务sipphoneid：{}", sipphoneid);

        });

    }

    public void taskExecute(List<SpRegTask> task) {

        for (SpRegTask t : task) {

            // 外网ip为空
            if (Tools.isNullStr(t.getOuterIp())) {
                logger.info("sipphone 对应的 外网地址为空, 跳过执行");
                t.setTaskStatus("08");
                t.setTaskTime(new Date());
                spRegTaskService.updateTaskStatus(t); // 更改任务状态 成功标为02，失败标为00， 08 外网ip为空 ,09 对应的内网ip为空
                break;
            }

            String register = "00";
            String r = "";
            if ("ADD".equals(t.getTaskType())) {
                register = "01";
                r = add(t);
            }
            if ("DEL".equals(t.getTaskType())) {
                register = "00";
                r = del(t);
            }

            // 对应的内网ip为空
            if ("09".equals(r)) {
                t.setTaskStatus("09");
                t.setTaskTime(new Date());
                spRegTaskService.updateTaskStatus(t); // 更改任务状态 成功标为02，失败标为00， 08 外网ip为空 ,09 对应的内网ip为空
                break;
            }

            // 如果失败，则终止运行sipphone的该任务列表
            if (!"00".equals(r)) break;

            // 成功时：更新注册状态
            try {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("register", register);
                params.put("id", t.getShownumId());
                spipMapperService.updateShowNumRegStatus(params);
            } catch (Exception e) {
                logger.info("更新外显号码的状态时出现错误：{}", e);
            }

            t.setTaskStatus("02");
            t.setTaskTime(new Date());
            spRegTaskService.updateTaskStatus(t); // 更改任务状态 成功标为02，失败标为00
        }
    }

    // http://内网ip:8185/register?method=del&user=xxxx&appid=xxxxx // 解绑
    public String del(SpRegTask task) {
        String r = "";

        if (Tools.isNullStr(task.getInnerIp())) {
            logger.info("sipphone 对应的内网地址为空");
            r = "09";
            return r;
        }

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("method", "del");
            params.put("appid", task.getAppid());
            params.put("user", task.getSipphone());

            String url = "http://" +  task.getInnerIp() + "/register";

            r = HttpUtil.get(url, params);

            logger.info("解除绑定sipphone时返回的状态码：{}", r);
        } catch (Exception e) {
            logger.warn("解除绑定sipphone时出现错误:{}", e);
            r = "02";
        }

        return r;

    }

    // http://内网ip:8185/register?method=add&appid=xxx&ip=xxx.xxx.xxx.xxx&port=xxx&user=xxx&pwd=xxx // 注册
    public String add(SpRegTask task) {

        String r = "";

        if (Tools.isNullStr(task.getInnerIp())) {
            logger.info("sipphone 对应的内网地址为空");
            r = "09";
            return r;
        }

        try {
            String outerIp = task.getOuterIp();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("method", "add");
            params.put("appid", task.getAppid());
            params.put("ip", outerIp.split(":")[0]);
            params.put("port", outerIp.split(":")[1]);
            params.put("user", task.getSipphone());
            params.put("pwd", task.getPwd());

            String url = "http://" + task.getInnerIp() + "/register";
            r = HttpUtil.get(url, params);

            logger.info("注册sipphone时返回的状态码：{}", r);
        } catch (Exception e) {
            logger.warn("注册sipphone时出现错误:{}", e);
            r = "02";
        }

        return r;

    }

    /**
     * 定时扫描待发消息
     */
    @PostConstruct
    public void registerSipphone() {
        String envName = System.getProperty("envName");
        logger.info("the envName is {}", envName);

        Timer timer = new Timer("register sipphone Start", false);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                logger.info("开始 执行云话机注册-解绑任务");
                try {
                    doTask(envName); // 定时 执行云话机注册-解绑任务
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("error:{}", e);
                }

                logger.info("结束 执行云话机注册-解绑任务");
            }
        };

        // 下一次执行时间相对于 上一次 实际执行完成的时间点
        timer.schedule(task, 60 * 1000, 60 * 1000);
    }

}
