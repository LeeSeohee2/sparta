package com.example.jwt.service;

import com.example.jwt.domain.user.Role;
import com.example.jwt.domain.user.User;
import com.example.jwt.domain.user.UserRepository;
import com.example.jwt.web.dto.SignupRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //데이터베이스 전담해주세요!
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long signup(SignupRequest req) {

        //1) 이메일 중복 체크를 해야된다.
        if(userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        //2) 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(req.getPassword());

        //3) User 엔티티 생성
        User user = new User(
                req.getName(),
                req.getEmail(),
                encodedPassword,
                Role.USER
        );

        //4) 저장 & ID반환
        return userRepository.save(user).getId();

    }

}
