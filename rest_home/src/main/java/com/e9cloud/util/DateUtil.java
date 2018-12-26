package com.e9cloud.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/** 变量：日期格式化类型 - 格式:yyyy/MM/dd */
	public static final int DEFAULT = 0;
	public static final int YM = 1;

	/** 变量：日期格式化类型 - 格式:yyyy-MM-dd */
	public static final int YMR_SLASH = 11;


	/** 变量：日期格式化类型 - 格式:yyyyMMdd */
	public static final int NO_SLASH = 2;

	/** 变量：日期格式化类型 - 格式:yyyyMM */
	public static final int YM_NO_SLASH = 3;

	/**  变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm:ss */
	public static final int DATE_TIME = 4;

	/** 变量：日期格式化类型 - 格式:yyyyMMddHHmmss */
	public static final int DATE_TIME_NO_SLASH = 5;

	/** 变量：日期格式化类型 - 格式:yyyy/MM/dd HH:mm */
	public static final int DATE_HM = 6;

	/** 变量：日期格式化类型 - 格式:HH:mm:ss */
	public static final int TIME = 7;

	/** 变量：日期格式化类型 - 格式:HH:mm */
	public static final int HM = 8;

	/** 变量：日期格式化类型 - 格式:HHmmss */
	public static final int LONG_TIME = 9;

	/** 变量：日期格式化类型 - 格式:HHmm */
	public static final int SHORT_TIME = 10;

	/** 变量：日期格式化类型 - 格式:yyyy-MM-dd HH:mm:ss */
	public static final int DATE_TIME_LINE = 12;

	/** 变量：日期格式化类型 - 格式:yyyyMMddHHmmssSSS */
	public static final int DATE_TIME_SLASH = 13;

	public static String dateToStr(Date date, int type) {
		switch (type) {
		case DEFAULT:
			return dateToStr(date);
		case YM:
			return dateToStr(date, "yyyy/MM");
		case NO_SLASH:
			return dateToStr(date, "yyyyMMdd");
		case YMR_SLASH:
			return dateToStr(date, "yyyy-MM-dd");
		case YM_NO_SLASH:
			return dateToStr(date, "yyyyMM");
		case DATE_TIME:
			return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
		case DATE_TIME_NO_SLASH:
			return dateToStr(date, "yyyyMMddHHmmss");
		case DATE_HM:
			return dateToStr(date, "yyyy/MM/dd HH:mm");
		case TIME:
			return dateToStr(date, "HH:mm:ss");
		case HM:
			return dateToStr(date, "HH:mm");
		case LONG_TIME:
			return dateToStr(date, "HHmmss");
		case SHORT_TIME:
			return dateToStr(date, "HHmm");
		case DATE_TIME_LINE:
			return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
		case DATE_TIME_SLASH:
				return dateToStr(date, "yyyyMMddHHmmssSSS");
		default:
			throw new IllegalArgumentException("Type undefined : " + type);
		}
	}

	public static String dateToStr(Date date, String pattern) {
		if (date == null || date.equals(""))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy/MM/dd");
	}

	public static Date strToDate(String str, String pattern) throws ParseException {
		if (str == null || str.equals(""))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(str);
	}

	/**
	 * 当天凌晨3点
	 * 
	 * @return
	 */
	public static Date zoreThree() {
		String date = dateToStr(new Date());
		String pattern = date + " 03:00";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			return formatter.parse(pattern);
		} catch (ParseException e) {
			return new Date();
		}

	}

	public static long MINUTE = 60 * 1000;
	public static long HOUR = 60 * MINUTE;
	public static long DAY = 24 * HOUR;

	/**
	 计算到date当前时间的间隔秒
	 *
	 * */
	public static Integer secondOfTwoDate(Date date) {
		long curTime=new Date().getTime();
		long dateTime=curTime;
		if(Utils.notEmpty(date)){
			dateTime=date.getTime();
		}

		return (int)(dateTime-curTime)/1000;
	}

	public static Date addDate(int second) {
		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.SECOND,second);
		return calendar.getTime();
	}

	public static String getSecond(String startTime, String endTime) throws ParseException {
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmss");
		long a = sdf.parse(endTime).getTime();
		long b = sdf.parse(startTime).getTime();
		int c = (int)((a - b) / 1000);
		return String.valueOf(c);
	}

	public static void main(String[] args) throws ParseException {
		Date date=new Date();
		try {
			date=strToDate("20160614000000","yyyyMMddHHmmss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(secondOfTwoDate(date));


		System.out.println(getSecond("20170422160030", "20170422160130"));
	}


}
