package tech.wys.rpc;

import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.serializer.CommonSerializer;


/**
 * @Author: wys
 * @Desc: 客户端类通用接口
 * @Date: 2021/11/16
**/ 
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);
}
