package com.gradle.demo.controller;

import com.gradle.demo.dto.common.APIResult;
import com.gradle.demo.dto.response.ProcessDefinitionRequestDTO;
import com.gradle.demo.dto.response.ProcessInstanceRequestDTO;
import com.gradle.demo.utils.BeanConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Wangjunwei
 * @Date 2021/5/17
 * @Description
 */

@Api(tags = "流程实例相关")
@RestController
@RequestMapping(value = "/processes")
public class ProcessesController {


  @Resource
  private RepositoryService repositoryService;

  @Resource
  private RuntimeService runtimeService;


  @ApiOperation(value = "查看所有流程定义信息")
  @GetMapping("/getProcessDefinition")
  public APIResult<?> getProcessDefinition() {
    List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
    List<ProcessDefinitionRequestDTO> result = BeanConvertUtils.copyList(list, ProcessDefinitionRequestDTO.class);
    return APIResult.success(result);
  }

  @ApiOperation(value = "停用流程定义")
  @DeleteMapping("/deleteProcessDefinition/{processDefinitionId}")
  public APIResult<?> deleteProcessDefinition(@PathVariable String processDefinitionId) {
    repositoryService.suspendProcessDefinitionById(processDefinitionId);
    return APIResult.success();
  }


  @ApiOperation(value = "创建一个流程实例")
  @PostMapping("/createProcessInstance/{processDefinitionId}")
  public APIResult<?> createProcessInstance(@PathVariable String processDefinitionId) {
    ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
    ProcessInstanceRequestDTO result = BeanConvertUtils.copyBean(processInstance, ProcessInstanceRequestDTO.class);
    return APIResult.success(result);
  }

  @ApiOperation(value = "获取流程实例详情")
  @GetMapping("/getProcessInstance/{processInstanceId}")
  public APIResult<?> getProcessInstance(@PathVariable String processInstanceId) {
    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    ProcessInstanceRequestDTO result = BeanConvertUtils.copyBean(processInstance, ProcessInstanceRequestDTO.class);
    return APIResult.success(result);
  }

}
