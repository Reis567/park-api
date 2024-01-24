package com.reis.demo.park.api.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
    private static Key generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start ){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRES_HOURS).plusMinutes(EXPIRES_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }
}
