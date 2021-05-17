package com.gradle.demo.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;

/**
 * @User: Administrator
 * @Time: 2021/5/8
 * @Description:
 */


@Slf4j
public class WorkflowDemo {

    public static void main(String[] args) {
        // 初始化流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 存储服务
        RepositoryService repositoryService = engine.getRepositoryService();
        // 部署后载入流程定义
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("processesBackup/ask_for_leave.bpmn20.xml");
        Deployment deploy = deployment.deploy();


        // 获取刚刚载入的流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId());
        ProcessDefinition definition = processDefinitionQuery.singleResult();


        // 运行时服务
        RuntimeService runtimeService = engine.getRuntimeService();
        // 流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(definition.getId());
        String processId = processInstance.getId();



        // 任务服务
        TaskService taskService = engine.getTaskService();
        // 当前task任务
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        log.info("当前任务：{}， 任务信息：{}", task.getName(), task.getOwner());
        // 设置代理人
//        taskService.setAssignee(task.getId(), "1");
        // 设置流程节点需要的参数
        HashMap<String, Object> map = new HashMap<>();
        map.put("day",1);
        taskService.setVariables(task.getId(),map);
        // 完成当前任务，流程会转向下一个任务节点
        taskService.complete(task.getId());


        // 关闭引擎
        engine.close();
    }
}
