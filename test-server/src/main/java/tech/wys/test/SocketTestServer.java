package tech.wys.test;

import tech.wys.rpc.annotation.ServiceScan;
import tech.wys.rpc.api.HelloService;
import tech.wys.rpc.serializer.CommonSerializer;
import tech.wys.rpc.transport.RpcServer;
import tech.wys.rpc.transport.socket.server.SocketServer;


/**
 * @Author: wys
 * @Desc: 测试用服务提供方（服务端）
 * @Date: 2021/11/16
**/
@ServiceScan
public class SocketTestServer {

    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9998, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }

}
