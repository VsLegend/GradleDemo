package com.gradle.demo.activiti;

import com.gradle.demo.activiti.common.ProcessEngineCreator;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 用户以及用户组管理
 */
public class GroupAndUserManageDemo {

    public static void main(String[] args) {
        IdentityService identityService = ProcessEngineCreator.createIdentityService();
        // 创建新的用户组
        Group group = identityService.newGroup("1");
        group.setName("管理组");
        group.setType("manage");
        identityService.saveGroup(group);
        User user = identityService.newUser("1");
        user.setFirstName("李");
        user.setLastName("治");
        user.setPassword("123456");
        User user1 = identityService.newUser("2");
        user1.setFirstName("唐");
        user1.setLastName("梨");
        user1.setPassword("123456");
        identityService.saveUser(user);
        identityService.saveUser(user1);
        // 创建用户与组的关系
        identityService.createMembership(user.getId(), group.getId());

    }

}
