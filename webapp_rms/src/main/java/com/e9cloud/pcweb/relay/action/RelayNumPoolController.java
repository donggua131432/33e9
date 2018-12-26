package com.e9cloud.pcweb.relay.action;

import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.RelayNumPool;
import com.e9cloud.mybatis.domain.SipBasic;
import com.e9cloud.mybatis.service.RelayNumService;
import com.e9cloud.mybatis.service.RelayService;
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
 * 中继强显号号码池管理
 */

@Controller
@RequestMapping("/relayNumPool")

public class RelayNumPoolController extends BaseController {
    @Autowired
    private RelayNumService relayNumService;
    @Autowired
    private RelayService relayService;
    /**
     * 中继强显号码池管理界面
     * @retur
     */
    @RequestMapping("/relayNumPoolMgr")
    public String subNumPoolMgr(HttpServletRequest request, Model model){
        logger.info("--------------------------------RelayNumberPoolController subNumPoolMgr start--------------------------------");
        String id = request.getParameter("id");
        SipBasic sipBasic = relayService.getRelayInfoById(id);
        model.addAttribute("sipBasic", sipBasic);
        return "relay/relayNumPoolMgr";
    }


    /**
     * 修改系统默认强显中继
     * @return
     */
    @RequestMapping("/updateIsForce")
    @ResponseBody
    public JSonMessageSubmit updateIsForce(HttpServletRequest request){
        logger.info("--------------------------------RelayNumberPoolController updateIsForce start--------------------------------");
        String isForce = request.getParameter("isForce");
        String id = request.getParameter("id");
        SipBasic sipBasic = new SipBasic();
        if(Tools.isNullStr(isForce)){
            sipBasic.setIsForce("0");
        }else {
            sipBasic.setIsForce("1");
        }

        sipBasic.setId(Integer.parseInt(id));
        try {
            relayService.updateSipBasicInfo(sipBasic);
            if(sipBasic.getIsForce().equals("1")){
                return new JSonMessageSubmit("0","设置系统默认强显中继！");
            }else {
                return new JSonMessageSubmit("0","取消系统默认强显中继！");
            }

        }catch(Exception e){
            return new JSonMessageSubmit("1","修改系统默认强显中继失败！");
        }
    }
    /**
     * 单个添加号码页面
     * @return
     */
    @RequestMapping("/addRelayNumber")
    public String addRelayNumber(HttpServletRequest request, Model model){
        logger.info("--------------------------------RelayNumberPoolController addRelayNumber start--------------------------------");
        model.addAttribute("relayNum", request.getParameter("relayNum"));
        return "relay/addRelayNumber";
    }


    /**
     * 添加中继强显号码
     * @param
     * @return
     */
    @RequestMapping("/addNumbers")
    @ResponseBody
    public JSonMessageSubmit addNumbers(RelayNumPool relayNumPool,  String numbers, Integer maxConcurrent,HttpServletRequest request){
        logger.info("--------------------------------RelayNumberController addNumbers start--------------------------------");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        String[] numArr = numbers.split(",");
        StringBuilder existNum = new StringBuilder();
        StringBuilder relayNum = new StringBuilder();
        StringBuilder succNum = new StringBuilder();
        StringBuilder fomatNum = new StringBuilder();
        if(numArr.length > 0) {
            for (int i = 0; i < numArr.length; i++) {
                isNum = pattern.matcher(numArr[i]);
                //判断号码是否合法
                if (isNum.matches()) {
                    List<RelayNumPool> RelayNumResult = relayNumService.getRelayNumberByNumber(relayNumPool.getRelayNum(),numArr[i]);
                    if (RelayNumResult.size()>0) {
//                            return new JSonMessageSubmit("1", numArr[i] +"号码已存在，信息添加失败！");
                        existNum.append(numArr[i]+",");
                        relayNum.append(RelayNumResult.get(0).getRelayNum()+",");

                    } else {
                        RelayNumPool RelayNumber = new RelayNumPool();
                        RelayNumber.setRelayNum(relayNumPool.getRelayNum());
                        RelayNumber.setNumber(numArr[i]);
                        RelayNumber.setMaxConcurrent(maxConcurrent);
                        RelayNumber.setCreateTime(new Date());
                        relayNumService.addRelayNumberPool(RelayNumber);
                        succNum.append(numArr[i]+",");
                    }
                } else {
//                    return new JSonMessageSubmit("1", numArr[i]+"号码格式错误，信息添加失败！");

                    fomatNum.append(numArr[i]+",");
                }
            }
        }
        String succStr ="";
        String existStr ="";
        String fomatStr ="";
        if(succNum.length()>0){
            succStr =succNum.substring(0,succNum.length()-1)+"号码添加成功;";
        }
        if(existNum.length()>0){
//            existStr =existNum.substring(0,existNum.length()-1)+"号码添加失败已存在中继ID为"+relayNum.substring(0,relayNum.length()-1)+";";
            existStr =existNum.substring(0,existNum.length()-1)+"号码添加失败,已存在强显号码列表中;";
        }
        if(fomatNum.length()>0){
            fomatStr =fomatNum.substring(0,existNum.length()-1)+"号码格式错误;";
        }
        return new JSonMessageSubmit("0",succStr+"\r\n" + existStr+"\r\n" +fomatStr+"\r\n");
    }

    /**
     * 中继强显号码池列表
     * @param page
     * @return
     */
    @RequestMapping("/getRelayNumList")
    @ResponseBody
    public PageWrapper getRelayNumList(Page page){
        logger.info("----RelayNumberPoolController getRelayNumberList start-------");
        return relayNumService.getRelayNumList(page);
    }

    // 修改并发数页面
    @RequestMapping(value = "toUpdateMax", method = RequestMethod.GET)
    public String toEditRelayNumInfo(String id, Model model) {
        logger.info("--------------------------------RelayNumberPoolController toEditRelayNumInfo start--------------------------------");
        RelayNumPool relayNumPool = relayNumService.getRelayNumInfoById(Integer.parseInt(id));
        model.addAttribute("relayNumPool", relayNumPool);
        return "relay/relay_num_update";
    }
    // 修改强显号码池并发数
    @RequestMapping(value = "updateMax", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage updateRelayNum(RelayNumPool relayNumPool,String maxCon) {
        logger.info("--------------------------------RelayNumberPoolController updateRelayNum start--------------------------------");
        if (Tools.isNullStr(maxCon)){
            relayNumPool.setMaxConcurrent(0);
        }else {
            relayNumPool.setMaxConcurrent(Integer.parseInt(maxCon));
        }
        relayNumService.updateRelayNumber(relayNumPool);

        return new JSonMessage("ok", "修改并发数成功！");
    }

    /**
     * 删除 强显号号码
     * @param request
     * @return
     */
    @RequestMapping("/deleteRelayNumber")
    @ResponseBody
    public JSonMessageSubmit deleteRelayNumber(HttpServletRequest request){
        logger.info("--------------------------------RelayNumberPoolController deleteRelayNumber start--------------------------------");
        RelayNumPool relayNumPool = new RelayNumPool();
        relayNumPool.setId(Integer.parseInt(request.getParameter("id")));
        //判断是否还存在此条数据
        boolean flag = relayNumService.checkRelayNumUnique(relayNumPool);
        if(flag == false){
            relayNumService.deleteRelayNumber(relayNumPool);
            return new JSonMessageSubmit("0","删除数据成功！");
        }else{
            return new JSonMessageSubmit("1","该数据信息已删除！");
        }
    }


    /**
     * 导入号码
     * @return
     */
    @RequestMapping("/importRelayNumber")
    public String importRelayNumber(HttpServletRequest request, Model model){
        logger.info("--------------------------------RelayNumberPoolController importRelayNumber start--------------------------------");
        model.addAttribute("relayNum", request.getParameter("relayNum"));
        return "relay/importRelayNumbers";
    }

    /**
     * 上传Sub Number Excel文件信息
     * @param RelayNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/RelayNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile RelayNumFile, HttpServletRequest request){
        logger.info("--------------------------------RelayNumberPoolController upload start--------------------------------");
        if(RelayNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            try {
                List<RelayNumPool> insertRelayNumErrorList = saveExcel(RelayNumFile, request);
                if (insertRelayNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 处理excel中的数据信息
     * @param SubNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<RelayNumPool> saveExcel(MultipartFile SubNumFile, HttpServletRequest request) throws Exception{
        logger.info("--------------------------------RelayNumberPoolController saveExcel start--------------------------------");
        List<RelayNumPool> RelayNumList = new ArrayList<>();
        InputStream is = SubNumFile.getInputStream();
        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置RelayNum信息
            RelayNumPool relayNumber = new RelayNumPool();
            relayNumber.setRelayNum(request.getParameter("relayNum"));
            relayNumber.setMaxConcurrent(Integer.parseInt(arrayStr[0]));
            relayNumber.setNumber(arrayStr[1]);
            relayNumber.setCreateTime(new Date());
            RelayNumList.add(relayNumber);
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<RelayNumPool> insertRelayNumErrorList = new ArrayList<>();
        for (RelayNumPool RelayNumResult : RelayNumList) {

            isNum = pattern.matcher(RelayNumResult.getNumber());
            //判断号码是否合法
            if (isNum.matches()) {
                List<RelayNumPool> RelayNum = relayNumService.getRelayNumberByNumber(RelayNumResult.getRelayNum(),RelayNumResult.getNumber());
                if(RelayNum != null && RelayNum.size() > 0){

                    insertRelayNumErrorList.add(RelayNumResult);
                }else{
                    relayNumService.addRelayNumberPool(RelayNumResult);
                }
            }else{
                //将错误号码添加至错误的集合中
                insertRelayNumErrorList.add(RelayNumResult);
            }
        }
        request.getSession().setAttribute("insertRelayNumErrorList",insertRelayNumErrorList);
        return insertRelayNumErrorList;
    }

    /**
     * 下载RelayNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorRelayNum")
    public ModelAndView downLoadErrorRelayNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------RelayNumberPoolController downLoadErrorRelayNum start--------------------------------");
        List<RelayNumPool> insertRelayNumErrorList = (List<RelayNumPool>) request.getSession().getAttribute("insertRelayNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertRelayNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadExcel(List<RelayNumPool> insertRelayNumErrorList) {
        logger.info("--------------------------------RelayNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (RelayNumPool RelayNumPool : insertRelayNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("MaxConcurrent",RelayNumPool.getMaxConcurrent().toString());
            excelMap.put("number", RelayNumPool.getNumber());
//          excelMap.put("flag", "错误号码");
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("最大并发数");
        titles.add("号码");
//      titles.add("信息");

        List<String> columns = new ArrayList<String>();
        columns.add("MaxConcurrent");
        columns.add("number");
//      columns.add("flag");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","错误中继强显号码信息");
        contentMap.put("excelName", "错误中继强显号码信息");
        return contentMap;
    }
}
