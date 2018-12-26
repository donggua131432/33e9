package com.e9cloud.common;

import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.RegexUtils;
import com.frameworkset.util.RegexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/23.
 */
public class Boo extends Foo {

    @Override
    public void toF(Map hashMap){

    }

    public static void main(String[] args) {
        boolean b = RegexUtils.checkMobile("17057011988");
        System.out.println(b);

        String ss = "112223/sseeel.mp3";
        ss = ss.substring(0,ss.lastIndexOf(".")) + ".wav";
        System.out.println(ss);

        try {
            InputStream is = new FileInputStream(new File("D:\\web\\tempvoice\\nas\\2d3ed298127f41c5b6e990b032f496eb\\voice4f4f5c5115994dc69f1b7eab82d53e87.wav"));
            boolean ib = FileUtil.isMP3File(is);
            System.out.println(ib);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
