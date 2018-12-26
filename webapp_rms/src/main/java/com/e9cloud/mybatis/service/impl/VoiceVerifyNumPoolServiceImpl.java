package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.office.AppointLinkExcelReader;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.RegexUtils;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.TelnoCity;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.mybatis.domain.VoiceVerifyNumPool;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.CityManagerService;
import com.e9cloud.mybatis.service.TelnoInfoService;
import com.e9cloud.mybatis.service.VoiceVerifyNumPoolService;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 语音验证码--公共资源号码池
 * Created by wzj on 2017/5/4.
 */
@Service
public class VoiceVerifyNumPoolServiceImpl extends BaseServiceImpl implements VoiceVerifyNumPoolService {

    @Autowired
    private TelnoInfoService telnoInfoService;

    @Autowired
    private CityManagerService cityManagerService;

    /**
     * 分页查询
     * @param page 分页信息
     * @return
     */
    @Override
    public PageWrapper pageVoiceVerifyNumPool(Page page) {
        return this.page(Mapper.VoiceVerifyNumPool_Mapper.pageVoiceVerifyNumPool, page);
    }

    /**
     * 导出
     * @param page 分页信息
     * @return
     */
    @Override
    public List<Map<String,Object>> downloadVoiceVerifyNumPool(Page page){
        return this.download(Mapper.VoiceVerifyNumPool_Mapper.pageVoiceVerifyNumPool, page);
    }

    /**
     * 批量删除号码，并返回已经分配的号码
     *
     * @param ids 号码id
     * @return
     */
    @Override
    public List<String> deletePhones(String[] ids) {
        List<VoiceVerifyNumPool> numPools = this.findObjectList(Mapper.VoiceVerifyNumPool_Mapper.selectNumByIds, ids);
        if (!Tools.isEmptyList(numPools)) {
            List<String> id = numPools.stream().filter(vNum -> "00".equals(vNum.getStatus())).map(VoiceVerifyNumPool::getId).collect(Collectors.toList());
            if (!Tools.isEmptyList(id)){
                this.update(Mapper.VoiceVerifyNumPool_Mapper.updateToDelStatus, id);
            }
            return numPools.stream().filter(vNum -> "01".equals(vNum.getStatus())).map(VoiceVerifyNumPool::getNumber).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 添加号码
     *
     * @param numPool
     */
    @Override
    public void saveNum(VoiceVerifyNumPool numPool) {
        this.save(Mapper.VoiceVerifyNumPool_Mapper.insertSelective, numPool);
    }

    /**
     * 添加号码
     *
     * @param number
     */
    public long countNumbyNumber(String number) {
        return this.findObjectByPara(Mapper.VoiceVerifyNumPool_Mapper.countNumbyNumber, number);
    }

    /**
     * 校验号码
     *
     * @param numPool
     * @return
     */
    @Override
    public JSonMessage checkNumber(VoiceVerifyNumPool numPool) {
        if (countNumbyNumber(numPool.getNumber()) > 0) {
            return new JSonMessage(R.ERROR, "该号码已经存在");
        }

        if (RegexUtils.checkMobile(numPool.getNumber())) {
            TelnoInfo telnoInfo = telnoInfoService.getTelnoInfoByTelno(numPool.getNumber().substring(0,7));
            if (telnoInfo == null) {
                return new JSonMessage(R.ERROR, "号码没有匹配到城市");
            }
            if (!telnoInfo.getCcode().equals(numPool.getCityid())){
                return new JSonMessage(R.ERROR, "号码和城市不匹配");
            }
        }

        return new JSonMessage(R.OK, "");
    }

    /**
     * 处理excel中的数据信息
     * @param file
     * @param request
     * @return
     * @throws Exception
     */
    public List<VoiceVerifyNumPool> savePhoneExcel(MultipartFile file, HttpServletRequest request) throws Exception{
        List<VoiceVerifyNumPool> poolList = new ArrayList<>();
        InputStream is = file.getInputStream();

        // 对读取Excel表格内容测试
        AppointLinkExcelReader excelReader = new AppointLinkExcelReader();
        Map<Integer, String> mapFile = excelReader.readExcelContent(is);
        for(Integer key : mapFile.keySet()){
            String[] arrayStr = mapFile.get(key).split("-");
            if (Tools.isNotNullStr(arrayStr[0]) || Tools.isNotNullStr(arrayStr[1])) {
                //设置axbnumber信息
                VoiceVerifyNumPool phone = new VoiceVerifyNumPool();
                phone.setAreacode(arrayStr[0]);
                phone.setNumber(arrayStr[1]);

                poolList.add(phone);
            }

        }
        Pattern pattern = Pattern.compile("[0-9]{5,30}");

        Matcher isNum = null;
        List<VoiceVerifyNumPool> insertPhoneErrorList = new ArrayList<>();
        for (VoiceVerifyNumPool numPool : poolList) {

            // 校验区号
            if (Tools.isNullStr(numPool.getAreacode())) {
                numPool.setImportCommon("区号不能为空");
                insertPhoneErrorList.add(numPool);
                continue;
            }

            // 校验号码
            if (Tools.isNullStr(numPool.getNumber())) {
                numPool.setImportCommon("号码不能为空");
                insertPhoneErrorList.add(numPool);
                continue;
            }

            // 校验区号
            TelnoCity telnoCity = cityManagerService.getTelnoCityByAreaCode(numPool.getAreacode());
            if (telnoCity == null) {
                numPool.setImportCommon("区号不存在");
                insertPhoneErrorList.add(numPool);
                continue;
            }

            // 设置城市主键
            numPool.setCityid(telnoCity.getCcode());

            // 校验公共号码
            isNum = pattern.matcher(numPool.getNumber());
            //判断号码是否合法
            if (!isNum.matches()) {
                numPool.setImportCommon("号码格式不正确");
                insertPhoneErrorList.add(numPool);
                continue;
            }

            // 95 号码跳过
            if (!RegexUtils.check95Num(numPool.getNumber())) {

                // 手机号码跳过
                if (!RegexUtils.checkMobile(numPool.getNumber())) {
                    if(!numPool.getNumber().startsWith(telnoCity.getAreaCode())){
                        numPool.setImportCommon("号码和城市不匹配");
                        insertPhoneErrorList.add(numPool);
                        continue;
                    }
                }

                JSonMessage jSonMessage = checkNumber(numPool);
                if (!R.OK.equals(jSonMessage.getCode())) {
                    numPool.setImportCommon(jSonMessage.getMsg());
                    insertPhoneErrorList.add(numPool);
                    continue;
                }

            }

            //数据库无此公共号码,直接入库
            numPool.setId(ID.randomUUID());
            saveNum(numPool);
        }

        request.getSession().setAttribute("insertPhoneErrorList", insertPhoneErrorList);

        return insertPhoneErrorList;
    }
}
