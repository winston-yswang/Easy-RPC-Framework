package tech.wys.test;

import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.registry.DefaultServiceRegistry;
import tech.wys.rpc.registry.ServiceRegistry;
import tech.wys.rpc.serializer.HessianSerializer;
import tech.wys.rpc.serializer.JsonSerializer;
import tech.wys.rpc.serializer.KryoSerializer;
import tech.wys.rpc.socket.server.SocketServer;


/**
 * @Author: wys
 * @Desc: 测试用服务提供方（服务端）
 * @Date: 2021/11/16
**/ 
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl(); // 创建一个服务实体
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry(); // 创建一个注册中心
        serviceRegistry.register(helloService); // 将服务注册在注册中心
        SocketServer socketServer = new SocketServer(serviceRegistry);   // rpc服务器与注册中心关联
        socketServer.setSerializer(new KryoSerializer());
        socketServer.start(9000);  // 启动rpc服务


    }

}
