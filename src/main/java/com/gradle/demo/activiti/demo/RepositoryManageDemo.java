package com.gradle.demo.activiti.demo;

import com.gradle.demo.config.ProcessEngineInstanceConfig;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;


/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 存储以及构建实例
 *
 * RepositoryService功能：
 *
 * 所有有关“静态”数据（例如流程定义）的东西，都可以通过RepositoryService访问。从概念上说，所有这种静态数据，都是Activiti引擎“仓库（repository）”中的内容。
 *
 * 提供了管理与控制deployments（部署）与process definitions（流程定义）的操作
 *
 * 查询引擎已知的部署与流程定义。
 *
 * 暂停或激活部署中的某些流程，或整个部署。暂停意味着不能再对它进行操作，激活是其反操作。
 *
 * 读取各种资源，比如部署中保存的文件，或者引擎自动生成的流程图。
 *
 * 读取POJO版本的流程定义。使用它可以用Java而不是xml的方式检查流程。
 */
public class RepositoryManageDemo {

    public static void main(String[] args) {
        // 存储服务
        RepositoryService repositoryService = ProcessEngineInstanceConfig.createRepositoryService();
        RuntimeService runtimeService = ProcessEngineInstanceConfig.createRuntimeService();
        // 载入流程定义
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment.addClasspathResource("processesBackup/ask_for_leave.bpmn20.xml");
        Deployment deploy = deployment.deploy();
        // 7501 null null
        System.out.println(deploy.getId() + deploy.getKey() + deploy.getName());

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        String processDefinitionId = processDefinition.getId();

        /**
         * 流程定义控制
         */
        // 暂停流程定义 当流程定义暂停后，不能再创建新的流程实例（会抛出异常）
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
        // 重新激活流程定义
        repositoryService.activateProcessDefinitionById(processDefinitionId);


        /**
         * 流程实例控制
         */
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        String processInstanceId = processInstance.getId();
        // 当流程实例暂停后，不能进行流程操作（例如完成任务会抛出异常），作业（如定时器）也不会执行
        runtimeService.suspendProcessInstanceById(processInstanceId);
        // 激活实例
        runtimeService.activateProcessInstanceById(processInstanceId);
    }

}
