package com.island.myblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Luo Zhen
 * @create 2019-05-29 9:52
 */
@Slf4j
@Data
@Component
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    /**
     * 盐
     */
    private String key;
    /**
     * 超时时间
     */
    private long ttl;

    private String header;



    /**
     * 生成JWT
     *
     * @param id
     * @return
     */
    public String createJWT(Integer id) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder jwt = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(id+"")
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(nowMillis + ttl * 1000));

        return jwt.compact();
    }

    /**
     * 解析JWT
     *
     * @param token
     * @return
     */
    public Claims parseJWT(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            log.debug("validate is token error",e);
            return null;
        }

    }

    public boolean isTokenExpired(Date expiration){
        return expiration.before(new Date());
    }
}
