package com.e9cloud.core.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.e9cloud.mybatis.domain.*;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;

import com.google.common.collect.ImmutableList;

/**
 * 类作用：工具类
 *
 * @author wzj
 */
public class Tools {

    // 私有化权限
    private Tools() {
    }

    /**
     * 将用","分割的字符串转换为List列表
     *
     * @param str
     *          要拆分的字符串
     * @return List<String>
     */
    public static List<String> stringToList(String str) {
        return stringToList(str, ",");
    }

    /**
     * 用某个字符分割的字符串并转换为List列表
     *
     * @param str 要拆分的字符串
     * @param split
     *            分割符
     * @return List<String>
     */
    public static List<String> stringToList(String str, String split) {
        return ImmutableList.copyOf(StringUtils.split(str, split));
    }

    /**
     * 将用","分割的字符串转换为Array列表
     *
     * @param str
     *          要拆分的字符串
     * @return Array
     */
    public static String[] stringToArray(String str) {
        return stringToArray(str, ",");
    }

    /**
     * 将数组中的元素用连接符链接
     *
     * @param objects 要拆分的字符串
     * @param split
     *            分割符
     * @return Array
     */
    public static String arrayJoinWith(Object[] objects, String split) {
        String str = "";
        for (Object o : objects) {
            str += split + o.toString();
        }
        if (isNotNullStr(str)) {
            str = str.substring(split.length());
        }
        return str;
    }

    /**
     * 用某个字符分割的字符串并转换为Array列表
     *
     * @param str 要拆分的字符串
     * @param split
     *            分割符
     * @return Array
     */
    public static String[] stringToArray(String str, String split) {
        return StringUtils.split(str, split);
    }




    /**
     * 用于domain类的toString方法
     *
     * @param obj
     *            可传入domain类的当前对象this
     * @return string
     */
    public static String toString(Object obj) {
        return ToStringBuilder.reflectionToString(obj);
    }

    /**
     * 字符串非空判断
     *
     * @param str
     * @return true/false
     */
    public static boolean isNotNullStr(String str) {
        return null != str && !"".equals(str.trim()) && !"null".equals(str);
    }

    public static Map<String, Object> listToMap(List lists, String key, String value, Class entiyClass) throws Exception {
        Map<String, Object> maps = new HashMap<>();

        for (Object o : lists) {
            Object kval = null;
            Object vval = null;
            Field[] fs = entiyClass.getDeclaredFields();
            for(int i = 0 ; i < fs.length; i++){
                Field f = fs[i];
                if (f.getName().equals(key)) {
                    f.setAccessible(true); //设置些属性是可以访问的
                    kval = f.get(o);//得到此属性的值
                }
                if (f.getName().equals(value)) {
                    f.setAccessible(true); //设置些属性是可以访问的
                    vval = f.get(o);//得到此属性的值
                }
            }
            maps.put(kval.toString(), vval);
        }

        return maps;
    }

    /**
     * 字符串非空判断
     *
     * @param str 字符串
     * @return true/false
     */
    public static boolean isNullStr(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotNull(Object o) {
        return !isNull(o);
    }

    /**
     * 数组非空判断
     *
     * @param obj object数组
     * @return true/false
     */
    public static boolean isNotNullArray(Object[] obj) {
        return obj != null && obj.length > 0;
    }

    /**
     * 根据请求判断是否是来自手机端的请求
     * @param request
     * @return
     */
    public static boolean isMoblie(HttpServletRequest request) {
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

        // 移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

        String userAgent = request.getHeader("User-Agent").toLowerCase();

        if (null == userAgent) {
            userAgent = "";
        }

        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);

        return matcherPhone.find() || matcherTable.find();
    }

    public static String toYW(String yw) { // 业务转换
        if (Tools.isNotNullStr(yw)) {
            return yw.replace("rest","专线语音").replace("mask","专号通")
                    .replace("ecc", "云总机").replace("cc","智能云调度")
                    .replace("voice","语音通知").replace("sp","云话机")
                    .replace("sip","SIP业务").replace("vn","虚拟号");
        }
        return "";
    }

    /**
     * 读取文件名(不包括后缀)
     *
     * @param fileName 文件名（text.txt）
     * @return text
     */
    public static String getFileName(String fileName) {
        if (isNullStr(fileName)) {
            return "";
        }
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 读取文件后缀名
     *
     * @param fileName 文件名（text.txt）
     * @return 后缀名
     */
    public static String getSuffix(String fileName) {
        if (isNullStr(fileName)) {
            return null;
        }
        if (!fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 得到之前的日期
     * @param days
     * @return
     */
    public static String[] getBeforeDays(int days) {
        if (days > 0) {
            return null;
        }
        int cnt = Math.abs(days);
        String[] datestrs = new String[cnt];
        for (int i=0; i<cnt; i++) {
            datestrs[i] = formatDate(addDay(new Date(), days + i), "yyyy-MM-dd");
        }
        return datestrs;
    }

    /**
     * 增加或减少月数
     * @param date 日期
     * @param months 月数
     * @return
     */
    public static Date addMonth(Date date, int months){
        if (date == null) {
            return null;
        }
        return DateUtils.addMonths(date,months);
    }

    /**
     * 增加或减少天数
     * @param date 日期
     * @param days 天数
     * @return
     */
    public static Date addDay(Date date, int days){
        if (date == null) {
            return null;
        }
        return DateUtils.addDays(date, days);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return  simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date parseDate(String date) {
        return parseDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseDate(String date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return  simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 得到某天的开始时间
     * @param d 前后几天
     * @return
     */
    public static Date getDate(int d){
        return getStartDate(DateUtils.addDays(new Date(), d));
    }

    /**
     * 得到某天的开始时间
     * @param date 某天
     * @return
     */
    private static Date getStartDate(Date date){
        date = DateUtils.setHours(date, 0);
        date = DateUtils.setMinutes(date, 0);
        date = DateUtils.setSeconds(date, 0);
        date = DateUtils.setMilliseconds(date, 0);
        return date;
    }

    /**
     * bit 转 十进制
     * @return
     */
    public static int bitToDecimal(int[] bits){
        int i = 0;
        if (bits != null) {
            for (int bit : bits) {
                i |= (1 << bit);
            }
        }
        return i;
    }

    /**
     * 十进制 转 bit位数组
     * @return
     */
    public static Object[] decimalToBits(int dec){
        List<Integer> bits = new ArrayList<>();
        String binaryString = Integer.toBinaryString(dec);

        for (int i = 0; i< binaryString.length(); i++) {
            if ((dec & (1 << i)) != 0) {
                bits.add(i);
            }
        }

        return bits.toArray();
    }

    /**
     * 十进制 转 bit位数组
     * @return
     */
    public static String decimalToBitStr(int dec){
        String bits = "";
        String binaryString = Integer.toBinaryString(dec);

        for (int i = 0; i< binaryString.length(); i++) {
            if ((dec & (1 << i)) != 0) {
                bits += "," + i;
            }
        }

        if (bits.length() > 0){
            bits = bits.substring(1);
        }

        return bits;
    }

    /**
     * 得到日期路径
     */
    public static String composeDataPath(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
    /**
     * 得到6位随机数字
     *
     * @return 6位随机数
     */
    public static String getRandomNum() {
        Random r = new Random();
        int i = r.nextInt(100000);
        i = i + (r.nextInt(9) + 1) * 100000;
        return String.valueOf(i);
    }

    public static String toStr(Object o) {
        return o == null ? "" : o.toString();
    }

    public static String decode(Object o, String... strs) {

        if (strs == null){
            return toStr(o);
        }

        int len = strs.length;
        if (len == 1) {
            return strs[0];
        }

        for (int i=0; i<len-1; i+=2) {
            if (toStr(o).equals(strs[i])) {
                return strs[i+1];
            }
        }

        return strs.length%2 == 0 ? toStr(o) : strs[len-1];
    }

    /**
     * 根据模板和参数生产目标消息
     * @param temp
     * @param params
     * @return
     */
    public static String process(String temp, Map<String, Object> params) {

        if (params != null && isNotNullStr(temp)) {
            for (Entry<String, Object> entry : params.entrySet()) {
                temp = temp.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue() + "");
            }
        }

        return temp;
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    public static  Map<String,String> listToMap(List<Map<String,String>> list){
        Map<String,String> retMap = new HashMap<String,String>();
        if(list!=null&&list.size()>0){
            for (Map<String,String> maps:list){
                    String appid = maps.get("APPID");
                    String appName = maps.get("APP_NAME");
                    retMap.put(appid,appName);
            }
        }
        return  retMap;
    }
    /**
     * 编码
     * @param bstr
     * @return String
     */
    public static String encode(byte[] bstr){
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码
     * @param str
     * @return string
     */
    public static byte[] decode(String str){
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer( str );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }
    public static void main(String[] args) {
        System.out.println(stringToArray("appid","@"));
    }

}