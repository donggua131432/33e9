package com.e9cloud.pcweb.sipPhone.biz;

import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.FixPhone;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.SipPhoneApply;
import com.e9cloud.mybatis.domain.SpApplyNum;
import com.e9cloud.mybatis.service.SPApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/3.
 */
@Service
public class SPAllotService {

    @Autowired
    private SPApplyService spApplyService;

    public JSonMessage allotPage(String id){
        SipPhoneApply spApply = spApplyService.getApplyWithCityById(id);

        Map<String, Object> params = new HashMap<>();
        params.put("cityid", spApply.getCityid());
        params.put("amount", spApply.getAmount());
        params.put("fixAmount", spApply.fixAmount());

        List<SipPhone> sipPhones = spApplyService.getSipPhonesByAllot(params);

        List<FixPhone> fixPhones = spApplyService.getFixPhonesByAllot(params);

        if (sipPhones.size() < spApply.getAmount()) { // sip 号码不足
            return new JSonMessage(R.ERROR, "sip 号码不足");
        }

        if (fixPhones.size() < spApply.fixAmount()) { // 固话号码不足
            return new JSonMessage(R.ERROR, "固话号码不足");
        }

        List<Map> maps = new ArrayList<>();

        StringBuilder sip_fix = new StringBuilder();

        for (int i=0,j=0; i<spApply.getAmount(); i++) {
            Map<String, Object> map = new HashMap<>();

            map.put("rowNO", i+1);

            map.put("sipphone", sipPhones.get(i).getSipPhone());
            map.put("pwd", sipPhones.get(i).getPwd());
            map.put("sipRealm", sipPhones.get(i).getSipRealm());
            map.put("ipPort", sipPhones.get(i).getIpPort());


            map.put("fixphone", fixPhones.get(j).getNumber());
            map.put("showNum", fixPhones.get(j).getNumber());


            map.put("auditStatus", "01");
            map.put("pname", "pname");
            map.put("cname", "cname");

            sip_fix.append(sipPhones.get(i).getId()).append("_").append(fixPhones.get(j).getId()).append(",");

            maps.add(map);
            if (i % spApply.getRate() == (spApply.getRate()-1)) {
                j++;
            }
        }

        return new JSonMessage(R.OK, sip_fix.toString(), maps);
    }

    /**
     * 保存审核信息，和号码列表
     * @param apApply 申请信息
     * @param sipFix sipphoenId 和 fixPhoneid
     * @return
     */
    public JSonMessage saveAllotNumsAndAudit(SipPhoneApply apApply, String sipFix) {

        if ("01".equals(apApply.getAuditStatus())) {
            String[] sipFixs = Tools.stringToArray(sipFix);
            List<SpApplyNum> applyNums = new ArrayList<>();

            for (String sf : sipFixs) {
                if (sf.contains("_")) {
                    String sipphoneId = sf.split("_")[0];
                    String fixphoneId = sf.split("_")[1];

                    boolean isAllot = spApplyService.isAllot(sipphoneId,fixphoneId);
                    if (isAllot) {
                        return new JSonMessage(R.ERROR, "一些SIP号码或固话号码已被分配");
                    }

                    SpApplyNum applyNum = new SpApplyNum();
                    applyNum.setAppid(apApply.getAppid());
                    applyNum.setApplyId(apApply.getId());
                    applyNum.setCityid(apApply.getCityid());
                    applyNum.setSipphoneId(sipphoneId);
                    applyNum.setFixphoneId(fixphoneId);

                    applyNums.add(applyNum);
                }
            }

            for (SpApplyNum applyNum : applyNums) {
                spApplyService.saveShowNum(applyNum);
            }
        }

        spApplyService.updateSPApplyAudit(apApply);

        return new JSonMessage(R.OK, "");
    }

    public JSonMessage checkSipphone(String sipphone, String cityid) {
        Map<String, Object> params = new HashMap<>();
        params.put("sipphone", sipphone);
        params.put("cityid", cityid);
        List<Map<String, Object>> msg = spApplyService.checkSipphoneByMap(params);
        if (msg == null || msg.size() == 0) {
            return new JSonMessage(R.ERROR, "SIP号码不存在");
        }
        String sipphoneId = "";
        for (Map<String, Object> map : msg) {
            if ("01".equals(map.get("status"))) {
                return new JSonMessage(R.ERROR, "SIP号码已分配");
            }
            sipphoneId = String.valueOf(map.get("id"));
        }
        return new JSonMessage(R.OK, "", sipphoneId);
    }

    public JSonMessage checkFixphone(String fixphone, String cityid, String appid) {
        Map<String, Object> params = new HashMap<>();
        params.put("fixphone", fixphone);
        params.put("cityid", cityid);
        params.put("appid", appid);
        List<Map<String, Object>> msg = spApplyService.checkFixphoneByMap(params);
        if (msg == null || msg.size() == 0) {
            return new JSonMessage(R.ERROR, "固话号码不存在");
        }
        String fixphoneId = "";
        for (Map<String, Object> map : msg) {
            if ("01".equals(map.get("status"))) {
                return new JSonMessage(R.ERROR, "固话号码已分配");
            }
            fixphoneId = String.valueOf(map.get("id"));
        }
        return new JSonMessage(R.OK, "", fixphoneId);
    }
}
