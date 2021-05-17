package com.gradle.demo.activiti;

import com.gradle.demo.activiti.common.ProcessEngineInstance;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @User: Administrator
 * @Time: 2021/5/17
 * @Description: ServiceTask测试
 */
@Slf4j
public class ServiceTaskDemo {

    public static void main(String[] args) {
        RepositoryService repositoryService = ProcessEngineInstance.createRepositoryService();
        RuntimeService runtimeService = ProcessEngineInstance.createRuntimeService();
        TaskService taskService = ProcessEngineInstance.createTaskService();
        IdentityService identityService = ProcessEngineInstance.createIdentityService();


        // 部署流程定义
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("processes/serviceTask.bpmn").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        String processInstanceId = processInstance.getId();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : taskList) {
            log.info("任务id：{}，任务名称：{}", task.getId(), task.getName());
        }

    }

}
