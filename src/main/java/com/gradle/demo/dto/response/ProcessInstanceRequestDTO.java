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
public class ProcessInstanceRequestDTO implements Serializable {

    private String id;
    private String name;
    private String processDefinitionId;
    private String processDefinitionName;
    private String processDefinitionKey;
    private Map<String, Object> processVariables;

    private String deploymentId;

    private String description;

    private Date startTime;
}
