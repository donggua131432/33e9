package com.e9cloud.pcweb.account.action;

import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.FeeListService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/2/22.
 */
@Controller
@RequestMapping("/feelist")
public class FeeListController extends BaseController {
    private final static String FEE_LIST= "acc/feeList";//add  by  li.xin

    @Autowired
    private FeeListService feeListService;

    /**
     * 返回显示资费列表
     */
    @RequestMapping("/feelist")
    public String queryfeeli(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
        logger.info("queryfeelist start");
        return FEE_LIST;
    }


    /**
     * 查询资费列表
     */
    @RequestMapping("/query")
    public String queryfeelist(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        logger.info("queryfeelist start");
        Account account =  (Account) request.getSession().getAttribute("userInfo");
        String feeid = account.getFeeid();
        String busType = account.getBusType();
        model.addAttribute("busType", busType);

        String countCc = feeListService.countCc(account.getSid());
        String countSipPhone = feeListService.countSipPhone(account.getSid());
        String countEcc = feeListService.countEcc(account.getSid());
        String countAxb = feeListService.countAxb(account.getSid());

        RestRate restRate = feeListService.findRestRateByFeeid(feeid);
        CallRate callRate = feeListService.findCallRateByFeeid(feeid);
        MaskRate maskRate = feeListService.findMaskRateByFeeid(feeid);
        VoiceRate voiceRate = feeListService.findVoiceRateByFeeid(feeid);
        SipphonRate sipphonRate = feeListService.findSipphonRateByFeeid(feeid);
        IvrRate ivrRate = feeListService.findIvrRateRateByFeeid(feeid);
        AxbRate axbRate = feeListService.findAxbRateByFeeid(feeid);
        VoiceVerifyRate voiceVerifyRate = feeListService.findVoiceVerifyRateByFeeid(feeid);

        model.addAttribute("restRate", restRate);
        model.addAttribute("callRate", callRate);
        model.addAttribute("maskRate", maskRate);
        model.addAttribute("voiceRate", voiceRate);
        model.addAttribute("sipphonRate", sipphonRate);
        model.addAttribute("ivrRate", ivrRate);
        model.addAttribute("axbRate", axbRate);
        model.addAttribute("voiceVerifyRate", voiceVerifyRate);

        model.addAttribute("countCc", countCc);
        model.addAttribute("countSipPhone", countSipPhone);
        model.addAttribute("countEcc", countEcc);
        model.addAttribute("countAxb", countAxb);
        return FEE_LIST;
    }

}

