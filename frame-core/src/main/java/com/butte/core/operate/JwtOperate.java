package com.butte.core.operate;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.butte.base.entity.ExeRep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

/**
 * JWT加密解密工具
 * @author 公众号:知了一笑
 * @since 2021-09-13 20:39
 */
@Component
public class JwtOperate {

    @Value("${security.jwt.secret:jwtSecret}")
    private String secret ;

    /**
     * 内容加密
     * @param content 内容
     * @return java.lang.String 令牌
     * @since 2021-09-13 20:45
     */
    public String encode (Object content){
        Jwt jwt = JwtHelper.encode(String.valueOf(content),macSigner()) ;
        return jwt.getEncoded() ;
    }

    /**
     * 令牌解密
     * @param token 令牌
     * @return java.lang.Integer
     * @since 2021-09-13 20:46
     */
    public Integer parseIntDecode (String token){
        String claims = JwtHelper.decode(token).getClaims() ;
        if (StrUtil.isNotEmpty(claims)){
            int claimsId = Integer.parseInt(claims) ;
            if (claimsId < 1){
                throw new ExeRep(HttpStatus.HTTP_FORBIDDEN,"token error");
            }
            return claimsId;
        }
        throw new ExeRep(HttpStatus.HTTP_FORBIDDEN,"token error");
    }

    /**
     * 令牌解密
     * @param token 令牌
     * @return java.lang.Integer
     * @since 2021-09-13 20:46
     */
    public String decode (String token){
        String claims = JwtHelper.decode(token).getClaims() ;
        if (StrUtil.isNotEmpty(claims)){
            return claims ;
        }
        throw new ExeRep(HttpStatus.HTTP_FORBIDDEN,"token error");
    }

    /**
     * 签名
     */
    @Bean
    private MacSigner macSigner (){
        return new MacSigner(secret) ;
    }

}
