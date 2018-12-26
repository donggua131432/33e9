package com.e9cloud.mybatis.service;

import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.User;
import com.e9cloud.mybatis.mapper.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    public User findAccountByLoginName(String loginName){
        return userService.findAccountByLoginName(loginName);
    }

    public boolean hasAction(Integer userId, String url) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("url", url);
        try {
            List<Map<String, Object>> actions = userService.findObjectListByMap(Mapper.User_Mapper.selectUserAction, params);
            System.out.println(JSonUtils.toJSon(actions));
            return actions.size() != 0
                    && !actions.stream().anyMatch(map -> (map.get("url").equals(url) && (Long)(map.get("hasAction")) == 0));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        String url = "/developers/audit";
        String urls = "[{\"hasAction\":1,\"url\":\"/menu\"},{\"hasAction\":1,\"url\":\"/user/index\"},{\"hasAction\":1,\"url\":\"/action/index\"},{\"hasAction\":0,\"url\":\"/callRate/showCallRateList\"},{\"hasAction\":0,\"url\":\"/restRate/showRestRateList\"},{\"hasAction\":1,\"url\":\"/log/index\"},{\"hasAction\":0,\"url\":\"/financial/showRecharge\"},{\"hasAction\":0,\"url\":\"/financial/rechargeList\"},{\"hasAction\":0,\"url\":\"/financial/balanceList\"},{\"hasAction\":0,\"url\":\"/financial/showPayment\"},{\"hasAction\":0,\"url\":\"/financial/paymentList\"},{\"hasAction\":1,\"url\":\"/user/getUserInfo\"},{\"hasAction\":0,\"url\":\"/znydd/toAdd\"},{\"hasAction\":0,\"url\":\"/znydd/toAppList\"},{\"hasAction\":0,\"url\":\"/developers/audit\"},{\"hasAction\":0,\"url\":\"/openi/toAppList\"},{\"hasAction\":0,\"url\":\"/userAdminMgr/toUserAdmin\"},{\"hasAction\":0,\"url\":\"/number/audit\"},{\"hasAction\":0,\"url\":\"/ringtone/audit\"},{\"hasAction\":0,\"url\":\"/feeRate/feeRateConfig\"},{\"hasAction\":0,\"url\":\"/maskRate/showMaskRateList\"},{\"hasAction\":0,\"url\":\"/maskLine/maskLineList\"},{\"hasAction\":0,\"url\":\"/maskLine/maskNumberPoolConfig\"},{\"hasAction\":0,\"url\":\"/financial/balanceHistoryList\"},{\"hasAction\":0,\"url\":\"/maskRingtone/audit\"},{\"hasAction\":0,\"url\":\"/numberBlack/numberBlackMgr\"},{\"hasAction\":0,\"url\":\"/customerMgr/customerMgrIndex\"},{\"hasAction\":1,\"url\":\"/msgMgr/query\"},{\"hasAction\":0,\"url\":\"/sip/toAppList\"},{\"hasAction\":0,\"url\":\"/relay/list\"},{\"hasAction\":0,\"url\":\"/numberpart/toList\"},{\"hasAction\":0,\"url\":\"/cityMgr/cityMgrIndex\"},{\"hasAction\":0,\"url\":\"/ccinfo/toList\"},{\"hasAction\":0,\"url\":\"/ccareacity/toList\"},{\"hasAction\":1,\"url\":\"/roleMgr/roleMgrIndex\"},{\"hasAction\":0,\"url\":\"/supplier/supplierList\"},{\"hasAction\":0,\"url\":\"/appointLink/appointLinkList\"},{\"hasAction\":0,\"url\":\"/relaygroup1/index\"},{\"hasAction\":0,\"url\":\"/relaygroup2/index\"},{\"hasAction\":0,\"url\":\"/relaygroup3/index\"},{\"hasAction\":0,\"url\":\"/sipNumPool/toSipNumList\"},{\"hasAction\":0,\"url\":\"/codeType/goRoute\"},{\"hasAction\":0,\"url\":\"/voiceRate/showVoiceRateList\"},{\"hasAction\":0,\"url\":\"/tempVoiceAudit/list\"},{\"hasAction\":0,\"url\":\"/sipPhoneNum/audit\"},{\"hasAction\":0,\"url\":\"/pubNumResPool/pubNumResPool\"},{\"hasAction\":0,\"url\":\"/sp/appIndex\"},{\"hasAction\":0,\"url\":\"/spRate/showPhoneRateList\"},{\"hasAction\":0,\"url\":\"/spApply/index\"},{\"hasAction\":0,\"url\":\"/openTask/toTask\"},{\"hasAction\":0,\"url\":\"/ivrRate/showIvrRateList\"},{\"hasAction\":0,\"url\":\"/ecc/toAppList\"},{\"hasAction\":0,\"url\":\"/sipNumResPool/sipNumResPool\"},{\"hasAction\":0,\"url\":\"/eccFixPhonePool/eccFixPhonePool\"},{\"hasAction\":0,\"url\":\"/eccShowNumPool/eccShowNumPool\"},{\"hasAction\":0,\"url\":\"/eccSwitchBoardPool/eccSwitchBoardPool\"},{\"hasAction\":0,\"url\":\"/sipurl/index\"},{\"hasAction\":0,\"url\":\"/cbVoiceCode/toZnyddList\"},{\"hasAction\":0,\"url\":\"/cbVoiceCode/toZhtList\"},{\"hasAction\":0,\"url\":\"/cbVoiceCode/toSipList\"},{\"hasAction\":0,\"url\":\"/cbVoiceCode/toKfjkList\"},{\"hasAction\":0,\"url\":\"/axbNumResPool//axbNumResPool\"},{\"hasAction\":0,\"url\":\"/axbCustNumResPool/axbCustNumberPoolConfig\"},{\"hasAction\":0,\"url\":\"/axbRate/showAxbRateList\"},{\"hasAction\":0,\"url\":\"/voiceVerifyRate/showVoiceVerifyRateList\"},{\"hasAction\":0,\"url\":\"/voiceVerify/list\"}]";
        List<Map<String, Object>> actions = JSonUtils.readValue(urls, ArrayList.class);
        System.out.println(actions.size() != 0 && !actions.stream().anyMatch((map) -> (map.containsValue(url) && map.containsValue(0))));
    }
}
