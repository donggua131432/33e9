package com.e9cloud.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/12/7.
 */
public class DateTools {

    public static void main(String[] args) {

        Calendar calendar = new GregorianCalendar();
        // 1. 当前登陆日期的周一至周五的日期 比如 现在是2012-06-11 周一是2012-06-11 周五就是 2012-06-15。
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        System.out.println("登录日期的周一：" + print(calendar.getTime()));
        // 2.星期五，第六天s
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        System.out.println("登录日期的周五：" + print(calendar.getTime()));
        // 3.当前月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("当前月的第一天：" + print(calendar.getTime()));
        // 4.当前月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        System.out.println("当前月的最后一天：" + print(calendar.getTime()));

        // 季度初
        calendar.setTime(new Date());
        int month = getQuarterInMonth(calendar.get(Calendar.MONTH), true);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println("当季度的第一天：" + print(calendar.getTime()));

        // 季度末
        calendar.setTime(new Date());
        month = getQuarterInMonth(calendar.get(Calendar.MONTH), false);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        System.out.println("当前时间的季度末：" + print(calendar.getTime()));
    }

    private static String print(Date d) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(d);
    }

    // 返回第几个月份，不是几月
    // 季度一年四季， 第一季度：2月-4月， 第二季度：5月-7月， 第三季度：8月-10月， 第四季度：11月-1月
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {
        int months[] = { 1, 4, 7, 10 };
        if (!isQuarterStart) {
            months = new int[] { 3, 6, 9, 12 };
        }
        if (month >= 2 && month <= 4)
            return months[0];
        else if (month >= 5 && month <= 7)
            return months[1];
        else if (month >= 8 && month <= 10)
            return months[2];
        else
            return months[3];
    }

    /**
     * 获取指定日期所在周的周一
     * @Methods Name getMonday
     * @return Date
     */
    public Date getMonday(Date date){
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_WEEK, 2);//老外将周日定位第一天，周一取第二天
        return cDay.getTime();
    }

    /**
     * 获取指定日期所在周日
     * @Methods Name getSunday
     * @return Date
     */
    public Date getSunday(Date date){
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        if(Calendar.DAY_OF_WEEK==cDay.getFirstDayOfWeek()){ //如果刚好是周日，直接返回
        return date;
        }else{//如果不是周日，加一周计算
        cDay.add(Calendar.DAY_OF_YEAR, 7);
        cDay.set(Calendar.DAY_OF_WEEK, 1);
        System.out.println(cDay.getTime());
        return cDay.getTime();
        }
    }

    /**
     * 得到本月第一天的日期
     * @Methods Name getFirstDayOfMonth
     * @return Date
     */
    public Date getFirstDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 得到本月最后一天的日期
     * @Methods Name getLastDayOfMonth
     * @return Date
     */
    public static Date getLastDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 得到本季度第一天的日期
     * @Methods Name getFirstDayOfQuarter
     * @return Date
     */
    public Date getFirstDayOfQuarter(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){
            cDay.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){
            cDay.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 得到本季度最后一天的日期
     * @Methods Name getLastDayOfQuarter
     * @return Date
     */
    public Date getLastDayOfQuarter(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int curMonth = cDay.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){
            cDay.set(Calendar.MONTH, Calendar.MARCH);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){
            cDay.set(Calendar.MONTH, Calendar.JUNE);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {
            cDay.set(Calendar.MONTH, Calendar.AUGUST);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            cDay.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }
}
