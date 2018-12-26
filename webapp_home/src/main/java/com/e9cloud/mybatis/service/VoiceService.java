package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.*;
import java.util.List;
import java.util.Map;
/**
 * Created by admin on 2016/3/24.
 */
public interface VoiceService extends IBaseService  {

    /**
     * 分页查询应用铃声信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceList(Page page);


    PageWrapper voicePhoneListBySid(Page page);


    /**
     * 创建铃声
     * @param voice
     * @return
     */
    public void saveVoice(AppVoice voice);
    /**
     * 修改铃声
     * @param voice
     * @return
     */
    public void updateVoice(AppVoice voice);

    void updateVoiceName(Map map);

    /**
     * 修改铃声
     * @param appid
     * @return
     */
    public void delVoice(String appid);

    public String countVoiceByAppid(String appid);

    public String updateVoiceByAppid(String appid);
    /**
     * 根据ID值查询APPname的值
     * @param id
     * @return
     */
    public String findAppNameByID (String id);


    public String findAppNameByAPPid (String appid);

    public String getAppvoiceURLByAPPid (String appid);


    /**
     * 铃声查看
     * @param appid
     * @return
     */
    public AppVoice findVoiceListByAppid (String appid);




}
