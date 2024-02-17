package com.wl.controller;

import com.google.gson.JsonObject;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import com.service.OpenFeigenTest;
import com.wl.bean.ServerBean;
import com.wl.service.Impl.ServerServiceImpl;
import com.wl.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.alibaba.csp.sentinel.cluster.server.EmbeddedClusterTokenServerProvider.getServer;

/**
 * @author wl
 * @version 1.0
 * @date 2023/11/5 上午10:22
 */
@Slf4j
public class MyRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }

    @Resource
    ServerService serverService ;
//寻找最小负载值得服务器
    @Override
    public Server choose(Object o) {
//        log.info("key:" + o);
        List<Server> allServers = getLoadBalancer().getAllServers();
        System.out.println("server:"+allServers);


        int flag = 0;
        //serverService.getServerLBIndex返回最小负载服务节点下标
        System.out.println("min lb is"+serverService.getServerLBIndex());
        ServerBean serverBean = serverService.getServerLBIndex();
        String temp = serverBean.getServerHost()+":"+serverBean.getServerPort();
//        System.out.println("serverString:"+allServers.get(1).toString());
        for(int i = 0;i <allServers.size();i++)
        {


            if(temp.equals(allServers.get(i).toString()))
            {
                flag = i;
            }

        }

//        System.out.println("flag:"+flag);
//        System.out.println(serverBean.getServerHost()+":"+serverBean.getServerPort());
        return allServers.get(flag);
    }
}