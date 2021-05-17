package com.gradle.demo.activiti.demo;

import com.gradle.demo.config.ProcessEngineInstanceConfig;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

/**
 * @User: Administrator
 * @Time: 2021/5/10
 * @Description: 用户以及用户组管理
 *
 * IdentityService功能
 *
 * 它用于管理（创建，更新，删除，查询……）组与用户。
 *
 * 请重点注意，Activiti实际上在运行时并不做任何用户检查。例如任务可以分派给任何用户，而引擎并不会验证系统中是否存在该用户。
 *
 * 这是因为Activiti 有时要与LDAP、Active Directory等服务结合使用。
 */
public class GroupAndUserManageDemo {

    public static void main(String[] args) {
        IdentityService identityService = ProcessEngineInstanceConfig.createIdentityService();
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
