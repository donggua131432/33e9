package com.e9cloud.rest.action;

import com.e9cloud.rest.obt.CallBack;
import com.e9cloud.rest.obt.MaskCallBack;
import com.e9cloud.util.ConstantsEnum;
import com.e9cloud.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class CallBackValid {

	/**
	 * rest请求字段基本验证
	 * 
	 * @param callBack
	 * @return map
	 */
	public static Map baseValid(CallBack callBack) {
		Map map = new HashMap<>();
		String respCode=null;
		String respDesc=null;
		boolean flag=true;
		//必选字段非空验证
		if(!Utils.notEmpty(callBack.getAppId())){
			respCode= ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
			flag=false;
		}else if(!Utils.notEmpty(callBack.getFrom())){
			respCode=ConstantsEnum.REST_FROM_NULL.getCode();
			respDesc=ConstantsEnum.REST_FROM_NULL.getDesc();
			flag=false;
		}else if(!Utils.phonenoValid(callBack.getFrom())){
			respCode=ConstantsEnum.REST_FROM_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_FROM_FORMAT.getDesc();
			flag=false;
		}else if(!Utils.notEmpty(callBack.getTo())){
			respCode=ConstantsEnum.REST_TO_NULL.getCode();
			respDesc=ConstantsEnum.REST_TO_NULL.getDesc();
			flag=false;
		}else if(!Utils.phonenoValidB(callBack.getTo())){
			respCode=ConstantsEnum.REST_TO_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_TO_FORMAT.getDesc();
			flag=false;
		}else if(Utils.cardValid(callBack.getFrom()) && !Utils.notEmpty(callBack.getToSerNum())){
			//若主叫为物联网卡号，被叫参数必填，为空时返回错误码提示
			respCode=ConstantsEnum.REST_TOSERNUM_NULL.getCode();
			respDesc=ConstantsEnum.REST_TOSERNUM_NULL.getDesc();
			flag=false;
		}else if(callBack.getMaxCallTime()<0 || callBack.getMaxCallTime()>28800){
			//maxCallTime格式不符（不为空时，小于0 或 超过8小时）
			respCode=ConstantsEnum.REST_MAXCALLTIME_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_MAXCALLTIME_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getNeedRecord()!=0 && callBack.getNeedRecord()!=1){
			//needRecord格式不符（不为空时，非0和1数字）
			respCode=ConstantsEnum.REST_NEEDRECORD_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_NEEDRECORD_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getCountDownTime()!=0 && callBack.getCountDownTime()!=5
				&& callBack.getCountDownTime()!=30 && callBack.getCountDownTime()!=50){
			//countDownTime格式不符（不为空时，非5/30/50数字）
			respCode=ConstantsEnum.REST_COUNTDOWNTIME_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_COUNTDOWNTIME_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getCountDownTime()!=0 && (callBack.getMaxCallTime()==0
				|| callBack.getMaxCallTime()<callBack.getCountDownTime())){
			//countDownTime无效（不为空时，maxCallTime为空或maxCallTime小于countDownTime）
			respCode=ConstantsEnum.REST_COUNTDOWNTIME_INVALID.getCode();
			respDesc=ConstantsEnum.REST_COUNTDOWNTIME_INVALID.getDesc();
			flag=false;
		}else if(Utils.notEmpty(callBack.getUserData()) && callBack.getUserData().length()>128){
			//userData格式不符（不为空时，最长128字节）
			respCode=ConstantsEnum.REST_USERDATA_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_USERDATA_FORMAT.getDesc();
			flag=false;
		}
		map.put("flag",flag);
		map.put("respCode",respCode);
		map.put("respDesc",respDesc);
		return map;
	}

	/**
	 * rest 私密回拨请求字段基本验证
	 *
	 * @param callBack
	 * @return map
	 */
	public static Map maskBaseValid(MaskCallBack callBack) {
		Map map = new HashMap<>();
		String respCode=null;
		String respDesc=null;
		boolean flag=true;
		//必选字段格式验证
		if(!Utils.notEmpty(callBack.getAppId())){
			respCode= ConstantsEnum.REST_APPID_NULL.getCode();
			respDesc=ConstantsEnum.REST_APPID_NULL.getDesc();
			flag=false;
		}else if(!Utils.notEmpty(callBack.getFrom())){
			respCode=ConstantsEnum.REST_FROM_NULL.getCode();
			respDesc=ConstantsEnum.REST_FROM_NULL.getDesc();
			flag=false;
		}else if(!Utils.phoneAndTelValid(callBack.getFrom())){
			respCode=ConstantsEnum.REST_FROM_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_FROM_FORMAT.getDesc();
			flag=false;
		}else if(!Utils.notEmpty(callBack.getTo())){
			respCode=ConstantsEnum.REST_TO_NULL.getCode();
			respDesc=ConstantsEnum.REST_TO_NULL.getDesc();
			flag=false;
		}else if(!Utils.phoneAndTelValid(callBack.getTo())){
			respCode=ConstantsEnum.REST_TO_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_TO_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getMaxCallTime()<0 || callBack.getMaxCallTime()>28800){
			//maxCallTime格式不符（不为空时，小于0 或 超过8小时）
			respCode=ConstantsEnum.REST_MAXCALLTIME_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_MAXCALLTIME_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getNeedRecord()!=0 && callBack.getNeedRecord()!=1){
			//needRecord格式不符（不为空时，非0和1数字）
			respCode=ConstantsEnum.REST_NEEDRECORD_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_NEEDRECORD_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getCountDownTime()!=0 && callBack.getCountDownTime()!=5
				&& callBack.getCountDownTime()!=30 && callBack.getCountDownTime()!=50){
			//countDownTime格式不符（不为空时，非5/30/50数字）
			respCode=ConstantsEnum.REST_COUNTDOWNTIME_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_COUNTDOWNTIME_FORMAT.getDesc();
			flag=false;
		}else if(callBack.getCountDownTime()!=0 && (callBack.getMaxCallTime()==0
				|| callBack.getMaxCallTime()<callBack.getCountDownTime())){
			//countDownTime无效（不为空时，maxCallTime为空或maxCallTime小于countDownTime）
			respCode=ConstantsEnum.REST_COUNTDOWNTIME_INVALID.getCode();
			respDesc=ConstantsEnum.REST_COUNTDOWNTIME_INVALID.getDesc();
			flag=false;
		}else if(Utils.notEmpty(callBack.getUserData()) && callBack.getUserData().length()>128){
			//userData格式不符（不为空时，最长128字节）
			respCode=ConstantsEnum.REST_USERDATA_FORMAT.getCode();
			respDesc=ConstantsEnum.REST_USERDATA_FORMAT.getDesc();
			flag=false;
		}
		map.put("flag",flag);
		map.put("respCode",respCode);
		map.put("respDesc",respDesc);
		return map;
	}

}
