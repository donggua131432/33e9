package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.util.RegexUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.EccSipphone;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.PubNumResPoolService;
import com.e9cloud.mybatis.service.SipNumResPoolService;
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
 * Created by dell on 2017/2/13.
 */
@Service
public class EccSipPhoneImportService {

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private SipNumResPoolService sipNumResPoolService;

    @Autowired
    private PubNumResPoolService pubNumResPoolService;

    /**
     * 处理excel中的数据信息
     * @param SipPhoneFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<EccSipphone> saveSipPhoneExcel(MultipartFile SipPhoneFile, HttpServletRequest request) throws Exception{
        List<EccSipphone> eccSipPhoneList = new ArrayList<>();
        InputStream is = SipPhoneFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1]) || Tools.isNotNullStr(arrayStr[2])
                    || Tools.isNotNullStr(arrayStr[3]) || Tools.isNotNullStr(arrayStr[4])) {
                //设置sipphone信息
                EccSipphone eccSipPhone = new EccSipphone();
                eccSipPhone.setAreaCode(arrayStr[0]);
                eccSipPhone.setSipPhone(arrayStr[1]);
                eccSipPhone.setPwd(arrayStr[2]);
                eccSipPhone.setSipRealm(arrayStr[3]);
                eccSipPhone.setIpPort(arrayStr[4]);

                eccSipPhoneList.add(eccSipPhone);
            }

        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<EccSipphone> insertEccSipPhoneErrorList = new ArrayList<>();
        for (EccSipphone sp : eccSipPhoneList) {

            // 校验区号
            if (Tools.isNullStr(sp.getAreaCode())) {
                sp.setImportCommon("区号不能为空");
                insertEccSipPhoneErrorList.add(sp);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(sp.getAreaCode());
            if (telnoCity == null) {
                sp.setImportCommon("区号不存在");
                insertEccSipPhoneErrorList.add(sp);
                continue;
            }

            // 设置城市主键
            sp.setCityid(telnoCity.getCcode());

            // 校验SIP号码
            isNum = pattern.matcher(sp.getSipPhone());
            //判断号码是否合法
            if (!isNum.matches()) {
                sp.setImportCommon("号码格式不正确");
                insertEccSipPhoneErrorList.add(sp);
                continue;
            }

            if (sp.getSipPhone().indexOf(sp.getAreaCode()) != 0) {
                sp.setImportCommon("号码区号不正确");
                insertEccSipPhoneErrorList.add(sp);
                continue;
            }

            List<EccSipphone> sipPhone = sipNumResPoolService.getSipNumResPoolBySipPhone(sp.getSipPhone());
            //数据库有此sip号码
            if(sipPhone.size() > 0){
                sp.setImportCommon("号码已存在");
                insertEccSipPhoneErrorList.add(sp);
                continue;
            }

            //检查sip号码是否需要注册（true为需要）
            boolean flag = pubNumResPoolService.checkRegisterSipphone(sp.getSipPhone());
            if (flag == true){
                // 校验认证密码
                if (!RegexUtils.chechAnyChar(sp.getPwd(), 1, 20)) {
                    sp.setImportCommon("认证密码格式不正确");
                    insertEccSipPhoneErrorList.add(sp);
                    continue;
                }

                // 校验SIP Realm
                if (!RegexUtils.chechAnyChar(sp.getSipRealm(), 1, 20)) {
                    sp.setImportCommon("SIP REALM格式不正确");
                    insertEccSipPhoneErrorList.add(sp);
                    continue;
                }

                // 校验ipport
                if (!RegexUtils.checkIpAndPort(sp.getIpPort())) {
                    sp.setImportCommon("IP:Port格式不正确");
                    insertEccSipPhoneErrorList.add(sp);
                    continue;
                }
            }else {
                sp.setPwd("");
                sp.setSipRealm("");
                sp.setIpPort("");
            }


            //数据库无此sip号码,直接入库
            sipNumResPoolService.addSipPhone(sp);
        }

        request.getSession().setAttribute("insertEccSipPhoneErrorList", insertEccSipPhoneErrorList);

        return insertEccSipPhoneErrorList;
    }
}
