package com.reis.demo.park.api.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
    
    public static final  String JWT_BEARER = "Bearer ";
    public static final  String JWT_AUTHORIZATION = "Authorization";
    public static final  String SECRET_KEY = "PLS-CHANGE-ME-PLS-CHANGE-ME-PLS-CHANGE-ME";
    public static final  long EXPIRE_DAYS = 0;
    public static final  long EXPIRES_HOURS = 0;
    public static final  long EXPIRES_MINUTES = 2;

    private JwtUtils(){

    }
    private static javax.crypto.SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        }

    private static Date toExpireDate(Date start ){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRES_HOURS).plusMinutes(EXPIRES_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static JwtToken createToken(String username, String role){
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);
        String token = Jwts.builder()
            .header().add("typ", "JWT")
            .and()
            .subject(username).issuedAt(issuedAt)
            .expiration(limit)
            .signWith(generateKey())
            .claim("role", role)
            .compact();
            return new JwtToken(token);
    }
    private static Claims getClaimsFromToken(String token){
        try {
            return Jwts.parser()
                        .verifyWith(generateKey())
                        .build()
                        .parseSignedClaims(refactorToken(token)).getPayload();
        } catch (JwtException exception) {
            log.error(String.format("Token invalido %s", exception.getMessage()));
        }
        return null;

    }

    public static String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    private static String refactorToken(String token){
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length())
        }
        return token;
    }
}
