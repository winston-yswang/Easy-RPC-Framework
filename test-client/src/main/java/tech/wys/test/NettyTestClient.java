package tech.wys.test;


import tech.wys.rpc.RpcClient;
import tech.wys.rpc.RpcClientProxy;
import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.netty.client.NettyClient;
import tech.wys.rpc.serializer.HessianSerializer;
import tech.wys.rpc.serializer.JsonSerializer;
import tech.wys.rpc.serializer.KryoSerializer;

/**
 * @Author: wys
 * @Desc: 测试用Netty消费者
 * @Date: 2021/11/18
**/
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new JsonSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);


    }
}
