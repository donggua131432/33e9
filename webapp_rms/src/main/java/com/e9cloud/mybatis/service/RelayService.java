package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SipBasic;

import java.util.List;
import java.util.Map;

/**
 * 中继配置相关Service
 * Created by Administrator on 2016/7/18.
 */
public interface RelayService extends IBaseService {

    /**
     * 分页查询中继列表
     * @param page 分页信息
     * @return PageWrapper
     */
    PageWrapper pageRelay(Page page);

    /**
     * 得到中继列表
     * @param relay
     * @return
     */
    List<SipBasic> getRelays(Map<String, Object> relay);

    /**
     * 查询未占用且正常中继列表
     * @return
     */
    List<SipBasic> getRelaysByLimit();

    /**
     * 查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表
     * @return
     */
    PageWrapper getRelaysByRelayType(Page page);

    /**
     * 查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表
     * @return
     */
    List<SipBasic> getRelays();

    /**
     * 导出中继列表
     * @param page 分页信息
     * @return PageWrapper
     */
    List<Map<String, Object>> pageDownloadRelay(Page page);

    /**
     * 添加中继信息
     * @param sipBasic
     * @return
     */
    public void saveRelayInfo(SipBasic sipBasic);

    /**
     * 判断唯一性
     * @param sipBasic
     * @return
     */
    long countSipBasicByNum(SipBasic sipBasic);

    /**
     * 判断中继是否已经被引用
     * @param sipBasic
     * @return
     */

    long flagSipBasic(SipBasic sipBasic);

    long editcountSipBasicByNum(SipBasic sipBasic);

    /**
     * 修改中继信息
     * @param sipBasic
     * @return
     */
    public void updateSipBasicInfo(SipBasic sipBasic);

    public  SipBasic  getRelayInfoById(String id);
    /**
     * 更改中继状态
     *
     * @param relayNum
     * @return
     */
    void updateRelayStatusByRelayNum(String relayNum, String status);

    /**
     * 中继是否可用
     * @param relayNum 中继编号
     * @return 0：不可用 1：可用
     */
    long countEnableRelays(String relayNum);

    /**
     * 解冻所有的中继
     */
    void updateForThawRelay();

    public SipBasic getRelayInfoByRelayNum(String relayNum);

    /**
     * 中继资源下拉列表
     * @param useType 用途类型
     * @param busType 业务类型
     * @param resId 资源id
     * @return List<SipBasic>
     */
    List<SipBasic> getRelaysForRes(String useType, String busType, Integer resId);

    /**
     * 根据中继编号得到中继分配状态
     * @param relayNum 中继编号
     * @return relay.allot
     */
    SipBasic getLimitStatusByRelayNum(String relayNum);

    /**
     * 中继资源下拉列表(呼叫中心代答中继)
     * @return
     */
    List<SipBasic> getRelaysForAnswerTrunk();

    /**
     * 添加、删除、修改中继是，向tb_cb_task中插入一条任务
     * @param oldRelayId 老中继id
     * @param newRelay 新中继
     */
    void addRelayToCBTask(Integer oldRelayId, SipBasic newRelay);
}
