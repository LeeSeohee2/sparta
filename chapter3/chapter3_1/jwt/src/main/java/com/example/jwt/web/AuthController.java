package com.example.jwt.web;

import com.example.jwt.JwtTokenProvider;
import com.example.jwt.domain.user.Role;
import com.example.jwt.service.UserService;
import com.example.jwt.web.dto.LoginRequest;
import com.example.jwt.web.dto.SignupRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        //1) 데이터 확인용!
        System.out.println("데이터 확인: " + request.getEmail());

        //2) 실제 데이터를 처리하는 서비스 메서드!
        Long result = userService.signup(request);

        //3) 결과 확인용!
        System.out.println("결과:" + result);

        return "signup";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String result =  jwtTokenProvider.createAccessToken("1","eryna.g.c", Role.USER);
        System.out.println("토큰값:"+result);
        return "login";
    }
}
