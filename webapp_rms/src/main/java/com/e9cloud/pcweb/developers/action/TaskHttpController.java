package com.e9cloud.pcweb.developers.action;

import com.e9cloud.core.common.ID;
import com.e9cloud.core.common.LogType;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.*;
import com.e9cloud.mybatis.domain.*;
import com.e9cloud.mybatis.service.TaskInfoService;
import com.e9cloud.pcweb.BaseController;
import com.e9cloud.pcweb.msg.util.TempCode;
import com.e9cloud.thirdparty.Sender;
import com.e9cloud.thirdparty.sms.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2016/12/22.
 */
@Controller
@RequestMapping("/openTask")
public class TaskHttpController extends BaseController{

    @Autowired
    public TaskInfoService taskInfoService;

//    @Autowired
//    public EexcuteTaskService excuteTaskService;


    @RequestMapping(value = "toTask")
    public String toTask() {
        return "developersMgr/openhttpTask";
    }

    @RequestMapping(value = "toShow", method = RequestMethod.GET)
    public String toShow(String taskID,Model model) {
        model.addAttribute("taskID",taskID);
        return "developersMgr/executeTask";
    }

    // 创建任务
    @RequestMapping(value = "toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return "developersMgr/addTask";
    }


    @RequestMapping(value = "addTask", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessageSubmit addTask(TaskHttp taskHttp, UrlHttp urlHttp,String url) throws Exception{
        logger.info("添加任务开始--------");

        Date date = new Date();
        String a = ID.randomUUID();
        taskHttp.setTaskId(a);
        taskHttp.setAddtime(date);
        taskInfoService.saveTaskHttp(taskHttp);

        String[] urlArr = url.split(",");
        for (int i = 0; i < urlArr.length; i++) {
            urlHttp.setUrl(urlArr[i]);
            urlHttp.setTaskId(a);
            urlHttp.setAddtime(date);
            taskInfoService.saveUrlHttp(urlHttp);
        }
        return new JSonMessageSubmit("0","信息保存成功！");
    }


    @RequestMapping(value = "updateTask", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessageSubmit updateTask(TaskHttp taskHttp, String urls,String ids) throws Exception{
        Date date = new Date();
        taskHttp.setUpdatetime(date);
        taskInfoService.updateTaskHttpList(taskHttp);

        String[] idArr = ids.split(",");
        String[] urlArr = urls.split(",");

        List<UrlHttp> urlHttpList = taskInfoService.queryListByDId(taskHttp.getTaskId());
//        for (int i = 0; i < idArr.length; i++) {
//            if (urlArr[i] =)
//        }

        for (UrlHttp a : urlHttpList){

            if (!Arrays.asList(idArr).contains(a.getId().toString())){
                taskInfoService.delUrlHttpInfo(a.getId().toString());
                System.out.print("需要删除"+a.getId());
            }
        }

        for (int i = 0; i < urlArr.length; i++) {
            UrlHttp urlHttp = new UrlHttp();
            if ("-1".equals(idArr[i])){
                urlHttp.setUrl(urlArr[i]);
                urlHttp.setTaskId(taskHttp.getTaskId());
                urlHttp.setAddtime(date);
                taskInfoService.saveUrlHttp(urlHttp);
            }else {
                urlHttp.setId(Integer.parseInt(idArr[i]));
                urlHttp.setUrl(urlArr[i]);
                urlHttp.setUpdatetime(date);
                taskInfoService.updateUrlHttp(urlHttp);
            }
        }
        return new JSonMessageSubmit("0","信息保存成功！");
    }



    /**
     * 分页查询开发者列表
     * @param page 分页信息
     * @return
     */
    @RequestMapping(value = "pageTask",method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageTaskList(Page page){

        return taskInfoService.pageTaskList(page);
    }


    // 编辑任务页面
    @RequestMapping(value = "toEdit", method = RequestMethod.GET)
    public String toEdit(String taskID, Model model) {
        List<UrlHttp> urlHttpList = taskInfoService.queryListByDId(taskID);
        TaskHttp taskHttp = taskInfoService.queryTaskHttpById(taskID);
        model.addAttribute("taskHttp", taskHttp);
        model.addAttribute("urlHttpList", urlHttpList);
        return "developersMgr/task_edit";
    }



    // 删除一个任务
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSonMessage delete(TaskHttp taskHttp,Integer id) {
        logger.info("-------------TaskHttpController delete start-------------");
        taskHttp.setId(id);
        taskHttp.setStatus("01");
        taskInfoService.updateTaskHttp(taskHttp);
        logger.info("-------------TaskHttpController delete end-------------");

        return new JSonMessage("ok", "删除任务完成");
    }



    /**
     * 分页展示当前任务下的所有URL信息
     * @param page 分页信息
     * @return
     */
    @RequestMapping(value = "pageURLList",method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageURLList(Page page,String taskID){
        page.addParams("taskID",taskID);
        return taskInfoService.pageURLList(page);

    }


    /**
     * 执行任务
     * @return
     */
    @RequestMapping("/executeTask")
    @ResponseBody
    public Map<String,String> executeTask(HttpServletRequest request, HttpServletResponse response,String id,String url,String task_id) throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        try{
            if(StringUtils.isEmpty(id)){
                map.put("code","01");
                return map;
            }
        }catch (Exception e){
            map.put("code","99");
            return  map;
        }
        ExecuteHttp Execute = taskInfoService.queryExecuteHttpById(id);
        if (Execute != null){
            Date a = Execute.getUpdatetime();
            Date b = new Date();
            long interval = b.getTime() - a.getTime() ;
            //5分钟之内  不能重复执行
            if (interval < 300000){
                map.put("code","88");
                return map;
            }
        }


        ExecuteHttp executeHttp = new ExecuteHttp();
        executeHttp.setTaskId(task_id);
        executeHttp.setUrl(url);
        executeHttp.setUrl_id(Integer.parseInt(id));
        executeHttp.setAddtime(new Date());
        executeHttp.setUpdatetime(new Date());
//        HttpUtil.get(url);
        executeHttp.setResult(HttpUtil.get(url));
        map.put("code","00");
        taskInfoService.saveExecuteHttp(executeHttp);

        return map;
    }
}
