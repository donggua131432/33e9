package com.e9cloud.pcweb.revenue.biz;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.DateTools;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.service.RevenueReportService;
import com.e9cloud.pcweb.BaseController;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/29.
 */
@Service
public class RevenueReportBizService extends BaseController {

    @Autowired
    private RevenueReportService revenueReportService;

    public Map<String, Object> monthDetails(String ym, String feeid, String sid) {

        Map<String, Object> params = new HashMap<>();
        params.put("ym", ym);
        params.put("feeid", feeid);
        params.put("sid", sid);

        Date date = Tools.parseDate(ym, "yyyy-MM");
        params.put("datemax", DateTools.getLastDayOfMonth(date));
        params.put("datemin", DateUtils.addDays(date, -1));

        Map<String, Object> details = new HashMap<>();

        List<Map<String, Object>> rents = revenueReportService.getRents(params);
        Map<String, Object> month = revenueReportService.monthByFeeid(params);

        details.put("ym", ym); // 月份
        details.put("month", month);

        details.put("rest", dealRest(params)); // rest 专线语音
        details.put("mask", dealMask(params, rents)); // mask 专号通
        details.put("sip", dealSip(params)); // sip sip接口
        details.put("cc", dealCC(params, rents)); // cc 智能云调度
        details.put("sp", dealSp(params, rents)); // sp 云话机
        details.put("ecc", dealEcc(params, rents)); // ecc 云总机
        details.put("voiceverify",dealVoiceVerify(params));//语音验证码

        return details;
    }

    // 处理 rest
    private Map<String, Object> dealRest(Map<String, Object> params) {
        List<Map<String, Object>> rests = revenueReportService.restMonthDetails(params);

        if (rests.size() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum = 0, jflyscsum = 0;
            BigDecimal fee = new BigDecimal(0);
            BigDecimal recordingfee = new BigDecimal(0);
            BigDecimal tfee = new BigDecimal(0);

            for (int i=0; i<rests.size(); i++) {
                thscsum += toLong(rests.get(i).get("thscsum"));
                jfscsum += toLong(rests.get(i).get("jfscsum"));
                jflyscsum += toLong(rests.get(i).get("jflyscsum"));
                fee = fee.add(toBigDecimal(rests.get(i).get("fee")));
                recordingfee = recordingfee.add(toBigDecimal(rests.get(i).get("recordingfee")));

                rests.get(i).put("tfee", toBigDecimal(rests.get(i).get("fee")).add(toBigDecimal(rests.get(i).get("recordingfee"))));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum", jfscsum);
            total.put("jflyscsum", jflyscsum);
            total.put("fee", fee);
            total.put("recordingfee", recordingfee);
            total.put("tfee", tfee.add(fee).add(recordingfee));

            detail.put("rows", rests);
            detail.put("total", total);
            return detail;
        }

        return null;
    }

    // 处理 mask
    private Map<String, Object> dealMask(Map<String, Object> params, List<Map<String, Object>> rents) {

        List<Map<String, Object>> masks = revenueReportService.maskMonthDetails(params);
        BigDecimal rent = getRents(rents, "mask", "rent");

        if (masks.size() > 0 || rent.doubleValue() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum = 0, jflyscsum = 0;
            BigDecimal fee = new BigDecimal(0);
            BigDecimal recordingfee = new BigDecimal(0);
            BigDecimal tfee = new BigDecimal(0);

            for (int i=0; i<masks.size(); i++) {
                thscsum += toLong(masks.get(i).get("thscsum"));
                jfscsum += toLong(masks.get(i).get("jfscsum"));
                jflyscsum += toLong(masks.get(i).get("jflyscsum"));
                fee = fee.add(toBigDecimal(masks.get(i).get("fee")));
                recordingfee = recordingfee.add(toBigDecimal(masks.get(i).get("recordingfee")));

                masks.get(i).put("tfee", toBigDecimal(masks.get(i).get("fee")).add(toBigDecimal(masks.get(i).get("recordingfee"))));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum", jfscsum);
            total.put("jflyscsum", jflyscsum);
            total.put("fee", fee);
            total.put("recordingfee", recordingfee);
            total.put("tfee", tfee.add(fee).add(recordingfee).add(rent));

            detail.put("rent", rent);
            detail.put("rows", masks);
            detail.put("total", total);
            return detail;
        }

        return null;
    }

    // 处理 sip
    private Map<String, Object> dealSip(Map<String, Object> params) {
        List<Map<String, Object>> sips = revenueReportService.sipMonthDetails(params);

        if (sips.size() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum6 = 0, jfscsum60 = 0;
            BigDecimal fee = new BigDecimal(0);

            for (int i=0; i<sips.size(); i++) {
                thscsum += toLong(sips.get(i).get("thscsum"));
                jfscsum6 += toLong(sips.get(i).get("jfscsum6"));
                jfscsum60 += toLong(sips.get(i).get("jfscsum60"));
                fee = fee.add(toBigDecimal(sips.get(i).get("fee")));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum6", jfscsum6);
            total.put("jfscsum60", jfscsum60);
            total.put("fee", fee);

            detail.put("rows", sips);
            detail.put("total", total);

            return detail;
        }

        return null;
    }

    // 处理 CC
    private Map<String, Object> dealCC(Map<String, Object> params, List<Map<String, Object>> rents) {
        List<Map<String, Object>> ccs = revenueReportService.ccMonthDetails(params);
        BigDecimal rent = getRents(rents, "cc", "rent");

        if (ccs.size() > 0 || rent.doubleValue() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum = 0;
            BigDecimal fee = new BigDecimal(0);

            for (int i=0; i<ccs.size(); i++) {
                thscsum += toLong(ccs.get(i).get("thscsum"));
                jfscsum += toLong(ccs.get(i).get("jfscsum"));
                fee = fee.add(toBigDecimal(ccs.get(i).get("fee")));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum", jfscsum);
            total.put("fee", fee.add(rent));

            detail.put("rows", ccs);
            detail.put("rent", rent);
            detail.put("total", total);

            return detail;
        }

        return null;
    }

    // 处理 Sp
    private Map<String, Object> dealSp(Map<String, Object> params, List<Map<String, Object>> rents) {
        List<Map<String, Object>> sps = revenueReportService.spMonthDetails(params);

        BigDecimal rent = getRents(rents, "sp", "rent"); // 月租
        BigDecimal minconsume = getRents(rents, "sp", "minconsume"); // 低消

        if (sps.size() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum = 0, jflyscsum = 0;
            BigDecimal fee = new BigDecimal(0);
            BigDecimal recordingfee = new BigDecimal(0);
            BigDecimal tfee = new BigDecimal(0);

            for (int i=0; i<sps.size(); i++) {
                thscsum += toLong(sps.get(i).get("thscsum"));
                jfscsum += toLong(sps.get(i).get("jfscsum"));
                jflyscsum += toLong(sps.get(i).get("jflyscsum"));
                fee = fee.add(toBigDecimal(sps.get(i).get("fee")));
                recordingfee = recordingfee.add(toBigDecimal(sps.get(i).get("recordingfee")));

                sps.get(i).put("tfee", toBigDecimal(sps.get(i).get("fee")).add(toBigDecimal(sps.get(i).get("recordingfee"))));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum", jfscsum);
            total.put("jflyscsum", jflyscsum);
            total.put("fee", fee);
            total.put("recordingfee", recordingfee);
            total.put("tfee", tfee.add(fee).add(recordingfee).add(rent).add(minconsume));

            detail.put("rows", sps);
            detail.put("rent", rent);
            detail.put("minconsume", minconsume);
            detail.put("total", total);

            return detail;
        }

        return null;
    }

    // 处理 ecc
    private Map<String, Object> dealEcc(Map<String, Object> params, List<Map<String, Object>> rents) {
        List<Map<String, Object>> sps = revenueReportService.eccMonthDetails(params);

        BigDecimal zjrent = getRents(rents, "ecc", "rent_ivr"); // 总机月租
        BigDecimal rent = getRents(rents, "ecc", "rent_sp"); // 月租
        BigDecimal minconsume = getRents(rents, "ecc", "minconsume"); // 低消

        if (sps.size() > 0) {
            Map<String, Object> detail = new HashMap<>();
            Map<String, Object> total = new HashMap<>();

            long thscsum = 0, jfscsum = 0, jflyscsum = 0;
            BigDecimal fee = new BigDecimal(0);
            BigDecimal recordingfee = new BigDecimal(0);
            BigDecimal tfee = new BigDecimal(0);

            for (int i=0; i<sps.size(); i++) {
                thscsum += toLong(sps.get(i).get("thscsum"));
                jfscsum += toLong(sps.get(i).get("jfscsum"));
                jflyscsum += toLong(sps.get(i).get("jflyscsum"));
                fee = fee.add(toBigDecimal(sps.get(i).get("fee")));
                recordingfee = recordingfee.add(toBigDecimal(sps.get(i).get("recordingfee")));

                sps.get(i).put("tfee", toBigDecimal(sps.get(i).get("fee")).add(toBigDecimal(sps.get(i).get("recordingfee"))));
            }

            total.put("thscsum", thscsum);
            total.put("jfscsum", jfscsum);
            total.put("jflyscsum", jflyscsum);
            total.put("fee", fee);
            total.put("recordingfee", recordingfee);
            total.put("tfee", tfee.add(fee).add(zjrent).add(recordingfee).add(rent).add(minconsume));

            detail.put("rows", sps);
            detail.put("zjrent", zjrent);
            detail.put("rent", rent);
            detail.put("minconsume", minconsume);
            detail.put("total", total);

            return detail;
        }

        return null;
    }


    // 处理 voiceVerify
    private Map<String, Object> dealVoiceVerify(Map<String, Object> params) {
        List<Map<String, Object>> rests = revenueReportService.voiceVerifyMonthDetails(params);
        if (rests.size() > 0) {
            Map<String, Object> detail = new HashMap<>();

            detail.put("rows", rests);
            return detail;
        }

        return null;
    }


    private BigDecimal getRents(List<Map<String, Object>> rents, String yw, String feetype) {
        BigDecimal rent = new BigDecimal(0);

        if (rents != null && rents.size() > 0){
            for (Map<String, Object> rs : rents) {
                if (yw.equals(rs.get("yw")) && feetype.equals(rs.get("feetype"))){
                    rent = rent.add(toBigDecimal(rs.get("fee")));
                }
            }
        }

        return rent;
    }

    private long toLong(Object o) {
        if (o == null) return 0;
        return Long.valueOf(Tools.toStr(o));
    }

    private BigDecimal toBigDecimal(Object o) {
        if (o == null) return new BigDecimal(0);
        return new BigDecimal(Tools.toStr(o));
    }

}
