package com.gradle.demo.dto.request;

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
public class TaskRequestDTO implements Serializable {

    private String taskId;

    private String userId;

    private Map<String, Object> variables;

}
