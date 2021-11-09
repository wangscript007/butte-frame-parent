package com.butte.core.handle;

import com.butte.base.entity.ExeRep;
import com.butte.base.entity.Rep;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常映射
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:31
 */
@RestControllerAdvice
public class ExeHandle {

    /**
     * 默认异常
     * @since 2021-08-07 19:31
     */
    @ExceptionHandler(value = Exception.class)
    public Rep<?> defaultException(Exception exception) {
        return Rep.unknown(exception.getMessage());
    }

    /**
     * IO异常
     * @since 2021-08-07 19:31
     */
    @ExceptionHandler(value = IOException.class)
    public Rep<?> IOException(IOException exception) {
        return Rep.unknown(exception.getMessage());
    }

    /**
     * 自定义异常
     * @since 2021-08-07 19:31
     */
    @ExceptionHandler(value = ExeRep.class)
    public Rep<?> repException(ExeRep exeRep) {
        return Rep.error(exeRep);
    }

}
