package com.gradle.demo.activiti.demo;

import com.gradle.demo.config.ProcessEngineInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 任务实例
 */
@Slf4j
public class RuntimeManageAndProcessInstanceDemo {
    public static void main(String[] args) {
        RepositoryService repositoryService = ProcessEngineInstanceConfig.createRepositoryService();
        RuntimeService runtimeService = ProcessEngineInstanceConfig.createRuntimeService();
        // 获取刚刚载入的流程定义
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().deploymentId("7501");
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

        // 根据流程定义创建流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
//        log.info("流程实例信息-----实例Id：{}，实例KEY：{}，实例名称{}", processInstance.getId(), processInstance.getBusinessKey(), processInstance.getName());

        // 查询流程实例
        ProcessInstance singleResult = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
//        log.info("流程实例信息-----实例Id：{}，实例KEY：{}，实例名称{}", singleResult.getId(), singleResult.getBusinessKey(), singleResult.getName());
    }

}
