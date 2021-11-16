package tech.wys.rpc.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author: wys
 * @Desc: RPC调用过程中的错误
 * @Date: 2021/11/15
**/
@AllArgsConstructor
@Getter
public enum RpcError {

    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;

}
