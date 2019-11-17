package com.lx.validator.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    /**
     * 请求是否成功处理
     */
    private boolean success;
    /**
     * 业务状态码
     */
    private int code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回结果
     */
    private Object data;


    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        return new Result(true, 200, "OK", null);
    }

    public static Result fail(int code, String message, Object data) {
        return new Result(false, code, message, data);
    }

    public static Result fail(int code, String message) {
        return fail(code, message, null);
    }
}
