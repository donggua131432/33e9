package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccSwitchboard;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/2/10.
 */
public interface EccSwitchBoardPoolService extends IBaseService {

    PageWrapper getEccSwitchBoardList(Page page);

    /**
     * 获取号码池信息列表
     * @param number
     * @returnＥｃｃ
     */
    List<EccSwitchboard> getEccSwitchBoardByNumber(String number);


    /**
     * 添加公共号码管理池
     * @param eccSwitchboard
     */
    void addEccSwitchBoard(EccSwitchboard eccSwitchboard);

    /**
     * 根据id查询EccSwitchboard信息
     * @param id
     * @return
     */
    List<EccSwitchboard> getEccSwitchBoardById(String id);

    /**
     * 根据id批量删除
     * @param strId
     */
    void deleteEccSwitchBoardByIds(String[] strId);

    /**
     * 修改总机号码
     * @param eccSwitchboard
     */
    void updateEccSwitchBoard(EccSwitchboard eccSwitchboard);

    List<EccSwitchboard> getEccSwitchBoard(String id);

    /**
     * 公共号码管理池导出
     * @param page
     * @return
     */
    List<Map<String, Object>> downloadEccSwitchBoardList(Page page);

}
