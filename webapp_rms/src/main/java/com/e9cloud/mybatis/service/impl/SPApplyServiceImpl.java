package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SPApplyService;
import com.e9cloud.mybatis.service.SpRegTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 云话机号码审请分配相关 Service
 * Created by wzj on 2016/10/30.
 */
@Service
public class SPApplyServiceImpl extends BaseServiceImpl implements SPApplyService {

    @Autowired
    private SpRegTaskService taskService; // 云话机注册任务列表

    /**
     * 分页查询申请列表
     *
     * @param page 分页参数
     * @return PageWrapper
     */
    @Override
    public PageWrapper pageApplyList(Page page) {
        return this.page(Mapper.SipPhoneApply_Mapper.pageApplyList, page);
    }

    /**
     * 选择申请信息包含城市和省份名称
     *
     * @param id
     * @return SipPhoneApply
     */
    @Override
    public SipPhoneApply getApplyWithCityById(String id) {
        return this.findObjectByPara(Mapper.SipPhoneApply_Mapper.selectApplyWithCity, id);
    }

    /**
     * 分页查询申请号码列表
     *
     * @param page
     * @return PageWrapper
     */
    @Override
    public PageWrapper pageNumList(Page page) {
        return this.page(Mapper.SpApplyNum_Mapper.pageNumList, page);
    }

    /**
     * 删除云话机外显号
     *
     * @param id shownumIds
     */
    @Override
    public void deleteShowNums(String[] id) {
        for (String i : id) {
            SpApplyNum applyNum = this.findObject(Mapper.SpApplyNum_Mapper.selectByPrimaryKey, Long.valueOf(i));
            updateSipPhoneStauts(applyNum.getSipphoneId(), "03");
            long fixs = findObject(Mapper.SpApplyNum_Mapper.countSpApplyNumByFixId, applyNum);
            if (fixs == 0) {
                updateFixPhoneStauts(applyNum.getFixphoneId(), "03");
            }
            this.update(Mapper.SpApplyNum_Mapper.updateShowNumStatus, Long.valueOf(i));

            taskService.addToDoTask(applyNum.getAppid(), applyNum.getId(), applyNum.getSipphoneId(), applyNum.getFixphoneId(), "DEL"); // 添加一条待办任务
        }
    }

    /**
     * 查询外显号表报
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadShowNumList(Page page) {
        return this.download(Mapper.SpApplyNum_Mapper.pageNumList, page);
    }

    /**
     * 查询申请记录表报
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadApplyList(Page page) {
        return this.download(Mapper.SipPhoneApply_Mapper.pageApplyList, page);
    }

    /**
     * 后台添加一条申请记录
     *
     * @param apply
     */
    @Override
    public void addApply(SipPhoneApply apply) {
        this.save(Mapper.SipPhoneApply_Mapper.insertSelective, apply);
    }

    /**
     * 后台添加一个外显号，并审核通过
     *
     * @param applyNum 外显号
     */
    @Override
    public void addShowNum(SpApplyNum applyNum) {
        this.save(Mapper.SpApplyNum_Mapper.insertSelective, applyNum); // 添加外显号

        updateSipPhoneStauts(applyNum.getSipphoneId(), "01"); // 设为已分配
        updateFixPhoneStauts(applyNum.getFixphoneId(), "01"); // 设为已分配

        // saveAuidtInfoWithoutAudit(applyNum);

        taskService.addToDoTask(applyNum.getAppid(), applyNum.getId(), applyNum.getSipphoneId(), applyNum.getFixphoneId(), "ADD"); // 添加一个任务
    }

    /**
     * 修改外显号码
     *
     * @param applyNum
     */
    @Override
    public void updateShowNum(SpApplyNum applyNum) {
        this.update(Mapper.SpApplyNum_Mapper.updateByPrimaryKeySelective, applyNum); // 添加外显号
        saveAuidtInfoWithoutAudit(applyNum);
    }

    /**
     * 后台添加一个外显号，并审核通过
     *
     * @param applyNum 外显号
     */
    @Override
    public void saveShowNum(SpApplyNum applyNum) {
        this.save(Mapper.SpApplyNum_Mapper.insertSelectiveAndCopyShowNum, applyNum); // 添加外显号

        updateSipPhoneStauts(applyNum.getSipphoneId(), "01"); // 设为已分配
        updateFixPhoneStauts(applyNum.getFixphoneId(), "01"); // 设为已分配

        // saveAuidtInfoWithoutAudit(applyNum);

        taskService.addToDoTask(applyNum.getAppid(), applyNum.getId(), applyNum.getSipphoneId(), applyNum.getFixphoneId(), "ADD"); // 添加一个任务
    }

    // 直接插入一条成功的审核记录
    private void saveAuidtInfoWithoutAudit(SpApplyNum applyNum) {

        logger.info(JSonUtils.toJSon(applyNum));
        SpNumAudit audit = new SpNumAudit();
        audit.setAuditStatus("01"); // 审核通过
        audit.setShowNum(applyNum.getShowNum());
        audit.setShowNumId(applyNum.getId());

        this.save(Mapper.ShowNumApply_Mapper.insertNumAuditSelective, audit); // 添加一条审核记录
    }

    // 更改sipphone和fixphone的状态
    private void updateSipPhoneStauts(String sipphoneId, String status) {
        SipPhone sipPhone = new SipPhone();
        sipPhone.setId(sipphoneId);
        sipPhone.setStatus(status);
        this.update(Mapper.SipPhone_Mapper.updateByPrimaryKeySelective, sipPhone);
    }

    private void updateFixPhoneStauts(String fixphoneId, String status) {
        FixPhone fixPhone = new FixPhone();
        fixPhone.setId(fixphoneId);
        fixPhone.setStatus(status);
        this.update(Mapper.FixPhone_Mapper.updateByPrimaryKeySelective, fixPhone);
    }

    /**
     * 选择一定数量的sip 号码 （未分配的）
     *
     * @param params sip 号码数
     * @return
     */
    @Override
    public List<SipPhone> getSipPhonesByAllot(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.SipPhone_Mapper.selectSipPhonesByAllot, params);
    }

    /**
     * 选择一定数量的固话 号码 （未分配的）
     *
     * @param params 固话 号码数
     * @return
     */
    @Override
    public List<FixPhone> getFixPhonesByAllot(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.FixPhone_Mapper.selectFixPhonesByAllot, params);
    }

    /**
     * 保存审核信息
     *
     * @param spApply
     */
    @Override
    public void updateSPApplyAudit(SipPhoneApply spApply) {
        this.update(Mapper.SipPhoneApply_Mapper.updateByPrimaryKeySelective, spApply);
    }

    /**
     * 判断sip 号码 和 固话号码 是否被占用
     *
     * @param sipphoneId sip 号码 id
     * @param fixphoneId 固话id
     * @return
     */
    @Override
    public boolean isAllot(String sipphoneId, String fixphoneId) {
        Map<String, Object> params = new HashMap<>();
        params.put("sipphoneId", sipphoneId);
        params.put("fixphoneId", fixphoneId);
        long count = this.findObjectByMap(Mapper.SipPhoneApply_Mapper.countAllot, params);

        return count > 0;
    }

    /**
     * 校验sipphone
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> checkSipphoneByMap(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.SpApplyNum_Mapper.checkSipphone, params);
    }

    /**
     * 校验fixphone
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> checkFixphoneByMap(Map<String, Object> params) {
        return this.findObjectListByMap(Mapper.SpApplyNum_Mapper.checkFixphone, params);
    }

    /**
     * 校验外显号
     *
     * @param params
     * @return
     */
    @Override
    public Long checkShowNumByMap(Map<String, Object> params) {
        return this.findObjectByMap(Mapper.SpApplyNum_Mapper.checkShowNum, params);
    }

    /**
     * 根据id选择外显号
     *
     * @param id
     * @return
     */
    @Override
    public SpApplyNum getShowNumWithSipFixPhoneByPK(Long id) {
        return this.findObject(Mapper.SpApplyNum_Mapper.selectShowNumWithSipFixPhoneByPK ,id);
    }

    /**
     * 开启/关闭长途，开启/禁用号码
     *
     * @param applyNum
     */
    @Override
    public void updateSipstatus(SpApplyNum applyNum) {
        this.update(Mapper.SpApplyNum_Mapper.updateSipStatus, applyNum);
    }
}
