package com.e9cloud.rest.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.e9cloud.mybatis.domain.CbVoiceCode;
import com.e9cloud.mybatis.service.VoiceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.MaskNumRelation;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.MaskNumRelationService;
import com.e9cloud.rest.mask.BestMask;
import com.e9cloud.rest.mask.JedisLock;
import com.e9cloud.rest.mask.MakeRelationRedis;
import com.e9cloud.rest.mask.MakeService;
import com.e9cloud.rest.obt.MaskNumber;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.DateUtil;
import com.e9cloud.util.Utils;

@Service
public class MaskNumService {
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private MaskNumRelationService mRelationService;

	@Autowired
	private VoiceCodeService voiceCodeService;

	/**
	 * 申请隐私号
	 * 
	 * @param maskNumber
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> apply(MaskNumber maskNumber) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = maskNumber.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(maskNumber.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_FORMAT.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_FORMAT.getDesc();
		} else if (maskNumber.getRealNumA().equals(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMAB_EQUALS.getCode();
			respDesc = ConstantsEnum.REST_REALNUMAB_EQUALS.getDesc();
		} else if (Utils.notEmpty(maskNumber.getNeedRecord()) &&
				maskNumber.getNeedRecord() != 0 && maskNumber.getNeedRecord() != 1) {
			// needRecord格式不符（不为空时，非0和1数字）
			respCode = ConstantsEnum.REST_NEEDRECORD_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_NEEDRECORD_FORMAT.getDesc();
		} else if (Utils.notEmpty(maskNumber.getValidTime()) &&
				maskNumber.getValidTime() <= 0) {
			// validTime格式不符（不为空时，小于等于0）
			respCode = ConstantsEnum.REST_VALIDTIME_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_VALIDTIME_FORMAT.getDesc();
		} else if (Utils.notEmpty(maskNumber.getAreaCode()) && !Utils.areaCodeValid(maskNumber.getAreaCode())) {
			// areaCode格式不符（以0开头，三或者四位、全数字）
			respCode = ConstantsEnum.REST_AREACODE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_AREACODE_FORMAT.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = maskNumber.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
			} else if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
				//应用开启限额，并超额
				respCode = ConstantsEnum.REST_APPID_LIMITDUE.getCode();
				respDesc = ConstantsEnum.REST_APPID_LIMITDUE.getDesc();
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
				} else {
					// TODO OK
					// 根据真实号码A和B申请有效隐私号码
					// 1、A和B已存在隐私号映射，直接返回相关隐私号，并在错误描述中提示已存在映射
					// 2、A和B不存在隐私号映射，查找对应appId且或areaCode的有效隐私号：
					// 2.1 存在有效隐私号，取第一个并做响应映射关系后，返回隐私号提示成功
					// maskNumber.setMaskNumber(maskNumber);
					// 2.2 不存在有效隐私号，返回错误描述提示失败
					///////////////////////////////
					String phoneA = maskNumber.getRealNumA(), phoneB = maskNumber.getRealNumB();
					String appId = maskNumber.getAppId();
					Integer defual_needRecord=0;
					String needRecord = defual_needRecord.toString();
					if (Utils.notEmpty(maskNumber.getNeedRecord())) {
						needRecord = maskNumber.getNeedRecord() + "";
					}
					Integer defual_validTime=24 * 60 * 60;
					String validTime = defual_validTime.toString();
					if (Utils.notEmpty(maskNumber.getValidTime())) {
						validTime = maskNumber.getValidTime() + "";
					}
					String arean = "";
					if (Utils.notEmpty(maskNumber.getAreaCode())) {
						arean = maskNumber.getAreaCode();
					}
					BestMask bestMask = MakeService.getInstance().applyMaskNum(phoneA, phoneB, appId, needRecord,
							validTime, arean, accountSid);
					if (!Utils.notEmpty(bestMask)) {
						// 不存在有效隐私号码
						respCode = ConstantsEnum.REST_MASKNUM_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_MASKNUM_NOTEXIST.getDesc();
						String url = InitApp.maskLogUrl + "/log/additive";
						try{
							HttpRequest.get(true, url, appId);
						}catch(Exception e){
							e.printStackTrace();
						}


					} else {
						maskNumber.setMaskNumber(bestMask.getMaskNum());
						maskNumber.setAreaCode(bestMask.getArean());
						if (Utils.notEmpty(bestMask.getValidTime())) {
							maskNumber.setValidTime(Integer.parseInt(bestMask.getValidTime()));
						}else{
							if (!Utils.notEmpty(maskNumber.getValidTime())) {
								//设置默认值（接口未传递参数）
								maskNumber.setValidTime(defual_validTime);
							}
						}
						if (Utils.notEmpty(bestMask.getNeedRecord())) {
							maskNumber.setNeedRecord(Integer.parseInt(bestMask.getNeedRecord()));
						}else{
							if (!Utils.notEmpty(maskNumber.getNeedRecord())) {
								//设置默认值（接口未传递参数）
								maskNumber.setNeedRecord(defual_needRecord);
							}
						}

						//根据bestMask有效时间是否为空，区分是新增还是已存在
						if (Utils.notEmpty(bestMask.getValidTime())) {
							respCode = ConstantsEnum.REST_MASKNUMREF_EXIST.getCode();
							respDesc = ConstantsEnum.REST_MASKNUMREF_EXIST.getDesc();
						}else{
							respCode = ConstantsEnum.REST_SUCCCODE.getCode();
							respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
						}

					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	/**
	 * 释放隐私号
	 * 
	 * @param maskNumber
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> release(MaskNumber maskNumber) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = maskNumber.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(maskNumber.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_FORMAT.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_FORMAT.getDesc();
		} else if (maskNumber.getRealNumA().equals(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMAB_EQUALS.getCode();
			respDesc = ConstantsEnum.REST_REALNUMAB_EQUALS.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = maskNumber.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
			} else if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
				//应用开启限额，并超额
				respCode = ConstantsEnum.REST_APPID_LIMITDUE.getCode();
				respDesc = ConstantsEnum.REST_APPID_LIMITDUE.getDesc();
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
				} else {
					// TODO OK
					// 根据真实号码A和B释放映射隐私号码
					// 1、A和B不存在隐私号映射，直接返回错误码，并在错误描述中提示 不存在有效隐私号映射关系
					// 2、A和B存在有效隐私号映射，进行映射关系释放（具体需要怎么操作表关系）
					///////////////////////////////
					// redis释放
					BestMask mask = MakeService.getInstance().releaseMaskNum(maskNumber.getRealNumA(),
							maskNumber.getRealNumB(), maskNumber.getAppId());
					if (!Utils.notEmpty(mask)) {
						// 1.A和B不存在隐私号映射，直接返回错误码，并在错误描述中提示 不存在有效隐私号映射关系
						respCode = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getDesc();
					} else {
						respCode = ConstantsEnum.REST_SUCCCODE.getCode();
						respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
						// TODO OK
						// mysql释放
						MaskNumRelation maskNumRelation = new MaskNumRelation();
						maskNumRelation.setId(mask.getMaskId());
						maskNumRelation.setTimeOut(new Date());
						mRelationService.update(maskNumRelation);

					}
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	public Map<String, String> queryAll(MaskNumber maskNumber) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = maskNumber.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(maskNumber.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (Utils.notEmpty(maskNumber.getAreaCode()) && !Utils.areaCodeValid(maskNumber.getAreaCode())) {
			// areaCode格式不符（以0开头，三或者四位、全数字）
			respCode = ConstantsEnum.REST_AREACODE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_AREACODE_FORMAT.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = maskNumber.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
			} else if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
				//应用开启限额，并超额
				respCode = ConstantsEnum.REST_APPID_LIMITDUE.getCode();
				respDesc = ConstantsEnum.REST_APPID_LIMITDUE.getDesc();
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
				} else {
					// TODO OK
					// 根据appId且或areaCode查询全部有效隐私号码映射关系
					// 1、存在有效隐私号映射，直接返回有效隐私号映射关系列表
					// 隐私号映射关系列表

					// mysql查出所有映射
					MaskNumRelation maskNumRelation = new MaskNumRelation();
					maskNumRelation.setAppid(appid);
					if (Utils.notEmpty(maskNumber.getAreaCode())) {
						maskNumRelation.setArean(maskNumber.getAreaCode());
					}
					maskNumRelation.setTimeOut(new Date());
					List<MaskNumRelation> maskNumRelations = mRelationService.select(maskNumRelation);

					// 返回客户端
					List<MaskNumber> maskNumberList = new ArrayList<MaskNumber>();
					for (MaskNumRelation relation : maskNumRelations) {
						MaskNumber number = new MaskNumber();
						number.setRealNumA(relation.getPhonea());
						number.setRealNumB(relation.getPhoneb());
						number.setMaskNumber(relation.getMakeNum());
						number.setAreaCode(relation.getArean());
						number.setNeedRecord(Integer.parseInt(relation.getNeedRecord()));
						// 存放隐私号码截止现在剩余有效时长
						number.setValidTime(DateUtil.secondOfTwoDate(relation.getTimeOut()));
						maskNumberList.add(number);
					}
					maskNumber.setMaskNumberList(maskNumberList);
					// 2、无有效映射关系，返回错误代码提示不存在
					///////////////////////////////

					respCode = ConstantsEnum.REST_SUCCCODE.getCode();
					respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

	public Map<String, String> query(MaskNumber maskNumber) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = maskNumber.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(maskNumber.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumA())) {
			respCode = ConstantsEnum.REST_REALNUMA_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMA_FORMAT.getDesc();
		} else if (!Utils.notEmpty(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_NULL.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_NULL.getDesc();
		} else if (!Utils.phoneAndTelValid(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMB_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_REALNUMB_FORMAT.getDesc();
		} else if (maskNumber.getRealNumA().equals(maskNumber.getRealNumB())) {
			respCode = ConstantsEnum.REST_REALNUMAB_EQUALS.getCode();
			respDesc = ConstantsEnum.REST_REALNUMAB_EQUALS.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = maskNumber.getAppId();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appid);
			// 应用信息不正常，返回对应的错误码报文
			if (appInfo == null || !"02".equals(appInfo.getBusType())) {
				respCode = ConstantsEnum.REST_APPID_NO.getCode();
				respDesc = ConstantsEnum.REST_APPID_NO.getDesc();
			} else if (!"00".equals(appInfo.getStatus())) {
				respCode = ConstantsEnum.REST_APPID_ABNORMAL.getCode();
				respDesc = ConstantsEnum.REST_APPID_ABNORMAL.getDesc();
			} else if (!accountSid.equals(appInfo.getSid())) {
				respCode = ConstantsEnum.REST_APPID_NOT_SID.getCode();
				respDesc = ConstantsEnum.REST_APPID_NOT_SID.getDesc();
			} else if ("01".equals(appInfo.getLimitFlag()) && appInfo.getQuota().compareTo(BigDecimal.ZERO)<=0) {
				//应用开启限额，并超额
				respCode = ConstantsEnum.REST_APPID_LIMITDUE.getCode();
				respDesc = ConstantsEnum.REST_APPID_LIMITDUE.getDesc();
			} else {
				// 获取用户token
				User user = accountService.getUserBySid(accountSid);
				if (user == null) {
					respCode = ConstantsEnum.REST_SIDUSER_NO.getCode();
					respDesc = ConstantsEnum.REST_SIDUSER_NO.getDesc();
				} else if (!"1".equals(user.getStatus())) {
					respCode = ConstantsEnum.REST_SID_ABNORMAL.getCode();
					respDesc = ConstantsEnum.REST_SID_ABNORMAL.getDesc();
				} else if (user.getFee().compareTo(BigDecimal.ZERO) <= 0) {
					respCode = ConstantsEnum.REST_SID_OVERDUE.getCode();
					respDesc = ConstantsEnum.REST_SID_OVERDUE.getDesc();
				} else {
					// TODO OK
					// 根据真实号码A和B查询有效隐私号码
					// 1、A和B已存在隐私号映射，直接返回相关隐私号
					// maskNumber.setMaskNumber(maskNumber);
					// 2、A和B不存在隐私号映射，返回错误代码提示不存在
					///////////////////////////////
					BestMask bestMask = MakeService.getInstance().queryBestMask(maskNumber.getRealNumA(),
							maskNumber.getRealNumB(), maskNumber.getAppId());
					if (!Utils.notEmpty(bestMask)) {
						// A和B不存在隐私号映射，返回错误代码提示不存在
						respCode = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_MASKNUMREF_NOTEXIST.getDesc();
					} else {
						maskNumber.setMaskNumber(bestMask.getMaskNum());
						maskNumber.setAreaCode(bestMask.getArean());
						if (Utils.notEmpty(bestMask.getValidTime())) {
							maskNumber.setValidTime(Integer.parseInt(bestMask.getValidTime()));
						}
						if (Utils.notEmpty(bestMask.getNeedRecord())) {
							maskNumber.setNeedRecord(Integer.parseInt(bestMask.getNeedRecord()));
						}

						respCode = ConstantsEnum.REST_SUCCCODE.getCode();
						respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
					}

				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}


	/*改方法目前没有用到因此注释  dukai on 2017-04-18
	public void loadMysql() {
		MaskNumRelation maskNumRelation = new MaskNumRelation();
		maskNumRelation.setTimeOut(new Date());
		List<MaskNumRelation> maskNumRelations = mRelationService.select(maskNumRelation);
		// 加入redis缓存
		JedisLock.lock();
		MakeRelationRedis makeRelationRedis = MakeRelationRedis.getInstance();
		for (MaskNumRelation relation : maskNumRelations) {
			String phoneA = relation.getPhonea();
			String phoneB = relation.getPhoneb();
			String maskNum = relation.getMakeNum();
			String appId = relation.getAppid();
			String id = relation.getId();
			long mill = relation.getTimeOut().getTime() - relation.getCreateDate().getTime();
			if (mill < 0) {
				continue;
			}
			String timeOut = String.valueOf(mill);
			String isRecord = "0";
			String arean = relation.getArean();
			AppInfo appInfo = appInfoService.getAppInfoByAppid(appId);
			String accountSid = appInfo.getAppid();
			//获取语音编码
			CbVoiceCode cbVoiceCode = voiceCodeService.findVoiceCodeByBusCode("mask");
			String voiceCode = "G729";
			if(cbVoiceCode!=null){
				voiceCode = cbVoiceCode.getVoiceCode();
			}
			makeRelationRedis.addABMask(phoneA, phoneB, maskNum, appId, timeOut, isRecord, arean, accountSid, id,voiceCode);
		}
		JedisLock.unlock();
	}*/
}
