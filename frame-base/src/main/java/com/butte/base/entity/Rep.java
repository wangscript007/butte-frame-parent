package com.butte.base.entity;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返参包装
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rep<T> {

    private int code ;

    private String msg ;

    private T data ;

    public Boolean isSus (){
        return code == 200 ;
    }

    /**
     * 构造方法
     */
    public Rep(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 正常响应
     * @since 2021-08-07 19:30
     */
    public static <T> Rep<T> ok (T data) {
        Rep<T> result = new Rep<>();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg("OK");
        result.setData(data);
        return result ;
    }

    /**
     * 正常空响应
     * @since 2021-08-07 19:30
     */
    public static Rep<Void> okVoid () {
        Rep<Void> result = new Rep<>();
        result.setCode(HttpStatus.HTTP_OK);
        result.setMsg("OK");
        return result ;
    }

    /**
     * 错误描述
     * @since 2021-08-07 19:29
     */
    public static <T> Rep<T> error (ExeRep baseExe) {
        Rep<T> result = new Rep<>();
        result.setMsg(baseExe.getMsg());
        result.setCode(baseExe.getCode());
        return result ;
    }

    /**
     * 未知异常
     * @since 2021-08-07 19:29
     */
    public static <T> Rep<T> unknown (String msg) {
        Rep<T> result = new Rep<>();
        result.setMsg(msg);
        result.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        return result ;
    }
}