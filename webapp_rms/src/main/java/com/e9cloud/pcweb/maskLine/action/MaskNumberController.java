package com.e9cloud.pcweb.maskLine.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumPool;
import com.e9cloud.mybatis.service.CityAreaCodeService;
import com.e9cloud.mybatis.service.MaskLineService;
import com.e9cloud.pcweb.BaseController;

import com.e9cloud.pcweb.maskLine.MakePoolRedis;
import com.e9cloud.redis.base.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * Created by dukai on 2016/6/2.
 */
@Controller
@RequestMapping("/maskNum")
public class MaskNumberController extends BaseController{
    @Autowired
    private MaskLineService  maskLineService;

    @Autowired
    private CityAreaCodeService cityAreaCodeService;

    @Autowired
    private RedisOperator redisOperator;
    /**
     * 删除隐私号
     * @param request
     * @return
     */
    @RequestMapping("/deleteMaskNumber")
    @ResponseBody
    public JSonMessageSubmit deleteMaskNumberPool(HttpServletRequest request){
        logger.info("----MaskLineController deleteMaskNumberPool start-------");
        String strId = request.getParameter("strId");
        String[] aa = strId.split(",");
        for(String id : aa) {
            MaskNum maskNum = new MaskNum();
            maskNum.setId(id);
            MaskNum maskNumResult = maskLineService.getMaskNumList(maskNum).get(0);
            if(maskNumResult != null){
                String key = new StringBuffer("mask*").append(maskNumResult.getNumber()).append("*").toString();
                if (redisOperator.existKeys(key) == false) {
                    maskNum.setAppid("");
                    maskNum.setStatus("03");
                }else{
                    maskNum.setStatus("02");
                }
                maskLineService.updateMaskNumber(maskNum);
                MakePoolRedis.getInstance().delMakeNum(maskNumResult.getAppid(),maskNumResult.getCityAreaCode().getAreaCode(),maskNumResult.getNumber());
            }
        }
        return new JSonMessageSubmit("0","删除数据成功！");
    }



    /**
     * 批量添加隐私号码
     * @param maskNum
     * @return
     */
    @RequestMapping("/addMaskNumbers")
    @ResponseBody
    public JSonMessageSubmit addMaskNumbers(MaskNum maskNum, String numbers, HttpServletRequest request){
        logger.info("--------------------------------MaskNumberController regionNumUpload start--------------------------------");
        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        //添加隐私号信息
        String[] numArr = numbers.split(",");
        List<MaskNum> maskNumResultLsit = new ArrayList<>();
        if(numArr.length > 0) {
            for (int i = 0; i < numArr.length; i++) {
                isNum = pattern.matcher(numArr[i]);
                //判断号码是否合法
                if (isNum.matches()) {
                    MaskNum maskNumResult = maskLineService.getMaskNumberByNumber(numArr[i]);
                    MaskNum maskNumber = new MaskNum();
                    maskNumber.setAppid(maskNum.getAppid());
                    maskNumber.setCityid(maskNum.getCityid());
                    if (maskNumResult != null) {
                        if ("01".equals(maskNumResult.getStatus())) {
                            return new JSonMessageSubmit("1", maskNumResult.getNumber()+"号码已分配，信息添加失败！");
                        } else if ("02".equals(maskNumResult.getStatus())) {
                            return new JSonMessageSubmit("1", maskNumResult.getNumber()+"号码已锁定，信息添加失败！");
                        } else if ("03".equals(maskNumResult.getStatus())) {
                            maskNumber.setId(maskNumResult.getId());
                            maskNumber.setNumber(maskNumResult.getNumber());
                            maskNumber.setStatus(maskNumResult.getStatus());
                        }
                    } else {
                        maskNumber.setId(ID.randomUUID());
                        maskNumber.setNumber(numArr[i]);
                        maskNumber.setStatus("01");
                    }
                    maskNumResultLsit.add(maskNumber);
                } else {
                    return new JSonMessageSubmit("1", numArr[i]+"号码格式错误，信息添加失败！");
                }
            }
        }

        if(maskNumResultLsit.size() > 0){
            //获取隐私号码池的信息
            MaskNumPool maskNumPoolResult = maskLineService.getMaskNumberPoolByObje(new MaskNumPool(maskNum.getAppid()));
            //向隐私号码池中添加信息
            if(maskNumPoolResult == null){
                MaskNumPool maskNumPool = new MaskNumPool();
                maskNumPool.setId(ID.randomUUID());
                maskNumPool.setUid(request.getParameter("uid"));
                maskNumPool.setSid(request.getParameter("sid"));
                maskNumPool.setAppid(maskNum.getAppid());
                maskLineService.addMaskNumberPool(maskNumPool);
            }

            //添加隐私号信息
            for (MaskNum maskNumResult : maskNumResultLsit) {
                if ("03".equals(maskNumResult.getStatus())) {
                    maskNumResult.setStatus("01");
                    maskLineService.updateMaskNumber(maskNumResult);
                    //向redis 隐私号码池中添加号码
                    MakePoolRedis.getInstance().addMakeNum(maskNumResult.getAppid(), request.getParameter("areaCode"), maskNumResult.getNumber());
                } else {
                    maskLineService.addMaskNumber(maskNumResult);
                    //向redis 隐私号码池中添加号码
                    MakePoolRedis.getInstance().addMakeNum(maskNumResult.getAppid(), request.getParameter("areaCode"), maskNumResult.getNumber());
                }
            }
        }else{
            return new JSonMessageSubmit("1","信息添加失败！");
        }
        return new JSonMessageSubmit("0","信息添加成功！");
    }


    /**
     * 下载MaskNumber上传时Excel文件中错误的号码信息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/downLoadErrorMaskNum")
    public ModelAndView downLoadErrorMaskNum(HttpServletRequest request) throws Exception{
        logger.info("--------------------------------MaskNumberController downLoadErrorMaskNum start--------------------------------");
        List<MaskNum> insertMaskNumErrorList = (List<MaskNum>) request.getSession().getAttribute("insertMaskNumErrorList");
        Map<String, Object> contentMap = downLoadExcel(insertMaskNumErrorList);
        return new ModelAndView(new POIXlsView(), contentMap);
    }

    /**
     * 上传MaskNumber Excel文件信息
     * @param maskNumFile
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/maskNumExcelUpload", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody

    public JSonMessageSubmit upload(@RequestParam(required = false, value = "file") MultipartFile maskNumFile, HttpServletRequest request){
        logger.info("--------------------------------MaskNumberController upload start--------------------------------");

        if(maskNumFile == null){
            return new JSonMessageSubmit("0","导入数据失败！");
        }else{
            MaskNumPool maskNumPoolResult = maskLineService.getMaskNumberPoolByObje(new MaskNumPool(request.getParameter("appid")));
            //向隐私号码池中添加信息
            if(maskNumPoolResult == null){
                MaskNumPool maskNumPool = new MaskNumPool();
                maskNumPool.setId(ID.randomUUID());
                maskNumPool.setUid(request.getParameter("uid"));
                maskNumPool.setSid(request.getParameter("sid"));
                maskNumPool.setAppid(request.getParameter("appid"));
                maskLineService.addMaskNumberPool(maskNumPool);
            }

            try {
                List<MaskNum> insertMaskNumErrorList = saveExcel(maskNumFile, request);
                if (insertMaskNumErrorList.size() > 0) {
                    return new JSonMessageSubmit("2","存在错误号码,部分数据导入成功,请查看下载内容！");
                }
            } catch (Exception e) {
                logger.info("导入文件失败：" + e);
                return new JSonMessageSubmit("0","导入数据失败,请下载模板编辑数据进行导入！");
            }

            return new JSonMessageSubmit("1","导入数据成功,重复及空列数据不会被导入！");
        }
    }

    public List<MaskNum> saveExcel(MultipartFile maskNumFile, HttpServletRequest request) throws Exception{
        List<MaskNum> maskNumList = new ArrayList<>();
        InputStream is = maskNumFile.getInputStream();
        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        String strAreaCodes = "";
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (i == 1) {
                if (strAreaCodes.indexOf(arrayStr[1]) < 0) {
                    strAreaCodes += "'" + arrayStr[1] + "'";
                }
            } else {
                if (strAreaCodes.indexOf(arrayStr[1]) < 0) {
                    strAreaCodes += ",'" + arrayStr[1] + "'";
                }
            }
            //设置隐私号信息
            MaskNum maskNum = new MaskNum();
            maskNum.setAppid(request.getParameter("appid"));
            maskNum.setNumber(arrayStr[2]);
            maskNum.setStatus("01");
            //设置城市区号信息
            CityAreaCode cityAreaCode = new CityAreaCode();
            cityAreaCode.setCity(arrayStr[0]);
            cityAreaCode.setAreaCode(arrayStr[1]);
            maskNum.setCityAreaCode(cityAreaCode);
            maskNumList.add(maskNum);
        }

        //根据区号查询出区域信息
        List<CityAreaCode> cityAreaCodeList = cityAreaCodeService.selectCityAreaCodeByAreaCode(strAreaCodes);
        Map<String, String> cityAreaCodeMap = new HashMap<>();
        for (CityAreaCode cityAreaCode : cityAreaCodeList) {
            cityAreaCodeMap.put(cityAreaCode.getAreaCode(), cityAreaCode.getId());
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");
        Matcher isNum = null;
        List<MaskNum> insertMaskNumErrorList = new ArrayList<>();
        for (MaskNum maskNumResult : maskNumList) {
            //设置城市ID
            maskNumResult.setCityid(cityAreaCodeMap.get(maskNumResult.getCityAreaCode().getAreaCode()));
            isNum = pattern.matcher(maskNumResult.getNumber());
            //判断号码是否合法
            if (isNum.matches()) {
                MaskNum maskNumBool =  maskLineService.getMaskNumberByNumber(maskNumResult.getNumber());
                if(maskNumBool != null){
                    if("03".equals(maskNumBool.getStatus())){
                        maskNumResult.setId(maskNumBool.getId());
                        maskLineService.updateMaskNumber(maskNumResult);
                        //向redis 隐私号码池中添加号码
                        MakePoolRedis.getInstance().addMakeNum(maskNumResult.getAppid(),maskNumResult.getCityAreaCode().getAreaCode(),maskNumResult.getNumber());
                    }else if("02".equals(maskNumBool.getStatus())){
                        maskNumResult.setStatus("已锁定");
                        insertMaskNumErrorList.add(maskNumResult);
                    }else if("01".equals(maskNumBool.getStatus())){
                        maskNumResult.setStatus("已分配");
                        insertMaskNumErrorList.add(maskNumResult);
                    }
                }else{
                    maskNumResult.setId(ID.randomUUID());
                    maskLineService.addMaskNumber(maskNumResult);
                    //向redis 隐私号码池中添加号码
                    MakePoolRedis.getInstance().addMakeNum(maskNumResult.getAppid(),maskNumResult.getCityAreaCode().getAreaCode(),maskNumResult.getNumber());
                }
            }else{
                //将错误号码添加至错误的集合中
                maskNumResult.setStatus("号码格式不正确");
                insertMaskNumErrorList.add(maskNumResult);
            }
        }

        request.getSession().setAttribute("insertMaskNumErrorList",insertMaskNumErrorList);
        return insertMaskNumErrorList;
    }

    public Map<String, Object> downLoadExcel(List<MaskNum> insertMaskNumErrorList) {
        logger.info("--------------------------------MaskNumberController downLoadExcel start--------------------------------");
        List mapList = new ArrayList<Map<String, Object>>();
        for (MaskNum maskNum : insertMaskNumErrorList) {
            Map<String, Object> excelMap = new HashMap<>();
            excelMap.put("city", maskNum.getCityAreaCode().getCity());
            excelMap.put("areaCode", maskNum.getCityAreaCode().getAreaCode());
            excelMap.put("number", maskNum.getNumber());
            excelMap.put("status", maskNum.getStatus());
            mapList.add(excelMap);
        }

        List titles = new ArrayList<String>();
        titles.add("城市");
        titles.add("区号");
        titles.add("号码");
        titles.add("信息");

        List columns = new ArrayList<String>();
        columns.add("city");
        columns.add("areaCode");
        columns.add("number");
        columns.add("status");

        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("titles", titles);
        contentMap.put("columns", columns);
        contentMap.put("list", mapList);
        contentMap.put("excelName", "错误隐私号码信息");
        return contentMap;
    }
}
