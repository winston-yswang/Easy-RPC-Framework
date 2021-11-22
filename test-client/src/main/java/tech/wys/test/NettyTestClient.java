package tech.wys.test;


import tech.wys.rpc.api.ByeService;
import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.transport.RpcClient;
import tech.wys.rpc.transport.RpcClientProxy;
import tech.wys.rpc.transport.netty.client.NettyClient;

/**
 * @Author: wys
 * @Desc: 测试用Netty消费者
 * @Date: 2021/11/18
**/
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(CommonSerializer.HESSIAN_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));

    }
}
