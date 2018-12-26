package com.e9cloud.mybatis.service;

import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.TelnoCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hzd on 2017/4/18.
 */
@Service
public class AxbNumImportService {

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private AxbNumResPoolService axbNumResPoolService;

    /**
     * 处理excel中的数据信息
     * @param AxbPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<AxbPhone> saveAxbPhoneExcel(MultipartFile AxbPhoneFile, HttpServletRequest request) throws Exception{
        List<AxbPhone> axbPhoneList = new ArrayList<>();
        InputStream is = AxbPhoneFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for(Integer key : mapFile.keySet()){
            String[] arrayStr = mapFile.get(key).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                //设置axbnumber信息
                AxbPhone axbPhone = new AxbPhone();
                axbPhone.setAreacode(arrayStr[0]);
                axbPhone.setNumber(arrayStr[1]);

                axbPhoneList.add(axbPhone);
            }

        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<AxbPhone> insertAxbPhoneErrorList = new ArrayList<>();
        for (AxbPhone axb : axbPhoneList) {

            // 校验区号
            if (Tools.isNullStr(axb.getAreacode())) {
                axb.setImportCommon("区号不能为空");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            // 校验号码
            if (Tools.isNullStr(axb.getNumber())) {
                axb.setImportCommon("号码不能为空");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(axb.getAreacode());
            if (telnoCity == null) {
                axb.setImportCommon("区号不存在");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            // 设置城市主键
            axb.setCityid(telnoCity.getCcode());

            // 校验公共号码
            isNum = pattern.matcher(axb.getNumber());
            //判断号码是否合法
            if (!isNum.matches()) {
                axb.setImportCommon("虚拟号码格式不正确");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            List<AxbPhone> axbPhone = axbNumResPoolService.getAxbNumResPoolByAxbPhone(axb);
            //数据库有此公共号码
            if(axbPhone.size() > 0){
                axb.setImportCommon("虚拟号码已存在");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            //判断区号，号码是否匹配
            boolean checkAxbNumber = axbNumResPoolService.checkAxbNumberMatchAreacode(axb);
            if(checkAxbNumber){
                axb.setImportCommon("区号号码不匹配");
                insertAxbPhoneErrorList.add(axb);
                continue;
            }

            //数据库无此公共号码,直接入库
            axbNumResPoolService.addAxbPhone(axb);
        }

        request.getSession().setAttribute("insertAxbPhoneErrorList", insertAxbPhoneErrorList);

        return insertAxbPhoneErrorList;
    }
}
