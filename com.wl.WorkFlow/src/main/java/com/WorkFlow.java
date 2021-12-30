package com;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude  = SecurityAutoConfiguration.class)
@EnableDiscoveryClient
public class WorkFlow {
    public static void main(String[] args) {
        SpringApplication.run(WorkFlow.class, args);
    }
}
