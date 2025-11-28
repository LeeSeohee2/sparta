package com.example.jwt;


import com.example.jwt.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long validityMillis;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long secs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityMillis = secs * 1000L;
    }

    // Access Token을 생성하는 함수를 작성하기
    public String createAccessToken(String userId, String email, Role role) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))   // 사용자 고유 번호 PK
                .claim("email", email)              // 사용자 이메일
                .claim("role", role)                // USER/ADMIN
                .setIssuedAt(now)                     // 발급 시간
                .setExpiration(expiry)                // 만료
                .signWith(key, SignatureAlgorithm.HS256)  // 토큰 문자열 발급
                .compact();                               // 최종 토큰 문자열받기

    }

    //사용자의 정보를 꺼내가는 메서드!
    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // 만료,혹시 위조됬는지 확인하는 메서드!
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }




}
