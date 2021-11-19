package tech.wys.rpc;

import tech.wys.rpc.serializer.CommonSerializer;


/**
 * @Author: wys
 * @Desc: 服务器类通用接口
 * @Date: 2021/11/16
**/ 
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);
}
