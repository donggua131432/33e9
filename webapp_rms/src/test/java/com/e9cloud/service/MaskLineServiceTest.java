package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskLineService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
public class MaskLineServiceTest extends BaseTest {

    @Autowired
    private MaskLineService maskLineService;

    @Test
    public void testForDownload(){
        Page page = new Page();
        List<Map> mask = maskLineService.download(Mapper.MaskNum_Mapper.pageMaskNumber, page);
        mask.forEach((map) -> {
            map.forEach((k, v) -> {
                System.out.print(k + ":" + v + "\t");
            });
            System.out.println();
        });
    }

    @Test
    public void testGetMaskNumByObj() {

        MaskNum maskNum = new MaskNum();
        maskNum.setId("f6a4e46892b94111911da22f9fff7721");
        MaskNum m = maskLineService.getMaskNumberByObj(maskNum);

        logger.info(JSonUtils.toJSon(m));
    }

}
