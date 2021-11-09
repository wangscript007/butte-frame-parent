package com.butte.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecord {

    private Long id;

    private String appName ;

    private String className ;

    private String methodName ;

    private String bizSign ;

    private String optSign ;

    private String[] paramArr ;

    private Object[] argsArr ;

    private Long spanTime ;

    private Date createTime ;
}