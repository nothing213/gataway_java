package com;


//import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.service.OpenFeigenTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

//(clients = {OpenFeigenTest.class})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Feign
{
    public static void main(String[] args) {

        SpringApplication.run(Feign.class, args);
    }
}
