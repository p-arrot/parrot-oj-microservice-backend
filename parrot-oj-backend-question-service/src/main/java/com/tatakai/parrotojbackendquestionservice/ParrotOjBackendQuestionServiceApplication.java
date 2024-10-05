package com.tatakai.parrotojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tatakai.parrotojbackendserviceclient")
@MapperScan("com.tatakai.parrotojbackendquestionservice.mapper")
@ComponentScan("com.tatakai")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ParrotOjBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParrotOjBackendQuestionServiceApplication.class, args);
    }

}
