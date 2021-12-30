package com.controller;


//import com.mangofactory.swagger.plugin.EnableSwagger;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;


@RestController
public class ApiListController implements Serializable {
    private int num = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @SentinelResource(value = "hello",blockHandler = "deal_hello")
    @GetMapping("/hello")
    public String helloApi()
    {
        return "this is hello api";
    }
    public String deal_hello(BlockException blockException)
    {
        return "this is hello blockException";
    }

    @GetMapping("/helloApi2/{data}")
    public int helloApi2(@PathVariable int data)
    {
        num = 2;
        return num;
    }
    @GetMapping("/test")
    public String test()
    {

        return "this is test api";
    }

}
