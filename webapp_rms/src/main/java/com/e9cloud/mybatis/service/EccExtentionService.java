package com.e9cloud.mybatis.service;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.EccExtention;
import com.e9cloud.mybatis.domain.EccShownum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ecc 分机号
 * Created by Administrator on 2017/2/13.
 */
public interface EccExtentionService extends IBaseService {

    /**
     * 根据appid分页查询分机号
     * @param page page分页信息
     * @return 分页信息
     */
    PageWrapper pageExtention(Page page);

    /**
     * 开启禁用长途、开启禁用号码
     * @param eccExtention
     */
    void updateSubNumStatus(EccExtention eccExtention);

    /**
     * 批量删除分机号
     * @param id
     */
    void deleteSubNums(String[] id);

    /**
     * 获取下载列表
     * @param page
     * @return
     */
    List<Map<String,Object>> downloadExtention(Page page);

    /**
     * 核对外显号码
     * @param appid appid
     * @param eccid eccid
     * @param showNum 外显号
     * @return
     */
    JSonMessage checkShowNum(String appid, String eccid, String showNum, String extid);

    /**
     * 核对分机号码
     * @param eccid eccid
     * @param subNum 分机号码
     * @return
     */
    JSonMessage checkSubNum(String eccid, String subNum, String extid);

    /**
     * 核对接听号码
     * @param eccid eccid
     * @param numType 号码类型
     * @param phone 号码
     * @return
     */
    JSonMessage checkPhone(String eccid, String numType, String phone, String extid);

    /**
     * 添加分机号
     * @param eccExtention
     */
    void saveSubNum(EccExtention eccExtention);

    List<EccExtention> saveEccSubNumExcel(MultipartFile eccShowNumFile, HttpServletRequest request) throws IOException;

    EccExtention getExtentionByPK(String id);

    void editSubNum(EccExtention eccExtention);

    long countExtentionByEccId(String id);


    List<Map<String, Object>> getExtentionNumList(String appid);
}
