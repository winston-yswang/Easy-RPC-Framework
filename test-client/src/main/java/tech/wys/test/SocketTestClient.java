package tech.wys.test;

import tech.wys.rpc.RpcClientProxy;
import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.HessianSerializer;
import tech.wys.rpc.serializer.JsonSerializer;
import tech.wys.rpc.serializer.KryoSerializer;
import tech.wys.rpc.socket.client.SocketClient;


/**
 * @Author: wys
 * @Desc: 测试用消费者（客户端）
 * @Date: 2021/11/16
**/ 
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
