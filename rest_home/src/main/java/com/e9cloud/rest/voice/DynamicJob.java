package com.e9cloud.rest.voice;

import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import java.util.Date;
import java.util.Map;

public class DynamicJob {


    //要执行类, 实现Job接口
    private Class<? extends Job> target;

    //cron 表达式
    private String cronExpression;

    private String jobGroup = Scheduler.DEFAULT_GROUP;

    //必须唯一
    private String jobName;

    private Date startTime;//调度开始时间
    private Date endTime;//调度结束时间

    private int repeatCount = 0;//重复执行次数
    private long repeatInterval = 1;//执行时间隔间

    private transient TriggerKey triggerKey;
    private transient JobDetail jobDetail;

    private transient SimpleTriggerImpl trigger;

    //default
    public DynamicJob() {
    }

    public DynamicJob(String jobName) {
        this.jobName = jobName;
    }

    public Class<? extends Job> target() {
        return target;
    }

    public DynamicJob target(Class<? extends Job> target) {
        this.target = target;
        return this;
    }


    public DynamicJob cronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
        return this;
    }

    public String jobGroup() {
        return jobGroup;
    }

    public DynamicJob jobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        return this;
    }

    public String jobName() {
        return jobName;
    }

    public DynamicJob jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public TriggerKey triggerKey() {
        if (triggerKey == null) {
            triggerKey = TriggerKey.triggerKey(this.jobName, this.jobGroup);
        }
        return triggerKey;
    }

    public JobDetail jobDetail() {
        if (jobDetail == null) {
            jobDetail = JobBuilder.newJob(target)
                    .withIdentity(this.jobName, this.jobGroup).storeDurably(true)
                    .build();
        }
        return jobDetail;
    }

    public SimpleTriggerImpl simpleTrigger(){
        if(trigger==null){
            trigger  =  new SimpleTriggerImpl();
            trigger.setKey(triggerKey);
            trigger.setJobName(jobDetail.getKey().getName());
            trigger.setStartTime(startTime);
            trigger.setEndTime(endTime);
            trigger.setRepeatCount(repeatCount);
            trigger.setRepeatInterval(repeatInterval);
        }
        return trigger;
    }

    /*
   * 传参数给 执行的 job
   * 在job中 通过
   *  context.getMergedJobDataMap().get(key) 获取值
   * */
    public DynamicJob addJobData(String key, Object value) {
        final JobDetail detail = jobDetail();
        final JobDataMap jobDataMap = detail.getJobDataMap();
        jobDataMap.put(key, value);
        return this;
    }

    /*
   * 传参数给 执行的 job
   * 在job中 通过
   *  context.getMergedJobDataMap().get(key) 获取值
   * */
    public DynamicJob addJobDataMap(Map<String, Object> map) {
        final JobDetail detail = jobDetail();
        final JobDataMap jobDataMap = detail.getJobDataMap();
        jobDataMap.putAll(map);
        return this;
    }

    public String cronExpression() {
        return this.cronExpression;
    }


    public Date startTime() {
        return this.startTime;
    }

    public DynamicJob startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date endTime() {
        return this.endTime;
    }

    public DynamicJob endTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public int  repeatCount() {
        return this.repeatCount;
    }

    public DynamicJob repeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public long  repeatInterval() {
        return this.repeatInterval;
    }

    public DynamicJob repeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
        return this;
    }

    public CronTrigger cronTrigger() {
        final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(this.cronExpression);
        return TriggerBuilder.newTrigger().withIdentity(triggerKey())
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{target=").append(target);
        sb.append(", cronExpression='").append(cronExpression).append('\'');
        sb.append(", jobGroup='").append(jobGroup).append('\'');
        sb.append(", jobName='").append(jobName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}