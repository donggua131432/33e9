package com.e9cloud.pcweb.master.action;

import com.e9cloud.pcweb.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页相关controller
 *
 * Created by Administrator on 2016/1/18.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

    /**
     * 请求首页
     * @return 返回首页
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return redirect + "auth";
    }

    /**
     * 案例教育
     * @return 返回首页
     */
    @RequestMapping(value = "case/education", method = RequestMethod.GET)
    public String caseEducation(){
        return "case/education_case";
    }

    /**
     * 案例地产
     * @return 返回首页
     */
    @RequestMapping(value = "case/estate", method = RequestMethod.GET)
    public String caseEstate(){
        return "case/estate_case";
    }

    /**
     * 案例金融
     * @return 返回首页
     */
    @RequestMapping(value = "case/financial", method = RequestMethod.GET)
    public String caseFinancial(){
        return "case/financial_case";
    }

    /**
     * 案例物流
     * @return 返回首页
     */
    @RequestMapping(value = "case/logistics", method = RequestMethod.GET)
    public String caseLogistics(){
        return "case/logistics_case";
    }

    /**
     * 产品分流页
     * @return 返回首页
     */
    @RequestMapping(value = "product/index", method = RequestMethod.GET)
    public String product(){
        return "product/product";
    }

    /**
     * 产品-回拨rest
     * @return 返回首页
     */
    @RequestMapping(value = "product/hbrest", method = RequestMethod.GET)
    public String productHbrest(){
        return "product/product_hbrest";
    }

    /**
     *  专号通
     * @return 返回首页
     */
    @RequestMapping(value = "product/designed", method = RequestMethod.GET)
    public String productDesigned(){
        return "product/product_designed";
    }

    /**
     * 产品-95xxx
     * @return 返回首页
     */
    @RequestMapping(value = "product/znydd", method = RequestMethod.GET)
    public String productZnydd(){
        return "product/product_znydd";
    }

    /**
     * 价格页面
     * @return 返回首页
     */
    @RequestMapping(value = "price/index", method = RequestMethod.GET)
    public String price(){
        return "price/price";
    }

    /**
     * API
     * @return 返回首页
     */
    @RequestMapping(value = "api/index", method = RequestMethod.GET)
    public String api(){
        return "api/api";
    }

    /**
     * API
     * @return 回调通知接口
     */
    @RequestMapping(value = "api/CallbackInterface", method = RequestMethod.GET)
    public String apiCallbackInterface(){
        return "api/API_CallbackInterface";
    }


    /**
     * API
     * @return 实时在线通话数查询接口
     */
    @RequestMapping(value = "api/checkInterface", method = RequestMethod.GET)
    public String apiCheckInterface(){
        return "api/API_checkInterface";
    }


    /**
     * API
     * @return 专线语音接口
     */
    @RequestMapping(value = "api/dedicatedVoice", method = RequestMethod.GET)
    public String apiDedicatedVoice(){
        return "api/API_dedicatedVoice";
    }

    /**
     * API
     * @return 全局错误码表
     */
    @RequestMapping(value = "api/globalErrorCode", method = RequestMethod.GET)
    public String apiGlobalErrorCode(){
        return "api/API_globalErrorCode";
    }

    /**
     * API
     * @return 取消专线语音通话接口
     */
    @RequestMapping(value = "api/CancelCallback", method = RequestMethod.GET)
    public String CancelCallback() {
        return "api/API_CancelCallback";
    }

    /**
     * API
     * @return 录音及下载接口
     */
    @RequestMapping(value = "api/recordDownLoad", method = RequestMethod.GET)
    public String apiRecordDownLoad(){
        return "api/API_recordDownLoad";
    }

    /**
     * API
     * @return REST API接人介绍
     */
    @RequestMapping(value = "api/RESTInterface", method = RequestMethod.GET)
    public String apiRESTInterface(){
        return "api/API_RESTInterface";
    }

    /**
     * API
     * @return 开发者账户使用指南
     */
    @RequestMapping(value = "api/UseGuide", method = RequestMethod.GET)
    public String apiUseGuide(){
        return "api/API_UseGuide";
    }

    /**
     * API
     * @return 开放接口使用指南
     */
    @RequestMapping(value = "api/ManagementGuidelines", method = RequestMethod.GET)
    public String apiManagementGuidelines(){
        return "api/API_ManagementGuidelines";
    }

    /**
     * API
     * @return 平台审核规范
     */
    @RequestMapping(value = "api/PlatformAudit", method = RequestMethod.GET)
    public String apiPlatformAudit(){
        return "api/API_PlatformAudit";
    }

    /**
     * API
     * @return 平台服务标准
     */
    @RequestMapping(value = "api/PlatformServices", method = RequestMethod.GET)
    public String apiPlatformServices(){
        return "api/API_PlatformServices";
    }

    /**
     * API
     * @return 应用映射号码查询接口
     */
    @RequestMapping(value = "api/AppMapNumCheckInterface", method = RequestMethod.GET)
    public String AppMapNumCheckInterface() {
        return "api/API_AppMapNumCheckInterface";
    }

    /**
     * API
     * @return 专号通回调通知接口
     */
    @RequestMapping(value = "api/specialCallbackINoticenterface", method = RequestMethod.GET)
    public String specialCallbackINoticenterface() {
        return "api/API_specialCallbackINoticenterface";
    }

    /**
     * API
     * @return 专号通接口
     */
    @RequestMapping(value = "api/specialInterface", method = RequestMethod.GET)
    public String specialInterface() {
        return "api/API_specialInterface";
    }

    /**
     * API
     * @return 专号通号码申请接口
     */
    @RequestMapping(value = "api/specialNumAppleInterface", method = RequestMethod.GET)
    public String specialNumAppleInterface() {
        return "api/API_specialNumAppleInterface";
    }

    /**
     * API
     * @return 专号通号码查询接口
     */
    @RequestMapping(value = "api/specialNumCheckInterface", method = RequestMethod.GET)
    public String specialNumCheckInterface() {
        return "api/API_specialNumCheckInterface";
    }

    /**
     * API
     * @return 专号通号码释放接口
     */
    @RequestMapping(value = "api/specialNumReleaseInterface", method = RequestMethod.GET)
    public String specialNumReleaseInterface() {
        return "api/API_specialNumReleaseInterface";
    }

    /**
     * API
     * @return 通话记录查询接口
     */
    @RequestMapping(value = "api/calledRecordCheckRest", method = RequestMethod.GET)
    public String calledRecordCheckRest() {
        return "api/API_calledRecordCheckRest";
    }

    /**
     * 注册协议
     * @return 返回首页
     */
    @RequestMapping(value = "statement", method = RequestMethod.GET)
    public String regStatement(){
        return "register/home2/service_terms";
    }

    /**
     * 交流页面/关于我们
     * @return 返回首页
     */
    @RequestMapping(value = "aboutas", method = RequestMethod.GET)
    public String aboutas(){
        return "commun/About_us";
    }

    /**
     * 交流页面/联系我们
     * @return 返回首页
     */
    @RequestMapping(value = "contactus", method = RequestMethod.GET)
    public String contactus(){
        return "commun/Contact_us";
    }
}
