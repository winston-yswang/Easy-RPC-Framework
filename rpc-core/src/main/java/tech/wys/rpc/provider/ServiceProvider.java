package tech.wys.rpc.provider;


/**
 * @Author: wys
 * @Desc: 保存和提供服务实例对象
 * @Date: 2021/11/21
**/ 
public interface ServiceProvider {
    
    <T> void addServiceProvider(T service, String serviceName);
    
    Object getServiceProvider(String serviceName);
}
