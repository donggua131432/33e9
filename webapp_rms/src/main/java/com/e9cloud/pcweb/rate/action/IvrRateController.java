package com.e9cloud.pcweb.rate.action;


import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.IvrRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.IvrRateService;
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
 * Created by dukai on 2017/02/10.
 */
@Controller
@RequestMapping("/ivrRate")
public class IvrRateController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private IvrRateService ivrRateService;

    @Autowired
    private AuthenCompanyService authenCompanyService;


    /**
     * 添加云总机费率配置信息
     * @return
     */
    @RequestMapping(value = "/addIvrRate", method = RequestMethod.GET)
    public String addIvrRate(Model model){
        logger.info("----IvrRateController addIvrRate start-------");
        IvrRate ivrRate = ivrRateService.findIvrRateByFeeId("0");
        model.addAttribute("ivrRate", ivrRate);
        return "rate/addIvrRate";
    }

    /**
     * 添加云总机费率配置信息
     * @param ivrRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "/submitIvrRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitIvrRate(IvrRate ivrRate, String sid){
        logger.info("----IvrRateController submitIvrRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            IvrRate resultIvrRate = ivrRateService.findIvrRateByFeeId(resultUserAdmin.getFeeid());
            if(resultIvrRate == null){
                logger.info("----IvrRateController insert ivrRate info-------");
                ivrRate.setFeeid(resultUserAdmin.getFeeid());
                ivrRateService.saveIvrRate(ivrRate);
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
                if(ivrRate.getStartDate() != null && ivrRate.getEndDate() != null){
                    content += "</br>有效期："+ivrRate.getStartDate()+"至"+ivrRate.getEndDate();
                }
                if(ivrRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(ivrRate.getCallinSipDiscount() != null){
                    content += "</br>呼入总机-SIP折扣率："+ivrRate.getCallinSipDiscount();
                }
                if(ivrRate.getCallinNonsipDiscount() != null){
                    content += "</br>呼入总机-非SIP折扣率："+ivrRate.getCallinNonsipDiscount()/10+"%";
                }
                if(ivrRate.getCallinDirectDiscount() != null){
                    content += "</br>呼入-直呼折扣率："+ivrRate.getCallinDirectDiscount()/10+"%";
                }
                if(ivrRate.getCallinRecordingDiscount() != null){
                    content += "</br>呼入-录音折扣率："+ivrRate.getCallinRecordingDiscount()/10+"%";
                }
                if(ivrRate.getCalloutLocalDiscount() != null){
                    content += "</br>呼出-市话折扣率："+ivrRate.getCalloutLocalDiscount()/10+"%";
                }
                if(ivrRate.getCalloutNonlocalDiscount() != null){
                    content += "</br>呼出-长途折扣率："+ivrRate.getCalloutNonlocalDiscount()/10+"%";
                }
                if(ivrRate.getCalloutRecordingDiscount() != null){
                    content += "</br>呼出-录音折扣率："+ivrRate.getCalloutRecordingDiscount()/10+"%";
                }
                if(ivrRate.getIvrRent() != null){
                    content += "</br>总机号码月租："+ivrRate.getIvrRent()+"元";
                }
                if(ivrRate.getSipnumRentDiscount() != null){
                    content += "</br>SIP号码月租折扣率："+ivrRate.getSipnumRentDiscount()/10+"%";
                }
                if(ivrRate.getSipnumMinCost() != null){
                    content += "</br>SIP号码低消："+ivrRate.getSipnumMinCost()+"元";
                }
                LogUtil.log("新增云总机费率配置",content, LogType.INSERT);
                logger.info("----insert ivrRate info success-------");
            }else{
                map.put("info","已配置费率信息，无法添加新的费率配置！");
            }
        }
        return map;
    }

    /**
     * 云总机费率列表信息
     * @return
     */
    @RequestMapping(value = "/showIvrRateList")
    public String showIvrRateList(Model model){
        logger.info("----IvrRateController showIvrRateList start-------");
        IvrRate ivrRate = ivrRateService.findIvrRateByFeeId("0");
        model.addAttribute("ivrRate", ivrRate);
        return "rate/ivrRateList";
    }


    /**
     * 分页查询费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/pageIvrRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageIvrRateUnion(Page page) throws Exception {
        logger.info("----IvrRateController pageIvrRateUnion start-------");
        return ivrRateService.pageIvrRateUnion(page);
    }



    /**
     * 获取云总机相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getIvrRateDetail", method = RequestMethod.GET)
    public String getIvrRateDetail(HttpServletRequest request,Model model){
        logger.info("----IvrRateController getIvrRateDetail start-------");
        model.addAttribute("standardRate", ivrRateService.findIvrRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<IvrRate> ivrRates = ivrRateService.selectIvrRateList(userAdmin);
        if(ivrRates.size()>0){
            model.addAttribute("ivrRate", ivrRates.get(0));
        }
        return "/rate/ivrRateDetail";
    }

    /**
     * updateIvrRate 修改费率配置信息
     * @param ivrRate
     * @return
     */
    @RequestMapping(value = "/updateIvrRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> updateIvrRate(IvrRate ivrRate){
        logger.info("----IvrRateController updateIvrRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        if(ivrRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(ivrRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            IvrRate beforeIvrRate = ivrRateService.findIvrRateByFeeId(ivrRate.getFeeid());

            ivrRateService.updateIvrRate(ivrRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");


            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(ivrRate.getStartDate() != null && ivrRate.getEndDate() != null){
                beforContent += "</br>有效期：永久有效";
                afterConter += "</br>有效期："+ivrRate.getStartDate()+"至"+ivrRate.getEndDate();
            }
            if(ivrRate.getForever() != null && ivrRate.getForever()){
                beforContent += "</br>有效期："+beforeIvrRate.getStartDate()+"至"+beforeIvrRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }

            if(ivrRate.getCallinSipDiscount() != null){
                beforContent += "</br>呼入总机-SIP折扣率："+beforeIvrRate.getCallinSipDiscount()/10+"%";
                afterConter += "</br>呼入总机-SIP折扣率："+ivrRate.getCallinSipDiscount()/10+"%";
            }
            if(ivrRate.getCallinNonsipDiscount() != null){
                beforContent += "</br>呼入总机-非SIP折扣率："+beforeIvrRate.getCallinNonsipDiscount()/10+"%";
                afterConter += "</br>呼入总机-非SIP折扣率："+ivrRate.getCallinNonsipDiscount()/10+"%";
            }

            if(ivrRate.getCallinDirectDiscount() != null){
                beforContent += "</br>呼入-直呼折扣率："+beforeIvrRate.getCallinDirectDiscount()/10+"%";
                afterConter += "</br>呼入-直呼折扣率："+ivrRate.getCallinDirectDiscount()/10+"%";
            }

            if(ivrRate.getCallinRecordingDiscount() != null){
                beforContent += "</br>呼入-录音折扣率："+beforeIvrRate.getCallinRecordingDiscount()/10+"%";
                afterConter += "</br>呼入-录音折扣率："+ivrRate.getCallinRecordingDiscount()/10+"%";
            }

            if(ivrRate.getCalloutLocalDiscount() != null){
                beforContent += "</br>呼出-市话折扣率："+beforeIvrRate.getCalloutLocalDiscount()/10+"%";
                afterConter += "</br>呼出-市话折扣率："+ivrRate.getCalloutLocalDiscount()/10+"%";
            }

            if(ivrRate.getCalloutNonlocalDiscount() != null){
                beforContent += "</br>呼出-长途折扣率："+beforeIvrRate.getCalloutNonlocalDiscount()/10+"%";
                afterConter += "</br>呼出-长途折扣率："+ivrRate.getCalloutNonlocalDiscount()/10+"%";
            }

            if(ivrRate.getCalloutRecordingDiscount() != null){
                beforContent += "</br>呼出-录音折扣率："+beforeIvrRate.getCalloutRecordingDiscount()/10+"%";
                afterConter += "</br>呼出-录音折扣率："+ivrRate.getCalloutRecordingDiscount()/10+"%";
            }

            if(ivrRate.getIvrRent() != null){
                beforContent += "</br>总机号码月租："+beforeIvrRate.getIvrRent()+"元";
                afterConter += "</br>总机号码月租："+ivrRate.getIvrRent()+"元";
            }

            if(ivrRate.getSipnumRentDiscount() != null){
                beforContent += "</br>SIP号码月租折扣率："+beforeIvrRate.getSipnumRentDiscount()/10+"%";
                afterConter += "</br>SIP号码月租折扣率："+ivrRate.getSipnumRentDiscount()/10+"%";
            }

            if(ivrRate.getSipnumMinCost() != null){
                beforContent += "</br>SIP号码低消："+beforeIvrRate.getSipnumMinCost()+"元";
                afterConter += "</br>SIP号码低消："+ivrRate.getSipnumMinCost()+"元";
            }
            LogUtil.log("云总机费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----updateIvrRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }


}
