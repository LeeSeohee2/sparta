package com.example.chat.service;

import com.example.chat.domain.user.User;
import com.example.chat.domain.user.UserRepository;
import com.example.chat.security.JwtTokenProvider;
import com.example.chat.web.dto.LoginReqeust;
import com.example.chat.web.dto.TokenResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    //인증과 관련된 서비스 처리

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public  AuthService(UserRepository userRepository,
                        PasswordEncoder passwordEncoder
                        , JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //1) 이메일 찾기
    public TokenResponse login(LoginReqeust loginReqeust){

        //2)user 정보가 없을 경우(이메일이 없을 경우에는 에러를 발생할 수있다)
        User user = userRepository.findByEmail(loginReqeust.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("이메일이 없습니다."));

        //3) 이메일과 비밀번호를 확인한다.
        //  로그인한 비밀번호랑, 회원가입시 입력한 비밀번호랑 비교를 해야된다.
        //  회원가입시 비밀번호는 암호화가 되어있다. 그래서 matches()
        //  비밀번호가 맞으면 토큰이 있는지 토큰을 가져오기 메서드를 실행하면되고!
        //  만약 비밀번호가 맞지 않다면 더 이상 실행 이유가 없다!
        if(!passwordEncoder.matches(loginReqeust.getPassword(),
                                    user.getPassword())){
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다.");
        }
        //4)로그인 정보가 맞다면 토큰을 발급한다.
        String token = jwtTokenProvider.generateAccessToken(user);

        return new TokenResponse(token,user.getId());

    }


}
