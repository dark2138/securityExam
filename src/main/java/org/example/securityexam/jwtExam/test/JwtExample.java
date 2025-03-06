package org.example.securityexam.jwtExam.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class JwtExample {
    public static void main(String[] args) {
       // SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String secret = "abcdefghijklmnopqrstuvwxzy123456";
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey =  Keys.hmacShaKeyFor(bytes);

        // jwt 생성
        String jwt = Jwts.builder()
                .setIssuer("Do-app")
                .setSubject("DO1212")
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setAudience("Do-audience")
                .claim("role", "ADMIN")
                .signWith(secretKey)
                .compact();

        System.out.println(jwt);

        System.out.println("==============================================");

        // jwt 파싱 검증
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        System.out.println(claims.getExpiration());
        System.out.println(claims.getSubject());
        System.out.println(claims.getAudience());
    }
}
