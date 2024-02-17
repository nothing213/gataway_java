//package com.config;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.web.client.RestTemplate;
//
//
//@Configuration
//@EnableFeignClients
//public class ApplicationContextConfig
//{
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate()
//    {
//        return new RestTemplate();
//    }
//
//    }
////applicationContext.xml <bean id="" class="">