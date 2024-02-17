package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class gataway2 {
    public static void main(String[] args) {
        SpringApplication.run(gataway2.class, args);
    }
}
