package com.example.springbootvue.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//controller 统一返回的包装类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private static final String SUCCESS_CODE = "200";
    private static final String AUTH_ERROR_CODE = "401";
    private static final String FORBIDDEN_CODE = "403";
    private static final String NOT_FOUND_CODE = "404";
    private static final String ERROR_CODE = "500";

    private String code;  // Http Status Code
    private String msg;   // Error Message
    private Object data;  // Packaged Data

    public static Result success(Object data){
        return new Result(SUCCESS_CODE, "success", data);
    }

    public static Result success(){
        return new Result(SUCCESS_CODE, "success", null);
    }

    public static Result error(String msg){
        return new Result(ERROR_CODE, msg, null);
    }

    public static Result error(String code, String msg){
        return new Result(code, msg, null);
    }

    public static Result error(){
        return new Result(ERROR_CODE, "error", null);
    }
}
