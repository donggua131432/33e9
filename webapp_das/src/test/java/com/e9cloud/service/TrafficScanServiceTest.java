package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.TrafficScanService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2016/12/3.
 */
public class TrafficScanServiceTest extends BaseTest {

    @Autowired
    private TrafficScanService trafficScanService;

    Map<String, Object> params_d = new HashMap<>();
    Map<String, Object> params_h = new HashMap<>();
    Map<String, Object> params_m = new HashMap<>();

    @Before
    public void be(){
        // 近30日
        params_d.put("startDay", Tools.getDate(-30));
        params_d.put("endDay", Tools.getDate(0));

        // 近7日
        params_h.put("startDay", Tools.getDate(-7));
        params_h.put("endDay", Tools.getDate(0));

        // 昨天
        params_m.put("startDay", Tools.getDate(-1));
        params_m.put("endDay", Tools.getDate(0));

    }

    @Test
    public void testAllScan() {

        // cc
        List<Map<String, Object>> cc_d = trafficScanService.countCcScanByDay(params_d);
        List<Map<String, Object>> cc_h = trafficScanService.countCcScanByHour(params_h);
        List<Map<String, Object>> cc_m = trafficScanService.countCcScanByMin(params_m);

        // mask
        List<Map<String, Object>> mask_d = trafficScanService.countMaskScanByDay(params_d);
        List<Map<String, Object>> mask_h = trafficScanService.countMaskScanByHour(params_h);
        List<Map<String, Object>> mask_m = trafficScanService.countMaskScanByMin(params_m);

        // sp
        List<Map<String, Object>> sp_d = trafficScanService.countSpScanByDay(params_d);
        List<Map<String, Object>> sp_h = trafficScanService.countSpScanByHour(params_h);
        List<Map<String, Object>> sp_m = trafficScanService.countSpScanByMin(params_m);

        // sip
        List<Map<String, Object>> sip_d = trafficScanService.countSipScanByDay(params_d);
        List<Map<String, Object>> sip_h = trafficScanService.countSipScanByHour(params_h);
        List<Map<String, Object>> sip_m = trafficScanService.countSipScanByMin(params_m);

        // voice
        List<Map<String, Object>> voice_d = trafficScanService.countVoiceScanByDay(params_d);
        List<Map<String, Object>> voice_h = trafficScanService.countVoiceScanByHour(params_h);
        List<Map<String, Object>> voice_m = trafficScanService.countVoiceScanByMin(params_m);

    }


}
