package com.controller;

import com.bean.CommonResult;
import com.service.OpenFeigenTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Cus {
    @Autowired
    OpenFeigenTest openFeigenTest;

    @GetMapping("/demo")
    public CommonResult demo(){
        System.out.println("对象："+openFeigenTest);
        return openFeigenTest.demo();
    }

    @GetMapping("/hello2/{data}")
    public String helloApi2(@PathVariable String data){
        return openFeigenTest.helloApi2(data);
    }
}
