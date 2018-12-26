package com.e9cloud.core.util;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.e9cloud.mybatis.domain.*;
import com.google.gson.Gson;
import org.apache.commons.lang3.ArrayUtils;
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
     * 将用","分割的字符串转换为Array列表
     *
     * @param strs
     *          要拆分的字符串
     *          join
     * @return Array
     */
    public static String joinArray(String[] strs, String join) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<strs.length; i ++) {
            if (i == 0) {
                sb.append(strs[i]);
            } else {
                sb.append(join).append(strs[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 将用","分割的字符串转换为Array列表
     *
     * @param strs
     *          要拆分的字符串
     *          join
     * @return Array
     */
    public static String joinList(List<String> strs, String join) {
        if (isEmptyList(strs)) return "";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<strs.size(); i ++) {
            if (i == 0) {
                sb.append(strs.get(i));
            } else {
                sb.append(join).append(strs.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * list 是不是空
     * @param list
     * @return
     */
    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
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

    /**
     * 字符串非空判断
     *
     * @param str 字符串
     * @return true/false
     */
    public static boolean isNullStr(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str);
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
     * 比较两个字符串是否相等
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return boolean
     */
    public static boolean eqStr(String str1, String str2) {
        return toStr(str1).equals(toStr(str2));
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

    public static String formatDate(Object date) {
        if (date instanceof Date) {
            return formatDate((Date)date, "yyyy-MM-dd HH:mm:ss");
        }

        return "";
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
     * bit 转 十进制
     * @return
     */
    public static long bitToLong(int[] bits){
        long i = 0;
        if (bits != null) {
            for (int bit : bits) {
                i |= (1 << bit);
            }
        }
        return i;
    }

    /**
     * bit 转 十进制
     * @return
     */
    public static long bitToLong(String[] bits){
        long i = 0;
        if (bits != null) {
            for (String bit : bits) {
                int b = Integer.valueOf(bit);
                i |= (1 << b);
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
    public static Object[] decimalToBits(long dec){
        List<Integer> bits = new ArrayList<>();
        String binaryString = Long.toBinaryString(dec);

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
    public static Integer[] decimalTointBits(int dec){
        List<Integer> bits = new ArrayList<>();
        String binaryString = Integer.toBinaryString(dec);

        for (int i = 0; i< binaryString.length(); i++) {
            if ((dec & (1 << i)) != 0) {
                bits.add(i);
            }
        }

        return bits.toArray(new Integer[bits.size()]);
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
     * 十进制 转 bit位数组
     * @return
     */
    public static String decimalToBitStr(long dec){
        String bits = "";
        String binaryString = Long.toBinaryString(dec);

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

    public static  void main(String args[]){

        /*System.out.println(decode("", "01", "11", "02", "22"));

        System.out.println(bitToDecimal(new int[]{2,1,0}));
        System.out.println(Arrays.toString(decimalToBits(5)));*/
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

//    public static void main(String[] args) {
//        Date date = new Date();
//
//        System.out.println(addDay(date, 0));
//        System.out.println(date);
//   }

}