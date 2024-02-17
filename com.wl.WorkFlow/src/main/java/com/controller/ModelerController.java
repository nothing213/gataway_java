package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

//import com.activiti6.bean.ApiData;
//import com.activiti6.service.ApiService;
//import com.activiti6.service.Impl.ApiServiceImpl;
//import com.activiti6.test.testApi.Apidemo;
import cn.hutool.json.JSONObject;
import com.bean.ApiData;
import com.bean.CommonResult;
import com.pojo.Result;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


//import static com.sun.tools.attach.VirtualMachine.list;


@Controller
public class ModelerController {

    private static final Logger logger = LoggerFactory.getLogger(ModelerController.class);
    private static String ApiManagerUrl = "http://localhost:8001";
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private FormService formService ;

    @Resource
    private TaskService taskService ;
    @Autowired
    private ObjectMapper objectMapper;
    @Resource
    private HistoryService historyService;
    @Resource
    private RuntimeService runtimeService;


    ModelAndView modelAndView = new ModelAndView();

    @Resource
    private RestTemplate restTemplate = new RestTemplate();

//    private Apidemo apidemo;

    private Object result = "11";
    private Boolean flag;

    @RequestMapping("index")
    public ModelAndView index() {
        modelAndView.setViewName("index");
        modelAndView.addObject("modelList", repositoryService.createModelQuery().list());

        return modelAndView;
    }

    @RequestMapping("demo")
    public ModelAndView demo(ModelAndView modelAndView) {
        modelAndView.setViewName("demo");
        modelAndView.addObject("username", "张三");

        return modelAndView;
    }

    @RequestMapping("apiPage")
    public ModelAndView indexController(ModelAndView modelAndView) {
        modelAndView.setViewName("apiPage");

//        List<ApiData> apiData= apiService.findApi();
        CommonResult<ApiData> apiData = restTemplate.getForObject(ApiManagerUrl + "/findApi", CommonResult.class);
        System.out.println(apiData);
//        for(ApiData a:apiData)
//        {
//            System.out.println(a);
//        }
        modelAndView.addObject("apiList", apiData);
        return modelAndView;
    }

    /**
     * 跳转编辑器页面
     *
     * @return
     */
    @GetMapping("editor")
    public String editor() {
        return "modeler";
    }


    /**
     * 创建模型
     *
     * @param response
     * @param name     模型名称
     * @param key      模型key
     */
    @RequestMapping("/create")
    public void create(HttpServletResponse response, String name, String key) throws IOException {
        logger.info("创建模型入参name：{},key:{}", name, key);
        Model model = repositoryService.newModel();
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "");
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        createObjectNode(model.getId());
        response.sendRedirect("/editor?modelId=" + model.getId());

        logger.info("创建模型结束，返回模型ID：{}", model.getId());
    }

    /**
     * 创建模型时完善ModelEditorSource
     *
     * @param modelId
     */
    @SuppressWarnings("deprecation")
    private void createObjectNode(String modelId) {
        logger.info("创建模型完善ModelEditorSource入参模型ID：{}", modelId);
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        try {
            repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes("utf-8"));
        } catch (Exception e) {
            logger.info("创建模型时完善ModelEditorSource服务异常：{}", e);
        }
        logger.info("创建模型完善ModelEditorSource结束");
    }

    /**
     * 发布流程
     *
     * @param modelId 模型ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/publish")
    public Object publish(String modelId, HttpServletResponse response) {
        logger.info("流程部署入参modelId：{}", modelId);
        Map<String, String> map = new HashMap<String, String>();
        try {
            Model modelData = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                logger.info("部署ID:{}的模型数据为空，请先设计流程并成功保存，再进行发布", modelId);
                map.put("code", "FAILURE");
                return map;
            }
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addBpmnModel(modelData.getKey() + ".bpmn20.xml", model)
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            map.put("code", "SUCCESS");
            response.sendRedirect("/index");
        } catch (Exception e) {
            logger.info("部署modelId:{}模型服务异常：{}", modelId, e);
            map.put("code", "FAILURE");
        }
        logger.info("流程部署出参map：{}", map);
        return map;
    }

    /**
     * 撤销流程定义
     *
     * @param modelId 模型ID
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/revokePublish")
    public Object revokePublish(String modelId) {
        logger.info("撤销发布流程入参modelId：{}", modelId);
        Map<String, String> map = new HashMap<String, String>();
        Model modelData = repositoryService.getModel(modelId);
        if (null != modelData.getDeploymentId()) {
            try {
                /**
                 * 参数不加true:为普通删除，如果当前规则下有正在执行的流程，则抛异常
                 * 参数加true:为级联删除,会删除和当前规则相关的所有信息，包括历史
                 */
                repositoryService.deleteDeployment(modelData.getDeploymentId(), true);
                map.put("code", "SUCCESS");
            } catch (Exception e) {
                logger.error("撤销已部署流程服务异常：{}", e);
                map.put("code", "FAILURE");
            }
        } else {
            map.put("code", "未部署，无法撤销");
        }
        logger.info("撤销发布流程出参map：{}", map);
        return map;
    }

    /**
     * 删除流程实例
     *
     * @param modelId 模型ID
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public void deleteProcessInstance(String modelId) {
        System.out.println(modelId);

        if (modelId != null) {
            System.out.println("删除的id为" + modelId);
            repositoryService.deleteModel(modelId);
        } else {
            System.out.println("id为空，无法删除");
        }

    }

    @ResponseBody
    @RequestMapping("/history")
    public void runHistory() {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        System.out.println(historicProcessInstanceQuery.processDefinitionKey("开飞机").singleResult());
    }

    @ResponseBody
    @RequestMapping("/run")
    public void runProcessInstance(String modelName, HttpServletResponse response) throws IOException {

        if (modelName != null) {
//            ApiController apiController =new ApiController();

            System.out.println("流程名字：" + modelName);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(modelName);
            System.out.println("流程id:" + processInstance.getProcessDefinitionId());
//			System.out.println("当前活动id:"+processInstance.getActivityId());
//			System.out.println("流程实例id:"+processInstance.getDeploymentId());
            System.out.println("流程key：" + processInstance.getProcessDefinitionKey());
            System.out.println("启动成功");

        } else {
            System.out.println("启动失败");
        }
        response.sendRedirect("/index");
    }
    //封装成API
    @ResponseBody
    @GetMapping("/run/{modelName}")
    public CommonResult runProcessInstanceAPI(@PathVariable String modelName)  {

        if (modelName != null) {

            System.out.println("流程名字：" + modelName);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(modelName);
            System.out.println("流程id:" + processInstance.getProcessDefinitionId());

            System.out.println("流程key：" + processInstance.getProcessDefinitionKey());
            System.out.println("启动成功");
            CommonResult commonResult = completeTaskAPI(modelName);
           return commonResult;
        } else {
            System.out.println("启动失败");
            return new CommonResult(404,"error","no");
        }

    }
//   该功能换到apimanager
//    @ResponseBody
//    @PostMapping("do_api")
//    public void do_some(ApiData apiData,HttpServletResponse response)
//    {
//        restTemplate.postForObject(ApiManagerUrl+"/insertApi",apiData,CommonResult.class);
//
////        Boolean flag = apiService.insertApi(apiData);
//        if(flag == true)
//        {
//            System.out.println(apiData.getBody());
//            System.out.println("插入成功");
//        }
//        else{
//            System.out.println("插入失败");
//        }
//        try{
//            response.sendRedirect("/index");
//        }
//        catch (IOException e)
//        {
//            System.out.println(e);
//        }
//
//    }

//运行API
    public boolean CompeleteApi(String apiName, String data) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("data", "111");

        CommonResult apiData = restTemplate.getForObject(ApiManagerUrl + "/findApiByName/" + apiName, CommonResult.class);
        System.out.println(apiData);
        if (apiData.getCode() == 200) {
            JSONObject jsonObject = new JSONObject(apiData.getData());

            String servers = (String) jsonObject.get("servers");
            String paths = (String) jsonObject.get("paths");
            String url = "http://" + servers + paths;
            String method = (String) jsonObject.get("methods");

            if (!data.equals("")) {
                // data != null
                if (method.equals("POST")) {
                    result = ApiPost.interfaceUtil(url, data);
                } else {

                    System.out.println("参数是：" + data);
                    int url_flag = url.indexOf("{");
                    System.out.println(url.substring(0, url_flag));

                    result = ApiComplete.interfaceUtil(url.substring(0, url_flag) + data, "");
                }
            } else {
                result = ApiComplete.interfaceUtil(url, "");
            }

            modelAndView.addObject("resultSet",result);
//            System.out.println("apiName:" + apiName + ":" + result);
            return true;

        } else {
            System.out.println("没有该api");
            return false;
        }
    }

    @ResponseBody
    @GetMapping("/test")
    public void test() {

        Model modelData = repositoryService.getModel("1");
        List<Task> tasklist = taskService.createTaskQuery().list();
        for (Task t : tasklist) {
            System.out.println("name:" + t.getName() + "id:" + t.getId() + "definitionid:" + t.getProcessDefinitionId());
        }

        List<Model> modelerlist = repositoryService.createModelQuery().list();
        for (Model m : modelerlist) {
            System.out.println("id:" + m.getId() + "流程名字:" + m.getName() + "部署id：" + m.getDeploymentId() + "key:" + m.getKey());

        }
        System.out.println(modelData.getName() + " " + modelData.getKey() + " " + modelData.getDeploymentId());
    }

    @ResponseBody
    @RequestMapping("/task/{modelname}")
    public void Task(@PathVariable String modelname) {
        TaskFormData userTaskFormData = formService.getTaskFormData(modelname);
        List<FormProperty> formProperties = userTaskFormData.getFormProperties();


        for (FormProperty formProperty : formProperties) {
            System.out.println("id" + formProperty.getId());
            System.out.println("name" + formProperty.getName());
            System.out.println("type:" + formProperty.getType().getInformation("values"));
            System.out.println("value" + formProperty.getValue());
        }

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(modelname)
                .list();
        List<ProcessDefinition> definitions = processDefinitionQuery.processDefinitionKey(modelname)
                .list();
    }

    @ResponseBody
    @RequestMapping("/complete")
    public boolean completeTask(String modelName, HttpServletResponse response) throws IOException {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(modelName) //流程Key
                .list();
        Result r = new Result();
        r.setNum(1);
        Map<String, Object> var = new HashMap<>();
        var.put("evetion",r);

        if (taskList.size() != 0) {
            for (Task task : taskList) {
                System.out.println(task.getId()+"id is");
                TaskFormData userTaskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = userTaskFormData.getFormProperties();

                int formSize = formProperties.size();
                if (formSize == 0) {
                    flag = CompeleteApi(task.getName(), "");
                    taskService.complete(task.getId(),var);
                    System.out.println("完成的任务id：" + task.getId());
                } else {
                    for (FormProperty formProperty : formProperties) {
                        if (formProperty.getId() != null) {
                            flag = CompeleteApi(task.getName(), formProperty.getId());
                            taskService.complete(task.getId());
                        } else {
                            System.out.println("bu参数：" + formProperty.getId());
                            flag = CompeleteApi(task.getName(), "");
                            taskService.complete(task.getId());
                        }
                    }
                }
            }
            if (flag == true)
            {
                completeTask(modelName, response);
                return true;
            } else
            {
                System.out.println("出现异常!停止运行！");
                response.sendRedirect("/index");
                return false;
//                completeTask(modelName, response);
            }
        }
        else
        {
            System.out.println("任务结束！");
            response.sendRedirect("/index");
            return  false;
        }

    }
    Stack<CommonResult> stack = new Stack<CommonResult>();
    CommonResult commonResult = new CommonResult();
    CommonResult commonResult1 = new CommonResult();
    //打包成API对外发布
    @ResponseBody
    @GetMapping("/complete/{modelName}")
    public CommonResult completeTaskAPI(@PathVariable String  modelName) {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(modelName) //流程Key
                .list();
        Result r = new Result();
        r.setNum(1);
        Map<String, Object> var = new HashMap<>();
        var.put("evetion",r);
        if (taskList.size() != 0) {
            for (Task task : taskList) {
                System.out.println(task.getId()+"id is");
                TaskFormData userTaskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = userTaskFormData.getFormProperties();

                int formSize = formProperties.size();
                if (formSize == 0) {//说明没有参数

                    commonResult =   CompeleteApiAPI(task.getName(), "");
                    stack.push(commonResult);
                    taskService.complete(task.getId(),var);
                    System.out.println("完成的任务id：" + task.getId()+"任务名字是"+task.getName());
                } else {
                    for (FormProperty formProperty : formProperties) {
                        if (formProperty.getId() != null) {
                            commonResult = CompeleteApiAPI(task.getName(), formProperty.getId());
                            stack.push(commonResult);
                            taskService.complete(task.getId());
                            System.out.println("完成的任务id：" + task.getId()+"任务名字是"+task.getName());
                        } else {
                            System.out.println("bu参数：" + formProperty.getId());
                            commonResult = CompeleteApiAPI(task.getName(), "");
                            stack.push(commonResult);
                            taskService.complete(task.getId());
                        }
                    }
                }


            }
            if (flag == true)
            {


                completeTaskAPI(modelName);

                return commonResult1;
            } else
            {
                System.out.println("出现异常!停止运行！");

                return  new CommonResult(404,"error","");
            }
        }
        else
        {
            System.out.println("任务结束！");

            System.out.println("任务："+commonResult);
            return commonResult;
        }
//        System.out.println("返回任务是"+commonResult);
//        return  commonResult;
    }
    //主要是打包成接口发布出去
    public CommonResult CompeleteApiAPI(String apiName, String data) {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("data", "111");

        CommonResult apiData = restTemplate.getForObject(ApiManagerUrl + "/findApiByName/" + apiName, CommonResult.class);
        System.out.println(apiData);
        if (apiData.getCode() == 200) {
            JSONObject jsonObject = new JSONObject(apiData.getData());

            String servers = (String) jsonObject.get("servers");
            String paths = (String) jsonObject.get("paths");
            String url = "http://" + servers + paths;
            String method = (String) jsonObject.get("methods");

            if (!data.equals("")) {
                // data != null
                if (method.equals("POST")) {
                    result = ApiPost.interfaceUtil(url, data);
                } else {

                    System.out.println("参数是：" + data);
                    int url_flag = url.indexOf("{");
                    System.out.println(url.substring(0, url_flag));

                    result = ApiComplete.interfaceUtil(url.substring(0, url_flag) + data, "");
                }
            } else {
                result = ApiComplete.interfaceUtil(url, "");
            }

            modelAndView.addObject("resultSet",result);
//            System.out.println("apiName:" + apiName + ":" + result);
            flag = true;
            System.out.println("return:"+result);
            commonResult1 = new CommonResult(200,"success",result);
            return  new CommonResult(200,"success",result);

        } else {
            System.out.println("没有该api");
            flag = false;
            return  new CommonResult(404,"error","没有该API");
        }
    }
    @ResponseBody
    @RequestMapping("/del")
    public void deleteDeployment() {
        repositoryService.deleteDeployment("167501", true);
        System.out.println("删除成功");
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
//		repositoryService.deleteDeployment("22636");
    }


}
