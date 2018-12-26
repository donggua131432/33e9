package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CbTask;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CbTaskService;
import com.e9cloud.mybatis.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.OpenJpaDialect;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/7/18.
 */
@Service
public class RelayServiceImpl extends BaseServiceImpl implements RelayService {

    @Autowired
    private CbTaskService cbTaskService;

    /**
     * 分页查询中继列表
     *
     * @param page 分页信息
     * @return PageWrapper
     */
    @Override
    public PageWrapper pageRelay(Page page) {
        return this.page(Mapper.SipBasic_Mapper.pageRelay, page);
    }

    /**
     * 得到中继列表
     *
     * @param relay
     * @return
     */
    @Override
    public List<SipBasic> getRelays(Map<String, Object> relay) {
        return this.findObjectList(Mapper.SipBasic_Mapper.selectRelays, relay);
    }

    /**
     * 查询未占用且正常中继列表
     *
     * @return
     */
    @Override
    public List<SipBasic> getRelaysByLimit() {
        return this.findObjectList(Mapper.SipBasic_Mapper.selectRelaysByLimit,SipBasic.class);
    }

    /**
     * 查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表
     *
     * @return
     */
    @Override
    public PageWrapper getRelaysByRelayType(Page page) {
        return this.page(Mapper.SipBasic_Mapper.selectRelaysByRelayType,page);
    }

    /**
     * 查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表
     *
     * @return
     */
    @Override
    public List<SipBasic> getRelays() {
        return this.findObjectList(Mapper.SipBasic_Mapper.getRelaysByRelayType,SipBasic.class);
    }

    /**
     * 导出中继列表
     *
     * @param page 分页信息
     * @return PageWrapper
     */
    @Override
    public List<Map<String, Object>> pageDownloadRelay(Page page) {
        return this.findObjectList(Mapper.SipBasic_Mapper.pageDownloadRelay, page);
    }

    /**
     * 插入数据
     *
     * @param sipBasic
     */
    @Override
    public void saveRelayInfo(SipBasic sipBasic) {
        this.save(Mapper.SipBasic_Mapper.insertSelective, sipBasic);
    }
    /**
     * 判断唯一性
     *
     * @param sipBasic
     */
    @Override
    public long countSipBasicByNum(SipBasic sipBasic) {
        return this.findObject(Mapper.SipBasic_Mapper.countSipBasicByNum, sipBasic);
    }


    /**
     * 判断中继是否被引用
     *
     * @param sipBasic
     */
    @Override
    public long flagSipBasic(SipBasic sipBasic) {
        return this.findObject(Mapper.SipBasic_Mapper.flagSipBasic, sipBasic);
    }

    @Override
    public long editcountSipBasicByNum(SipBasic sipBasic) {
        return this.findObject(Mapper.SipBasic_Mapper.editcountSipBasicByNum, sipBasic);
    }

    //修改
    @Override
    public void updateSipBasicInfo(SipBasic sipBasic) {
        this.update(Mapper.SipBasic_Mapper.updateSipBasicInfo, sipBasic);
    }

    //查看
    @Override
    public SipBasic getRelayInfoById(String id) {
        return  this.findObjectByPara(Mapper.SipBasic_Mapper.getRelayInfoById, id);
    }


    /**
     * 更改中继状态
     *
     * @param relayNum
     * @return
     */
    @Override
    public void updateRelayStatusByRelayNum(String relayNum, String status){
        Map<String, String> params = new HashMap<>();
        params.put("relayNum", relayNum);
        params.put("status", status);

        this.update(Mapper.SipBasic_Mapper.updateRelayStatusByRelayNum, params);
    }

    /**
     * 中继是否可用
     *
     * @param relayNum 中继
     * @return 0：不可用 1：可用
     */
    @Override
    public long countEnableRelays(String relayNum) {
        return this.findObject(Mapper.SipBasic_Mapper.countEnableRelays, relayNum);
    }

    /**
     * 解冻所有的中继
     */
    @Override
    public void updateForThawRelay() {
        List<SipBasic> relays = this.findObjectList(Mapper.SipBasic_Mapper.selectFrozenRelay, null);
        if (relays != null && relays.size() > 0) {

        }
    }

    @Override
    public SipBasic getRelayInfoByRelayNum(String relayNum) {
        return  this.findObjectByPara(Mapper.SipBasic_Mapper.getSipBasicByRelayNum , relayNum);
    }

    /**
     * 中继资源下拉列表
     *
     * @param useType 用途类型
     * @param busType 业务类型
     * @param resId   资源id
     * @return List<SipBasic>
     */
    @Override
    public List<SipBasic> getRelaysForRes(String useType, String busType, Integer resId) {
        Map<String, Object> params = new HashMap<>();
        params.put("useType", useType);
        params.put("busType", busType);
        params.put("resId", resId);

        return this.findObjectListByMap(Mapper.SipBasic_Mapper.selectRelaysForRes, params);
    }

    /**
     * 根据中继编号得到中继分配状态
     *
     * @param relayNum 中继编号
     * @return relay.limitStatus
     */
    @Override
    public SipBasic getLimitStatusByRelayNum(String relayNum) {
        return this.findObjectByPara(Mapper.SipBasic_Mapper.selectLimitStatusByRelayNum, relayNum);
    }

    /**
     * 中继资源下拉列表(呼叫中心代答中继)
     *
     * @return
     */
    @Override
    public List<SipBasic> getRelaysForAnswerTrunk() {
        return this.findObjectList(Mapper.SipBasic_Mapper.selectRelaysForAnswerTrunk, null);
    }

    /**
     * 添加、删除、修改中继是，向tb_cb_task中插入一条任务
     *
     * @param oldRelayId 老中继id
     * @param newRelay 新中继
     */
    @Override
    public void addRelayToCBTask(Integer oldRelayId, SipBasic newRelay) {

        SipBasic oldRelay = this.getRelayInfoById(Tools.toStr(oldRelayId));

        String relayNum = "";

        String oldIpport2 = "";
        String oldIpport4 = "";
        if (oldRelay != null) {
            relayNum = oldRelay.getRelayNum();
            oldIpport2 = oldRelay.getIpport2();
            oldIpport4 = oldRelay.getIpport4();
        }

        String newIpport2 = "";
        String newIpport4 = "";
        if (newRelay != null) {
            relayNum = newRelay.getRelayNum();
            newIpport2 = newRelay.getIpport2();
            newIpport4 = newRelay.getIpport4();
        }

        List<Object> rjs = new ArrayList<>();

        Map<String, String> orj2 = validRelay(relayNum, "2", "del", oldIpport2, newIpport2, newIpport4);
        if (orj2 != null) rjs.add(orj2);

        Map<String, String> orj4 = validRelay(relayNum, "4", "del", oldIpport4, newIpport2, newIpport4);
        if (orj4 != null) rjs.add(orj4);

        Map<String, String> nrj2 = validRelay(relayNum, "2", "add", newIpport2, oldIpport2, oldIpport4);
        if (nrj2 != null) rjs.add(nrj2);

        Map<String, String> nrj4 = validRelay(relayNum, "4", "add", newIpport4, oldIpport2, oldIpport4);
        if (nrj4 != null) rjs.add(nrj4);

        if (rjs.size() > 0) {
            Map<String, Object> relayJson = new LinkedHashMap<>();
            relayJson.put("type", CbTask.TaskType.fsproxy.toString());
            relayJson.put("relays", rjs);

            CbTask task = new CbTask();
            task.setType(CbTask.TaskType.fsproxy);
            task.setParamJson(JSonUtils.toJSon(relayJson));
            cbTaskService.saveCbTask(task);
        }
    }

    // 校验中继
    private Map<String, String> validRelay(String relayNum, String flag, String operation, String ipport, String ipport2, String ipport4) {
        if (!Tools.isNullStr(ipport) && !Tools.eqStr(ipport, ipport2) && !Tools.eqStr(ipport, ipport4)) {
            Map<String, Object> params = new HashMap<>();
            params.put("relayNum", relayNum);
            params.put("ipport", ipport);

            long cnt = this.findObjectByMap(Mapper.SipBasic_Mapper.countRelayForTask, params);
            if (cnt == 0) {
                Map<String, String> relayJson = new LinkedHashMap<>();
                relayJson.put("ipport", ipport);
                relayJson.put("flag", flag);
                relayJson.put("operation", operation);
                return relayJson;
            }
        }
        return null;
    }

}
