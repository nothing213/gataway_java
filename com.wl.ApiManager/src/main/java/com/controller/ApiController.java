package com.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bean.ApiData;
import com.bean.CommonResult;
import com.bean.OpenApiData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Resource
    private ApiService apiService;
    private Object object = null;
    @RequestMapping("index")
    public ModelAndView index(ModelAndView modelAndView) {

        return modelAndView;
    }
    @RequestMapping("apiPage")
    public ModelAndView indexController(ModelAndView modelAndView){
        modelAndView.setViewName("apiPage");
//        List<ApiData> apiData= apiService.findApi();
        List<OpenApiData> openApiData = apiService.findAllApi();
        List<JSONObject> jsonObjectList = null;

//        JSONArray jsonArray = new JSONArray();
//        JSONObject[] jsonObj = new JSONObject[openApiData.size()];




     modelAndView.addObject("apiList",openApiData);

       return modelAndView;
    }
    @ResponseBody
    @GetMapping("/findApiByName/{apiName}")
    public CommonResult<OpenApiData> findApiByName(@PathVariable("apiName") String apiName)
    {   OpenApiData openApiData = apiService.findApiByName(apiName);
        if(openApiData != null)
        {
            return new CommonResult(200,"查询成功",openApiData);
        }
        else{
            return new CommonResult(404,"查询失败","not found");
        }
    }

    @ResponseBody
    @GetMapping("/findAllApi")
    public CommonResult<OpenApiData> findAllApi( )
    {   List<OpenApiData> openApiData = apiService.findAllApi();

        if(openApiData != null)
        {
            return new CommonResult(200,"查询成功",openApiData);
        }
        else{
            return new CommonResult(404,"查询失败","not found");
        }
    }


    @ResponseBody
    @PostMapping("/insertApi")
    public CommonResult insertApi(@RequestBody OpenApiData openapiData)
    {
        System.out.println(openapiData);
        object = apiService.insertApi(openapiData);

        if(object != null)
        {
            System.out.println(openapiData);
            return new CommonResult(200,"插入成功",openapiData);
        }
        else{
            return new CommonResult(500,"插入失败",null);
        }

    }

    @ResponseBody
    @GetMapping("/testA")
    public String testA()
    {
        return "------testA";
    }

    @ResponseBody
    @PostMapping("/complete")
    @SentinelResource(value = "complete",blockHandler = "deal_complete")
    public CommonResult<OpenApiData> complete(@RequestBody String path)
    {
        JSONObject json = new JSONObject(path);

        Object result = null;
        String apiName = (String)json.get("path");
        CommonResult openApiData =  findApiByName(apiName);

        if(openApiData.getCode() == 200)
        {
            JSONObject jsonObject = new JSONObject(openApiData.getData());

            String servers = (String)jsonObject.get("servers");
            String paths = (String)jsonObject.get("paths");
            String url = "http://"+servers+paths;


            result = ApiComplete.interfaceUtil(url,"");
            System.out.println(result);
           return new CommonResult(200,"运行成功",result);

        }else{
            return new CommonResult(404,"未找到该api",openApiData);
        }
    }

    public CommonResult<OpenApiData> deal_complete(String path, BlockException blockException)
    {
        return new CommonResult(444,"资源忙，请稍后再试！");
    }

    @ResponseBody
    @PostMapping("import_swagger")
    public CommonResult<OpenApiData> import_swagger(@RequestBody String path)
    {
       JSONObject url = new JSONObject(path);
       OpenApiData openApiData[] = new OpenApiData[1000];



        System.out.println(openApiData[1]);

        Object result =  ApiComplete.interfaceUtil((String)url.get("path"),"");
        System.out.println(result);


        String result_json = (String) result;
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(result_json).getAsJsonObject();

        JsonElement hosts = jo.get("host");
        Set<String> paths = jo.get("paths").getAsJsonObject().keySet();
        String[] methods = new String[1000];
        Object[] paths_array =  paths.toArray();
        String[] temple_methods = new String[1000];
        String[] tags = new String[1000];
        String[] api_name = new String[1000];
        JsonElement info = null;

        for(int i = 0;i< paths_array.length;i++)
        {

            openApiData[i] = new OpenApiData();
        }
        for(int i = 0;i < paths_array.length;i++)
        {

            temple_methods[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().keySet());
            methods[i] = temple_methods[i].substring(1,temple_methods[i].length()-1);

            info = jo.get("info");
            openApiData[i].setMethods(methods[i]);

            if(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post")!=null)
            {
                api_name[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post").getAsJsonObject().get("summary"));
                tags[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post").getAsJsonObject().get("tags"));
                openApiData[i].setOpenApi("3.0.0");
                openApiData[i].setApiName(api_name[i].substring(1,api_name[i].length()-1));
                openApiData[i].setPaths((String) paths_array[i]);
                openApiData[i].setServers(hosts.getAsString());
                openApiData[i].setInfo(info.getAsJsonObject().toString().substring(1,info.getAsJsonObject().toString().length()-1));
                openApiData[i].setGroup_api(tags[i].substring(2,tags[i].length()-2));
            }
            else if(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get") != null)
            {
                api_name[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get").getAsJsonObject().get("summary"));
                tags[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get").getAsJsonObject().get("tags"));
                openApiData[i].setOpenApi("3.0.0");
                openApiData[i].setApiName(api_name[i].substring(1,api_name[i].length()-1));
                openApiData[i].setPaths((String) paths_array[i]);
                openApiData[i].setServers(hosts.getAsString());
                openApiData[i].setInfo(info.getAsJsonObject().toString().substring(1,info.getAsJsonObject().toString().length()-1));
                openApiData[i].setGroup_api(tags[i].substring(2,tags[i].length()-2));
            }
        }

        Object[] objects = new Object[1000];
        for(int i = 0; i< paths_array.length;i++)
        {
            System.out.println(openApiData[i]);

            objects[i] = apiService.insertApi(openApiData[i]);
            if(objects[i] == null)
                return new CommonResult(500,"插入失败",null);
        }

        JsonElement Tags = jo.get("tags");


            return new CommonResult(200,"插入成功",openApiData);



    }

}
