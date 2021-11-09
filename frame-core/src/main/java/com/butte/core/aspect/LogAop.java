package com.butte.core.aspect;

import com.butte.base.constant.BizOptEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 * @author 公众号:知了一笑
 * @since 2021-09-22 22:52
 */
@Target({ElementType.METHOD})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAop {

    /**
     * 业务标识
     */
    String bizSign() ;

    /**
     * bizSign 操作标识
     */
    BizOptEnum optSign() ;
}