package com.gradle.demo.service;

import org.activiti.engine.task.Task;

/**
 * @User: Administrator
 * @Time: 2021/5/18
 * @Description:
 */
public interface TaskHandleService {

    public Task singleResult(String userId, String taskId);

}
