package com.e9cloud.pcweb.msg.action;

import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.SysMessage;
import com.e9cloud.mybatis.service.SysMessageService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.redis.util.RedisDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("query")
    public String query() {
        return "msg/index";
    }

    @RequestMapping("pageMsg")
    @ResponseBody
    public PageWrapper pageMsg(HttpServletRequest request, Page page, Model model) {
        logger.info("=================SysMessageController pageMsg start====================");
        page.addParams("uid", this.getCurrUserId(request));
        return sysMessageService.pageMsg(page);
    }

    @RequestMapping("getcount")
    @ResponseBody
    public Map<String, Object> getcount (HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        String countA = sysMessageService.countHadRead(this.getCurrUserId(request));
        String countB = sysMessageService.countUnRead(this.getCurrUserId(request));
        int countAll = Integer.parseInt(countA) +Integer.parseInt(countB) ;
        map.put("countA",countA);
        map.put("countB",countB);
        map.put("countAll",countAll);

        if (this.getAttributeFromSession(request, has_read) != null) {
            map.put(has_read, "Y");
        } else {
            this.addAttributeToSession(request, has_read, true);
        }

        return map;
    }

    //批量将消息标为已读
    @RequestMapping("updateStatus")
    @ResponseBody
    public Map<String,String> updateStatus (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        String[] aa = strId.split(",");
        sysMessageService.updateStatusBylink(aa);
        map.put("code","00");
        return map;
    }

   //批量删除消息
    @RequestMapping("deleteMessage")
    @ResponseBody
    public Map<String,String> deleteMessage (HttpServletRequest request) {
        Map<String,String> map = new HashMap<String,String>();
        String strId = request.getParameter("strId");
        if (StringUtils.isEmpty(strId)){
            map.put("code","99");
            return map;
        }
        String[] aa = strId.split(",");
        sysMessageService.deleteStatusBylink(aa);
        map.put("code","00");
        return map;
    }

    //点击链接   消息状态变为已读
    @RequestMapping("readMessage")
    @ResponseBody
    public Map<String, Object> readMessage (HttpServletRequest request, String strId) {
        Map<String, Object> map = new HashMap<String, Object>();
        sysMessageService.readUpStatus(strId);
        SysMessage message = sysMessageService.getSysMessageById(strId);

        map.put("msg", message);
        return map;
    }

}
