package tech.wys.rpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.entity.RpcResponse;
import tech.wys.rpc.enumeration.ResponseCode;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;


/**
 * @Author: wys
 * @Desc: 检查响应与请求
 * @Date: 2021/11/17
**/ 
public class RpcMessageChecker {

    public static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    public static final String INTERFACE_NAME = "interfaceName";

    private RpcMessageChecker() {}

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse) {
        if (rpcResponse == null) {
            logger.error("调用服务失败，serviceName: {}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            logger.error("请求与响应号不匹配: {} != {}", rpcRequest.getRequestId(), rpcResponse.getRequestId());
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode())) {
            logger.error("调用服务失败，serviceName：{}, RpcResponse：{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }

}
