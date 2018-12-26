package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.RestRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.RestRateService;
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
 *RestRateController负责回拨Rest（专线语音）费率相关的业务控制，
 *
 * Created by DuKai on 2016/1/28.
 *
 */
@Controller
@RequestMapping(value = "/restRate")
public class RestRateController extends BaseController {

    @Autowired
    private RestRateService restRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    @RequestMapping(method = RequestMethod.GET)
    public String showRestRateList(){
        return "rate/restRateList";
    }

    /**
     * 获取回拨Rest（专线语音）费率列表信息
     * @return
     */
    @RequestMapping("/showRestRateList")
    public String showRestRateList(Model model){
        logger.info("----showRestRateList start-------");
        RestRate restRate = restRateService.findRestRateByFeeId("0");
        model.addAttribute("restRate", restRate);
        return "rate/restRateList";
    }


    /**
     * 添加回拨Rest（专线语音）费率配置信息
     * @return
     */
    @RequestMapping(value = "addRestRate", method = RequestMethod.GET)
    public String addFeeRate(HttpServletRequest request, Model model){
        logger.info("----addRestRate start-------");
        RestRate restRate = restRateService.findRestRateByFeeId("0");
        //System.out.println(JSonUtils.toJSon(restRate));
        model.addAttribute("restRate", restRate);
        return "rate/addRestRate";
    }

    /**
     * 添加回拨Rest（专线语音）费率配置信息
     * @param request
     * @param restRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitRestRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitRestRate(HttpServletRequest request, RestRate restRate, String sid){
        logger.info("----submitRestRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","专线语音费率ID不存在！");
        }else{
            RestRate resultRestRate = restRateService.findRestRateByFeeId(resultUserAdmin.getFeeid());
            if(resultRestRate == null){
                logger.info("----insert RestRate info-------");
                restRate.setFeeid(resultUserAdmin.getFeeid());
                restRateService.saveRestRate(restRate);
                map.put("status","0");
                map.put("info","专线语音费率信息添加成功！");

                //记录操作日志
                AuthenCompany authenCompanyAgr = new AuthenCompany();
                authenCompanyAgr.setUid(resultUserAdmin.getUid());
                AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
                String content ="";
                if(authenCompany.getName()!=null){
                    content += "客户名称："+authenCompany.getName();
                }
                if(restRate.getStartDate() != null && restRate.getEndDate() != null){
                    content += "</br>有效期："+restRate.getStartDate()+"至"+restRate.getEndDate();
                }
                if(restRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(restRate.getFeeMode() != null){
                    content += "</br>计费方式："+restRate.getFeeMode();
                }
                if(restRate.getRestaDiscount() != null){
                    content += "</br>A路通话折扣率："+restRate.getRestaDiscount()/10+"%";
                }
                if(restRate.getRestDiscount() != null){
                    content += "</br>B路通话折扣率："+restRate.getRestDiscount()/10+"%";
                }
                if(restRate.getRecordingDiscount() != null){
                    content += "</br>录音折扣率："+restRate.getRecordingDiscount()/10+"%";
                }
                LogUtil.log("新增专线语音费率配置",content, LogType.INSERT);
                logger.info("----insert RestRate info success-------");
            }else{
                map.put("info","已配置专线语音费率信息，无法添加新的配置！");
            }
        }

        return map;
    }

    /**
     * 获取回拨Rest相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getRestRateDetail", method = RequestMethod.GET)
    public String getRestRateDetail(HttpServletRequest request,Model model){
        logger.info("----getRestRateDetail start-------");
        model.addAttribute("standardRate", restRateService.findRestRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<RestRate> restRates = restRateService.selectRestRateList(userAdmin);
        if(restRates.size()>0){
            model.addAttribute("restRate", restRates.get(0));
        }
        return "/rate/restRateDetail";
    }

    /**
     * 联表查询回拨Rest(专线语音)费率信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getRestRateUnion")
    @ResponseBody
    public RestRate getRestRateUnion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("----getRestRateUnion start-------");
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(request.getParameter("sid"));
        userAdmin.setUid(request.getParameter("uid"));
        userAdmin.setFeeid(request.getParameter("feeId"));

        List<RestRate> restRates = restRateService.selectRestRateList(userAdmin);
        if(restRates.size()>0){
            return restRates.get(0);
        }else{
            return null;
        }
    }


    /**
     * 分页查询专线语音费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pageRestRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageRestRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----pageRestRateUnion start-------");
        return restRateService.pageRestRateUnion(page);
    }

    /**
     * udpateRestRate 修改费率配置信息
     * @param request
     * @param restRate
     * @return
     */
    @RequestMapping(value = "udpateRestRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpateRestRate(HttpServletRequest request, RestRate restRate){
        logger.info("----udpateRestRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");
        if(restRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(restRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            RestRate beforRestRate = restRateService.findRestRateByFeeId(restRate.getFeeid());

            restRateService.updateRestRate(restRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");

            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(restRate.getStartDate() != null && restRate.getEndDate() != null){
                beforContent += "</br>有效期：永久有效";
                afterConter += "</br>有效期："+restRate.getStartDate()+"至"+restRate.getEndDate();
            }
            if(restRate.getForever() != null && restRate.getForever() == true){
                beforContent += "</br>有效期："+beforRestRate.getStartDate()+"至"+beforRestRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }
            if(restRate.getRestaDiscount() != null){
                beforContent += "</br>A路通话折扣率："+beforRestRate.getRestaDiscount()/10+"%";
                afterConter += "</br>A路通话折扣率："+restRate.getRestaDiscount()/10+"%";
            }
            if(restRate.getRestDiscount() != null){
                beforContent += "</br>B路通话折扣率："+beforRestRate.getRestDiscount()/10+"%";
                afterConter += "</br>B路通话折扣率："+restRate.getRestDiscount()/10+"%";
            }
            if(restRate.getRecordingDiscount() != null){
                beforContent += "</br>录音折扣率："+beforRestRate.getRecordingDiscount()/10+"%";
                afterConter += "</br>录音折扣率："+restRate.getRecordingDiscount()/10+"%";
            }

            LogUtil.log("专线语音费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----udpateRestRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }

}
