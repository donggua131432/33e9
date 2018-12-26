package com.e9cloud.pcweb.income.biz;

import com.e9cloud.pcweb.BaseController;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dukai on 2016/8/24.
 */
@Service
public class IncomeService extends BaseController {

    /**
     * 根据类型获取时间
     * @return
     */
    public Date getDayByType(String type){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if("yesterday".equals(type)){
            //获取昨天时间
            cal.add(Calendar.DATE, -1);
            return cal.getTime();
        }else if("yesterdayStart".equals(type)){
            //获取昨天开始时间
            cal.add(Calendar.DATE, -1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return cal.getTime();
        }else if("yesterdayEnd".equals(type)){
            //获取昨天结束时间
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.add(Calendar.SECOND, -1);
            return cal.getTime();
        }else if("beforeMonth1".equals(type)){
            //获取11个月前时间
            cal.add(Calendar.MONTH, -1);
            return cal.getTime();
        }else if("beforeMonth12".equals(type)){
            //获取12个月前时间
            cal.add(Calendar.MONTH, -12);
            return cal.getTime();
        }else if("currentMonthStart".equals(type)){
            //获取本月开始信息
            cal.add(Calendar.MONTH, 0);
            cal.set(Calendar.DAY_OF_MONTH,1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            return cal.getTime();
        }else if("7day".equals(type)){
            //获取近7天时间
            cal.add(Calendar.DATE, -7);
            return cal.getTime();
        }else if("30day".equals(type)){
            //获取近30天时间
            cal.add(Calendar.DATE, -30);
            return cal.getTime();
        }else if("beforeHour3".equals(type)){
            //获取3小时以前的时间
            cal.add(Calendar.HOUR_OF_DAY, -3);
            return cal.getTime();
        }else{
            return cal.getTime();
        }
    }
}
