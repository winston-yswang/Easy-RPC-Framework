package tech.wys.rpc.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author: wys
 * @Desc: 方法调用的状态响应码
 * @Date: 2021/11/16
**/ 
@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "调用方法"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    private final int code;
    private final String message;

}
