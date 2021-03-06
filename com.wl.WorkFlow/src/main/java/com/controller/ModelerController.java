package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

//import com.activiti6.bean.ApiData;
//import com.activiti6.service.ApiService;
//import com.activiti6.service.Impl.ApiServiceImpl;
//import com.activiti6.test.testApi.Apidemo;
import cn.hutool.json.JSONObject;
import com.bean.ApiData;
import com.bean.CommonResult;
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
public class ModelerController{

    private static final Logger logger = LoggerFactory.getLogger(ModelerController.class);
    private static String ApiManagerUrl = "http://localhost:8001";
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;


    @Resource
    private RestTemplate restTemplate;

//    private Apidemo apidemo;

    private Object result;

    @RequestMapping("index")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("modelList", repositoryService.createModelQuery().list());

        return modelAndView;
    }
    @RequestMapping("apiPage")
    public ModelAndView indexController(ModelAndView modelAndView){
        modelAndView.setViewName("apiPage");

//        List<ApiData> apiData= apiService.findApi();
        CommonResult<ApiData> apiData = restTemplate.getForObject(ApiManagerUrl+"/findApi", CommonResult.class);
        System.out.println(apiData);
//        for(ApiData a:apiData)
//        {
//            System.out.println(a);
//        }
        modelAndView.addObject("apiList",apiData);
        return modelAndView;
    }
    /**
     * ?????????????????????
     * @return
     */
    @GetMapping("editor")
    public String editor(){
        return "modeler";
    }


    /**
     * ????????????
     * @param response
     * @param name ????????????
     * @param key ??????key
     */
    @RequestMapping("/create")
    public void create(HttpServletResponse response,String name,String key) throws IOException {
        logger.info("??????????????????name???{},key:{}",name,key);
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
        response.sendRedirect("/editor?modelId="+ model.getId());

        logger.info("?????????????????????????????????ID???{}",model.getId());
    }

    /**
     * ?????????????????????ModelEditorSource
     * @param modelId
     */
    @SuppressWarnings("deprecation")
    private void createObjectNode(String modelId){
        logger.info("??????????????????ModelEditorSource????????????ID???{}",modelId);
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        try {
            repositoryService.addModelEditorSource(modelId,editorNode.toString().getBytes("utf-8"));
        } catch (Exception e) {
            logger.info("?????????????????????ModelEditorSource???????????????{}",e);
        }
        logger.info("??????????????????ModelEditorSource??????");
    }

    /**
     * ????????????
     * @param modelId ??????ID
     * @return
     */
    @ResponseBody
    @RequestMapping("/publish")
    public Object publish(String modelId,HttpServletResponse response){
        logger.info("??????????????????modelId???{}",modelId);
        Map<String, String> map = new HashMap<String, String>();
        try {
            Model modelData = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                logger.info("??????ID:{}???????????????????????????????????????????????????????????????????????????",modelId);
                map.put("code", "FAILURE");
                return map;
            }
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addBpmnModel(modelData.getKey()+".bpmn20.xml", model)
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            map.put("code", "SUCCESS");
            response.sendRedirect("/index");
        } catch (Exception e) {
            logger.info("??????modelId:{}?????????????????????{}",modelId,e);
            map.put("code", "FAILURE");
        }
        logger.info("??????????????????map???{}",map);
        return map;
    }

    /**
     * ??????????????????
     * @param modelId ??????ID
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/revokePublish")
    public Object revokePublish(String modelId){
        logger.info("????????????????????????modelId???{}",modelId);
        Map<String, String> map = new HashMap<String, String>();
        Model modelData = repositoryService.getModel(modelId);
        if(null != modelData.getDeploymentId()){
            try {
                /**
                 * ????????????true:??????????????????????????????????????????????????????????????????????????????
                 * ?????????true:???????????????,????????????????????????????????????????????????????????????
                 */
                repositoryService.deleteDeployment(modelData.getDeploymentId(),true);
                map.put("code", "SUCCESS");
            } catch (Exception e) {
                logger.error("????????????????????????????????????{}",e);
                map.put("code", "FAILURE");
            }
        }
        else{
            map.put("code", "????????????????????????");
        }
        logger.info("????????????????????????map???{}",map);
        return map;
    }

    /**
     * ??????????????????
     * @param modelId ??????ID
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public void deleteProcessInstance(String modelId){
        System.out.println(modelId);

        if(modelId != null)
        {
            System.out.println("?????????id???"+modelId);
            repositoryService.deleteModel(modelId);
        }else{
            System.out.println("id?????????????????????");
        }

    }
    @ResponseBody
    @RequestMapping("/history")
    public void runHistory()
    {
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        System.out.println(historicProcessInstanceQuery.processDefinitionKey("?????????").singleResult());
    }
    @ResponseBody
    @RequestMapping("/run")
    public void runProcessInstance(String modelName,HttpServletResponse response) throws IOException {

        if(modelName != null)
        {
//            ApiController apiController =new ApiController();
            Map<String,Object> var =new HashMap<>();
//            var.put("apiController",apiController);
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(modelName,var);
            System.out.println("??????id:"+processInstance.getProcessDefinitionId());
//			System.out.println("????????????id:"+processInstance.getActivityId());
//			System.out.println("????????????id:"+processInstance.getDeploymentId());
            System.out.println("??????key???"+processInstance.getProcessDefinitionKey());
            System.out.println("????????????");


        }
        else{
            System.out.println("????????????");
        }
        response.sendRedirect("/index");


    }
//   ???????????????apimanager
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
//            System.out.println("????????????");
//        }
//        else{
//            System.out.println("????????????");
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


    public void CompeleteApi(String apiName,String data)
    {
        CommonResult apiData =  restTemplate.getForObject(ApiManagerUrl+"/findApiByName/"+apiName,CommonResult.class);
//        System.out.println(apiData);
        if(apiData.getCode() == 200)
        {
            JSONObject jsonObject = new JSONObject(apiData.getData());

            String servers = (String)jsonObject.get("servers");
            String paths = (String)jsonObject.get("paths");
            String url = "http://"+servers+paths;

            System.out.println(apiName+"????????????!!!");
            result = ApiComplete.interfaceUtil(url,"");
        }else{
            System.out.println("?????????api");
        }
    }



    @ResponseBody
    @GetMapping("/test")
    public void test()
    {
        Model modelData = repositoryService.getModel("1");
        repositoryService.deleteModel("280074");
        System.out.println(modelData.getName()+" "+modelData.getKey()+""+modelData.getDeploymentId());
    }


    @ResponseBody
    @RequestMapping("/task/{modelname}")
    public void Task(@PathVariable String  modelname)
    {
        TaskFormData userTaskFormData = formService.getTaskFormData("280007");

        List<FormProperty> formProperties = userTaskFormData.getFormProperties();

        Model modelData = repositoryService.getModel("140009");


        for(FormProperty formProperty :formProperties)
        {
            System.out.println("id"+formProperty.getId());
            System.out.println("name"+formProperty.getName());
            System.out.println("value"+formProperty.getValue());
        }

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(modelname)
                .list();

        List<ProcessDefinition> definitions = processDefinitionQuery.processDefinitionKey(modelname)
                .list();

    }
    @ResponseBody
    @RequestMapping("/complete")
    public void completeTask(String modelName,HttpServletResponse response) throws IOException {

        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(modelName) //??????Key
                .list();
//		System.out.println(taskList);
        if(taskList.size() != 0)
        {
            for(Task task:taskList)
            {
                taskService.complete(task.getId());

                CompeleteApi(task.getName(),"");
            }
            completeTask(modelName,response);
        }else{
            System.out.println("???????????????");
            response.sendRedirect("/index");
        }


    }
    @ResponseBody
    @RequestMapping("/del")
    public void deleteDeployment() {
        repositoryService.deleteDeployment("167501",true);
        System.out.println("????????????");
        //??????true ??????????????????????????????????????????????????????????????????????????????????????????false????????????????????????????????????
//		repositoryService.deleteDeployment("22636");
    }


}
