package tech.wys.rpc.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.entity.RpcResponse;
import tech.wys.rpc.registry.ServiceRegistry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: wys
 * @Desc: 处理RpcRequest的工作线程
 * @Date: 2021/11/16
**/
public class RequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

            RpcRequest rpcRequest = (RpcRequest) ois.readObject();  // 读取客户端传来的请求
            String interfaceName = rpcRequest.getInterfaceName();   // 获取服务名称
            Object service = serviceRegistry.getService(interfaceName); // 从注册中心获取对应的服务实体
            Object result = requestHandler.handle(rpcRequest, service);// 进行过程调用
            oos.writeObject(RpcResponse.sucess(result));
            oos.flush();

        } catch (IOException | ClassNotFoundException e) {
            logger.error("调用或发送时有错误发送：", e);
        }
    }
}
