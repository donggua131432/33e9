package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.RegexUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.SipPhone;
import com.e9cloud.mybatis.domain.Sipurl;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.SipurlService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/14.
 */
@Service
public class SipurlServiceImpl extends BaseServiceImpl implements SipurlService {

    @Override
    public PageWrapper pageSipurl(Page page) {
        return this.page(Mapper.Sipurl_Mapper.pageSipurl, page);
    }

    @Override
    public void addSipUrl(Sipurl sipurl) {
        sipurl.setId(ID.randomUUID());
        this.save(Mapper.Sipurl_Mapper.insertSelective, sipurl);
    }

    /**
     * 校验号码的唯一性
     *
     * @param sipurl
     * @return
     */
    @Override
    public long checkNum(Sipurl sipurl) {
        return this.findObject(Mapper.Sipurl_Mapper.countNum, sipurl);
    }

    /**
     * 根据id删除SipUrl
     *
     * @param id
     */
    @Override
    public void delSipUrlByPK(String id) {
        this.delete(Mapper.Sipurl_Mapper.deleteByPrimaryKey, id);
    }

    @Override
    public List<Map<String, Object>> downloadSipUrl(Page page) {
        return this.download(Mapper.Sipurl_Mapper.pageSipurl, page);
    }

    @Override
    public Sipurl getSipurlByPK(String id) {
        return this.findObjectByPara(Mapper.Sipurl_Mapper.selectByPrimaryKey, id);
    }

    @Override
    public void updateSipUrl(Sipurl sipurl) {
        this.update(Mapper.Sipurl_Mapper.updateByPrimaryKeySelective, sipurl);
    }

    @Override
    public List<Sipurl> saveSipUrlExcel(MultipartFile file, HttpServletRequest request) throws IOException {
        List<Sipurl> sipurlList = new ArrayList<>();
        InputStream is = file.getInputStream();
        // 对读取Excel表格内容测试
        String split = "~";
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is, split);
        for (int i = 1; i <= mapFile.size(); i++) {
            String[] arrayStr = mapFile.get(i).split(split);
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                Sipurl sipurl = new Sipurl();
                sipurl.setSipurl(arrayStr[0]);
                sipurl.setNum(arrayStr[1]);

                sipurlList.add(sipurl);
            }
        }

        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<Sipurl> errorList = new ArrayList<>();
        for (Sipurl sp : sipurlList) {

            // 校验sipurl
            if (Tools.isNullStr(sp.getSipurl())) {
                sp.setImportCommon("SipUrl不能为空");
                errorList.add(sp);
                continue;
            }

            // 校验sipurl
            if (!RegexUtils.checkSIPURL(sp.getSipurl())) {
                sp.setImportCommon("SipUrl格式不正确");
                errorList.add(sp);
                continue;
            }

            // 校验num
            isNum = pattern.matcher(sp.getNum());

            //判断号码是否合法
            if (!isNum.matches()) {
                sp.setImportCommon("号码格式不正确");
                errorList.add(sp);
                continue;
            }

            long cnt = this.checkNum(sp);
            if (cnt != 0) {
                sp.setImportCommon("号码已存在");
                errorList.add(sp);
                continue;
            }

            this.addSipUrl(sp);
        }

        request.getSession().setAttribute("insertSipUrlErrorList", errorList);

        return errorList;
    }
}
