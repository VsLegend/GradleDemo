package com.gradle.demo.dto.common;

import lombok.Data;

/**
 * @User: Administrator
 * @Time: 2021/5/7
 * @Description:
 */

@Data
public class APIResult<T> {

    private String message = "";

    private Integer code = 1000;

    private T data = null;

    public APIResult() {
    }

    public APIResult(T data) {
        this.data = data;
    }

    public APIResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public APIResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <U> APIResult<U> success(U data) {
        return new APIResult<>(data);
    }


    public static <U> APIResult<U> failed(Integer code, String message) {
        return new APIResult<>(code, message);
    }
}
