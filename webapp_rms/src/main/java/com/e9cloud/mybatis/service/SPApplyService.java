package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.FixPhone;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SipPhoneApply;
import com.e9cloud.mybatis.domain.SpApplyNum;

import java.util.List;
import java.util.Map;

/**
 * 云话机号码审请分配相关 Service
 * Created by Administrator on 2016/10/30.
 */
public interface SPApplyService extends IBaseService {

    /**
     * 分页查询申请列表
     * @param page 分页参数
     * @return PageWrapper
     */
    PageWrapper pageApplyList(Page page);

    /**
     * 选择申请信息包含城市和省份名称
     * @param id
     * @return SipPhoneApply
     */
    SipPhoneApply getApplyWithCityById(String id);

    /**
     * 分页查询申请号码列表
     * @param page
     * @return PageWrapper
     */
    PageWrapper pageNumList(Page page);

    /**
     * 删除云话机外显号
     * @param id shownumIds
     */
    void deleteShowNums(String[] id);

    /**
     * 查询外显号表报
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadShowNumList(Page page);

    /**
     * 查询申请记录表报
     * @param page
     * @return
     */
    List<Map<String,Object>> downloadApplyList(Page page);

    /**
     * 后台添加一条申请记录
     * @param apply
     */
    void addApply(SipPhoneApply apply);

    /**
     * 后台添加一个外显号，并审核通过
     * @param applyNum 外显号
     */
    void addShowNum(SpApplyNum applyNum);

    /**
     * 后台添加一个外显号，并审核通过
     * @param applyNum 外显号
     */
    void saveShowNum(SpApplyNum applyNum);

    /**
     * 选择一定数量的sip 号码 （未分配的）
     * @param params sip 号码数
     * @return
     */
    List<SipPhone> getSipPhonesByAllot(Map<String, Object> params);

    /**
     * 选择一定数量的固话 号码 （未分配的）
     *
     * @param params 固话 号码数
     * @return
     */
    List<FixPhone> getFixPhonesByAllot(Map<String, Object> params);

    /**
     * 保存审核信息
     * @param apApply
     */
    void updateSPApplyAudit(SipPhoneApply apApply);

    /**
     * 判断sip 号码 和 固话号码 是否被占用
     * @param sipphoneId sip 号码 id
     * @param fixphoneId 固话id
     * @return
     */
    boolean isAllot(String sipphoneId, String fixphoneId);

    /**
     * 校验sipphone
     * @param params
     * @return
     */
    List<Map<String, Object>> checkSipphoneByMap(Map<String, Object> params);

    /**
     * 校验fixphone
     * @param params
     * @return
     */
    List<Map<String, Object>> checkFixphoneByMap(Map<String, Object> params);

    /**
     * 校验外显号
     * @param params
     * @return
     */
    Long checkShowNumByMap(Map<String, Object> params);

    /**
     * 根据id选择外显号
     * @param id
     * @return
     */
    SpApplyNum getShowNumWithSipFixPhoneByPK(Long id);

    /**
     * 修改外显号码
     * @param applyNum
     */
    void updateShowNum(SpApplyNum applyNum);

    /**
     * 开启/关闭长途，开启/禁用号码
     * @param applyNum
     */
    void updateSipstatus(SpApplyNum applyNum);
}
