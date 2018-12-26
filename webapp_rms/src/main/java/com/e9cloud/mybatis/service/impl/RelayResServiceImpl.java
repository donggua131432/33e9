package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.RelayRes;
import com.e9cloud.mybatis.domain.RelayResPer;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.RelayResService;
import com.e9cloud.mybatis.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 供应商资源管理
 * Created by Administrator on 2016/11/23.
 */
@Service
public class RelayResServiceImpl extends BaseServiceImpl implements RelayResService {

    @Autowired
    private RelayService relayService;

    /**
     * 分页查询资源信息
     *
     * @param page 分页信息
     * @return 分页列表
     */
    @Override
    public PageWrapper pageRes(Page page) {
        return this.page(Mapper.RelayRes_Mapper.pageRes, page);
    }

    /**
     * 资源id
     *
     * @param id
     */
    @Override
    public void delRes(Integer id) {
        RelayRes relayRes = new RelayRes();
        relayRes.setId(id);
        this.delete(Mapper.RelayRes_Mapper.updateForDelByPK, relayRes);
    }

    /**
     * 下载列表
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadRes(Page page) {
        return this.download(Mapper.RelayRes_Mapper.pageRes, page);
    }

    /**
     * 通过主键（id）得到资源信息(价格详细，中继，城市)
     *
     * @param id
     * @return RelayRes
     */
    @Override
    public RelayRes getResWithPersAndCityById(Integer id) {
        return this.findObject(Mapper.RelayRes_Mapper.selectResWithPersAndCityById, id);
    }

    /**
     * 保存资源信息，并保存价格详细信息
     *
     * @param relayRes 资源信息
     * @param resPers  价格详细信息
     */
    @Override
    public JSonMessage addResAndPers(RelayRes relayRes, List<RelayResPer> resPers) {

        SipBasic sipBasic = relayService.getLimitStatusByRelayNum(relayRes.getRelayNum());
        if ("01".equals(sipBasic.getLimitStatus())) { // 如果已经分配
            return new JSonMessage(R.ERROR, "中继已经被占用了");
        }
        if (!"01".equals(sipBasic.getUseType())) { // 如果不是供应商中继
            return new JSonMessage(R.ERROR, "中继类型不正确");
        }

        this.save(Mapper.RelayRes_Mapper.insertSelective, relayRes);
        for (RelayResPer per : resPers) {
            per.setResId(relayRes.getId());
            this.save(Mapper.RelayResPer_Mapper.insertSelective, per);
        }

        return new JSonMessage(R.OK, "提交成功");
    }

    /**
     * 保存资源信息，并保存价格详细信息
     *
     * @param relayRes 资源信息
     * @param resPers  价格详细信息
     */
    @Override
    public JSonMessage updateResAndPers(RelayRes relayRes, List<RelayResPer> resPers) {

        if (!Tools.eqStr(relayRes.getOldRelayNum(), relayRes.getRelayNum())) {
            SipBasic sipBasic = relayService.getLimitStatusByRelayNum(relayRes.getRelayNum());
            if ("01".equals(sipBasic.getLimitStatus())) { // 如果已经分配
                return new JSonMessage(R.ERROR, "中继已经被占用了");
            }
            if (!"01".equals(sipBasic.getUseType())) { // 如果不是供应商中继
                return new JSonMessage(R.ERROR, "中继类型不正确");
            }
        }

        this.update(Mapper.RelayRes_Mapper.updateByPrimaryKeySelective, relayRes);
        this.delete(Mapper.RelayResPer_Mapper.delByResId, relayRes.getId());
        for (RelayResPer per : resPers) {
            per.setResId(relayRes.getId());
            this.save(Mapper.RelayResPer_Mapper.insertSelective, per);
        }

        return new JSonMessage(R.OK, "提交成功");
    }
}
