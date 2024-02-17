package com.wl.controller;

import com.wl.bean.ServerBean;
import com.wl.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
public class TestController {

    @Resource
    ServerService serverService;

    @GetMapping("/testServer")
    public String insertTestServer()
    {
        Random r = new Random();
        ServerBean serverBean = new ServerBean("Test2","TestHost","9999",0.4f);
        serverBean.setServerName("Test");
        serverBean.setServerPort("1111");
        serverBean.setServerHost("1.1.1.1");
        serverBean.setServerLB(r.nextFloat());

        if(serverService.setServerMsg(serverBean))
        {
            return "success";
        }else{
            return "false";
        }

    }
}
