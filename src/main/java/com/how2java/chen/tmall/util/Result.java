package com.how2java.chen.tmall.util;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一封装（包装）响应结果
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:21:53
 */

public class Result {

    public static final Integer SUCCESS_CODE = 0;
    public static final Integer FALL_CODE = 1;

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String message;

    @Setter
    @Getter
    private Object data;


    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static Result success() {
        return new Result(SUCCESS_CODE, null, null);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, "success", data);
    }

    public static Result fail(String message) {
        return new Result(FALL_CODE, message, null);
    }

}
