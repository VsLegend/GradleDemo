package com.gradle.demo.activiti.demo;

import com.gradle.demo.config.ProcessEngineInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User: Administrator
 * @Time: 2021/5/14
 * @Description: 任务的执行流
 */
@Slf4j
public class TaskWithExecutionDemo {

    public static void main(String[] args) {
        RepositoryService repositoryService = ProcessEngineInstanceConfig.createRepositoryService();
        RuntimeService runtimeService = ProcessEngineInstanceConfig.createRuntimeService();
        TaskService taskService = ProcessEngineInstanceConfig.createTaskService();
        IdentityService identityService = ProcessEngineInstanceConfig.createIdentityService();


        // 部署流程定义
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("processesBackup/ask_for_leave.bpmn20.xml").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        User user = identityService.createUserQuery().userId("1").singleResult();


        // 创建一个带有参数的流程实例
        /**
         * 引擎并没有处理required这个状态，只是在取这个变量时，会有报错
         * WARN org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior -
         * property not found in task description expression Unknown property used in expression:
         * Your manager has disapproved your vacation request for ${numberOfDays} days. Reason:${managerMotivation}
         */
        Map<String, Object> variables = new HashMap<>();
        variables.put("employeeName", user.getFirstName() + user.getLastName());
        variables.put("numberOfDays", 4);
        variables.put("vacationMotivation", "I'm really tired!");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);
        String processInstanceId = processInstance.getId();

        // 当前请假人为执行人
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
//        taskList.forEach(task -> log.info("任务信息-----------ID：{}， NAME{}, PI：{}",
//                task.getId(), task.getName(), task.getProcessInstanceId()
//        ));
        Task task = taskList.get(0);

        taskService.setOwner(task.getId(), user.getId());


        /**
         * 执行流包含1个主执行流，多个子执行流
         * 一个流程实例的主执行流只能有一个，子执行流的个数与当前需要执行的task数量（如并行执行时的多个并行task）有关
         */
        List<Execution> executionList = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
//        executionList.forEach(execution -> log.info("执行流信息-----------ID：{}， NAME{}, PI：{}",
//                execution.getId(), execution.getName(), execution.getProcessInstanceId()));
        // 运行时增加执行流参数
        // 全局执行流参数，所有执行流都能查到
//        runtimeService.setVariable(executionId, "key-variable-A", "value-variable-A");
        // 本地执行流参数，只有当前执行流能查到
//        runtimeService.setVariableLocal(executionId, "key-variable-B", "value-variable-B");


        // 提交任务时，传递参数，该流程将进入下一个节点
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);




    }

}
