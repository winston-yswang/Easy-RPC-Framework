package tech.wys.rpc.loadBalancer;


import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Author: wys
 * @Desc: 负载均衡接口
 * @Date: 2021/11/21
**/
public interface LoadBalancer {

    Instance select(List<Instance> instances);

}
