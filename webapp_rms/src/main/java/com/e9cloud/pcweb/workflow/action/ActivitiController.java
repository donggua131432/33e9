package com.e9cloud.pcweb.workflow.action;

import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessage;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.activiti.cmd.JumpActivityCmd;
import com.e9cloud.mybatis.activiti.service.WorkflowProcessDefinitionService;
import com.e9cloud.mybatis.activiti.service.WorkflowTraceService;
import com.e9cloud.mybatis.activiti.util.WorkflowUtils;

import com.e9cloud.mybatis.domain.WorkFlow;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * 流程管理控制器
 *
 * @author HenryYan
 */
@Controller
@RequestMapping(value = "/workflow")
public class ActivitiController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected WorkflowProcessDefinitionService workflowProcessDefinitionService;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected WorkflowTraceService traceService;

    @Autowired
    ManagementService managementService;

    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    @RequestMapping("toWorkflow")
    public String toWorkflow() {
        return "workflow/list";
    }

    @RequestMapping("toDeploy")
    public String toDeploy() {
        return "workflow/deploy";
    }

    /**
     * 流程定义列表
     *
     * @return
     */
    @RequestMapping(value = "/pageWorkflow", method = RequestMethod.POST)
    @ResponseBody
    public PageWrapper pageWorkflow(com.e9cloud.core.page.Page page) {

        List<WorkFlow> list = new ArrayList<WorkFlow>();

        ProcessDefinitionQuery pDQ = repositoryService.createProcessDefinitionQuery();
        page.setTotal(pDQ.count());

        pDQ.orderByDeploymentId().desc().listPage(page.getStart(), page.getPageSize()).forEach((processDefinition) -> {
            WorkFlow workFlow = new WorkFlow();

            workFlow.setName(processDefinition.getName());
            workFlow.setKey(processDefinition.getKey());
            workFlow.setVersion(processDefinition.getVersion());
            workFlow.setResourceName(processDefinition.getResourceName());
            workFlow.setDiagramResourceName(processDefinition.getDiagramResourceName());

            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            workFlow.setDeploymentId(deploymentId);
            workFlow.setDeploymentTime(Tools.formatDate(deployment.getDeploymentTime()));

            list.add(workFlow);
    });

    return new PageWrapper(page.getDraw(), page.getPage(), page.getPageSize(), page.getTotal(), list);

}

    /**
     * 部署全部流程
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/redeploy/all")
    public String redeployAll(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws Exception {
        workflowProcessDefinitionService.deployAllFromClasspath(exportDir);
        return "redirect:/workflow/process-list";
    }

    /**
     * 读取资源，通过部署ID
     * @param deploymentId	流程部署的ID
     * @param resourceName	资源名称(foo.xml|foo.png)
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource")
    public void loadByDeployment(@RequestParam("deploymentId") String deploymentId,
                                 @RequestParam("resourceName") String resourceName, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read")
    public void loadByProcessDefinition(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/process/delete")
    @ResponseBody
    public JSonMessage delete(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return new JSonMessage("ok", "");
    }

    /**
     * 输出跟踪流程信息
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process/trace")
    @ResponseBody
    public List<Map<String, Object>> traceProcess(@RequestParam("pid") String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = traceService.traceProcess(processInstanceId);
        return activityInfos;
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/process/trace/auto/{executionId}")
    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)
            throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        // 不使用spring请使用下面的两行代码
//    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
//    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

        // 使用spring注入引擎请使用下面的这行代码
        //processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public String deploy(@RequestParam(value = "file", required = false) MultipartFile file) {

        String fileName = file.getOriginalFilename();

        try {
            InputStream fileInputStream = file.getInputStream();

            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }

        } catch (Exception e) {
            logger.error("error on deploy process, because of file input stream", e);
        }

        return "redirect:/workflow/toWorkflow";
    }

    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "processdefinition/update/{state}/{processDefinitionId}")
    public String updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId,
                              RedirectAttributes redirectAttributes) {
        if (state.equals("active")) {
            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processDefinitionId + "]的流程定义。");
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processDefinitionId + "]的流程定义。");
        }
        return "redirect:/workflow/process-list";
    }

    /**
     * 导出图片文件到硬盘
     *
     * @return
     */
    @RequestMapping(value = "export/diagrams")
    @ResponseBody
    public List<String> exportDiagrams(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws IOException {
        List<String> files = new ArrayList<String>();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();

        for (ProcessDefinition processDefinition : list) {
            files.add(WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir));
        }

        return files;
    }

    @RequestMapping(value = "activity/jump")
    @ResponseBody
    public boolean jump(@RequestParam("executionId") String executionId,
                        @RequestParam("activityId") String activityId) {
        Command<Object> cmd = new JumpActivityCmd(executionId, activityId);
        managementService.executeCommand(cmd);
        return true;
    }

    @RequestMapping(value = "bpmn/model/{processDefinitionId}")
    @ResponseBody
    public BpmnModel queryBpmnModel(@PathVariable("processDefinitionId") String processDefinitionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        return bpmnModel;
    }

}