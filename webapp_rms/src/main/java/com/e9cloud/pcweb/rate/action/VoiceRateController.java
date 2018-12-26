package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.domain.VoiceRate;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.MaskRateService;
import com.e9cloud.mybatis.service.UserAdminService;
import com.e9cloud.mybatis.service.VoiceRateService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dukai on 2016/6/1.
 */
@Controller
@RequestMapping("/voiceRate")
public class VoiceRateController extends BaseController{

    @Autowired
    private VoiceRateService voiceRateService;

    @Autowired
    private MaskRateService maskRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    /**
     * 语音通知费率列表信息
     * @return
     */
    @RequestMapping(value = "/showVoiceRateList")
    public String showVoiceRateList(Model model){
        logger.info("----VoiceRateController showMaskRateList start-------");
        VoiceRate rate = voiceRateService.findVoiceRateByFeeId("0");
        model.addAttribute("voiceRate", rate);
        return "rate/voiceRateList";
    }


    /**
     * 语音通知费率配置信息
     * @return
     */
    @RequestMapping(value = "addVoiceRate", method = RequestMethod.GET)
    public String addMaskRate(HttpServletRequest request, Model model){
        logger.info("----VoiceRateController addMaskRate start-------");
        VoiceRate rate = voiceRateService.findVoiceRateByFeeId("0");
        model.addAttribute("voiceRate", rate);
        return "rate/addVoiceRate";
    }

    /**
     * 添加私密专线费率配置信息
     * @param voiceRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitVoiceRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitVoiceRate(VoiceRate voiceRate, String sid){
        logger.info("----VoiceRateController submitVoiceRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            VoiceRate resultVoiceRate = voiceRateService.findVoiceRateByFeeId(resultUserAdmin.getFeeid());
            if(resultVoiceRate == null){
                logger.info("----VoiceRateController insert MaskRate info-------");
                voiceRate.setFeeid(resultUserAdmin.getFeeid());
                if (voiceRate.getForever()==null){
                    voiceRate.setForever(false);
                }
                voiceRateService.saveVoiceRate(voiceRate);
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
                if(voiceRate.getStartDate() != null && voiceRate.getEndDate() != null){
                    content += "</br>有效期："+voiceRate.getStartDate()+"至"+voiceRate.getEndDate();
                }
                if(voiceRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(voiceRate.getPer60Discount() != null){
                    content += "</br>语音通知折扣率："+voiceRate.getPer60Discount()/10+"%";
                }
                LogUtil.log("新增私密专线费率配置",content, LogType.INSERT);
                logger.info("----insert MaskRate info success-------");
            }else{
                map.put("info","已配置费率信息，无法添加新的费率配置！");
            }
        }

        return map;
    }

    /**
     * 获取私密专线相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getVoiceRateDetail", method = RequestMethod.GET)
    public String getVoiceRateDetail(HttpServletRequest request,Model model){
        logger.info("----VoiceRateController getVoiceRateDetail start-------");
        model.addAttribute("standardRate", voiceRateService.findVoiceRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<VoiceRate> voiceRates = voiceRateService.selectVoiceRateList(userAdmin);
        if(voiceRates.size()>0){
            model.addAttribute("voiceRate", voiceRates.get(0));
        }
        return "/rate/voiceRateDetail";
    }

    /**
     * 联表查询私密专线费率信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMaskRateUnion")
    @ResponseBody
    public MaskRate getMaskRateUnion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----VoiceRateController getMaskRateUnion start-------");
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(request.getParameter("sid"));
        userAdmin.setUid(request.getParameter("uid"));
        userAdmin.setFeeid(request.getParameter("feeId"));

        List<MaskRate> maskRates = maskRateService.selectMaskRateList(userAdmin);
        if(maskRates.size()>0){
            return maskRates.get(0);
        }else{
            return null;
        }
    }

    /**
     * 分页查询费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pageVoiceRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageVoiceRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----VoiceRateController pageMaskRateUnion start-------");
        return voiceRateService.pageVoiceRateUnion(page);
    }

    /**
     * udpateMaskRate 修改费率配置信息
     * @param request
     * @param voiceRate
     * @return
     */
    @RequestMapping(value = "updateVoiceRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateVoiceRate(HttpServletRequest request, VoiceRate voiceRate){
        logger.info("----VoiceRateController udpateMaskRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");
        if(voiceRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(voiceRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            VoiceRate beforVoiceRate = voiceRateService.findVoiceRateByFeeId(voiceRate.getFeeid());

            voiceRateService.updateVoiceRate(voiceRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");


            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(voiceRate.getStartDate() != null && voiceRate.getEndDate() != null){
                beforContent += "</br>有效期：永久有效";
                afterConter += "</br>有效期："+voiceRate.getStartDate()+"至"+voiceRate.getEndDate();
            }
            if(voiceRate.getForever() != null && voiceRate.getForever() == true){
                beforContent += "</br>有效期："+beforVoiceRate.getStartDate()+"至"+beforVoiceRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }

            if(voiceRate.getPer60Discount() != null){
                beforContent += "</br>语音通知折扣："+beforVoiceRate.getPer60Discount()/10+"%";
                afterConter += "</br>语音通知折扣率："+voiceRate.getPer60Discount()/10+"%";
            }

            LogUtil.log("语音通知费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----udpateVoiceRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }
}
