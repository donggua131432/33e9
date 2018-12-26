package com.e9cloud.mybatis.service;

import com.e9cloud.mybatis.base.IBaseService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface OverviewsService extends IBaseService {

    /**
     * 统计消费用户数-每日累计消费金额大于等于300元的用户
     * @param map
     * @return int
     */
    public int CountOverviewsUser(Map map);

    /**
     * 统计所有业务的总览情况-总收入、累计通话时长、计费通话时长
     * @param map
     * @return Map
     */
    Map selectOverviews(Map map);

    /**
     * 统计各业务消费占比：专线语音、专号通、SIP、智能云调度
     * @param map
     * @return List
     */
    List<Map> selectTypePercent(Map map);

    /**
     * 按月统计所有业务的总览情况-总收入
     * @param map
     * @return Map
     */
    Map selectOverviewsRev(Map map);

    /**
     * 按月统计所有业务的总览情况-充值金额
     * @param map
     * @return Map
     */
    Map selectOverviewsMon(Map map);
    /**
     * 按月统计所有业务的总览情况-注册用户数
     * @param map
     * @return Map
     */
    String selectOverviewsCon(String map);

    /**
     * 按月统计所有业务的总览情况-新增用户数
     * @param map
     * @return Map
     */
    String selectOverviewsConNew(Map map);

    /**
     * 按月统计所有业务的总览情况-活跃用户数
     * @param map
     * @return Map
     */
    String selectOverviewsConActive(Map map);

    /**
     * 统计消费前10客户
     * @param map
     * @return List
     */
    List<Map> selectOverviewsTop(Map map);

    /**
     * 按月统计消费前10客户
     * @param map
     * @return List
     */
    List<Map> selectOverviewsTopRev(Map map);

    /**
     * 话务总览--统计专线语音rest
     * @param map
     * @return List
     */
    List<Map> selectRest(Map map);
    /**
     * 话务总览--智能云调度cc
     * @param map
     * @return List
     */
    List<Map> selectCc(Map map);
    /**
     * 话务总览--sip
     * @param map
     * @return List
     */
    List<Map> selectSip(Map map);
    /**
     * 话务总览--语音通知voice
     * @param map
     * @return List
     */
    List<Map> selectVoice(Map map);
    /**
     * 话务总览--专号通mask
     * @param map
     * @return List
     */
    List<Map> selectMask(Map map);
    /**
     * 话务总览--云话机sipp
     * @param map
     * @return List
     */
    List<Map> selectSipp(Map map);

    /**
     * 话务总览--云总机ecc
     * @param map
     * @return List
     */
    List<Map> selectEcc(Map map);

    /**
     * 话务总览--语音验证码voiceverify
     * @param map
     * @return List
     */
    List<Map> selectVoiceverify(Map map);

    /**
     * 消费统计---rest
     * @param map
     * @return List
     */
    List<Map> selectRestFeeSum(Map map);
    List<Map> selectRestAblineSum(Map map);
    String selectRestCount(Map map);

    /**
     * 消费统计---Mask
     * @param map
     * @return List
     */
    List<Map> selectMaskFeeSum(Map map);
    List<Map> selectMaskAblineSum(Map map);
    List<Map> selectMaskNumFee(Map map);
    List<Map> selectMaskRent(Map map);
    String selectMaskCount(Map map);

    /**
     * 消费统计---Voice
     * @param map
     * @return List
     */
    List<Map> selectVoiceFeeSum(Map map);
    String selectVoiceCount(Map map);

    /**
     * 消费统计---SIP
     * @param map
     * @return List
     */
    List<Map> selectSipFeeSum(Map map);
    List<Map> selectSipAblineSum(Map map);
    String selectSipCount(Map map);

    /**
     * 消费统计---智能云调度
     * @param map
     * @return List
     */
    List<Map> selectCcFeeSum(Map map);
    List<Map> selectCcAblineSum(Map map);
    List<Map> selectCcNumFee(Map map);
    String selectCcCount(Map map);

    /**
     * 消费统计---云话机
     * @param map
     * @return List
     */
    List<Map> selectSpFeeSum(Map map);
    List<Map> selectSpAblineSum(Map map);
    String selectSpCount(Map map);


    /**
     * 消费统计---云总机
     * @param map
     * @return List
     */
    List<Map> selectEccFeeSum(Map map);
    List<Map> selectEccAblineSum(Map map);
    List<Map> selectEccSipRent(Map map);
    List<Map> selectEccZjRent(Map map);
    List<Map> selectEccMinCost(Map map);
    String selectEccCount(Map map);

    /**
     * 消费统计---voiceverify
     * @param map
     * @return List
     */
    List<Map> selectVoiceverifyFeeSum(Map map);
    String selectVoiceverifyCount(Map map);
    List<Map> selectVoiceverifyPjSum(Map map);

}
