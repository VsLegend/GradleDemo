package com.gradle.demo.activiti.demo;

import com.gradle.demo.config.ProcessEngineInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 任务管理
 *
 * TaskService功能：
 *
 * 查询分派给用户或组的任务
 *
 * 创建standalone（独立运行）任务。这是一种没有关联到流程实例的任务。
 *
 * 决定任务的执行用户（assignee），或者将用户通过某种方式与任务关联。
 *
 * 认领（claim）与完成（complete）任务。认领是指某人决定成为任务的执行用户，也即他将会完成这个任务。完成任务是指“做这个任务要求的工作”，通常是填写某种表单。
 */
@Slf4j
public class TaskManageDemo {

    public static void main(String[] args) throws FileNotFoundException {

        IdentityService identityService = ProcessEngineInstanceConfig.createIdentityService();
        TaskService taskService = ProcessEngineInstanceConfig.createTaskService();


        String processInstanceId = "12501";
        // 任务列表，一个流程实例当前有一个或多个任务（如并行执行时的多个task）
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String taskId = task.getId();
//        log.info("流程节点的任务信息-----任务Id：{}，任务名称：{}，实例id{}", taskId, task.getName(), task.getProcessInstanceId());

        /**
         * 附件保存
         * URL提供文件内容
         * 输入流提供文件内容
         */
        Attachment attachment = taskService.createAttachment("附件类型", taskId, processInstanceId,
                "附件名称", "附件描述", "附件地址");
        Attachment attachmentStream = taskService.createAttachment("附件类型", taskId, processInstanceId,
                "附件名称", "附件描述", new FileInputStream(new File("file.name.xml")));
        Attachment attachment1 = taskService.getAttachment(attachment.getId());
        InputStream attachmentContent = taskService.getAttachmentContent(attachment.getId());
        // 获取task下的所有附件
        List<Attachment> taskAttachments = taskService.getTaskAttachments(taskId);
        // 获取流程实例下的所有附件（包含所有task）
        List<Attachment> processInstanceAttachments = taskService.getProcessInstanceAttachments(processInstanceId);


        /**
         * 设置本地参数或全局参数，会保存到ACT_RU_VARIABLE表中
         */
        // 参数的作用域仅限于当前节点（当前Task）
        taskService.setVariableLocal(taskId, "key", "value");
        taskService.setVariablesLocal(taskId, new HashMap<>());

        // 参数的作用域作用于全局
        taskService.setVariable(taskId, "key", "value");
        taskService.setVariables(taskId, new HashMap<>());


        /**
         * 任务候选组、候选人以及任务处理人和代理人等相关任务设置
         */
        Group group = identityService.createGroupQuery().groupId("1").singleResult();
        User user = identityService.createUserQuery().userId("1").singleResult();
        // 设置候选组，仅允许该组的人进行任务的接受
        taskService.addCandidateGroup(task.getId(), group.getId());
        taskService.addCandidateUser(taskId, user.getId());

        // 查询用户有权处理的任务
        List<Task> candidateList = taskService.createTaskQuery().taskCandidateUser(user.getId()).list();
        String candidate = candidateList.stream().map(Task::getName).collect(Collectors.joining(" "));
//        log.info("查询用户有权处理的任务：{}", candidate);

        // 查询用户已经领取的任务
        List<Task> ownerList = taskService.createTaskQuery().taskOwner(user.getId()).list();
        String owner = ownerList.stream().map(Task::getName).collect(Collectors.joining(" "));
//        log.info("查询用户有权处理的任务：{}", owner);

        // 设置任务执行人
//        task.setOwner("2");
//        taskService.saveTask(task);
        taskService.setOwner(task.getId(), "2");

        // 设置代理人 这里的用户都不会去验证，他是否存在于用户系统中（IdentityService）
        task.setAssignee("2");
        taskService.saveTask(task);
        // 这里不会对task是否有Assignee做验证，而claim方法则会验证，后者在有Assignee的时候会报错 ActivitiTaskAlreadyClaimedException
        taskService.setAssignee(taskId, "2");
        taskService.claim(taskId, "2");


        // 完成任务
        taskService.complete(taskId);

    }

}
