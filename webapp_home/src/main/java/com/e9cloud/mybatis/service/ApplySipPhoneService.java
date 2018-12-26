package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccExtention;
import com.e9cloud.mybatis.domain.SipPhoneApply;
import com.e9cloud.mybatis.domain.SpApplyNum;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/10/26.
 */
public interface ApplySipPhoneService extends IBaseService {

    /**
     * 查询所有申请记录
     *
     * @param page
     * @return
     */
    PageWrapper getAllApplyRecord(Page page);

    /**
     * 根据id查询申请记录
     *
     * @param id
     * @return
     */
    SipPhoneApply getApplyRecordById(String id);

    /** 单个删除申请记录 **/
    void delApplyRecord(String id);

    /**
     * 查询应用号码列表
     *
     * @param page
     * @return
     */
    PageWrapper getSipPhoneNumList(Page page);

    /**
     * 根据id查询申请记录
     *
     * @param id
     * @return
     */
    SpApplyNum getSpApplyNumById(Long id);

    /**
     * 删除云话机外显号
     * @param id shownumIds
     */
    void deleteSpApplyNum(String[] id);

    /** 编辑外显号码 **/
    void updateShowNum(SpApplyNum spApplyNum);

    /** 往审核表里插入新编辑外显号码 **/
    void saveShowNumToAudio(SpApplyNum spApplyNum);

    /**
     * 导出报表 号码列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadSipNumReport(Page page);

    /**
     * 申请号码
     * @param sipPhoneApply
     * @return
     */
    public void saveSipPhoneApply(SipPhoneApply sipPhoneApply);

    /**
     * 云话机数据统计
     * @param page
     * @return
     */
    public PageWrapper pageDataStatistics(Page page);

    /**
     * 导出书籍统计
     * @param page
     * @return
     */
    public List<Map<String, Object>> downloadDataStatisticsReport(Page page);

    /**
     * 云话机消费统计-日报
     * @param page
     * @return
     */
    public PageWrapper pageConsumptionStatistics(Page page);

    /**
     * 导出云话机消费统计-日报
     * @param page
     * @return
     */
    public List<Map<String, Object>>  downloadConsumptionStatisticsList(Page page);

    /**
     * 云话机消费统计-月报
     * @param page
     * @return
     */
    public PageWrapper pageMonthConsumptionStatistics(Page page);

    /**
     * 导出云话机消费统计-月报
     * @param page
     * @return
     */
    public List<Map<String, Object>>  downloadMonthConsumStatisticsList(Page page);

    /**
     * 导出云话机消费统计-月报明细
     * @param page
     * @return
     */
    public List<Map<String, Object>>  downloadStatSipphoneConsumeRecordList(Page page);

    /**
     * 开启/关闭长途，开启/禁用号码
     * @param applyNum
     */
    void updateSipstatus(SpApplyNum applyNum);



    /**
     * 开启/关闭长途，开启/禁用号码-------ECC 云总机业务
     * @param eccExtention
     */
    void updateEccstatus(EccExtention eccExtention);
}
