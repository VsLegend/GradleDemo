package com.gradle.demo.controller;

import com.gradle.demo.dto.common.APIResult;
import com.gradle.demo.dto.request.UserGroupRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author Wangjunwei
 * @Date 2021/5/17
 * @Description
 */

@Api(tags = "用户用户组管理")
@RestController
@RequestMapping(value = "/userManager")
public class UserAndGroupController {

  @Resource
  private IdentityService identityService;

  @ApiOperation(value = "查看所有用户")
  @GetMapping("/getUserList")
  public APIResult<?> getUserList() {
    List<User> list = identityService.createUserQuery().list();
    return APIResult.success(list);
  }

  @ApiOperation(value = "查看所有用户组")
  @GetMapping("/getUserGroupList")
  public APIResult<?> getUserGroupList() {
    List<Group> list = identityService.createGroupQuery().list();
    return APIResult.success(list);
  }

  @ApiOperation(value = "添加用户")
  @PostMapping("/addUser")
  public APIResult<?> addUser(@RequestBody UserGroupRequestDTO dto) {
    User user = identityService.newUser(UUID.randomUUID().toString());
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setPassword(dto.getPassword());
    user.setEmail(dto.getEmail());
    identityService.saveUser(user);
    return APIResult.success();
  }

  @ApiOperation(value = "添加组")
  @PostMapping("/addGroup")
  public APIResult<?> addGroup(@RequestBody UserGroupRequestDTO dto) {
    Group group = identityService.newGroup(UUID.randomUUID().toString());
    group.setName(dto.getGroupName());
    group.setType(dto.getGroupType());
    identityService.saveGroup(group);
    return APIResult.success();
  }

}
