package com.gradle.demo.activiti;

import com.gradle.demo.activiti.common.ProcessEngineCreator;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description:
 */
@Slf4j
public class TaskManageDemo {

    public static void main(String[] args) {
        IdentityService identityService = ProcessEngineCreator.createIdentityService();
        TaskService taskService = ProcessEngineCreator.createTaskService();


        Task task = taskService.createTaskQuery().processInstanceId("12501").singleResult();
        String taskId = task.getId();
        log.info("流程节点的任务信息-----任务Id：{}，任务名称：{}，实例id{}", taskId, task.getName(), task.getProcessInstanceId());

        Group group = identityService.createGroupQuery().groupId("1").singleResult();
        User user = identityService.createUserQuery().userId("1").singleResult();
        // 设置候选组，仅允许该组的人进行任务的接受
//        taskService.addCandidateGroup(task.getId(), group.getId());
//        taskService.addCandidateUser(taskId, user.getId());

        // 查询用户有权处理的任务
        List<Task> candidateList = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
        String candidate = candidateList.stream().map(Task::getName).reduce("", (a, b) -> a  + " " + b);
        log.info("查询用户有权处理的任务：{}", candidate);

        // 查询用户已经领取的任务
        List<Task> ownerList = taskService.createTaskQuery().taskOwner(user.getId()).list();
        String owner = ownerList.stream().map(Task::getName).reduce("", (a, b) -> a  + " " + b);
        log.info("查询用户有权处理的任务：{}", owner);

        // 设置任务执行人
//        taskService.setOwner(task.getId(), "2");

        // 设置代理人
//        taskService.setAssignee(taskId, "2");
//        taskService.claim(taskId, "2");


        // 设置参数，会保存到ACT_RU_VARIABLE表中
        taskService.setVariable(taskId, "key", "value");
        taskService.setVariables(taskId, new HashMap<>());

        // 完成任务
//        taskService.complete(taskId);

    }

}
