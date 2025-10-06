package com.knowledge.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果类
 *
 * @author system
 * @date 2025-10-04
 */
@Data
public class Result<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 状态码
     */
    private Integer code;
    
    /**
     * 返回信息
     */
    private String message;
    
    /**
     * 返回数据
     */
    private T data;
    
    /**
     * 时间戳
     */
    private Long timestamp;
    
    public Result() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public Result(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }
    
    public Result(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }
    
    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功");
    }
    
    /**
     * 成功返回结果
     *
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }
    
    /**
     * 成功返回结果
     *
     * @param message 返回信息
     * @param data    返回数据
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }
    
    /**
     * 失败返回结果
     */
    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败");
    }
    
    /**
     * 失败返回结果
     *
     * @param message 返回信息
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message);
    }
    
    /**
     * 失败返回结果
     *
     * @param code    状态码
     * @param message 返回信息
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }
}





