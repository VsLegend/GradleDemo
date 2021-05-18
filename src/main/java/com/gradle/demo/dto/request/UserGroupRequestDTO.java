package com.gradle.demo.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @User: Administrator
 * @Time: 2021/5/18
 * @Description:
 */

@Data
public class UserGroupRequestDTO implements Serializable {

    private String firstName;

    private String lastName;

    private String password;

    private String email;


    // group
    private String groupName;

    private String groupType;

}
