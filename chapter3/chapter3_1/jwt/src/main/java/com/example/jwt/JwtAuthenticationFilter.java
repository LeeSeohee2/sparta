package com.example.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter
//  = Http 하나의 요청마다 딱 한번만 실행되는 필터를 만들기 위해서 상속받은 추상클래스

// 모든 요청마다 한번씩 실행되는 JWT검사기!
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 모든 요청이 들어올 때마다 실행하는 메서드

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                throws ServletException, IOException {
        // 1)요청 헤더에서 JWT를 뽑기
        String token = resolve(request);

        //2) 유효한 토큰이면 유저 정보 조회
        if(token != null && jwtTokenProvider.validate(token)){
            // 2-1) 토큰이 만료되었는지 확인을 위해서 provider안에 validate를 함수를 생성한다
            // 3)만약 유효한 토큰이라면 사용자 정보 꺼내기!
            Claims claims = jwtTokenProvider.parse(token);

            String email = claims.get("email",String.class);

        }

    }

    private String resolve(HttpServletRequest req) {
        String bearer = req.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer "))
                ? bearer.substring(7)
                : null;
    }

}
