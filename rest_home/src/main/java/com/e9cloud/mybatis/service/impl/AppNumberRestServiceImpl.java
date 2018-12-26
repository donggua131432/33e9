package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppNumberRestService;
import com.e9cloud.util.InitValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
@Service
public class AppNumberRestServiceImpl extends BaseServiceImpl implements AppNumberRestService {

    /**
     * 根据appid查询所有的显号集合
     * @param appid
     * @return List
     */
    @Override
    public List<String> findAppNumberRestListByAppid(String appid){
        //先从缓存容器中加载，不存在或超时再查数据库
        List<String> appNumberList= InitValue.appNumberCache.get(appid);
        if(appNumberList == null){
            List<AppNumberRest> list = this.findObjectListByPara(Mapper.AppNumberRest_Mapper.findAppNumberRestListByAppid, appid);
            if(list!=null && list.size()>0){
                appNumberList=new ArrayList<String>();
                //进行显号获取存放list集合
                for(Iterator it = list.iterator(); it.hasNext(); ) {
                    AppNumberRest appNumberRest = (AppNumberRest)it.next();
                    appNumberList.add(appNumberRest.getNumber());
                }
                //放入缓存容器，一分钟有效
                InitValue.appNumberCache.put(appid,appNumberList,60);
            }
        }
        return appNumberList;
    }
}
