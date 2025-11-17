package com.aditya.todoApp.CorsConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtillity {

    private String secret = "A8fu92KdL0pQwXr3Zs9TjBv4Nc7Gh5Uy1Rq2Mx6Wz8Ye0Ts4Vn9Hb3Df6Pq8Kl2Mn8fu92KdL0pQwXr3Zs9TjBv4Nc7Gh5Uy1Rq2Mx6Wz8Ye0Ts4Vn9Hb3Df6Pq8Kl2Mn";
    private long jwtExpirationMs = 604800000;

    public String generateToken(String userName){
        return Jwts
                .builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (JwtException  | IllegalArgumentException e){
            return false;
        }
    }

}
