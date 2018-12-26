package com.e9cloud.telno;

/**
 * 号码工具 Created by Administrator on 2016/5/13.
 */
public class TelnoUtil {

	/**
	 * 获得域1类型
	 * 
	 * @param phone显号
	 * @param dest目的地
	 * @return
	 */
	public static NumType getRnType(String phone, String dest) {
		if (phone == null || phone.isEmpty()) {
			// 不需要显号
			return NumType.NO_NUM;
		} else if (phone.startsWith("1") && phone.length() == 11) {
			if (dest != null && dest.startsWith("1") && dest.length() == 11) {
				return NumType.PHONE_DEST;
			}
			// 手机
			return NumType.PHONE;
		} else if (phone.startsWith("95")) {
			// 955
			return NumType.NINE_FIVE;
		} else if (phone.startsWith("400")) {
			// 400
			return NumType.FOUR_ZERO_ZERO;
		}
		// 固话
		return NumType.FIXED;
	}

	/**
	 * 不足七位的后面补0,超过截取
	 * 
	 * @param result
	 * @return
	 */
	public static String format(StringBuffer result) {
		if (result.length() > 7) {
			return result.substring(0, 7);
		}
		while (result.length() < 7) {
			result.append("0");
		}

		return result.toString();
	}
}
