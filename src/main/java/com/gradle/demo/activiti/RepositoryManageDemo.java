package com.gradle.demo.activiti;

import com.gradle.demo.activiti.common.ProcessEngineCreator;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;


/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 存储以及构建实例
 */
public class RepositoryManageDemo {

    public static void main(String[] args) {
        // 存储服务
        RepositoryService repositoryService = ProcessEngineCreator.createRepositoryService();
        // 载入流程定义
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("processes/ask__for_leave.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        // 7501 null null
        System.out.println(deploy.getId() + deploy.getKey() + deploy.getName());
    }

}
