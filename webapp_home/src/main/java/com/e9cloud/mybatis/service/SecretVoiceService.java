package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppVoice;
import com.e9cloud.mybatis.domain.SecretVoice;

import java.util.Map;

/**
 * Created by admin on 2016/5/31.
 */
public interface  SecretVoiceService  extends IBaseService {

    /**
     * 分页查询应用铃声信息
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceList(Page page);
    /**
     * 创建铃声
     * @param voice
     * @return
     */
    public void saveVoice(SecretVoice voice);
    /**
     * 修改铃声
     * @param voice
     * @return
     */
    public void updateVoice(SecretVoice voice);

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
    public SecretVoice findVoiceListByAppid (String appid);

}
