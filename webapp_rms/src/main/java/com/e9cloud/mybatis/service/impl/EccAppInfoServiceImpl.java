package com.e9cloud.mybatis.service.impl;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.EccAppInfo;
import com.e9cloud.mybatis.domain.EccSwitchboard;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.EccAppInfoService;
import com.e9cloud.mybatis.service.EccSwitchBoardPoolService;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
@Service
public class EccAppInfoServiceImpl extends BaseServiceImpl implements EccAppInfoService {

    @Autowired
    private EccSwitchBoardPoolService switchBoardPoolService;

    @Override
    public EccAppInfo getEccAppInfoByAppid(String appid) {
        return this.findObjectByPara(Mapper.EccAppInfo_Mapper.selectEccAppInfoByAppid, appid);
    }

    @Override
    public EccAppInfo getEccAppInfoByPK(String eccid) {
        return this.findObjectByPara(Mapper.EccAppInfo_Mapper.selectByPrimaryKey, eccid);
    }

    @Override
    public JSonMessage checkSwitchboard(String appid, String eccid, String cityid, String switchboard) {
        JSonMessage jSonMessage = new JSonMessage();

        List<EccSwitchboard> switchboards = switchBoardPoolService.getEccSwitchBoardByNumber(switchboard);
        if (switchboards == null || switchboards.size() == 0) {
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("总机号码不存在");
        } else if (switchboards.size() > 1){
            jSonMessage.setCode(R.ERROR);
            jSonMessage.setMsg("存在多个总机号码");
        } else {
            EccSwitchboard board = switchboards.get(0);
            jSonMessage.setCode(R.OK);
            jSonMessage.setMsg(board.getId());

            if (!Tools.eqStr(appid, board.getAppid())) {
                if (Tools.isNotNullStr(board.getAppid())) {
                    jSonMessage.setCode(R.ERROR);
                    jSonMessage.setMsg("总机号码已被占用");
                }
            }

            if (!Tools.eqStr(cityid, board.getCityid())) {
                jSonMessage.setCode(R.ERROR);
                jSonMessage.setMsg("总机号码所在城市不匹配");
            }
        }

        return jSonMessage;
    }

    @Override
    public String saveOrUpdateIVRAndReturnId(EccAppInfo eccInfo) {
        if (Tools.isNotNullStr(eccInfo.getId())) { // 编辑
            EccAppInfo oldInfo = getEccAppInfoByPK(eccInfo.getId());
            if (!Tools.eqStr(oldInfo.getSwitchboardId(), eccInfo.getSwitchboardId())) {
                //
                EccSwitchboard board = new EccSwitchboard();
                board.setId(oldInfo.getSwitchboardId());
                board.setStatus("03");
                board.setEccid("");
                board.setAppid("");
                switchBoardPoolService.updateEccSwitchBoard(board);

                //
                board.setId(eccInfo.getSwitchboardId());
                board.setStatus("01");
                board.setEccid(eccInfo.getId());
                board.setAppid(eccInfo.getAppid());
                switchBoardPoolService.updateEccSwitchBoard(board);
            }
            this.update(Mapper.EccAppInfo_Mapper.updateByPrimaryKeySelective, eccInfo);

        } else { // 新增
            String eccid = ID.randomUUID();
            eccInfo.setId(eccid);
            this.save(Mapper.EccAppInfo_Mapper.insertSelective, eccInfo);

            EccSwitchboard board = new EccSwitchboard();
            board.setId(eccInfo.getSwitchboardId());
            board.setStatus("01");
            board.setEccid(eccid);
            board.setAppid(eccInfo.getAppid());
            switchBoardPoolService.updateEccSwitchBoard(board);
        }
        return eccInfo.getId();
    }
}
