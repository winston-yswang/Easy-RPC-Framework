package tech.wys.rpc.exception;


import tech.wys.rpc.enumeration.RpcError;

/**
 * @Author: wys
 * @Desc: RPC调用异常
 * @Date: 2021/11/15
**/
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) { super(error.getMessage() + ":" + detail); }

    public RpcException(String message, Throwable cause) { super(message, cause); }

    public RpcException(RpcError error) { super(error.getMessage()); }

}
