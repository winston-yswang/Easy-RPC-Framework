package tech.wys.rpc.socket.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.RpcClient;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.entity.RpcResponse;
import tech.wys.rpc.enumeration.ResponseCode;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.socket.server.SocketServer;
import tech.wys.rpc.socket.util.ObjectReader;
import tech.wys.rpc.socket.util.ObjectWriter;
import tech.wys.rpc.util.RpcMessageChecker;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author: wys
 * @Desc: Socket方式远程方法调用的消费者（客户端）
 * @Date: 2021/11/19
**/ 
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;

    private CommonSerializer serializer;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try (Socket socket = new Socket(host, port)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer); // 对rpcRequest序列化并发送

            Object obj = ObjectReader.readObject(inputStream);  // 接受服务端返回的数据
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();

        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
