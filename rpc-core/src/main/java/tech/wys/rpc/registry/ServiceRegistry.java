package tech.wys.rpc.registry;


/**
 * @Author: wys
 * @Desc: 服务注册表通用接口
 * @Date: 2021/11/15
**/
public interface ServiceRegistry {

    /**
     * @Desc: 将一个服务注册进注册表
     * @Param: service 待注册的服务实体
     * @return: <T> 服务实体类
    **/
    <T> void register(T service);

    /**
     * @Desc: 根据服务名称获取服务实体
     * @Param: serviceName 服务名称
     * @return: <T> 服务实体
     **/
    Object getService(String serviceName);
}
