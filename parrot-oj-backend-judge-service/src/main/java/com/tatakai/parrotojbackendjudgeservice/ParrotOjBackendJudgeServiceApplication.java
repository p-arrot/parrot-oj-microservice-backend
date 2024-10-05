package com.tatakai.parrotojbackendjudgeservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tatakai.parrotojbackendserviceclient")
@ComponentScan("com.tatakai")
public class ParrotOjBackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParrotOjBackendJudgeServiceApplication.class, args);
    }

}
