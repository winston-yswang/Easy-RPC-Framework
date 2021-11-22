package tech.wys.rpc.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author: wys
 * @Desc: 默认的服务注册表，保存服务端本地服务
 * @Date: 2021/11/21
**/ 
public class ServiceProviderImpl implements ServiceProvider {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();    // 服务接口名与服务实体的映射
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet(); // 已经注册的服务
    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        if (registeredService.contains(serviceName)) return;    // 已经注册过该服务
        registeredService.add(serviceName); // 新服务开始注册流程
        serviceMap.put(serviceName, service);
        logger.info("向接口: {} 注册服务: {}", service.getClass().getInterfaces(), serviceName);
    }

    /**
     * @Desc: 返回服务实体
     * @Param: String 服务名称
     * @return: Object
     **/
    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
