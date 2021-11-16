package tech.wys.test;

import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.registry.DefaultServiceRegistry;
import tech.wys.rpc.registry.ServiceRegistry;
import tech.wys.rpc.server.RpcServer;


/**
 * @Author: wys
 * @Desc: 测试用服务提供方（服务端）
 * @Date: 2021/11/16
**/ 
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl(); // 创建一个服务实体
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry(); // 创建一个注册中心
        serviceRegistry.register(helloService); // 将服务注册在注册中心
        RpcServer rpcServer = new RpcServer(serviceRegistry);   // rpc服务器与注册中心关联
        rpcServer.start(9000);  // 启动rpc服务


    }

}
