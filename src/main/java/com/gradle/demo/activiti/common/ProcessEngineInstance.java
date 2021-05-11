package com.gradle.demo.activiti.common;

import org.activiti.engine.*;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 流程实例创建
 */
public class ProcessEngineInstance {

    private static final ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();;


    /**
     * 获取流程引擎
     * @return
     */
    public static ProcessEngine createProcessEngine() {
        return processEngine;
    }

    /**
     * 获取存储服务
     * @return
     */
    public static RepositoryService createRepositoryService() {
        return processEngine.getRepositoryService();
    }

    /**
     * 获取运行时服务
     * @return
     */
    public static RuntimeService createRuntimeService() {
        return processEngine.getRuntimeService();
    }

    /**
     * 获取任务服务
     * @return
     */
    public static TaskService createTaskService() {
        return processEngine.getTaskService();
    }

    /**
     * 获取身份服务
     * @return
     */
    public static IdentityService createIdentityService() {
        return processEngine.getIdentityService();
    }

    /**
     * 获取历史数据服务
     * @return
     */
    public static HistoryService createHistoryService() {
        return processEngine.getHistoryService();
    }

}
