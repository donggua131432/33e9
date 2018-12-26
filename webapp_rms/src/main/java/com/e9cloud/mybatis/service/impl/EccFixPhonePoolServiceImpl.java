package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.EccFixphone;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccFixPhonePoolService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/2/13.
 */
@Service
public class EccFixPhonePoolServiceImpl extends BaseServiceImpl implements EccFixPhonePoolService {
    @Override
    public PageWrapper getEccFixPhoneList(Page page) {
        return  this.page(Mapper.EccFixPhone_Mapper.pageEccFixPhoneList, page);
    }

    /**
     * ecc 非fixphone列表导出
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> downloadEccFixPhonePool(Page page) {
        return this.download(Mapper.EccFixPhone_Mapper.pageEccFixPhoneList, page);
    }

    @Override
    public List<EccFixphone> getEccFixphoneByNumber(String fixphone) {
        Map<String, Object> parmas = new HashMap<>();
        parmas.put("fixphone", fixphone);
        return this.findObjectListByMap(Mapper.EccFixPhone_Mapper.selectEccFixphoneByNumber, parmas);
    }

    @Override
    public void saveFixPhone(EccFixphone eccFixphone) {
        this.save(Mapper.EccFixPhone_Mapper.insertSelective, eccFixphone);
    }

    @Override
    public void deleteByPK(String id) {
        this.delete(Mapper.EccFixPhone_Mapper.deleteByPrimaryKey, id);
    }
}
