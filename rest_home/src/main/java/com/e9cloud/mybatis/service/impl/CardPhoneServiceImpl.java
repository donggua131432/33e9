package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CardPhone;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CardPhoneService;
import com.e9cloud.util.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/7.
 */
@Service
public class CardPhoneServiceImpl extends BaseServiceImpl implements CardPhoneService {

    /**
     * 查询物联网卡绑定号码基本信息
     * @param map
     */
    @Override
    public CardPhone findCardPhoneByMap(Map map){
        return (CardPhone) this.findObjectByMap(Mapper.CardPhone_Mapper.findCardPhoneByMap, map);
    }

    /**
     * 绑定物联网卡手机号
     * @param cardPhone
     */
    @Override
    public void bindCardPhone(CardPhone cardPhone){
        this.save(Mapper.CardPhone_Mapper.bindCardPhone, cardPhone);
    }

    /**
     * 解绑物联网卡手机号
     * @param cardPhone
     */
    @Override
    public void unbindCardPhone(CardPhone cardPhone){
        this.delete(Mapper.CardPhone_Mapper.unbindCardPhone, cardPhone);
    }

    /**
     * 获取物联网卡绑定的号码对象列表集合
     * @param card
     * @return List
     */
    @Override
    public List<CardPhone> findCardPhoneListBycard(Map card){
        return this.findObjectListByMap(Mapper.CardPhone_Mapper.findCardPhoneListBycard, card);
    }

    /**
     * 获取物联网卡绑定的号码列表集合
     * @param card
     * @return List
     */
    @Override
    public List<String> findPhoneListBycard(Map card){
        List<String> phones= new ArrayList<String>();
        List<CardPhone> cardPhones=findCardPhoneListBycard(card);
        if(Utils.notEmpty(cardPhones)){
            for(CardPhone cardPhone:cardPhones){
                phones.add(cardPhone.getPhone());
            }
        }
        return phones;
    }
}
