package com.butte.base.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 异常包装
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ExeRep extends RuntimeException {

    private int code ;

    private String msg ;

    public ExeRep(Rep baseResp) {
        super(baseResp.getMsg());
        this.code = baseResp.getCode() ;
        this.msg = baseResp.getMsg() ;
    }

}
