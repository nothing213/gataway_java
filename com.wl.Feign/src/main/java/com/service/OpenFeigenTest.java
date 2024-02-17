package com.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.bean.CommonResult;

//import com.bean.ServerBean;
import com.bean.OpenApiData;
import com.bean.ServerBean;
import com.config.MyFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//,url = "http://localhost:8001"
//@Component
@FeignClient(name = "ApiManager",configuration = MyFeignConfiguration.class)
public interface OpenFeigenTest {
    @GetMapping("/demo")
    public CommonResult demo();

    @GetMapping("/hello2/{data}")
    public String helloApi2(@PathVariable String data);

    @GetMapping("/getServerMsg")
    public Object getServerMsg();
    @GetMapping("/findAll")
    public CommonResult find();

    @ResponseBody
    @PostMapping("/complete")
    public CommonResult<OpenApiData> complete(@RequestBody OpenApiData openApiData);

}
