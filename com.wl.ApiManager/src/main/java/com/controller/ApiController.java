

package com.controller;

import cn.hutool.json.JSONObject;
import com.ApiManager;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bean.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.ApiService;
import com.service.ServerService;

//import com.wl.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@FeignClient(value="ApiManager")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
    @Resource
    private ApiService apiService;
    private Object object = null;

    @Resource
    private ServerService serverService;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("index")
    public ModelAndView index(ModelAndView modelAndView) {

        return modelAndView;
    }

    @RequestMapping("apiPage")
    public ModelAndView indexController(ModelAndView modelAndView) {
        modelAndView.setViewName("apiPage");
//        List<ApiData> apiData= apiService.findApi();
        List<OpenApiGroup> openApiData = apiService.findAllApi();
        List<JSONObject> jsonObjectList = null;

//        JSONArray jsonArray = new JSONArray();
//        JSONObject[] jsonObj = new JSONObject[openApiData.size()];


        modelAndView.addObject("apiList", openApiData);

        return modelAndView;
    }

    @ResponseBody
    @GetMapping("/test3")
    public CommonResult test3()
    {

        return new CommonResult(200, "测试3", serverPort);
    }
    @ResponseBody
    @GetMapping("/getServerMsg")
    public CommonResult getServerMsg()
    {
        List<ServerBean> results = serverService.getServer();

        return new CommonResult(200, "success",results);
    }
//    @ResponseBody
//    @PostMapping("/login")
//    public CommonResult login(@RequestBody User user)
//    {
//        Boolean flag = loginService.findUser(user.getUsername(), user.getPassword());
//        if (flag != null)
//        {
//            return new CommonResult(200, "success", user.getUsername());
//        }
//        else{
//            return new CommonResult(404,"not found",null);
//        }
//
//    }
    @ResponseBody
    @GetMapping("/findApiByName/{apiName}")
    public CommonResult<OpenApiGroup> findApiByName(@PathVariable("apiName") String apiName) {

        OpenApiGroup openApiGroup = apiService.findApiByName(apiName);

        System.out.println(apiName);

        if (openApiGroup != null) {
            return new CommonResult(200, "查询成功", openApiGroup);
        } else {
            return new CommonResult(404, "查询失败", "not found");
        }
    }

    @ResponseBody
    @GetMapping("/findApiById")
    public CommonResult<OpenApiData> findApiById(@RequestBody OpenApiData openApiData) {
        object = apiService.findApiById(openApiData.getId());
        if (object != null) {
            return new CommonResult(200, "查询成功", openApiData);
        } else {
            return new CommonResult(404, "查询失败", "not found");
        }
    }

    @ResponseBody
    @GetMapping("/findAll")
    public CommonResult<OpenApiGroup> find() {
        List<OpenApiGroup> openApiData = apiService.findAllApi();

        if (openApiData != null) {
            return new CommonResult(200, "查询成功", openApiData);
        } else {
            return new CommonResult(404, "查询失败", "not found");
        }
    }

    @ResponseBody
    @GetMapping("/findAllGroup")
    public CommonResult<ApiGroup> findAllGroup() {
        List<ApiGroup> apiGroup = apiService.findAllGroup();

        if (apiGroup == null) {
            return new CommonResult(404, "查询失败", "not found");
        } else {
            return new CommonResult(200, "查询成功", apiGroup);
        }
    }

    @ResponseBody
    @PostMapping("/findGroupByName")
    public CommonResult findGroupByName(@RequestBody ApiGroup apiGroup) {

        object = apiService.findGroupByName(apiGroup.getApiGroup());

        if (object == null) {
            return new CommonResult(404, "查询失败", "not found");
        } else {
            return new CommonResult(200, "查询成功", object);
        }
    }

    @ResponseBody
    @PostMapping("/addApiGroup")
    public CommonResult<OpenApiGroup> addApiGroup(@RequestBody ApiGroup apiGroup) {
        System.out.println(apiGroup);

        object = apiService.addApiGroup(apiGroup);

        if (object != null) {
            return new CommonResult(200, "添加成功", object);
        } else {
            return new CommonResult(404, "添加失败", object);
        }
    }

    /**
     * 删除api分组
     *
     * @param apiGroup
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteApiGroup")
    public CommonResult<OpenApiGroup> deleteApiGroup(@RequestBody ApiGroup apiGroup) {
        System.out.println(apiGroup);

        object = apiService.findGroupByName(apiGroup.getApiGroup());

        if (object != null) {
            Object object2 = apiService.deleteApiGroup(apiGroup.getApiGroup());
            Object result = apiService.deleteApiByGroup(apiGroup.getApiGroup());
            if (object2 != null || result != null)
                return new CommonResult(200, "删除成功", object);
            else
                return new CommonResult(500, "删除失败", object);
        } else {
            return new CommonResult(404, "未找到分组", object);
        }
    }

    @ResponseBody
    @PostMapping("/delete")
    public CommonResult<OpenApiGroup> delete(@RequestBody OpenApiData openApiData) {
        System.out.println(openApiData);

        object = apiService.deleteApi(openApiData.getId());

        if (object != null) {
            return new CommonResult(200, "删除成功", object);
        } else {
            return new CommonResult(404, "未找到分组", object);
        }
    }

    @ResponseBody
    @PostMapping("/insert")
    public CommonResult insertApi(@RequestBody OpenApiData openapiData) {
        System.out.println(openapiData);

        object = apiService.insertApi(openapiData);

        if (object != null) {
            System.out.println(openapiData);
            return new CommonResult(200, "插入成功", openapiData);
        } else {
            return new CommonResult(500, "插入失败", null);
        }

    }

    @ResponseBody
    @PostMapping("/saveApi")
    public CommonResult saveApi(@RequestBody OpenApiData openapiData) {
        System.out.println(openapiData);
        object = apiService.saveApi(openapiData);

        if (object != null) {
            System.out.println(openapiData);
            return new CommonResult(200, "更新成功", openapiData);
        } else {
            return new CommonResult(500, "更新失败", null);
        }

    }

    @ResponseBody
    @GetMapping("/testA")
    public Long testA() {
        Long startTime = System.currentTimeMillis();



        Long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        return endTime - startTime;


    }

    //    @ResponseBody
//    @PostMapping("/complete")
//    @SentinelResource(value = "complete",blockHandler = "deal_complete")
//    public CommonResult<OpenApiData> complete(@RequestBody String path)
//    {
//
//        JSONObject json = new JSONObject(path);
//
//        Object result = null;
//        String apiName = (String)json.get("path");
//        apiName = apiName.substring(1);//去掉第一个字符/
//
//        String[] api_temple = new String[100];
//        String body = null;
//        Boolean is_parm = false;
//        if(apiName.contains("/") || apiName.contains("?"))
//        {
//            if(apiName.contains("/"))
//            {
//                api_temple = apiName.split("/");
//                apiName = api_temple[0];
//                body = api_temple[1];
//                is_parm = true;
//            }
//            else{
//                api_temple = apiName.split("\\?");
//                apiName = api_temple[0];
//                body = api_temple[1];
//            }
//        }
//        CommonResult openApiData =  findApiByName("/"+apiName);
//
//        System.out.println("apiname:"+apiName+" body:"+body);
//        if(openApiData.getCode() == 200)
//        {
//
//            JSONObject jsonObject = new JSONObject(openApiData.getData());
//
//            String servers = (String)jsonObject.get("servers");
//            String paths = (String)jsonObject.get("paths");
//            String url_parm = "http://"+servers+paths+"/"+body;
//            String url = "http://"+servers+paths;
//            String method = (String)jsonObject.get("methods");
//            System.out.println("methods:"+method);
//            if(method.equals("POST"))
//            {
//                    result =ApiPost.interfaceUtil(url,body);
//            }else{
//                if(is_parm)
//                {
//                    result = ApiComplete.interfaceUtil(url_parm,"");
//                }
//                else{
//                    result = ApiComplete.interfaceUtil(url,"");
//                }
//
//            }
//
//
//            System.out.println(result);
//           return new CommonResult(200,"运行成功",result);
//
//        }else{
//            return new CommonResult(404,"未找到该api",openApiData);
//        }
//    }


    @ResponseBody
    @PostMapping("/complete")
    public CommonResult<OpenApiData> complete(@RequestBody OpenApiData openApiData)
    {
        ServerBean serverLBIndex = serverService.getServerLBIndex();
        Float min = serverLBIndex.getServerLB();
//        System.out.println("当前最小lb："+serverLBIndex.getServerLB());
        int flag = 1;

//        System.out.println(flag);

            if(min > 0.8)
            {
                flag = 1;
            }else if(min < 0.4){
                flag = 3;
            }else{
                flag = 2;
            }
        TokenBucket tokenBucket = new TokenBucket(100, flag);
            // 模拟请求处理

                    if (tokenBucket.tryConsume(50)) {
                        System.out.println(Thread.currentThread().getName() + " 执行请求");
                        return complete2(openApiData);
                    }
                    else {
                        System.out.println(Thread.currentThread().getName() + " 请求被限流");
                        return new CommonResult(400,"fail","请求被限流");}

    }

    @ResponseBody
//    @PostMapping("/complete2")
    @SentinelResource(value = "complete", blockHandler = "deal_complete")
    public CommonResult<OpenApiData> complete2(@RequestBody OpenApiData openApiData) {

        System.out.println(openApiData);

        String paths = openApiData.getPaths();
        String apiName = openApiData.getApiName();
        System.out.println("api名字" + apiName);
        String temple_path = paths.substring(1);

        Object result = null;

        CommonResult result_api = findApiByName(apiName);


        System.out.println("result:" + result_api);

        System.out.println();


        if (result_api.getCode() == 200) {
            JSONObject jsonObject = new JSONObject(result_api.getData());

            String servers = (String) jsonObject.get("servers");
            String url = "http://" + servers + paths;
            String method = (String) jsonObject.get("methods");
            String[] templeant = {"no_parm"};
            //判断是否有query参数

            if (!Arrays.equals(openApiData.getParm_query(), templeant)) {
                System.out.println("openApiData.getParm_query():" + openApiData.getParm_query());
                String[] parm_value = openApiData.getParm_query_value();
                String[] parm_key = openApiData.getParm_query();
                String[] parm_temple = new String[parm_key.length];
                for (int i = 0; i < parm_key.length; i++) {
                    parm_temple[i] = parm_key[i] + "=" + parm_value[i];
                }

                for (String s : parm_temple) {
                    System.out.println(s);
                }
                String parm_result = parm_temple[0];

                for (int i = 1; i < parm_key.length; i++) {
                    parm_result += "&" + parm_temple[i];
                }
//            System.out.println(parm_result);
                result = ApiPost.interfaceUtil(url, parm_result);
            } else if (!openApiData.getInput22().equals("") || !openApiData.getInput11().equals("") || !openApiData.getInput33().equals("")) {
// && !openApiData.getInput22().equals("") || !openApiData.getInput11().equals("")  || !openApiData.getInput33().equals("")
                int index = paths.indexOf("{");
//                paths = paths.substring(0, 8);
                paths = paths.substring(0,index);
                Object parm1 = openApiData.getInput2();
                Object parm2 = openApiData.getInput1();
                Object parm3 = openApiData.getInput3();
                url = "http://" + servers + paths + parm1 + "/" + parm2 + "/" + parm3;
                System.out.println("paths:"+paths+"parm1:"+parm1+"parm2:"+parm2+"parm3:"+parm3);
                System.out.println(url);
                System.out.println("input1:" + openApiData.getInput1());
                System.out.println("input11:" + openApiData.getInput11());
                System.out.println("input2:" + openApiData.getInput2());
                System.out.println("input22:" + openApiData.getInput22());
                System.out.println("input3:" + openApiData.getInput3());
                System.out.println("input33:" + openApiData.getInput33());
                result = ApiComplete.interfaceUtil(url, "");


            } else if (!openApiData.getRequestBody().equals("{}") && !openApiData.getRequestBody().equals(" ")) {
                System.out.println("body:" + openApiData.getRequestBody().equals(" "));
                System.out.println(openApiData.getRequestBody().equals("{}"));
                System.out.println(openApiData.getRequestBody());
                result = ApiPost.runBody(url, openApiData.getRequestBody());

            } else {

                result = ApiComplete.interfaceUtil(url, "");
                System.out.println(result);
            }//“---”后面加名字为了统计该api调用次数
            return new CommonResult(200, "运行成功", result + "---" + apiName);

        } else {

            return new CommonResult(404, "未找到该api", openApiData.getPaths());
        }
    }

    public CommonResult<OpenApiData> deal_complete(String path, BlockException blockException) {
        return new CommonResult(444, "资源忙，请稍后再试！");
    }

    @ResponseBody
    @PostMapping("import_swagger")
    public CommonResult<OpenApiData> import_swagger(@RequestBody String path) {
        JSONObject url = new JSONObject(path);
        OpenApiData openApiData[] = new OpenApiData[1000];

        System.out.println(openApiData[1]);

        Object result = ApiComplete.interfaceUtil((String) url.get("path"), "");
        System.out.println(result);


        String result_json = (String) result;
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(result_json).getAsJsonObject();

        JsonElement hosts = jo.get("host");
        Set<String> paths = jo.get("paths").getAsJsonObject().keySet();
        String[] methods = new String[1000];
        Object[] paths_array = paths.toArray();
        String[] temple_methods = new String[1000];
        String[] tags = new String[1000];
        String[] api_name = new String[1000];
        JsonElement info = null;

        for (int i = 0; i < paths_array.length; i++) {

            openApiData[i] = new OpenApiData();
        }
        for (int i = 0; i < paths_array.length; i++) {

            temple_methods[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().keySet());
            methods[i] = temple_methods[i].substring(1, temple_methods[i].length() - 1);

            info = jo.get("info");
            openApiData[i].setMethods(methods[i]);

            if (jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post") != null) {
                api_name[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post").getAsJsonObject().get("summary"));
                tags[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("post").getAsJsonObject().get("tags"));
                openApiData[i].setOpenApi("3.0.0");
                openApiData[i].setApiName(api_name[i].substring(1, api_name[i].length() - 1));
                openApiData[i].setPaths((String) paths_array[i]);
                openApiData[i].setServers(hosts.getAsString());
                openApiData[i].setInfo(info.getAsJsonObject().toString().substring(1, info.getAsJsonObject().toString().length() - 1));
                openApiData[i].setGroup_api(tags[i].substring(2, tags[i].length() - 2));
            } else if (jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get") != null) {
                api_name[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get").getAsJsonObject().get("summary"));
                tags[i] = String.valueOf(jo.get("paths").getAsJsonObject().get((String) paths_array[i]).getAsJsonObject().get("get").getAsJsonObject().get("tags"));
                openApiData[i].setOpenApi("3.0.0");
                openApiData[i].setApiName(api_name[i].substring(1, api_name[i].length() - 1));
                openApiData[i].setPaths((String) paths_array[i]);
                openApiData[i].setServers(hosts.getAsString());
                openApiData[i].setInfo(info.getAsJsonObject().toString().substring(1, info.getAsJsonObject().toString().length() - 1));
                openApiData[i].setGroup_api(tags[i].substring(2, tags[i].length() - 2));
            }
        }

        Object[] objects = new Object[1000];
        for (int i = 0; i < paths_array.length; i++) {
            System.out.println(openApiData[i]);

            objects[i] = apiService.insertApi(openApiData[i]);
            if (objects[i] == null)
                return new CommonResult(500, "插入失败", null);
        }
        JsonElement Tags = jo.get("tags");

        return new CommonResult(200, "插入成功", openApiData);

    }

    @ResponseBody
    @PostMapping("/sendMsg")
    public CommonResult sendMsg(@RequestBody String value) {
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(value).getAsJsonObject();
        System.out.println(value);
        System.out.println(jo.get("value").getAsJsonObject().keySet());
        Set<String> key = jo.get("value").getAsJsonObject().keySet();//获取所有key
        JsonObject result = (JsonObject) jo.get("value");
        System.out.println("reuslt:" + result);
        //使用json.get("value")获取后面的value。不得不说还是google的json好用多了！！！动态获取所有key太好用了
        Object[] key_array = key.toArray();
        for (int i = 0; i < key.size(); i++) {
            System.out.println(key_array[i] + ":" + result.get((String) key_array[i]));
        }
        //  System.out.println(jo.get("value").getAsJsonObject().entrySet());

        return new CommonResult(200, "success", "nice");

    }
    /*
        查询所有用户，用于在API发布的时候，指定用户使用
    * */
    @ResponseBody
    @GetMapping("/findAllUser")
    public CommonResult findAllUser()
    {
        List<User> apiUser = apiService.findAllUser();

        return new CommonResult(200, "success", apiUser);
    }
}
