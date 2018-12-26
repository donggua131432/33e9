package com.e9cloud.pcweb.appointLink.action;

import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AppointLink;
import com.e9cloud.mybatis.service.AppointLinkService;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 特例表
 * Created by hzd on 2016/8/31.
 */
@Controller
@RequestMapping("/appointLink")
public class AppointLinkController extends BaseController {

    @Autowired
    private AppointLinkService appointLinkService;

    /////////////////////////////////////////// AppointLink start //////////////////////////////////////////////

    // 跳转到 特例表列表页面
    @RequestMapping("/appointLinkList")
    public String toRest() {
        return "appointLink/appointLinklist";
    }

    /**
     * 列表数据查询
     * @param page
     * @return
     */
    @RequestMapping("/pageappointLinkList")
    @ResponseBody
    public PageWrapper pageCityList(Page page){
        logger.info("=====================================AppointLinkController pageappointLinkList Execute=====================================");
        return appointLinkService.pageAppointLinkList(page);

    }

    @RequestMapping("/addAppointLink")
    public String addCustomer(){
        logger.info("=====================================AppointLinkController addAppointLink Execute=====================================");
        return "appointLink/appointLinkAdd";
    }

    /**
     * 添加线路特例信息
     * @param request
     * @return
     */
    @RequestMapping("/saveAppointLink")
    @ResponseBody
    public JSonMessage saveAppointLink(HttpServletRequest request, AppointLink appointLink){
        logger.info("=====================================CustomerManagerController saveCustomer Execute=====================================");
        if(Tools.isNullStr(appointLink.getXhTelno()) && Tools.isNullStr(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if("*".equals(appointLink.getXhTelno()) && Tools.isNullStr(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if("*".equals(appointLink.getXhTelno()) && "*".equals(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if(Tools.isNullStr(appointLink.getXhTelno()) && "*".equals(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        AppointLink AppointLinkResult = appointLinkService.queryAppointLinkInfo(appointLink);
        if(AppointLinkResult!=null){
            return new JSonMessage("no", "已存在完全相同数据");
        }else{
            String xhTelno = appointLink.getXhTelno().trim();
            appointLink.setXhTelno(xhTelno);
            String  destTelno = appointLink.getDestTelno().trim();
            appointLink.setDestTelno(destTelno);
            String rn = appointLink.getRn().trim();
            appointLink.setRn(rn);
            appointLink.setCreateTime(new Date());
            appointLinkService.saveAppointLink(appointLink);
            return new JSonMessage("ok", "新增特例完成");
        }

    }

    // 显示线路特例信息
    @RequestMapping(value = "toShow", method = RequestMethod.GET)
    public String toShow(String id, Model model) {
        AppointLink appointLink = appointLinkService.queryAppointLinkInfoById(id);
        model.addAttribute("appointLink", appointLink);

        return "appointLink/appointLinkShow";
    }

    // 编辑线路特例页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String id, Model model) {
        AppointLink appointLink = appointLinkService.queryAppointLinkInfoById(id);
        model.addAttribute("appointLink", appointLink);

        return "appointLink/appointLinkEdit";
    }

    // 修改一个特例信息
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage edit(AppointLink appointLink) {
        logger.info("-------------AppointLinkInfoController edit start-------------");
        if(Tools.isNullStr(appointLink.getXhTelno()) && Tools.isNullStr(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if("*".equals(appointLink.getXhTelno()) && Tools.isNullStr(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if("*".equals(appointLink.getXhTelno()) && "*".equals(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        if(Tools.isNullStr(appointLink.getXhTelno()) && "*".equals(appointLink.getDestTelno())){
            return new JSonMessage("no", "配置错误");
        }
        AppointLink AppointLinkResult = appointLinkService.queryAppointLinkInfo(appointLink);
        if(AppointLinkResult!=null&&!AppointLinkResult.getId().equals(appointLink.getId())){
            return new JSonMessage("no", "已存在完全相同数据");
        }else{
            String xhTelno = appointLink.getXhTelno().trim();
            appointLink.setXhTelno(xhTelno);
            String  destTelno = appointLink.getDestTelno().trim();
            appointLink.setDestTelno(destTelno);
            String rn = appointLink.getRn().trim();
            appointLink.setRn(rn);
            appointLinkService.updateAppointLinkInfo(appointLink);
        }

        logger.info("-------------AppointLinkInfoController edit end-------------");

        return new JSonMessage("ok", "编辑特例完成");
    }

    // 删除一个特例
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage delete(AppointLink appointLink) {
        logger.info("-------------AppointLinkInfoController delete start-------------");
        appointLinkService.delAppointLinkInfo(appointLink);
        logger.info("-------------AppointLinkInfoController delete end-------------");

        return new JSonMessage("ok", "删除特例完成");
    }

    /**
     * 导入特例表
     * @return
     */
    @RequestMapping("/importAppointLink")
    public String importAppointLink(HttpServletRequest request, Model model){
        return "appointLink/importAppointLink";
    }

    /**
     * 上传SIP Number Excel文件信息
     * @param appointLinkFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/appointLinkExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile appointLinkFile, HttpServletRequest request){

        if(appointLinkFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<AppointLink> insertSipNumErrorList = saveExcel(appointLinkFile, request);
                if (insertSipNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<AppointLink> saveExcel(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<AppointLink> appointLinkList = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (Integer key: mapFile.keySet()) {
            String[] arrayStr = mapFile.get(key).split("-");

            int size = arrayStr.length;

            //设置SIP选号信息
            AppointLink appointLink = new AppointLink();
            appointLink.setXhTelno(arrayStr[0].trim());
            appointLink.setDestTelno(arrayStr[1].trim());
            if(Tools.isNotNullStr(arrayStr[2])){
                appointLink.setType(Integer.parseInt(arrayStr[2]));
            }else{
                appointLink.setType(null);
            }

            appointLink.setRn(arrayStr[3].trim());
            appointLink.setRemake(arrayStr[4]);
            appointLinkList.add(appointLink);
        }

        Pattern xhTelnoPattern = Pattern.compile("[0-9]{0,20}|[*]?");
        Pattern destTelnoPattern = Pattern.compile("[0-9]{0,20}|[*]?");
        Pattern typePattern = Pattern.compile("[0-9]{0,11}");
        Pattern rnPattern = Pattern.compile("[0-9]{0,20}");
        Matcher xhTelnoMatcher = null;
        Matcher destTelnoMatcher = null;
        Matcher typeMatcher = null;
        Matcher rnMatcher = null;
        Boolean remarkLength =false;
        Boolean typeMatch = false;
        List<AppointLink> insertAppointLinkErrorList = new ArrayList<>();
        for (AppointLink appointLinkResult : appointLinkList) {
            AppointLink AppointLinkResult = appointLinkService.queryAppointLinkInfo(appointLinkResult);

            if(AppointLinkResult!=null){
                //将重复号码添加至错误的集合中
                insertAppointLinkErrorList.add(appointLinkResult);
            }else{
                xhTelnoMatcher = xhTelnoPattern.matcher(appointLinkResult.getXhTelno().trim());
                destTelnoMatcher = destTelnoPattern.matcher(appointLinkResult.getDestTelno().trim());
                if(appointLinkResult.getType()!=null){
                    typeMatch = typePattern.matcher(appointLinkResult.getType().toString()).matches();

                }else{
                    typeMatch = true;
                }

                rnMatcher = rnPattern.matcher(appointLinkResult.getRn());
                if(appointLinkResult.getRemake().length()<=20){
                    remarkLength = true;
                }
                if (xhTelnoMatcher.matches() && destTelnoMatcher.matches() && typeMatch && rnMatcher.matches() && remarkLength) {
                    if("*".equals(appointLinkResult.getXhTelno()) && Tools.isNullStr(appointLinkResult.getDestTelno())){
                        //（ 显号，被叫 ）为（*，空）加入错误excel
                        insertAppointLinkErrorList.add(appointLinkResult);
                    }else if ("*".equals(appointLinkResult.getXhTelno()) && "*".equals(appointLinkResult.getDestTelno())){
                        //（ 显号，被叫 ）为（*，*）加入错误excel
                        insertAppointLinkErrorList.add(appointLinkResult);
                    }else if (Tools.isNullStr(appointLinkResult.getXhTelno()) && Tools.isNullStr(appointLinkResult.getDestTelno())){
                        //（ 显号，被叫 ）为（空，空）加入错误excel
                        insertAppointLinkErrorList.add(appointLinkResult);
                    }else if (Tools.isNullStr(appointLinkResult.getXhTelno()) && "*".equals(appointLinkResult.getDestTelno())){
                        //（ 显号，被叫 ）为（空，*）加入错误excel
                        insertAppointLinkErrorList.add(appointLinkResult);
                    }else {
                        appointLinkResult.setCreateTime(new Date());
                        appointLinkService.saveAppointLink(appointLinkResult);
                    }
                }else{
                    //将错误号码添加至错误的集合中
                    insertAppointLinkErrorList.add(appointLinkResult);
                }
            }

        }

        request.getSession().setAttribute("insertAppointLinkErrorList",insertAppointLinkErrorList);
        return insertAppointLinkErrorList;
    }

    /**
     * 下载SIPNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorAppointLink")
    public ModelAndView downLoadErrorAppointLink(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SipNumberPoolController downLoadErrorSipNum start--------------------------------");
        List<AppointLink> insertAppointLinkErrorList = (List<AppointLink>) request.getSession().getAttribute("insertAppointLinkErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertAppointLinkErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<AppointLink> insertAppointLinkErrorList) {
        logger.info("--------------------------------SipNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (AppointLink appointLink : insertAppointLinkErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("xhTelno", appointLink.getXhTelno());
            excelMap.put("destTelno", appointLink.getDestTelno());
            if(appointLink.getType()!=null){
                excelMap.put("type", appointLink.getType().toString());
            }else{
                excelMap.put("type", "");
            }
            excelMap.put("rn", appointLink.getRn());
            excelMap.put("remark", appointLink.getRemake());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("显示号码前缀");
        titles.add("呼叫目的号码前缀");
        titles.add("类型(number)");
        titles.add("RN值");
        titles.add("备注");

        List<String> columns = new ArrayList<String>();
        columns.add("xhTelno");
        columns.add("destTelno");
        columns.add("type");
        columns.add("rn");
        columns.add("remark");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","错误特例表信息");
        contentMap.put("excelName", "错误特例表信息");
        return contentMap;
    }
    //////////////////////////////////////////// AppointLink end //////////////////////////////////////////////

}
