package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.VoiceCustNumPoolService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/5/3.
 */
@Service
public class VoiceCustNumPoolServiceImpl extends BaseServiceImpl implements VoiceCustNumPoolService{


    /**
     * 获取客户号码池信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getVoiceCustAppList(Page page) {
        return this.page(Mapper.VoiceVerifyApp_Mapper.selectVoiceAppPoolList, page);
    }

    /**
     * 获取用户号码池号码信息列表
     * @param page
     * @return
     */
    @Override
    public PageWrapper getVoiceCustNumberList(Page page) {
        return this.page(Mapper.VoiceVerCustNum_Mapper.selectVoiceCustNumberList, page);
    }

    /**
     * 获取号码池信息列表
     *
     * @param voiceVerNum
     * @return
     */
    @Override
    public List<VoiceVerifyNumPool> getVoiceNumPoolByPhone(VoiceVerifyNumPool voiceVerNum) {
        return this.findObjectList(Mapper.VoiceVerifyNumPool_Mapper.getVoiceNumPoolByPhone,voiceVerNum);
    }

    /**
     * 根据条件获取用户号码池信息（app_pool）
     * @param voiceVerifyApp
     * @return
     */
    @Override
    public VoiceVerifyApp getVoiceCustAppPoolByObj(VoiceVerifyApp voiceVerifyApp){
        return this.findObject(Mapper.VoiceVerCustNum_Mapper.getVoiceCustAppPoolByObj, voiceVerifyApp);
    }


    /**
     * 添加用户号码池
     * @param voiceVerifyApp
     */
    @Override
    public void addVoiceCustAppPool(VoiceVerifyApp voiceVerifyApp){
        this.save(Mapper.VoiceVerifyApp_Mapper.insetVoiceCustAppPool, voiceVerifyApp);
    }

    /**
     * 用于查询app下是否有正常或者已锁定的号码列表
     * @param appid
     */
    @Override
    public boolean checkHasNumber(String appid){
        int count = this.findObjectByPara(Mapper.VoiceVerCustNum_Mapper.checkHasNumber, appid);
        return count == 0;
    }



    /**
     * 根据ID删除隐私号码池信息
     * @param id
     */
    @Override
    public void deleteVoiceCustAppPoolById(String id) {
        this.delete(Mapper.VoiceVerCustNum_Mapper.deleteVoiceCustAppPool, id);
    }



    /**
     * 添加客户号码池
     * @param voiceVerifyNumPool
     */
    @Override
    public void addVoiceCustNumberPool(VoiceVerCustNum voiceVerCustNum, VoiceVerifyNumPool voiceVerifyNumPool) {
        this.save(Mapper.VoiceVerCustNum_Mapper.insetVoiceCustNumberPool, voiceVerCustNum);
        this.update(Mapper.VoiceVerifyNumPool_Mapper.updateVoicePhoneByStatus, voiceVerifyNumPool);
    }


    /**
     * 用户号码池列表信息--导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadVoiceCustNumPool(Page page) {
        return this.download(Mapper.VoiceVerCustNum_Mapper.selectVoiceCustNumberList, page);
    }


    /**
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadVoiceAppInfo(Page page) {
        return this.download(Mapper.VoiceVerifyApp_Mapper.selectVoiceAppPoolList, page);
    }

    /**
     * 根据条件获取隐私号码池信息
     * @param voiceVerCustNum
     * @return
     */
    @Override
    public List<VoiceVerCustNum> getVoiceCustNumberPoolByObj(VoiceVerCustNum voiceVerCustNum) {
        return this.findObjectList(Mapper.VoiceVerCustNum_Mapper.selectVoiceCustNumberPoolByObj, voiceVerCustNum);
    }


    @Override
    public VoiceVerifyNumPool getVoicePhoneById(String id) {
        return this.findObjectByPara(Mapper.VoiceVerifyNumPool_Mapper.getVoicePhoneById,id);
    }

    /**
     * 根据id变更公共号码池号码状态
     */
    @Override
    public void updateVoicePhoneByStatus(VoiceVerifyNumPool voicePhone){
        this.update(Mapper.VoiceVerifyNumPool_Mapper.updateVoicePhoneNumByStatus, voicePhone);
    }


    /**
     * 没绑定  逻辑删除
     */
    @Override
    public void deleteVoiceCustNum(VoiceVerCustNum voiceVerCustNum){
        this.update(Mapper.VoiceVerCustNum_Mapper.deleteVoiceCustNum, voiceVerCustNum);
    }

    /**
     * 批量删除号码，并返回已经分配的号码
     * @return
     */
    @Override
    public String deletePhones(String strId) {
        String[] aa = strId.split(",");
        for(String id : aa) {
            VoiceVerCustNum voiceCustNumber = new VoiceVerCustNum();
            voiceCustNumber.setId(id);
            List<VoiceVerCustNum> voiceCustNumberReturn = this.getVoiceCustNumberPoolByObj(voiceCustNumber);
            if(voiceCustNumberReturn.get(0)!=null){
                VoiceVerifyNumPool voicePhone = this.getVoicePhoneById(voiceCustNumberReturn.get(0).getNumId());
                if(voicePhone!=null){
                    //公共号码池号码状态变为待分配
                    voicePhone.setStatus("00");
                    this.updateVoicePhoneByStatus(voicePhone);
                    //客户号码池直接逻辑删除
                    voiceCustNumber.setStatus("02");
                    this.deleteVoiceCustNum(voiceCustNumber);
                }
            }
        }
        return null;
    }
}