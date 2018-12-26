package com.e9cloud.rest.obt;

import com.e9cloud.util.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MaskNumber {
	private String appId;// 应用ID
	private String realNumA;// 申请隐私号码的真实号码1
	private String realNumB;// 申请隐私号码的真实号码2
	private Integer needRecord;// 0表示不录音；1表示录音；不传默认值0。该录音标志位标记这一个映射关系的回拨及回呼是否录音
	private Integer validTime;// 隐私号码有效时间，秒为单位；例如3600秒-有效时间为一个小时，不传默认24小时有效
	private String areaCode;// 传入区号，根据区号选择对应地区的可用隐私号码，不传则隐私号码池全局选择隐私号码

	// 扩增字段
	private String accountSid;
	private String maskNumber;// 隐私号码
	// 用于查询应用占用中全部隐私号码映射列表
	private List<MaskNumber> maskNumberList;

	// 将对象字段转换成Json字符串
	public String toJson() {
		return "\"realNumA\":\"" + realNumA + "\"," + "\"realNumB\":\"" + realNumB + "\"," + "\"maskNumber\":\""
				+ maskNumber + "\"," + "\"areaCode\":\"" + areaCode + "\"," + "\"validTime\":" + validTime + ","
				+ "\"needRecord\":" + needRecord;
	}

	// 将对象字段转换成XML字符串
	public String toXML() {
		return "<realNumA>" + realNumA + "</realNumA>" + "<realNumB>" + realNumB + "</realNumB>" + "<maskNumber>"
				+ maskNumber + "</maskNumber>" + "<areaCode>" + areaCode + "</areaCode>" + "<validTime>" + validTime
				+ "</validTime>" + "<needRecord>" + needRecord + "</needRecord>";
	}

	// 将maskNumberList转换成Json字符串
	public String toListJson() {
		StringBuffer str = new StringBuffer();
		if (Utils.notEmpty(maskNumberList)) {
			// 'maskNumberList':[{'':''},{'':''}]
			str.append(",");
			str.append("\"maskNumberList\":");
			str.append(new Gson().toJson(maskNumberList));
		}
		return str.toString();
	}

	// 将maskNumberList转换成XML字符串
	public String toListXML() {
		StringBuffer str = new StringBuffer();
		if (Utils.notEmpty(maskNumberList)) {
			str.append("<maskNumberList>");
			for (MaskNumber maskNumber : maskNumberList) {
				str.append("<maskNumber>").append("<realNumA>" + maskNumber.getRealNumA() + "</realNumA>")
						.append("<realNumB>" + maskNumber.getRealNumB() + "</realNumB>")
						.append("<maskNumber>" + maskNumber.getMaskNumber() + "</maskNumber>")
						.append("<areaCode>" + maskNumber.getAreaCode() + "</areaCode>")
						.append("<validTime>" + maskNumber.getValidTime() + "</validTime>")
						.append("<needRecord>" + maskNumber.getNeedRecord() + "</needRecord>").append("</maskNumber>");
			}
			str.append("</maskNumberList>");
		}
		return str.toString();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getRealNumA() {
		return realNumA;
	}

	public void setRealNumA(String realNumA) {
		this.realNumA = realNumA;
	}

	public String getRealNumB() {
		return realNumB;
	}

	public void setRealNumB(String realNumB) {
		this.realNumB = realNumB;
	}

	public Integer getNeedRecord() {
		return needRecord;
	}

	public void setNeedRecord(Integer needRecord) {
		this.needRecord = needRecord;
	}

	public Integer getValidTime() {
		return validTime;
	}

	public void setValidTime(Integer validTime) {
		this.validTime = validTime;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAccountSid() {
		return accountSid;
	}

	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	public String getMaskNumber() {
		return maskNumber;
	}

	public void setMaskNumber(String maskNumber) {
		this.maskNumber = maskNumber;
	}

	public List<MaskNumber> getMaskNumberList() {
		return maskNumberList;
	}

	public void setMaskNumberList(List<MaskNumber> maskNumberList) {
		this.maskNumberList = maskNumberList;
	}

	public static void main(String[] args) {
		MaskNumber maskNumber = new MaskNumber();
		maskNumber.setRealNumA("110");
		maskNumber.setRealNumB("220");
		maskNumber.setMaskNumber("000");
		maskNumber.setAreaCode("0755");
		maskNumber.setValidTime(3600);
		maskNumber.setNeedRecord(1);
		MaskNumber maskNumber1 = new MaskNumber();
		maskNumber1.setRealNumA("111");
		maskNumber1.setRealNumB("221");
		maskNumber1.setMaskNumber("001");
		maskNumber1.setAreaCode("0755");
		maskNumber1.setValidTime(3600);
		maskNumber1.setNeedRecord(1);
		MaskNumber maskNumber2 = new MaskNumber();
		maskNumber2.setRealNumA("112");
		maskNumber2.setRealNumB("222");
		maskNumber2.setMaskNumber("002");
		maskNumber2.setAreaCode("0755");
		maskNumber2.setValidTime(3600);
		maskNumber2.setNeedRecord(1);
		List<MaskNumber> maskNumberList = new ArrayList<MaskNumber>();
		maskNumberList.add(maskNumber1);
		maskNumberList.add(maskNumber2);

		maskNumber.setMaskNumberList(maskNumberList);

		String body = new Gson().toJson(maskNumber);
		System.out.println(body);
		body = new Gson().toJson(maskNumberList);
		System.out.println(body);
		body = new Gson().toJson(maskNumber1);
		System.out.println(body);
		// {"realNumA":"110","realNumB":"220","needRecord":1,"validTime":3600,"areaCode":"0755",
		// "maskNumber":"000","maskNumberList":[{"realNumA":"111","realNumB":"221","needRecord":1,
		// "validTime":3600,"areaCode":"0755","maskNumber":"001"},{"realNumA":"112","realNumB":"222",
		// "needRecord":1,"validTime":3600,"areaCode":"0755","maskNumber":"002"}]}
	}
}
