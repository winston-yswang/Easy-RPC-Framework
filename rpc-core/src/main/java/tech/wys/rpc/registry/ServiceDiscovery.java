package tech.wys.rpc.registry;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    /**
     * @Desc: 根据服务名称查找服务实体
     * @Param: serviceName 服务名称
     * @return: 服务实体
    **/
    InetSocketAddress lookupService(String serviceName);

}
