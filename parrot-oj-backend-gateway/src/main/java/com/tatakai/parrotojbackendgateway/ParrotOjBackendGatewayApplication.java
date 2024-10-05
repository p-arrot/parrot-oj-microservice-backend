package com.tatakai.parrotojbackendgateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tatakai.parrotojbackendserviceclient")
@ComponentScan("com.tatakai")
public class ParrotOjBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParrotOjBackendGatewayApplication.class, args);
    }

}
