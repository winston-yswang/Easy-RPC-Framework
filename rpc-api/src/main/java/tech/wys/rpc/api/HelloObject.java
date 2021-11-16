package tech.wys.rpc.api;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * @Author: wys
 * @Desc: 测试用api的实体
 * @Date: 2021/11/16
**/ 
@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    
    private Integer id;
    private String message;
    
}
