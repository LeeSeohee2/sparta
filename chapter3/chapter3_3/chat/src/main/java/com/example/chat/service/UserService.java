package com.example.chat.service;

import com.example.chat.domain.user.Role;
import com.example.chat.domain.user.User;
import com.example.chat.domain.user.UserRepository;
import com.example.chat.web.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 서비스가 하는 일은 데이터베이스를 하기 전에 내용들을 처리하는 메서드!
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입시 실행할 메서드
    public Long signup(SignupRequest req){

        // 1) 내가 회원가입한 email 중복 확인
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("중복 이메일");
        }

        //2) 이메일 중복이 아닐경우 비밀번호 암호화를 해서 보내야된다.
        String encodedPw = passwordEncoder.encode(req.getPassword());

        //3) 데이터베이스로 보내기 위해서 User엔티티로 정보를 묶는다.
        User user = new User(
                req.getName(),
                req.getEmail(),
                encodedPw,
                Role.user
        );

        //4) DB호출을 한 다음에 저장이 완료되면 아이디를 가져와서 컨트롤러에서
        //  결과를 보내준다.
        return userRepository.save(user).getId();
    }

}
