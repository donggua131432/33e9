package com.e9cloud.mybatis.service;

import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/5/3.
 */
public interface VoiceCustNumPoolService  extends IBaseService{

    /**
     * 获取客户号码池信息列表
     * @param page
     * @return
     */
    PageWrapper getVoiceCustAppList(Page page);

    /**
     * 获取用户号码信息列表
     * @param page
     * @return
     */
    PageWrapper getVoiceCustNumberList(Page page);

    /**
     * 添加用户号码池
     * @param voiceVerifyApp
     */
    void addVoiceCustAppPool(VoiceVerifyApp voiceVerifyApp);
    /**
     * 获取号码池信息列表
     * @param voiceVerNum
     * @return
     */
    List<VoiceVerifyNumPool> getVoiceNumPoolByPhone(VoiceVerifyNumPool voiceVerNum);

    /**
     * 根据条件获取用户号码池信息（app_pool）
     * @param voiceVerifyApp
     * @return
     */
    VoiceVerifyApp getVoiceCustAppPoolByObj(VoiceVerifyApp voiceVerifyApp);


    /**
     * 用于查询app下是否有正常或者已锁定的号码列表
     * @param appid
     */
    boolean checkHasNumber(String appid);

    /**
     * 根据ID删除号码池信息
     * @param id
     */
    void deleteVoiceCustAppPoolById(String id);


    /**
     * 添加客户号码
     * @param voiceVerCustNum
     */
    void addVoiceCustNumberPool(VoiceVerCustNum voiceVerCustNum, VoiceVerifyNumPool voiceVerifyNumPool);


    //用户号码管理池导出
    List<Map<String, Object>> downloadVoiceCustNumPool(Page page);


    //用户App池导出
    List<Map<String, Object>> downloadVoiceAppInfo(Page page);


    /**
     * 根据条件获取客户号码信息
     * @param voiceVerCustNum
     * @return
     */
    List<VoiceVerCustNum> getVoiceCustNumberPoolByObj(VoiceVerCustNum voiceVerCustNum);


    VoiceVerifyNumPool getVoicePhoneById(String id);


    //根据id变更公共号码池号码状态
    void updateVoicePhoneByStatus(VoiceVerifyNumPool voicePhone);


    //没绑定，直接逻辑删除
    void deleteVoiceCustNum(VoiceVerCustNum voiceVerCustNum);


    /**
     * 批量删除号码，
     * @return
     */
    String deletePhones(String strId);

}
