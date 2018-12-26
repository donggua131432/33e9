
package com.e9cloud.util;

public enum ConstantsEnum {

	/***   整形枚举    start***/
	REQ_CODE(1),
	RET_CODE(2),
	/***   整形枚举    end***/


	/***   字符串枚举    start***/
	/** 消息类型枚举:
	    1，请求；
		2，响应；
		3，通知；
		4，心跳；
		5，取消回拨；
		6，隐私回拨请求；
		7，隐私通知；
		8，搜房回呼通知；
		9，呼叫样本数据；
		10，虚拟号码回呼通知
		11，语音通知请求；
		12，语音通知回调；
		13，SipPhone回拨请求；
		14，SipPhone回拨回调；
		15，SipPhone直拨回调；
		16，SipPhone回呼回调；
	    17，语音验证码请求
	    18，语音验证码回调
	 */




	REST_REQUEST_CODE("0001"),
	REST_RESPONSE_CODE("0002"),
	REST_NOTIFY_CODE("0003"),
	REST_HERT_CODE("0004"),
	REST_CANCEL_CODE("0005"),
	REST_MASK_REQUEST_CODE("0006"),
	REST_MASK_NOTIFY_CODE("0007"),
	REST_SFHH_NOTIFY_CODE("0008"),
	REST_VIRTUAL_NOTIFY_CODE("0010"),
	REST_VOICE_REQUEST_CODE("0011"),
	REST_VOICE_NOTIFY_CODE("0012"),
	REST_SIPPHONE_REQUEST_CODE("0013"),
	REST_SIPPHONE_NOTIFY_CODE("0014"),
	REST_SIPPHONE_DIRECT_NOTIFY_CODE("0015"),
	REST_SIPPHONE_CALLBACK_NOTIFY_CODE("0016"),
	REST_VOICE_VALIDATE_REQUEST_CODE("0017"),
	REST_VOICE_VALIDATE_NOTIFY_CODE("0018"),
	// 统计MogonDb表名称
	RESTREQ_NAME("RestReqResInfo"),
	RESTREQTYPE_XML("xml"),
	RESTREQTYPE_JSON("json"),

	INTERFACE_REQTYPE_REST("rest"),
	INTERFACE_REQTYPE_MASK("mask"),
	INTERFACE_REQTYPE_VN("vn"),
	INTERFACE_REQTYPE_AUTH("auth"),
	/***   字符串枚举    end***/

	REST_SUCCCODE("000000","success"),
	/***   REST错误码和错误描述    start***/
	//	内部错误
	REST_UNKNOW("900000","未知错误"),
	REST_INSIDE("900001","内部错误"),
	REST_ROOT_ERROR("900097","报文根元素错误"),
	REST_PARAM_ERROR("900098","参数错误"),

	//公共错误
	REST_NOVERSION("900100","无效版本号"),
	REST_SIG_NULL("900101","sig参数为空"),
	REST_AUTH_EXCEPTION("900102","Authorization解密异常"),
	REST_AUTH_FORMAT("900103","Authorization格式不对"),
	REST_SID_NULL("900104","accountSid为空"),
	REST_SID_LENGTH("900105","accountSid长度不符"),
	REST_SID_FORMAT("900106","accountSid格式不符"),
	REST_TIMESTAMP_NULL("900107","时间戳为空"),
	REST_TIMESTAMP_LENGTH("900108","时间戳长度不符"),
	REST_TIMESTAMP_FORMAT("900109","时间戳格式不符"),
	REST_TIMESTAMP_INVALID("900110","时间戳无效"),
	REST_SIDUSER_NO("900111","SID用户不存在"),
	REST_SIG_FAIL("900112","验证参数sig不通过"),
	REST_VOICE_CODE("900113","请设置语音编码"),

	//账号
	REST_APPID_NULL("900200","appId为空"),
	REST_APPID_NO("900201","appId不存在"),
	REST_APPID_ABNORMAL("900202","appId不正常"),
	REST_APPID_STOP("900203","appId回调url不启用"),
	REST_APPID_NOT_SID("900204","appId不属于主账号"),
	REST_SID_OVERDUE("900205","主账号欠费"),
	REST_SUBID_NULL("900206","subId为空"),
	REST_SUBID_NO("900207","subId不存在"),
	REST_SUBID_NOT_APPID("900208","subId不属于应用"),
	REST_SUBID_ABNORMAL("900209","subId不正常"),
	REST_SID_ABNORMAL("900210","主账号不正常"),
	REST_APPID_LIMITDUE("900211","appId已超出限额"),
	REST_SUBNAME_NULL("900212","subName为空"),
	REST_SUBNAME_LENGTH("900213","subName长度不符"),
	REST_SUBNAME_EXIST("900214","subName已存在"),
	REST_NEWSUBNAME_NULL("900215","newSubName为空"),
	REST_NEWSUBNAME_LENGTH("900216","newSubName长度不符"),
	REST_NEWSUBNAME_EXIST("900217","newSubName已存在"),
	REST_SUBID_LENGTH("900218","subId长度不符"),
	REST_SUBID_FORMAT("900219","subId格式不符"),
	REST_BUS_TYPE_ERROR("900220","应用业务类型错误"),

	//	专线语音
	REST_FROM_NULL("900300","主叫为空"),
	REST_FROM_FORMAT("900301","主叫格式不符"),
	REST_TO_NULL("900302","被叫为空"),
	REST_TO_FORMAT("900303","被叫格式不符"),
	REST_FROMORTOSERNUM_NOT_AUDIT("900304","主被叫显号未审核"),
	REST_FROMSERNUM_NOSELF("900305","主叫显号不能为主叫号码本身"),
	REST_FROMSERNUM_NOT_AUDIT("900306","主叫显号未审核"),
	REST_TOSERNUM_NOSELF("900307","被叫显号不能为被叫号码本身"),
	REST_TOSERNUM_NOT_AUDIT("900308","被叫显号未审核"),
	REST_MAXCALLTIME_FORMAT("900309","maxCallTime格式不符"),
	REST_NEEDRECORD_FORMAT("900310","needRecord格式不符"),
	REST_COUNTDOWNTIME_FORMAT("900311","countDownTime格式不符"),
	REST_COUNTDOWNTIME_INVALID("900312","countDownTime无效"),
	REST_CALLID_NULL("900313","callId为空"),
	REST_QUERYTIME_NULL("900314","queryTime为空"),
	REST_QUERYTIME_FORMAT("900315","queryTime格式不符"),
	REST_USERDATA_FORMAT("900316","userData格式不符"),
	REST_CALLID_INVALID("900317","callId无效"),
	REST_TYPE_NULL("900318","type为空"),
	REST_CALLNOTIFY_OUTOF48("900319","通话记录已超过48小时"),
	REST_FROM_NUMBERBLACK("900320","主叫黑名单号码限制"),
	REST_TO_NUMBERBLACK("900321","被叫黑名单号码限制"),
	REST_FROMSERNUM_NUMBERBLACK("900322","主叫显号黑名单号码限制"),
	REST_TOSERNUM_NUMBERBLACK("900323","被叫显号黑名单号码限制"),
	REST_CALLNOTIFY_TIMESLIMIT("900324","查询次数超限"),
	REST_NUMBERTYPE_NULL("900325","numberType为空"),
	REST_NUMBERTYPE_FORMAT("900326","numberType格式不符"),
	REST_ACTION_NULL("900327","action为空"),
	REST_ACTION_FORMAT("900328","action格式不符"),
	REST_NUMBER_NULL("900329","number为空"),
	REST_TYPE_FORMAT("900330","type格式不符"),
	REST_FROM_TO_SAME("900331","主被叫号码相同"),

	//  专号通
	REST_MASKNUMREF_NOTEXIST("900400","不存在隐私号码映射"),
	REST_REALNUMA_NULL("900401","realNumA为空"),
	REST_REALNUMA_FORMAT("900402","realNumA格式不符"),
	REST_REALNUMB_NULL("900403","realNumB为空"),
	REST_REALNUMB_FORMAT("900404","realNumB格式不符"),
	REST_VALIDTIME_FORMAT("900405","validTime格式不符"),
	REST_MASKNUM_NOTEXIST("900406","不存在有效隐私号码"),
	REST_REALNUMAB_EQUALS("900407","realNumA和realNumB相同"),
	REST_AREACODE_FORMAT("900408","areaCode格式不符"),
	REST_MASKNUMREF_EXIST("900409","隐私映射已存在"),

	//  物联网卡
	REST_CARD_NULL("900500","card为空"),
	REST_CARD_FORMAT("900501","card格式不符"),
	REST_PHONE_NULL("900502","phone为空"),
	REST_PHONE_FORMAT("900503","phone格式不符"),
	REST_CARDPHONE_EXIST("900504","物联网卡号码已绑定"),
	REST_CARDPHONE_NOTEXIST("900505","物联网卡号码未绑定"),
	REST_CARDPHONE_UPLIMIT("900506","同一物联网卡最多绑定五个号码"),
	REST_TOSERNUM_NULL("900507","被叫显号为空"),
	REST_TOSERNUM_UNBIND("900508","被叫显号属于未绑定号码"),

	//语音通知
	REST_VOICE_RECID_NULL("900600","语音通知模板voiceRecId为空"),
	REST_VOICE_TO_LIST_MAX("900601","被叫号码列表超过最大值"),
	REST_VOICE_DTMF_FLAG("900602","反馈接收开关格式不符"),
	REST_VOICE_ORDER_FLAG("900603","预约下发开关格式不符"),
	REST_VOICE_ORDER_TIME("900604","预约下发时间格式不符"),
	REST_VOICE_TIME_COMPARE("900605","预约下发时间必须大于当前系统时间"),
	REST_VOICE_MODULE_PARAMS("900606","模板参数格式不符"),
	REST_VOICE_RECID("900607","语音通知模板不存在"),
	REST_REQUESTID_NULL("900608","requestId为空"),
	REST_REQUESTID_NOTEXIST("900609","requestId不存在"),
	REST_REQUESTID_PUSH("900610","预约已下发"),
	REST_REQUESTID_CANCEL("900611","预约已取消"),
	REST_VOICE_TIME_MAX("900612","预约时间不能超过24小时"),
	REST_VOICE_RECID_NUM("900613","语音通知模板voiceRecId格式不符"),
	REST_VOICE_NOTEXIST("900614","语音通知状态回调不存在"),

	//sipPhone
	REST_SIPPHONE_FROM_NOTAUDIT("900700","主叫号码未审核"),
	REST_SIPPHONE_TO_NOTAUDIT("900701","被叫号码未审核"),
	REST_SIPPHONE_CALLBACK_NOTBUS("900702","SipPhone间回拨业务未开通"),
	REST_SIPPHONE_APP_EXTRA("900703","未设置应用扩展信息"),
	REST_SIPPHONE_NULL("900704","Sip号码为空"),
	REST_SIPPHONE_NOMATCH("900705","Sip号码格式不符"),
	REST_SIPPHONE_NOTAUDIT("900706","Sip号码未审核"),
	REST_SIPPHONE_SWITCH_NULL("900707","呼叫标识为空"),
	REST_SIPPHONE_LONG_NULL("900708","长途标识为空"),
	REST_SIPPHONE_FROM_DISABLE("900709","主叫Sip号码已禁用"),
	REST_SIPPHONE_TO_DISABLE("900710","被叫Sip号码已禁用"),
	REST_SIPPHONE_NOLONG("900711","无长途权限"),
	REST_SIPPHONE_SWITCH_NOMAT("900712","呼叫标识错误"),
	REST_SIPPHONE_LONG_NOMAT("900713","长途标识错误"),

	//虚拟小号Rest接口
	REST_AXB_NUMBER_NOT_EXIST("901100","不存在虚拟小号绑定关系"),
	REST_AXB_TELA_NULL("901101","telA为空"),
	REST_AXB_TELA_FORMAT("901102","telA格式不符"),
	REST_AXB_TELB_NULL("901103","telB为空"),
	REST_AXB_TELB_FORMAT("901104","telB格式不符"),
	REST_AXB_EXPIRATION_FORMAT("901105","expiration格式不符"),
	REST_AXB_TEL_EQUALS("901106","telA和telB相同"),
	REST_AXB_AREACODE_NULL("901107","areacode为空"),
	REST_AXB_AREACODE_FORMAT("901108","areacode格式不符"),
	REST_AXB_USERDATA_OUT("901109","userData数据长度超出范围"),
	REST_AXB_BUSINESS_TYPE_OPEN("901110","没有开通虚拟小号业务"),
	REST_AXB_BIND_ID_NULL("901111","绑定ID为空"),
	REST_AXB_HTTP_FAIL("901112","内部http请求失败"),
	REST_AXB_HTTP_TIME_OUT("901113","内部http请求连接超时"),
	REST_AXB_TELX_NULL("901114","号码池为空"),
	REST_AXB_TELX_NOT("901115","号码池无可用的虚拟小号"),
	REST_AXB_CALLRECORDING_FORMAT("901116","录音控制格式不符"),
	REST_AXB_OTHER_ERROR("901117","其它错误"),


	//语音验证码
	REST_VOICEVALIDATE_VOICERECID_FORMAT("900800","模板ID格式不符"),
	REST_VOICEVALIDATE_VERIFYCODE_NULL("900801","验证码为空"),
	REST_VOICEVALIDATE_VERIFYCODE_FORMAT("900802","验证码格式不符"),
	REST_VOICEVALIDATE_CALLED_NULL("900803","被叫电话号码为空"),
	REST_VOICEVALIDATE_CALLED_FORMAT("900804","被叫电话号码格式不符"),
	REST_VOICEVALIDATE_DISPLAYNUM_FORMAT("900805","被叫显号格式不符"),
	REST_VOICEVALIDATE_DISPLAYNUM_NULL("900806","显号池无可用显号"),
	REST_VOICEVALIDATE_DISPLAYNUM_NOT("900807","显号池无此号码"),
	REST_VOICEVALIDATE_TO_NUMBERBLACK("900808","被叫黑名单号码限制"),
	REST_VOICEVALIDATE_TOSERNUM_NUMBERBLACK("900809","被叫显号黑名单号码限制"),
	REST_VOICEVERIFY_ISNOT("900810","语音模板不存在"),
	REST_VOICEVERIFY_AUDIT("900811","语音模板未审核"),
	REST_VOICEVERIFY_AUDIT_NO("900812","语音模板审核未通过");
	/***   REST错误码和错误描述    end***/
	
	private int intValue;
	public int getIntValue(){
		return intValue;
	}
	private ConstantsEnum(int intValue){
		this.intValue=intValue;
	}

	private String strValue;
	public String getStrValue(){
		return strValue;
	}
	private ConstantsEnum(String strValue){
		this.strValue=strValue;
	}

	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	private ConstantsEnum(String code,String desc){
		this.code=code;
		this.desc=desc;
	}

	public static void main(String[] args)
	{
		System.out.println(ConstantsEnum.REQ_CODE.getIntValue());
		System.out.println(ConstantsEnum.REST_NOTIFY_CODE.getStrValue());
		System.out.println(ConstantsEnum.REST_AUTH_EXCEPTION);
		System.out.println(ConstantsEnum.REST_AUTH_EXCEPTION.getCode());
		System.out.println(ConstantsEnum.REST_AUTH_EXCEPTION.getDesc());
		System.out.println(ConstantsEnum.REST_AUTH_EXCEPTION.getIntValue());
		System.out.println(ConstantsEnum.REST_AUTH_EXCEPTION.getStrValue());

	}
}
