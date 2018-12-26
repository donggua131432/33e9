package com.e9cloud.pcweb.relaygroup.biz;

import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.RelayGroup1;
import com.e9cloud.mybatis.domain.RelayGroup2;
import com.e9cloud.mybatis.domain.RelayGroup3;
import com.e9cloud.mybatis.service.RelayGroup1Service;
import com.e9cloud.mybatis.service.RelayGroup2Service;
import com.e9cloud.mybatis.service.RelayGroup3Service;
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
 * 中继群业务处理
 * Created by Administrator on 2016/9/12.
 */
@Service
public class RelayGroupService {

    @Autowired
    private RelayGroup1Service relayGroup1Service;

    @Autowired
    private RelayGroup2Service relayGroup2Service;

    @Autowired
    private RelayGroup3Service relayGroup3Service;

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<RelayGroup1> saveExcel1(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<RelayGroup1> relayGroups = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();

        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);

        for (int i=1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置SIP选号信息
            RelayGroup1 relayGroup = new RelayGroup1();

            relayGroup.setTgNum(arrayStr[0]);
            relayGroup.setTgName(arrayStr[1]);
            relayGroup.setCityName(arrayStr[2]);
            relayGroup.setAreaCode(arrayStr[3]);
            relayGroup.setCodeStart(arrayStr[4]);
            relayGroup.setCodeEnd(arrayStr[5]);

            relayGroups.add(relayGroup);
        }

        Pattern xhTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern destTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern typePattern = Pattern.compile("[0-9]*");
        Pattern rnPattern = Pattern.compile("[0-9]*");

        Matcher xhTelnoMatcher = null;
        Matcher destTelnoMatcher = null;
        Matcher typeMatcher = null;
        Matcher rnMatcher = null;
        Boolean remarkLength =false;

        List<RelayGroup1> group1ErrorList = new ArrayList<>();

        for (RelayGroup1 rg : relayGroups) {
            relayGroup1Service.saveRelayGroup1(rg);
        }

        return group1ErrorList;
    }

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<RelayGroup2> saveExcel2(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<RelayGroup2> relayGroups = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();

        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);

        for (int i=1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置SIP选号信息
            RelayGroup2 relayGroup = new RelayGroup2();

            relayGroup.setTgNum(arrayStr[0]);
            relayGroup.setTgName(arrayStr[1]);
            relayGroup.setOperatorCode(arrayStr[2]);
            relayGroup.setOperatorName(arrayStr[3]);
            relayGroup.setCalledPre(arrayStr[4]);

            relayGroups.add(relayGroup);
        }

        Pattern xhTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern destTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern typePattern = Pattern.compile("[0-9]*");
        Pattern rnPattern = Pattern.compile("[0-9]*");

        Matcher xhTelnoMatcher = null;
        Matcher destTelnoMatcher = null;
        Matcher typeMatcher = null;
        Matcher rnMatcher = null;
        Boolean remarkLength =false;

        List<RelayGroup2> group1ErrorList = new ArrayList<>();

        for (RelayGroup2 rg : relayGroups) {
            relayGroup2Service.saveRelayGroup2(rg);
        }

        return group1ErrorList;
    }

    /**
     * 处理excel中的数据信息
     * @param sipNumFile
     * @param request
     * @return
     * @throws Exception
     */
    public List<RelayGroup3> saveExcel3(MultipartFile sipNumFile, HttpServletRequest request) throws Exception{
        List<RelayGroup3> relayGroups = new ArrayList<>();
        InputStream is = sipNumFile.getInputStream();

        // 对读取Excel表格内容测试
        ExcelReader excelReader = new ExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);

        for (int i=1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split("-");

            //设置SIP选号信息
            RelayGroup3 relayGroup = new RelayGroup3();

            relayGroup.setTgNum(arrayStr[0]);
            relayGroup.setTgName(arrayStr[1]);
            relayGroup.setCodeStart(arrayStr[2]);
            relayGroup.setCodeEnd(arrayStr[3]);

            relayGroups.add(relayGroup);
        }

        Pattern xhTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern destTelnoPattern = Pattern.compile("[0-9]*|[*]?");
        Pattern typePattern = Pattern.compile("[0-9]*");
        Pattern rnPattern = Pattern.compile("[0-9]*");

        Matcher xhTelnoMatcher = null;
        Matcher destTelnoMatcher = null;
        Matcher typeMatcher = null;
        Matcher rnMatcher = null;
        Boolean remarkLength =false;

        List<RelayGroup3> group1ErrorList = new ArrayList<>();

        for (RelayGroup3 rg : relayGroups) {
            relayGroup3Service.saveRelayGroup3(rg);
        }

        return group1ErrorList;
    }
}
