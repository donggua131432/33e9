package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.SpNumAudit;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/10/29.
 */
public interface ShowNumApplyService extends IBaseService {
    /**
     * 分页查询外显号码审核列表
     * @param page 分页信息
     * @return pageWrapper
     */
    PageWrapper pageShowNumApplyList(Page page);

    /**
     * 导出外显号码审核列表
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadShowNumApplyInfo(Page page);

    /** 根据ids查询多个spApplyNum **/
    public List<SpApplyNum> getSpApplyNumByIds(Long[] ids);

    /** 批量审核外显号码 **/
    void updateSpApplyNums(Map map);

    /**
     * 根据ids查询多个审核记录
     *
     * @param strId
     * @return
     */
    List<SpNumAudit> getSpNumAuditByIds(Long[] strId);

    /**
     * 审核通过批量更新tb_sp_apply_num和tb_sp_num_audio表信息
     * @param spApplyNumList
     */
    void updateAppNumberList(List<SpApplyNum> spApplyNumList,List<SpNumAudit> spNumAuditList);

    /**
     * 根据shownum_id查询最新审批记录（解决外显号再次更新，而后台已在审批，作出提示）
     */
    SpNumAudit getSpNumAuditByShowNumId(Long showNumId);
}
