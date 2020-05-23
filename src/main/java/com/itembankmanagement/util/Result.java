package com.itembankmanagement.util;


import lombok.Getter;

public class Result<T> {
    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private T data;

    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(){
        return (Result<T>)success("");
    }

    public static <T> Result<T> error(int code, String message){
        return new Result<>(code, message);
    }

    private Result(T data){
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(int code, String message){
        this.code = code;
        this.message = message;
    }

}
