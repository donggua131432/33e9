package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.EccShownum;
import com.e9cloud.mybatis.domain.EccSipphone;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccShowNumPoolService;
import com.e9cloud.mybatis.service.SipNumResPoolService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/10
 */
@Service
public class EccShowNumPoolServiceImpl extends BaseServiceImpl implements EccShowNumPoolService {
    @Override
    public PageWrapper getEccShowNumList(Page page) {
        return  this.page(Mapper.EccShowNum_Mapper.pageEccShowNumList, page);
    }
    /**
     * 获取号码池信息列表
     *
     * @param number
     * @return
     */
    @Override
    public List<EccShownum> getEccShowNumByNumber(String number) {
        return this.findObjectListByPara(Mapper.EccShowNum_Mapper.getEccShowNumByNumber, number);
    }


    /**
     * 添加公共号码管理池
     *
     * @param eccShownum
     */
    @Override
    public void addEccShowNum(EccShownum eccShownum){
        this.save(Mapper.EccShowNum_Mapper.insertEccShowNum,eccShownum);
    }

    @Override
    public List<EccShownum> getEccShowNumById(String id) {
        return this.findObjectListByPara(Mapper.EccShowNum_Mapper.getEccShowNumById,id);
    }

    @Override
    public void deleteEccShowNumByIds(String[] strId) {
        this.delete(Mapper.EccShowNum_Mapper.deleteEccShowNumByIds, strId);
    }

    @Override
    public void updateEccShowNum(EccShownum eccShownum){
        this.save(Mapper.EccShowNum_Mapper.updateEccShowNumById, eccShownum);
    }

    @Override
    public List<EccShownum> getEccShowNum(String id) {
        return this.findObjectListByPara(Mapper.EccShowNum_Mapper.getEccShowNum,id);
    }

    @Override
    public List<Map<String, Object>> downloadEccShowNumList(Page page) {
        return this.download(Mapper.EccShowNum_Mapper.downloadEccShowNumList, page);
    }

}
