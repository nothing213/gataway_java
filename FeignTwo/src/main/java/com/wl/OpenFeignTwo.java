package com.wl;


import com.service.OpenFeigenTest;
import com.wl.conf.RuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients = {OpenFeigenTest.class})
@RibbonClient(value = "service-hi", configuration = RuleConfig.class)

public class OpenFeignTwo
{

    public static void main(String[] args) {

        SpringApplication.run(OpenFeignTwo.class, args);

    }
}
