package com.gradle.demo.controller;

import com.gradle.demo.dto.common.APIResult;
import com.gradle.demo.dto.request.TaskRequestDTO;
import com.gradle.demo.dto.response.TaskResponseDTO;
import com.gradle.demo.service.TaskHandleService;
import com.gradle.demo.utils.BeanConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Wangjunwei
 * @Date 2021/5/17
 * @Description
 */

@Api(tags = "节点任务")
@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @Resource
    private IdentityService identityService;

    @Resource
    private TaskHandleService taskHandleService;


    @ApiOperation(value = "查看实例的所有任务")
    @GetMapping("/getTaskList/{processInstanceId}")
    public APIResult<?> getTaskList(@PathVariable String processInstanceId) {
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<TaskResponseDTO> result = BeanConvertUtils.copyList(list, TaskResponseDTO.class);
        return APIResult.success(result);
    }

    @ApiOperation(value = "设置参与组")
    @PutMapping("/putCandidateGroup/{groupId}/{taskId}")
    public APIResult<?> putCandidateGroup(@PathVariable String groupId, @PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Objects.requireNonNull(task);
        if (!StringUtils.isEmpty(task.getOwner()))
            return APIResult.failed(5001, "当前任务已有owner");
        taskService.addCandidateGroup(taskId, groupId);
        return APIResult.success();
    }

    @ApiOperation(value = "查看用户的可接或参与的任务")
    @GetMapping("/getTaskListByUserId/{userId}")
    public APIResult<?> getTaskListByUserId(@PathVariable String userId) {
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
        List<TaskResponseDTO> result = BeanConvertUtils.copyList(list.stream()
                .filter(task -> StringUtils.isEmpty(task.getOwner())).collect(Collectors.toList()), TaskResponseDTO.class);
        return APIResult.success(result);
    }

    @ApiOperation(value = "接受任务")
    @PutMapping("/acceptTask/{userId}/{taskId}")
    public APIResult<?> getTaskListByUserId(@PathVariable String userId, @PathVariable String taskId) {
        Task task = taskHandleService.singleResult(userId, taskId);
        Objects.requireNonNull(task);
        if (!StringUtils.isEmpty(task.getOwner()))
            return APIResult.failed(5001, "当前任务已有owner");
        taskService.setOwner(taskId, userId);
        return APIResult.success();
    }

    @ApiOperation(value = "完成任务")
    @PutMapping("/completeTask")
    public APIResult<?> completeTask(@RequestBody TaskRequestDTO dto) {
        String userId = dto.getUserId();
        String taskId = dto.getTaskId();
        Map<String, Object> variables = dto.getVariables();
        Task task = taskHandleService.singleResult(userId, taskId);
        Objects.requireNonNull(task);
        if (!task.getOwner().equals(dto.getUserId()))
            return APIResult.failed(4001, "非任务处理人");
        taskService.complete(taskId, variables);
        return APIResult.success();
    }

}
