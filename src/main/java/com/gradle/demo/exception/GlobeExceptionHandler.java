package com.gradle.demo.exception;


import com.gradle.demo.dto.common.APIResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.stream.Collectors;

/**
 * @User: Administrator
 * @Time: 2021/5/7
 * @Description:
 */

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobeExceptionHandler {


    @ExceptionHandler(Exception.class)
    public APIResult<String> processException(NativeWebRequest request, Exception e) {
        log.error("exception", e);
        return APIResult.failed(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public APIResult<String> processValidationException(RuntimeException e) {
        log.error("runtime exception {} ", e);
        return APIResult.failed(9001, e.getMessage());
    }


    /**
     * 优化valid验证参数提示
     *
     * @param exception 异常信息
     * @return APIResult 响应信息
     */
    @ExceptionHandler(value = BindException.class)
    public APIResult<?> bindException(BindException exception) {
        String message = exception.getFieldErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        return APIResult.failed(5001, message);
    }
    /**
     * 处理请求对象属性不满足校验规则的异常信息
     * as
     * @param exception 异常
     * @return APIResult 响应结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public APIResult<?> exception(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String message = result.getFieldErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        return APIResult.failed(5002, message);
    }
}
