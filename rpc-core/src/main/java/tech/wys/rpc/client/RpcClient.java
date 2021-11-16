package tech.wys.rpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.entity.RpcResponse;
import tech.wys.rpc.enumeration.ResponseCode;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Author: wys
 * @Desc: 远程调用的消费者
 * Date: 2021/11/15
**/ 
public class RpcClient {

    private static final Logger logger= LoggerFactory.getLogger(RpcClient.class);

    public Object sendRequest(RpcRequest rpcRequest, String host, int port) {

        try(Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush(); // 向服务端请求方法调用

            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject(); // 接受服务端返回结果
            // 返回结果为空
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service: " + rpcRequest.getInterfaceName());
            }
            // 响应状态码为空或者返回状态码不为SUCCESS
            if (rpcResponse.getStatusCode() == null && rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败，service: {}, response: {}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, "service: " + rpcRequest.getInterfaceName());
            }

            return rpcResponse.getData();

        } catch (Exception e) {
           logger.error("调用时有错误发生：", e);
           throw new RpcException("服务调用失败：", e);
        }
    }

}
