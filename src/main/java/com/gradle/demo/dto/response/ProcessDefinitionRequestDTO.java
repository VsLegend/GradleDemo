package com.gradle.demo.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @User: Administrator
 * @Time: 2021/5/18
 * @Description:
 */
@Data
public class ProcessDefinitionRequestDTO implements Serializable {

    private String id;
    private String name;
    private String key;
    private String resourceName;
    private String deploymentId;
    private String description;
}
