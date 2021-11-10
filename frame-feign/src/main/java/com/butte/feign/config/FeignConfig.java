package com.butte.feign.config;

import com.butte.feign.decode.FeignDecode;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
/**
 * Feign基础配置
 * @author 公众号:知了一笑
 * @since 2021-08-07 19:32
 */
@Configuration
public class FeignConfig {

    @Bean
    @Primary
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> feignHttpConverter) {
        return new OptionalDecoder(
                new FeignDecode(new SpringDecoder(feignHttpConverter)));
    }
}
