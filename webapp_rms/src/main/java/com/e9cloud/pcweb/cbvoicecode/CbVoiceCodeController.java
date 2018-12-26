package com.e9cloud.pcweb.cbvoicecode;

import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.CbVoiceCodeService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础配置
 * Created by Administrator on 2017/4/8.
 */
@Controller
@RequestMapping("/cbVoiceCode")
public class CbVoiceCodeController extends BaseController {

    @Autowired
    private CbVoiceCodeService cbVoiceCodeService;


    // 跳转到智能云调度基础配置页面
    @RequestMapping("/toZnyddList")
    public String toZnyddList(Model model) {
        CbVoiceCode cbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.cc.toString());
        model.addAttribute("cbVoiceCode", cbVoiceCode);
        return "cbVoiceCode/znyddCbVoiceCodelist";
    }

    // 跳转到专号通基础配置页面
    @RequestMapping("/toZhtList")
    public String toZhtList(Model model) {
        CbVoiceCode cbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.mask.toString());
        model.addAttribute("cbVoiceCode", cbVoiceCode);
        return "cbVoiceCode/zhtCbVoiceCodelist";
    }

    // 跳转到开放接口基础配置页面
    @RequestMapping("/toKfjkList")
    public String toKfjkList(Model model) {
        CbVoiceCode zxyyCbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.rest.toString());

        CbVoiceCode yytzCbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.voice.toString());
        model.addAttribute("zxyyCbVoiceCode", zxyyCbVoiceCode);
        model.addAttribute("yytzCbVoiceCode", yytzCbVoiceCode);

        return "cbVoiceCode/kfjkCbVoiceCodelist";
    }

    // 跳转到sip基础配置页面
    @RequestMapping("/toSipList")
    public String toSipList(Model model) {
        CbVoiceCode cbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.sip.toString());
        model.addAttribute("cbVoiceCode", cbVoiceCode);
        return "cbVoiceCode/sipCbVoiceCodelist";
    }

    // 跳转到语音验证码基础配置页面
    @RequestMapping("/toVoiceValidateList")
    public String toVoiceValidateList(Model model) {
        CbVoiceCode cbVoiceCode = cbVoiceCodeService.findCbVoiceCodeByBusCode(CbVoiceCode.Code.voiceValidate.toString());
        model.addAttribute("cbVoiceCode", cbVoiceCode);
        return "cbVoiceCode/voiceValidateCbVoiceCodelist";
    }

    // 修改智能云调度语音编码
    @RequestMapping("/editCbVoiceCode")
    @ResponseBody
    public JSonMessage edit(String id,String voiceCode) {
        CbVoiceCode cbVoiceCode = new CbVoiceCode();
        cbVoiceCode.setId(Integer.valueOf(id));
        cbVoiceCode.setVoiceCode(voiceCode);
        cbVoiceCodeService.updateCbVoiceCode(cbVoiceCode);
        return new JSonMessage(R.OK, "修改成功");
    }

}
