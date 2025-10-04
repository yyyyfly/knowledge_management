package com.knowledge.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 */
public class JwtUtil {
    // 密钥（生产环境应该放到配置文件中）
    private static final String SECRET_KEY = "knowledge-management-system-secret-key-2024-very-long-secret";
    
    // Token有效期：24小时
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成Token
     */
    public static String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析Token
     */
    public static Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从Token中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 验证Token是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}

