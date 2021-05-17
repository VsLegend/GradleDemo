package com.gradle.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @User: Administrator
 * @Time: 2021/5/7
 * @Description:
 */

@Data
public class ClRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "姓名不能为空！")
    private String name;

    @NotNull(message = "年龄不能为空！")
    private Integer age;

}
