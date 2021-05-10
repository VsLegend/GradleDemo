package com.gradle.demo.controller;

import com.gradle.demo.dto.ClRequestDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wangjunwei
 * @Date 2019/8/6
 * @Description
 */
@RestController
@RequestMapping("/client")
public class ClientController {

  @GetMapping("/getInfo")
  public String getInfo(@RequestBody @Validated ClRequestDTO clRequestDTO) {
    return "client 返回数据";
  }
}
