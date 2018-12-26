package com.e9cloud.rest.mask;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.service.VoiceCodeService;
import com.e9cloud.rest.service.VoiceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumRelation;
import com.e9cloud.mybatis.service.MaskNumRelationService;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.IDGenerator;
import com.e9cloud.util.RandomUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 私密专线Service
 * 
 * @author Administrator
 *
 */
public class MakeService {

	private static final Logger logger = LoggerFactory.getLogger(MakeService.class);
	private static final MakeService MAKE_SERVICE = new MakeService();
	private MaskNumRelationService maskNumRelationService = SpringContextHolder.getBean(MaskNumRelationService.class);

	private VoiceService voiceService = SpringContextHolder.getBean(VoiceService.class);
	private VoiceCodeService voiceCodeService = SpringContextHolder.getBean(VoiceCodeService.class);

	private MakeService() {
		fixTimeDelete();
	}

	public static MakeService getInstance() {
		return MAKE_SERVICE;
	}

	/**
	 * 申请隐私号
	 * 
	 * @param phoneA
	 *            号码A
	 * @param phoneB
	 *            号码B
	 * @param appId
	 *            应用id
	 * @param isRecord
	 * @param timeOut
	 * @param arean
	 * 
	 */
	public BestMask applyMaskNum(String phoneA, String phoneB, String appId, String isRecord, String timeOut,
			String arean, String accountSid) {
		MakeRelationRedis makeRelationRedis = MakeRelationRedis.getInstance();
		BestMask bestMask = queryBestMask(phoneA, phoneB, appId);
		if (bestMask != null) {
			// TODO更改有效时间
			return bestMask;
		}
		// 去池中拿适合隐私号
		bestMask = getBestMask(appId, phoneA, phoneB, arean);
		if (bestMask == null) {
			logger.error("隐私号码已用完");
			JedisLock.unlock();
			return bestMask;
		}
		String maskNum = bestMask.getMaskNum();
		arean = bestMask.getArean();
		// 加入redis缓存
		String id = IDGenerator.getUniqueIDStr();
		//获取语音编码
		CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("mask");
		String voiceCode = "G729";
		if(cbVoiceCode!=null){
			voiceCode = cbVoiceCode.getVoiceCode();
		}
		makeRelationRedis.addABMask(phoneA, phoneB, maskNum, appId, timeOut, isRecord, arean, accountSid, id,voiceCode);
		JedisLock.unlock();
		// 插入mysql数据库
		MaskNumRelation maskNumRelation = new MaskNumRelation();
		maskNumRelation.setId(id);
		maskNumRelation.setNeedRecord(isRecord);
		maskNumRelation.setAppid(appId);
		maskNumRelation.setArean(arean);
		maskNumRelation.setCreateDate(new Date());
		maskNumRelation.setMakeNum(maskNum);
		maskNumRelation.setPhonea(phoneA);
		maskNumRelation.setPhoneb(phoneB);
		maskNumRelation.setSecond(timeOut);
		maskNumRelation.setTimeOut(DateUtil.addDate(Integer.parseInt(timeOut)));
		maskNumRelationService.insert(maskNumRelation);
		return bestMask;

	}

	/**
	 * 
	 * @param phoneA
	 *            号码a
	 * @param phoneB
	 *            号码b
	 * @param appId
	 *            应用id
	 * @return false失败不存在隐私号，true成功
	 */

	public BestMask releaseMaskNum(String phoneA, String phoneB, String appId) {
		MakeRelationRedis makeRelationRedis = MakeRelationRedis.getInstance();
		BestMask bestMask = queryBestMask(phoneA, phoneB, appId);
		if (bestMask == null) {
			return bestMask;
		}
		long result = makeRelationRedis.delete(phoneA, phoneB, appId);
		if (result <= 0) {
			result = makeRelationRedis.delete(phoneB, phoneA, appId);
		}
		makeRelationRedis.delete(phoneA, bestMask.getMaskNum());
		makeRelationRedis.delete(phoneB, bestMask.getMaskNum());
		return bestMask;
	}

	/**
	 * 查询隐私号
	 * 
	 * @param phoneA
	 * @param phoneB
	 */
	public String queryMaskNum(String phoneA, String phoneB, String appId) {
		MakeRelationRedis makeRelationRedis = MakeRelationRedis.getInstance();
		String value = makeRelationRedis.getValue(phoneA, phoneB, appId);
		if (StringUtils.isEmpty(value)) {
			value = makeRelationRedis.getValue(phoneB, phoneA, appId);
		}
		BestMask bestMask = null;
		if (StringUtils.isEmpty(value) == false) {
			bestMask = new Gson().fromJson(value, BestMask.class);
			return bestMask.getMaskNum();
		}
		return null;
	}

	/**
	 * 查询隐私号及对应区
	 * 
	 * @param phoneA
	 * @param phoneB
	 * @param appId
	 */
	public BestMask queryBestMask(String phoneA, String phoneB, String appId) {
		MakeRelationRedis makeRelationRedis = MakeRelationRedis.getInstance();
		String value = makeRelationRedis.getValue(phoneA, phoneB, appId);
		BestMask bestMask = null;
		long timeOut = -1;
		if (StringUtils.isEmpty(value)) {
			value = makeRelationRedis.getValue(phoneB, phoneA, appId);
			timeOut = makeRelationRedis.ttl(phoneB, phoneA, appId);
		} else {
			timeOut = makeRelationRedis.ttl(phoneA, phoneB, appId);
		}
		if (StringUtils.isEmpty(value) == false) {
			bestMask = new Gson().fromJson(value, BestMask.class);
			if (timeOut > 0) {
				bestMask.setValidTime(String.valueOf(timeOut));
			}

		}

		return bestMask;
	}

	public boolean existRelation(String appId, String phoneA, String phoneB, String maskNum) {
		String value = queryMaskNum(phoneA, phoneB, appId);
		return maskNum.equals(value);
	}

	/**
	 * 获得隐私号
	 * 
	 * @param appId
	 * @param phoneA
	 * @param phoneB
	 * @param arean
	 * @return
	 */
	private BestMask getBestMask(String appId, String phoneA, String phoneB, String arean) {
		long start = System.currentTimeMillis();
		BestMask bestMask = null;
		List<String> maskNums = null;

		// 获取应用全部
		if (StringUtils.isEmpty(arean)) {
			Map<String, List<String>> map = MakePoolRedis.getInstance().getAll(appId);
			if (JedisLock.lock() == false) {
				return bestMask;
			}
			for (Entry<String, List<String>> e : map.entrySet()) {
				bestMask = getBestMask(phoneA, phoneB, e.getKey(), e.getValue());
				if (bestMask != null) {
					return bestMask;
				}
			}
			long end = System.currentTimeMillis();
			logger.info("获取隐私号耗时:" + (end - start));
			return bestMask;
		}

		// 获取指定区号
		maskNums = MakePoolRedis.getInstance().getAll(appId, arean);
		if (maskNums == null) {
			return bestMask;
		}
		if (JedisLock.lock() == false) {
			return bestMask;
		}
		bestMask = getBestMask(phoneA, phoneB, arean, maskNums);
		long end = System.currentTimeMillis();
		logger.info("获取隐私号耗时:" + (end - start));
		return bestMask;
	}

	public BestMask getBestMask(String phoneA, String phoneB, String arean, List<String> maskNums) {
		BestMask bestMask = null;
		int size = maskNums.size();
		int index = 0;
		String[] maskNumStr = new String[size];
		maskNums.toArray(maskNumStr);
		while (index < size) {
			// 随机数据
			int rand = RandomUtil.getInt(index, size - 1);
			String maskNum2 = maskNumStr[rand];
			boolean am = MakeRelationRedis.getInstance().exesit(phoneA, maskNum2);
			boolean bm = MakeRelationRedis.getInstance().exesit(phoneB, maskNum2);
			if (!am && !bm) {
				bestMask = new BestMask();
				bestMask.setArean(arean);
				bestMask.setMaskNum(maskNum2);
				break;
			}

			// 数据交换
			String temp = maskNumStr[index];
			maskNumStr[index] = maskNumStr[rand];
			maskNumStr[rand] = temp;
			index++;
		}
		return bestMask;
	}

	/**
	 * 定时删除表
	 */
	public void fixTimeDelete() {
		Timer timer = new Timer("clear mysqlmasknum", true);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// 删除1天前的
				MaskNumRelation relation = new MaskNumRelation();
				long mill = new Date().getTime() - 1 * DateUtil.DAY;
				relation.setTimeOut(new Date(mill));
				maskNumRelationService.delete(relation);
			}
		};
		Date date = DateUtil.zoreThree();
		timer.scheduleAtFixedRate(task, date, DateUtil.DAY);
	}

	/**
	 * 定时同步mysql数据
	 */
	public void fixSysMysql() {
		Timer timer = new Timer("sys mysqlmasknumpool", true);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				sysMysql();
			}
		};
		Date date = DateUtil.zoreThree();
		timer.scheduleAtFixedRate(task, date, DateUtil.DAY);
	}

	/**
	 * 同步mysql号码池数据
	 */
	public void sysMysql() {
		long start = System.currentTimeMillis();
		List<MaskNum> maskNums = maskNumRelationService.getMaskNumListByStatus("01");
		Map<String, Map<String, Set<String>>> map = new HashMap<>();
		for (MaskNum maskNum : maskNums) {
			String appId = maskNum.getAppid();
			String cityId = maskNum.getCityid();
			String num = maskNum.getNumber();
			Map<String, Set<String>> areanMap = map.get(appId);
			if (areanMap == null) {
				areanMap = new HashMap<>();
				map.put(appId, areanMap);
			}
			Set<String> nums = areanMap.get(cityId);
			if (nums == null) {
				nums = new HashSet<String>();
				areanMap.put(cityId, nums);
			}
			nums.add(num);
		}

		for (Entry<String, Map<String, Set<String>>> e : map.entrySet()) {
			String appId = e.getKey();
			MakePoolRedis.getInstance().clearMaskNum(appId);
			Map<String, Set<String>> m = e.getValue();
			for (Entry<String, Set<String>> ee : m.entrySet()) {
				String cityId = ee.getKey();
				CityAreaCode cityAreaCode = maskNumRelationService.selectByPrimiry(cityId);
				if (cityAreaCode == null) {
					logger.error(cityId + "错误的城市id");
					continue;
				}
				MakePoolRedis.getInstance().sysMaskNum(appId, cityAreaCode.getAreaCode(), ee.getValue());
			}

		}
		long end = System.currentTimeMillis();
		logger.info("同步数据耗时:" + (end - start));
	}

	public void syncVoiceNotify(){
			voiceService.syncVoiceNotify();
	}
}
