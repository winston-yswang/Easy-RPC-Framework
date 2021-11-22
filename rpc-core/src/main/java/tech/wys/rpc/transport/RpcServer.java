package tech.wys.rpc.transport;


import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.serializer.CommonSerializer;

/**
 * @Author: wys
 * @Desc: 服务器类通用接口
 * @Date: 2021/11/20
**/
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);

}
