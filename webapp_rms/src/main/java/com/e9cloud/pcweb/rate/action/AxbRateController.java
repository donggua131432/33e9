package com.e9cloud.pcweb.rate.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.AxbRateService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixin on 2017/4/18.
 */
@Controller
@RequestMapping("/axbRate")
public class AxbRateController  extends BaseController {
    @Autowired
    private AxbRateService axbRateService;
    @Autowired
    private UserAdminService userAdminService;



    @RequestMapping(method = RequestMethod.GET)
    public String showAxbRateList(){
        return "rate/axbRateList";
    }

    /**
     * 获取费率列表信息
     * @return
     */
    @RequestMapping("/showAxbRateList")
    public String showAxbRateList(Model model){
        logger.info("----showRestRateList start-------");
        AxbRate axbRate = axbRateService.findAxbRateByFeeId("0");
        model.addAttribute("axbRate", axbRate);
        return "rate/axbRateList";
    }


    /**
     * 添加费率配置信息
     * @return
     */
    @RequestMapping(value = "/addAxbRate", method = RequestMethod.GET)
    public String addAxbRate(Model model){
        logger.info("----AxbRateController addAxbRate start-------");
        AxbRate axbRate = axbRateService.findAxbRateByFeeId("0");
        model.addAttribute("axbRate", axbRate);
        return "rate/addAxbRate";
    }


    /**
     *  虚拟小号  费率配置信息
     * @param request
     * @param axbRate
     * @param sid
     * @return
     */
    @RequestMapping(value = "submitAxbRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> submitAxbRate(HttpServletRequest request, AxbRate axbRate, String sid){
        logger.info("----submitAxbRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setSid(sid);
        UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);

        if(resultUserAdmin == null){
            map.put("info","虚拟小号费率ID不存在！");
        }else{
            AxbRate resultAxbRate = axbRateService.findAxbRateByFeeId(resultUserAdmin.getFeeid());
            if(resultAxbRate == null){
                logger.info("----insert AxbRate info-------");
                axbRate.setFeeid(resultUserAdmin.getFeeid());

                axbRateService.saveAxbRate(axbRate);
                map.put("status","0");
                map.put("info","虚拟小号费率信息添加成功！");

            }else{
                map.put("info","已配置虚拟小号费率信息，无法添加新的配置！");
            }
        }

        return map;
    }




    /**
     * 获取隐私小号相关信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "getAxbRateDetail", method = RequestMethod.GET)
    public String getAxbRateDetail(HttpServletRequest request,Model model){
        logger.info("----getAxbRateDetail start-------");
        model.addAttribute("standardRate", axbRateService.findAxbRateByFeeId("0"));

        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setFeeid(request.getParameter("feeid"));
        List<AxbRate> axbRate = axbRateService.selectAxbRateList(userAdmin);
        if(axbRate.size()>0){
            model.addAttribute("axbRate", axbRate.get(0));
        }
        return "/rate/axbRateDetail";
    }


    /**
     * 分页查询费率信息
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pageAxbRateUnion", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageAxbRateUnion(Page page, HttpServletRequest request) throws Exception {
        logger.info("----pageAxbRateUnion start-------");
        return axbRateService.pageAxbRateUnion(page);
    }



    /**
     * udpateRestRate 修改费率配置信息
     * @param request
     * @param axbRate
     * @return
     */
    @RequestMapping(value = "udpateAxbRate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> udpateAxbRate(HttpServletRequest request, AxbRate axbRate){
        logger.info("----udpateAxbRate start-------");
        Map map = new HashMap<String,String>();
        map.put("status","1");
        if(axbRate.getFeeid() != null){
            UserAdmin userAdmin = new UserAdmin();
            userAdmin.setFeeid(axbRate.getFeeid());
            UserAdmin resultUserAdmin = userAdminService.getUserAdmin(userAdmin);
            AuthenCompany authenCompanyAgr = new AuthenCompany();
            authenCompanyAgr.setUid(resultUserAdmin.getUid());

            axbRateService.updateAxbRate(axbRate);
            map.put("status","0");
            map.put("info","更新数据信息成功！");

        }else{
            map.put("info","更新数据信息失败！");
        }
        return map;
    }
}
