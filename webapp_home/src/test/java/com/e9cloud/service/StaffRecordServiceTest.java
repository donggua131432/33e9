package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.Area;
import com.e9cloud.mybatis.domain.CallCenter;
import com.e9cloud.mybatis.domain.StafRecord;
import com.e9cloud.mybatis.service.AreaService;
import com.e9cloud.mybatis.service.CallCenterService;
import com.e9cloud.mybatis.service.ZnyddService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/3/11.
 */
public class StaffRecordServiceTest extends BaseTest{

    private int callType;//呼入1呼出2
    private int operator;//电信1联通2移动3
    private String callCenter;//呼叫中心
    private String dispatchArea;//调度区域
    private String area;//区域
    private int jb;//际标
    private int count;//呼叫数
    private int thsc;//通话时长（秒）
    private int jfsc;//计费时长(分)
    private int connSucc;//接通1未接通0
    private int connFalse;//接通0未接通1
    private Double fee;//花费

    @Autowired
    private ZnyddService znyddService;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private AreaService areaService;

    @Test
    public void testInsertStafRecord(){

        List<CallCenter> callCenters = callCenterService.getAllCallCenter(null);
        int size = callCenters.size();

        List<Area> areas = areaService.getAllArea(null);
        int lent = areas.size();

        String[] operator = {"1", "2", "3"};
        String[] callType = {"1", "2"};
        String[] conn = {"1", "0", "1", "1"};

        int ly = 2100000000;
        int ls = 1800 * 1000;

        long mill = System.currentTimeMillis();

        Random r = new Random();

        StafRecord stafRecord = new StafRecord();

        for (int i=0; i<487; i++) {
            stafRecord.setArea(areas.get(r.nextInt(lent)).getId() + ""); // 区域
            stafRecord.setCallCenter(callCenters.get(r.nextInt(size)).getId()); //呼 叫 中 心
            stafRecord.setCallType(callType[r.nextInt(2)]); // 呼 叫 类 型
            String c = conn[r.nextInt(4)];
            stafRecord.setConn(c); // 接 通 状 态
            // stafRecord.setDispatchArea(areas.get(r.nextInt(lent)).getId() + ""); // 调度区域
            logger.info("++++++++++++++++{}", ly);
            long s_time = mill - r.nextInt(ly);
            int s_length = r.nextInt(ls);

            stafRecord.setEndTime(new Date(s_time)); // 结 束 时 间
            stafRecord.setStartTime(new Date(s_time - s_length)); // 开 始 时 间
            stafRecord.setOperator(operator[r.nextInt(3)]); // 所 属 运 营 商

            int thsc = s_length/1000;
            int jfsc = (thsc + 59)/60;

            stafRecord.setJfsc(jfsc); // 计费时长
            stafRecord.setThsc(thsc); // 通话时长

            if ("0".equals(c)) {
                stafRecord.setFee("0");
            } else {
                String fee = new DecimalFormat("####.###").format(jfsc * 0.20).toString();
                stafRecord.setFee(fee); // 费 用
            }

            znyddService.insertStafRecordSelective(stafRecord);
        }


    }

}
