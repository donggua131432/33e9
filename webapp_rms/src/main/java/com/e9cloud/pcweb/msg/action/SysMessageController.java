package com.e9cloud.pcweb.msg.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.JSonUtils;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.UserUtil;
import com.e9cloud.mybatis.domain.DicData;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.service.IDicDataService;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.pcweb.BaseController;
import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 消息管理
 *
 * Created by Administrator on 2016/7/6.
 */
@Controller
@RequestMapping("/msgMgr")
public class SysMessageController extends BaseController {

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private IDicDataService dicDataService;

    @RequestMapping("query")
    public String query(Model model) {
        List<DicData> dicDatas = dicDataService.findDicListByType("msgCode");
        model.addAttribute("dicDatas", dicDatas);
        model.addAttribute("dds", JSonUtils.toJSon(dicDatas));
        return "msg/index";
    }

    @RequestMapping("pageMsg")
    @ResponseBody
    public PageWrapper pageMsg(HttpServletRequest request, Page page) {
        logger.info("=================SysMessageController pageMsg start====================");
        return sysMessageService.pageMsgTemp(page);
    }

    /**
     * 查看一条消息
     * @return
     */
    @RequestMapping("toShowMsg")
    public String toShowMsg(Model model, String id) {

        SysMessage msg = sysMessageService.getSysMessageTempById(id);
        model.addAttribute("msg", msg);
        model.addAttribute("dicDatas", dicDataService.findDicListByType("msgCode"));

        return "msg/show";
    }

    /**
     * 添加一条消息
     * @return
     */
    @RequestMapping("toAddMsg")
    public String toAddMsg(Model model) {
        List<DicData> dicDatas = dicDataService.findDicListByType("msgCode");
        model.addAttribute("dicDatas", dicDatas);
        return "msg/add";
    }

    /**
     * 添加一条消息
     * @return
     */
    @RequestMapping(value = "addMsg", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage addMsg(SysMessage message) {
        sysMessageService.addMsgTemp(message);
        return new JSonMessage(R.OK, "");
    }
}
