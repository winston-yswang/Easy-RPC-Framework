package tech.wys.test;


import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.transport.RpcClientProxy;
import tech.wys.rpc.transport.socket.client.SocketClient;

/**
 * @Author: wys
 * @Desc: 测试用消费者（客户端）
 * @Date: 2021/11/16
**/ 
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        for(int i = 0; i < 20; i ++) {
            String res = helloService.hello(object);
            System.out.println(res);
        }
    }


}
