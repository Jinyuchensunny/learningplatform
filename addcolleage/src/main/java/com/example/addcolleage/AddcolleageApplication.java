package com.example.addcolleage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
@EnableDiscoveryClient//代表自己是一个服务提供方
public class AddcolleageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddcolleageApplication.class, args);
    }

}
