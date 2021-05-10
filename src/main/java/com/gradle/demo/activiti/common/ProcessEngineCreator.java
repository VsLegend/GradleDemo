package com.gradle.demo.activiti.common;

import org.activiti.engine.*;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 流程实例创建
 */
public class ProcessEngineCreator {

    /**
     * 获取流程引擎
     * @return
     */
    public static ProcessEngine createProcessEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * 获取存储服务
     * @return
     */
    public static RepositoryService createRepositoryService() {
        return createProcessEngine().getRepositoryService();
    }

    /**
     * 获取运行时服务
     * @return
     */
    public static RuntimeService createRuntimeService() {
        return createProcessEngine().getRuntimeService();
    }

    /**
     * 获取任务服务
     * @return
     */
    public static TaskService createTaskService() {
        return createProcessEngine().getTaskService();
    }

    /**
     * 获取身份服务
     * @return
     */
    public static IdentityService createIdentityService() {
        return createProcessEngine().getIdentityService();
    }

    /**
     * 获取历史数据服务
     * @return
     */
    public static HistoryService createHistoryService() {
        return createProcessEngine().getHistoryService();
    }

}
