package com.config;

import com.controller.MyLoadBalancerRule;
import com.netflix.loadbalancer.IRule;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFeignConfiguration {
    @Bean
    public IRule myRule() {
        return new MyLoadBalancerRule();
    }
    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }
}