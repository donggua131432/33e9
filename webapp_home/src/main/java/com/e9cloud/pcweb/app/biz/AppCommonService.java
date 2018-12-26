package com.e9cloud.pcweb.app.biz;

import com.e9cloud.core.util.CalculateUtil;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.SipDayRecord;
import com.e9cloud.mybatis.service.SpendFeeService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Dukai on 2016/7/22.
 */
@Service
public class AppCommonService extends BaseController{

    @Autowired
    @Qualifier("mongoDBDao")
    private MongoDBDao mongoDBDao;

    @Autowired
    private SpendFeeService spendFeeService;

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
        }else if("beforeMonth12".equals(type)){
            //获取12个月前时间
            cal.add(Calendar.MONTH, -11);
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
        }else if("90day".equals(type)){
            //获取近90天时间(近三个月)
            cal.add(Calendar.DATE, -90);
            return cal.getTime();
        }
        else if("beforeHour3".equals(type)){
            //获取3小时以前的时间
            cal.add(Calendar.HOUR_OF_DAY, -3);
            return cal.getTime();
        }else{
            return cal.getTime();
        }
    }

    /**
     * 根据collection名及时间条件  获取费用信息总和（取自mongoDB的数据）
     * @param account
     * @param dateType  yesterday表示昨日消费信息  month表示本月的消费信息
     * @return
     */
    public double getCallRecordSumFee(Account account, String collection, String dateType){
        Query queryFee = new Query();
        queryFee.addCriteria(Criteria.where("feeid").is(account.getFeeid()));
        queryFee.addCriteria(Criteria.where("fee").gte(0));
        if("yesterday".equals(dateType)){
            //昨日消费
            Date yesterdayStart = getDayByType("yesterdayStart");
            Date yesterdayEnd = getDayByType("yesterdayEnd");
            queryFee.addCriteria(Criteria.where("closureTime").gte(yesterdayStart).lte(yesterdayEnd));
        }else if("month".equals(dateType)){
            //本月消费
            Date currentMonth = getDayByType("currentMonthStart");
            queryFee.addCriteria(Criteria.where("closureTime").gte(currentMonth));
        }

        /*String sumFee;
        if("costLog".equals(collection)){
            sumFee = mongoDBDao.sum("fee", queryFee, collection,"feeid");
        }else{
            sumFee = mongoDBDao.sum("fee", queryFee, collection+"_"+account.getFeeid(),"feeid");
        }*/
        String sumFee = mongoDBDao.sum("fee", queryFee, collection,"feeid");
        String sumRecordingFee =  mongoDBDao.sum("recordingFee", queryFee, collection,"feeid");

        double totalFee = CalculateUtil.sum( Double.parseDouble(sumFee), Double.parseDouble(sumRecordingFee));

        return totalFee;
    }


    /**
     * 获取Sip本月消费总和
     * @param map
     * @return
     */
    public String getSipMonthSpendSumFee(Map map){
        map.put("statday", getDayByType(""));
        Map<String,Object> resultMap = spendFeeService.getSipCurrentMonthSumFee(map);
        if(resultMap != null){
            return String.format("%.2f", Double.parseDouble(resultMap.get("fee").toString()));
        }
        return String.format("%.2f", Double.parseDouble("0"));
    }

    /**
     * 获取Sip昨日消费总和
     * @param map
     * @return
     */
    public String getSipYesterDaySpendSumFee(Map map){
        map.put("statday", getDayByType("yesterday"));
        Map<String,Object> resultMap = spendFeeService.getSipYesterdaySumFee(map);
        if(resultMap != null){
            return String.format("%.2f", Double.parseDouble(resultMap.get("fee").toString()));
        }
        return String.format("%.2f", Double.parseDouble("0"));
    }


    /**
     * 获取本月消费总和
     * @param map
     * @return
     */
    public String getMonthSpendSumFee(Map map){
        map.put("statday", getDayByType(""));
        Map<String,Object> resultMap = spendFeeService.getCurrentMonthSumFee(map);
        if(resultMap != null){
            //return new DecimalFormat("#0.00").format(Double.parseDouble(resultMap.get("fee").toString()));
            return String.format("%.2f", Double.parseDouble(resultMap.get("fee").toString()));
        }
        return String.format("%.2f", Double.parseDouble("0"));
    }

    /**
     * 获取昨日消费总和
     * @param map
     * @return
     */
    public String getYesterDaySpendSumFee(Map map){
        map.put("statday", getDayByType("yesterday"));
        Map<String,Object> resultMap = spendFeeService.getYesterdaySumFee(map);
        if(resultMap != null){
            //return new DecimalFormat("#0.00").format(resultMap.get("fee").toString());
            return String.format("%.2f", Double.parseDouble(resultMap.get("fee").toString()));
        }
        return String.format("%.2f", Double.parseDouble("0"));
    }

}
