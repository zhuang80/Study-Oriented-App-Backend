package com.wequan.bu.controller.vo.result;

/**
 * 响应结果生成工具
 * @author ChrisChen
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "success";

    /**
     * 请求成功，不需要返回数据
     * @return 响应结果
     */
    public static Result success() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 请求成功，需要返回数据
     * @param data 返回数据
     * @param <T> 类型
     * @return 响应结果
     */
    public static <T> Result<T> success(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 请求失败
     * @param message 失败消息
     * @return 响应结果
     */
    public static <T> Result<T> fail(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    /**
     * 请求失败
     * @param code 状态码
     * @param message 失败消息
     * @return 响应结果
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

}
