package tech.wys.test;


import tech.wys.rpc.netty.server.NettyServer;
import tech.wys.rpc.registry.DefaultServiceRegistry;
import tech.wys.rpc.serializer.HessianSerializer;
import tech.wys.rpc.serializer.JsonSerializer;
import tech.wys.rpc.serializer.KryoSerializer;

/**
 * @Author: wys
 * @Desc: 测试用Netty服务提供者（服务端）
 * @Date: 2021/11/16
**/ 
public class NettyTestServer {

    public static void main(String[] args) {
        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new JsonSerializer());
        server.start(9999);
    }
}
