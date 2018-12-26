package com.e9cloud.util;

public enum RestError {

	// 隐私号
	/**
	 * 不存在隐私号码映射
	 */
	MASKNUM_REF_NOTEXIST("900400", "不存在隐私号码映射"),
	/**
	 * realNumA为空
	 */
	MASKNUM_REALNUMA_NULL("900401", "realNumA为空"),
	/**
	 * realNumA格式不符
	 */
	MASKNUM_REALNUMA_FORMAT("900402", "realNumA格式不符"),
	/**
	 * realNumB为空
	 */
	MASKNUM_REALNUMB_NULL("900403", "realNumB为空"),
	/**
	 * realNumB格式不符
	 */
	MASKNUM_REALNUMB_FORMAT("900404", "realNumB格式不符"),
	/**
	 * validTime格式不符
	 */
	MASKNUM_VALIDTIME_FORMAT("900405", "validTime格式不符"),
	/**
	 * 不存在有效隐私号码
	 */
	MASKNUM_NOTEXIST("900406", "不存在有效隐私号码"),

	// 子账号
	/**
	 * subName为空
	 */
	SUB_SUBNAME_NULL("900300", "subName为空"),
	/**
	 * subName长度不符
	 */
	SUB_SUBNAME_LENGTH("900301", "subName长度不符"),
	/**
	 * subName已存在
	 */
	SUB_SUBNAME_EXIST("900302", "subName已存在"),
	/**
	 * newSubName为空
	 */
	SUB_NEWSUBNAME_NULL("900303", "newSubName为空"),
	/**
	 * newSubName长度不符
	 */
	SUB_NEWSUBNAME_LENGTH("900304", "newSubName长度不符"),
	/**
	 * newSubName已存在
	 */
	SUB_NEWSUBNAME_EXIST("900305", "newSubName已存在"),
	/**
	 * subId长度不符
	 */
	SUB_SUBID_LENGTH("900306", "subId长度不符"),
	/**
	 * subId格式不符
	 */
	SUB_SUBID_FORMAT("900307", "subId格式不符"),

	// 回拨
	/**
	 * from为空
	 */
	CALL_FROM_NULL("900200", "from为空"),
	/**
	 * from格式不符
	 */
	CALL_FROM_FORMAT("900201", "from格式不符"),
	/**
	 * to为空
	 */
	CALL_TO_NULL("900202", "to为空"),
	/**
	 * to格式不符
	 */
	CALL_TO_FORMAT("900203", "to格式不符"),
	/**
	 * 主被叫显号未审核
	 */
	CALL_FROMORTOSERNUM_NOT_AUDIT("900204", "主被叫显号未审核"),
	/**
	 * 主叫显号不能为主叫号码本身
	 */
	CALL_FROMSERNUM_NOSELF("900205", "主叫显号不能为主叫号码本身"),
	/**
	 * 主叫显号未审核
	 */
	CALL_FROMSERNUM_NOT_AUDIT("900206", "主叫显号未审核"),
	/**
	 * 被叫显号不能为被叫号码本身
	 */
	CALL_TOSERNUM__NOSELF("900207", "被叫显号不能为被叫号码本身"),
	/**
	 * 被叫显号未审核
	 */
	CALL_TOSERNUM_NOT_AUDIT("900208", "被叫显号未审核"),
	/**
	 * maxCallTime格式不符
	 */
	CALL_MAXCALLTIME_FORMATT("900209", "maxCallTime格式不符"),
	/**
	 * needRecord格式不符
	 */
	CALL_NEEDRECORD_FORMAT("900210", "needRecord格式不符"),
	/**
	 * countDownTime格式不符
	 */
	CALL_COUNTDOWNTIME_FORMAT("900211", "countDownTime格式不符"),
	/**
	 * countDownTime无效
	 */
	CALL_COUNTDOWNTIME_INVALID("900212", "countDownTime无效"),
	/**
	 * callId为空
	 */
	CALL_CALLID_NULL("900213", "callId为空"),
	/**
	 * queryTime为空
	 */
	CALL_QUERYTIME_NULL("900214", "queryTime为空"),
	/**
	 * queryTime格式不符
	 */
	CALL_QUERYTIME_FORMAT("900215", "queryTime格式不符"),
	/**
	 * userData格式不符
	 */
	CALL_USERDATA_FORMAT("900216", "userData格式不符"),
	/**
	 * callId无效
	 */
	CALL_CALLID_INVALID("900217", "callId无效"),

	// 账号
	/**
	 * sig参数为空
	 */
	ACCOUNT_SIG_NULL("900100", "sig参数为空"),
	/**
	 * Authorization解密异常
	 */
	ACCOUNT_AUTH_EXCEPTION("900101", "Authorization解密异常"),
	/**
	 * Authorization格式不对
	 */
	ACCOUNT_AUTH_FORMAT("900102", "Authorization格式不对"),
	/**
	 * accountSid为空
	 */
	ACCOUNT_SID_NULL("900103", "accountSid为空"),
	/**
	 * accountSid长度不符
	 */
	ACCOUNT_SID_LENGTH("900104", "accountSid长度不符"),
	/**
	 * accountSid格式不符
	 */
	ACCOUNT_SID_FORMAT("900105", "accountSid格式不符"),
	/**
	 * 时间戳为空
	 */
	ACCOUNT_TIMESTAMP_NULL("900106", "时间戳为空"),
	/**
	 * 时间戳长度不符
	 */
	ACCOUNT_TIMESTAMP_LENGTH("900107", "时间戳长度不符"),
	/**
	 * 时间戳格式不符
	 */
	ACCOUNT_TIMESTAMP_FORMAT("900108", "时间戳格式不符"),
	/**
	 * 时间戳无效
	 */
	ACCOUNT_TIMESTAMP_INVALID("900109", "时间戳无效"),
	/**
	 * SID用户不存在
	 */
	ACCOUNT_SIDUSER_NO("900110", "SID用户不存在"),
	/**
	 * 验证参数sig不通过
	 */
	ACCOUNT_SIG_FAIL("900111", "验证参数sig不通过"),
	/**
	 * appId为空
	 */
	ACCOUNT_APPID_NULL("900112", "appId为空"),
	/**
	 * appId不存在
	 */
	ACCOUNT_APPID_NO("900113", "appId不存在"),
	/**
	 * appId不正常
	 */
	ACCOUNT_APPID_ABNORMAL("900114", "appId不正常"),
	/**
	 * appId回调url不启用
	 */
	ACCOUNT_APPID_STOP("900115", "appId回调url不启用"),
	/**
	 * appId不属于主账号
	 */
	ACCOUNT_APPID_NOT_SID("900116", "appId不属于主账号"),
	/**
	 * 主账号欠费
	 */
	ACCOUNT_SID_OVERDUE("900117", "主账号欠费"),
	/**
	 * subId为空
	 */
	ACCOUNT_SUBID_NULL("900118", "subId为空"),
	/**
	 * subId不存在
	 */
	ACCOUNT_SUBID_NO("900119", "subId不存在"),
	/**
	 * subId不属于应用
	 */
	ACCOUNT_SUBID_NOT_APPID("900120", "subId不属于应用"),
	/**
	 * subId不正常
	 */
	ACCOUNT_SUBID_ABNORMAL("900121", "subId不正常"),
	/**
	 * 主账号不正常
	 */
	ACCOUNT_SID_ABNORMAL("900122", "主账号不正常"),

	// 内部错误
	/**
	 * 未知错误
	 */
	UNKNOW("900000", "未知错误"),
	/**
	 * 内部错误
	 */
	INSIDE("900001", "内部错误"),
	/**
	 * 无效版本号
	 */
	NOVERSION("900099", "无效版本号"),

	/**
	 * 成功
	 */
	SUCCCODE("000000", "success"),;
	// 代号
	private String code;
	// 描述
	private String desc;

	RestError(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
