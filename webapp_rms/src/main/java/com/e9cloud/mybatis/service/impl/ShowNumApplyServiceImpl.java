package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.domain.SpNumAudit;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.ShowNumApplyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2016/10/29.
 */
@Service
public class ShowNumApplyServiceImpl extends BaseServiceImpl implements ShowNumApplyService {
    /**
     * 分页查询外显号码审核列表
     *
     * @param page 分页信息
     * @return pageWrapper
     */
    @Override
    public PageWrapper pageShowNumApplyList(Page page) {
        return this.page(Mapper.ShowNumApply_Mapper.pageShowNumApplyList, page);
    }

    /**
     * 导出外显号码审核列表
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadShowNumApplyInfo(Page page) {
        return this.download(Mapper.ShowNumApply_Mapper.pageShowNumApplyList, page);
    }

    /** 根据ids查询多个spApplyNum **/
    @Override
    public List<SpApplyNum> getSpApplyNumByIds(Long[] ids) {
        return this.findObjectList(Mapper.ShowNumApply_Mapper.getSpApplyNumByIds, ids);
    }

    /** 批量审核外显号码 **/
    @Override
    public void updateSpApplyNums(Map map) {
        this.update(Mapper.ShowNumApply_Mapper.updateSpApplyNums, map);
    }

    /** 根据ids查询多个审批记录 **/
    @Override
    public List<SpNumAudit> getSpNumAuditByIds(Long[] ids) {
        return this.findObjectList(Mapper.ShowNumApply_Mapper.getSpNumAuditByIds, ids);
    }

    /** 审核通过批量更新tb_sp_apply_num表信息 **/
    @Override
    public void updateAppNumberList(List<SpApplyNum> spApplyNumList,List<SpNumAudit> spNumAuditList) {
        if (spApplyNumList==null){
            this.update(Mapper.ShowNumApply_Mapper.updateSpNumAudio,spNumAuditList);
        }else {
            this.save(Mapper.ShowNumApply_Mapper.updateAppNumberList, spApplyNumList);
            this.update(Mapper.ShowNumApply_Mapper.updateSpNumAudio,spNumAuditList);
        }
    }

    public SpNumAudit getSpNumAuditByShowNumId(Long id){
        return this.findObject(Mapper.ShowNumApply_Mapper.getSpNumAuditByShowNumId, id);
    }
}
