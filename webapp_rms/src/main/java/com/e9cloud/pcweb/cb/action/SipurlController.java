package com.e9cloud.pcweb.cb.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.Sipurl;
import com.e9cloud.mybatis.service.SipurlService;
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
import javax.tools.Tool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2017/3/14.
 */
@Controller
@RequestMapping("/sipurl")
public class SipurlController extends BaseController {

    @Autowired
    private SipurlService sipurlService;

    @RequestMapping("index")
    public String index() {
        return "cb/sipurl_list";
    }

    @RequestMapping("toAddSipUrl")
    public String toAddSipUrl(Sipurl sipurl) {
        return "cb/sipurl_add";
    }

    @RequestMapping("page")
    @ResponseBody
    public PageWrapper list(Page page) {
        return sipurlService.pageSipurl(page);
    }

    @RequestMapping("addOrUpdateSipUrl")
    @ResponseBody
    public JSonMessage addOrUpdateSipUrl(Sipurl sipurl) {
        long num = sipurlService.checkNum(sipurl);
        if (num == 0) {
            if (Tools.isNullStr(sipurl.getId())) { // 新增
                sipurlService.addSipUrl(sipurl);
            } else { // 修改
                sipurlService.updateSipUrl(sipurl);
            }
            return new JSonMessage(R.OK, "操作成功");
        }

        return new JSonMessage(R.ERROR, "该号码已存在");
    }

    /**
     * 校验号码的唯一性
     * @param sipurl
     * @return
     */
    @RequestMapping("checkNum")
    @ResponseBody
    public JSonMessage checkNum(Sipurl sipurl) {
        long num = sipurlService.checkNum(sipurl);
        if (num > 0) {
            return new JSonMessage(R.ERROR, "该号码已存在");
        }
        return new JSonMessage(R.OK, "");
    }

    @RequestMapping("delSipUrl")
    @ResponseBody
    public JSonMessage delSipUrl(String id) {
        sipurlService.delSipUrlByPK(id);
        return new JSonMessage(R.OK, "");
    }

    @RequestMapping("toEditSipUrl")
    public String toEditSipUrl(Model model, String id) {
        Sipurl sipurl = sipurlService.getSipurlByPK(id);
        model.addAttribute("sipurl", sipurl);
        return "cb/sipurl_add";
    }

    @RequestMapping("toImportExcel")
    public String toImportExcel() {
        return  "cb/sipurl_import";
    }

    /**
     * 上传 Excel文件信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/excelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
    public JSonMessageSubmit excelUpload(@RequestParam(required = false, value = "file") MultipartFile file, HttpServletRequest request){

        if(file == null){
            return new JSonMessageSubmit("0", "导入数据失败！");
        }else{
            try {
                List<Sipurl> insertErrorList = sipurlService.saveSipUrlExcel(file, request);
                if (insertErrorList.size() > 0) {
                    return new JSonMessageSubmit("2", "存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0", "导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1", "导入数据成功,重复及空列数据不会被导入！");
        }
    }

    /**
     * 下载SubNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorList")
    public ModelAndView downLoadErrorList(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------SubNumberPoolController downLoadErrorSipPhone start--------------------------------");
        List<Sipurl> insertSipUrlErrorList = (List<Sipurl>) request.getSession().getAttribute("insertSipUrlErrorList");
        Map<String, Object> contentMap = downLoadErrorExcel(insertSipUrlErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 下载
     */
    public Map<String, Object> downLoadErrorExcel(List<Sipurl> errorList) {
        logger.info("--------------------------------SubNumberPoolController downLoadExcel start--------------------------------");
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        for (Sipurl sipurl : errorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("sipurl", sipurl.getSipurl());
            excelMap.put("num", sipurl.getNum());
            excelMap.put("importCommon", sipurl.getImportCommon());
            mapList.add(excelMap);
        }

        List<String> titles = new ArrayList<String>();
        titles.add("SipUrl");
        titles.add("号码");
        titles.add("原因");

        List<String> columns = new ArrayList<String>();
        columns.add("sipurl");
        columns.add("num");
        columns.add("importCommon");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("title","SipUrl错误列表");
        contentMap.put("excelName", "SipUrl错误列表");

        return contentMap;
    }

    /**
     * 导出报表
     *
     * @return
     */
    @RequestMapping(value = "downloadReport", method = RequestMethod.GET)
    public ModelAndView downloadReport(Page page) {
        List<Map<String, Object>> totals = sipurlService.downloadSipUrl(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(totals != null && totals.size() > 0) {
            for(Map<String, Object> total : totals) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sipurl", Tools.toStr(total.get("sipurl")));
                map.put("num", Tools.toStr(total.get("num")));
                list.add(map);
            }
        }

        List<String> titles = new ArrayList<String>();
        titles.add("SipUrl");
        titles.add("号码");

        List<String> columns = new ArrayList<String>();
        columns.add("sipurl");
        columns.add("num");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("titles", titles);
        map.put("columns", columns);
        map.put("list", list);
        map.put("title", "SipUrl列表");
        map.put("excelName", "SipUrl列表");
        return new ModelAndView(new POIXlsView(), map);
    }

}
