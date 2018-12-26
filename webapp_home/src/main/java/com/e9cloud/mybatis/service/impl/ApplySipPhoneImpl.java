package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ApplySipPhoneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
@Service
public class ApplySipPhoneImpl extends BaseServiceImpl implements ApplySipPhoneService {

    /**
     * 查询所有申请记录
     *
     * @param page
     * @return
     */
    public PageWrapper getAllApplyRecord(Page page){
        return this.page(Mapper.SipPhone_Mapper.getAllApplyRecord, page);
    }

    /**
     * 根据id查询申请记录
     *
     * @param id
     * @return
     */
    public SipPhoneApply getApplyRecordById(String id){
        return this.findObjectByPara(Mapper.SipPhone_Mapper.getApplyRecordById, id);
    }

    /** 单个删除申请记录 **/
    @Override
    public void delApplyRecord(String id) {
        this.delete(Mapper.SipPhone_Mapper.delApplyRecord,id);
    }

    /**
     * 查询应用号码列表
     *
     * @param page
     * @return
     */
    public PageWrapper getSipPhoneNumList(Page page){
        return this.page(Mapper.SipPhone_Mapper.getSipPhoneNumList, page);
    }

    /**
     * 根据id查询sipphone号码
     *
     * @param id
     * @return
     */
    public SpApplyNum getSpApplyNumById(Long id){
        return this.findObject(Mapper.SipPhone_Mapper.getSpApplyNumById, id);
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteSpApplyNum(String[] id) {
        for (String i : id) {
            SpApplyNum applyNum = this.findObject(Mapper.SipPhone_Mapper.selectByPrimaryKey, Long.valueOf(i));
            updateSipPhoneStauts(applyNum.getSipphoneId(), "03");
            long fixs = findObject(Mapper.SipPhone_Mapper.countSpApplyNumByFixId, applyNum);
            if (fixs == 0) {
                updateFixPhoneStauts(applyNum.getFixphoneId(), "03");
            }
            this.update(Mapper.SipPhone_Mapper.updateShowNumStatus, Long.valueOf(i));
            saveSpRegTask(applyNum,"DEL");

        }
    }

    // 更改sipphone和fixphone的状态
    private void updateSipPhoneStauts(String sipphoneId, String status) {
        SipPhone sipPhone = new SipPhone();
        sipPhone.setId(sipphoneId);
        sipPhone.setStatus(status);
        this.update(Mapper.SipPhone_Mapper.updateSipPhone, sipPhone);
    }

    private void updateFixPhoneStauts(String fixphoneId, String status) {
        FixPhone fixPhone = new FixPhone();
        fixPhone.setId(fixphoneId);
        fixPhone.setStatus(status);
        this.update(Mapper.SipPhone_Mapper.updateFixPhone, fixPhone);
    }

    private void saveSpRegTask(SpApplyNum applyNum, String taskType) {
        SipPhoneRegTask spTask = new SipPhoneRegTask();
        spTask.setAppid(applyNum.getAppid());
        spTask.setShowNumId(applyNum.getId());
        spTask.setSipphoneId(applyNum.getSipphoneId());
        spTask.setFixphoneId(applyNum.getFixphoneId());
        spTask.setTaskType(taskType);
        this.save(Mapper.SipPhone_Mapper.saveDelShowNumToTAsk, spTask);
    }

    /** 编辑外显号码 **/
    public void updateShowNum(SpApplyNum spApplyNum){
        this.update(Mapper.SipPhone_Mapper.updateShowNum, spApplyNum);
    }

    /**
     * 导出报表 号码列表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadSipNumReport(Page page) {
        return this.download(Mapper.SipPhone_Mapper.downloadSipNumReport, page);
    }

    /**
     * 申请号码
     * @param sipPhoneApply
     * @return
     */
    @Override
    public void saveSipPhoneApply(SipPhoneApply sipPhoneApply) {
        this.save(Mapper.SipPhone_Mapper.saveSipPhoneApply,sipPhoneApply);
    }

    /** 往审核表里插入新编辑外显号码 **/
    @Override
    public void saveShowNumToAudio(SpApplyNum spApplyNum){
        this.save(Mapper.SipPhone_Mapper.saveShowNumToAudio,spApplyNum);
    }

    /**
     * 云话机数据统计
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageDataStatistics(Page page) {
        return this.page(Mapper.SpDayRecord_Mapper.pageSpDayRecordList, page);
    }

    @Override
    public List<Map<String, Object>> downloadDataStatisticsReport(Page page) {
        return  this.download(Mapper.SpDayRecord_Mapper.downloadSpDayRecordList, page);
    }

    /**
     * 云话机消费统计-日报
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageConsumptionStatistics(Page page) {
        return this.page(Mapper.SpDayRecord_Mapper.pageConsumptionStatistics, page);
    }

    /**
     * 导出云话机消费统计-日报
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadConsumptionStatisticsList(Page page) {
        return  this.download(Mapper.SpDayRecord_Mapper.downloadConsumptionStatisticsList, page);
    }

    /**
     * 云话机消费统计-月报
     * @param page
     * @return
     */
    @Override
    public PageWrapper pageMonthConsumptionStatistics(Page page) {
        return this.page(Mapper.SpDayRecord_Mapper.pageMonthConsumptionStatistics, page);
    }

    /**
     * 导出云话机消费统计-月报
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadMonthConsumStatisticsList(Page page) {
        return  this.download(Mapper.SpDayRecord_Mapper.downloadMonthConsumStatisticsList, page);
    }

    /**
     * 导出云话机消费统计-月报明细
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadStatSipphoneConsumeRecordList(Page page) {
        return  this.download(Mapper.SpDayRecord_Mapper.downloadStatSipphoneConsumeRecordList, page);
    }

    /**
     * 开启/关闭长途，开启/禁用号码
     *
     * @param applyNum
     */
    @Override
    public void updateSipstatus(SpApplyNum applyNum) {
        this.update(Mapper.SipPhone_Mapper.updateSipStatus, applyNum);
    }


    /**
     * 开启/关闭长途，开启/禁用号码-------ECC 云总机业务
     *
     * @param eccExtention
     */

    @Override
    public void updateEccstatus(EccExtention eccExtention) {
        this.update(Mapper.EccExtention_Mapper.updateEccstatus, eccExtention);
    }

}
