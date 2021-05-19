package com.gradle.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @User: Administrator
 * @Time: 2021/5/7
 * @Description:
 */


@EnableOpenApi
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class
})
public class ActivitiGradleDemo {
    public static void main(String[] args) {
        SpringApplication.run(ActivitiGradleDemo.class, args);
    }
}