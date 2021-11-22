package tech.wys.test;


import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.transport.netty.server.NettyServer;

/**
 * @Author: wys
 * @Desc: 测试用Netty服务提供者（服务端）
 * @Date: 2021/11/16
**/ 
public class NettyTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.HESSIAN_SERIALIZER);
        server.publishService(helloService, HelloService.class);

    }
}
