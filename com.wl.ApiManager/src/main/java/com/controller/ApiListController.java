package com.controller;


//import com.mangofactory.swagger.plugin.EnableSwagger;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.bean.ApiGroup;
import com.bean.CommonResult;
import com.bean.User;
import io.swagger.models.auth.In;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;


@RestController
public class ApiListController implements Serializable {
    private int num = 0;
    @Value("${server.port}")
    String port;
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    @GetMapping("/demo")
    public CommonResult demo() {

        return new CommonResult(200,"success","this is server:"+port);
    }
    @GetMapping("/OneApi")
    public int oneApi() {

        return 1;
    }

    @GetMapping("/twoApi")
    public int twoApi() {

        return 2;
    }
    @GetMapping("/threeApi")
    public int threeApi() {

        return 3;
    }
    @SentinelResource(value = "hello", blockHandler = "deal_hello")
    @GetMapping("/hello")
    public String helloApi() {
        return "this is hello api";
    }

    public String deal_hello(BlockException blockException) {
        return "this is hello blockException";
    }

    @GetMapping("/getSex")
    public String getSex() {
        return "man";
    }

    @GetMapping("/hello2/{data}")
    public String helloApi2(@PathVariable String data) {
        return data;
    }

    @GetMapping("/hello3/{data}/{data2}")
    public String helloApi3(@PathVariable int data, @PathVariable String data2) {
        return data + ":" + data2;
    }

    @PostMapping("/test")
    public String test(@RequestParam String data) {

        return data;
    }

    @PostMapping("/test4")
    public String test4(@RequestParam Map<Integer, String> data) {

        return data + ": ";
    }

    @PostMapping("/test3")
    public String test3(@RequestParam String data1, String data2) {

        return data1 + ": " + data2;
    }


    @GetMapping("/test2/{data}/{time}")
    public String test_get(@PathVariable String data, @PathVariable int time) {
        return data + " " + time;
    }

    @PostMapping("/test_body")
    public ApiGroup test_body(@RequestBody ApiGroup user) {
        return user;
    }

    @GetMapping("/dataSourceTest")
    public String[] dataSourceTest() {
        String[] datasource = {"hello", "test", "demo"};
        return datasource;
    }
}
