package com.e9cloud.core.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String toStr(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    public static String padLeft(Object o, int len, String str) {
        String s = toStr(o);
        int length = s.length();
        if (length < len){
            for (int i=0; i<(len-length); i++) {
                s = str + s;
            }
        }
        return s;
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
        return null != str && !"".equals(str) && !"null".equals(str);
    }

    /**
     * 字符串非空判断
     *
     * @param str 字符串
     * @return true/false
     */
    public static boolean isNullStr(String str) {
        return str == null || "".equals(str) || "null".equals(str);
    }

    /**
     * Object非空判断
     *
     * @param str Object
     * @return true/false
     */
    public static boolean isNullObject(Object str) {
        return str == null || "".equals(str) || "null".equals(str);
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
     *  设置3位小数
     * @param o
     * @return
     */

    public static BigDecimal toSetScale(Object o){
        BigDecimal bd = null;
        if (o instanceof Double) {
            bd = new BigDecimal((Double)o);
        } else if (o instanceof Float) {
            bd = new BigDecimal((Float)o);
        } else {
            bd = new BigDecimal(String.valueOf(o));
        }

        return bd.setScale(3,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 马赛克
     * @param str
     * @return
     */
    public static String mosaic(String str){
        if (isNotNullStr(str)) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                sb.append("*");
            }
            return sb.toString();
        }
        return "";
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

    /**
     * 字符串反向
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        if(isNullStr(str)){
            return str;
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 得到十二个月
     * @return
     */
    public static List<int[]> getMonthsForOneYear(){

        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));

        List<int[]> l = new ArrayList<>(12);
        for (int i = 1; i < 13; i++) {
            int m = month > i ? (month - i) : (month - i + 12);
            int y = month > i ? year : (year - 1);
            l.add(new int[]{y,m});
        }

        return Lists.reverse(l);
    }

    /**
     * 邮箱转换为邮箱地址
     *
     * @param email
     * @return
     */
    public static String toEmailUrl(String email) {
        if(isNotNullStr(email)){
            return "http://mail." + email.split("@")[1];
        }

        return "";
    }

    public static String Base64Encode(String param, String code) throws Exception {
        String base64 = null;
        try {
            base64 = Base64.encodeBase64String(param.getBytes(code));
        } catch (UnsupportedEncodingException e) {
            throw e;
        }
        return base64;
    }

    /**
     * 解base64编码
     *
     * @param src
     * @return
     */
    public static String unBase64(String src) {
        return new String(new Base64().decode(src.getBytes()));
    }

    /**
     * 得到日期路径
     */
    public static String composeDataPath(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    /**
     * 得到日期路径
     */
    public static String composeDataPath(String prePath){
        return prePath + "/" + composeDataPath();
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
                temp = temp.replaceAll("${" + entry.getKey() + "}", entry.getValue() + "");
            }
        }

        return temp;
    }

    public static void main(String[] args)
    {
        PathMatcher matcher = new AntPathMatcher();
        // 完全路径url方式路径匹配
        String requestPath="/znydd/bill/consume";//请求路径
        String patternPath="/znydd/bill/**";//路径匹配模式


//        String patternPath="/znydd/";//请求路径
//        String requestPath="/znydd/bill/**";//路径匹配模式

        // 不完整路径uri方式路径匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/login.do";//路径匹配模式
        // 模糊路径方式匹配
        // String requestPath="/app/pub/login.do";//请求路径
        // String patternPath="/**/*.do";//路径匹配模式
        // 包含模糊单字符路径匹配
        //String requestPath = "/app/pub/login.do";// 请求路径
        //String patternPath = "/**/lo?in.do";// 路径匹配模式
        boolean result = matcher.match(patternPath, requestPath);
        System.out.println(result);
        List<int[]> l = getMonthsForOneYear();
        l.forEach((s)->{
            System.out.println(s[0] + "-" + s[1]);
        });
    }

    public static String decode(Object o, String... strs) {

        if (strs == null){
            return toStr(o);
        }

        if (strs.length == 1) {
            return strs[0];
        }

        for (int i=0; i<strs.length; i+=2) {
            if (toStr(o).equals(strs[i])) {
                return strs[i+1];
            }
        }

        return strs.length%2 == 0 ? toStr(o) : strs[strs.length-1];
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

}