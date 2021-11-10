package com.butte.feign.decode;

import cn.hutool.http.HttpStatus;
import com.butte.base.entity.ExeRep;
import com.butte.base.entity.Rep;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Feign解码
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:33
 */
public class FeignDecode extends ResponseEntityDecoder {

    public FeignDecode(Decoder decoder) {
        super(decoder);
    }

    @Override
    public Object decode(Response response, Type type) {
        if (!type.getTypeName().startsWith(Rep.class.getName())) {
            throw new RuntimeException("响应格式异常");
        }
        try {
            return super.decode(response, type);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
