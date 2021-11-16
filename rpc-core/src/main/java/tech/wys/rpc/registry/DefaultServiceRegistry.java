package tech.wys.rpc.registry;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: wys
 * @Desc: 默认的服务注册表
 * @Date: 2021/11/16
**/
public class DefaultServiceRegistry implements ServiceRegistry{

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();   // 服务接口名与服务实体的映射
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();    // 已经注册的服务


    /**
     * @Desc: 服务端初始化时注册服务
     * @Param: <T> 服务实体
     * @return:
    **/
    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName(); // 返回service的包含路径类名
        if (registeredService.contains(serviceName))    // 已经注册过该服务
            return;
        registeredService.add(serviceName); // 新服务开始注册流程
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口：{} 注册服务：{}", interfaces, serviceName);

    }

    /**
     * @Desc: 返回服务实体
     * @Param: String 服务名称
     * @return: Object
    **/
    @Override
    public synchronized Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
