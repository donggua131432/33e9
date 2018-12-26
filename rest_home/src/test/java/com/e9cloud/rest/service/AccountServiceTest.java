package com.e9cloud.rest.service;

import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.domain.CardPhone;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.service.VnService;
import com.e9cloud.mybatis.service.VoiceNotifyService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.base.BaseTest;
import com.e9cloud.mybatis.domain.Account;
import com.e9cloud.mybatis.domain.UserExternInfo;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.obt.VnWhite;
import com.e9cloud.rest.obt.VoiceNotify;
import com.e9cloud.rest.obt.VoiceReq;
import com.e9cloud.rest.vn.VirtualNumService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * test for AccountService
 */
public class AccountServiceTest extends BaseTest {


    private static String VN_WHITE = "VN:WHITE_";//白名单
    private static String VN_VNM = "VN:VNM:";//虚拟号映射（客户虚拟号对应业务内容）

    @Autowired
    private CardPhoneCTLService cardPhoneCTLService;


    @Autowired
    private AccountService accountService;


    @Autowired
    private VirtualNumService virtualNumService;

    @Autowired
    private VnService vnService;

    @Autowired
    VoiceService voiceService;

    @Autowired
    VoiceNotifyService voiceNotifyService;
    @Test
    public void testUser(){

        User user = accountService.getUserByUid("0db4f8a88ac14ff6aae9fbad5ebf3949");
        System.out.println("----------------------"+new Gson().toJson(user));
    }

    @Test
    public void testCard(){
        CardPhone cardPhone = new CardPhone();
        cardPhone.setAppId("9321da35d369426e8a10dbd39b79c884");
        cardPhone.setPhone("1064692010395");
        cardPhone.setCard("13812345678");
        try {
            cardPhoneCTLService.unBind(cardPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    @Test
    public void testVn() throws Exception {
        VnObj obj = new VnObj();
        obj.setAppid("4ee5e4b178154200b6e31a5d5c3c6e82");
        obj.setSid("798b9ec0eaa94142852602c402570146");
        obj.setCaller("13825161903");
        obj.setCalled("13418477875");
        obj.setCreatetime(new Date());
        obj.setNeedrecord(0);
        obj.setValidtime(72000);
        obj.setVnid(ID.randomUUID());
        VnObj vnObj =  virtualNumService.addVnNum(obj);
        System.out.println("---dbvn---"+new Gson().toJson(vnObj));
    }

    @Test
    public void testgetVn(){
        JedisClusterUtil jedisClusterUtil = ctx.getBean(JedisClusterUtil.class);
        //jedisClusterUtil.STRINGS.set(VN_VNM+"798b9ec0eaa94142852602c402570146"+"_"+"123456","798b9ec0eaa94142852602c402570146|4ee5e4b178154200b6e31a5d5c3c6e82|13825161903|10013|1|a809bd6645b149f3b91360549ff4db56");
        //System.out.print("---vn" + jedisClusterUtil.STRINGS.get(VN_VNM+"798b9ec0eaa94142852602c402570146"+"_"+"1001"));
        System.out.print("---white" + jedisClusterUtil.HASH.hget(VN_WHITE+"798b9ec0eaa94142852602c402570146", "1001"));
    }

    @Test
    public void getDbvn(){
        VnObj obj = new VnObj();
        obj.setVn("843993");
        System.out.println("vn==="+vnService.findVnByObj(obj));
    }

    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    @Test
    public void insertWhtie(){
        for(int i=10011;i<10016;i++){
            VnWhite obj = new VnWhite();
            obj.setSid("798b9ec0eaa94142852602c402570146");
            obj.setNum(String.valueOf(i));
            obj.setNtype(1);
            obj.setCreatetime(new Date());
            obj.setValidtime(14400);
            virtualNumService.addVnWhite(obj.getSid(),obj.getNum(),obj.getValidtime());
        }

    }

    @Transactional   //标明此方法需使用事务
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
    @Test
    public void insertVoice(){
        Date d=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        VoiceReq voiceReq = new VoiceReq();
        voiceReq.setRequestId("123456699999");
        voiceReq.setAccountSid(ID.randomUUID());
        voiceReq.setAppId(ID.randomUUID());
        voiceReq.setVoiceRecId("1234566777");
        voiceReq.setDtmfFlag(0);
        voiceReq.setOrderTime(df.format(new Date(d.getTime() +  10* 60 * 1000)));
        voiceReq.setOrderFlag(1);
        voiceReq.setDisplayNum("15810494406");
        voiceReq.setSnCode("0080");

        voiceReq.setVoicePath("2017/a.mp3");
        List<String> toList = new ArrayList<String>();
        toList.add("13924641704");
        toList.add("13924641705");
        toList.add("13924641706");
        voiceReq.setBatchFlag(toList!=null&&toList.size()>1?1:0);
        voiceReq.setToList(toList);
        voiceReq.setToNum(StringUtils.join(toList.toArray(),","));
        voiceNotifyService.insertVoice(voiceReq);
    }

    @Test
    public void testVoice(){
        Date d=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        VoiceReq voiceReq = new VoiceReq();
        voiceReq.setRequestId("1234566777777");
        voiceReq.setAccountSid(ID.randomUUID());
        voiceReq.setAppId(ID.randomUUID());
        voiceReq.setDtmfFlag(0);
        voiceReq.setOrderTime(df.format(new Date(d.getTime() +  1* 30 * 1000)));
        voiceReq.setOrderFlag(1);
        voiceReq.setDisplayNum("15810494406");
        voiceReq.setSnCode("0080");
        List<String> toList = new ArrayList<String>();
        toList.add("13924641704");
        toList.add("13924641705");
        toList.add("13924641706");
        voiceReq.setToList(toList);
        voiceReq.setToNum(StringUtils.join(toList.toArray(),","));
        voiceService.addJob(voiceReq);
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
