package com.e9cloud.pcweb.relay.action;

import com.e9cloud.core.common.LogType;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.LogUtil;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.RelayBussinessAreaCodeService;
import com.e9cloud.mybatis.service.RelayBussinessZeroService;
import com.e9cloud.mybatis.service.RelayService;
import com.e9cloud.mybatis.service.impl.DicDataServiceImpl;
import com.e9cloud.pcweb.BaseController;
import org.apache.poi.util.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import org.springframework.util.StringUtils;

/**
 * 中继配置相关
 * Created by Administrator on 2016/7/18.
 */
@Controller
@RequestMapping("relay")
public class RelayController extends BaseController {

    @Autowired
    private RelayService relayService;

    @Autowired
    private IDicDataService iDicDataService;

    @Autowired
    private RelayBussinessAreaCodeService relayBussinessAreaCodeService;

    @Autowired
    private RelayBussinessZeroService relayBussinessZeroService;

    @Override
    public HttpServletResponse downFile(HttpServletResponse response, File file, String fileName, boolean delFile) throws Exception {
        return super.downFile(response, file, fileName, delFile);
    }

    // 中继配置列表页面
    @RequestMapping(value = "list")
    public String list() {
        return "relay/relayList";
    }

    // 分页查询中继信息
    @RequestMapping(value = "pageRelay", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageRelay(Page page) {
        return relayService.pageRelay(page);
    }

    // 展示新增中继页面
    @RequestMapping(value = "toAddRelay")
    public String toAddRelay(Model model) {
        List<DicData> relayBus = iDicDataService.findDicListByType("relayBus");
        model.addAttribute("relayBus", relayBus);
        return "relay/relayAdd";
    }


    // 添加中继配置信息
    @RequestMapping(value = "addRelay",method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addRelay(HttpServletRequest request, SipBasic sipBasic, String[] sipBus) {

        String ip1 = request.getParameter("ip1")!=null?request.getParameter("ip1").trim():"";
        String port1 = request.getParameter("port1")!=null?request.getParameter("port1").trim():"";
        sipBasic.setIpport1(!"".equals(ip1) && !"".equals(port1) ? ip1+":"+port1 : "");

        String ip2 = request.getParameter("ip2")!=null?request.getParameter("ip2").trim():"";
        String port2 = request.getParameter("port2")!=null?request.getParameter("port2").trim():"";
        sipBasic.setIpport2(!"".equals(ip2) && !"".equals(port2) ? ip2+":"+port2 : "");

        String ip3 = request.getParameter("ip3")!=null?request.getParameter("ip3").trim():"";
        String port3 = request.getParameter("port3")!=null?request.getParameter("port3").trim():"";
        sipBasic.setIpport3(!"".equals(ip3) && !"".equals(port3) ? ip3+":"+port3 : "");

        String ip4 = request.getParameter("ip4")!=null?request.getParameter("ip4").trim():"";
        String port4 = request.getParameter("port4")!=null?request.getParameter("port4").trim():"";
        sipBasic.setIpport4(!"".equals(ip4) && !"".equals(ip4) ? ip4+":"+port4 : "");

        sipBasic.setSipBusiness(Tools.bitToLong(sipBus));
        sipBasic.setCreateTime(new Date());
        relayService.addRelayToCBTask(sipBasic.getId(), sipBasic); // 中继修改是添加cb任务
        relayService.saveRelayInfo(sipBasic);
        return new JSonMessage("ok", "添加中继成功！");
    }

    // 校验唯一性
    @RequestMapping("checkUnique")
    @ResponseBody
    public JSonMessage checkUnique(SipBasic sipBasic){
        JSonMessage jSonMessage = new JSonMessage();
        long l = relayService.countSipBasicByNum(sipBasic);
        if (l == 0) {
            jSonMessage.setCode("ok");
        }
        return jSonMessage;
    }
    public static void main(String[] args) {

        System.out.println(Long.toHexString(0));
    }

    // 修改中继信息时候校验唯一性 (排除自身id  和其余数据校验唯一性)
    @RequestMapping("editcheckUnique")
    @ResponseBody
    public JSonMessage editcheckUnique(HttpServletRequest request,SipBasic sipBasic){
        JSonMessage jSonMessage = new JSonMessage();
        String id = request.getParameter("id");
        sipBasic.setId(Integer.parseInt(id));
        long l = relayService.editcountSipBasicByNum(sipBasic);
        if (l == 0) {
            jSonMessage.setCode("ok");
        }
        return jSonMessage;
    }

    /**
     * 删除中继
     * @param request
     * @return
     */
    @RequestMapping("/deleteRelay")
    @ResponseBody
    public JSonMessageSubmit deleteRelay(HttpServletRequest request){

        SipBasic sipBasic = new SipBasic();
        sipBasic.setId(Integer.parseInt(request.getParameter("id")));

        //判断是否还存在此条数据
        long a = relayService.countSipBasicByNum(sipBasic);

        //判断中继是否正在被引用
        long flag = relayService.flagSipBasic(sipBasic);

        if(a == 1){
            if (flag == 1){
                return new JSonMessageSubmit("2","中继被引用！");
            }else {
                relayService.addRelayToCBTask(sipBasic.getId(), sipBasic); // 中继修改是添加cb任务

                sipBasic.setStatus("01");
                relayService.updateSipBasicInfo(sipBasic);
                return new JSonMessageSubmit("0","删除数据成功！");
            }

        }else{
            return new JSonMessageSubmit("1","该数据信息已删除！");
        }
    }

    // 修改中继页面
    @RequestMapping(value = "toEditRelayInfo", method = RequestMethod.GET)
    public String toEditRelayInfo(HttpServletRequest request , String id, Model model) {
        //判断中继是否正在被引用
        SipBasic sipBasic1 = new SipBasic();
        sipBasic1.setId(Integer.parseInt(id));
        long flag = relayService.flagSipBasic(sipBasic1);
        model.addAttribute("flag", flag);
        SipBasic sipBasic = relayService.getRelayInfoById(id);
        model.addAttribute("sipBasic", sipBasic);

        //对数据库 10.0.33.39:5080 形式字段进行处理
        /*String a[] = sipBasic.getIpport1().split(":");
        model.addAttribute("ip1", a[0]);
        model.addAttribute("port1", a[1]);
        String a2[] = sipBasic.getIpport2().split(":");
        model.addAttribute("ip2", a2[0]);
        model.addAttribute("port2", a2[1]);

        String a3[] = sipBasic.getIpport3().split(":");
        model.addAttribute("ip3", a3[0]);
        model.addAttribute("port3", a3[1]);

        String a4[] = sipBasic.getIpport4().split(":");
        model.addAttribute("ip4", a4[0]);
        model.addAttribute("port4", a4[1]);*/
        if(!sipBasic.getIpport1().equals("")){
            String a[] = sipBasic.getIpport1().split(":");
            model.addAttribute("ip1", a[0]);
            model.addAttribute("port1", a[1]);
        }

        if(!sipBasic.getIpport2().equals("")){
            String a2[] = sipBasic.getIpport2().split(":");
            model.addAttribute("ip2", a2[0]);
            model.addAttribute("port2", a2[1]);
        }
        if(!sipBasic.getIpport3().equals("")){
            String a3[] = sipBasic.getIpport3().split(":");
            model.addAttribute("ip3", a3[0]);
            model.addAttribute("port3", a3[1]);
        }
        if(!sipBasic.getIpport4().equals("")){
            String a4[] = sipBasic.getIpport4().split(":");
            model.addAttribute("ip4", a4[0]);
            model.addAttribute("port4", a4[1]);
        }
        //转换为16进制数显示
        model.addAttribute("bus", Tools.decimalToBitStr(sipBasic.getSipBusiness()));

        List<DicData> relayBus = iDicDataService.findDicListByType("relayBus");
        model.addAttribute("relayBus", relayBus);


        return "relay/relay_edit";
    }

    // 修改中继信息
    @RequestMapping(value = "updateRelayInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateRelayInfo(HttpServletRequest request, SipBasic sipBasic, String maxCon, String oldUseType, String oldBusType, String[] sipBus) {

        logger.info("update RelayInfo relayNum is {}", sipBasic.getRelayNum());

        String ip1 = request.getParameter("ip1")!=null?request.getParameter("ip1").trim():"";
        String port1 = request.getParameter("port1")!=null?request.getParameter("port1").trim():"";
        sipBasic.setIpport1(!"".equals(ip1) && !"".equals(port1) ? ip1+":"+port1 : "");

        String ip2 = request.getParameter("ip2")!=null?request.getParameter("ip2").trim():"";
        String port2 = request.getParameter("port2")!=null?request.getParameter("port2").trim():"";
        sipBasic.setIpport2(!"".equals(ip2) && !"".equals(port2) ? ip2+":"+port2 : "");

        String ip3 = request.getParameter("ip3")!=null?request.getParameter("ip3").trim():"";
        String port3 = request.getParameter("port3")!=null?request.getParameter("port3").trim():"";
        sipBasic.setIpport3(!"".equals(ip3) && !"".equals(port3) ? ip3+":"+port3 : "");

        String ip4 = request.getParameter("ip4")!=null?request.getParameter("ip4").trim():"";
        String port4 = request.getParameter("port4")!=null?request.getParameter("port4").trim():"";
        sipBasic.setIpport4(!"".equals(ip4) && !"".equals(port4) ? ip4+":"+port4 : "");

        String sipBusiness_16 = request.getParameter("sipBusiness_16");
        if (!"".equals(sipBusiness_16)&&null!=sipBusiness_16){
            sipBasic.setSipBusiness(Long.parseLong(sipBusiness_16, 16));
        }
        if (!"00".equals(sipBasic.getUseType())){
            sipBasic.setBusType("");
        }

        long flag = relayService.flagSipBasic(sipBasic);
        if (flag > 0 && (Tools.isNotNullStr(oldUseType)) && (!Tools.eqStr(sipBasic.getUseType(), oldUseType) || !Tools.eqStr(sipBasic.getBusType(), oldBusType))) { // 中继已经被占用，不能修改中继类型
            return new JSonMessage("error", "中继已被占用，类型和业务类型不能被修改");
        }
        if (Tools.isNullStr(maxCon)){
            sipBasic.setMaxConcurrent(65535);
        }else {
            sipBasic.setMaxConcurrent(Integer.parseInt(maxCon));
        }
        sipBasic.setSipBusiness(Tools.bitToLong(sipBus));
        sipBasic.setCreateTime(new Date());
        relayService.addRelayToCBTask(sipBasic.getId(), sipBasic); // 中继修改是添加cb任务
        relayService.updateSipBasicInfo(sipBasic);
        return new JSonMessage("ok", "修改中继信息成功！");
    }

    // 查看中继信息
    @RequestMapping(value = "toShowRelayInfo", method = RequestMethod.GET)
    public String toShowRelayInfo(String id, Model model) {
        SipBasic sipBasic = relayService.getRelayInfoById(id);
        model.addAttribute("sipBasic", sipBasic);
        //对数据库 10.0.33.39:5080 形式字段进行处理
        if(!sipBasic.getIpport1().equals("")){
            String a[] = sipBasic.getIpport1().split(":");
            model.addAttribute("ip1", a[0]);
            model.addAttribute("port1", a[1]);
        }

        if(!sipBasic.getIpport2().equals("")){
            String a2[] = sipBasic.getIpport2().split(":");
            model.addAttribute("ip2", a2[0]);
            model.addAttribute("port2", a2[1]);
        }
        if(!sipBasic.getIpport3().equals("")){
            String a3[] = sipBasic.getIpport3().split(":");
            model.addAttribute("ip3", a3[0]);
            model.addAttribute("port3", a3[1]);
        }
        if(!sipBasic.getIpport4().equals("")){
            String a4[] = sipBasic.getIpport4().split(":");
            model.addAttribute("ip4", a4[0]);
            model.addAttribute("port4", a4[1]);
        }

        //转换为16进制数显示
        model.addAttribute("bus", Tools.decimalToBitStr(sipBasic.getSipBusiness()));

        List<DicData> relayBus = iDicDataService.findDicListByType("relayBus");
        model.addAttribute("relayBus", relayBus);

        //转换为16进制数显示
        //model.addAttribute("sipBusiness_16",Long.toHexString(sipBasic.getSipBusiness()));
        return "relay/relay_show";
    }

    /**
     * 中继资源下拉列表
     * @return
     */
    @RequestMapping("getRelaysForRes")
    @ResponseBody
    public List<SipBasic> getRelaysForRes(String useType, String busType, Integer resId) {
        return relayService.getRelaysForRes(useType, busType, resId);
    }

    /**
     * 中继资源下拉列表(呼叫中心代答中继)
     * @return
     */
    @RequestMapping("getRelaysForAnswerTrunk")
    @ResponseBody
    public List<SipBasic> getRelaysForAnswerTrunk() {
        return relayService.getRelaysForAnswerTrunk();
    }


    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(HttpServletRequest request, Model model, Page page) {
        List<Map<String, Object>> totals =  relayService.pageDownloadRelay(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("relayNum",String.valueOf(total.get("relayNum")));
                map.put("relayName", String.valueOf(total.get("relayName")));
                map.put("relayType", String.valueOf(total.get("relayType")));
                map.put("maxConcurrent",String.valueOf(total.get("maxConcurrent")));
                map.put("createTime", String.valueOf(total.get("createTime")));
                map.put("sipUrl", String.valueOf(total.get("sipUrl")));
                map.put("ipport1", String.valueOf(total.get("ipport1")));
                map.put("ipport2", String.valueOf(total.get("ipport2")));
                map.put("ipport3", String.valueOf(total.get("ipport3")));
                map.put("ipport4", String.valueOf(total.get("ipport4")));
                if("".equals(total.get("createTime")) || null == total.get("createTime")){
                    map.put("createTime", "");
                }
                else {
                    map.put("createTime", String.valueOf(total.get("createTime").toString().substring(0,19)));
                }

                map.put("relayType", String.valueOf(total.get("relayType")));

                if("00".equals(total.get("relayType"))){
                    map.put("relayType","闭塞");

                }
                if("01".equals(total.get("relayType"))){
                    map.put("relayType","入中继");
                }
                if("10".equals(total.get("relayType"))){
                    map.put("relayType","出中继");
                }
                if("11".equals(total.get("relayType"))){
                    map.put("relayType","双向中继");
                }
                list.add(map);
            }
        }
        List<String> titles = new ArrayList<String>();

        titles.add("中继名称");
        titles.add("中继ID");
        titles.add("中继方向");
        titles.add("并发数");
        titles.add("外域对端IP和端口");
        titles.add("外域本端IP和端口");
        titles.add("内域对端IP和端口");
        titles.add("内域本端IP和端口");
        titles.add("SIP-URI域名");
        titles.add("创建时间");

        List<String> columns = new ArrayList<String>();

        columns.add("relayName");
        columns.add("relayNum");
        columns.add("relayType");
        columns.add("maxConcurrent");
        columns.add("ipport1");
        columns.add("ipport2");
        columns.add("ipport3");
        columns.add("ipport4");
        columns.add("sipUrl");
        columns.add("createTime");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "中继配置列表");
        map.put("excelName","中继配置列表");

        Map<String, Object> params = page.getParams();
        Object timemin = Tools.formatDate(page.getTimemin(),"yyyy-MM-dd" );
        Object timemax = Tools.formatDate(page.getTimemax(),"yyyy-MM-dd" );
        LogUtil.log("中继配置列表", "中继配置列表导出一条记录。导出内容的查询条件为：时间范围为：" + timemin +" 至 "+ timemax +  " 中继名称： "
                + params.get("relayName") + "，中继ID：" + params.get("relayNum"), LogType.UPDATE);
        return new ModelAndView(new POIXlsView(), map);
    }

    // ECC配置页
    @RequestMapping(value = "toEccConfig", method = RequestMethod.GET)
    public String toEccConfig(String id, Model model) {
        SipBasic sipBasic = relayService.getRelayInfoById(id);
        model.addAttribute("sipBasic", sipBasic);

        List<RelayBusinessAreaCode> relayBusinessAreaCodeList = relayBussinessAreaCodeService.findListByRelayId(Integer.parseInt(id));
        List<RelayBusinessZero> relayBusinessZeroList = relayBussinessZeroService.findListByRelayId(Integer.parseInt(id));

        setRelayBussinessAreaCode(model,relayBusinessAreaCodeList);
        setRelayBussinessZero(model,relayBusinessZeroList);

        return "relay/relay_ecc";
    }
    public void setRelayBussinessAreaCode(Model model,List<RelayBusinessAreaCode> relayBusinessAreaCodeList){
        RelayBusinessAreaCode relayBusinessAreaCode1 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode2 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode3 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode4 = new RelayBusinessAreaCode();

        Byte opertion = new Byte("2");
        relayBusinessAreaCode1.setOpertion(opertion);
        relayBusinessAreaCode1.setContent("1");
        relayBusinessAreaCode2.setOpertion(opertion);
        relayBusinessAreaCode2.setContent("1");
        relayBusinessAreaCode3.setOpertion(opertion);
        relayBusinessAreaCode3.setContent("1");
        relayBusinessAreaCode4.setOpertion(opertion);
        relayBusinessAreaCode4.setContent("1");

        for(RelayBusinessAreaCode relayBusinessAreaCode : relayBusinessAreaCodeList){
            if(relayBusinessAreaCode.getDirection()==0 && relayBusinessAreaCode.getNumType()==0){ //内域拨打外域-主叫 本地固话号码
                relayBusinessAreaCode1 =relayBusinessAreaCode;
            }else if(relayBusinessAreaCode.getDirection()==0 && relayBusinessAreaCode.getNumType()==1){ //内域拨打外域-被叫 本地固话号码
                relayBusinessAreaCode2 =relayBusinessAreaCode;
            }else if(relayBusinessAreaCode.getDirection()==1 && relayBusinessAreaCode.getNumType()==0){ //外域拨打内域-主叫 本地固话号码
                relayBusinessAreaCode3 =relayBusinessAreaCode;
            }else if(relayBusinessAreaCode.getDirection()==1 && relayBusinessAreaCode.getNumType()==1){ //外域拨打内域-被叫 本地固话号码
                relayBusinessAreaCode4 =relayBusinessAreaCode;
            }
        }

        model.addAttribute("relayBusinessAreaCode1", relayBusinessAreaCode1);
        model.addAttribute("relayBusinessAreaCode2", relayBusinessAreaCode2);
        model.addAttribute("relayBusinessAreaCode3", relayBusinessAreaCode3);
        model.addAttribute("relayBusinessAreaCode4", relayBusinessAreaCode4);
    }
    public void setRelayBussinessZero(Model model,List<RelayBusinessZero> relayBusinessZeroList){
        RelayBusinessZero relayBusinessZero1 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero2 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero3 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero4 = new RelayBusinessZero();

        Byte opertion = new Byte("2");
        relayBusinessZero1.setOpertion(opertion);
        relayBusinessZero2.setOpertion(opertion);
        relayBusinessZero3.setOpertion(opertion);
        relayBusinessZero4.setOpertion(opertion);

        for(RelayBusinessZero relayBusinessZero : relayBusinessZeroList){
            //内域拨打外域-主叫异地手机号码
            if(relayBusinessZero.getDirection()==0 && relayBusinessZero.getNumType()==0){
                relayBusinessZero1 = relayBusinessZero;
            }else if(relayBusinessZero.getDirection()==0 && relayBusinessZero.getNumType()==1){//内域拨打外域-被叫异地手机号码
                relayBusinessZero2 = relayBusinessZero;
            }else if(relayBusinessZero.getDirection()==1 && relayBusinessZero.getNumType()==0){//外域拨打内域-主叫异地手机号码
                relayBusinessZero3 = relayBusinessZero;
            }else if(relayBusinessZero.getDirection()==1 && relayBusinessZero.getNumType()==1){//外域拨打内域-被叫异地手机号码
                relayBusinessZero4 = relayBusinessZero;
            }
        }
        model.addAttribute("relayBusinessZero1", relayBusinessZero1);
        model.addAttribute("relayBusinessZero2", relayBusinessZero2);
        model.addAttribute("relayBusinessZero3", relayBusinessZero3);
        model.addAttribute("relayBusinessZero4", relayBusinessZero4);
    }

    // 修改ECC配置
    @RequestMapping(value = "updateEccConfig", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateEccConfig(HttpServletRequest request, int[] operator1s, int[] operator2s, int[] operator3s, int[] operator4s) {

        String relayId = request.getParameter("relayId")!=null?request.getParameter("relayId").trim():"";

        //删除区号业务信息
        relayBussinessAreaCodeService.deleteByRelayId(Integer.parseInt(relayId));
        //删除0前缀信息
        relayBussinessZeroService.deleteByRelayId(Integer.parseInt(relayId));

        //保存区号业务信息
        addRelayBusinessAreaCode(request);
        //保存0前缀业务信息
        addRelayBusinessZero(request, operator1s, operator2s, operator3s, operator4s);

        return new JSonMessage("ok", "ECC配置成功！");

    }

    public void addRelayBusinessAreaCode(HttpServletRequest request){

        String relayId = request.getParameter("relayId")!=null?request.getParameter("relayId").trim():"";
        String opertion1 = request.getParameter("opertion1")!=null?request.getParameter("opertion1").trim():"";
        String opertion2 = request.getParameter("opertion2")!=null?request.getParameter("opertion2").trim():"";
        String opertion3 = request.getParameter("opertion3")!=null?request.getParameter("opertion3").trim():"";
        String opertion4 = request.getParameter("opertion4")!=null?request.getParameter("opertion4").trim():"";

        String content1 = request.getParameter("content1")!=null?request.getParameter("content1").trim():"";
        String content2 = request.getParameter("content2")!=null?request.getParameter("content2").trim():"";
        String content3 = request.getParameter("content3")!=null?request.getParameter("content3").trim():"";
        String content4 = request.getParameter("content4")!=null?request.getParameter("content4").trim():"";

        RelayBusinessAreaCode relayBusinessAreaCode1 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode2 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode3 = new RelayBusinessAreaCode();
        RelayBusinessAreaCode relayBusinessAreaCode4 = new RelayBusinessAreaCode();

        if(!StringUtils.isEmpty(opertion1)){
            if(!"2".equals(opertion1)){
                setRelayBusinessAreaCode(relayBusinessAreaCode1,opertion1,1,relayId,content1);
            }
        }

        if(!StringUtils.isEmpty(opertion2)){
            if(!"2".equals(opertion2)){
                setRelayBusinessAreaCode(relayBusinessAreaCode2,opertion2,2,relayId,content2);
            }
        }

        if(!StringUtils.isEmpty(opertion3)){
            if(!"2".equals(opertion3)){
                setRelayBusinessAreaCode(relayBusinessAreaCode3,opertion3,3,relayId,content3);
            }
        }

        if(!StringUtils.isEmpty(opertion4)){
            if(!"2".equals(opertion4)){
                setRelayBusinessAreaCode(relayBusinessAreaCode4,opertion4,4,relayId,content4);
            }
        }

    }

    public void setRelayBusinessAreaCode(RelayBusinessAreaCode relayBusinessAreaCode,String opertion,int i,String relayId,String content){
        relayBusinessAreaCode.setRelayId(Integer.parseInt(relayId));
        relayBusinessAreaCode.setContent(content);
        relayBusinessAreaCode.setOpertion(new Byte(opertion));
        if(i==1){
            relayBusinessAreaCode.setDirection(new Byte("0"));
            relayBusinessAreaCode.setNumType(new Byte("0"));
            relayBussinessAreaCodeService.saveRelayBussinessAreaCode(relayBusinessAreaCode);
        }else if(i==2){
            relayBusinessAreaCode.setDirection(new Byte("0"));
            relayBusinessAreaCode.setNumType(new Byte("1"));
            relayBussinessAreaCodeService.saveRelayBussinessAreaCode(relayBusinessAreaCode);
        }else if(i==3){
            relayBusinessAreaCode.setDirection(new Byte("1"));
            relayBusinessAreaCode.setNumType(new Byte("0"));
            relayBussinessAreaCodeService.saveRelayBussinessAreaCode(relayBusinessAreaCode);
        }else if(i==4) {
            relayBusinessAreaCode.setDirection(new Byte("1"));
            relayBusinessAreaCode.setNumType(new Byte("1"));
            relayBussinessAreaCodeService.saveRelayBussinessAreaCode(relayBusinessAreaCode);
        }
    }

    public void addRelayBusinessZero(HttpServletRequest request, int[] operator1s, int[] operator2s, int[] operator3s, int[] operator4s) {

        String relayId = request.getParameter("relayId")!=null?request.getParameter("relayId").trim():"";

        String opertion5 = request.getParameter("opertion5")!=null?request.getParameter("opertion5").trim():"";
        String opertion6 = request.getParameter("opertion6")!=null?request.getParameter("opertion6").trim():"";
        String opertion7 = request.getParameter("opertion7")!=null?request.getParameter("opertion7").trim():"";
        String opertion8 = request.getParameter("opertion8")!=null?request.getParameter("opertion8").trim():"";

        RelayBusinessZero relayBusinessZero1 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero2 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero3 = new RelayBusinessZero();
        RelayBusinessZero relayBusinessZero4 = new RelayBusinessZero();

        if(!StringUtils.isEmpty(opertion5)){
            if(!"2".equals(opertion5)){
                setRelayBusinessZero(relayBusinessZero1,opertion5,1,relayId,operator1s);
            }
        }

        if(!StringUtils.isEmpty(opertion6)){
            if(!"2".equals(opertion6)){
                setRelayBusinessZero(relayBusinessZero2,opertion6,2,relayId,operator2s);
            }
        }

        if(!StringUtils.isEmpty(opertion7)){
            if(!"2".equals(opertion7)){
                setRelayBusinessZero(relayBusinessZero3,opertion7,3,relayId,operator3s);
            }
        }

        if(!StringUtils.isEmpty(opertion8)){
            if(!"2".equals(opertion8)){
                setRelayBusinessZero(relayBusinessZero4,opertion8,4,relayId,operator4s);
            }
        }
    }

    public void setRelayBusinessZero(RelayBusinessZero relayBusinessZero,String opertion,int i,String relayId,int[] operators){
        relayBusinessZero.setRelayId(Integer.parseInt(relayId));
        relayBusinessZero.setOpertion(new Byte(opertion));
        if("0".equals(opertion)){
            int operator = Tools.bitToDecimal(operators);
            relayBusinessZero.setOperator(operator);
        }
        if(i==1){
            relayBusinessZero.setDirection(new Byte("0"));
            relayBusinessZero.setNumType(new Byte("0"));
            relayBussinessZeroService.saveRelayBussinessZero(relayBusinessZero);
        }else if(i==2){
            relayBusinessZero.setDirection(new Byte("0"));
            relayBusinessZero.setNumType(new Byte("1"));
            relayBussinessZeroService.saveRelayBussinessZero(relayBusinessZero);
        }else if(i==3){
            relayBusinessZero.setDirection(new Byte("1"));
            relayBusinessZero.setNumType(new Byte("0"));
            relayBussinessZeroService.saveRelayBussinessZero(relayBusinessZero);
        }else if(i==4) {
            relayBusinessZero.setDirection(new Byte("1"));
            relayBusinessZero.setNumType(new Byte("1"));
            relayBussinessZeroService.saveRelayBussinessZero(relayBusinessZero);
        }

    }

}
