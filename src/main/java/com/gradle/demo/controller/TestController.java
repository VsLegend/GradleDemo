package com.gradle.demo.controller;

import com.gradle.demo.dto.ClRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @User: Administrator
 * @Time: 2021/5/17
 * @Description:
 */

@Api(tags = "测试swagger")
@RestController
@RequestMapping("/test")
public class TestController {


    @ApiOperation(value = "测试")
    @PostMapping("/test")
    public String test(@RequestBody @Validated ClRequestDTO clRequestDTO) {
        System.out.println(clRequestDTO.getName());
        return "client 返回数据";
    }
}
