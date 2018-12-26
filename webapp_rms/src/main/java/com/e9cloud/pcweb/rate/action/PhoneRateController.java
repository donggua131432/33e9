package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.SipphonRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.MaskRateService;
import com.e9cloud.mybatis.service.PhoneRateService;
import com.e9cloud.mybatis.service.UserAdminService;
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
 * Created by admin on 2016/10/28.
 */
@Controller
@RequestMapping("/spRate")
public class PhoneRateController extends BaseController {

    @Autowired
    private PhoneRateService phoneRateService;

    @Autowired
    private MaskRateService maskRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    /**
     * 云话机费率配置信息
     * @return
     */
    @RequestMapping(value = "addPhoneRate", method = RequestMethod.GET)
    public String addPhoneRate(HttpServletRequest request, Model model){
        logger.info("----PhoneRateController addPhoneRate start-------");
        SipphonRate rate = phoneRateService.findPhoneRateByFeeId("0");
        model.addAttribute("phoneRate", rate);
        return "rate/addPhoneRate";
    }

    /**
     * 添加云话机费率配置信息
     * @param sipphonRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitPhoneRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitPhoneRate(SipphonRate sipphonRate, String sid){
        logger.info("----PhoneRateController submitPhoneRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            SipphonRate resultPhoneRate = phoneRateService.findPhoneRateByFeeId(resultUserAdmin.getFeeid());
            if(resultPhoneRate == null){
                logger.info("----PhoneRateController insert SipphonRate info-------");
                sipphonRate.setFeeid(resultUserAdmin.getFeeid());
                phoneRateService.savePhoneRate(sipphonRate);
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
                if(sipphonRate.getStartDate() != null && sipphonRate.getEndDate() != null){
                    content += "</br>有效期："+sipphonRate.getStartDate()+"至"+sipphonRate.getEndDate();
                }
                if(sipphonRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(sipphonRate.getRestADiscount() != null){
                    content += "</br>云话机回拨A路折扣率："+sipphonRate.getRestADiscount()/10+"%";
                }
                if(sipphonRate.getRestBDiscount() != null){
                    content += "</br>云话机回拨B路折扣率："+sipphonRate.getRestBDiscount()/10+"%";
                }
                if(sipphonRate.getRestBSipphoneDiscount() != null){
                    content += "</br>云话机回拨B路Sip Phone折扣率："+sipphonRate.getRestBSipphoneDiscount()/10+"%";
                }
                if(sipphonRate.getRestRecordingDiscount() != null){
                    content += "</br>云话机回拨录音折扣率："+sipphonRate.getRestRecordingDiscount()/10+"%";
                }
                if(sipphonRate.getSipcallsipDiscount() != null){
                    content += "</br>云话机直拨Sip Phone折扣率："+sipphonRate.getSipcallsipDiscount()/10+"%";
                }
                if(sipphonRate.getSipcallRecordingDiscount() != null){
                    content += "</br>云话机直拨录音折扣率："+sipphonRate.getSipcallRecordingDiscount()/10+"%";
                }
                if(sipphonRate.getBackcallDiscount() != null){
                    content += "</br>云话机回呼折扣率："+sipphonRate.getBackcallDiscount()/10+"%";
                }
                if(sipphonRate.getBackcallRecordingDiscount() != null){
                    content += "</br>云话机回呼录音折扣率："+sipphonRate.getBackcallRecordingDiscount()/10+"%";
                }
                if(sipphonRate.getNumRentDiscount() != null){
                    content += "</br>单号码月租折扣率："+sipphonRate.getNumRentDiscount()/10+"%";
                }
                if(sipphonRate.getNumMinCost() != null){
                    content += "</br>单号码低消："+sipphonRate.getNumMinCost()+"元";
                }
                LogUtil.log("新增云话机费率配置",content, LogType.INSERT);

                logger.info("----insert SipphonRate info success-------");
            }else{
                map.put("info","已配置费率信息，无法添加新的费率配置！");
            }
        }

        return map;
    }


    /**
     * 私密专线费率列表信息
     * @return
     */
    @RequestMapping(value = "/showPhoneRateList")
    public String showPhoneRateList(Model model){
        logger.info("----PhoneRateController showPhoneRateList start-------");
        SipphonRate rate = phoneRateService.findPhoneRateByFeeId("0");
        model.addAttribute("phoneRate", rate);
        return "rate/phoneRateList";
    }



    /**
     * 分页查询费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pagePhoneRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pagePhoneRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----PhoneRateController pagePhoneRateUnion start-------");
        return phoneRateService.pagePhoneRateUnion(page);
    }

    /**
     * 获取云话机相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getPhoneRateDetail", method = RequestMethod.GET)
    public String getPhoneRateDetail(HttpServletRequest request,Model model){
        logger.info("----PhoneRateController getPhoneRateDetail start-------");
        model.addAttribute("standardRate", phoneRateService.findPhoneRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<SipphonRate> sipphonRates = phoneRateService.selectPhoneRateList(userAdmin);
        if(sipphonRates.size()>0){
            model.addAttribute("phoneRate", sipphonRates.get(0));
        }
        return "/rate/phoneRateDetail";
    }

    /**
     * udpatePhoneRate 修改费率配置信息
     * @param request
     * @param sipphonRate
     * @return
     */
    @RequestMapping(value = "udpatePhoneRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpatePhoneRate(HttpServletRequest request, SipphonRate sipphonRate){
        logger.info("----PhoneRateController udpatePhoneRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        if(sipphonRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(sipphonRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            SipphonRate beforSipphonRate = phoneRateService.findPhoneRateByFeeId(sipphonRate.getFeeid());

            phoneRateService.updatePhoneRate(sipphonRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");

            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }

            if(beforSipphonRate.getStartDate() != null && beforSipphonRate.getEndDate() != null){
                beforContent += "</br>有效期："+beforSipphonRate.getStartDate()+"至"+beforSipphonRate.getEndDate();
            } else {
                beforContent += "</br>有效期：永久有效";
            }

            if(sipphonRate.getStartDate() != null && sipphonRate.getEndDate() != null){
                afterConter += "</br>有效期："+sipphonRate.getStartDate()+"至"+sipphonRate.getEndDate();
            } else {
                afterConter += "</br>有效期：永久有效";
            }

            if(sipphonRate.getForever() != null && sipphonRate.getForever()){
                beforContent += "</br>有效期："+beforSipphonRate.getStartDate()+"至"+beforSipphonRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }

            if(sipphonRate.getRestADiscount() != null){
                beforContent += "</br>云话机回拨A路折扣率："+beforSipphonRate.getRestADiscount()/10+"%";
                afterConter += "</br>云话机回拨A路折扣率："+sipphonRate.getRestADiscount()/10+"%";
            }
            if(sipphonRate.getRestBDiscount() != null){
                beforContent += "</br>云话机回拨B路折扣率："+beforSipphonRate.getRestBDiscount()/10+"%";
                afterConter += "</br>云话机回拨B路折扣率："+sipphonRate.getRestBDiscount()/10+"%";
            }
            if(sipphonRate.getRestBSipphoneDiscount() != null){
                beforContent += "</br>云话机回拨B路Sip Phone折扣率："+beforSipphonRate.getRestBSipphoneDiscount()/10+"%";
                afterConter += "</br>云话机回拨B路Sip Phone折扣率："+sipphonRate.getRestBSipphoneDiscount()/10+"%";
            }
            if(sipphonRate.getRestRecordingDiscount() != null){
                beforContent += "</br>云话机回拨录音折扣率："+beforSipphonRate.getRestRecordingDiscount()/10+"%";
                afterConter += "</br>云话机回拨录音折扣率："+sipphonRate.getRestRecordingDiscount()/10+"%";
            }
            if(sipphonRate.getSipcallsipDiscount() != null){
                beforContent += "</br>云话机直拨Sip Phone折扣率："+beforSipphonRate.getSipcallsipDiscount()/10+"%";
                afterConter += "</br>云话机直拨Sip Phone折扣率："+sipphonRate.getSipcallsipDiscount()/10+"%";
            }
            if(sipphonRate.getSipcallRecordingDiscount() != null){
                beforContent += "</br>云话机直拨录音折扣率："+beforSipphonRate.getSipcallRecordingDiscount()/10+"%";
                afterConter += "</br>云话机直拨录音折扣率："+sipphonRate.getSipcallRecordingDiscount()/10+"%";
            }
            if(sipphonRate.getBackcallDiscount() != null){
                beforContent += "</br>云话机回呼折扣率："+beforSipphonRate.getBackcallDiscount()/10+"%";
                afterConter += "</br>云话机回呼折扣率："+sipphonRate.getBackcallDiscount()/10+"%";
            }
            if(sipphonRate.getBackcallRecordingDiscount() != null){
                beforContent += "</br>云话机回呼录音折扣率："+beforSipphonRate.getBackcallRecordingDiscount()/10+"%";
                afterConter += "</br>云话机回呼录音折扣率："+sipphonRate.getBackcallRecordingDiscount()/10+"%";
            }
            if(sipphonRate.getNumRentDiscount() != null){
                beforContent += "</br>单号码月租折扣率："+beforSipphonRate.getNumRentDiscount()/10+"%";
                afterConter += "</br>单号码月租折扣率："+sipphonRate.getNumRentDiscount()/10+"%";
            }
            if(sipphonRate.getNumMinCost() != null){
                beforContent += "</br>单号码低消："+beforSipphonRate.getNumMinCost()+"元";
                afterConter += "</br>单号码低消："+sipphonRate.getNumMinCost()+"元";
            }

            LogUtil.log("云话机费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----updatePhoneRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }
}
