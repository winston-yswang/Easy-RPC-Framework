package tech.wys.rpc.registry;


import java.net.InetSocketAddress;

/**
 * @Author: wys
 * @Desc: 服务注册通用接口
 * @Date: 2021/11/15
**/
public interface ServiceRegistry {

    /**
     * @Desc: 将一个服务注册进注册表
     * @Param: serviceName 服务名称
     * @return: inetSocketAddress 提供服务的地址
    **/
    void register(String serviceName, InetSocketAddress inetSocketAddress);


}
