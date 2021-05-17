package com.gradle.demo.controller;

import com.gradle.demo.dto.common.APIResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wangjunwei
 * @Date 2019/8/6
 * @Description
 */

@Api(tags = "节点流程")
@RestController
@RequestMapping(value = "/nodeTransfer")
public class FlowTransferController {

  @Resource
  private TaskService taskService;

  @ApiOperation(value = "查看所有任务")
  @GetMapping("/getTaskList")
  public Object getTaskList() {
    List<Task> list = taskService.createTaskQuery().list();
    return APIResult.success(list);
  }

}
