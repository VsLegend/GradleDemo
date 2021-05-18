package com.gradle.demo.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @User: Administrator
 * @Time: 2021/5/18
 * @Description:
 */
@Data
public class TaskResponseDTO implements Serializable {

    private String id;

    private String name;

    private String assignee;

    private String executionId;

    private String owner;

    private Map<String, Object> processVariables;

    private String processDefinitionId;

    private String description;

    private Date createTime;
}
