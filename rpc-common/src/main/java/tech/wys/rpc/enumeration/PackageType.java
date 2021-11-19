package tech.wys.rpc.enumeration;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author: wys
 * @Desc: 包类型
 * @Date: 2021/11/17
**/ 
@AllArgsConstructor
@Getter
public enum PackageType {
    
    REQUEST_PACK(0),
    RESPONSE_PACK(1);
    
    private final int code;
}
