package tech.wys.rpc.entity;

import lombok.Data;
import tech.wys.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @Author: wys
 * @Desc: 提供者执行完成或者出错后向消费者返回的结果对象
 * @Date: 2021/11/16
**/
@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 请求号
     */
    private String requestId;

    /**
     * 响应状态码
     */
    private Integer statusCode;

    /**
     * 响应状态补充信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 调用成功返回状态码和数据等
    */
    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    /**
     * 调用失败返回状态码和失败原因
     */
    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
