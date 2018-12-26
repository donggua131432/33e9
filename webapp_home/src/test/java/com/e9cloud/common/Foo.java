package com.e9cloud.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/1/23.
 */
public class Foo {

    private String m; // 0

    private String n; // 1

    private String v; // 2

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public void toF(Map map){
        map.forEach((k,v)->{
            System.out.println(k);
        });
    }

    public static void main(String[] args) throws Exception {
        Map<String, Integer> maps = new HashMap<>();
        maps.put("123", 1);
        maps.put("113", 2);
        maps.forEach((k,v)->{
            System.out.println(v);
        });
        System.out.println(1200000000);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(3600*24*30*3*1000 > Integer.MAX_VALUE);

        Date date = new SimpleDateFormat("yyyy年M月").parse("2016年12月");

        System.out.println(new SimpleDateFormat("yyyy年M月").format(date));

        int ls = 1800 * 1000;
        Random r = new Random();
        for (int i=0; i< 100; i++) {
            int s_length = r.nextInt(ls);

            int jfsc = (s_length/1000 + 59)/60;
            int thsc = s_length/1000;
            System.out.println(new DecimalFormat("####.###").format(jfsc * 0.20).toString() + ":" + thsc);

        }
    }
}
