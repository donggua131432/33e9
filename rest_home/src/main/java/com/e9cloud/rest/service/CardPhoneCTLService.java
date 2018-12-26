package com.e9cloud.rest.service;

import com.e9cloud.mybatis.domain.AppInfo;
import com.e9cloud.mybatis.domain.CardPhone;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.mybatis.service.CardPhoneService;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardPhoneCTLService {
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CardPhoneService cardPhoneService;

	/**
	 * 物联网卡号码绑定
	 * 
	 * @param cardPhone
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> bind(CardPhone cardPhone) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = cardPhone.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(cardPhone.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_NULL.getCode();
			respDesc = ConstantsEnum.REST_CARD_NULL.getDesc();
		} else if (!Utils.cardValid(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_CARD_FORMAT.getDesc();
		} else if (!Utils.notEmpty(cardPhone.getPhone())) {
			respCode = ConstantsEnum.REST_PHONE_NULL.getCode();
			respDesc = ConstantsEnum.REST_PHONE_NULL.getDesc();
		} else if (!Utils.phoneValid(cardPhone.getPhone())) {
			respCode = ConstantsEnum.REST_PHONE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_PHONE_FORMAT.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = cardPhone.getAppId();
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
					//物联网卡绑定手机号（同一物联网卡最大绑定5个）
					//查询物联网卡绑定手机号码是否已绑定过
					Map<String,String> mapTmp=new HashMap<String,String>();
					mapTmp.put("card",cardPhone.getCard());
					mapTmp.put("phone",cardPhone.getPhone());
					mapTmp.put("appId",cardPhone.getAppId());
					CardPhone cardPhoneTmp=cardPhoneService.findCardPhoneByMap(mapTmp);
					if(Utils.notEmpty(cardPhoneTmp)){
						//物联网卡号码已绑定，则返回错误码
						respCode = ConstantsEnum.REST_CARDPHONE_EXIST.getCode();
						respDesc = ConstantsEnum.REST_CARDPHONE_EXIST.getDesc();
					}else{
						//同一物联网卡最大绑定5个过滤

						List<CardPhone> cardPhoneList=cardPhoneService.findCardPhoneListBycard(mapTmp);
						if(Utils.notEmpty(cardPhoneList) && cardPhoneList.size()>=5){
							//同一物联网卡已绑定五个以上，则返回错误码
							respCode = ConstantsEnum.REST_CARDPHONE_UPLIMIT.getCode();
							respDesc = ConstantsEnum.REST_CARDPHONE_UPLIMIT.getDesc();
						}else{
							//设置创建时间
							cardPhone.setCreateTime(new Date());
							//绑定物联网卡手机号
							cardPhoneService.bindCardPhone(cardPhone);
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
	 * 物联网卡号码解绑
	 *
	 * @param cardPhone
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> unBind(CardPhone cardPhone) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = cardPhone.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(cardPhone.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_NULL.getCode();
			respDesc = ConstantsEnum.REST_CARD_NULL.getDesc();
		} else if (!Utils.cardValid(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_CARD_FORMAT.getDesc();
		} else if (!Utils.notEmpty(cardPhone.getPhone())) {
			respCode = ConstantsEnum.REST_PHONE_NULL.getCode();
			respDesc = ConstantsEnum.REST_PHONE_NULL.getDesc();
		} else if (!Utils.phoneValid(cardPhone.getPhone())) {
			respCode = ConstantsEnum.REST_PHONE_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_PHONE_FORMAT.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = cardPhone.getAppId();
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
					//物联网卡解绑手机号
					//查询物联网卡绑定手机号码是否已绑定过
					Map<String,String> mapTmp=new HashMap<String,String>();
					mapTmp.put("card",cardPhone.getCard());
					mapTmp.put("phone",cardPhone.getPhone());
					mapTmp.put("appId",cardPhone.getAppId());
					CardPhone cardPhoneTmp=cardPhoneService.findCardPhoneByMap(mapTmp);
					if(!Utils.notEmpty(cardPhoneTmp)){
						//物联网卡号码未绑定，则返回错误码
						respCode = ConstantsEnum.REST_CARDPHONE_NOTEXIST.getCode();
						respDesc = ConstantsEnum.REST_CARDPHONE_NOTEXIST.getDesc();
					}else{
						//物联网卡解绑手机号
						cardPhoneService.unbindCardPhone(cardPhone);
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

	/**
	 * 物联网卡号码查询
	 *
	 * @param cardPhone
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> queryBindInfo(CardPhone cardPhone) throws Exception {
		String respCode = null;// 响应码
		String respDesc = null;// 相应描述
		String accountSid = cardPhone.getAccountSid();

		Map map = new HashMap<>();
		// 必选字段格式验证
		if (!Utils.notEmpty(cardPhone.getAppId())) {
			respCode = ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc = ConstantsEnum.REST_APPID_NULL.getDesc();
		} else if (!Utils.notEmpty(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_NULL.getCode();
			respDesc = ConstantsEnum.REST_CARD_NULL.getDesc();
		} else if (!Utils.cardValid(cardPhone.getCard())) {
			respCode = ConstantsEnum.REST_CARD_FORMAT.getCode();
			respDesc = ConstantsEnum.REST_CARD_FORMAT.getDesc();
		} else {
			// 根据appid查找应用信息
			String appid = cardPhone.getAppId();
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
					//获取物联网卡绑定的号码列表集合
					Map<String,String> mapTmp=new HashMap<String,String>();
					mapTmp.put("card",cardPhone.getCard());
					mapTmp.put("appId",cardPhone.getAppId());
					List<CardPhone> cardPhones=cardPhoneService.findCardPhoneListBycard(mapTmp);
					if(Utils.notEmpty(cardPhones)){
						cardPhone.setCardPhones(cardPhones);
					}
					respCode = ConstantsEnum.REST_SUCCCODE.getCode();
					respDesc = ConstantsEnum.REST_SUCCCODE.getDesc();
				}
			}
		}
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}

}
