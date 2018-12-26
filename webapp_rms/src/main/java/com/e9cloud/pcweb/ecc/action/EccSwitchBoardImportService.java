package com.e9cloud.pcweb.ecc.action;

import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.EccShownum;
import com.e9cloud.mybatis.domain.EccSwitchboard;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.EccSwitchBoardPoolService;
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
public class EccSwitchBoardImportService {

    @Autowired
    private CityManagerService cityManagerService;

    @Autowired
    private EccSwitchBoardPoolService eccSwitchBoardPoolService;

    /**
     * 处理excel中的数据信息
     * @param SwitchBoardFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<EccSwitchboard> saveEccSwitchBoardExcel(MultipartFile SwitchBoardFile, HttpServletRequest request) throws Exception{
        List<EccSwitchboard> eccSwitchBoardList = new ArrayList<>();
        InputStream is = SwitchBoardFile.getInputStream();
        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1]) ) {
                //设置sipphone信息
                EccSwitchboard eccSwitchboard = new EccSwitchboard();
                eccSwitchboard.setAreaCode(arrayStr[0]);
                eccSwitchboard.setNumber(arrayStr[1]);


                eccSwitchBoardList.add(eccSwitchboard);
            }

        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<EccSwitchboard> insertEccSwitchBoardErrorList = new ArrayList<>();
        for (EccSwitchboard sp : eccSwitchBoardList) {

            // 校验区号
            if (Tools.isNullStr(sp.getAreaCode())) {
                sp.setImportCommon("区号不能为空");
                insertEccSwitchBoardErrorList.add(sp);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(sp.getAreaCode());
            if (telnoCity == null) {
                sp.setImportCommon("区号不存在");
                insertEccSwitchBoardErrorList.add(sp);
                continue;
            }

            // 设置城市主键
            sp.setCityid(telnoCity.getCcode());

            // 校验SIP号码
            isNum = pattern.matcher(sp.getNumber());
            //判断号码是否合法
            if (!isNum.matches()) {
                sp.setImportCommon("号码格式不正确");
                insertEccSwitchBoardErrorList.add(sp);
                continue;
            }

            if (sp.getNumber().indexOf(sp.getAreaCode()) != 0) {
                sp.setImportCommon("号码区号不正确");
                insertEccSwitchBoardErrorList.add(sp);
                continue;
            }

            List<EccSwitchboard> eccSwitchboard = eccSwitchBoardPoolService.getEccSwitchBoardByNumber(sp.getNumber());
            //数据库有此sip号码
            if(eccSwitchboard.size() > 0){
                sp.setImportCommon("号码已存在");
                insertEccSwitchBoardErrorList.add(sp);
                continue;
            }

            //数据库无此sip号码,直接入库
            eccSwitchBoardPoolService.addEccSwitchBoard(sp);
        }

        request.getSession().setAttribute("insertEccSwitchBoardErrorList", insertEccSwitchBoardErrorList);

        return insertEccSwitchBoardErrorList;
    }
}
