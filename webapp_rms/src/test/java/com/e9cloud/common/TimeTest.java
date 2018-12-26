package com.e9cloud.common;

import com.e9cloud.core.util.HttpUtil;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.R;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class TimeTest {

    public static void main(String[] args) {

        long st = System.currentTimeMillis();
        for (int i=0;i<1000;i++) {
            long startTime = System.currentTimeMillis();
            HttpUtil.get("https://www.baidu.com");
            long endTime = System.currentTimeMillis();

            System.out.println("time :" + (endTime - startTime));
        }
        long et = System.currentTimeMillis();
        System.out.println("totalTime : " + (et - st));
    }

    @Test
    public void testP() {
        for (int h=0;h<24;h++) {
            for (int m=0;m<12;m++) {
                System.out.print("\""+(h<10?"0"+h:h)+":"+((m*5)<10?"0"+(m*5):(m*5))+"\",");
            }
            System.out.println();
        }


    }

    @Test
    public void testT() {
        /*List<JSonMessage> jSonMessages = new ArrayList<>();
        jSonMessages.add(new JSonMessage(R.OK, "123"));
        jSonMessages.add(new JSonMessage(R.OK, "123"));
        jSonMessages.add(new JSonMessage(R.OK, "123"));
        String jsonStr = JSonUtils.toJSon(jSonMessages);
        System.out.println(jsonStr);*/
        String js = "[{\"code\":\"ok\",\"msg\":\"123\",\"data\":null},{\"code\":\"ok\",\"msg\":\"123\",\"data\":null},{\"code\":\"ok\",\"msg\":\"123\",\"data\":null}]";

        List<JSonMessage> j = JSonUtils.readValue(js, List.class, JSonMessage.class);
        System.out.println(j);

    }

}
