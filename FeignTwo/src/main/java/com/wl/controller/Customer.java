package com.wl.controller;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.bean.CommonResult;

import com.bean.OpenApiData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.service.OpenFeigenTest;
import com.wl.bean.ServerBean;
import com.wl.service.ServerService;
import lombok.var;
import org.apache.catalina.Server;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class Customer {
    @Resource
    OpenFeigenTest openFeigenTest;

    @Resource
    ServerService serverService;

    @GetMapping("/demo")
    public CommonResult demo()
    {

        return openFeigenTest.demo();
    }

    @GetMapping("/getServerMsg")
    public Object getServer(){

        List<ServerBean> serverBean =  serverService.getServerMsg();

        Float[] floats = new Float[serverBean.size()];

        for(int i = 0;i<serverBean.size();i++)
        {
            floats[i] = serverBean.get(i).getServerLB();
        }
        int flag = 0;
        Float min = floats[0];
        for(int i = 0;i< floats.length;i++)
        {
            if(floats[i] < min)
            {
                min = floats[i];
                flag = i;
            }
        }

        return serverBean;
    }



    @GetMapping("/hello2/{data}")
    public String helloApi2(@PathVariable String data) {
        System.out.println("对象:"+openFeigenTest);
        return openFeigenTest.helloApi2(data);
    }
    @GetMapping("/findAll")
    public CommonResult findAll(){
        System.out.println("data:"+ openFeigenTest.find());
        return new CommonResult(200,"success","");
    }

    @ResponseBody
    @PostMapping("/complete")
    public CommonResult<OpenApiData> complete(@RequestBody OpenApiData openApiData){
        return openFeigenTest.complete(openApiData);
    }

}
