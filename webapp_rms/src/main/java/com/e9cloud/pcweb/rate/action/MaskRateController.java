package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.AuthenCompany;
import com.e9cloud.mybatis.domain.MaskRate;
import com.e9cloud.mybatis.domain.UserAdmin;
import com.e9cloud.mybatis.service.AuthenCompanyService;
import com.e9cloud.mybatis.service.MaskRateService;
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
 * Created by dukai on 2016/6/1.
 */
@Controller
@RequestMapping("/maskRate")
public class MaskRateController extends BaseController{

    @Autowired
    private MaskRateService maskRateService;

    @Autowired
    private UserAdminService userAdminService;

    @Autowired
    private AuthenCompanyService authenCompanyService;

    /**
     * 私密专线费率列表信息
     * @return
     */
    @RequestMapping(value = "/showMaskRateList")
    public String showMaskRateList(Model model){
        logger.info("----MaskRateController showMaskRateList start-------");
        MaskRate rate = maskRateService.findMaskRateByFeeId("0");
        model.addAttribute("maskRate", rate);
        return "rate/maskRateList";
    }


    /**
     * 私密专线费率配置信息
     * @return
     */
    @RequestMapping(value = "addMaskRate", method = RequestMethod.GET)
    public String addMaskRate(HttpServletRequest request, Model model){
        logger.info("----MaskRateController addMaskRate start-------");
        MaskRate rate = maskRateService.findMaskRateByFeeId("0");
        model.addAttribute("maskRate", rate);
        return "rate/addMaskRate";
    }

    /**
     * 添加私密专线费率配置信息
     * @param maskRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitMaskRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitMaskRate(MaskRate maskRate, String sid){
        logger.info("----MaskRateController submitMaskRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","费率ID不存在！");
        }else{
            MaskRate resultMaskRate = maskRateService.findMaskRateByFeeId(resultUserAdmin.getFeeid());
            if(resultMaskRate == null){
                logger.info("----MaskRateController insert MaskRate info-------");
                maskRate.setFeeid(resultUserAdmin.getFeeid());
                maskRateService.saveMaskRate(maskRate);
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
                if(maskRate.getStartDate() != null && maskRate.getEndDate() != null){
                    content += "</br>有效期："+maskRate.getStartDate()+"至"+maskRate.getEndDate();
                }
                if(maskRate.getForever() != null){
                    content += "</br>有效期：永久有效";
                }
                if(maskRate.getFeeMode() != null){
                    content += "</br>计费方式："+maskRate.getFeeMode();
                }
                if(maskRate.getMaskaDiscount() != null){
                    content += "</br>隐私回拨A路折扣率："+maskRate.getMaskaDiscount()/10+"%";
                }
                if(maskRate.getMaskbDiscount() != null){
                    content += "</br>隐私回拨B路折扣率："+maskRate.getMaskbDiscount()/10+"%";
                }
                if(maskRate.getRecordingDiscount() != null){
                    content += "</br>隐私回拨录音折扣率："+maskRate.getRecordingDiscount()/10+"%";
                }
                if(maskRate.getCallbackDiscount() != null){
                    content += "</br>隐私回呼折扣率："+maskRate.getCallbackDiscount()/10+"%";
                }
                if(maskRate.getRecordingCallbackDiscount() != null){
                    content += "</br>隐私回呼录音折扣率："+maskRate.getRecordingCallbackDiscount()/10+"%";
                }

                if(maskRate.getRent() != null){
                    content += "</br>号码占用月租费："+maskRate.getRent()+"元";
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
    @RequestMapping(value = "getMaskRateDetail", method = RequestMethod.GET)
    public String getMaskRateDetail(HttpServletRequest request,Model model){
        logger.info("----MaskRateController getMaskRateDetail start-------");
        model.addAttribute("standardRate", maskRateService.findMaskRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<MaskRate> maskRates = maskRateService.selectMaskRateList(userAdmin);
        if(maskRates.size()>0){
            model.addAttribute("maskRate", maskRates.get(0));
        }
        return "/rate/maskRateDetail";
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
        logger.info("----MaskRateController getMaskRateUnion start-------");
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
    @RequestMapping(value = "pageMaskRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageMaskRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----MaskRateController pageMaskRateUnion start-------");
        return maskRateService.pageMaskRateUnion(page);
    }

    /**
     * udpateMaskRate 修改费率配置信息
     * @param request
     * @param maskRate
     * @return
     */
    @RequestMapping(value = "udpateMaskRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpateMaskRate(HttpServletRequest request, MaskRate maskRate){
        logger.info("----MaskRateController udpateMaskRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");
        if(maskRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(maskRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());
            AuthenCompany authenCompany = authenCompanyService.getAuthenCompany(authenCompanyAgr);
            MaskRate beforMaskRate = maskRateService.findMaskRateByFeeId(maskRate.getFeeid());

            maskRateService.updateMaskRate(maskRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");


            //记录操作日志
            String beforContent ="";
            String afterConter="";
            if(authenCompany.getName()!=null){
                beforContent += "客户名称："+authenCompany.getName();
                afterConter += "客户名称："+authenCompany.getName();
            }
            if(maskRate.getStartDate() != null && maskRate.getEndDate() != null){
                beforContent += "</br>有效期：永久有效";
                afterConter += "</br>有效期："+maskRate.getStartDate()+"至"+maskRate.getEndDate();
            }
            if(maskRate.getForever() != null && maskRate.getForever() == true){
                beforContent += "</br>有效期："+beforMaskRate.getStartDate()+"至"+beforMaskRate.getEndDate();
                afterConter += "</br>有效期：永久有效";
            }

            if(maskRate.getMaskaDiscount() != null){
                beforContent += "</br>隐私回拨A路折扣："+beforMaskRate.getMaskaDiscount()/10+"%";
                afterConter += "</br>隐私回拨A路折扣率："+maskRate.getMaskaDiscount()/10+"%";
            }
            if(maskRate.getMaskbDiscount() != null){
                beforContent += "</br>隐私回拨B路折扣率："+beforMaskRate.getMaskbDiscount()/10+"%";
                afterConter += "</br>隐私回拨B路折扣率："+maskRate.getMaskbDiscount()/10+"%";
            }

            if(maskRate.getRecordingDiscount() != null){
                beforContent += "</br>隐私回拨录音折扣率："+beforMaskRate.getRecordingDiscount()/10+"%";
                afterConter += "</br>隐私回拨录音折扣率："+maskRate.getRecordingDiscount()/10+"%";
            }

            if(maskRate.getCallbackDiscount() != null){
                beforContent += "</br>隐私回呼折扣率："+beforMaskRate.getCallbackDiscount()/10+"%";
                afterConter += "</br>隐私回呼折扣率："+maskRate.getCallbackDiscount()/10+"%";
            }

            if(maskRate.getRecordingCallbackDiscount() != null){
                beforContent += "</br>隐私回呼录音折扣率："+beforMaskRate.getRecordingCallbackDiscount()/10+"%";
                afterConter += "</br>隐私回呼录音折扣率："+maskRate.getRecordingCallbackDiscount()/10+"%";
            }


            if(maskRate.getRent() != null){
                beforContent += "</br>号码占用月租费："+beforMaskRate.getRent()+"元";
                afterConter += "</br>号码占用月租费："+maskRate.getRent()+"元";
            }

            LogUtil.log("私密专线费率配置详情修改","修改前：</br>"+beforContent+"</br>修改后：</br>"+afterConter,LogType.UPDATE);

            logger.info("----udpateMaskRate success-------");
        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }
}
