package com.example.chat.web;

import com.example.chat.security.JwtTokenProvider;
import com.example.chat.service.AuthService;
import com.example.chat.service.UserService;
import com.example.chat.web.dto.LoginReqeust;
import com.example.chat.web.dto.SignupRequest;
import com.example.chat.web.dto.TokenResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwt;

    public AuthController(UserService userService,
                          AuthService authService,
                          JwtTokenProvider jwt) {
        this.userService = userService;
        this.authService = authService;
        this.jwt = jwt;

    }

    //회원가입시 데이터 가져오기
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest user) {

        System.out.println("signup 메서드 들어왔다."+ user.getEmail());

        //회원가입을 처리하는 서비스를 호출하기
        Long result = userService.signup(user);

        System.out.println("현재 저장된 userId :" + result +"번째 ");


        return "signup";
    }

    //로그인시 데이터 가져오기
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginReqeust login) {

        TokenResponse token = authService.login(login);
        System.out.println("토큰값: " + token);
        return token;
    }

    @GetMapping("/test")
    public String test(
            @RequestHeader("Authorization") String authHeader
    ) {
        //1) "Bearer {accessToken} -> 실제 부분만 추출
        String token = authHeader.replace("Bearer ", "");

        //2) userId를 검색해서 가져오기
        Long userId = jwt.getUserId(token);

        return "userId = " + userId;
    }




}
