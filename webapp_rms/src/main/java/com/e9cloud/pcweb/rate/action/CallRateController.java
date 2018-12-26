package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.CallRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.CallRateService;
import com.e9cloud.mybatis.service.UserAdminService;
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
 *CallRateController负责95呼入呼出（z智能云调度）费率相关的业务控制，
 *
 * Created by DuKai on 2016/1/28.
 *
 */
@Controller
@RequestMapping(value = "/callRate")
public class CallRateController extends BaseController {

    @Autowired
    private CallRateService callRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    /**
     * 获取智能云调度费率列表信息
     * @return
     */
    @RequestMapping(value = "/showCallRateList")
    public String showCallRateList(Model model){
        logger.info("----showCallRateList start-------");
        CallRate rate = callRateService.findCallRateByFeeId("0");
        model.addAttribute("callRate", rate);
        return "rate/callRateList";
    }


    /**
     * 添加智能云调度费率配置信息
     * @return
     */
    @RequestMapping(value = "addCallRate", method = RequestMethod.GET)
    public String addCallRate(HttpServletRequest request, Model model){
        logger.info("----addCallRate start-------");
        CallRate rate = callRateService.findCallRateByFeeId("0");
        model.addAttribute("callRate", rate);
        return "rate/addCallRate";
    }

    /**
     * 添加智能云调度费率配置信息
     * @param request
     * @param callRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitCallRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitCallRate(HttpServletRequest request, CallRate callRate, String sid){
        logger.info("----submitCallRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            CallRate resultCalleRate = callRateService.findCallRateByFeeId(resultUserAdmin.getFeeid());
            if(resultCalleRate == null){
                logger.info("----insert CallRate info-------");
                callRate.setFeeid(resultUserAdmin.getFeeid());
                callRateService.saveCallRate(callRate);
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
                if(callRate.getStartDate() != null && callRate.getEndDate() != null){
                    content += "</br>有效期："+callRate.getStartDate()+"至"+callRate.getEndDate();
                }
                if(callRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(callRate.getFeeMode() != null){
                    content += "</br>计费方式："+callRate.getFeeMode();
                }
                if(callRate.getCallinDiscount() != null){
                    content += "</br>呼入折扣率："+callRate.getCallinDiscount()/10+"%";
                }
                if(callRate.getCalloutDiscount() != null){
                    content += "</br>呼出折扣率："+callRate.getCalloutDiscount()/10+"%";
                }

                if(callRate.getRelayRent()!=null && callRate.getRelayCnt() != null){
                    content += "</br>专线月租费：" + callRate.getRelayRent() + "元 " + callRate.getRelayCnt() + "条";
                }

                LogUtil.log("新增智能云调度费率配置", content, LogType.INSERT);
                logger.info("----insert CallRate info success-------");
            }else{
                map.put("info","已配置费率信息，无法添加新的费率配置！");
            }
        }

        return map;
    }

    /**
     * 获取智能云调度相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getCallRateDetail", method = RequestMethod.GET)
    public String getCallRateDetail(HttpServletRequest request,Model model){
        logger.info("----getCallRateDetail start-------");
        model.addAttribute("standardRate", callRateService.findCallRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<CallRate> callRates = callRateService.selectCallRateList(userAdmin);
        if(callRates.size()>0){
            model.addAttribute("callRate", callRates.get(0));
        }
        return "/rate/callRateDetail";
    }

    /**
     * 联表查询智能云调度费率信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCallRateUnion")
    @ResponseBody
    public CallRate getCallRateUnion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getCallRateUnion start-------");
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(request.getParameter("sid"));
        userAdmin.setUid(request.getParameter("uid"));
        userAdmin.setFeeid(request.getParameter("feeId"));

        List<CallRate> callRates = callRateService.selectCallRateList(userAdmin);
        if(callRates.size()>0){
           return callRates.get(0);
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
    @RequestMapping(value = "pageCallRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageCallRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----pageCallRateUnion start-------");
        return callRateService.pageCallRateUnion(page);
    }

    /**
     * udpateCallRate 修改费率配置信息
     * @param request
     * @param callRate
     * @return
     */
    @RequestMapping(value = "udpateCallRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpateCallRate(HttpServletRequest request, CallRate callRate){
        logger.info("----udpateCallRate start-------");
        Map<String,String> map = new HashMap<String,String>();
        map.put("status","1");
        if(callRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(callRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            CallRate beforCallRate = callRateService.findCallRateByFeeId(callRate.getFeeid());

            callRateService.updateCallRate(callRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");


            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(callRate.getStartDate() != null && callRate.getEndDate() != null){
                beforContent += "</br>有效期：永久有效";
                afterConter += "</br>有效期："+callRate.getStartDate()+"至"+callRate.getEndDate();
            }
            if(callRate.getForever() != null && callRate.getForever() == true){
                beforContent += "</br>有效期："+beforCallRate.getStartDate()+"至"+beforCallRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }
            if(callRate.getCallinDiscount() != null){
                beforContent += "</br>呼入折扣率："+beforCallRate.getCallinDiscount()/10+"%";
                afterConter += "</br>呼入折扣率："+callRate.getCallinDiscount()/10+"%";
            }
            if(callRate.getCalloutDiscount() != null){
                beforContent += "</br>呼出折扣率："+beforCallRate.getCalloutDiscount()/10+"%";
                afterConter += "</br>呼出折扣率："+callRate.getCalloutDiscount()/10+"%";
            }

            if(callRate.getRelayCnt()!=null){
                beforContent += "</br>专线月租费："+beforCallRate.getRelayCnt()+"条";
                afterConter += "</br>专线月租费："+callRate.getRelayCnt()+"条";
            }

            LogUtil.log("智能云调度费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----udpateCallRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }

}
