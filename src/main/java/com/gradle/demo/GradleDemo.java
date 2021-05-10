package com.gradle.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @User: Administrator
 * @Time: 2021/5/7
 * @Description:
 */

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GradleDemo {
    public static void main(String[] args) {
        SpringApplication.run(GradleDemo.class, args);
    }
}