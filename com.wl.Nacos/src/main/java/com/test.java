package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther zzyy
 * @create 2020-02-23 14:12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class test
{
    public static void main(String[] args) {
        SpringApplication.run(test.class, args);
    }
}
