package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.CallRateService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * Created by DuKai on 2016/1/26.
 */
public class CallRateServiceTest extends BaseTest{
    @Autowired
    private CallRateService callRateService;

    /**
     * 32 位UUID,移除了"-"
     *
     * @return UUID
     */
    public static String randomUUID() {
        String uuid = java.util.UUID.randomUUID().toString();
        return StringUtils.remove(uuid, '-');
    }

    /**
     * 36 位UUID,移除了"-",并添加四位表头
     *
     * @return UUID
     */
    public static String randomUUIDWithHead(String idType) {
        return StringUtils.join(idType, randomUUID());
    }

    @Test
    public void testFindUserByLoginName() {
       /* System.out.println("随机生成ID1："+randomUUID());*/

    /*    Map map = new HashMap<String,String>();
        map.put("status","0");
        map.put("status","1");

        FeeRate feeRate = feeRateService.findFeeRateByFeeId("0");
        System.out.println("费率信息："+feeRate.getFeeid()+"---------"+feeRate.getCallinDiscount()+"-----------"+feeRate.getCalloutDiscount()+"--------"+map.get("status"));*/

        UserAdmin userAdmin = new UserAdmin();
        /*userAdmin.setUid("13030c558d864a278a1bac88cd7a582c");
        userAdmin.setSid("006b0c9f5fe9481e84b5a246ca97e816");
        userAdmin.setFeeid("851584df9b1b4d03b038ad50586f4a36");*/

        List<CallRate> callRateList = callRateService.selectCallRateList(userAdmin);

        for(int i = 0;i < callRateList.size(); i ++){
            System.out.println("==========================sid:"+callRateList.get(i).getUserAdmin().getSid()+"==========================uid:"+callRateList.get(i).getUserAdmin().getUid()+
                    "==========================feeid:"+callRateList.get(i).getUserAdmin().getFeeid()+"==========================name:"+callRateList.get(i).getAuthenCompany().getName()+
                    "==========================address:"+callRateList.get(i).getAuthenCompany().getAddress());
        }

        System.out.println("==================================================联合查询信息为："+callRateList.size());



    }
}
