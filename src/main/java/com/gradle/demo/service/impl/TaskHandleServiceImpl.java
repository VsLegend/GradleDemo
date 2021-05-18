package com.gradle.demo.service.impl;

import com.gradle.demo.service.TaskHandleService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @User: Administrator
 * @Time: 2021/5/18
 * @Description:
 */
@Service
public class TaskHandleServiceImpl implements TaskHandleService {

    @Resource
    private TaskService taskService;

    @Override
    public Task singleResult(String userId, String taskId) {
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskId(taskId).singleResult();
        return task;
    }
}
