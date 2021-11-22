package tech.wys.rpc.transport;

import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.serializer.CommonSerializer;

public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
