package com.e9cloud.rest.obt;

import com.e9cloud.telno.TelnoUtil;

/**
 * Created by Administrator on 2016/5/13.
 */
public class TelnoObj {
	private String areacode;
	private String operator;

	public String getAreacode() {
		return areacode;
	}

	public String getOperator() {
		return operator;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public TelnoObj(String areacode, String operator) {
		this.areacode = areacode;
		this.operator = operator;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(operator);
		result.append(areacode);
		return TelnoUtil.format(result);
	}
}
