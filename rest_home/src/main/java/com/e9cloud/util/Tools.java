package com.e9cloud.util;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.mybatis.domain.AppointLink;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    public static String genRandom() {
        String randomStr = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            randomStr += String.valueOf(random.nextInt(10));
        }
        return randomStr;
    }
    public static String genMogonDbTableName(String name,Date datetime){
        String date = DateUtil.dateToStr(datetime, DateUtil.NO_SLASH);
        if(isNotNullStr(date)){
            return  name+"_"+date;
        }else{
            return  name+"_"+DateUtil.dateToStr(new Date(), DateUtil.NO_SLASH);
        }
    }
    public static void main(String[] args) {
        List<AppointLink> list = new ArrayList<>();
        AppointLink app1 = new AppointLink();
        app1.setXhTelno("1");
        app1.setDestTelno("0755");
        app1.setRn("9017104");
        AppointLink app2 = new AppointLink();
        app2.setXhTelno("");
        app2.setDestTelno("10646");
        app2.setRn("90020");
        AppointLink app3 = new AppointLink();
        app3.setXhTelno("");
        app3.setDestTelno("02023330200");
        app3.setRn("90016");
        AppointLink app4 = new AppointLink();
        app4.setXhTelno("075533648");
        app4.setDestTelno("02777");
        app4.setRn("9017104");
        AppointLink app5 = new AppointLink();
        app5.setXhTelno("90020");
        app5.setDestTelno("");
        app5.setRn("9017104");
        AppointLink app6 = new AppointLink();
        app6.setXhTelno("07556119");
        app6.setDestTelno("");
        app6.setRn("90020");
        AppointLink app7 = new AppointLink();
        app7.setXhTelno("075532981");
        app7.setDestTelno("075579");
        app7.setRn("90019090");
        AppointLink app8 = new AppointLink();
        app8.setXhTelno("075532981");
        app8.setDestTelno("02079");
        app8.setRn("90019091");
        AppointLink app9 = new AppointLink();
        app9.setXhTelno("07553638");
        app9.setDestTelno("");
        app9.setRn("90017001");
        AppointLink app10 = new AppointLink();
        app10.setXhTelno("*");
        app10.setDestTelno("075579");
        app10.setRn("90017002");
        AppointLink app11 = new AppointLink();
        app11.setXhTelno("020");
        app11.setDestTelno("075577123456");
        app11.setRn("90017003");
        list.add(app11);
        list.add(app9);
        list.add(app10);
        list.add(app1);
        list.add(app2);
        list.add(app3);
        list.add(app4);
        list.add(app5);
        list.add(app6);
        list.add(app7);
        list.add(app8);
        Collections.sort(list, new Comparator<AppointLink>() {

            @Override
            public int compare(AppointLink o1, AppointLink o2) {
                int len1 = o1.getXhTelno().length() * 100 + o1.getDestTelno().length();
                int len2 = o2.getXhTelno().length() * 100 + o2.getDestTelno().length();
                if ("*".equals(o1.getXhTelno())) {
                    len1 = 5000 + o1.getDestTelno().length();
                }
                if ("*".equals(o2.getXhTelno())) {
                    len2 = 5000 + o2.getDestTelno().length();
                }
                return len2 - len1;
            }
        });
        for (int i = 0; i < list.size(); i++) {
            AppointLink appointLink = list.get(i);
            System.out.println(appointLink.getXhTelno()+"--"+appointLink.getDestTelno());
        }
        System.out.println(appointLink(list,"075536380001","07557900010001"));

    }
    public static  String appointLink(List<AppointLink> appointLinks,String xh, String dest) {
        int size = appointLinks.size();
        for (int i = 0; i < size; i++) {
            AppointLink appointLink = appointLinks.get(i);
            if (xh.startsWith(appointLink.getXhTelno()) && dest.startsWith(appointLink.getDestTelno())) {
                return appointLink.getRn();
            }
            if("*".equals(appointLink.getXhTelno())&& dest.startsWith(appointLink.getDestTelno())){
                return appointLink.getRn();
            }
        }
        return null;
    }
    public  static void testClientUdp(){
        String msg = "11231 123 123";
        byte[] buf = msg.getBytes();
        try {
            InetAddress address = InetAddress.getByName("10.155.155.114");  //服务器地址
            int port = 5000;  //服务器的端口号
            //创建发送方的数据报信息
            DatagramPacket dataGramPacket = new DatagramPacket(buf, buf.length, address, port);

            DatagramSocket socket = new DatagramSocket();  //创建套接字
            socket.send(dataGramPacket);  //通过套接字发送数据

            //接收服务器反馈数据
            byte[] backbuf = new byte[1024];
            DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);
            socket.receive(backPacket);  //接收返回数据
            String backMsg = new String(backbuf, 0, backPacket.getLength());
            System.out.println("服务器返回的数据为:" + backMsg);

            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}