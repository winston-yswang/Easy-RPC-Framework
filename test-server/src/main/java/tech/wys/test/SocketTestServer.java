package tech.wys.test;

import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.transport.socket.server.SocketServer;


/**
 * @Author: wys
 * @Desc: 测试用服务提供方（服务端）
 * @Date: 2021/11/16
**/ 
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }

}
