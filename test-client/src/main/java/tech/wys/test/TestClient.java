package tech.wys.test;

import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.client.RpcClientProxy;


/**
 * @Author: wys
 * @Desc: 测试用消费者（客户端）
 * @Date: 2021/11/16
**/ 
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
