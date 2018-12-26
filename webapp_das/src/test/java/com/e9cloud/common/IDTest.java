package com.e9cloud.common;

import com.e9cloud.core.common.BaseLogger;
import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.DateTools;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/15.
 */
public class IDTest extends BaseLogger{

    @Test
    public void testRandomUUID(){
        System.out.println(Long.parseLong("3D", 16));
        System.out.println(Long.parseLong("3D", 16));
    }

    public static void main(String[] args) {
        System.out.println(ID.randomUUID());
        System.out.println(Tools.arrayJoinWith(Tools.getBeforeDays(-7), ","));

        BigDecimal bigDecimal = new BigDecimal(0);
        System.out.println(bigDecimal.add(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(1)));

        Date date = Tools.parseDate("2016-12", "yyyy-MM");
        System.out.println("datemax" + DateTools.getLastDayOfMonth(date));
        System.out.println("datemin" + DateUtils.addDays(date, -1));

        int i = 0,j = 0;
        System.out.println(i++);
        System.out.println(++j);
        System.out.println(i);
        System.out.println(j);

        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("data", DateTools.getLastDayOfMonth(date));
        System.out.println(JSonUtils.toJSon(objectMap));
    }

}
