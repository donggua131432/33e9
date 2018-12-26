package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.EccSwitchboard;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccSwitchBoardPoolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/10
 */
@Service
public class EccSwitchBoardPoolServiceImpl extends BaseServiceImpl implements EccSwitchBoardPoolService {
    @Override
    public PageWrapper getEccSwitchBoardList(Page page) {
        return  this.page(Mapper.EccSwitchBoard_Mapper.pageEccSwitchBoardList, page);
    }
    /**
     * 获取号码池信息列表
     *
     * @param number
     * @return
     */
    @Override
    public List<EccSwitchboard> getEccSwitchBoardByNumber(String number) {
        return this.findObjectList(Mapper.EccSwitchBoard_Mapper.getEccSwitchBoardByNumber,number);
    }


    /**
     * 添加公共号码管理池
     *
     * @param eccSwitchboard
     */
    @Override
    public void addEccSwitchBoard(EccSwitchboard eccSwitchboard){
        this.save(Mapper.EccSwitchBoard_Mapper.insertEccSwitchBoard,eccSwitchboard);
    }

    @Override
    public List<EccSwitchboard> getEccSwitchBoardById(String id) {
        return this.findObjectListByPara(Mapper.EccSwitchBoard_Mapper.getEccSwitchBoardById,id);
    }

    @Override
    public void deleteEccSwitchBoardByIds(String[] strId) {
        this.delete(Mapper.EccSwitchBoard_Mapper.deleteEccSwitchBoardByIds, strId);
    }

    @Override
    public void updateEccSwitchBoard(EccSwitchboard eccSwitchboard){
        this.save(Mapper.EccSwitchBoard_Mapper.updateEccSwitchBoardById,eccSwitchboard);
    }

    @Override
    public List<EccSwitchboard> getEccSwitchBoard(String id) {
        return this.findObjectListByPara(Mapper.EccSwitchBoard_Mapper.getEccSwitchBoard,id);
    }

    @Override
    public List<Map<String, Object>> downloadEccSwitchBoardList(Page page) {
        return this.download(Mapper.EccSwitchBoard_Mapper.downloadEccSwitchBoardList, page);
    }

}
