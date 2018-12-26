package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.Sipurl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/14.
 */
public interface SipurlService extends IBaseService {

    PageWrapper pageSipurl(Page page);

    /**
     * 添加一条记录
     * @param sipurl
     */
    void addSipUrl(Sipurl sipurl);

    /**
     * 校验号码的唯一性
     * @param sipurl
     * @return
     */
    long checkNum(Sipurl sipurl);

    /**
     * 根据id删除SipUrl
     * @param id
     */
    void delSipUrlByPK(String id);

    List<Map<String, Object>> downloadSipUrl(Page page);

    Sipurl getSipurlByPK(String id);

    void updateSipUrl(Sipurl sipurl);

    List<Sipurl> saveSipUrlExcel(MultipartFile file, HttpServletRequest request) throws IOException;
}
