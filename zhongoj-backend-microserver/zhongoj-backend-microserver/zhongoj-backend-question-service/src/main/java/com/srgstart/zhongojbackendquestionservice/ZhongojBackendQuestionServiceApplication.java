package com.srgstart.zhongojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.srgstart.zhongojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.srgstart")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.srgstart.zhongojbackendserviceclient.service")
public class ZhongojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhongojBackendQuestionServiceApplication.class, args);
    }

}
