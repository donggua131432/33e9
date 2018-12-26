package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AxbPhone;
import com.e9cloud.mybatis.domain.VoiceVerifyNumPool;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 语音验证码--公共资源号码池
 * Created by wzj on 2017/5/4.
 */
public interface VoiceVerifyNumPoolService extends IBaseService {

    /**
     * 分页查询
     * @param page 分页信息
     * @return
     */
    PageWrapper pageVoiceVerifyNumPool(Page page);

    /**
     * 导出
     * @param page
     * @return
     */
    List<Map<String,Object>> downloadVoiceVerifyNumPool(Page page);

    /**
     * 批量删除号码，并返回已经分配的号码
     * @param ids 号码id
     * @return
     */
    List<String> deletePhones(String[] ids);

    /**
     * 添加号码
     * @param numPool
     */
    void saveNum(VoiceVerifyNumPool numPool);

    /**
     * 校验号码
     * @param numPool
     * @return
     */
    JSonMessage checkNumber(VoiceVerifyNumPool numPool);

    List<VoiceVerifyNumPool> savePhoneExcel(MultipartFile file, HttpServletRequest request) throws Exception;
}
