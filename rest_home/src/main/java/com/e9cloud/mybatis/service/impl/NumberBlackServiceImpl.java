package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.domain.NumberBlack;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.NumberBlackService;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
@Service
public class NumberBlackServiceImpl extends BaseServiceImpl implements NumberBlackService {

    /**
     * 获取黑名单号码列表集合
     * @return List
     */
    @Override
    public List<String> findList(){
        //先从缓存容器中加载，不存在或超时再查数据库
        List<String> numberBlacks= InitValue.numberBlackCache.get("numberBlacks");
        if(numberBlacks == null){
            List<NumberBlack> list = this.findList(Mapper.NumberBlack_Mapper.findList);
            if(list!=null && list.size()>0){
                numberBlacks=new ArrayList<String>();
                //进行黑名单号码获取存放list集合
                for(Iterator it = list.iterator(); it.hasNext(); ) {
                    NumberBlack numberBlack = (NumberBlack)it.next();
                    numberBlacks.add(numberBlack.getNumber());
                }
                //放入缓存容器，一分钟有效
                InitValue.numberBlackCache.put("numberBlacks",numberBlacks,60);
            }
        }
        return numberBlacks;
    }
}
