package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceVerifyRate;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.mybatis.service.VoiceVerifyRateService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzd on 2017/5/2.
 */
@Controller
@RequestMapping("/voiceVerifyRate")
public class VoiceVerifyRateController extends BaseController{

    @Autowired
    private VoiceVerifyRateService voiceVerifyRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;


    /**
     * 语音通知费率列表信息
     * @return
     */
    @RequestMapping(value = "/showVoiceVerifyRateList")
    public String showVoiceVerifyRateList(Model model){
        logger.info("----VoiceVerifyRateController showVoiceVerifyRateList start-------");
        VoiceVerifyRate rate = voiceVerifyRateService.findVoiceVerifyRateByFeeId("0");
        model.addAttribute("voiceVerifyRate", rate);
        return "rate/voiceVerifyRateList";
    }

    /**
     * 语音通知费率配置信息
     * @return
     */
    @RequestMapping(value = "addVoiceCodeRate", method = RequestMethod.GET)
    public String addVoiceCodeRate(HttpServletRequest request, Model model){
        logger.info("----VoiceCodeRateController addVoiceCodeRate start-------");
        VoiceVerifyRate rate = voiceVerifyRateService.findVoiceVerifyRateByFeeId("0");
        model.addAttribute("voiceVerifyRate", rate);
        return "rate/addVoiceVerifyRate";
    }

    /**
     * 分页查询费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pageVoiceVerifyRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageVoiceVerifyRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----pageVoiceVerifyRateUnion start-------");
        return voiceVerifyRateService.pageVoiceVerifyRateUnion(page);
    }

    /**
     * 获取语音验证码相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getVoiceVerifyRateDetail", method = RequestMethod.GET)
    public String getVoiceVerifyRateDetail(HttpServletRequest request,Model model){
        logger.info("----VoiceCodeRateController getVoiceVerifyRateDetail start-------");
        model.addAttribute("standardRate", voiceVerifyRateService.findVoiceVerifyRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<VoiceVerifyRate> voiceVerifyRates = voiceVerifyRateService.selectVoiceVerifyRateList(userAdmin);
        if(voiceVerifyRates.size()>0){
            model.addAttribute("voiceVerifyRate", voiceVerifyRates.get(0));
        }
        return "/rate/voiceVerifyRateDetail";
    }

    /**
     * 添加私密专线费率配置信息
     * @param VoiceVerifyRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitVoiceVerifyRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitVoiceVerifyRate(VoiceVerifyRate VoiceVerifyRate, String sid){
        logger.info("----VoiceCodeRateController submitVoiceVerifyRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            VoiceVerifyRate resultVoiceRate = voiceVerifyRateService.findVoiceVerifyRateByFeeId(resultUserAdmin.getFeeid());
            if(resultVoiceRate == null){
                logger.info("----VoiceCodeRateController insert VoiceVerifyRate info-------");
                VoiceVerifyRate.setFeeid(resultUserAdmin.getFeeid());
                if (VoiceVerifyRate.getForever()==null){
                    VoiceVerifyRate.setForever(false);
                }
                if ("01".equals(VoiceVerifyRate.getFeeMode())){
                    VoiceVerifyRate.setPer60Discount(null);
                }
                voiceVerifyRateService.saveVoiceVerifyRate(VoiceVerifyRate);
                map.put("status","0");
                map.put("info","费率信息添加成功！");

                //记录操作日志
                AuthenCompany authenCompanyAgr = new AuthenCompany();
                authenCompanyAgr.setUid(resultUserAdmin.getUid());
                AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
                String content ="";
                if(authenCompany.getName()!=null){
                    content += "客户名称："+authenCompany.getName();
                }
                if(VoiceVerifyRate.getStartDate() != null && VoiceVerifyRate.getEndDate() != null){
                    content += "</br>有效期："+VoiceVerifyRate.getStartDate()+"至"+VoiceVerifyRate.getEndDate();
                }
                if(VoiceVerifyRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(VoiceVerifyRate.getPerDiscount() != null){
                    content += "</br>语音验证码按条计算折扣率："+VoiceVerifyRate.getPerDiscount()/10+"%";
                }
                if(VoiceVerifyRate.getPer60Discount() != null){
                    content += "</br>语音验证码按分钟折扣率："+VoiceVerifyRate.getPer60Discount()/10+"%";
                }
                LogUtil.log("语音验证码费率配置",content, LogType.INSERT);
                logger.info("----insert VoiceVerifyRate info success-------");
            }else{
                map.put("info","已配置费率信息，无法添加新的费率配置！");
            }
        }

        return map;
    }

    /**
     * udpateVoiceVerifyRate 修改费率配置信息
     * @param request
     * @param voiceVerifyRate
     * @return
     */
    @RequestMapping(value = "udpateVoiceVerifyRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpateVoiceVerifyRate(HttpServletRequest request, VoiceVerifyRate voiceVerifyRate){
        logger.info("----VoiceVerifyRateController udpateVoiceVerifyRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");
        if(voiceVerifyRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(voiceVerifyRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            VoiceVerifyRate beforVoiceVerifyRate = voiceVerifyRateService.findVoiceVerifyRateByFeeId(voiceVerifyRate.getFeeid());

            String updateDateFlag = request.getParameter("updateDateFlag");
            if (updateDateFlag!=null && "1".equals(updateDateFlag)){
                voiceVerifyRate.setPer60Discount(beforVoiceVerifyRate.getPer60Discount());
                voiceVerifyRate.setPerDiscount(beforVoiceVerifyRate.getPerDiscount());
            }

            voiceVerifyRateService.updateVoiceVerifyRate(voiceVerifyRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");


            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(voiceVerifyRate.getStartDate() != null && voiceVerifyRate.getEndDate() != null){
                beforContent += "</br>修改前有效期：永久有效";
                afterConter += "</br>修改后有效期："+voiceVerifyRate.getStartDate()+"至"+voiceVerifyRate.getEndDate();
            }
            if(voiceVerifyRate.getForever() != null && voiceVerifyRate.getForever() == true){
                beforContent += "</br>修改前有效期："+beforVoiceVerifyRate.getStartDate()+"至"+beforVoiceVerifyRate.getEndDate();
                afterConter += "</br>修改后有效期：永久有效";
            }

            if(voiceVerifyRate.getPer60Discount() != null && beforVoiceVerifyRate.getPer60Discount() !=null){
                beforContent += "</br>修改前语音验证码按分钟折扣率："+beforVoiceVerifyRate.getPer60Discount()/10+"%";
                afterConter += "</br>修改后语音验证码按分钟折扣率："+voiceVerifyRate.getPer60Discount()/10+"%";
            }
            if(voiceVerifyRate.getPerDiscount() != null && beforVoiceVerifyRate.getPerDiscount() !=null){
                beforContent += "</br>修改前语音验证码按条折扣率："+beforVoiceVerifyRate.getPerDiscount()/10+"%";
                afterConter += "</br>修改后语音验证码按条折扣率："+voiceVerifyRate.getPerDiscount()/10+"%";
            }

            LogUtil.log("语音验证码费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----udpateVoiceVerifyRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }

}
