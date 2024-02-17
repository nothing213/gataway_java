package com.controller;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MyLoadBalancerRule extends AbstractLoadBalancerRule {
    private AtomicInteger nextServerCyclicCounter;
    private ILoadBalancer lb;
    public MyLoadBalancerRule() {
        nextServerCyclicCounter = new AtomicInteger(0);
    }
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        //获取ILoadBalancer对象
        lb = getLoadBalancer();
    }
    @Override
    public Server choose(Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        int count = 0;
        while (server == null && count++ < 10) {
            List<Server> reachableServers = lb.getReachableServers();
            List<Server> allServers = lb.getAllServers();
            int serverCount = allServers.size();
            if (serverCount == 0) {
                return null;
            }
            //算法逻辑
            server = reachableServers.get(nextServerCyclicCounter.incrementAndGet() % reachableServers.size());
            if (server == null) {
                Thread.yield();
                continue;
            }
            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }
            server = null;
            Thread.yield();
        }
        if (count >= 10) {
            System.out.println("No available alive servers after 10 tries from load balancer: " + lb);
        }
        System.out.println("这里是自定义负载均衡");
        return server;
    }
}