package com.gradle.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
public class GradleDemo {
    public static void main(String[] args) {
        SpringApplication.run(GradleDemo.class, args);
    }
}