package com.e9cloud.mybatis.service.impl;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.OverviewsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/23.
 */
@Service
public class OverviewsServiceImpl extends BaseServiceImpl implements OverviewsService {

    /**
     * 统计消费用户数-每日累计消费金额大于等于300元的用户
     * @param map
     * @return int
     */
    @Override
    public int CountOverviewsUser(Map map){
        return (Integer)this.findObjectByMap(Mapper.Overviews_Mapper.CountOverviewsUser,map);
    }

    /**
     * 统计所有业务的总览情况-总收入、累计通话时长、计费通话时长(1:日报2：月报)
     * @param map
     * @return Map
     */
    @Override
    public Map selectOverviews(Map map){
        Map mapback = new HashMap<>();
        mapback = (Map)this.findObjectByMap(Mapper.Overviews_Mapper.selectOverviews,map);
        return mapback;
    }

    /**
     * 统计各业务消费占比：专线语音、专号通、SIP、智能云调度
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectTypePercent(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectTypePercent,map);
    }

    /**
     * 按月统计所有业务的总览情况-总收入
     * @param map
     * @return Map
     */
    @Override
    public Map selectOverviewsRev(Map map){
        return (Map)this.findObjectByMap(Mapper.Overviews_Mapper.selectOverviewsRev,map);
    }

    /**
     * 按月统计所有业务的总览情况-充值金额
     * @param map
     * @return Map
     */
    @Override
    public Map selectOverviewsMon(Map map){
        return (Map)this.findObjectByMap(Mapper.Overviews_Mapper.selectOverviewsMon,map);
    }

    /**
     * 按月统计所有业务的总览情况-注册用户数
     * @param map
     * @return Map
     */
    @Override
    public String selectOverviewsCon(String map){
        return this.findObject(Mapper.Overviews_Mapper.selectOverviewsCon,map);
    }

    /**
     * 按月统计所有业务的总览情况-新增用户数
     * @param map
     * @return Map
     */
    @Override
    public String selectOverviewsConNew(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectOverviewsConNew,map);
    }

    /**
     * 按月统计所有业务的总览情况-活跃用户数
     * @param map
     * @return Map
     */
    @Override
    public String selectOverviewsConActive(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectOverviewsConActive,map);
    }


    /**
     * 统计消费前10客户
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectOverviewsTop(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectOverviewsTop,map);
    }

    /**
     * 按月统计消费前10客户
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectOverviewsTopRev(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectOverviewsTopRev,map);
    }

    /**
     * 话务总览--专线语音rest
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectRest(Map map){
        List<Map> typeList = new ArrayList<Map>();
        List<Map> typeListBack = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectRest,map);
        if(typeList!=null&&typeList.size()>0){
            Map mapRateDay = this.findObject(Mapper.Overviews_Mapper.selectRcdBillRate,map);
            typeListBack = addRcdBillRate(typeList,mapRateDay);
        }
        return typeListBack;
    }

    public List<Map> addRcdBillRate(List<Map> typeList,Map map){
        if(map!=null){
            for (Map map2:typeList){
                if("A".equals(map2.get("abline"))){
                    map2.put("rcdBillRate",map.get("rcdBillRate"));
                }else{
                    map2.put("rcdBillRate","");
                }
            }
        }else{
            for (Map map2:typeList){
                map2.put("rcdBillRate","");
            }
        }
        return typeList;
    }

    /**
     *话务总览--智能云调度cc
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectCc(Map map){
        List<Map> typeList = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectCc,map);
        return typeList;
    }

    /**
     * 话务总览--sip
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectSip(Map map){
        List<Map> typeList = new ArrayList<Map>();
        List<Map> typeListBack = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectSip,map);
        if(typeList!=null&&typeList.size()>0){
            if("1".equals(map.get("reportType"))){
                Map map1 = this.findObject(Mapper.Overviews_Mapper.selectSipNumByDay,map);
                typeListBack = addSipNum(typeList,map1);
            }else if("2".equals(map.get("reportType"))){
                Map map2 = this.findObject(Mapper.Overviews_Mapper.selectSipNumByMonth,map);
                typeListBack = addSipNum(typeList,map2);
            }

        }
        return typeList;
    }

    public List<Map> addSipNum(List<Map> typeList,Map map){
        if(map!=null){
            for (Map map2:typeList){
                map2.put("sipNum",map.get("sipNum"));
            }
        }else{
            for (Map map2:typeList){
                map2.put("sipNum","");
            }
        }
        return typeList;
    }

    /**
     * 话务总览--语音通知voice
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectVoice(Map map){
        List<Map> typeList = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectVoice,map);
        return typeList;
    }

    /**
     * 话务总览--专号通mask
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectMask(Map map){
        List<Map> typeList = new ArrayList<Map>();
        List<Map> typeListBack = new ArrayList<Map>();
            typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectMask,map);
            if(typeList!=null&&typeList.size()>0){
                Map mapMaskNumByDay = this.findObject(Mapper.Overviews_Mapper.selectMaskNum,map);
                Map mapA = this.findObject(Mapper.Overviews_Mapper.selectRcdBillRateA,map);
                Map mapC = this.findObject(Mapper.Overviews_Mapper.selectRcdBillRateC,map);
                mapA.put("maskNum",mapMaskNumByDay.get("maskNum"));
                mapA.put("rcdBillRateC",mapC.get("rcdBillRateC"));
                typeListBack = addRcdBillRateAndMaskNum(typeList,mapA);
            }
        return typeListBack;
    }

    public List<Map> addRcdBillRateAndMaskNum(List<Map> typeList,Map map){
        for (Map map2:typeList){
            if("A路".equals(map2.get("abline"))){
                map2.put("rcdBillRate",map.get("rcdBillRateA"));
                map2.put("maskNum",map.get("maskNum"));
            }else if("回呼".equals(map2.get("abline"))){
                map2.put("rcdBillRate",map.get("rcdBillRateC"));
                map2.put("maskNum","");
            }else{
                map2.put("rcdBillRate","");
                map2.put("maskNum","");
            }
        }

        return typeList;
    }

    /**
     * 话务总览--云话机sipp
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectSipp(Map map){
        List<Map> typeList = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectSipp,map);
        return typeList;
    }

    /**
     * 话务总览--云总机ecc
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectEcc(Map map){
        List<Map> typeList = new ArrayList<Map>();
        typeList = this.findObjectListByMap(Mapper.Overviews_Mapper.selectEcc,map);
        return typeList;
    }

    /**
     * 话务总览--语音验证码voiceverify
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectVoiceverify(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectVoiceverify,map);
    }

    /**
     *  消费统计---rest
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectRestFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectRestFeeSum,map);
    }
    @Override
    public List<Map> selectRestAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectRestAblineSum,map);
    }
    @Override
    public String selectRestCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectRestCount,map);
    }

    /**
     *  消费统计---Mask
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectMaskFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectMaskFeeSum,map);
    }

    @Override
    public List<Map> selectMaskNumFee(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectMaskNumFee,map);
    }
    @Override
    public List<Map> selectMaskRent(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectMaskRent,map);
    }

    @Override
    public List<Map> selectMaskAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectMaskAblineSum,map);
    }
    @Override
    public String selectMaskCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectMaskCount,map);
    }

    /**
     *  消费统计---Voice
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectVoiceFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectVoiceFeeSum,map);
    }
    @Override
    public String selectVoiceCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectVoiceCount,map);
    }

    /**
     *  消费统计---SIP
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectSipFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectSipFeeSum,map);
    }
    @Override
    public List<Map> selectSipAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectSipAblineSum,map);
    }
    @Override
    public String selectSipCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectSipCount,map);
    }

    /**
     *  消费统计---智能云调度
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectCcFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectCcFeeSum,map);
    }
    @Override
    public List<Map> selectCcAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectCcAblineSum,map);
    }
    @Override
    public List<Map> selectCcNumFee(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectCcNumFee,map);
    }
    @Override
    public String selectCcCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectCcCount,map);
    }


    /**
     *  消费统计---云话机
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectSpFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectSpFeeSum,map);
    }
    @Override
    public List<Map> selectSpAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectSpAblineSum,map);
    }
    @Override
    public String selectSpCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectSpCount,map);
    }



    /**
     *  消费统计---云总机
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectEccFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectEccFeeSum,map);
    }
    @Override
    public List<Map> selectEccAblineSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectEccAblineSum,map);
    }

    @Override
    public List<Map> selectEccSipRent(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectEccSipRent,map);
    }
    @Override
    public List<Map> selectEccZjRent(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectEccZjRent,map);
    }
    @Override
    public List<Map> selectEccMinCost(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectEccMinCost,map);
    }
    @Override
    public String selectEccCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectEccCount,map);
    }

    /**
     *  消费统计---voiceverify
     * @param map
     * @return List
     */
    @Override
    public List<Map> selectVoiceverifyFeeSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectVoiceverifyFeeSum,map);
    }
    @Override
    public String selectVoiceverifyCount(Map map){
        return this.findObject(Mapper.Overviews_Mapper.selectVoiceverifyCount,map);
    }
    @Override
    public List<Map> selectVoiceverifyPjSum(Map map){
        return this.findObjectListByMap(Mapper.Overviews_Mapper.selectVoiceverifyPjSum,map);
    }
}
