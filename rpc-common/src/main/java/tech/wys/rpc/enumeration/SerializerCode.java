package tech.wys.rpc.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author: wys
 * @Desc: 字节流中标识序列化和反序列化器
 * @Date: 2021/11/17
**/ 
@AllArgsConstructor
@Getter
public enum SerializerCode {
    
    KRYO(0),
    JSON(1),
    HESSIAN(2);
    
    private final int code;
}
