package com.tatakai.parrotojbackenduserservice;

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
@MapperScan("com.tatakai.parrotojbackenduserservice.mapper")
@ComponentScan("com.tatakai")
public class ParrotOjBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParrotOjBackendUserServiceApplication.class, args);
    }

}
