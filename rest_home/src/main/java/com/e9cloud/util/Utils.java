package com.e9cloud.util;

import com.e9cloud.rest.obt.CallBack;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {

    /**
     * 判断对象是否为空<br />
     * 不为空返回true，为空返回false
     *
     * @param o
     * @return
     */
    public static boolean notEmpty(Object o) {
        boolean notEmpty = false;
        if (o instanceof String) {
            String s = (String) o;
            if (s != null && !"".equals(s) && !"undefined".equals(s)
                    && !"null".equals(s)) {
                notEmpty = true;
            }
        } else if (o instanceof Collection) {
            Collection c = (Collection) o;
            if (c != null && c.size() > 0) {
                notEmpty = true;
            }
        } else if (o instanceof Object[]) {
            Object[] arr = (Object[]) o;
            if (arr != null && arr.length > 0) {
                notEmpty = true;
            }
        } else if (o != null) {
            return true;
        }
        return notEmpty;
    }

    /**
     * 如果源字符串为NULL，则返回目标整数，否则返回由src转换后的整数
     *
     * @param src 源字符串
     * @param des 目标整数
     * @return
     */
    public static int convertToInt(String src, int des) {
        int tmp = des;
        if (src == null || src.equals("")) {
            return des;
        } else {
            try {
                tmp = Integer.parseInt(src);
            } catch (Exception e) {
            }
            return tmp;
        }
    }

    /**
     * 字符在字符串中出现的次数
     *
     * @param string
     * @param a
     * @return
     */
    public static int occurTimes(String string, String a) {
        int pos = -2;
        int n = 0;

        while (pos != -1) {
            if (pos == -2) {
                pos = -1;
            }
            pos = string.indexOf(a, pos + 1);
            if (pos != -1) {
                n++;
            }
        }
        return n;
    }

    /**
     * 判断字符串中是否仅包含字母数字和汉字
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     */
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含字母数字
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     */
    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含数字
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     */
    public static boolean isDigit(String str) {
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }

    /**
     * 判断字符串中是否仅包含数字（支持正负符号）
     * 各种字符的unicode编码的范围：
     * 汉字：[0x4e00,0x9fa5]（或十进制[19968,40869]）
     * 数字：[0x30,0x39]（或十进制[48, 57]）
     * 小写字母：[0x61,0x7a]（或十进制[97, 122]）
     * 大写字母：[0x41,0x5a]（或十进制[65, 90]）
     */
    public static boolean isDirectionDigit(String str) {
        String regex = "^[\\+\\-]?[0-9]+$";
        return str.matches(regex);
    }

    /**
     * 对数字字符串进行转整形
     */
    public static Integer strToInteger(String str) {
        Integer num = 0;
        if (str != null && !"".equals(str) && !"undefined".equals(str)
                && !"null".equals(str) && isDirectionDigit(str)) {
            num = Integer.parseInt(str);
        }
        return num;
    }

    /**
     * 主被叫校验-满足其一就返回true，反之返回false
     * 手机：^[1][358][0-9]{9}$  以1开头，第2位数字为3或5或8，后面接9位数字
     * 固话：固话-带区号11/12位  以0开头
     * 400号码：400开头
     * 955号码：9XXXXX及扩展号-最大11位
     * 物联网卡：10646,10648,10649开头的13位号码
     */
    public static boolean phonenoValid(String str) {
        String regex_phone = "^[1][0-9]{10}$";
        String regex_telephone = "^[0][0-9]{10,11}$";
        String regex_400 = "^[4][0][0][0-9]+$";
        String regex_9 = "^[9][0-9]{4,10}$";
        String regex_card = "^[1][0][6][4][689][0-9]{8}$";//物联网卡
        return str.matches(regex_phone) || str.matches(regex_telephone) || str.matches(regex_400)
                || str.matches(regex_9) || str.matches(regex_card);
    }

    /**
     * 被叫校验-满足其一就返回true，反之返回false
     * 手机：^[1][358][0-9]{9}$  以1开头，第2位数字为3或5或8，后面接9位数字
     * 固话：固话-带区号11/12位  以0开头
     * 400号码：400开头
     * 955号码：9XXXXX及扩展号-最大11位
     */
    public static boolean phonenoValidB(String str) {
        String regex_phone = "^[1][0-9]{10}$";
        String regex_telephone = "^[0][0-9]{10,11}$";
        String regex_400 = "^[4][0][0][0-9]+$";
        String regex_9 = "^[9][0-9]{4,10}$";
        return str.matches(regex_phone) || str.matches(regex_telephone) || str.matches(regex_400) || str.matches(regex_9);
    }


    /**
     * 主被叫校验-满足其一就返回true，反之返回false
     * 手机：^[1][358][0-9]{9}$  以1开头，第2位数字为3或5或8，后面接9位数字
     * 固话：固话-带区号11/12位  以0开头
     */
    public static boolean phoneAndTelValid(String str) {
        String regex_phone = "^[1][0-9]{10}$";
        String regex_telephone = "^[0][0-9]{10,11}$";
        return str.matches(regex_phone) || str.matches(regex_telephone);
    }

    /**
     * 手机号校验-满足其一就返回true，反之返回false
     * 手机：^[1][358][0-9]{9}$  以1开头，第2位数字为3或5或8，后面接9位数字
     */
    public static boolean phoneValid(String str) {
        String regex_phone = "^[1][0-9]{10}$";
        return str.matches(regex_phone);
    }

    /**
     * 物联网卡校验-满足其一就返回true，反之返回false
     * 手机：^[1][0][6][4][689][0-9]{8}$  物联网卡-13位数字，以10646、10648、10649开头
     */
    public static boolean cardValid(String str) {
        String regex_card = "^[1][0][6][4][689][0-9]{8}$";
        return str.matches(regex_card);
    }

    /**
     * 主被叫校验-满足其一就返回true，反之返回false
     * sipPhone: 区号+77+6位数号码
     */
    public static boolean sipPhoneValid(String str) {
        String regex_sip_phone1 = "^[0][1-2][0-9][7][79][0-9]{6,10}$";
        String regex_sip_phone2 = "^[0][3-9][0-9]{2}[7][79][0-9]{6,10}$";
        return str.matches(regex_sip_phone1) || str.matches(regex_sip_phone2);
    }

    /**
     * 区域校验-满足返回true，反之返回false
     * 以0开头，三或者四位、全数字
     */
    public static boolean areaCodeValid(String str) {
        String regex_areacode = "^[0][0-9]{2,3}$";//以0开头，三或者四位
        return str.matches(regex_areacode) && isDigit(str);
    }

    /**
     * 校验时间格式是否正确 并且时间大于系统时间
     *
     * @param str
     * @return
     */
    public static boolean dateTimeValid(String str, String format) {
        String regex_dateTime = "";
        if ("yyyyMMddHHmmss".equals(format)) {
            regex_dateTime = "^(\\d{4})(0\\d{1}|1[0-2])(0\\d{1}|[12]\\d{1}|3[01])(0\\d{1}|1\\d{1}|2[0-3])[0-5]\\d{1}([0-5]\\d{1})$";
        } else if ("yyyy-MM-dd HH:mm:ss".equals(format)) {
            regex_dateTime = "(\\d{4})-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01]) (0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})";
        } else if ("yyyy/MM/dd HH:mm:ss".equals(format)) {
            regex_dateTime = "(\\d{4})/(0\\d{1}|1[0-2])/(0\\d{1}|[12]\\d{1}|3[01]) (0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})";
        }

        return str.matches(regex_dateTime);
    }

    /**
     * 处理字符串，返回len长度，源字符串长就截取，短就前面补0
     * 源字符串为null返回len个0
     *
     * @param src
     * @param len
     * @return
     */
    public static String cutString(String src, int len) {
        if (src == null) {
            String res = "";
            while (res.length() < len) {
                res = "0" + res;
            }
            return res;
        }
        if (src.length() > len) {
            return src.substring(0, len);
        }
        if (src.length() < len) {
            while (src.length() < len) {
                src = "0" + src;
            }
        }
        return src;
    }


    /**
     * 语音验证码模板ID校验
     * @param str
     * @return
     */
    public static boolean voiceRecIdValid(String str){
        String regex_areacode = "^[0-9]{6}$"; //6位数字
        return str.matches(regex_areacode);
    }

    /**
     * 语音验证码校验
     * @param str
     * @return
     */
    public static boolean verifyCodeValid(String str){
        String regex_areacode = "^[0-9]{4,8}$"; //6位数字
        return str.matches(regex_areacode);
    }


    /**
     * 手机 固话 95 号码校验
     * @param str
     * @return
     */
    public static boolean phoneTelNfValid(String str) {
        String regex_phone = "^[1][0-9]{10}$";
        String regex_telephone = "^[0][0-9]{10,11}$";
        String regex_9 = "^[9][0-9]{4,10}$";
        return str.matches(regex_phone) || str.matches(regex_telephone) || str.matches(regex_9);
    }

    public static void main(String[] args) {
        //System.out.println(cutString(null, 2));
        //System.out.println(sipPhoneValid("0755783996699"));
    }
}
