package com.wequan.bu.controller.vo.result;

/**
 * 响应结果生成工具
 * @author ChrisChen
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    public static Result success() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> success(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result fail(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}
