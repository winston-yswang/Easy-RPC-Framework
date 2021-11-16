package tech.wys.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.api.HelloObject;
import tech.wys.rpc.api.HelloService;


/**
 * @Author: wys
 * @Desc: HelloService接口的实现类
 * @Date: 2021/11/16
**/
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是调用的返回值，id=" + object.getId();
    }
}
