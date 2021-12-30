package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class eureka2
{
    public static void main(String[] args) {
        SpringApplication.run(eureka2.class, args);
    }
}
